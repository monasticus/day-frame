package com.arch.dayframe.controller.listeners;

import com.arch.dayframe.gui.DayFrameFrame;
import com.arch.dayframe.gui.panel.TimePanel;
import org.junit.jupiter.api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Tag("ClockListener")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClockListenerTest {

    private static JLabel dayFrameTimeLabel;
    private static ClockListener clockListener;
    private static ActionEvent mockEvent;

    @BeforeEach
    void setUp() {
        DayFrameFrame dayFrame = new DayFrameFrame();
        dayFrameTimeLabel = getTimeLabel(dayFrame);
        clockListener = new ClockListener(dayFrame);
        mockEvent = new ActionEvent(-1, -1, "");
    }

    @Test @Order(1)
    void testActionPerformedOnce() {
        String textBefore = dayFrameTimeLabel.getText();

        String currentTime = getCurrentTimeWithoutMillis();
        clockListener.actionPerformed(mockEvent);
        String textAfter = dayFrameTimeLabel.getText();

        assertNotEquals(textBefore, textAfter);
        assertEquals("", textBefore);
        assertTrue(textAfter.startsWith(currentTime));
    }

    @Disabled
    @Test @Order(2)
    void testActionPerformedMoreThanOnce() throws InterruptedException {
        String textBefore = dayFrameTimeLabel.getText();

        String middleTime = getCurrentTimeWithoutMillis();
        clockListener.actionPerformed(mockEvent);
        String textMiddle = dayFrameTimeLabel.getText();

        Thread.sleep(1500);

        String afterTime = getCurrentTimeWithoutMillis();
        clockListener.actionPerformed(mockEvent);
        String textAfter = dayFrameTimeLabel.getText();

        assertNotEquals(textBefore, textMiddle);
        assertNotEquals(textMiddle, textAfter);
        assertNotEquals(textAfter, textBefore);
        assertEquals("", textBefore);
        assertTrue(textMiddle.startsWith(middleTime));
        assertTrue(textAfter.startsWith(afterTime));
    }

    private static JLabel getTimeLabel(DayFrameFrame dayFrame) {
        JRootPane jRootPane = (JRootPane) dayFrame.getComponent(0);
        JLayeredPane jLayeredPane = Arrays.stream(jRootPane.getComponents()).filter(c -> c instanceof JLayeredPane).map(c -> (JLayeredPane) c).findFirst().orElse(null);
        JPanel borderPanelsParent = (JPanel) jLayeredPane.getComponent(0);
        List<Component> borderPanels = Arrays.stream(borderPanelsParent.getComponents()).collect(Collectors.toList());
        JPanel timePanel = (JPanel) borderPanels.stream().filter(c -> c instanceof TimePanel).findFirst().get();
        return (JLabel) timePanel.getComponent(1);
    }

    private String getCurrentTimeWithoutMillis() {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minutes = currentTime.get(Calendar.MINUTE);
        int second = currentTime.get(Calendar.SECOND);
        return String.format("%02d:%02d:%02d", hour, minutes, second);
    }
}