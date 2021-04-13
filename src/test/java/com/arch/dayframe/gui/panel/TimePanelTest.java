package com.arch.dayframe.gui.panel;

import com.arch.dayframe.model.bp.BreakPoint;
import com.arch.dayframe.model.bp.BreakPointFactory;
import org.junit.jupiter.api.*;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Tag("TimePanel")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TimePanelTest {

    private static TimePanel defaultTimePanel;

    @BeforeAll
    static void beforeAll() {
        defaultTimePanel = new TimePanel();
    }

    @Test @Order(1)
    @DisplayName("01. Default Background Test")
    void testDefaultBackground() {
        Color bgColor = defaultTimePanel.getBackground();
        assertEquals(Color.WHITE, bgColor);
    }

    @Test @Order(2)
    @DisplayName("02. Default Layout Test")
    void testDefaultLayout() {
        GridLayout layout = assertDoesNotThrow(() -> (GridLayout) defaultTimePanel.getLayout());
        assertEquals(1, layout.getRows());
        assertEquals(3, layout.getColumns());
    }

    @Test @Order(3)
    @DisplayName("03. Default Preferred Size Test")
    void testDefaultPreferredSize() {
        Dimension preferredSize = defaultTimePanel.getPreferredSize();
        assertEquals(0, preferredSize.width);
        assertEquals(65, preferredSize.height);
    }

    @Test @Order(4)
    @DisplayName("04. Component's General Configuration Test")
    void testComponentsGeneralConfiguration() {
        List<JLabel> labels = assertDoesNotThrow(() -> Arrays.stream(defaultTimePanel.getComponents()).map(c -> (JLabel) c).collect(Collectors.toList()));
        labels.forEach(c -> assertEquals(SwingConstants.CENTER, c.getAlignmentX()));
        assertEquals(3, defaultTimePanel.getComponentCount());
    }

    @Test @Order(5)
    @DisplayName("05. Center Label's Font Test")
    void testCenterLabelFont() {
        JLabel centerLabel = getCenterLabel();
        Font centerLabelFont = centerLabel.getFont();

        assertEquals(Font.SANS_SERIF, centerLabelFont.getFamily());
        assertEquals(Font.BOLD, centerLabelFont.getStyle());
        assertEquals(22, centerLabelFont.getSize());
    }

    @Test @Order(6)
    @DisplayName("06. Center Label's Border Test")
    void testCenterLabelBorder() {
        JLabel centerLabel = getCenterLabel();
        MatteBorder centerLabelBorder = assertDoesNotThrow(() -> (MatteBorder) centerLabel.getBorder());
        Insets borderInsets = centerLabelBorder.getBorderInsets();
        Color borderColor = centerLabelBorder.getMatteColor();

        assertEquals(Color.BLACK, borderColor);
        assertEquals(0, borderInsets.top);
        assertEquals(1, borderInsets.left);
        assertEquals(1, borderInsets.bottom);
        assertEquals(1, borderInsets.right);
    }

    @Test @Order(7)
    @DisplayName("07. Center Label's Foreground Test")
    void testCenterLabelForeground() {
        JLabel centerLabel = getCenterLabel();
        Color labelForeground = centerLabel.getForeground();

        assertEquals(Color.BLACK, labelForeground);
    }

    @Test @Order(8)
    @DisplayName("08. Side Labels Font Test")
    void testSideLabelsFont() {
        List<JLabel> sideLabels = getSideLabels();
        sideLabels.forEach(sideLabel -> {
            Font sideLabelFont = sideLabel.getFont();
            assertEquals(Font.SANS_SERIF, sideLabelFont.getFamily());
            assertEquals(Font.BOLD, sideLabelFont.getStyle());
            assertEquals(17, sideLabelFont.getSize());
        });
    }

    @Test @Order(9)
    @DisplayName("09. Side Labels Border Test")
    void testSideLabelsBorder() {
        List<JLabel> sideLabels = getSideLabels();
        sideLabels.forEach(sideLabel -> {
            MatteBorder sideLabelBorder = assertDoesNotThrow(() -> (MatteBorder) sideLabel.getBorder());
            Insets borderInsets = sideLabelBorder.getBorderInsets();
            Color borderColor = sideLabelBorder.getMatteColor();

            assertEquals(Color.BLACK, borderColor);
            assertEquals(1, borderInsets.top);
            assertEquals(0, borderInsets.left);
            assertEquals(0, borderInsets.bottom);
            assertEquals(0, borderInsets.right);
        });
    }

    @Test @Order(10)
    @DisplayName("10. Side Labels Foreground Test")
    void testSideLabelsForeground() {
        List<JLabel> sideLabels = getSideLabels();
        JLabel leftLabel = sideLabels.get(0);
        JLabel rightLabel = sideLabels.get(1);

        assertEquals(Color.BLACK, leftLabel.getForeground());
        assertEquals(Color.BLACK, rightLabel.getForeground());
    }

    @Test @Order(11)
    @DisplayName("11. Default Label's Values Test")
    void testDefaultLabelValues() {
        List<JLabel> labels = getLabels();
        labels.forEach(label -> assertEquals("", label.getText()));
    }

    @Test @Order(12)
    @DisplayName("12. test setCenterLabelValue()")
    void testSetCenterLabelValue() {
        TimePanel timePanel = new TimePanel();
        JLabel centerLabel = (JLabel) timePanel.getComponents()[1];

        timePanel.setCenterLabelValue("12:23:05.0");
        String labelText = centerLabel.getText();

        assertEquals("12:23:05.0", labelText);
    }

    @Test @Order(13)
    @DisplayName("13. test setCenterLabelValue() - null passed")
    void testSetCenterLabelValueWithNull() {
        TimePanel timePanel = new TimePanel();
        JLabel centerLabel = (JLabel) timePanel.getComponents()[1];

        assertDoesNotThrow(() -> timePanel.setCenterLabelValue(null));
        String labelText = centerLabel.getText();
        Color labelForeground = centerLabel.getForeground();

        assertNotNull(labelText);
        assertEquals("", labelText);
        assertEquals(Color.BLACK, labelForeground);
    }

    @Test @Order(14)
    @DisplayName("14. test setLeftLabelValue()")
    void testSetLeftLabelValue() {
        BreakPoint breakPoint = BreakPointFactory.fromDescription("23:58 - message");
        TimePanel timePanel = new TimePanel();
        JLabel leftLabel = (JLabel) timePanel.getComponents()[0];

        timePanel.setLeftLabelValue(breakPoint);
        String labelText = leftLabel.getText();
        Color labelColor = leftLabel.getForeground();

        assertEquals("23:58", labelText);
        assertEquals(Color.BLACK, labelColor);
    }

    @Test @Order(15)
    @DisplayName("15. test setLeftLabelValue() - postponed break point passed")
    void testSetLeftLabelValueWithPostponedBreakPoint() {
        BreakPoint breakPoint = BreakPointFactory.fromDescription("23:57 - message");
        breakPoint.postpone(1);
        TimePanel timePanel = new TimePanel();
        JLabel leftLabel = (JLabel) timePanel.getComponents()[0];

        timePanel.setLeftLabelValue(breakPoint);
        String labelText = leftLabel.getText();
        Color labelColor = leftLabel.getForeground();

        assertEquals("23:58", labelText);
        assertEquals(Color.BLACK, labelColor);
    }

    @Test @Order(16)
    @DisplayName("16. test setLeftLabelValue() - null passed")
    void testSetLeftLabelValueWithNull() {
        TimePanel timePanel = new TimePanel();
        JLabel leftLabel = (JLabel) timePanel.getComponents()[0];

        assertDoesNotThrow(() -> timePanel.setLeftLabelValue(null));
        String labelText = leftLabel.getText();
        Color labelColor = leftLabel.getForeground();

        assertNotNull(labelText);
        assertEquals("", labelText);
        assertEquals(Color.BLACK, labelColor);
    }

    @Test @Order(17)
    @DisplayName("17. test setRightLabelValue()")
    void testSetRightLabelValue() {
        BreakPoint breakPoint = BreakPointFactory.fromDescription("23:59 - message");
        TimePanel timePanel = new TimePanel();
        JLabel leftLabel = (JLabel) timePanel.getComponents()[2];

        timePanel.setRightLabelValue(breakPoint);
        String labelText = leftLabel.getText();
        Color labelColor = leftLabel.getForeground();

        assertEquals("23:59", labelText);
        assertEquals(Color.BLACK, labelColor);
    }

    @Test @Order(18)
    @DisplayName("18. test setRightLabelValue() - postponed break point passed")
    void testSetRightLabelValueWithPostponedBreakPoint() {
        BreakPoint breakPoint = BreakPointFactory.fromDescription("23:58 - message");
        breakPoint.postpone(1);
        TimePanel timePanel = new TimePanel();
        JLabel leftLabel = (JLabel) timePanel.getComponents()[2];

        timePanel.setRightLabelValue(breakPoint);
        String labelText = leftLabel.getText();
        Color labelColor = leftLabel.getForeground();

        assertEquals("23:59", labelText);
        assertEquals(Color.RED, labelColor);
    }

    @Test @Order(19)
    @DisplayName("19. test setRightLabelValue() - null passed")
    void testSetRightLabelValueWithNull() {
        TimePanel timePanel = new TimePanel();
        JLabel leftLabel = (JLabel) timePanel.getComponents()[2];

        assertDoesNotThrow(() -> timePanel.setRightLabelValue(null));
        String labelText = leftLabel.getText();
        Color labelColor = leftLabel.getForeground();

        assertNotNull(labelText);
        assertEquals("", labelText);
        assertEquals(Color.BLACK, labelColor);
    }

    private JLabel getCenterLabel() {
        return getLabels().get(1);
    }

    private List<JLabel> getSideLabels() {
        List<JLabel> labels = getLabels();
        return List.of(labels.get(0), labels.get(2));
    }

    private List<JLabel> getLabels() {
        return Arrays.stream(defaultTimePanel.getComponents()).map(c -> (JLabel) c).collect(Collectors.toList());
    }
}