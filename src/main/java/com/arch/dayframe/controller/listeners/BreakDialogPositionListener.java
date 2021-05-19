package com.arch.dayframe.controller.listeners;

import com.arch.dayframe.gui.dialog.BreakDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

public class BreakDialogPositionListener implements ActionListener {

    private final BreakDialog dialog;

    public BreakDialogPositionListener(BreakDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (dialog.isMoved()) {
            dialog.resetLocation();
            dialog.requestFocus();
            moveCursorToDialogAndClickIt();
        }
    }

    private void moveCursorToDialogAndClickIt() {
        Point dialogCenterPoint = getDialogCenterPoint();
        moveCursorToPointAndClickIt(dialogCenterPoint);
    }

    private void moveCursorToPointAndClickIt(Point point) {
        try {
            Robot robot = new Robot();
            robot.mouseMove(point.x, point.y);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        } catch (AWTException ignore) {}
    }

    private Point getDialogCenterPoint() {
        Dimension dialogSize = dialog.getSize();
        Point dialogLocationOnScreen = dialog.getLocationOnScreen();
        int x = dialogLocationOnScreen.x + (dialogSize.width / 2);
        int y = dialogLocationOnScreen.y + (dialogSize.height / 2);
        return new Point(x, y);
    }
}
