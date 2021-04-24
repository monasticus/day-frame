package com.arch.dayframe.model.time;

public interface SimpleTime extends Comparable<SimpleTime>, Cloneable {

    String getTime();

    int getHour();

    int getMinutes();

    void add(int minutes);

    boolean isNow();

    boolean isPast();

    boolean isFuture();

    SimpleTime clone();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
