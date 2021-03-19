package com.arch.dayframe.model.bp;

import com.arch.dayframe.model.bp.BreakPointException.ErrorCode;

class BPSimpleTime implements SimpleTime {

    private static final String TIME_FORMAT = "%02d:%02d";

    private int hour;
    private int minutes;

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
    public void add(int minutes) {
        if (minutes >= 0) {
            minutes = getRidOfDays(minutes);
            changeTimeByMinutes(minutes);
        } else {
            throw new IllegalArgumentException("Minutes parameter cannot be lower than 0");
        }
    }

    private void validateTime(int hour, int minute) throws BreakPointException {
        validateHour(hour);
        validateMinute(minute);
    }

    private void validateHour(int hour) throws BreakPointException {
        if (!isCorrectHour(hour))
            throw new BreakPointException(ErrorCode.TIME_ERR, String.format("[hour=%d]", hour));
    }

    private void validateMinute(int minute) throws BreakPointException {
        if (!isCorrectMinute(minute))
            throw new BreakPointException(ErrorCode.TIME_ERR, String.format("[minute=%d]", minute));
    }

    private static boolean isCorrectHour(int hour) {
        return hour >= 0 && hour <= 23;
    }

    private static boolean isCorrectMinute(int minute) {
        return minute >= 0 && minute <= 59;
    }

    private int getRidOfDays(int minutes) {
        return minutes % (24*60);
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
