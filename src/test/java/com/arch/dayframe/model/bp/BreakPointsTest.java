package com.arch.dayframe.model.bp;

import com.arch.dayframe.model.bp.BreakPointException.ErrorCode;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@Tag("BreakPoints")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BreakPointsTest {

    private static final String TEST_DATA_DIRECTORY = "src/test/resources/model/bp/";
    private static final String VARIOUS_CORRECT = TEST_DATA_DIRECTORY + "break-points-various-correct.txt";
    private static final String VARIOUS_CORRECT_AND_INCORRECT_COMBINED = TEST_DATA_DIRECTORY + "break-points-various-correct-and-incorrect-combined.txt";
    private static final String VARIOUS_CORRECT_TIME_DUPLICATED = TEST_DATA_DIRECTORY + "break-points-various-correct-duplicated.txt";
    private static final String VARIOUS_CORRECT_SOME_PAST = TEST_DATA_DIRECTORY + "break-points-various-correct-some-past.txt";


    @Test
    void createBreakPointsIncorrect() {
        BreakPointException e = assertThrows(BreakPointException.class, () -> new BreakPoints(VARIOUS_CORRECT_AND_INCORRECT_COMBINED));
        assertEquals(ErrorCode.DESCRIPTION_FORMAT_ERR, e.getErrorCode());
        assertEquals("Wrong break point description: 9:59", e.getMessage());
    }

    @Test
    void createBreakPointsDuplicates() {
        BreakPointException e = assertThrows(BreakPointException.class, () -> new BreakPoints(VARIOUS_CORRECT_TIME_DUPLICATED));
        assertEquals(ErrorCode.TIME_DUPLICATE_ERR, e.getErrorCode());
        assertEquals("Duplicated break point time: 23:59", e.getMessage());
    }

    @Test
    void createBreakPointsCorrect() {
        assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
    }

    @Test
    void createBreakPointsSomePast() {
        assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT_SOME_PAST));
    }

    @Test
    void getSizeAllFuture() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 52), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
        int bpSize = breakPoints.getSize();

        assertEquals(8, bpSize);
    }

    @Test
    void getSizeSomePast() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 56), new BPSimpleTime(0, 3));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT_SOME_PAST));
        int bpSize = breakPoints.getSize();

        assertEquals(4, bpSize);
    }

    private void failTestInTimeFrame(SimpleTime timeStart, SimpleTime timeEnd) {
        timeEnd.add(1);
        if (!timeStart.isFuture())
            fail("Wait until " + timeEnd.getTime() + " with the test execution");
    }
}
