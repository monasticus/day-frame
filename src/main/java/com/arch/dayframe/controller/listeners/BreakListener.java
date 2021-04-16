package com.arch.dayframe.controller.listeners;

import com.arch.dayframe.gui.dialog.BreakDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class BreakListener implements ActionListener {

    public static final String WINDOWS_LOCK_COMMAND = "C:\\Windows\\System32\\rundll32.exe user32.dll,LockWorkStation";

    private final BreakDialog dialog;
    private final boolean lockScreen;

    public BreakListener(BreakDialog dialog) {
        this(dialog, true);
    }

    public BreakListener(BreakDialog dialog, boolean lockScreen) {
        this.dialog = dialog;
        this.lockScreen = lockScreen;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dialog.setVisible(false);
        dialog.postponeList.setSelectedIndex(0);
        if (lockScreen)
            lockScreen();
    }

    private void lockScreen() {
        try {
            Runtime.getRuntime().exec(WINDOWS_LOCK_COMMAND);
        } catch (IOException ignore) {}
    }
}
