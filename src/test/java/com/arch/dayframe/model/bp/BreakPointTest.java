package com.arch.dayframe.model.bp;

import org.junit.jupiter.api.*;

import java.security.InvalidParameterException;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@Tag("BreakPoint")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BreakPointTest {

    private static SimpleTime defaultBPTime;
    private static final String BP_MESSAGE = "It's time for a break";

    @BeforeEach
    void setUp() throws BreakPointException {
        defaultBPTime = new BPSimpleTime(9, 30);
    }

    @Test @Order(1)
    void testCreateBreakPoint() {
        BreakPoint breakPoint = assertDoesNotThrow(() -> new BreakPoint(defaultBPTime, BP_MESSAGE));

        String bpTime = breakPoint.getTimeValue();
        String bpMessage = breakPoint.getMessage();
        String expectedTime = defaultBPTime.getTime();

        assertNotNull(breakPoint);
        assertEquals(expectedTime, bpTime);
        assertEquals(BP_MESSAGE, bpMessage);
    }

    @Test @Order(2)
    void testCreateBreakPointWithNullSimpleTime() {
        assertThrows(InvalidParameterException.class, () -> new BreakPoint(null, BP_MESSAGE));
    }

    @Test @Order(3)
    void testCreateBreakPointWithNullMessage() {
        assertThrows(InvalidParameterException.class, () -> new BreakPoint(defaultBPTime, null));
    }

    @Test @Order(4)
    void testCreateBreakPointWithNullSimpleTimeAndMessage() {
        assertThrows(InvalidParameterException.class, () -> new BreakPoint(null, null));
    }

    @Test @Order(5)
    void testStaticGetTimeValue() {
        BreakPoint breakPoint = new BreakPoint(defaultBPTime, BP_MESSAGE);
        String bpTimeValue = BreakPoint.getTimeValue(breakPoint);
        assertEquals("09:30", bpTimeValue);
    }

    @Test @Order(6)
    void testGetTimeValue() {
        BreakPoint breakPoint = new BreakPoint(defaultBPTime, BP_MESSAGE);
        String bpTimeValue = breakPoint.getTimeValue();
        assertEquals("09:30", bpTimeValue);
    }

    @Test @Order(7)
    void testGetMessage() {
        BreakPoint breakPoint = new BreakPoint(defaultBPTime, BP_MESSAGE);
        String bpMessage = breakPoint.getMessage();
        assertEquals("It's time for a break", bpMessage);
    }

    @Test @Order(8)
    void testIsPostponedByDefault() {
        BreakPoint breakPoint = new BreakPoint(defaultBPTime, BP_MESSAGE);
        boolean isPostponed = breakPoint.isPostponed();
        assertFalse(isPostponed);
    }

    @Test @Order(9)
    void testIsPostponed() throws BreakPointException {
        BreakPoint breakPoint = new BreakPoint(defaultBPTime, BP_MESSAGE);
        boolean isPostponedBefore = breakPoint.isPostponed();

        breakPoint.postpone(1);
        boolean isPostponedAfter = breakPoint.isPostponed();

        assertFalse(isPostponedBefore);
        assertTrue(isPostponedAfter);
    }

    @Test @Order(10)
    void testPostponeForOneMinute() throws BreakPointException {
        BreakPoint breakPoint = new BreakPoint(defaultBPTime, BP_MESSAGE);
        boolean isPostponedBefore = breakPoint.isPostponed();
        String bpTimeValueBefore = breakPoint.getTimeValue();

        breakPoint.postpone(1);

        boolean isPostponedAfter = breakPoint.isPostponed();
        String bpTimeValueAfter = breakPoint.getTimeValue();

        assertFalse(isPostponedBefore);
        assertEquals("09:30", bpTimeValueBefore);
        assertTrue(isPostponedAfter);
        assertEquals("09:31", bpTimeValueAfter);
    }

    @Test @Order(11)
    void testPostponeForTwentyThreeHours() throws BreakPointException {
        BreakPoint breakPoint = new BreakPoint(defaultBPTime, BP_MESSAGE);
        boolean isPostponedBefore = breakPoint.isPostponed();
        String bpTimeValueBefore = breakPoint.getTimeValue();

        breakPoint.postpone(23 * 60);

        boolean isPostponedAfter = breakPoint.isPostponed();
        String bpTimeValueAfter = breakPoint.getTimeValue();

        assertFalse(isPostponedBefore);
        assertEquals("09:30", bpTimeValueBefore);
        assertTrue(isPostponedAfter);
        assertEquals("08:30", bpTimeValueAfter);
    }

    @Test @Order(12)
    void testPostponeForZeroMinutes() {
        BreakPoint breakPoint = new BreakPoint(defaultBPTime, BP_MESSAGE);

        BreakPointException e = assertThrows(BreakPointException.class, () -> breakPoint.postpone(0));
        assertEquals(BreakPointException.ErrorCode.ZERO_POSTPONE_ERR, e.getErrorCode());
        assertEquals("Wrong postponement value: 0", e.getMessage());
    }

    @Test @Order(13)
    void testPostponeForNegativeMinutes() {
        BreakPoint breakPoint = new BreakPoint(defaultBPTime, BP_MESSAGE);

        BreakPointException e = assertThrows(BreakPointException.class, () -> breakPoint.postpone(-1));
        assertEquals(BreakPointException.ErrorCode.NEGATIVE_POSTPONE_ERR, e.getErrorCode());
        assertEquals("Wrong postponement value: -1", e.getMessage());
    }

    @Test @Order(14)
    void testPostponeAfterZeroError() {
        BreakPoint breakPoint = new BreakPoint(defaultBPTime, BP_MESSAGE);
        boolean isPostponedBefore = breakPoint.isPostponed();
        String bpTimeValueBefore = breakPoint.getTimeValue();

        BreakPointException e = assertThrows(BreakPointException.class, () -> breakPoint.postpone(0));
        assertEquals(BreakPointException.ErrorCode.ZERO_POSTPONE_ERR, e.getErrorCode());
        assertEquals("Wrong postponement value: 0", e.getMessage());

        assertDoesNotThrow(() -> breakPoint.postpone(1));

        boolean isPostponedAfter = breakPoint.isPostponed();
        String bpTimeValueAfter = breakPoint.getTimeValue();

        assertFalse(isPostponedBefore);
        assertEquals("09:30", bpTimeValueBefore);
        assertTrue(isPostponedAfter);
        assertEquals("09:31", bpTimeValueAfter);
    }

    @Test @Order(15)
    void testPostponeAfterNegativeError() {
        BreakPoint breakPoint = new BreakPoint(defaultBPTime, BP_MESSAGE);
        boolean isPostponedBefore = breakPoint.isPostponed();
        String bpTimeValueBefore = breakPoint.getTimeValue();

        BreakPointException e = assertThrows(BreakPointException.class, () -> breakPoint.postpone(-1));
        assertEquals(BreakPointException.ErrorCode.NEGATIVE_POSTPONE_ERR, e.getErrorCode());
        assertEquals("Wrong postponement value: -1", e.getMessage());

        assertDoesNotThrow(() -> breakPoint.postpone(1));

        boolean isPostponedAfter = breakPoint.isPostponed();
        String bpTimeValueAfter = breakPoint.getTimeValue();

        assertFalse(isPostponedBefore);
        assertEquals("09:30", bpTimeValueBefore);
        assertTrue(isPostponedAfter);
        assertEquals("09:31", bpTimeValueAfter);
    }

    @Test @Order(16)
    void testPostponeTwoTimesBothPositive() {
        BreakPoint breakPoint = new BreakPoint(defaultBPTime, BP_MESSAGE);

        BreakPointException e = assertThrows(BreakPointException.class, () -> {
            breakPoint.postpone(1);
            breakPoint.postpone(2);
        });
        assertEquals(BreakPointException.ErrorCode.ALREADY_POSTPONED_ERR, e.getErrorCode());
        assertEquals("Break point has been already postponed.", e.getMessage());
    }

    @Test @Order(17)
    void testPostponeTwoTimesBothNegative() {
        BreakPoint breakPoint = new BreakPoint(defaultBPTime, BP_MESSAGE);

        BreakPointException e = assertThrows(BreakPointException.class, () -> {
            breakPoint.postpone(-1);
            breakPoint.postpone(-2);
        });
        assertEquals(BreakPointException.ErrorCode.NEGATIVE_POSTPONE_ERR, e.getErrorCode());
        assertEquals("Wrong postponement value: -1", e.getMessage());
    }

    @Test @Order(18)
    void testPostponeTwoTimesBothZero() {
        BreakPoint breakPoint = new BreakPoint(defaultBPTime, BP_MESSAGE);

        BreakPointException e = assertThrows(BreakPointException.class, () -> {
            breakPoint.postpone(0);
            breakPoint.postpone(0);
        });
        assertEquals(BreakPointException.ErrorCode.ZERO_POSTPONE_ERR, e.getErrorCode());
        assertEquals("Wrong postponement value: 0", e.getMessage());
    }

    @Test @Order(19)
    void testPostponeTwoTimesFirstPositiveSecondNegative() {
        BreakPoint breakPoint = new BreakPoint(defaultBPTime, BP_MESSAGE);

        BreakPointException e = assertThrows(BreakPointException.class, () -> {
            breakPoint.postpone(1);
            breakPoint.postpone(-1);
        });
        assertEquals(BreakPointException.ErrorCode.ALREADY_POSTPONED_ERR, e.getErrorCode());
        assertEquals("Break point has been already postponed.", e.getMessage());
    }

    @Test @Order(20)
    void testPostponeTwoTimesFirstPositiveSecondZero() {
        BreakPoint breakPoint = new BreakPoint(defaultBPTime, BP_MESSAGE);

        BreakPointException e = assertThrows(BreakPointException.class, () -> {
            breakPoint.postpone(1);
            breakPoint.postpone(0);
        });
        assertEquals(BreakPointException.ErrorCode.ALREADY_POSTPONED_ERR, e.getErrorCode());
        assertEquals("Break point has been already postponed.", e.getMessage());
    }

    @Test @Order(21)
    void testPostponeTwoTimesFirstNegativeSecondPositive() {
        BreakPoint breakPoint = new BreakPoint(defaultBPTime, BP_MESSAGE);

        BreakPointException e = assertThrows(BreakPointException.class, () -> {
            breakPoint.postpone(-1);
            breakPoint.postpone(1);
        });
        assertEquals(BreakPointException.ErrorCode.NEGATIVE_POSTPONE_ERR, e.getErrorCode());
        assertEquals("Wrong postponement value: -1", e.getMessage());
    }

    @Test @Order(22)
    void testPostponeTwoTimesFirstNegativeSecondZero() {
        BreakPoint breakPoint = new BreakPoint(defaultBPTime, BP_MESSAGE);

        BreakPointException e = assertThrows(BreakPointException.class, () -> {
            breakPoint.postpone(-1);
            breakPoint.postpone(0);
        });
        assertEquals(BreakPointException.ErrorCode.NEGATIVE_POSTPONE_ERR, e.getErrorCode());
        assertEquals("Wrong postponement value: -1", e.getMessage());
    }

    @Test @Order(23)
    void testPostponeTwoTimesFirstZeroSecondPositive() {
        BreakPoint breakPoint = new BreakPoint(defaultBPTime, BP_MESSAGE);

        BreakPointException e = assertThrows(BreakPointException.class, () -> {
            breakPoint.postpone(0);
            breakPoint.postpone(1);
        });
        assertEquals(BreakPointException.ErrorCode.ZERO_POSTPONE_ERR, e.getErrorCode());
        assertEquals("Wrong postponement value: 0", e.getMessage());
    }

    @Test @Order(24)
    void testPostponeTwoTimesFirstZeroSecondNegative() {
        BreakPoint breakPoint = new BreakPoint(defaultBPTime, BP_MESSAGE);

        BreakPointException e = assertThrows(BreakPointException.class, () -> {
            breakPoint.postpone(0);
            breakPoint.postpone(-1);
        });
        assertEquals(BreakPointException.ErrorCode.ZERO_POSTPONE_ERR, e.getErrorCode());
        assertEquals("Wrong postponement value: 0", e.getMessage());
    }

    @Test @Order(25)
    void testIsNow() throws BreakPointException {
        for (int h = 0; h < 24; h++){
            for(int m = 0; m < 60; m++){
                Calendar currentTime = Calendar.getInstance();
                int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
                int currentMinute = currentTime.get(Calendar.MINUTE);

                BPSimpleTime alternativeBPTime = new BPSimpleTime(h, m);
                BreakPoint breakPoint = new BreakPoint(alternativeBPTime, BP_MESSAGE);
                boolean isNow = breakPoint.isNow();

                boolean expectedIsNow = h == currentHour && m == currentMinute;

                assertEquals(expectedIsNow, isNow);
            }
        }
    }

    @Test @Order(26)
    void testIsNotPast() throws BreakPointException {
        for (int h = 0; h < 24; h++){
            for(int m = 0; m < 60; m++){
                Calendar currentTime = Calendar.getInstance();
                int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
                int currentMinute = currentTime.get(Calendar.MINUTE);

                BPSimpleTime alternativeBPTime = new BPSimpleTime(h, m);
                BreakPoint breakPoint = new BreakPoint(alternativeBPTime, BP_MESSAGE);
                boolean isNotPast = breakPoint.isNotPast();

                boolean expectedIsNotPast = h > currentHour || (h == currentHour && m >= currentMinute);

                assertEquals(expectedIsNotPast, isNotPast);
            }
        }
    }

    @Test @Order(27)
    void hasTheSameTime() throws BreakPointException {
        for (int h = 0; h < 24; h++){
            for(int m = 0; m < 60; m++){
                BPSimpleTime alternativeBPTime = new BPSimpleTime(h, m);

                BreakPoint breakPoint1 = new BreakPoint(defaultBPTime, BP_MESSAGE);
                BreakPoint breakPoint2 = new BreakPoint(alternativeBPTime, BP_MESSAGE);
                boolean hasTheSameTime1 = breakPoint1.hasTheSameTime(breakPoint2);
                boolean hasTheSameTime2 = breakPoint2.hasTheSameTime(breakPoint1);

                boolean expectedHasTheSameTime = h == defaultBPTime.getHour() && m == defaultBPTime.getMinutes();

                assertEquals(expectedHasTheSameTime, hasTheSameTime1);
                assertEquals(expectedHasTheSameTime, hasTheSameTime2);
            }
        }
    }

    @Test @Order(28)
    void testEquals() throws BreakPointException {
        BPSimpleTime alternativeBPTime = new BPSimpleTime(defaultBPTime.getHour(), defaultBPTime.getMinutes() + 1);
        String alternativeMessage = "";

        BreakPoint breakPoint1 = new BreakPoint(defaultBPTime, BP_MESSAGE);
        BreakPoint breakPoint2 = new BreakPoint(defaultBPTime, BP_MESSAGE);
        BreakPoint breakPoint3 = new BreakPoint(defaultBPTime, BP_MESSAGE, false);
        BreakPoint breakPoint4 = new BreakPoint(alternativeBPTime, BP_MESSAGE);
        BreakPoint breakPoint5 = new BreakPoint(defaultBPTime, alternativeMessage);
        BreakPoint breakPoint6 = new BreakPoint(defaultBPTime, BP_MESSAGE, true);
        BreakPoint breakPoint7 = new BreakPoint(alternativeBPTime, alternativeMessage);
        BreakPoint breakPoint8 = new BreakPoint(alternativeBPTime, BP_MESSAGE, true);
        BreakPoint breakPoint9 = new BreakPoint(defaultBPTime, alternativeMessage, true);
        BreakPoint breakPoint10 = new BreakPoint(alternativeBPTime, alternativeMessage, true);

        assertEquals(breakPoint1, breakPoint1);     // The same instance
        assertEquals(breakPoint2, breakPoint1);     // Another default instance
        assertEquals(breakPoint3, breakPoint1);     // Another instance with postponed field customized
        assertNotEquals(breakPoint4, breakPoint1);  // Different time
        assertEquals(breakPoint5, breakPoint1);     // Different message
        assertNotEquals(breakPoint6, breakPoint1);  // Different postponed
        assertNotEquals(breakPoint7, breakPoint1);  // Different time and message
        assertNotEquals(breakPoint8, breakPoint1);  // Different time and postponed
        assertNotEquals(breakPoint9, breakPoint1);  // Different message and postponed
        assertNotEquals(breakPoint10, breakPoint1); // Different time, message and postponed
    }

    @Test @Order(29)
    void testHashCode() throws BreakPointException {
        BPSimpleTime alternativeBPTime = new BPSimpleTime(defaultBPTime.getHour(), defaultBPTime.getMinutes() + 1);
        String alternativeMessage = "";

        BreakPoint breakPoint1 = new BreakPoint(defaultBPTime, BP_MESSAGE);
        BreakPoint breakPoint2 = new BreakPoint(defaultBPTime, BP_MESSAGE);
        BreakPoint breakPoint3 = new BreakPoint(defaultBPTime, BP_MESSAGE, false);
        BreakPoint breakPoint4 = new BreakPoint(alternativeBPTime, BP_MESSAGE);
        BreakPoint breakPoint5 = new BreakPoint(defaultBPTime, alternativeMessage);
        BreakPoint breakPoint6 = new BreakPoint(defaultBPTime, BP_MESSAGE, true);
        BreakPoint breakPoint7 = new BreakPoint(alternativeBPTime, alternativeMessage);
        BreakPoint breakPoint8 = new BreakPoint(alternativeBPTime, BP_MESSAGE, true);
        BreakPoint breakPoint9 = new BreakPoint(defaultBPTime, alternativeMessage, true);
        BreakPoint breakPoint10 = new BreakPoint(alternativeBPTime, alternativeMessage, true);

        assertEquals(breakPoint1.hashCode(), breakPoint1.hashCode());     // The same instance
        assertEquals(breakPoint2.hashCode(), breakPoint1.hashCode());     // Another default instance
        assertEquals(breakPoint3.hashCode(), breakPoint1.hashCode());     // Another instance with postponed field customized
        assertNotEquals(breakPoint4.hashCode(), breakPoint1.hashCode());  // Different time
        assertEquals(breakPoint5.hashCode(), breakPoint1.hashCode());     // Different message
        assertNotEquals(breakPoint6.hashCode(), breakPoint1.hashCode());  // Different postponed
        assertNotEquals(breakPoint7.hashCode(), breakPoint1.hashCode());  // Different time and message
        assertNotEquals(breakPoint8.hashCode(), breakPoint1.hashCode());  // Different time and postponed
        assertNotEquals(breakPoint9.hashCode(), breakPoint1.hashCode());  // Different message and postponed
        assertNotEquals(breakPoint10.hashCode(), breakPoint1.hashCode()); // Different time, message and postponed
    }

    @Test @Order(30)
    void compareTo() throws BreakPointException {
        BPSimpleTime alternativeBPTime1 = new BPSimpleTime(defaultBPTime.getHour(), defaultBPTime.getMinutes() + 1);
        BPSimpleTime alternativeBPTime2 = new BPSimpleTime(defaultBPTime.getHour(), defaultBPTime.getMinutes() + 2);

        BreakPoint breakPoint1 = new BreakPoint(defaultBPTime, BP_MESSAGE);
        BreakPoint breakPoint2 = new BreakPoint(defaultBPTime, BP_MESSAGE);
        BreakPoint breakPoint3 = new BreakPoint(alternativeBPTime1, BP_MESSAGE);
        BreakPoint breakPoint4 = new BreakPoint(alternativeBPTime2, BP_MESSAGE);

        assertEquals(0, breakPoint1.compareTo(breakPoint2));
        assertEquals(-1, breakPoint1.compareTo(breakPoint3));
        assertEquals(-1, breakPoint1.compareTo(breakPoint4));

        assertEquals(0, breakPoint2.compareTo(breakPoint1));
        assertEquals(-1, breakPoint2.compareTo(breakPoint3));
        assertEquals(-1, breakPoint2.compareTo(breakPoint4));

        assertEquals(1, breakPoint3.compareTo(breakPoint1));
        assertEquals(1, breakPoint3.compareTo(breakPoint2));
        assertEquals(-1, breakPoint3.compareTo(breakPoint4));

        assertEquals(1, breakPoint4.compareTo(breakPoint1));
        assertEquals(1, breakPoint4.compareTo(breakPoint2));
        assertEquals(1, breakPoint4.compareTo(breakPoint3));
    }

    @Test @Order(31)
    void testClone() {
        BreakPoint breakPoint1 = new BreakPoint(defaultBPTime, BP_MESSAGE);
        BreakPoint breakPoint2 = (BreakPoint) breakPoint1.clone();

        String bpTimeValue = breakPoint1.getTimeValue();
        String cloneTimeValue = breakPoint2.getTimeValue();
        String bpMessage = breakPoint1.getMessage();
        String cloneMessage = breakPoint2.getMessage();
        boolean bpPostponed = breakPoint1.isPostponed();
        boolean clonePostponed = breakPoint2.isPostponed();

        assertNotSame(breakPoint1, breakPoint2);
        assertEquals(breakPoint1, breakPoint2);
        assertEquals(bpTimeValue, cloneTimeValue);
        assertEquals(bpMessage, cloneMessage);
        assertEquals(bpPostponed, clonePostponed);
    }
}