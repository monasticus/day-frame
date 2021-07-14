package com.arch.dayframe.controller.utils;

import java.awt.*;
import java.awt.event.InputEvent;

public class DayFrameRobot {

    public static void moveCursorToComponent(Component component) {
        moveCursorToComponent(component, false);
    }

    public static void moveCursorToComponent(Component component, boolean click) {
        Point componentCenterPoint = getComponentCenterPoint(component);
        moveCursorToPoint(componentCenterPoint, click);
    }

    private static Point getComponentCenterPoint(Component component) {
        Dimension dialogSize = component.getSize();
        Point dialogLocationOnScreen = component.getLocationOnScreen();
        int x = dialogLocationOnScreen.x + (dialogSize.width / 2);
        int y = dialogLocationOnScreen.y + (dialogSize.height / 2);
        return new Point(x, y);
    }

    private static void moveCursorToPoint(Point point, boolean click) {
        try {
            Robot robot = new Robot();
            robot.mouseMove(point.x, point.y);
            if (click) {
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            }
        } catch (AWTException ignore) {}
    }
}
