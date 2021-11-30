package com.arch.dayframe.gui.panel;

import com.arch.dayframe.gui.utils.GBC;

import javax.swing.*;
import java.awt.*;

public class ManagementPanel extends JPanel {

    private static final String DEFAULT_TIME_FIELED_VALUE = "00:00";
    private static final String DEFAULT_MESSAGE_FIELD_VALUE = "";

    private JPanel leftPanel;
    private JPanel centerPanel;
    private JPanel rightPanel;

    public JTextField breakPointTimeField;
    public JTextField breakPointMessageField;
    public JButton addButton;

    public ManagementPanel() {
        setGeneralView();
        buildPanels();
    }

    public void resetValues(){
        breakPointTimeField.setText(DEFAULT_TIME_FIELED_VALUE);
        breakPointMessageField.setText(DEFAULT_MESSAGE_FIELD_VALUE);
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

    private void buildPanels() {
        buildLeftPanel();
        buildCenterPanel();
        buildRightPanel();
    }

    private void buildCenterPanel() {
        centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
//        centerPanel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        centerPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.BLACK));
        centerPanel.setForeground(Color.BLACK);
        add(centerPanel);
    }

    private void buildLeftPanel() {
        leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        buildSidePanel(leftPanel);
    }

    private void buildRightPanel() {
        GridBagLayout rightPanelLayout = new GridBagLayout();
        rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(rightPanelLayout);

        buildSidePanel(rightPanel);
        addBreakPointsManagement(rightPanel);
    }

    private void buildSidePanel(JPanel sidePanel) {
        sidePanel.setForeground(Color.BLACK);
//        sidePanel.setHorizontalAlignment(SwingConstants.CENTER);
        sidePanel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
        sidePanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
        add(sidePanel);
    }

    private void addBreakPointsManagement(JPanel rightPanel) {
        breakPointTimeField = new JTextField(DEFAULT_TIME_FIELED_VALUE, 3);
        breakPointMessageField = new JTextField(DEFAULT_MESSAGE_FIELD_VALUE, 15);
        addButton = new JButton();
        addButton.setBackground(Color.ORANGE);
        addButton.setText("ADD");
        addButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        addButton.setPreferredSize(new Dimension(205, 19));

        GridBagConstraints breakPointTimeGBC = new GBC(0, 0, 1, 1);
        GridBagConstraints breakPointDescriptionGBC = new GBC(1, 0, 1, 1);
        GridBagConstraints addButtonGBC = new GBC(0, 1, 2, 1);
        rightPanel.add(breakPointTimeField, breakPointTimeGBC);
        rightPanel.add(breakPointMessageField, breakPointDescriptionGBC);
        rightPanel.add(addButton, addButtonGBC);
    }
}
