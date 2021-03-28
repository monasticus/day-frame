package com.arch.dayframe.model.bp;

import com.arch.dayframe.model.bp.BreakPointException.ErrorCode;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @Test
    void testMoveForwardReturnsNotNull() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 52), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
        BreakPoint breakPoint = breakPoints.moveForward();
        BreakPoint firstBreakPoint = breakPoints.getBreakPointsList().get(0);

        assertNotNull(breakPoint);
        assertSame(firstBreakPoint, breakPoint);
    }

    @Test
    void testMoveForwardReturnsDifferent() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 52), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
        List<BreakPoint> moveForwardBreakPointsList = getBPListUsingMapper(breakPoints, i -> breakPoints.moveForward());
        HashSet<BreakPoint> moveForwardBreakPointSet = new HashSet<>(moveForwardBreakPointsList);

        assertEquals(moveForwardBreakPointsList.size(), moveForwardBreakPointSet.size());
    }

    @Test
    void testMoveForwardReturnsNull() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 52), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
        goToEndOfBreakPoints(breakPoints);
        BreakPoint breakPoint = assertDoesNotThrow(breakPoints::moveForward);

        assertNull(breakPoint);
    }

    @Test
    void testMoveBackReturnsNotNull() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 52), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
        breakPoints.moveForward();

        BreakPoint breakPoint = breakPoints.moveBack();
        BreakPoint firstBreakPoint = breakPoints.getBreakPointsList().get(0);

        assertNotNull(breakPoint);
        assertSame(firstBreakPoint, breakPoint);
    }

    @Test
    void testMoveBackReturnsNull() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 52), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
        BreakPoint breakPoint = assertDoesNotThrow(breakPoints::moveBack);

        assertNull(breakPoint);
    }

    @Test
    void testGetNextReturnsNotNull() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 52), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
        BreakPoint breakPoint = breakPoints.getNext();

        assertNotNull(breakPoint);
    }

    @Test
    void testGetNextReturnsNull() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 52), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
        goToEndOfBreakPoints(breakPoints);

        BreakPoint breakPoint = assertDoesNotThrow(breakPoints::getNext);

        assertNull(breakPoint);
    }

    @Test
    void testGetNextReturnsEqualsNotSame() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 52), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
        List<BreakPoint> getNextBreakPointsList = getBPListUsingMapper(breakPoints, i -> breakPoints.getNext());
        HashSet<BreakPoint> getNextBreakPointSet = new HashSet<>(getNextBreakPointsList);

        assertEquals(1, getNextBreakPointSet.size());
        compareAllListComponents(getNextBreakPointsList, Assertions::assertNotSame);
    }

    @Test
    void testGetPreviousReturnsNotNull() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 52), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
        breakPoints.moveForward();
        BreakPoint breakPoint = breakPoints.getPrevious();

        assertNotNull(breakPoint);
    }

    @Test
    void testGetPreviousReturnsNull() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 52), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
        BreakPoint breakPoint = assertDoesNotThrow(breakPoints::getPrevious);

        assertNull(breakPoint);
    }

    @Test
    void testGetPreviousReturnsEqualsNotSame() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 52), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
        breakPoints.moveForward();
        List<BreakPoint> getPreviousBreakPointsList = getBPListUsingMapper(breakPoints, i -> breakPoints.getPrevious());
        HashSet<BreakPoint> getPreviousBreakPointSet = new HashSet<>(getPreviousBreakPointsList);

        assertEquals(1, getPreviousBreakPointSet.size());
        compareAllListComponents(getPreviousBreakPointsList, Assertions::assertNotSame);
    }

    private void failTestInTimeFrame(SimpleTime timeStart, SimpleTime timeEnd) {
        timeEnd.add(1);
        if (!timeStart.isFuture())
            fail("Wait until " + timeEnd.getTime() + " with the test execution");
    }

    private List<BreakPoint> getBPListUsingMapper(BreakPoints breakPoints, IntFunction<BreakPoint> mapper) {
        return IntStream.rangeClosed(1, breakPoints.getSize())
                .mapToObj(mapper)
                .collect(Collectors.toList());
    }

    private void goToEndOfBreakPoints(BreakPoints breakPoints) {
        for (int i = 0; i < breakPoints.getSize(); i++)
            breakPoints.moveForward();
    }

    private void compareAllListComponents(List<BreakPoint> breakPointList, BiConsumer<Object, Object> assertion) {
        for (int i = 0; i < breakPointList.size() - 1; i++) {
            BreakPoint bpLeftSide = breakPointList.get(i);

            for (int j = i + 1; j < breakPointList.size(); j++) {
                BreakPoint bpRightSide = breakPointList.get(j);
                assertion.accept(bpLeftSide, bpRightSide);
            }
        }
    }
}
