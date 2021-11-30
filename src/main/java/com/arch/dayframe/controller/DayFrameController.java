package com.arch.dayframe.controller;

import com.arch.dayframe.controller.listeners.*;
import com.arch.dayframe.gui.DayFrameFrame;
import com.arch.dayframe.gui.dialog.BreakDialog;
import com.arch.dayframe.model.bp.BreakPointException;
import com.arch.dayframe.model.bp.BreakPoints;
import com.arch.dayframe.model.ringer.CycleRinger;
import com.arch.dayframe.model.ringer.Ringer;

import javax.swing.*;
import java.io.IOException;

public class DayFrameController {

    private static final String HOURS_SOUND_PATH = "src/main/resources/sounds/hours.wav";
    private static final String QUARTER_SOUND_PATH = "src/main/resources/sounds/quarter.wav";
    private static final int CLOCK_TIMER_DELAY = 100;
    private static final int RINGER_TIMER_DELAY = 100;
    private static final int BREAK_POINT_MOVEMENT_TIMER_DELAY = 10;
    private static final int FOCUS_TIMER_DELAY = 1000;

    private final DayFrameFrame dayFrame;
    private final BreakDialog dialog;
    private final BreakPoints breakPoints;

    public DayFrameController(DayFrameFrame dayFrame, String breakPointsPath) throws IOException, BreakPointException {
        this.dayFrame = dayFrame;
        this.dialog = new BreakDialog(dayFrame);
        this.breakPoints = new BreakPoints(breakPointsPath);
    }

    public void start() {
        initializeValues();
        addListeners();
        setTimers();
    }

    private void initializeValues() {
        dayFrame.setRecentBreakPoint(null);
        dayFrame.setNextBreakPoint(breakPoints.getNext());
        dayFrame.setBreakPointsList(breakPoints.getFutureWithoutNext());
    }

    private void addListeners() {
        dayFrame.addWindowListener(new DayFrameWindowListener(dayFrame));
        dayFrame.southPanel.addButton.addActionListener(new BreakPointAdditionListener(breakPoints, dayFrame));
        dialog.addWindowFocusListener(new BreakDialogWindowListener(dialog));
        dialog.postponeButton.addActionListener(new PostponementListener(breakPoints, dayFrame, dialog));
        dialog.okButton.addActionListener(new BreakListener(dialog));
    }

    private void setTimers() {
        setClockTimer();
        setRingerTimer();
        setBreakPointMovementTimer();
        setDialogPositionTimer();
    }

    private void setClockTimer() {
        Timer clockTimer = new Timer(CLOCK_TIMER_DELAY, new ClockListener(dayFrame));
        clockTimer.start();
    }

    private void setRingerTimer() {
        Ringer ringer = new CycleRinger(HOURS_SOUND_PATH, QUARTER_SOUND_PATH);
        Timer ringerTimer = new Timer(RINGER_TIMER_DELAY, new RingerListener(ringer, RINGER_TIMER_DELAY));
        ringerTimer.start();
    }

    private void setBreakPointMovementTimer() {
        Timer breakPointTimer = new Timer(BREAK_POINT_MOVEMENT_TIMER_DELAY, new BreakPointsMovementListener(dayFrame, dialog, breakPoints));
        breakPointTimer.start();
    }

    private void setDialogPositionTimer() {
        Timer dialogPositionTimer = new Timer(FOCUS_TIMER_DELAY, new BreakDialogPositionListener(dialog));
        dialogPositionTimer.start();
    }
}
