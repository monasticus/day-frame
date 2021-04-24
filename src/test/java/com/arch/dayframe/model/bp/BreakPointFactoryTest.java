package com.arch.dayframe.model.bp;

import com.arch.dayframe.model.time.BPSimpleTime;
import com.arch.dayframe.model.time.SimpleTime;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.arch.dayframe.model.bp.BreakPointException.ErrorCode.DESCRIPTION_FORMAT_ERR;
import static com.arch.dayframe.model.bp.BreakPointException.ErrorCode.TIME_DUPLICATE_ERR;
import static org.junit.jupiter.api.Assertions.*;

@Tag("BreakPointFactory")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BreakPointFactoryTest {

    private static final String TEST_DATA_DIRECTORY = "src/test/resources/model/bp/";
    private static final String NONEXISTENT_BREAK_POINTS_LINE = TEST_DATA_DIRECTORY + "nonexistent-break-points.txt";
    private static final String EMPTY_ONE_LINE = TEST_DATA_DIRECTORY + "break-points-empty-one-line.txt";
    private static final String EMPTY_TWO_LINES = TEST_DATA_DIRECTORY + "break-points-empty-two-lines.txt";
    private static final String EMPTY_FIRST_LINE_AND_ONE_CORRECT_DESC = TEST_DATA_DIRECTORY + "break-points-empty-first-line-and-one-correct-desc.txt";
    private static final String EMPTY_FIRST_LINE_AND_ONE_INCORRECT_DESC = TEST_DATA_DIRECTORY + "break-points-empty-first-line-and-one-incorrect-desc.txt";
    private static final String VARIOUS_CORRECT = TEST_DATA_DIRECTORY + "break-points-various-correct.txt";
    private static final String VARIOUS_INCORRECT = TEST_DATA_DIRECTORY + "break-points-various-incorrect.txt";
    private static final String VARIOUS_CORRECT_AND_INCORRECT_COMBINED = TEST_DATA_DIRECTORY + "break-points-various-correct-and-incorrect-combined.txt";
    private static final String VARIOUS_CORRECT_NOT_CHRONOLOGICALLY = TEST_DATA_DIRECTORY + "break-points-various-correct-not-chronologically.txt";
    private static final String VARIOUS_CORRECT_TIME_DUPLICATED = TEST_DATA_DIRECTORY + "break-points-various-correct-duplicated.txt";
    private static final String COMMENTED_ALL = TEST_DATA_DIRECTORY + "break-points-commented-all.txt";
    private static final String COMMENTED_SOME = TEST_DATA_DIRECTORY + "break-points-commented-some.txt";

    private static List<String> correctDescriptions;
    private static List<String> incorrectDescriptions;
    private static List<String> mixedDescriptions;
    private static final String COMMENTED_DESCRIPTION = "#23:59 - test-message";
    private static final String CORRECT_DESCRIPTION_1 = "23:52";
    private static final String CORRECT_DESCRIPTION_2 = "23:53 - test message";
    private static final String CORRECT_DESCRIPTION_3 = "23:54 - test message aA1!";
    private static final String CORRECT_DESCRIPTION_4 = " 23:55 - test message";
    private static final String CORRECT_DESCRIPTION_5 = "23:56 - test message ";
    private static final String CORRECT_DESCRIPTION_6 = " 23:57 - test message ";
    private static final String CORRECT_DESCRIPTION_7 = "23:58 - 1";
    private static final String CORRECT_DESCRIPTION_8 = "23:59 - 12345678901234567890123456789012345";
    private static final String INCORRECT_DESCRIPTION_01 = "9:59";
    private static final String INCORRECT_DESCRIPTION_02 = "23:9";
    private static final String INCORRECT_DESCRIPTION_03 = "9:9";
    private static final String INCORRECT_DESCRIPTION_04 = "9:59 - test message";
    private static final String INCORRECT_DESCRIPTION_05 = "23:9 - test message";
    private static final String INCORRECT_DESCRIPTION_06 = "9:9 - test message";
    private static final String INCORRECT_DESCRIPTION_07 = "aa:bb";
    private static final String INCORRECT_DESCRIPTION_08 = "aa:bb - test message";
    private static final String INCORRECT_DESCRIPTION_09 = "!!:!!";
    private static final String INCORRECT_DESCRIPTION_00 = "!!:!! - test message";
    private static final String INCORRECT_DESCRIPTION_11 = "23 59";
    private static final String INCORRECT_DESCRIPTION_12 = "23 59 - test message";
    private static final String INCORRECT_DESCRIPTION_13 = "23:59-test message";
    private static final String INCORRECT_DESCRIPTION_14 = "23:59 -test message ";
    private static final String INCORRECT_DESCRIPTION_15 = "23:59- test message ";
    private static final String INCORRECT_DESCRIPTION_16 = "23:59test message";
    private static final String INCORRECT_DESCRIPTION_17 = "23:59 test message";
    private static final String INCORRECT_DESCRIPTION_18 = "23:59 test - message";
    private static final String INCORRECT_DESCRIPTION_19 = "23:59-";
    private static final String INCORRECT_DESCRIPTION_20 = "23:59- ";
    private static final String INCORRECT_DESCRIPTION_21 = "23:59 -";
    private static final String INCORRECT_DESCRIPTION_22 = "23:59 - ";
    private static final String INCORRECT_DESCRIPTION_23 = "23:59 - 123456789012345678901234567890123456";
    private static final String INCORRECT_DESCRIPTION_24 = "test message - 23:59";
    private static final String INCORRECT_DESCRIPTION_25 = "test message-23:59";
    private static final String INCORRECT_DESCRIPTION_26 = "test message -23:59";
    private static final String INCORRECT_DESCRIPTION_27 = "test message- 23:59";
    private static final String INCORRECT_DESCRIPTION_28 = "test message";
    private static final String INCORRECT_DESCRIPTION_29 = " - test message";
    private static final String INCORRECT_DESCRIPTION_30 = "test message -";

    @BeforeAll
    static void beforeAll() {
        correctDescriptions = getCorrectDescriptions();
        incorrectDescriptions = getIncorrectDescriptions();
        mixedDescriptions = getMixedDescriptions();
    }

    @Test @Order(1)
    void fromPathNonexistent() {
        FileNotFoundException e = assertThrows(FileNotFoundException.class, () -> BreakPointFactory.fromPath(NONEXISTENT_BREAK_POINTS_LINE));
        assertEquals("Specified path doesn't match any file.", e.getMessage());
    }

    @Test @Order(1)
    void fromPathEmptyOneLine() {
        LinkedList<BreakPoint> breakPoints = assertDoesNotThrow(() -> BreakPointFactory.fromPath(EMPTY_ONE_LINE));
        assertEquals(0, breakPoints.size());
    }

    @Test @Order(2)
    void fromPathEmptyTwoLines() {
        LinkedList<BreakPoint> breakPoints = assertDoesNotThrow(() -> BreakPointFactory.fromPath(EMPTY_TWO_LINES));
        assertEquals(0, breakPoints.size());
    }

    @Test @Order(3)
    void fromPathEmptyLinesWithSpace() {
        LinkedList<BreakPoint> breakPoints = assertDoesNotThrow(() -> BreakPointFactory.fromPath(EMPTY_TWO_LINES));
        assertEquals(0, breakPoints.size());
    }

    @Test @Order(4)
    void fromPathEmptyFirstLineAndOneCorrectDescription() {
        LinkedList<BreakPoint> breakPoints = assertDoesNotThrow(() -> BreakPointFactory.fromPath(EMPTY_FIRST_LINE_AND_ONE_CORRECT_DESC));
        assertEquals(1, breakPoints.size());

        BreakPoint breakPoint = breakPoints.getFirst();
        String bpTimeValue = breakPoint.getTimeValue();
        String bpMessage = breakPoint.getMessage();

        assertEquals("23:59", bpTimeValue);
        assertEquals("test message", bpMessage);
    }

    @Test @Order(5)
    void fromPathEmptyFirstLineAndOneIncorrectDescription() {
        assertThrows(BreakPointException.class, () -> BreakPointFactory.fromPath(EMPTY_FIRST_LINE_AND_ONE_INCORRECT_DESC));
    }

    @Test @Order(6)
    void fromPathVariousCorrect() {
        LinkedList<BreakPoint> breakPoints = assertDoesNotThrow(() -> BreakPointFactory.fromPath(VARIOUS_CORRECT));
        testVariousCorrect(breakPoints);
    }

    @Test @Order(7)
    void fromPathVariousIncorrect() {
        assertThrows(BreakPointException.class, () -> BreakPointFactory.fromPath(VARIOUS_INCORRECT));
    }

    @Test @Order(8)
    void fromPathVariousCorrectAndIncorrectCombined() {
        assertThrows(BreakPointException.class, () -> BreakPointFactory.fromPath(VARIOUS_CORRECT_AND_INCORRECT_COMBINED));
    }

    @Test @Order(9)
    void fromPathVariousCorrectNotChronologically() {
        LinkedList<BreakPoint> breakPoints = assertDoesNotThrow(() -> BreakPointFactory.fromPath(VARIOUS_CORRECT_NOT_CHRONOLOGICALLY));
        assertEquals(3, breakPoints.size());

        assertEquals("23:57", breakPoints.get(0).getTimeValue());
        assertEquals("23:58", breakPoints.get(1).getTimeValue());
        assertEquals("23:59", breakPoints.get(2).getTimeValue());
    }

    @Test @Order(10)
    void fromPathVariousCorrectTimeDuplicated() {
        BreakPointException e = assertThrows(BreakPointException.class, () -> BreakPointFactory.fromPath(VARIOUS_CORRECT_TIME_DUPLICATED));
        assertEquals(TIME_DUPLICATE_ERR, e.getErrorCode());
        assertEquals("Duplicated break point time: 23:59", e.getMessage());
    }

    @Test @Order(11)
    void fromPathCommentedAll() {
        LinkedList<BreakPoint> breakPoints = assertDoesNotThrow(() -> BreakPointFactory.fromPath(COMMENTED_ALL));
        assertEquals(0, breakPoints.size());
    }

    @Test @Order(12)
    void fromPathCommentedSome() {
        LinkedList<BreakPoint> breakPoints = assertDoesNotThrow(() -> BreakPointFactory.fromPath(COMMENTED_SOME));
        assertEquals(5, breakPoints.size());
    }

    @Test @Order(13)
    void fromDescriptionsCorrect() {
        LinkedList<BreakPoint> breakPoints = assertDoesNotThrow(() -> BreakPointFactory.fromDescriptions(correctDescriptions));
        testVariousCorrect(breakPoints);
    }

    @Test @Order(14)
    void fromDescriptionsIncorrect() {
        assertThrows(BreakPointException.class, () -> BreakPointFactory.fromDescriptions(incorrectDescriptions));
    }

    @Test @Order(15)
    void fromDescriptionsMixed() {
        assertThrows(BreakPointException.class, () -> BreakPointFactory.fromDescriptions(mixedDescriptions));
    }

    @Test @Order(16)
    void fromDescriptionCorrectOnes() {
        BreakPoint breakPoint1 = assertDoesNotThrow(() -> BreakPointFactory.fromDescription(CORRECT_DESCRIPTION_1));
        BreakPoint breakPoint2 = assertDoesNotThrow(() -> BreakPointFactory.fromDescription(CORRECT_DESCRIPTION_2));
        BreakPoint breakPoint3 = assertDoesNotThrow(() -> BreakPointFactory.fromDescription(CORRECT_DESCRIPTION_3));
        BreakPoint breakPoint4 = assertDoesNotThrow(() -> BreakPointFactory.fromDescription(CORRECT_DESCRIPTION_4));
        BreakPoint breakPoint5 = assertDoesNotThrow(() -> BreakPointFactory.fromDescription(CORRECT_DESCRIPTION_5));
        BreakPoint breakPoint6 = assertDoesNotThrow(() -> BreakPointFactory.fromDescription(CORRECT_DESCRIPTION_6));
        BreakPoint breakPoint7 = assertDoesNotThrow(() -> BreakPointFactory.fromDescription(CORRECT_DESCRIPTION_7));
        BreakPoint breakPoint8 = assertDoesNotThrow(() -> BreakPointFactory.fromDescription(CORRECT_DESCRIPTION_8));

        List<BreakPoint> breakPoints = List.of(breakPoint1, breakPoint2, breakPoint3, breakPoint4, breakPoint5, breakPoint6, breakPoint7, breakPoint8);
        testVariousCorrect(breakPoints);
    }

    @Test @Order(17)
    void fromDescriptionIncorrectOnesThrow() {
        incorrectDescriptions.forEach(desc -> {
            BreakPointException e = assertThrows(BreakPointException.class, () -> BreakPointFactory.fromDescription(desc));
            assertEquals(DESCRIPTION_FORMAT_ERR, e.getErrorCode());
            assertEquals("Wrong break point description: " + desc, e.getMessage());
        });
    }

    @Test @Order(18)
    void fromDescriptionCommented() {
        BreakPointException e = assertThrows(BreakPointException.class, () -> BreakPointFactory.fromDescription(COMMENTED_DESCRIPTION));
        assertEquals(DESCRIPTION_FORMAT_ERR, e.getErrorCode());
        assertEquals("Wrong break point description: " + COMMENTED_DESCRIPTION, e.getMessage());
    }

    @Test @Order(19)
    void fromTimeAndNotEmptyMessage() {
        SimpleTime simpleTime = new BPSimpleTime();
        String message = "test message";

        BreakPoint breakPoint = assertDoesNotThrow(() -> BreakPointFactory.fromTimeAndMessage(simpleTime, message));
        String bpTimeValue = breakPoint.getTimeValue();
        String bpMessage = breakPoint.getMessage();
        String simpleTimeValue = simpleTime.getTime();

        assertNotNull(breakPoint);
        assertEquals(simpleTimeValue, bpTimeValue);
        assertEquals(message, bpMessage);
    }

    @Test @Order(20)
    void fromTimeAndEmptyMessage() {
        SimpleTime simpleTime = new BPSimpleTime();
        String message = "";

        BreakPoint breakPoint = assertDoesNotThrow(() -> BreakPointFactory.fromTimeAndMessage(simpleTime, message));
        String bpTimeValue = breakPoint.getTimeValue();
        String bpMessage = breakPoint.getMessage();
        String simpleTimeValue = simpleTime.getTime();

        assertNotNull(breakPoint);
        assertEquals(simpleTimeValue, bpTimeValue);
        assertEquals(message, bpMessage);
    }

    @Test @Order(21)
    void fromTimeAndNullMessage() {
        SimpleTime simpleTime = new BPSimpleTime();
        String message = null;

        assertThrows(InvalidParameterException.class, () -> BreakPointFactory.fromTimeAndMessage(simpleTime, message));
    }

    @Test @Order(22)
    void fromNullTimeAndNotNullMessage() {
        SimpleTime simpleTime = null;
        String message = "";

        assertThrows(InvalidParameterException.class, () -> BreakPointFactory.fromTimeAndMessage(simpleTime, message));
    }

    @Test @Order(23)
    void fromNullTimeAndMessage() {
        SimpleTime simpleTime = null;
        String message = null;

        assertThrows(InvalidParameterException.class, () -> BreakPointFactory.fromTimeAndMessage(simpleTime, message));
    }

    private static List<String> getCorrectDescriptions() {
        return List.of(
                CORRECT_DESCRIPTION_1, CORRECT_DESCRIPTION_2, CORRECT_DESCRIPTION_3, CORRECT_DESCRIPTION_4,
                CORRECT_DESCRIPTION_5, CORRECT_DESCRIPTION_6, CORRECT_DESCRIPTION_7, CORRECT_DESCRIPTION_8
        );
    }

    private static List<String> getIncorrectDescriptions() {
        return List.of(
                INCORRECT_DESCRIPTION_01, INCORRECT_DESCRIPTION_02, INCORRECT_DESCRIPTION_03,
                INCORRECT_DESCRIPTION_04, INCORRECT_DESCRIPTION_05, INCORRECT_DESCRIPTION_06,
                INCORRECT_DESCRIPTION_07, INCORRECT_DESCRIPTION_08, INCORRECT_DESCRIPTION_09,
                INCORRECT_DESCRIPTION_00, INCORRECT_DESCRIPTION_11, INCORRECT_DESCRIPTION_12,
                INCORRECT_DESCRIPTION_13, INCORRECT_DESCRIPTION_14, INCORRECT_DESCRIPTION_15,
                INCORRECT_DESCRIPTION_16, INCORRECT_DESCRIPTION_17, INCORRECT_DESCRIPTION_18,
                INCORRECT_DESCRIPTION_19, INCORRECT_DESCRIPTION_20, INCORRECT_DESCRIPTION_21,
                INCORRECT_DESCRIPTION_22, INCORRECT_DESCRIPTION_23, INCORRECT_DESCRIPTION_24,
                INCORRECT_DESCRIPTION_25, INCORRECT_DESCRIPTION_26, INCORRECT_DESCRIPTION_27,
                INCORRECT_DESCRIPTION_28, INCORRECT_DESCRIPTION_29, INCORRECT_DESCRIPTION_30
        );
    }

    private static List<String> getMixedDescriptions() {
        return Stream.of(correctDescriptions, incorrectDescriptions, List.of(COMMENTED_DESCRIPTION))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private void testVariousCorrect(List<BreakPoint> breakPoints) {
        assertEquals(8, breakPoints.size());
        assertEquals("23:52", breakPoints.get(0).getTimeValue());
        assertEquals("23:53", breakPoints.get(1).getTimeValue());
        assertEquals("23:54", breakPoints.get(2).getTimeValue());
        assertEquals("23:55", breakPoints.get(3).getTimeValue());
        assertEquals("23:56", breakPoints.get(4).getTimeValue());
        assertEquals("23:57", breakPoints.get(5).getTimeValue());
        assertEquals("23:58", breakPoints.get(6).getTimeValue());
        assertEquals("23:59", breakPoints.get(7).getTimeValue());
        assertEquals("", breakPoints.get(0).getMessage());
        assertEquals("test message", breakPoints.get(1).getMessage());
        assertEquals("test message aA1!", breakPoints.get(2).getMessage());
        assertEquals("test message", breakPoints.get(3).getMessage());
        assertEquals("test message", breakPoints.get(4).getMessage());
        assertEquals("test message", breakPoints.get(5).getMessage());
        assertEquals("1", breakPoints.get(6).getMessage());
        assertEquals("12345678901234567890123456789012345", breakPoints.get(7).getMessage());
    }
}