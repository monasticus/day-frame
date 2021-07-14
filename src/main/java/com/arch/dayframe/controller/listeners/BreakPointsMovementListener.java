package com.arch.dayframe.controller.listeners;

import com.arch.dayframe.controller.utils.DayFrameRobot;
import com.arch.dayframe.gui.DayFrameFrame;
import com.arch.dayframe.gui.dialog.BreakDialog;
import com.arch.dayframe.model.bp.BreakPoint;
import com.arch.dayframe.model.bp.BreakPoints;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BreakPointsMovementListener implements ActionListener {

    private final DayFrameFrame dayFrame;
    private final BreakDialog dialog;
    private final BreakPoints breakPoints;

    public BreakPointsMovementListener(DayFrameFrame dayFrame, BreakDialog dialog, BreakPoints breakPoints) {
        this.dayFrame = dayFrame;
        this.dialog = dialog;
        this.breakPoints = breakPoints;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (hasNextBreakPoint() && isNextBreakPointNow()) {
            BreakPoint recentBreakPoint = breakPoints.moveForward();
            BreakPoint nextBreakPoint = breakPoints.getNext();
            updateBreakPoints(recentBreakPoint, nextBreakPoint);
            showDialogWithBreakPoint(recentBreakPoint);
        }
    }

    private boolean hasNextBreakPoint() {
        return breakPoints.getNext() != null;
    }

    private boolean isNextBreakPointNow() {
        return breakPoints.getNext().isNow();
    }

    private void updateBreakPoints(BreakPoint recentBreakPoint, BreakPoint nextBreakPoint) {
        dayFrame.setRecentBreakPoint(recentBreakPoint);
        dayFrame.setNextBreakPoint(nextBreakPoint);
        dayFrame.removeBreakPointFromList(nextBreakPoint);
    }

    private void showDialogWithBreakPoint(BreakPoint breakPoint) {
        dialog.completeDialog(breakPoint);
        dialog.pack();
        dialog.setVisible(true);
        dialog.setAlwaysOnTop(true);
        dialog.toFront();
        dialog.requestFocusInWindow();
        DayFrameRobot.moveCursorToComponent(dialog, true);
    }
}
