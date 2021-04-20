package com.arch.dayframe.gui.panel;

import com.arch.dayframe.model.bp.BreakPoint;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class TimePanel extends JPanel {

    private JLabel leftLabel;
    private JLabel centerLabel;
    private JLabel rightLabel;

    public TimePanel() {
        setGeneralView();
        buildLabels();
    }

    public void setLeftLabelValue(BreakPoint breakPoint) {
        leftLabel.setText(BreakPoint.getTimeValue(breakPoint));
    }

    public void setCenterLabelValue(String value) {
        centerLabel.setText(Optional.ofNullable(value).orElse(""));
    }

    public void setRightLabelValue(BreakPoint breakPoint) {
        rightLabel.setText(BreakPoint.getTimeValue(breakPoint));
        Color color = breakPoint != null && breakPoint.isPostponed() ? Color.RED : Color.BLACK;
        rightLabel.setForeground(color);
    }

    private void setGeneralView() {
        setBackground(Color.WHITE);
        customizeLayout();
        setPreferredSize(new Dimension(0, 65));
    }

    private void customizeLayout() {
        GridLayout layout = new GridLayout(1, 3);
        setLayout(layout);
    }

    private void buildLabels() {
        buildLeftLabel();
        buildCenterLabel();
        buildRightLabel();
    }

    private void buildCenterLabel() {
        centerLabel = new JLabel();
        centerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        centerLabel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));
        centerLabel.setForeground(Color.BLACK);
        add(centerLabel);
    }

    private void buildLeftLabel() {
        leftLabel = new JLabel();
        buildSideLabel(leftLabel);
    }

    private void buildRightLabel() {
        rightLabel = new JLabel();
        buildSideLabel(rightLabel);
    }

    private void buildSideLabel(JLabel sideLabel) {
        sideLabel.setForeground(Color.BLACK);
        sideLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sideLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
        sideLabel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
        add(sideLabel);
    }
}
