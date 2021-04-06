package com.arch.dayframe.gui.panel;

import com.arch.dayframe.model.bp.BreakPoint;
import com.arch.dayframe.model.bp.BreakPointFactory;
import org.junit.jupiter.api.*;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("BreakPointsPanel")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BreakPointsPanelTest {

    private static BreakPointsPanel defaultBreakPointsPanel;

    @BeforeAll
    static void beforeAll() {
        defaultBreakPointsPanel = new BreakPointsPanel();
    }

    @AfterEach
    void tearDown() {
        defaultBreakPointsPanel.cleanBreakPoints();
    }

    @Test @Order(1)
    @DisplayName("01. Default Background Test")
    void testDefaultBackground() {
        Color bgColor = defaultBreakPointsPanel.getBackground();
        assertEquals(Color.WHITE, bgColor);
    }

    @Test @Order(2)
    @DisplayName("02. Default Layout Test")
    void testDefaultLayout() {
        GridLayout layout = assertDoesNotThrow(() -> (GridLayout) defaultBreakPointsPanel.getLayout());
        assertEquals(10, layout.getRows());
        assertEquals(1, layout.getColumns());
    }

    @Test @Order(3)
    @DisplayName("03. Default Preferred Size Test")
    void testDefaultPreferredSize() {
        Dimension preferredSize = defaultBreakPointsPanel.getPreferredSize();
        assertEquals(130, preferredSize.width);
        assertEquals(0, preferredSize.height);
    }

    @Test @Order(4)
    @DisplayName("04. Default Component's Count Test")
    void testDefaultComponentsCount() {
        assertEquals(0, defaultBreakPointsPanel.getComponentCount());
    }

    @Test @Order(5)
    @DisplayName("05. BP Label's Alignment Test")
    void testBPLabelAlignment() {
        addRegularBPLabel();

        JLabel label = (JLabel) defaultBreakPointsPanel.getComponent(0);

        assertEquals(SwingConstants.CENTER, label.getAlignmentX());
    }

    @Test @Order(6)
    @DisplayName("06. BP Label's Font Test")
    void testBPLabelFont() {
        addRegularBPLabel();

        JLabel label = (JLabel) defaultBreakPointsPanel.getComponent(0);
        Font labelFont = label.getFont();

        assertEquals(Font.SANS_SERIF, labelFont.getFamily());
        assertEquals(Font.BOLD, labelFont.getStyle());
        assertEquals(17, labelFont.getSize());
    }

    @Test @Order(7)
    @DisplayName("07. BP Label's Foreground Color Test - for regular break point")
    void testBPLabelColorForRegularBreakPoint() {
        addRegularBPLabel();

        JLabel label = (JLabel) defaultBreakPointsPanel.getComponent(0);
        Color fgColor = label.getForeground();

        assertEquals(Color.BLACK, fgColor);
    }

    @Test @Order(8)
    @DisplayName("08. BP Label's Foreground Color Test - for postponed break point")
    void testBPLabelColorForPostponedBreakPoint() {
        addPostponedBPLabel();

        JLabel label = (JLabel) defaultBreakPointsPanel.getComponent(0);
        Color fgColor = label.getForeground();

        assertEquals(Color.RED, fgColor);
    }

    @Test @Order(9)
    @DisplayName("09. test addBreakPoints()")
    void testAddBreakPoints() {
        List<BreakPoint> breakPoints = getTestBreakPointList();

        defaultBreakPointsPanel.addBreakPoints(breakPoints);

        assertEquals(3, defaultBreakPointsPanel.getComponentCount());
        assertEquals(Color.BLACK, defaultBreakPointsPanel.getComponent(0).getForeground());
        assertEquals(Color.RED, defaultBreakPointsPanel.getComponent(1).getForeground());
        assertEquals(Color.BLACK, defaultBreakPointsPanel.getComponent(2).getForeground());
    }

    @Test @Order(10)
    @DisplayName("10. test addBreakPoints() used twice")
    void testAddBreakPointsTwice() {
        List<BreakPoint> breakPoints = getTestBreakPointList();

        int componentCountStart = defaultBreakPointsPanel.getComponentCount();
        defaultBreakPointsPanel.addBreakPoints(breakPoints);
        int componentCountSAfterFirst = defaultBreakPointsPanel.getComponentCount();
        defaultBreakPointsPanel.addBreakPoints(breakPoints);
        int componentCountSAfterSecond = defaultBreakPointsPanel.getComponentCount();

        assertEquals(0, componentCountStart);
        assertEquals(3, componentCountSAfterFirst);
        assertEquals(6, componentCountSAfterSecond);
    }

    @Test @Order(11)
    @DisplayName("11. test cleanBreakPoints() with some labels")
    void testCleanBreakPointsWithSomeLabels() {
        List<BreakPoint> breakPoints = getTestBreakPointList();
        defaultBreakPointsPanel.addBreakPoints(breakPoints);

        int componentCountBefore = defaultBreakPointsPanel.getComponentCount();
        defaultBreakPointsPanel.cleanBreakPoints();
        int componentCountAfter = defaultBreakPointsPanel.getComponentCount();

        assertEquals(3, componentCountBefore);
        assertEquals(0, componentCountAfter);
    }

    @Test @Order(12)
    @DisplayName("12. test cleanBreakPoints() with no labels")
    void testCleanBreakPointsWithNoLabels() {
        int componentCountBefore = defaultBreakPointsPanel.getComponentCount();
        assertDoesNotThrow(() -> defaultBreakPointsPanel.cleanBreakPoints());
        int componentCountAfter = defaultBreakPointsPanel.getComponentCount();

        assertEquals(0, componentCountBefore);
        assertEquals(0, componentCountAfter);
    }

    @Test @Order(13)
    @DisplayName("13. test removeBreakPoint()")
    void testRemoveBreakPoint() {
        List<BreakPoint> breakPoints = getTestBreakPointList();
        BreakPoint postponedBreakPoint = breakPoints.get(1);
        defaultBreakPointsPanel.addBreakPoints(breakPoints);

        int componentCountBefore = defaultBreakPointsPanel.getComponentCount();
        defaultBreakPointsPanel.removeBreakPoint(postponedBreakPoint);
        int componentCountAfter = defaultBreakPointsPanel.getComponentCount();

        assertEquals(3, componentCountBefore);
        assertEquals(2, componentCountAfter);

        Arrays.stream(defaultBreakPointsPanel.getComponents())
                .forEach(bpLabel -> assertEquals(Color.BLACK, bpLabel.getForeground()));
    }

    @Test @Order(14)
    @DisplayName("14. test removeBreakPoint() with nonexistent break point")
    void testRemoveNonExistentBreakPoint() {
        List<BreakPoint> breakPoints = getTestBreakPointList();
        defaultBreakPointsPanel.addBreakPoints(breakPoints);
        BreakPoint nonexistentBreakPoint = BreakPointFactory.fromDescription("23:00 - non existent break point");

        int componentCountBefore = defaultBreakPointsPanel.getComponentCount();
        assertDoesNotThrow(() -> defaultBreakPointsPanel.removeBreakPoint(nonexistentBreakPoint));
        int componentCountAfter = defaultBreakPointsPanel.getComponentCount();

        assertEquals(3, componentCountBefore);
        assertEquals(3, componentCountAfter);
    }

    @Test @Order(15)
    @DisplayName("15. test removeBreakPoint() with null")
    void testRemoveNullBreakPoint() {
        List<BreakPoint> breakPoints = getTestBreakPointList();
        defaultBreakPointsPanel.addBreakPoints(breakPoints);

        int componentCountBefore = defaultBreakPointsPanel.getComponentCount();
        assertDoesNotThrow(() -> defaultBreakPointsPanel.removeBreakPoint(null));
        int componentCountAfter = defaultBreakPointsPanel.getComponentCount();

        assertEquals(3, componentCountBefore);
        assertEquals(3, componentCountAfter);
    }

    private void addRegularBPLabel() {
        List<BreakPoint> breakPoints = List.of(BreakPointFactory.fromDescription("23:59"));
        defaultBreakPointsPanel.addBreakPoints(breakPoints);
    }

    private void addPostponedBPLabel() {
        BreakPoint breakPoint = BreakPointFactory.fromDescription("23:58");
        breakPoint.postpone(1);
        List<BreakPoint> breakPoints = List.of(breakPoint);
        defaultBreakPointsPanel.addBreakPoints(breakPoints);
    }

    private List<BreakPoint> getTestBreakPointList() {
        BreakPoint breakPoint1 = BreakPointFactory.fromDescription("23:50");
        BreakPoint breakPoint2 = BreakPointFactory.fromDescription("23:55");
        BreakPoint breakPoint3 = BreakPointFactory.fromDescription("23:59");
        breakPoint2.postpone(1);
        return List.of(breakPoint1, breakPoint2, breakPoint3);
    }
}