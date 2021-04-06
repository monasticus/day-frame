package com.arch.dayframe.gui.panel;

import com.arch.dayframe.model.bp.BreakPoint;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BreakPointsPanel extends JPanel {

    public BreakPointsPanel() {
        setGeneralView();
    }

    public void addBreakPoints(List<BreakPoint> breakPoints) {
        for (BreakPoint breakPoint : breakPoints) {
            JLabel bpLabel = buildLabelForBreakPoint(breakPoint);
            add(bpLabel);
        }
    }

    public void cleanBreakPoints() {
        Arrays.stream(getComponents()).forEach(this::remove);
    }

    public void removeBreakPoint(BreakPoint breakPoint) {
        if (breakPoint != null) {
            JLabel bpLabel = findLabelForBreakPoint(breakPoint);
            if (bpLabel != null) {
                remove(bpLabel);
                validate();
                repaint();
            }
        }
    }

    private void setGeneralView() {
        setBackground(Color.WHITE);
        customizeLayout();
        setPreferredSize(new Dimension(130, 0));
    }

    private void customizeLayout() {
        GridLayout layout = new GridLayout(10, 1);
        setLayout(layout);
    }

    private JLabel buildLabelForBreakPoint(BreakPoint breakPoint) {
        JLabel bpLabel = buildLabel();
        bpLabel.setText(breakPoint.getTimeValue());
        bpLabel.setForeground(getLabelForegroundColor(breakPoint));
        return bpLabel;
    }

    private JLabel buildLabel() {
        JLabel bpLabel = new JLabel();
        bpLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bpLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
        return bpLabel;
    }

    private Color getLabelForegroundColor(BreakPoint breakPoint) {
        return breakPoint.isPostponed() ? Color.RED : Color.BLACK;
    }

    private JLabel findLabelForBreakPoint(BreakPoint breakPoint) {
        String bpTimeValue = breakPoint.getTimeValue();
        return Arrays.stream(getComponents())
                .map(c -> (JLabel) c)
                .filter(bpLabel -> bpLabel.getText().equals(bpTimeValue))
                .findFirst()
                .orElse(null);
    }
}
