package com.arch.dayframe.testutils.state;

import com.arch.dayframe.gui.DayFrameFrame;
import com.arch.dayframe.gui.dialog.BreakDialog;
import com.arch.dayframe.gui.panel.BreakPointsPanel;
import com.arch.dayframe.gui.panel.TimePanel;

public class GUIStateDTOFactory {

    public static BreakDialogStateDTO ofBreakDialog(BreakDialog breakDialog) {
        return new BreakDialogStateDTO(breakDialog);
    }

    public static BreakPointsPanelStateDTO ofBreakPointsPanel(BreakPointsPanel breakPointsPanel) {
        return new BreakPointsPanelStateDTO(breakPointsPanel);
    }

    public static TimePanelStateDTO ofTimePanel(TimePanel timePanel) {
        return new TimePanelStateDTO(timePanel);
    }

    public static DayFrameFrameStateDTO ofDayFrameFrame(DayFrameFrame dayFrame) {
        return new DayFrameFrameStateDTO(dayFrame);
    }
}
