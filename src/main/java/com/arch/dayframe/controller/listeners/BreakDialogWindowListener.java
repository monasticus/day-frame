package com.arch.dayframe.controller.listeners;

import com.arch.dayframe.controller.utils.DayFrameRobot;
import com.arch.dayframe.gui.dialog.BreakDialog;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BreakDialogWindowListener extends WindowAdapter {

    private final BreakDialog dialog;

    public BreakDialogWindowListener(BreakDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void windowLostFocus(WindowEvent e) {
        if (dialog.isVisible()) {
            dialog.requestFocus();
            DayFrameRobot.moveCursorToComponent(dialog);
        }
    }
}
