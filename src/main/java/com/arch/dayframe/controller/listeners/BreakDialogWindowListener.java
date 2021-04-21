package com.arch.dayframe.controller.listeners;

import com.arch.dayframe.gui.dialog.BreakDialog;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BreakDialogWindowListener extends WindowAdapter {

    private final BreakDialog dialog;

    public BreakDialogWindowListener(BreakDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void windowLostFocus(WindowEvent e) {
        if (dialog.isVisible()) {
            dialog.requestFocus();
            moveCursorToDialogCenterPoint();
        }
    }

    private void moveCursorToDialogCenterPoint() {
        Point dialogCenterPoint = getDialogCenterPoint();
        moveCursorToPoint(dialogCenterPoint);
    }

    private Point getDialogCenterPoint() {
        Dimension dialogSize = dialog.getSize();
        Point dialogLocationOnScreen = dialog.getLocationOnScreen();
        int x = dialogLocationOnScreen.x + (dialogSize.width / 2);
        int y = dialogLocationOnScreen.y + (dialogSize.height / 2);
        return new Point(x, y);
    }

    private void moveCursorToPoint(Point point) {
        try {
            new Robot().mouseMove(point.x, point.y);
        } catch (AWTException ignore) {}
    }
}
