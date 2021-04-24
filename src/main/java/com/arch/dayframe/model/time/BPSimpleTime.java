package com.arch.dayframe.model.time;

import com.arch.dayframe.model.bp.BreakPointException;
import com.arch.dayframe.model.bp.BreakPointException.ErrorCode;

import java.util.Calendar;
import java.util.Objects;

public class BPSimpleTime implements SimpleTime {

    private static final String TIME_FORMAT = "%02d:%02d";

    private int hour;
    private int minutes;

    public BPSimpleTime() {
        Calendar currentTime = Calendar.getInstance();
        this.hour = currentTime.get(Calendar.HOUR_OF_DAY);
        this.minutes = currentTime.get(Calendar.MINUTE);
    }

    public BPSimpleTime(int hour, int minutes) throws BreakPointException {
        validateTime(hour, minutes);
        this.hour = hour;
        this.minutes = minutes;
    }

    @Override
    public String getTime() {
        return String.format(TIME_FORMAT, hour, minutes);
    }

    @Override
    public int getHour() {
        return hour;
    }

    @Override
    public int getMinutes() {
        return minutes;
    }

    @Override
    public void add(int minutes) {
        if (minutes >= 0) {
            minutes = getRidOfDays(minutes);
            changeTimeByMinutes(minutes);
        } else {
            throw new IllegalArgumentException("Minutes parameter cannot be lower than 0");
        }
    }

    @Override
    public boolean isNow() {
        SimpleTime currentTime = new BPSimpleTime();
        return this.compareTo(currentTime) == 0;
    }

    @Override
    public boolean isPast() {
        SimpleTime currentTime = new BPSimpleTime();
        return this.compareTo(currentTime) < 0;
    }

    @Override
    public boolean isFuture() {
        SimpleTime currentTime = new BPSimpleTime();
        return this.compareTo(currentTime) > 0;
    }

    @Override
    public SimpleTime clone() {
        return new BPSimpleTime(hour, minutes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleTime that = (SimpleTime) o;
        return hour == that.getHour() && minutes == that.getMinutes();
    }

    @Override
    public int hashCode() {
        return Objects.hash(hour, minutes);
    }

    @Override
    public int compareTo(SimpleTime other) {
        int hourComparison = Integer.compare(this.hour, other.getHour());
        if (hourComparison != 0)
            return hourComparison;
        else
            return Integer.compare(this.minutes, other.getMinutes());
    }

    private void validateTime(int hour, int minute) throws BreakPointException {
        validateHour(hour);
        validateMinute(minute);
    }

    private void validateHour(int hour) throws BreakPointException {
        if (!isCorrectHour(hour))
            throw new BreakPointException(ErrorCode.TIME_FORMAT_ERR, String.format("[hour=%d]", hour));
    }

    private void validateMinute(int minute) throws BreakPointException {
        if (!isCorrectMinute(minute))
            throw new BreakPointException(ErrorCode.TIME_FORMAT_ERR, String.format("[minutes=%d]", minute));
    }

    private static boolean isCorrectHour(int hour) {
        return hour >= 0 && hour <= 23;
    }

    private static boolean isCorrectMinute(int minute) {
        return minute >= 0 && minute <= 59;
    }

    private int getRidOfDays(int minutes) {
        return minutes % (24 * 60);
    }

    private void changeTimeByMinutes(int minutes) {
        this.minutes += minutes;
        normalizeTime();
    }

    private void normalizeTime() {
        while (minutes >= 60) {
            hour++;
            minutes -= 60;
        }
        hour = hour >= 24 ? hour % 24 : hour;
    }
}
