package com.arch.dayframe.gui.utils;

import java.awt.*;
import java.util.List;

public class GBC extends GridBagConstraints {

    private static final List<Integer> ANCHOR_VALUES = List.of(
            // ABSOLUTE VALUES
            NORTHWEST, NORTH, NORTHEAST,
            WEST, CENTER, EAST,
            SOUTHWEST, SOUTH, SOUTHEAST,

            // ORIENTATION VALUES
            FIRST_LINE_START, LINE_START, FIRST_LINE_END,
            PAGE_START, PAGE_END,
            LAST_LINE_START, LINE_END, LAST_LINE_END
    );

    private static final List<Integer> FILL_VALUES = List.of(
            NONE, HORIZONTAL, VERTICAL, BOTH
    );

    public GBC(int gridx, int gridy) {
        this(gridx, gridy, 0, 0, 0, 0);
    }

    public GBC(int gridx, int gridy, int gridwidth, int gridheight) {
        this(gridx, gridy, gridwidth, gridheight, 0, 0);
    }

    public GBC(int gridx, int gridy, int gridwidth, int gridheight, double weightx, double weighty) {
        this.gridx = gridx;
        this.gridy = gridy;
        this.gridwidth = gridwidth;
        this.gridheight = gridheight;
        this.weightx = weightx;
        this.weighty = weighty;
    }

    public GBC setAnchor(int anchor){
        this.anchor = ANCHOR_VALUES.contains(anchor) ? anchor : CENTER;
        return this;
    }

    public GBC setFill(int fill){
        this.fill = FILL_VALUES.contains(fill) ? fill : NONE;
        return this;
    }

    public GBC setWeight(int weightx, int weighty){
        this.weightx = weightx;
        this.weighty = weighty;
        return this;
    }

    public GBC setInsets(int insets){
        return this.setInsets(insets, insets, insets, insets);
    }

    public GBC setInsets(int top, int left, int bottom, int right){
        this.insets = new Insets(top, left, bottom, right);
        return this;
    }

    public GBC setIpad(int ipad){
        return this.setIpad(ipad, ipad);
    }

    public GBC setIpad(int ipadx, int ipady){
        this.ipadx = ipadx;
        this.ipady = ipady;
        return this;
    }
}
