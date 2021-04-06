package com.arch.dayframe.model.bp;

import java.security.InvalidParameterException;
import java.util.Objects;
import java.util.Optional;

import static com.arch.dayframe.model.bp.BreakPointException.ErrorCode.*;

public class BreakPoint implements Comparable<BreakPoint>, Cloneable {

    private final SimpleTime time;
    private final String message;
    private boolean postponed;

    protected BreakPoint(SimpleTime time, String message) {
        this(time, message, false);
    }

    protected BreakPoint(SimpleTime time, String message, boolean postponed) {
        this.time = Optional.ofNullable(time).orElseThrow(InvalidParameterException::new);
        this.message = Optional.ofNullable(message).orElseThrow(InvalidParameterException::new);
        this.postponed = postponed;
    }

    public static String getTimeValue(BreakPoint breakPoint) {
        return breakPoint == null ? "" : breakPoint.getTimeValue();
    }

    public String getTimeValue() {
        return time.getTime();
    }

    public String getMessage() {
        return message;
    }

    public boolean isPostponed() {
        return postponed;
    }

    public void postpone(int postponementMinutes) throws BreakPointException {
        if (!postponed && postponementMinutes > 0) {
            time.add(postponementMinutes);
            postponed = true;
        } else if (postponed) {
            throw new BreakPointException(ALREADY_POSTPONED_ERR);
        } else if (postponementMinutes == 0) {
            throw new BreakPointException(ZERO_POSTPONE_ERR);
        } else {
            throw new BreakPointException(NEGATIVE_POSTPONE_ERR, String.valueOf(postponementMinutes));
        }
    }

    public boolean isNow() {
        return time.isNow();
    }

    public boolean isNotPast() {
        return !time.isPast();
    }

    public boolean hasTheSameTime(BreakPoint other) {
        String thisTime = getTimeValue();
        String otherTime = getTimeValue(other);
        return thisTime.equals(otherTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BreakPoint that = (BreakPoint) o;
        return postponed == that.postponed && time.getTime().equals(that.time.getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, postponed);
    }

    @Override
    public int compareTo(BreakPoint other) {
        return time.compareTo(other.time);
    }

    @Override
    public BreakPoint clone() {
        return new BreakPoint(time.clone(), message, postponed);
    }
}
