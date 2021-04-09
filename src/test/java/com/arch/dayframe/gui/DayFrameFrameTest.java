package com.arch.dayframe.gui;

import com.arch.dayframe.gui.panel.BreakPointsPanel;
import com.arch.dayframe.gui.panel.TimePanel;
import com.arch.dayframe.model.bp.BreakPoint;
import com.arch.dayframe.model.bp.BreakPointFactory;
import org.junit.jupiter.api.*;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Tag("DayFrameFrame")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DayFrameFrameTest {

    private static int DEFAULT_WIDTH;
    private static int DEFAULT_HEIGHT;
    private static int DEFAULT_X;
    private static int DEFAULT_Y;

    private static DayFrameFrame dayFrame;

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
        Dimension dayFrameSize = dayFrame.getSize();
        assertEquals(DEFAULT_WIDTH, dayFrameSize.width);
        assertEquals(DEFAULT_HEIGHT, dayFrameSize.height);
    }

    @Test @Order(2)
    @DisplayName("02. Resizable Test")
    void testResizable() {
        assertFalse(dayFrame.isResizable());
    }

    @Test @Order(3)
    @DisplayName("03. Default Location Test")
    void testLocationByDefault() {
        Point dayFrameLocation = dayFrame.getLocation();
        assertEquals(DEFAULT_X, dayFrameLocation.x);
        assertEquals(DEFAULT_Y, dayFrameLocation.y);
    }

    @Test @Order(4)
    @DisplayName("04. Default Location Test - for new instance, after moving first one and saving")
    void testNewInstanceLocationAfterMovingFirstInstance() {
        dayFrame.setLocation(1, 1);
        dayFrame.saveLocation();
        DayFrameFrame newDayFrame = new DayFrameFrame();

        Point newDayFrameLocation = newDayFrame.getLocation();
        assertEquals(1, newDayFrameLocation.x);
        assertEquals(1, newDayFrameLocation.y);
    }

    @Test @Order(5)
    @DisplayName("05. Default Layout Test")
    void testDefaultLayout() {
        LayoutManager layout = dayFrame.getLayout();
        assertTrue(layout instanceof BorderLayout);
    }

    @Test @Order(6)
    @DisplayName("06. Components Tree Test")
    void testComponents() {
        int dayFrameComponentCount = dayFrame.getComponentCount();
        JRootPane jRootPane = (JRootPane) dayFrame.getComponent(0);

        int jRootPaneComponentCount = jRootPane.getComponentCount();
        JLayeredPane jLayeredPane = Arrays.stream(jRootPane.getComponents()).filter(c -> c instanceof JLayeredPane).map(c -> (JLayeredPane) c).findFirst().orElse(null);

        int jLayeredPaneComponentCount = jLayeredPane.getComponentCount();
        JPanel directParent = (JPanel) jLayeredPane.getComponent(0);

        int directParentComponentCount = directParent.getComponentCount();
        List<Component> components = Arrays.stream(directParent.getComponents()).collect(Collectors.toList());
        long timePanelsCount = components.stream().filter(c -> c instanceof TimePanel).count();
        long breakPointPanelsCount = components.stream().filter(c -> c instanceof BreakPointsPanel).count();

        assertEquals(1, dayFrameComponentCount);
        assertEquals(2, jRootPaneComponentCount);
        assertEquals(1, jLayeredPaneComponentCount);
        assertEquals(5, directParentComponentCount);
        assertEquals(1L, timePanelsCount);
        assertEquals(1L, breakPointPanelsCount);
    }

    @Test @Order(7)
    @DisplayName("07. setClockValue()")
    void testSetClockValue() {
        JLabel clockLabel = (JLabel) dayFrame.northPanel.getComponent(1);

        String textBefore = clockLabel.getText();
        dayFrame.setClockValue("23:59:59.9");
        String textAfter = clockLabel.getText();

        assertEquals("", textBefore);
        assertEquals("23:59:59.9", textAfter);
    }

    @Test @Order(8)
    @DisplayName("08. setRecentBreakPoint()")
    void testSetRecentBreakPoint() {
        JLabel recentBreakPointLabel = (JLabel) dayFrame.northPanel.getComponent(0);
        BreakPoint breakPoint = BreakPointFactory.fromDescription("23:59 - message");

        String textBefore = recentBreakPointLabel.getText();
        dayFrame.setRecentBreakPoint(breakPoint);
        String textAfter = recentBreakPointLabel.getText();

        assertEquals("", textBefore);
        assertEquals("23:59", textAfter);
    }

    @Test @Order(9)
    @DisplayName("09. setNextBreakPoint()")
    void testSetNextBreakPoint() {
        JLabel nextBreakPointLabel = (JLabel) dayFrame.northPanel.getComponent(2);
        BreakPoint breakPoint = BreakPointFactory.fromDescription("23:59 - message");

        String textBefore = nextBreakPointLabel.getText();
        dayFrame.setNextBreakPoint(breakPoint);
        String textAfter = nextBreakPointLabel.getText();

        assertEquals("", textBefore);
        assertEquals("23:59", textAfter);
    }

    @Test @Order(10)
    @DisplayName("10. setBreakPointsList()")
    void testSetBreakPointsList() {
        List<BreakPoint> breakPoints = BreakPointFactory.fromDescriptions(List.of("23:56", "23:58 - message 2", "23:59", "23:57 - message"));

        int componentCountBefore = dayFrame.eastPanel.getComponentCount();
        dayFrame.setBreakPointsList(breakPoints);
        int componentCountAfter = dayFrame.eastPanel.getComponentCount();
        List<JLabel> breakComponents = Arrays.stream(dayFrame.eastPanel.getComponents()).map(c -> (JLabel) c).collect(Collectors.toList());

        assertEquals(0, componentCountBefore);
        assertEquals(4, componentCountAfter);
        assertEquals("23:56", breakComponents.get(0).getText());
        assertEquals("23:57", breakComponents.get(1).getText());
        assertEquals("23:58", breakComponents.get(2).getText());
        assertEquals("23:59", breakComponents.get(3).getText());
    }

    @Test @Order(11)
    @DisplayName("11. removeBreakPointFromList()")
    void testRemoveBreakPointFromList() {
        List<BreakPoint> breakPoints = BreakPointFactory.fromDescriptions(List.of("23:56", "23:58 - message 2", "23:59", "23:57 - message"));

        int componentCountBefore = dayFrame.eastPanel.getComponentCount();
        dayFrame.setBreakPointsList(breakPoints);
        int componentCountMiddle = dayFrame.eastPanel.getComponentCount();
        dayFrame.removeBreakPointFromList(breakPoints.get(2));
        int componentCountAfter = dayFrame.eastPanel.getComponentCount();
        List<JLabel> breakComponents = Arrays.stream(dayFrame.eastPanel.getComponents()).map(c -> (JLabel) c).collect(Collectors.toList());

        assertEquals(0, componentCountBefore);
        assertEquals(4, componentCountMiddle);
        assertEquals(3, componentCountAfter);
        assertEquals("23:56", breakComponents.get(0).getText());
        assertEquals("23:57", breakComponents.get(1).getText());
        assertEquals("23:59", breakComponents.get(2).getText());
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