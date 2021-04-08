package com.arch.dayframe.gui.dialog;

import com.arch.dayframe.gui.utils.GBC;
import com.arch.dayframe.model.bp.BreakPoint;

import javax.swing.*;
import java.awt.*;

public class BreakDialog extends JDialog {

    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 375;
    private static final String DIALOG_TITLE = "Break";

    private JPanel mainPanel;
    private JLabel timeLabel;
    private JLabel messageLabel;
    public JComboBox<Integer> postponeList;
    private JPanel buttonPanel;
    public JButton okButton;
    public JButton postponeButton;

    public BreakDialog(Frame owner) {
        super(owner, DIALOG_TITLE, false);
        buildMainPanel();
        configureBreakDialog();
    }

    public boolean isMoved() {
        return isVisible() && !getLocation().equals(getDefaultLocation());
    }

    public void resetLocation(){
        setDefaultLocation();
        requestFocus();
    }

    public void completeDialog(BreakPoint breakPoint) {
        boolean enabled = !breakPoint.isPostponed();
        timeLabel.setText(breakPoint.getTimeValue());
        messageLabel.setText(breakPoint.getMessage());
        postponeList.setEnabled(enabled);
        postponeButton.setEnabled(enabled);
    }

    private void buildMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        buildTimeLabel();
        buildMessageLabel();
        buildPostponeList();
        buildButtonPanel();
        add(mainPanel);
    }

    private void buildTimeLabel() {
        timeLabel = new JLabel();
        timeLabel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.BLACK));
        timeLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        mainPanel.add(timeLabel, new GBC(0, 1, 1, 2).setInsets(2));
    }

    private void buildMessageLabel() {
        messageLabel = new JLabel();
        messageLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC + Font.BOLD, 17));
        mainPanel.add(messageLabel, new GBC(0, 3, 1, 1).setInsets(2));
    }

    private void buildPostponeList() {
        postponeList = new JComboBox<>(new Integer[]{5, 10, 15});
        postponeList.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        mainPanel.add(postponeList, new GBC(0, 4, 1, 1).setInsets(2));
    }

    private void buildButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        buildOkButton();
        buildPostponeButton();
        mainPanel.add(buttonPanel, new GBC(0, 5, 1, 1).setInsets(2));
    }

    private void buildOkButton() {
        okButton = new JButton();
        okButton.setText("OK");
        buttonPanel.add(okButton);
    }

    private void buildPostponeButton() {
        postponeButton = new JButton();
        postponeButton.setText("Postpone");
        buttonPanel.add(postponeButton);
    }

    private void configureBreakDialog() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setDefaultLocation();
        setResizable(false);
        getRootPane().setDefaultButton(okButton);
    }

    private void setDefaultLocation() {
        setLocation(getDefaultLocation());
    }

    private Point getDefaultLocation() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (screenSize.getWidth() / 2 - DEFAULT_WIDTH / 2);
        int y = (int) (screenSize.getHeight() / 2 - DEFAULT_HEIGHT / 2);
        return new Point(x, y);
    }
}
