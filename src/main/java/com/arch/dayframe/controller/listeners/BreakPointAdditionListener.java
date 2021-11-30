package com.arch.dayframe.controller.listeners;

import com.arch.dayframe.gui.DayFrameFrame;
import com.arch.dayframe.model.bp.BreakPoint;
import com.arch.dayframe.model.bp.BreakPointException;
import com.arch.dayframe.model.bp.BreakPointFactory;
import com.arch.dayframe.model.bp.BreakPoints;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BreakPointAdditionListener implements ActionListener {

    private final BreakPoints breakPoints;
    private final DayFrameFrame dayFrame;

    public BreakPointAdditionListener(BreakPoints breakPoints, DayFrameFrame dayFrame) {
        this.breakPoints = breakPoints;
        this.dayFrame = dayFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        addBreakPoint();
    }

    private void addBreakPoint() {
        String breakPointTime = dayFrame.southPanel.breakPointTimeField.getText();
        String breakPointMessage = dayFrame.southPanel.breakPointMessageField.getText();
        String breakPointDescription = breakPointMessage.isBlank() ? breakPointTime : breakPointTime + " - " + breakPointMessage;
        try {
            BreakPoint breakPoint = BreakPointFactory.fromDescription(breakPointDescription);
            breakPoints.add(breakPoint);
            refreshDayFramePanels();
        } catch (BreakPointException e) {

        }

    }

    private void refreshDayFramePanels() {
        refreshTimePanel();
        refreshBreakPointsPanel();
        refreshManagementPanel();
    }

    private void refreshTimePanel() {
        dayFrame.setRecentBreakPoint(breakPoints.getPrevious());
        dayFrame.setNextBreakPoint(breakPoints.getNext());
    }

    private void refreshBreakPointsPanel() {
        dayFrame.setBreakPointsList(breakPoints.getFutureWithoutNext());
    }

    private void refreshManagementPanel() {
        dayFrame.southPanel.resetValues();
    }

}
