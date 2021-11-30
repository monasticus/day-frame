package com.arch.dayframe.gui;

import com.arch.dayframe.gui.panel.BreakPointsPanel;
import com.arch.dayframe.gui.panel.ManagementPanel;
import com.arch.dayframe.gui.panel.TimePanel;
import com.arch.dayframe.model.bp.BreakPoint;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.prefs.Preferences;

public class DayFrameFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 700;
    private static final int DEFAULT_HEIGHT = 450;

    public TimePanel northPanel;
    public BreakPointsPanel eastPanel;
    public ManagementPanel southPanel;
    public JPanel westPanel;
    public JPanel centerPanel;

    public DayFrameFrame() throws HeadlessException {
        build();
    }

    public void setClockValue(String time) {
        northPanel.setCenterLabelValue(time);
    }

    public void setRecentBreakPoint(BreakPoint breakPoint) {
        northPanel.setLeftLabelValue(breakPoint);
    }

    public void setNextBreakPoint(BreakPoint breakPoint) {
        northPanel.setRightLabelValue(breakPoint);
    }

    public void setBreakPointsList(List<BreakPoint> breakPoints) {
        eastPanel.cleanBreakPoints();
        eastPanel.addBreakPoints(breakPoints);
    }

    public void removeBreakPointFromList(BreakPoint breakPoint) {
        eastPanel.removeBreakPoint(breakPoint);
    }

    public void saveLocation(){
        Preferences preferences = Preferences.userNodeForPackage(this.getClass());
        preferences.putInt("left", getX());
        preferences.putInt("top", getY());
    }

    private void build() {
        setBounds();
        buildPanels();
    }

    private void setBounds() {
        Preferences preferences = Preferences.userNodeForPackage(this.getClass());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int left = preferences.getInt("left", (screenSize.width - DEFAULT_WIDTH) / 2);
        int top = preferences.getInt("top", (screenSize.height - DEFAULT_HEIGHT) / 2);

        setBounds(left, top, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setResizable(false);
    }

    private void buildPanels() {
        buildNorthPanel();
        buildEastPanel();
        buildSouthPanel();
        buildWestPanel();
        buildCenterPanel();
    }

    private void buildNorthPanel() {
        northPanel = new TimePanel();
        add(northPanel, BorderLayout.NORTH);
    }

    private void buildEastPanel() {
        eastPanel = new BreakPointsPanel();
        add(eastPanel, BorderLayout.EAST);
    }

    private void buildSouthPanel() {
        southPanel = new ManagementPanel();
        southPanel.setBackground(Color.WHITE);
        add(southPanel, BorderLayout.SOUTH);
    }

    private void buildWestPanel() {
        westPanel = new JPanel();
        westPanel.setBackground(Color.WHITE);
        add(westPanel, BorderLayout.WEST);
    }

    private void buildCenterPanel() {
        centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        add(centerPanel, BorderLayout.CENTER);
    }
}
