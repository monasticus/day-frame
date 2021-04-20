package com.arch.dayframe.testutils.state;

import com.arch.dayframe.gui.dialog.BreakDialog;
import com.arch.dayframe.gui.panel.BreakPointsPanel;

public class GUIStateDTOFactory {

    public static BreakDialogStateDTO ofBreakDialog(BreakDialog breakDialog) {
        return new BreakDialogStateDTO(breakDialog);
    }

    public static BreakPointsPanelStateDTO ofBreakPointsPanel(BreakPointsPanel breakPointsPanel) {
        return new BreakPointsPanelStateDTO(breakPointsPanel);
    }
}
