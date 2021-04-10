package com.arch.dayframe.controller.listeners;

import com.arch.dayframe.gui.DayFrameFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class ClockListener implements ActionListener {

    private final DayFrameFrame dayFrame;

    public ClockListener(DayFrameFrame dayFrame) {
        this.dayFrame = dayFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String currentTime = getCurrentTime();
        dayFrame.setClockValue(currentTime);
    }

    private static String getCurrentTime() {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minutes = currentTime.get(Calendar.MINUTE);
        int second = currentTime.get(Calendar.SECOND);
        int decimalSeconds = getDecimalSeconds();
        return String.format("%02d:%02d:%02d.%d", hour, minutes, second, decimalSeconds);
    }

    private static int getDecimalSeconds() {
        return (int) Math.floor((double) Calendar.getInstance().get(Calendar.MILLISECOND) / 100);
    }
}
