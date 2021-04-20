package com.arch.dayframe.gui.panel;

import com.arch.dayframe.model.bp.BreakPoint;
import com.arch.dayframe.model.bp.BreakPointFactory;
import com.arch.dayframe.testutils.state.GUIStateDTOFactory;
import com.arch.dayframe.testutils.state.TimePanelStateDTO;
import org.junit.jupiter.api.*;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

@Tag("TimePanel")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TimePanelTest {

    private static TimePanel defaultTimePanel;
    private static TimePanelStateDTO defaultTimePanelState;

    @BeforeAll
    static void beforeAll() {
        defaultTimePanel = new TimePanel();
        defaultTimePanelState = GUIStateDTOFactory.ofTimePanel(defaultTimePanel);
    }

    @Test @Order(1)
    @DisplayName("01. Default Background Test")
    void testDefaultBackground() {
        assertEquals(Color.WHITE, defaultTimePanelState.background);
    }

    @Test @Order(2)
    @DisplayName("02. Default Layout Test")
    void testDefaultLayout() {
        GridLayout layout = (GridLayout) defaultTimePanelState.layout;
        assertEquals(1, layout.getRows());
        assertEquals(3, layout.getColumns());
    }

    @Test @Order(3)
    @DisplayName("03. Default Preferred Size Test")
    void testDefaultPreferredSize() {
        assertEquals(0, defaultTimePanelState.preferredSizeWidth);
        assertEquals(65, defaultTimePanelState.preferredSizeHeight);
    }

    @Test @Order(4)
    @DisplayName("04. Component's General Configuration Test")
    void testComponentsGeneralConfiguration() {
        assertEquals(3, defaultTimePanelState.componentsCount);
        assertEquals(SwingConstants.CENTER, defaultTimePanelState.leftLabelAlignmentX);
        assertEquals(SwingConstants.CENTER, defaultTimePanelState.centerLabelAlignmentX);
        assertEquals(SwingConstants.CENTER, defaultTimePanelState.rightLabelAlignmentX);
        assertEquals(Component.CENTER_ALIGNMENT, defaultTimePanelState.leftLabelAlignmentY);
        assertEquals(Component.CENTER_ALIGNMENT, defaultTimePanelState.centerLabelAlignmentY);
        assertEquals(Component.CENTER_ALIGNMENT, defaultTimePanelState.rightLabelAlignmentY);
    }

    @Test @Order(5)
    @DisplayName("05. Center Label's Font Test")
    void testCenterLabelFont() {
        assertEquals(Font.SANS_SERIF, defaultTimePanelState.centerLabelFontFamily);
        assertEquals(Font.BOLD, defaultTimePanelState.centerLabelFontStyle);
        assertEquals(22, defaultTimePanelState.centerLabelFontSize);
    }

    @Test @Order(6)
    @DisplayName("06. Center Label's Border Test")
    void testCenterLabelBorder() {
        Color borderColor = defaultTimePanelState.centerLabelBorder.getMatteColor();
        Insets borderInsets = defaultTimePanelState.centerLabelBorder.getBorderInsets();

        assertEquals(Color.BLACK, borderColor);
        assertEquals(0, borderInsets.top);
        assertEquals(1, borderInsets.left);
        assertEquals(1, borderInsets.bottom);
        assertEquals(1, borderInsets.right);
    }

    @Test @Order(7)
    @DisplayName("07. Center Label's Foreground Test")
    void testCenterLabelForeground() {
        assertEquals(Color.BLACK, defaultTimePanelState.centerLabelForeground);
    }

    @Test @Order(8)
    @DisplayName("08. Side Labels Font Test")
    void testSideLabelsFont() {
        assertEquals(Font.SANS_SERIF, defaultTimePanelState.leftLabelFontFamily);
        assertEquals(Font.SANS_SERIF, defaultTimePanelState.rightLabelFontFamily);
        assertEquals(Font.BOLD, defaultTimePanelState.leftLabelFontStyle);
        assertEquals(Font.BOLD, defaultTimePanelState.rightLabelFontStyle);
        assertEquals(17, defaultTimePanelState.leftLabelFontSize);
        assertEquals(17, defaultTimePanelState.rightLabelFontSize);
    }

    @Test @Order(9)
    @DisplayName("09. Side Labels Border Test")
    void testSideLabelsBorder() {
        Color leftBorderColor = defaultTimePanelState.leftLabelBorder.getMatteColor();
        Insets leftBorderInsets = defaultTimePanelState.leftLabelBorder.getBorderInsets();

        assertEquals(Color.BLACK, leftBorderColor);
        assertEquals(1, leftBorderInsets.top);
        assertEquals(0, leftBorderInsets.left);
        assertEquals(0, leftBorderInsets.bottom);
        assertEquals(0, leftBorderInsets.right);

        Color rightBorderColor = defaultTimePanelState.leftLabelBorder.getMatteColor();
        Insets rightBorderInsets = defaultTimePanelState.leftLabelBorder.getBorderInsets();

        assertEquals(Color.BLACK, rightBorderColor);
        assertEquals(1, rightBorderInsets.top);
        assertEquals(0, rightBorderInsets.left);
        assertEquals(0, rightBorderInsets.bottom);
        assertEquals(0, rightBorderInsets.right);
    }

    @Test @Order(10)
    @DisplayName("10. Side Labels Foreground Test")
    void testSideLabelsForeground() {
        assertEquals(Color.BLACK, defaultTimePanelState.leftLabelForeground);
        assertEquals(Color.BLACK, defaultTimePanelState.rightLabelForeground);
    }

    @Test @Order(11)
    @DisplayName("11. Default Label's Values Test")
    void testDefaultLabelValues() {
        assertEquals("", defaultTimePanelState.leftLabelText);
        assertEquals("", defaultTimePanelState. centerLabelText);
        assertEquals("", defaultTimePanelState.rightLabelText);
    }

    @Test @Order(12)
    @DisplayName("12. test setCenterLabelValue()")
    void testSetCenterLabelValue() {
        TimePanel timePanel = new TimePanel();

        timePanel.setCenterLabelValue("12:23:05.0");

        TimePanelStateDTO timePanelState = GUIStateDTOFactory.ofTimePanel(timePanel);
        assertEquals("12:23:05.0", timePanelState.centerLabelText);
        assertEquals(Color.BLACK, timePanelState.centerLabelForeground);
    }

    @Test @Order(13)
    @DisplayName("13. test setCenterLabelValue() - null passed")
    void testSetCenterLabelValueWithNull() {
        TimePanel timePanel = new TimePanel();

        assertDoesNotThrow(() -> timePanel.setCenterLabelValue(null));

        TimePanelStateDTO timePanelState = GUIStateDTOFactory.ofTimePanel(timePanel);
        assertNotNull(timePanelState.centerLabelText);
        assertEquals("", timePanelState.centerLabelText);
        assertEquals(Color.BLACK, timePanelState.centerLabelForeground);
    }

    @Test @Order(14)
    @DisplayName("14. test setLeftLabelValue()")
    void testSetLeftLabelValue() {
        TimePanel timePanel = new TimePanel();

        BreakPoint breakPoint = BreakPointFactory.fromDescription("23:58 - message");
        timePanel.setLeftLabelValue(breakPoint);

        TimePanelStateDTO timePanelState = GUIStateDTOFactory.ofTimePanel(timePanel);
        assertEquals("23:58", timePanelState.leftLabelText);
        assertEquals(Color.BLACK, timePanelState.leftLabelForeground);
    }

    @Test @Order(15)
    @DisplayName("15. test setLeftLabelValue() - postponed break point passed")
    void testSetLeftLabelValueWithPostponedBreakPoint() {
        TimePanel timePanel = new TimePanel();

        BreakPoint breakPoint = BreakPointFactory.fromDescription("23:57 - message");
        breakPoint.postpone(1);
        timePanel.setLeftLabelValue(breakPoint);

        TimePanelStateDTO timePanelState = GUIStateDTOFactory.ofTimePanel(timePanel);
        assertEquals("23:58", timePanelState.leftLabelText);
        assertEquals(Color.BLACK, timePanelState.leftLabelForeground);
    }

    @Test @Order(16)
    @DisplayName("16. test setLeftLabelValue() - null passed")
    void testSetLeftLabelValueWithNull() {
        TimePanel timePanel = new TimePanel();

        assertDoesNotThrow(() -> timePanel.setLeftLabelValue(null));

        TimePanelStateDTO timePanelState = GUIStateDTOFactory.ofTimePanel(timePanel);
        assertNotNull(timePanelState.centerLabelText);
        assertEquals("", timePanelState.centerLabelText);
        assertEquals(Color.BLACK, timePanelState.centerLabelForeground);
    }

    @Test @Order(17)
    @DisplayName("17. test setRightLabelValue()")
    void testSetRightLabelValue() {
        TimePanel timePanel = new TimePanel();

        BreakPoint breakPoint = BreakPointFactory.fromDescription("23:59 - message");
        timePanel.setRightLabelValue(breakPoint);

        TimePanelStateDTO timePanelState = GUIStateDTOFactory.ofTimePanel(timePanel);
        assertEquals("23:59", timePanelState.rightLabelText);
        assertEquals(Color.BLACK, timePanelState.rightLabelForeground);
    }

    @Test @Order(18)
    @DisplayName("18. test setRightLabelValue() - postponed break point passed")
    void testSetRightLabelValueWithPostponedBreakPoint() {
        TimePanel timePanel = new TimePanel();

        BreakPoint breakPoint = BreakPointFactory.fromDescription("23:58 - message");
        breakPoint.postpone(1);
        timePanel.setRightLabelValue(breakPoint);

        TimePanelStateDTO timePanelState = GUIStateDTOFactory.ofTimePanel(timePanel);
        assertEquals("23:59", timePanelState.rightLabelText);
        assertEquals(Color.RED, timePanelState.rightLabelForeground);
    }

    @Test @Order(19)
    @DisplayName("19. test setRightLabelValue() - null passed")
    void testSetRightLabelValueWithNull() {
        TimePanel timePanel = new TimePanel();

        assertDoesNotThrow(() -> timePanel.setRightLabelValue(null));

        TimePanelStateDTO timePanelState = GUIStateDTOFactory.ofTimePanel(timePanel);
        assertNotNull(timePanelState.rightLabelText);
        assertEquals("", timePanelState.rightLabelText);
        assertEquals(Color.BLACK, timePanelState.rightLabelForeground);
    }
}