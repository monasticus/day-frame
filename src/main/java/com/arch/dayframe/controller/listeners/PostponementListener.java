package com.arch.dayframe.controller.listeners;

import com.arch.dayframe.gui.DayFrameFrame;
import com.arch.dayframe.gui.dialog.BreakDialog;
import com.arch.dayframe.model.bp.BreakPointException;
import com.arch.dayframe.model.bp.BreakPoints;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PostponementListener implements ActionListener {

    private final BreakPoints breakPoints;
    private final DayFrameFrame dayFrame;
    private final BreakDialog dialog;

    public PostponementListener(BreakPoints breakPoints, DayFrameFrame dayFrame, BreakDialog dialog) {
        this.breakPoints = breakPoints;
        this.dayFrame = dayFrame;
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            postponeBreakPoint();
            refreshDayFramePanels();
            refreshAndHideDialog();
        } catch (BreakPointException ignore) {} // impossible to be thrown using the GUI
    }

    private void postponeBreakPoint() {
        int minutes = getPostponementMinutes();
        breakPoints.postponeRecent(minutes);
    }

    private int getPostponementMinutes() {
        String selectedMinutes = String.valueOf(dialog.postponeList.getSelectedItem());
        return Integer.parseInt(selectedMinutes);
    }

    private void refreshDayFramePanels() {
        refreshTimePanel();
        refreshBreakPointsPanel();
    }

    private void refreshTimePanel() {
        dayFrame.setRecentBreakPoint(breakPoints.getPrevious());
        dayFrame.setNextBreakPoint(breakPoints.getNext());
    }

    private void refreshBreakPointsPanel() {
        dayFrame.setBreakPointsList(breakPoints.getFutureWithoutNext());
    }

    private void refreshAndHideDialog() {
        dialog.postponeList.setSelectedIndex(0);
        dialog.setVisible(false);
    }
}
