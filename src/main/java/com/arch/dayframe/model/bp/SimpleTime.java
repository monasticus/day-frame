package com.arch.dayframe.model.bp;

public interface SimpleTime extends Comparable<SimpleTime>{

    String getTime();

    int getHour();

    int getMinutes();

    void add(int minutes);

    boolean isNow();

    boolean isPast();

    boolean isFuture();

    @Override
    int compareTo(SimpleTime other);
}
