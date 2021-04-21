package com.arch.dayframe.controller.listeners;

import com.arch.dayframe.gui.DayFrameFrame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DayFrameWindowListener extends WindowAdapter {

    private final DayFrameFrame dayFrame;

    public DayFrameWindowListener(DayFrameFrame dayFrame) {
        this.dayFrame = dayFrame;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        dayFrame.saveLocation();
    }
}
