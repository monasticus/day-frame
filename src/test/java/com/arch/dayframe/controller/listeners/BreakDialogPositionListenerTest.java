package com.arch.dayframe.controller.listeners;

import com.arch.dayframe.gui.DayFrameFrame;
import com.arch.dayframe.gui.dialog.BreakDialog;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Tag("BreakDialogPositionListener")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BreakDialogPositionListenerTest {

    private static BreakDialog dialog;
    private static BreakDialogPositionListener listener;
    private static ActionEvent timerEvent;
    private static Robot robot;

    @BeforeEach
    void setUp() throws AWTException {
        DayFrameFrame dayFrame = new DayFrameFrame();
        dayFrame.setVisible(true);
        dialog = new BreakDialog(dayFrame);

        listener = new BreakDialogPositionListener(dialog);
        timerEvent = new ActionEvent(dialog, 0, "");
        robot = new Robot();
    }


    @AfterEach
    void tearDown() {
        dialog.setVisible(false);
    }

    @Test @Order(1)
    @DisplayName("01. BreakDialogPositionListener is an instance of ActionListener")
    void testTypeOfListener() {
        assertTrue(listener instanceof ActionListener);
    }

    @Test @Order(2)
    @DisplayName("02. when dialog is not visible")
    void testWhenInvisible() {
        robot.mouseMove(1, 1);

        Point mousePositionBefore = MouseInfo.getPointerInfo().getLocation();
        Point dialogLocationBefore = dialog.getLocation();
        boolean dialogFocusBefore = dialog.isFocused();

        listener.actionPerformed(timerEvent);

        Point mousePositionAfter = MouseInfo.getPointerInfo().getLocation();
        Point dialogLocationAfter = dialog.getLocation();
        boolean dialogFocusAfter = dialog.isFocused();

        assertEquals(mousePositionBefore,mousePositionAfter);
        assertEquals(dialogLocationBefore, dialogLocationAfter);
        assertFalse(dialogFocusBefore);
        assertFalse(dialogFocusAfter);
    }

    @Test @Order(3)
    @DisplayName("03. when dialog is visible but not moved")
    void testWhenVisibleButNotMoved() {
        dialog.setVisible(true);
        dialog.resetLocation();
        robot.mouseMove(1, 1);

        Point mousePositionBefore = MouseInfo.getPointerInfo().getLocation();
        Point dialogLocationBefore = dialog.getLocation();
        boolean dialogFocusBefore = dialog.isFocused();

        listener.actionPerformed(timerEvent);

        Point mousePositionAfter = MouseInfo.getPointerInfo().getLocation();
        Point dialogLocationAfter = dialog.getLocation();
        boolean dialogFocusAfter = dialog.isFocused();

        assertEquals(mousePositionBefore, mousePositionAfter);
        assertEquals(dialogLocationBefore, dialogLocationAfter);
        assertFalse(dialogFocusBefore);
        assertFalse(dialogFocusAfter);
    }

    @Test @Order(4)
    @DisplayName("04. when dialog is visible and moved")
    void testWhenVisibleAndMoved() {
        dialog.setVisible(true);
        dialog.setLocation(3, 3);
        robot.mouseMove(1, 1);

        Point mousePositionBefore = MouseInfo.getPointerInfo().getLocation();
        Point dialogLocationBefore = dialog.getLocation();
        boolean dialogFocusBefore = dialog.isFocused();

        listener.actionPerformed(timerEvent);

        Point mousePositionAfter = MouseInfo.getPointerInfo().getLocation();
        Point dialogLocationAfter = dialog.getLocation();
        boolean dialogFocusAfter = dialog.isFocused();

        Point expectedMousePositionBefore = new Point(1, 1);
        Point expectedMousePositionAfter = getDialogCenterPoint();
        Point expectedDialogLocationBefore = new Point(3, 3);
        Point expectedDialogLocationAfter = getDefaultDialogLocation();

        assertNotEquals(mousePositionBefore, mousePositionAfter);
        assertNotEquals(expectedDialogLocationBefore, expectedDialogLocationAfter);
        assertEquals(expectedMousePositionBefore, mousePositionBefore);
        assertEquals(expectedMousePositionAfter, mousePositionAfter);
        assertEquals(expectedDialogLocationBefore, dialogLocationBefore);
        assertEquals(expectedDialogLocationAfter, dialogLocationAfter);
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

    private static Point getDefaultDialogLocation() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (screenSize.getWidth() / 2 - dialog.getWidth() / 2);
        int y = (int) (screenSize.getHeight() / 2 - dialog.getHeight() / 2);
        return new Point(x, y);
    }
}
