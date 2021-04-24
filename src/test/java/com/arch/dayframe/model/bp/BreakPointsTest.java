package com.arch.dayframe.model.bp;

import com.arch.dayframe.model.bp.BreakPointException.ErrorCode;
import com.arch.dayframe.model.time.BPSimpleTime;
import com.arch.dayframe.model.time.SimpleTime;
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
    private static final String FUTURE_WITHOUT_NEXT_1 = TEST_DATA_DIRECTORY + "break-points-future-without-next-1.txt";
    private static final String FUTURE_WITHOUT_NEXT_2 = TEST_DATA_DIRECTORY + "break-points-future-without-next-2.txt";
    private static final String FUTURE_WITHOUT_NEXT_3 = TEST_DATA_DIRECTORY + "break-points-future-without-next-3.txt";
    private static final String FUTURE_WITHOUT_NEXT_4 = TEST_DATA_DIRECTORY + "break-points-future-without-next-4.txt";
    private static final String POSTPONE_RECENT_1 = TEST_DATA_DIRECTORY + "break-points-postpone-recent-1.txt";
    private static final String POSTPONE_RECENT_2 = TEST_DATA_DIRECTORY + "break-points-postpone-recent-2.txt";
    private static final String POSTPONE_RECENT_3 = TEST_DATA_DIRECTORY + "break-points-postpone-recent-3.txt";
    private static final String POSTPONE_RECENT_4 = TEST_DATA_DIRECTORY + "break-points-postpone-recent-4.txt";

    private static List<BreakPoint> allFutureBreakPoints;
    private static List<BreakPoint> somePastBreakPoints;

    @BeforeAll
    static void beforeAll() throws BreakPointException {
        allFutureBreakPoints = getAllFutureBreakPoints();
        somePastBreakPoints = getSomePastBreakPoints();
    }

    @Test @Order(1)
    @DisplayName("01. new BreakPoints() - incorrect break point description")
    void createBreakPointsIncorrect() {
        BreakPointException e = assertThrows(BreakPointException.class, () -> new BreakPoints(VARIOUS_CORRECT_AND_INCORRECT_COMBINED));
        assertEquals(ErrorCode.DESCRIPTION_FORMAT_ERR, e.getErrorCode());
        assertEquals("Wrong break point description: 9:59", e.getMessage());
    }

    @Test @Order(2)
    @DisplayName("02. new BreakPoints() - duplicated break point")
    void createBreakPointsDuplicates() {
        BreakPointException e = assertThrows(BreakPointException.class, () -> new BreakPoints(VARIOUS_CORRECT_TIME_DUPLICATED));
        assertEquals(ErrorCode.TIME_DUPLICATE_ERR, e.getErrorCode());
        assertEquals("Duplicated break point time: 23:59", e.getMessage());
    }

    @Test @Order(3)
    @DisplayName("03. new BreakPoints() - correct (all future)")
    void createBreakPointsCorrect() {
        assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
    }

    @Test @Order(4)
    @DisplayName("04. new BreakPoints() - correct (some past)")
    void createBreakPointsSomePast() {
        assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT_SOME_PAST));
    }

    @Test @Order(5)
    @DisplayName("05. getSize() - all future")
    void getSizeAllFuture() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 52), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
        int bpSize = breakPoints.getSize();

        assertEquals(8, bpSize);
    }

    @Test @Order(6)
    @DisplayName("06. getSize() - some past")
    void getSizeSomePast() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 56), new BPSimpleTime(0, 3));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT_SOME_PAST));
        int bpSize = breakPoints.getSize();

        assertEquals(4, bpSize);
    }

    @Test @Order(7)
    @DisplayName("07. getBreakPointsList() - all future")
    void getBreakPointsListAllFuture() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 52), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
        List<BreakPoint> breakPointsList = breakPoints.getBreakPointsList();
        int bpSize = breakPoints.getSize();
        int bpListSize = breakPointsList.size();

        assertNotNull(breakPointsList);
        assertEquals(bpSize, bpListSize);
        assertEquals(allFutureBreakPoints, breakPointsList);
    }

    @Test @Order(8)
    @DisplayName("08. getBreakPointsList() - some past")
    void getBreakPointsListSomePast() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 56), new BPSimpleTime(0, 3));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT_SOME_PAST));
        List<BreakPoint> breakPointsList = breakPoints.getBreakPointsList();
        int bpSize = breakPoints.getSize();
        int bpListSize = breakPointsList.size();

        assertNotNull(breakPointsList);
        assertEquals(bpSize, bpListSize);
        assertEquals(somePastBreakPoints, breakPointsList);
    }

    @Test @Order(9)
    @DisplayName("09. getBreakPointsList() - encapsulation test")
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

    @Test @Order(10)
    @DisplayName("10. getBreakPointsList() - deep encapsulation test")
    void testBreakPointsListDeepEncapsulation() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 52), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
        List<BreakPoint> breakPointsList1 = breakPoints.getBreakPointsList();
        List<BreakPoint> breakPointsList2 = breakPoints.getBreakPointsList();
        boolean anySame = breakPointsList1.stream().anyMatch(bp1 -> breakPointsList2.stream().anyMatch(bp2 -> bp1 == bp2));

        assertFalse(anySame);
    }

    @Test @Order(11)
    @DisplayName("11. moveForward() - has next")
    void testMoveForwardWhenHasNext() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 52), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
        BreakPoint breakPoint = breakPoints.moveForward();
        BreakPoint firstBreakPoint = breakPoints.getBreakPointsList().get(0);

        assertNotNull(breakPoint);
        assertEquals(firstBreakPoint, breakPoint);
    }

    @Test @Order(12)
    @DisplayName("12. moveForward() - has not next")
    void testMoveForwardWhenHasNotNext() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 52), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
        goToEndOfBreakPoints(breakPoints);
        BreakPoint breakPoint = assertDoesNotThrow(breakPoints::moveForward);

        assertNull(breakPoint);
    }

    @Test @Order(13)
    @DisplayName("13. moveForward() - moves forward and returns different break points")
    void testMoveForwardReturnsDifferent() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 52), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
        List<BreakPoint> moveForwardBreakPointsList = getListByFunction(breakPoints, i -> breakPoints.moveForward());
        HashSet<BreakPoint> moveForwardBreakPointSet = new HashSet<>(moveForwardBreakPointsList);

        assertEquals(moveForwardBreakPointsList.size(), moveForwardBreakPointSet.size());
    }

    @Test @Order(14)
    @DisplayName("14. moveBackward() - has previous")
    void testMoveBackwardWhenHasPrevious() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 52), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
        breakPoints.moveForward();

        BreakPoint breakPoint = breakPoints.moveBackward();
        BreakPoint firstBreakPoint = breakPoints.getBreakPointsList().get(0);

        assertNotNull(breakPoint);
        assertEquals(firstBreakPoint, breakPoint);
    }

    @Test @Order(15)
    @DisplayName("15. moveBackward() - has not previous")
    void testMoveBackwardWhenHasNotPrevious() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 52), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
        BreakPoint breakPoint = assertDoesNotThrow(breakPoints::moveBackward);

        assertNull(breakPoint);
    }

    @Test @Order(16)
    @DisplayName("16. moveBackward() - moves backward and returns different break points")
    void testMoveBackwardReturnsDifferent() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 52), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
        goToEndOfBreakPoints(breakPoints);
        List<BreakPoint> moveBackwardBreakPointsList = getListByFunction(breakPoints, i -> breakPoints.moveBackward());
        HashSet<BreakPoint> moveBackwardBreakPointSet = new HashSet<>(moveBackwardBreakPointsList);

        assertEquals(moveBackwardBreakPointsList.size(), moveBackwardBreakPointSet.size());
    }

    @Test @Order(17)
    @DisplayName("17. getNext() - has next")
    void testGetNextWhenHasNext() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 52), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
        BreakPoint breakPoint = breakPoints.getNext();

        assertNotNull(breakPoint);
    }

    @Test @Order(18)
    @DisplayName("18. getNext() - has not next")
    void testGetNextWhenHasNotNext() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 52), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
        goToEndOfBreakPoints(breakPoints);

        BreakPoint breakPoint = assertDoesNotThrow(breakPoints::getNext);

        assertNull(breakPoint);
    }

    @Test @Order(19)
    @DisplayName("19. getNext() - does not move forward and returns clone of the same break point")
    void testGetNextReturnsEquals() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 52), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
        List<BreakPoint> getNextBreakPointsList = getListByFunction(breakPoints, i -> breakPoints.getNext());
        HashSet<BreakPoint> getNextBreakPointSet = new HashSet<>(getNextBreakPointsList);

        assertEquals(1, getNextBreakPointSet.size());
        compareAllListComponents(getNextBreakPointsList, Assertions::assertNotSame);
    }

    @Test @Order(20)
    @DisplayName("20. getPrevious() - has previous")
    void testGetPreviousWhenHasPrevious() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 52), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
        breakPoints.moveForward();
        BreakPoint breakPoint = breakPoints.getPrevious();

        assertNotNull(breakPoint);
    }

    @Test @Order(21)
    @DisplayName("21. getPrevious() - has not previous")
    void testGetPreviousWhenHasNotPrevious() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 52), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
        BreakPoint breakPoint = assertDoesNotThrow(breakPoints::getPrevious);

        assertNull(breakPoint);
    }

    @Test @Order(22)
    @DisplayName("22. getPrevious() - does not move backward and returns clone of the same break point")
    void testGetPreviousReturnsEqualsNotSame() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 52), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(VARIOUS_CORRECT));
        breakPoints.moveForward();
        List<BreakPoint> getPreviousBreakPointsList = getListByFunction(breakPoints, i -> breakPoints.getPrevious());
        HashSet<BreakPoint> getPreviousBreakPointSet = new HashSet<>(getPreviousBreakPointsList);

        assertEquals(1, getPreviousBreakPointSet.size());
        compareAllListComponents(getPreviousBreakPointsList, Assertions::assertNotSame);
    }

    @Test @Order(23)
    @DisplayName("23. getFutureWithoutNext() - empty list")
    void getFutureWithoutNextEmpty() {
        testGetFutureWithoutNext(FUTURE_WITHOUT_NEXT_1);
    }

    @Test @Order(24)
    @DisplayName("24. getFutureWithoutNext() - next break point is the last one")
    void getFutureWithoutNextNextIsLast() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 59), new BPSimpleTime(23, 59));

        testGetFutureWithoutNext(FUTURE_WITHOUT_NEXT_2);
    }

    @Test @Order(25)
    @DisplayName("25. getFutureWithoutNext() - one break point besides the next one")
    void getFutureWithoutNextOneRemains() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 58), new BPSimpleTime(23, 59));

        testGetFutureWithoutNext(FUTURE_WITHOUT_NEXT_3);
    }

    @Test @Order(26)
    @DisplayName("26. getFutureWithoutNext() - two break points besides the next one")
    void getFutureWithoutNextTwoRemain() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 57), new BPSimpleTime(23, 59));

        testGetFutureWithoutNext(FUTURE_WITHOUT_NEXT_4);
    }

    @Test @Order(27)
    @DisplayName("27. postponeRecent() - no recent")
    void testPostponeRecentNoRecent() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 50), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(POSTPONE_RECENT_1));
        List<BreakPoint> listBefore = breakPoints.getBreakPointsList();

        breakPoints.postponeRecent(3);
        List<BreakPoint> listAfter = breakPoints.getBreakPointsList();

        boolean allSame = listAfter.containsAll(listBefore);
        boolean allEquals = listBefore.stream().allMatch(bpBefore -> listAfter.stream().anyMatch(bpBefore::equals));

        assertTrue(allSame);
        assertTrue(allEquals);
        assertEquals(listBefore.size(), listAfter.size());
    }

    @Test @Order(28)
    @DisplayName("28. postponeRecent() - postponement with no affect on order")
    void testPostponeRecentSameOrder() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 50), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(POSTPONE_RECENT_1));
        List<BreakPoint> listBefore = breakPoints.getBreakPointsList();

        breakPoints.moveForward();
        breakPoints.postponeRecent(3);
        List<BreakPoint> listAfter = breakPoints.getBreakPointsList();

        boolean allEquals = listBefore.stream().allMatch(bpBefore -> listAfter.stream().anyMatch(bpBefore::equals));
        int postponedIndex = listAfter.stream().filter(BreakPoint::isPostponed).map(listAfter::indexOf).findFirst().get();

        assertFalse(allEquals);
        assertEquals(0, postponedIndex);
        assertNotEquals(listAfter.size()-1, postponedIndex);

        assertTimeValues(listBefore, "23:50", "23:55", "23:59");
        assertTimeValues(listAfter, "23:53", "23:55", "23:59");
    }

    @Test @Order(29)
    @DisplayName("29. postponeRecent() - postponement with affect on order (1 skipped)")
    void testPostponeRecentSkipOne() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 50), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(POSTPONE_RECENT_2));
        List<BreakPoint> listBefore = breakPoints.getBreakPointsList();

        breakPoints.moveForward();
        breakPoints.postponeRecent(3);
        List<BreakPoint> listAfter = breakPoints.getBreakPointsList();
        assertEquals(listBefore.size(), listAfter.size());

        boolean allEquals = listBefore.stream().allMatch(bpBefore -> listAfter.stream().anyMatch(bpBefore::equals));
        int postponedIndex = listAfter.stream().filter(BreakPoint::isPostponed).map(listAfter::indexOf).findFirst().get();

        assertFalse(allEquals);
        assertEquals(1, postponedIndex);
        assertNotEquals(listAfter.size()-1, postponedIndex);
        assertEquals(listBefore.size(), listAfter.size());

        assertTimeValues(listBefore, "23:50", "23:51", "23:59");
        assertTimeValues(listAfter, "23:51", "23:53", "23:59");
    }

    @Test @Order(30)
    @DisplayName("30. postponeRecent() - postponement with affect on order (2 skipped)")
    void testPostponeRecentSkipTwo() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 50), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(POSTPONE_RECENT_3));
        List<BreakPoint> listBefore = breakPoints.getBreakPointsList();

        breakPoints.moveForward();
        breakPoints.postponeRecent(3);
        List<BreakPoint> listAfter = breakPoints.getBreakPointsList();

        boolean allEquals = listBefore.stream().allMatch(bpBefore -> listAfter.stream().anyMatch(bpBefore::equals));
        int postponedIndex = listAfter.stream().filter(BreakPoint::isPostponed).map(listAfter::indexOf).findFirst().get();

        assertFalse(allEquals);
        assertEquals(2, postponedIndex);
        assertNotEquals(listAfter.size()-1, postponedIndex);
        assertEquals(listBefore.size(), listAfter.size());

        assertTimeValues(listBefore, "23:50", "23:51", "23:52", "23:59");
        assertTimeValues(listAfter, "23:51", "23:52", "23:53", "23:59");
    }

    @Test @Order(31)
    @DisplayName("31. postponeRecent() - postponement with affect on order (all skipped)")
    void testPostponeRecentSkipAll() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 50), new BPSimpleTime(23, 53));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(POSTPONE_RECENT_4));
        List<BreakPoint> listBefore = breakPoints.getBreakPointsList();

        breakPoints.moveForward();
        breakPoints.postponeRecent(3);
        List<BreakPoint> listAfter = breakPoints.getBreakPointsList();

        boolean allEquals = listBefore.stream().allMatch(bpBefore -> listAfter.stream().anyMatch(bpBefore::equals));
        int postponedIndex = listAfter.stream().filter(BreakPoint::isPostponed).map(listAfter::indexOf).findFirst().get();

        assertFalse(allEquals);
        assertEquals(2, postponedIndex);
        assertEquals(listAfter.size()-1, postponedIndex);
        assertEquals(listBefore.size(), listAfter.size());

        assertTimeValues(listBefore, "23:50", "23:51", "23:52");
        assertTimeValues(listAfter, "23:51", "23:52", "23:53");
    }

    @Test @Order(32)
    @DisplayName("32. postponeRecent() - postponement in the middle of a list")
    void testPostponeRecentInMiddle() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 50), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(POSTPONE_RECENT_3));
        List<BreakPoint> listBefore = breakPoints.getBreakPointsList();

        breakPoints.moveForward();
        breakPoints.moveForward();
        breakPoints.postponeRecent(3);
        List<BreakPoint> listAfter = breakPoints.getBreakPointsList();

        boolean allEquals = listBefore.stream().allMatch(bpBefore -> listAfter.stream().anyMatch(bpBefore::equals));
        int postponedIndex = listAfter.stream().filter(BreakPoint::isPostponed).map(listAfter::indexOf).findFirst().get();

        assertFalse(allEquals);
        assertEquals(2, postponedIndex);
        assertNotEquals(listAfter.size()-1, postponedIndex);
        assertEquals(listBefore.size(), listAfter.size());

        assertTimeValues(listBefore, "23:50", "23:51", "23:52", "23:59");
        assertTimeValues(listAfter, "23:50", "23:52", "23:54", "23:59");
    }

    @Test @Order(33)
    @DisplayName("33. postponeRecent() - removes duplicates when postponement causes duplication")
    void testPostponeWithDuplicateAfterChange() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 50), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(POSTPONE_RECENT_3));
        List<BreakPoint> listBefore = breakPoints.getBreakPointsList();

        breakPoints.moveForward();
        breakPoints.postponeRecent(2);
        List<BreakPoint> listAfter = breakPoints.getBreakPointsList();

        boolean allEquals = listBefore.stream().allMatch(bpBefore -> listAfter.stream().anyMatch(bpBefore::equals));
        int postponedIndex = listAfter.stream().filter(BreakPoint::isPostponed).map(listAfter::indexOf).findFirst().get();

        assertFalse(allEquals);
        assertEquals(1, postponedIndex);
        assertNotEquals(listAfter.size()-1, postponedIndex);
        assertNotEquals(listBefore.size(), listAfter.size());

        assertTimeValues(listBefore, "23:50", "23:51", "23:52", "23:59");
        assertTimeValues(listAfter, "23:51", "23:52", "23:59");
    }

    @Test @Order(34)
    @DisplayName("34. postponeRecent() - postponement does not affect on iterating methods [moveForward(), moveBackward()] - no past break points")
    void testPostponeRefreshedIteratorNoPastBreakPoints() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 50), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(POSTPONE_RECENT_3));
        breakPoints.moveForward();
        breakPoints.postponeRecent(3);
        BreakPoint nextBreakPointAfterPostponement = breakPoints.moveForward();

        assertEquals("23:51", nextBreakPointAfterPostponement.getTimeValue());
    }

    @Test @Order(35)
    @DisplayName("35. postponeRecent() - postponement does not affect on iterating methods [moveForward(), moveBackward()] - some past break points")
    void testPostponeRefreshedIteratorSomePastBreakPoints() throws BreakPointException {
        failTestInTimeFrame(new BPSimpleTime(23, 50), new BPSimpleTime(23, 59));

        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(POSTPONE_RECENT_3));
        breakPoints.moveForward();
        breakPoints.moveForward();
        breakPoints.moveForward();
        breakPoints.moveForward();
        breakPoints.postponeRecent(2);
        BreakPoint previousBreakPointAfterPostponement = breakPoints.getPrevious();
        BreakPoint nextBreakPointAfterPostponement = breakPoints.moveForward();

        assertEquals("00:01", previousBreakPointAfterPostponement.getTimeValue());
        assertEquals("23:50", nextBreakPointAfterPostponement.getTimeValue());
    }

    private static List<BreakPoint> getAllFutureBreakPoints() throws BreakPointException {
        return List.of(
                new BreakPoint(new BPSimpleTime(23, 52), ""),
                new BreakPoint(new BPSimpleTime(23, 53), "test message"),
                new BreakPoint(new BPSimpleTime(23, 54), "test message aA1!"),
                new BreakPoint(new BPSimpleTime(23, 55), "test message"),
                new BreakPoint(new BPSimpleTime(23, 56), "test message"),
                new BreakPoint(new BPSimpleTime(23, 57), "test message"),
                new BreakPoint(new BPSimpleTime(23, 58), "1"),
                new BreakPoint(new BPSimpleTime(23, 59), "12345678901234567890123456789012345")
        );
    }

    private static List<BreakPoint> getSomePastBreakPoints() throws BreakPointException {
        return List.of(
                new BreakPoint(new BPSimpleTime(23, 56), "test message"),
                new BreakPoint(new BPSimpleTime(23, 57), "test message aA1!"),
                new BreakPoint(new BPSimpleTime(23, 58), "test message"),
                new BreakPoint(new BPSimpleTime(23, 59), "1")
        );
    }

    private void failTestInTimeFrame(SimpleTime timeStart, SimpleTime timeEnd) {
        timeEnd.add(1);
        if (!timeStart.isFuture())
            fail("Wait until " + timeEnd.getTime() + " with the test execution");
    }

    private List<BreakPoint> getListByFunction(BreakPoints breakPoints, IntFunction<BreakPoint> mapper) {
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

    private void testGetFutureWithoutNext(String path){
        BreakPoints breakPoints = assertDoesNotThrow(() -> new BreakPoints(path));
        List<BreakPoint> futureWithoutNext = breakPoints.getFutureWithoutNext();
        int allSize = breakPoints.getSize();
        int futureWithoutNextSize = futureWithoutNext.size();
        boolean isSubList = breakPoints.getBreakPointsList().containsAll(futureWithoutNext);

        int expectedFutureSize = allSize == 0 ? allSize : allSize-1;

        assertEquals(expectedFutureSize, futureWithoutNextSize);
        assertTrue(isSubList);
    }

    private void assertTimeValues(List<BreakPoint> list, String... timeValues) {
        for (int i = 0; i < list.size(); i++) {
            assertEquals(timeValues[i], list.get(i).getTimeValue());
        }
    }
}
