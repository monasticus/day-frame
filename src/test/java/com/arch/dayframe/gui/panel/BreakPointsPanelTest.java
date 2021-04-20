package com.arch.dayframe.gui.panel;

import com.arch.dayframe.model.bp.BreakPoint;
import com.arch.dayframe.model.bp.BreakPointFactory;
import com.arch.dayframe.testutils.state.BreakPointsPanelStateDTO;
import com.arch.dayframe.testutils.state.GUIStateDTOFactory;
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
    private static BreakPointsPanelStateDTO defaultBreakPointsPanelState;

    @BeforeAll
    static void beforeAll() {
        defaultBreakPointsPanel = new BreakPointsPanel();
        defaultBreakPointsPanelState = GUIStateDTOFactory.ofBreakPointsPanel(defaultBreakPointsPanel);
    }

    @AfterEach
    void tearDown() {
        defaultBreakPointsPanel.cleanBreakPoints();
    }

    @Test @Order(1)
    @DisplayName("01. Default Background Test")
    void testDefaultBackground() {
        assertEquals(Color.WHITE, defaultBreakPointsPanelState.background);
    }

    @Test @Order(2)
    @DisplayName("02. Default Layout Test")
    void testDefaultLayout() {
        GridLayout layout = (GridLayout) defaultBreakPointsPanelState.layout;
        assertEquals(10, layout.getRows());
        assertEquals(1, layout.getColumns());
    }

    @Test @Order(3)
    @DisplayName("03. Default Preferred Size Test")
    void testDefaultPreferredSize() {
        assertEquals(130, defaultBreakPointsPanelState.preferredSizeWidth);
        assertEquals(0, defaultBreakPointsPanelState.preferredSizeHeight);
    }

    @Test @Order(4)
    @DisplayName("04. Default Component's Count Test")
    void testDefaultComponentsCount() {
        assertEquals(0, defaultBreakPointsPanelState.componentsCount);
    }

    @Test @Order(5)
    @DisplayName("05. BP Label's Alignment Test")
    void testBPLabelAlignment() {
        addRegularBPLabel();
        BreakPointsPanelStateDTO breakPointsPanelState = GUIStateDTOFactory.ofBreakPointsPanel(defaultBreakPointsPanel);

        assertEquals(SwingConstants.CENTER, breakPointsPanelState.firstLabelAlignmentX);
        assertEquals(Component.CENTER_ALIGNMENT, breakPointsPanelState.firstLabelAlignmentY);
    }

    @Test @Order(6)
    @DisplayName("06. BP Label's Font Test")
    void testBPLabelFont() {
        addRegularBPLabel();
        BreakPointsPanelStateDTO breakPointsPanelState = GUIStateDTOFactory.ofBreakPointsPanel(defaultBreakPointsPanel);

        assertEquals(Font.SANS_SERIF, breakPointsPanelState.firstLabelFontFamily);
        assertEquals(Font.BOLD, breakPointsPanelState.firstLabelFontStyle);
        assertEquals(17, breakPointsPanelState.firstLabelFontSize);
    }

    @Test @Order(7)
    @DisplayName("07. BP Label's Foreground Color Test - for regular break point")
    void testBPLabelColorForRegularBreakPoint() {
        addRegularBPLabel();
        BreakPointsPanelStateDTO breakPointsPanelState = GUIStateDTOFactory.ofBreakPointsPanel(defaultBreakPointsPanel);

        assertEquals(Color.BLACK, breakPointsPanelState.firstLabelForeground);
    }

    @Test @Order(8)
    @DisplayName("08. BP Label's Foreground Color Test - for postponed break point")
    void testBPLabelColorForPostponedBreakPoint() {
        addPostponedBPLabel();
        BreakPointsPanelStateDTO breakPointsPanelState = GUIStateDTOFactory.ofBreakPointsPanel(defaultBreakPointsPanel);

        assertEquals(Color.RED, breakPointsPanelState.firstLabelForeground);
    }

    @Test @Order(9)
    @DisplayName("09. test addBreakPoints()")
    void testAddBreakPoints() {
        List<BreakPoint> breakPoints = getTestBreakPointList();
        defaultBreakPointsPanel.addBreakPoints(breakPoints);
        BreakPointsPanelStateDTO breakPointsPanelState = GUIStateDTOFactory.ofBreakPointsPanel(defaultBreakPointsPanel);

        assertEquals(3, breakPointsPanelState.componentsCount);
        assertEquals(Color.BLACK, breakPointsPanelState.labels.get(0).getForeground());
        assertEquals(Color.RED, breakPointsPanelState.labels.get(1).getForeground());
        assertEquals(Color.BLACK, breakPointsPanelState.labels.get(2).getForeground());
    }

    @Test @Order(10)
    @DisplayName("10. test addBreakPoints() used twice")
    void testAddBreakPointsTwice() {
        List<BreakPoint> breakPoints = getTestBreakPointList();

        BreakPointsPanelStateDTO breakPointsPanelStateBefore = GUIStateDTOFactory.ofBreakPointsPanel(defaultBreakPointsPanel);
        defaultBreakPointsPanel.addBreakPoints(breakPoints);
        BreakPointsPanelStateDTO breakPointsPanelStateAfterFirst = GUIStateDTOFactory.ofBreakPointsPanel(defaultBreakPointsPanel);
        defaultBreakPointsPanel.addBreakPoints(breakPoints);
        BreakPointsPanelStateDTO breakPointsPanelStateAfterSecond = GUIStateDTOFactory.ofBreakPointsPanel(defaultBreakPointsPanel);

        assertEquals(0, breakPointsPanelStateBefore.componentsCount);
        assertEquals(3, breakPointsPanelStateAfterFirst.componentsCount);
        assertEquals(6, breakPointsPanelStateAfterSecond.componentsCount);
    }

    @Test @Order(11)
    @DisplayName("11. test cleanBreakPoints() with some labels")
    void testCleanBreakPointsWithSomeLabels() {
        List<BreakPoint> breakPoints = getTestBreakPointList();
        defaultBreakPointsPanel.addBreakPoints(breakPoints);

        BreakPointsPanelStateDTO breakPointsPanelStateBefore = GUIStateDTOFactory.ofBreakPointsPanel(defaultBreakPointsPanel);
        defaultBreakPointsPanel.cleanBreakPoints();
        BreakPointsPanelStateDTO breakPointsPanelStateAfter = GUIStateDTOFactory.ofBreakPointsPanel(defaultBreakPointsPanel);

        assertEquals(3, breakPointsPanelStateBefore.componentsCount);
        assertEquals(0, breakPointsPanelStateAfter.componentsCount);
    }

    @Test @Order(12)
    @DisplayName("12. test cleanBreakPoints() with no labels")
    void testCleanBreakPointsWithNoLabels() {
        BreakPointsPanelStateDTO breakPointsPanelStateBefore = GUIStateDTOFactory.ofBreakPointsPanel(defaultBreakPointsPanel);
        assertDoesNotThrow(() -> defaultBreakPointsPanel.cleanBreakPoints());
        BreakPointsPanelStateDTO breakPointsPanelStateAfter = GUIStateDTOFactory.ofBreakPointsPanel(defaultBreakPointsPanel);

        assertEquals(0, breakPointsPanelStateBefore.componentsCount);
        assertEquals(0, breakPointsPanelStateAfter.componentsCount);
    }

    @Test @Order(13)
    @DisplayName("13. test removeBreakPoint()")
    void testRemoveBreakPoint() {
        List<BreakPoint> breakPoints = getTestBreakPointList();
        BreakPoint postponedBreakPoint = breakPoints.get(1);
        defaultBreakPointsPanel.addBreakPoints(breakPoints);

        BreakPointsPanelStateDTO breakPointsPanelStateBefore = GUIStateDTOFactory.ofBreakPointsPanel(defaultBreakPointsPanel);
        defaultBreakPointsPanel.removeBreakPoint(postponedBreakPoint);
        BreakPointsPanelStateDTO breakPointsPanelStateAfter = GUIStateDTOFactory.ofBreakPointsPanel(defaultBreakPointsPanel);

        assertEquals(3, breakPointsPanelStateBefore.componentsCount);
        assertEquals(2, breakPointsPanelStateAfter.componentsCount);

        breakPointsPanelStateAfter.labels.forEach(bpLabel -> assertEquals(Color.BLACK, bpLabel.getForeground()));
    }

    @Test @Order(14)
    @DisplayName("14. test removeBreakPoint() with nonexistent break point")
    void testRemoveNonExistentBreakPoint() {
        List<BreakPoint> breakPoints = getTestBreakPointList();
        defaultBreakPointsPanel.addBreakPoints(breakPoints);
        BreakPoint nonexistentBreakPoint = BreakPointFactory.fromDescription("23:00 - non existent break point");

        BreakPointsPanelStateDTO breakPointsPanelStateBefore = GUIStateDTOFactory.ofBreakPointsPanel(defaultBreakPointsPanel);
        assertDoesNotThrow(() -> defaultBreakPointsPanel.removeBreakPoint(nonexistentBreakPoint));
        BreakPointsPanelStateDTO breakPointsPanelStateAfter = GUIStateDTOFactory.ofBreakPointsPanel(defaultBreakPointsPanel);

        assertEquals(3, breakPointsPanelStateBefore.componentsCount);
        assertEquals(3, breakPointsPanelStateAfter.componentsCount);
    }

    @Test @Order(15)
    @DisplayName("15. test removeBreakPoint() with null")
    void testRemoveNullBreakPoint() {
        List<BreakPoint> breakPoints = getTestBreakPointList();
        defaultBreakPointsPanel.addBreakPoints(breakPoints);

        BreakPointsPanelStateDTO breakPointsPanelStateBefore = GUIStateDTOFactory.ofBreakPointsPanel(defaultBreakPointsPanel);
        assertDoesNotThrow(() -> defaultBreakPointsPanel.removeBreakPoint(null));
        BreakPointsPanelStateDTO breakPointsPanelStateAfter = GUIStateDTOFactory.ofBreakPointsPanel(defaultBreakPointsPanel);

        assertEquals(3, breakPointsPanelStateBefore.componentsCount);
        assertEquals(3, breakPointsPanelStateAfter.componentsCount);
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