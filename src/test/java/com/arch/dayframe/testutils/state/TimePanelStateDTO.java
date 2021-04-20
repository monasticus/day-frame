package com.arch.dayframe.testutils.state;

import com.arch.dayframe.gui.panel.TimePanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.List;

public class TimePanelStateDTO extends GUIStateDTO {

    public LayoutManager layout;
    public Color background;
    public List<Component> labels;
    public int preferredSizeWidth;
    public int preferredSizeHeight;
    public int componentsCount;

    public Color leftLabelForeground;
    public String leftLabelText;
    public String leftLabelFontFamily;
    public int leftLabelFontStyle;
    public int leftLabelFontSize;
    public float leftLabelAlignmentX;
    public float leftLabelAlignmentY;
    public MatteBorder leftLabelBorder;

    public Color centerLabelForeground;
    public String centerLabelText;
    public String centerLabelFontFamily;
    public int centerLabelFontStyle;
    public int centerLabelFontSize;
    public float centerLabelAlignmentX;
    public float centerLabelAlignmentY;
    public MatteBorder centerLabelBorder;

    public Color rightLabelForeground;
    public String rightLabelText;
    public String rightLabelFontFamily;
    public int rightLabelFontStyle;
    public int rightLabelFontSize;
    public float rightLabelAlignmentX;
    public float rightLabelAlignmentY;
    public MatteBorder rightLabelBorder;

    protected TimePanelStateDTO(TimePanel timePanel) {
        layout = getLayoutManager(timePanel);
        background = getBackground(timePanel);
        labels = getComponents(timePanel);
        preferredSizeWidth = getPreferredSizeWidth(timePanel);
        preferredSizeHeight = getPreferredSizeHeight(timePanel);
        componentsCount = getComponentCount(timePanel);

        JLabel leftLabel = (JLabel) labels.get(0);
        leftLabelForeground = getForeground(leftLabel);
        leftLabelText = getText(leftLabel);
        leftLabelFontFamily = getFontFamily(leftLabel);
        leftLabelFontStyle = getFontStyle(leftLabel);
        leftLabelFontSize = getFontSize(leftLabel);
        leftLabelAlignmentX = getAlignmentX(leftLabel);
        leftLabelAlignmentY = getAlignmentY(leftLabel);
        leftLabelBorder = (MatteBorder) getBorder(leftLabel);

        JLabel centerLabel = (JLabel) labels.get(1);
        centerLabelForeground = getForeground(centerLabel);
        centerLabelText = getText(centerLabel);
        centerLabelFontFamily = getFontFamily(centerLabel);
        centerLabelFontStyle = getFontStyle(centerLabel);
        centerLabelFontSize = getFontSize(centerLabel);
        centerLabelAlignmentX = getAlignmentX(centerLabel);
        centerLabelAlignmentY = getAlignmentY(centerLabel);
        centerLabelBorder = (MatteBorder) getBorder(centerLabel);

        JLabel rightLabel = (JLabel) labels.get(2);
        rightLabelForeground = getForeground(rightLabel);
        rightLabelText = getText(rightLabel);
        rightLabelFontFamily = getFontFamily(rightLabel);
        rightLabelFontStyle = getFontStyle(rightLabel);
        rightLabelFontSize = getFontSize(rightLabel);
        rightLabelAlignmentX = getAlignmentX(rightLabel);
        rightLabelAlignmentY = getAlignmentY(rightLabel);
        rightLabelBorder = (MatteBorder) getBorder(rightLabel);
    }
}
