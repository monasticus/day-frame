package com.arch.dayframe.model.bp;

import com.arch.dayframe.model.time.BPSimpleTime;
import org.junit.jupiter.api.*;

import java.security.InvalidParameterException;

import static com.arch.dayframe.model.bp.BreakPointException.ErrorCode.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("BreakPointException")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BreakPointExceptionTest {

    @Test @Order(1)
    @DisplayName("new BreakPointException() - nulls passed to constructor")
    void testNullsPassedToConstructor() {
        assertThrows(InvalidParameterException.class, () -> new BreakPointException(null));
        assertThrows(InvalidParameterException.class, () -> new BreakPointException(DESCRIPTION_FORMAT_ERR, null));
        assertThrows(InvalidParameterException.class, () -> new BreakPointException(null, null));
    }

    @Test @Order(2)
    @DisplayName("DESCRIPTION_FORMAT_ERR - without a source")
    void testDescriptionFormatErrorExceptionWithoutSource() {
        BreakPointException e = new BreakPointException(DESCRIPTION_FORMAT_ERR);
        assertEquals(DESCRIPTION_FORMAT_ERR, e.getErrorCode());
        assertEquals("Wrong break point description: [NO-SOURCE]", e.getMessage());
    }

    @Test @Order(3)
    @DisplayName("DESCRIPTION_FORMAT_ERR - with a source")
    void testDescriptionFormatErrorExceptionWithSource() {
        BreakPointException e = new BreakPointException(DESCRIPTION_FORMAT_ERR, "09:23-message");
        assertEquals(DESCRIPTION_FORMAT_ERR, e.getErrorCode());
        assertEquals("Wrong break point description: 09:23-message", e.getMessage());
    }

    @Test @Order(4)
    @DisplayName("TIME_FORMAT_ERR - without a source")
    void testTimeFormatErrorExceptionWithoutSource() {
        BreakPointException e = new BreakPointException(TIME_FORMAT_ERR);
        assertEquals(TIME_FORMAT_ERR, e.getErrorCode());
        assertEquals("Wrong break point time: [NO-SOURCE]", e.getMessage());
    }

    @Test @Order(5)
    @DisplayName("TIME_FORMAT_ERR - with a source (incorrect hour example)")
    void testTimeFormatErrorExceptionWithSourceHour() {
        BreakPointException e = new BreakPointException(TIME_FORMAT_ERR, "[hour=24]");
        assertEquals(TIME_FORMAT_ERR, e.getErrorCode());
        assertEquals("Wrong break point time: [hour=24]", e.getMessage());
    }

    @Test @Order(6)
    @DisplayName("TIME_FORMAT_ERR - with a source (incorrect minutes example)")
    void testTimeFormatErrorExceptionWithSourceMinute() {
        BreakPointException e = new BreakPointException(TIME_FORMAT_ERR, "[minutes=60]");
        assertEquals(TIME_FORMAT_ERR, e.getErrorCode());
        assertEquals("Wrong break point time: [minutes=60]", e.getMessage());
    }

    @Test @Order(7)
    @DisplayName("TIME_DUPLICATE_ERR - without a source")
    void testTimeDuplicateErrorExceptionWithoutSource() {
        BreakPointException e = new BreakPointException(TIME_DUPLICATE_ERR);
        assertEquals(TIME_DUPLICATE_ERR, e.getErrorCode());
        assertEquals("Duplicated break point time: [NO-SOURCE]", e.getMessage());
    }

    @Test @Order(8)
    @DisplayName("TIME_DUPLICATE_ERR - with a source")
    void testTimeDuplicateErrorExceptionWithSource() {
        BreakPoint exampleBP = new BreakPoint(new BPSimpleTime(9, 25), "message");

        BreakPointException e = new BreakPointException(TIME_DUPLICATE_ERR, exampleBP.getTimeValue());
        assertEquals(TIME_DUPLICATE_ERR, e.getErrorCode());
        assertEquals("Duplicated break point time: 09:25", e.getMessage());
    }

    @Test @Order(9)
    @DisplayName("ALREADY_POSTPONED_ERR - without a source")
    void testAlreadyPostponedErrorExceptionWithoutSource() {
        BreakPointException e = new BreakPointException(ALREADY_POSTPONED_ERR);
        assertEquals(ALREADY_POSTPONED_ERR, e.getErrorCode());
        assertEquals("Break point has been already postponed.", e.getMessage());
    }

    @Test @Order(10)
    @DisplayName("ALREADY_POSTPONED_ERR - with a source")
    void testAlreadyPostponedErrorExceptionWithSource() {
        BreakPointException e = new BreakPointException(ALREADY_POSTPONED_ERR, "not-taken-into-account");
        assertEquals(ALREADY_POSTPONED_ERR, e.getErrorCode());
        assertEquals("Break point has been already postponed.", e.getMessage());
    }

    @Test @Order(11)
    @DisplayName("ZERO_POSTPONE_ERR - without a source")
    void testZeroPostponeErrorExceptionWithoutSource() {
        BreakPointException e = new BreakPointException(ZERO_POSTPONE_ERR);
        assertEquals(ZERO_POSTPONE_ERR, e.getErrorCode());
        assertEquals("Wrong postponement value: 0", e.getMessage());
    }

    @Test @Order(12)
    @DisplayName("ZERO_POSTPONE_ERR - with a source")
    void testZeroPostponeErrorExceptionWithSource() {
        BreakPointException e = new BreakPointException(ZERO_POSTPONE_ERR, "not-taken-into-account");
        assertEquals(ZERO_POSTPONE_ERR, e.getErrorCode());
        assertEquals("Wrong postponement value: 0", e.getMessage());
    }

    @Test @Order(13)
    @DisplayName("NEGATIVE_POSTPONE_ERR - without a source")
    void testNegativePostponeErrorExceptionWithoutSource() {
        BreakPointException e = new BreakPointException(NEGATIVE_POSTPONE_ERR);
        assertEquals(NEGATIVE_POSTPONE_ERR, e.getErrorCode());
        assertEquals("Wrong postponement value: [NO-SOURCE]", e.getMessage());
    }

    @Test @Order(14)
    @DisplayName("NEGATIVE_POSTPONE_ERR - with a source")
    void testNegativePostponeErrorExceptionWithSource() {
        BreakPointException e = new BreakPointException(NEGATIVE_POSTPONE_ERR, String.valueOf(-5));
        assertEquals(NEGATIVE_POSTPONE_ERR, e.getErrorCode());
        assertEquals("Wrong postponement value: -5", e.getMessage());
    }
}