package com.arch.dayframe.testutils.state;

import com.arch.dayframe.gui.DayFrameFrame;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class DayFrameFrameStateDTO extends GUIStateDTO {

    public LayoutManager layout;
    public List<Component> panels;
    public Point location;
    public boolean resizable;
    public int width;
    public int height;

    protected DayFrameFrameStateDTO(DayFrameFrame dayFrame) {
        layout = getLayoutManager(dayFrame);
        panels = getDayFrameComponents(dayFrame);
        location = getLocation(dayFrame);
        resizable = isResizable(dayFrame);
        width = getSizeWidth(dayFrame);
        height = getSizeHeight(dayFrame);
    }

    private List<Component> getDayFrameComponents(DayFrameFrame dayFrame){
        JRootPane jRootPane = (JRootPane) dayFrame.getComponent(0);
        JLayeredPane jLayeredPane = Arrays.stream(jRootPane.getComponents())
                .filter(c -> c instanceof JLayeredPane)
                .map(c -> (JLayeredPane) c)
                .findFirst().orElse(null);
        JPanel directParent = (JPanel) jLayeredPane.getComponent(0);
        return Arrays.stream(directParent.getComponents()).collect(Collectors.toList());
    }
}
