package com.arch.dayframe.gui;

import com.arch.dayframe.gui.panel.BreakPointsPanel;
import com.arch.dayframe.gui.panel.TimePanel;
import com.arch.dayframe.model.bp.BreakPoint;
import com.arch.dayframe.model.bp.BreakPointFactory;
import com.arch.dayframe.testutils.state.BreakPointsPanelStateDTO;
import com.arch.dayframe.testutils.state.DayFrameFrameStateDTO;
import com.arch.dayframe.testutils.state.GUIStateDTOFactory;
import com.arch.dayframe.testutils.state.TimePanelStateDTO;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.util.List;
import java.util.prefs.Preferences;

import static org.junit.jupiter.api.Assertions.*;

@Tag("DayFrameFrame")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DayFrameFrameTest {

    private static int DEFAULT_WIDTH;
    private static int DEFAULT_HEIGHT;
    private static int DEFAULT_X;
    private static int DEFAULT_Y;

    private static DayFrameFrame dayFrame;
    private static DayFrameFrameStateDTO defaultDayFrameState;

    @BeforeAll
    static void beforeAll() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        DEFAULT_WIDTH = 700;
        DEFAULT_HEIGHT = 450;
        DEFAULT_X = (screenSize.width - DEFAULT_WIDTH) / 2;
        DEFAULT_Y = (screenSize.height - DEFAULT_HEIGHT) / 2;
    }

    @BeforeEach
    void setUp() {
        resetPreferences();
        dayFrame = new DayFrameFrame();
        defaultDayFrameState = GUIStateDTOFactory.ofDayFrameFrame(dayFrame);
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
    @DisplayName("01. Default Size Test")
    void testSize() {
        assertEquals(DEFAULT_WIDTH, defaultDayFrameState.width);
        assertEquals(DEFAULT_HEIGHT, defaultDayFrameState.height);
    }

    @Test @Order(2)
    @DisplayName("02. Resizable Test")
    void testResizable() {
        assertFalse(defaultDayFrameState.resizable);
    }

    @Test @Order(3)
    @DisplayName("03. Default Location Test")
    void testLocationByDefault() {
        Point expectedLocation = new Point(DEFAULT_X, DEFAULT_Y);
        assertEquals(expectedLocation, defaultDayFrameState.location);
    }

    @Test @Order(4)
    @DisplayName("04. Default Location Test - for new instance, after moving first one and saving")
    void testNewInstanceLocationAfterMovingFirstInstance() {
        Point customizedLocation = new Point(1, 1);
        dayFrame.setLocation(customizedLocation);
        dayFrame.saveLocation();

        DayFrameFrameStateDTO oldDayFrameState = GUIStateDTOFactory.ofDayFrameFrame(dayFrame);
        DayFrameFrameStateDTO newDayFrameState = GUIStateDTOFactory.ofDayFrameFrame(new DayFrameFrame());

        assertEquals(oldDayFrameState.location, newDayFrameState.location);
    }

    @Test @Order(5)
    @DisplayName("05. Default Layout Test")
    void testDefaultLayout() {
        assertTrue(defaultDayFrameState.layout instanceof BorderLayout);
    }

    @Test @Order(6)
    @DisplayName("06. General Components Test")
    void testComponents() {
        long timePanelsCount = defaultDayFrameState.panels.stream().filter(c -> c instanceof TimePanel).count();
        long breakPointPanelsCount = defaultDayFrameState.panels.stream().filter(c -> c instanceof BreakPointsPanel).count();

        assertEquals(1L, timePanelsCount);
        assertEquals(1L, breakPointPanelsCount);
    }

    @Test @Order(7)
    @DisplayName("07. setClockValue()")
    void testSetClockValue() {
        TimePanelStateDTO timePanelStateBefore = GUIStateDTOFactory.ofTimePanel(dayFrame.northPanel);
        dayFrame.setClockValue("23:59:59.9");
        TimePanelStateDTO timePanelStateAfter = GUIStateDTOFactory.ofTimePanel(dayFrame.northPanel);

        assertEquals("", timePanelStateBefore.centerLabelText);
        assertEquals("23:59:59.9", timePanelStateAfter.centerLabelText);
    }

    @Test @Order(8)
    @DisplayName("08. setRecentBreakPoint()")
    void testSetRecentBreakPoint() {
        BreakPoint breakPoint = BreakPointFactory.fromDescription("23:59 - message");

        TimePanelStateDTO timePanelStateBefore = GUIStateDTOFactory.ofTimePanel(dayFrame.northPanel);
        dayFrame.setRecentBreakPoint(breakPoint);
        TimePanelStateDTO timePanelStateAfter = GUIStateDTOFactory.ofTimePanel(dayFrame.northPanel);

        assertEquals("", timePanelStateBefore.leftLabelText);
        assertEquals("23:59", timePanelStateAfter.leftLabelText);
    }

    @Test @Order(9)
    @DisplayName("09. setNextBreakPoint()")
    void testSetNextBreakPoint() {
        BreakPoint breakPoint = BreakPointFactory.fromDescription("23:59 - message");

        TimePanelStateDTO timePanelStateBefore = GUIStateDTOFactory.ofTimePanel(dayFrame.northPanel);
        dayFrame.setNextBreakPoint(breakPoint);
        TimePanelStateDTO timePanelStateAfter = GUIStateDTOFactory.ofTimePanel(dayFrame.northPanel);

        assertEquals("", timePanelStateBefore.rightLabelText);
        assertEquals("23:59", timePanelStateAfter.rightLabelText);
    }

    @Test @Order(10)
    @DisplayName("10. setBreakPointsList()")
    void testSetBreakPointsList() {
        List<BreakPoint> breakPoints = BreakPointFactory.fromDescriptions(List.of("23:56", "23:58 - message 2", "23:59", "23:57 - message"));

        BreakPointsPanelStateDTO breakPointsPanelStateBefore = GUIStateDTOFactory.ofBreakPointsPanel(dayFrame.eastPanel);
        dayFrame.setBreakPointsList(breakPoints);
        BreakPointsPanelStateDTO breakPointsPanelStateAfter = GUIStateDTOFactory.ofBreakPointsPanel(dayFrame.eastPanel);

        assertEquals(0, breakPointsPanelStateBefore.componentsCount);
        assertEquals(4, breakPointsPanelStateAfter.componentsCount);
        assertEquals("23:56", breakPointsPanelStateAfter.labels.get(0).getText());
        assertEquals("23:57", breakPointsPanelStateAfter.labels.get(1).getText());
        assertEquals("23:58", breakPointsPanelStateAfter.labels.get(2).getText());
        assertEquals("23:59", breakPointsPanelStateAfter.labels.get(3).getText());
    }

    @Test @Order(11)
    @DisplayName("11. removeBreakPointFromList()")
    void testRemoveBreakPointFromList() {
        List<BreakPoint> breakPoints = BreakPointFactory.fromDescriptions(List.of("23:56", "23:58 - message 2", "23:59", "23:57 - message"));

        BreakPointsPanelStateDTO breakPointsPanelStateBefore = GUIStateDTOFactory.ofBreakPointsPanel(dayFrame.eastPanel);
        dayFrame.setBreakPointsList(breakPoints);
        BreakPointsPanelStateDTO breakPointsPanelStateMiddle = GUIStateDTOFactory.ofBreakPointsPanel(dayFrame.eastPanel);
        dayFrame.removeBreakPointFromList(breakPoints.get(2));
        BreakPointsPanelStateDTO breakPointsPanelStateAfter = GUIStateDTOFactory.ofBreakPointsPanel(dayFrame.eastPanel);

        assertEquals(0, breakPointsPanelStateBefore.componentsCount);
        assertEquals(4, breakPointsPanelStateMiddle.componentsCount);
        assertEquals(3, breakPointsPanelStateAfter.componentsCount);
        assertEquals("23:56", breakPointsPanelStateAfter.labels.get(0).getText());
        assertEquals("23:57", breakPointsPanelStateAfter.labels.get(1).getText());
        assertEquals("23:59", breakPointsPanelStateAfter.labels.get(2).getText());
    }

    @Test @Order(12)
    @DisplayName("12. saveLocation()")
    void testSaveLocation() {
        Preferences preferences = Preferences.userNodeForPackage(DayFrameFrame.class);

        int leftBefore = preferences.getInt("left", -1);
        int topBefore = preferences.getInt("top", -1);
        dayFrame.saveLocation();
        int leftMiddle = preferences.getInt("left", -1);
        int topMiddle = preferences.getInt("top", -1);
        dayFrame.setLocation(1, 1);
        dayFrame.saveLocation();
        int leftAfter = preferences.getInt("left", -1);
        int topAfter = preferences.getInt("top", -1);

        assertEquals(-1, leftBefore);
        assertEquals(-1, topBefore);
        assertEquals(DEFAULT_X, leftMiddle);
        assertEquals(DEFAULT_Y, topMiddle);
        assertEquals(1, leftAfter);
        assertEquals(1, topAfter);
    }
}