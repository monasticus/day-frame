package com.arch.dayframe.controller.listeners;

import com.arch.dayframe.gui.DayFrameFrame;
import com.arch.dayframe.gui.dialog.BreakDialog;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static org.junit.jupiter.api.Assertions.*;

@Tag("BreakDialogWindowListener")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BreakDialogWindowListenerTest {

    private static BreakDialog dialog;
    private static BreakDialogWindowListener listener;
    private static WindowEvent windowLostFocusEvent;
    private static Robot robot;

    @BeforeAll
    static void beforeAll() throws AWTException {
        DayFrameFrame dayFrame = new DayFrameFrame();
        dayFrame.setVisible(true);
        dialog = new BreakDialog(dayFrame);

        listener = new BreakDialogWindowListener(dialog);
        windowLostFocusEvent = new WindowEvent(dialog, WindowEvent.WINDOW_LOST_FOCUS);
        robot = new Robot();
    }

    @Test @Order(1)
    @DisplayName("01. BreakDialogWindowListener is an instance of WindowAdapter")
    void testTypeOfListener() {
        assertTrue(listener instanceof WindowAdapter);
    }

    @Test @Order(2)
    @DisplayName("02. windowLostFocus() - when dialog is not visible")
    void testWindowLostFocusWhenInvisible() {
        robot.mouseMove(1, 1);

        Point mousePositionBefore = MouseInfo.getPointerInfo().getLocation();
        boolean dialogFocusBefore = dialog.isFocused();

        listener.windowLostFocus(windowLostFocusEvent);

        Point mousePositionAfter = MouseInfo.getPointerInfo().getLocation();
        boolean dialogFocusAfter = dialog.isFocused();

        assertEquals(mousePositionBefore,mousePositionAfter);
        assertFalse(dialogFocusBefore);
        assertFalse(dialogFocusAfter);
    }

    @Test @Order(3)
    @DisplayName("03. windowLostFocus() - when dialog is visible")
    void testWindowLostFocusWhenVisible() {
        dialog.setVisible(true);
        robot.mouseMove(1, 1);

        Point mousePositionBefore = MouseInfo.getPointerInfo().getLocation();
        boolean dialogFocusBefore = dialog.isFocused();

        listener.windowLostFocus(windowLostFocusEvent);

        Point mousePositionAfter = MouseInfo.getPointerInfo().getLocation();
        boolean dialogFocusAfter = dialog.isFocused();

        Point expectedMousePositionBefore = new Point(1, 1);
        Point expectedMousePositionAfter = getDialogCenterPoint();

        assertNotEquals(mousePositionBefore, mousePositionAfter);
        assertEquals(expectedMousePositionBefore, mousePositionBefore);
        assertEquals(expectedMousePositionAfter, mousePositionAfter);
        assertFalse(dialogFocusBefore);
        assertTrue(dialogFocusAfter);
    }

    private static Point getDialogCenterPoint(){
        Dimension size = dialog.getSize();
        Point locationOnScreen = dialog.getLocationOnScreen();
        int x = locationOnScreen.x + (size.width / 2);
        int y = locationOnScreen.y + (size.height / 2);
        return new Point(x, y);
    }
}
