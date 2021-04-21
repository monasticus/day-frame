package com.arch.dayframe.controller.listeners;

import com.arch.dayframe.gui.DayFrameFrame;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("DayFrameWindowListener")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DayFrameWindowListenerTest {

    private static DayFrameFrame dayFrame;
    private static DayFrameWindowListener listener;
    private static WindowEvent windowClosingEvent;

    @BeforeEach
    void setUp() {
        resetPreferences();
        dayFrame = new DayFrameFrame();
        listener = new DayFrameWindowListener(dayFrame);
        windowClosingEvent = new WindowEvent(dayFrame, WindowEvent.WINDOW_CLOSING);
    }

    @AfterAll
    static void afterAll() {
        resetPreferences();
    }

    private static void resetPreferences() {
        Preferences preferences = Preferences.userNodeForPackage(DayFrameFrame.class);
        preferences.remove("left");
        preferences.remove("top");
    }

    @Test @Order(1)
    @DisplayName("01. DayFrameWindowListener is an instance of WindowAdapter")
    void testTypeOfListener() {
        assertTrue(listener instanceof WindowAdapter);
    }

    @Test @Order(2)
    @DisplayName("02. windowClosing()")
    void testWindowClosing() {
        Preferences preferences = Preferences.userNodeForPackage(DayFrameFrame.class);

        dayFrame.setLocation(new Point(1, 10));
        listener.windowClosing(windowClosingEvent);
        int leftAfterFirst = preferences.getInt("left", -1);
        int topAfterFirst = preferences.getInt("top", -1);

        dayFrame.setLocation(new Point(55, 550));
        listener.windowClosing(windowClosingEvent);
        int leftAfterSecond = preferences.getInt("left", -1);
        int topAfterSecond = preferences.getInt("top", -1);

        assertEquals(1, leftAfterFirst);
        assertEquals(10, topAfterFirst);
        assertEquals(55, leftAfterSecond);
        assertEquals(550, topAfterSecond);
    }
}
