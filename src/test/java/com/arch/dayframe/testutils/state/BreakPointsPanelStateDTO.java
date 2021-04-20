package com.arch.dayframe.testutils.state;

import com.arch.dayframe.gui.panel.BreakPointsPanel;

import java.awt.*;
import java.util.List;

public class BreakPointsPanelStateDTO extends GUIStateDTO {

    public LayoutManager layout;
    public Color background;
    public List<Component> labels;
    public int preferredSizeWidth;
    public int preferredSizeHeight;
    public int componentsCount;

    public Color firstLabelForeground;
    public String firstLabelFontFamily;
    public int firstLabelFontStyle;
    public int firstLabelFontSize;
    public float firstLabelAlignmentX;
    public float firstLabelAlignmentY;

    protected BreakPointsPanelStateDTO(BreakPointsPanel breakPointsPanel) {
        layout = getLayoutManager(breakPointsPanel);
        background = getBackground(breakPointsPanel);
        labels = getComponents(breakPointsPanel);
        preferredSizeWidth = getPreferredSizeWidth(breakPointsPanel);
        preferredSizeHeight = getPreferredSizeHeight(breakPointsPanel);
        componentsCount = getComponentCount(breakPointsPanel);

        Component firstLabel = labels.size() > 0 ? labels.get(0) : null;
        firstLabelForeground = firstLabel == null ? null : getForeground(firstLabel);
        firstLabelFontFamily = firstLabel == null ? null : getFontFamily(firstLabel);
        firstLabelFontStyle = firstLabel == null ? -1 : getFontStyle(firstLabel);
        firstLabelFontSize = firstLabel == null ? -1 : getFontSize(firstLabel);
        firstLabelAlignmentX = firstLabel == null ? -1 : getAlignmentX(firstLabel);
        firstLabelAlignmentY = firstLabel == null ? -1 : getAlignmentY(firstLabel);
    }
}
