package com.arch.dayframe.controller.listeners;

import com.arch.dayframe.model.ringer.CycleRinger;
import com.arch.dayframe.model.ringer.Ringer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class RingerListener implements ActionListener {

    private final Ringer ringer;
    private final int initialDelay;
    private final int standardDelay;
    private final int exceptionalDelay;

    public RingerListener(Ringer ringer, int initialDelay) {
        int cycleInterval = getCycleInterval(ringer);
        this.ringer = ringer;
        this.initialDelay = initialDelay;
        this.standardDelay = (cycleInterval * 60 * 1000) - 500;  // half of second before new interval
        this.exceptionalDelay = (cycleInterval - 1) * 60 * 1000; // one minute before new interval
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Timer timer = (Timer) e.getSource();
        if (!ringer.rang() && ringer.tryRing())
            pauseTimer(timer);
        else if (timerHasBeenPaused(timer))
            releaseTimer(timer);
    }

    private int getCycleInterval(Ringer ringer) {
        return ringer instanceof CycleRinger ? ((CycleRinger) ringer).getMinutesInterval() : 60;
    }

    private void pauseTimer(Timer timer) {
        // Exceptional delay will be used when application has been launched
        // in first minute of the interval but not in first second.
        // Thanks the exceptional delay, next bell will ring in first second.
        timer.setDelay(minuteJustStarted() ? standardDelay : exceptionalDelay);
    }

    private void releaseTimer(Timer timer) {
        timer.setDelay(initialDelay);
    }

    private boolean timerHasBeenPaused(Timer timer) {
        return timer.getDelay() != initialDelay;
    }

    private boolean minuteJustStarted() {
        return Calendar.getInstance().get(Calendar.SECOND) == 0;
    }
}
