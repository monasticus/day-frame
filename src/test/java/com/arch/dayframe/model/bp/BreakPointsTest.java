package com.arch.dayframe.model.bp;

import com.arch.dayframe.model.bp.BreakPointException.ErrorCode;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

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

    @Test
    void getBreakPointsListAllFuture() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 52), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
        List<BreakPoint> breakPointsList = breakPoints.getBreakPointsList();
        int bpSize = breakPoints.getSize();
        int bpListSize = breakPointsList.size();

        assertNotNull(breakPointsList);
        assertEquals(bpSize, bpListSize);
        assertEquals("23:52", breakPointsList.get(0).getTimeValue());
        assertEquals("23:53", breakPointsList.get(1).getTimeValue());
        assertEquals("23:54", breakPointsList.get(2).getTimeValue());
        assertEquals("23:55", breakPointsList.get(3).getTimeValue());
        assertEquals("23:56", breakPointsList.get(4).getTimeValue());
        assertEquals("23:57", breakPointsList.get(5).getTimeValue());
        assertEquals("23:58", breakPointsList.get(6).getTimeValue());
        assertEquals("23:59", breakPointsList.get(7).getTimeValue());
        assertEquals("", breakPointsList.get(0).getMessage());
        assertEquals("test message", breakPointsList.get(1).getMessage());
        assertEquals("test message aA1!", breakPointsList.get(2).getMessage());
        assertEquals("test message", breakPointsList.get(3).getMessage());
        assertEquals("test message", breakPointsList.get(4).getMessage());
        assertEquals("test message", breakPointsList.get(5).getMessage());
        assertEquals("1", breakPointsList.get(6).getMessage());
        assertEquals("12345678901234567890123456789012345", breakPointsList.get(7).getMessage());
    }

    @Test
    void getBreakPointsListSomePast() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 56), new BPSimpleTime(0, 3));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT_SOME_PAST));
        List<BreakPoint> breakPointsList = breakPoints.getBreakPointsList();
        int bpSize = breakPoints.getSize();
        int bpListSize = breakPointsList.size();

        assertNotNull(breakPointsList);
        assertEquals(bpSize, bpListSize);
        assertEquals("23:56", breakPointsList.get(0).getTimeValue());
        assertEquals("23:57", breakPointsList.get(1).getTimeValue());
        assertEquals("23:58", breakPointsList.get(2).getTimeValue());
        assertEquals("23:59", breakPointsList.get(3).getTimeValue());
        assertEquals("test message", breakPointsList.get(0).getMessage());
        assertEquals("test message aA1!", breakPointsList.get(1).getMessage());
        assertEquals("test message", breakPointsList.get(2).getMessage());
        assertEquals("1", breakPointsList.get(3).getMessage());
    }

    @Test
    void testBreakPointsListEncapsulation() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 52), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
        List<BreakPoint> breakPointsList = breakPoints.getBreakPointsList();
        int bpSizeBefore = breakPoints.getSize();
        int bpListSizeBefore = breakPointsList.size();

        breakPointsList.remove(0);

        List<BreakPoint> breakPointsListAfter = breakPoints.getBreakPointsList();
        int bpSizeAfter = breakPoints.getSize();
        int bpListSizeAfter = breakPointsListAfter.size();

        assertEquals(bpSizeBefore, bpSizeAfter);
        assertEquals(bpListSizeBefore, bpListSizeAfter);
    }

    private void failTestInTimeFrame(SimpleTime timeStart, SimpleTime timeEnd) {
        timeEnd.add(1);
        if (!timeStart.isFuture())
            fail("Wait until " + timeEnd.getTime() + " with the test execution");
    }
}
