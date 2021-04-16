package com.arch.dayframe.controller.listeners;

import com.arch.dayframe.gui.DayFrameFrame;
import com.arch.dayframe.gui.dialog.BreakDialog;
import org.junit.jupiter.api.*;

import java.awt.event.ActionEvent;
import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("BreakListener")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BreakListenerTest {

    private static final boolean DISABLED_LOCK_SCREEN = false;

    private static ActionEvent mockEvent;
    private static BreakDialog breakDialog;

    @BeforeEach
    void setUp() {
        breakDialog = new BreakDialog(new DayFrameFrame());
        mockEvent = new ActionEvent(-1, -1, "");
        simulateBreakPointsTimerListenerAction();
    }

    /**
     * BreakListener performs action only after BreakPointsTimerListener.
     * BreakPointsTimerListener action is performed whenever current time will be the time of next break point.
     * It shows BreakDialog having 'OK' button. The BreakListener performs action on the button click.
     * In the simulation we do not complete dialog view, since BreakListener do not use it.
     */
    private static void simulateBreakPointsTimerListenerAction() {
        breakDialog.setVisible(true);
    }

    @Disabled // Executing the test will lock your screen
    @Test @Order(1)
    @DisplayName("Visibility Test with lock screen enabled (by default)")
    void testBreakDialogVisibilityWithLockScreenEnabled() {
        BreakListener listener = new BreakListener(breakDialog);
        testBreakDialogVisibility(listener);
    }

    @Test @Order(2)
    @DisplayName("Visibility Test with lock screen disabled")
    void testBreakDialogVisibilityWithLockScreenDisabled() {
        BreakListener listener = new BreakListener(breakDialog, DISABLED_LOCK_SCREEN);
        testBreakDialogVisibility(listener);
    }

    @Disabled // Executing the test will lock your screen
    @Test @Order(3)
    @DisplayName("Postponement List Reset Test with lock screen enabled (by default)")
    void testBreakDialogPostponementListResetWithLockScreenEnabled() {
        BreakListener listener = new BreakListener(breakDialog);
        testBreakDialogPostponementListReset(listener);
    }

    @Test @Order(4)
    @DisplayName("Postponement List Reset Test with lock screen disabled")
    void testBreakDialogPostponementListResetWithLockScreenDisabled() {
        BreakListener listener = new BreakListener(breakDialog, DISABLED_LOCK_SCREEN);
        testBreakDialogPostponementListReset(listener);
    }

    void testBreakDialogVisibility(BreakListener listener) {
        boolean isVisibleBefore = breakDialog.isVisible();
        listener.actionPerformed(mockEvent);
        boolean isVisibleAfter = breakDialog.isVisible();

        assertTrue(isVisibleBefore);
        assertFalse(isVisibleAfter);
    }

    void testBreakDialogPostponementListReset(BreakListener listener) {
        selectNonDefaultPostponementListItem(15);

        String postponementMinutesBefore = getPostponementListSelectedItem();
        listener.actionPerformed(mockEvent);
        String postponementMinutesAfter = getPostponementListSelectedItem();

        assertEquals("15", postponementMinutesBefore);
        assertEquals("5", postponementMinutesAfter);
    }

    private void selectNonDefaultPostponementListItem(int postponementMinutes) {
        if (postponementMinutes == 10)
            breakDialog.postponeList.setSelectedIndex(1);
        else if (postponementMinutes == 15)
            breakDialog.postponeList.setSelectedIndex(2);
        else
            throw new InvalidParameterException("You can choose postponement minutes only 10 and 15 (since 5 is default)");
    }

    private String getPostponementListSelectedItem() {
        return String.valueOf(breakDialog.postponeList.getSelectedItem());
    }
}