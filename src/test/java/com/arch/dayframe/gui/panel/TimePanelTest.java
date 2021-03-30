package com.arch.dayframe.gui.panel;

import com.arch.dayframe.model.bp.BreakPoint;
import com.arch.dayframe.model.bp.BreakPointFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class TimePanelTest {

    private static TimePanel defaultTimePanel;

    @BeforeAll
    static void beforeAll() {
        defaultTimePanel = new TimePanel();
    }

    @Test
    void testDefaultBackground() {
        Color bgColor = defaultTimePanel.getBackground();
        assertEquals(Color.WHITE, bgColor);
    }

    @Test
    void testDefaultLayout() {
        GridLayout layout = assertDoesNotThrow(() -> (GridLayout) defaultTimePanel.getLayout());
        assertEquals(1, layout.getRows());
        assertEquals(3, layout.getColumns());
    }

    @Test
    void testDefaultPreferredSize() {
        Dimension preferredSize = defaultTimePanel.getPreferredSize();
        assertEquals(0, preferredSize.width);
        assertEquals(65, preferredSize.height);
    }

    @Test
    void testComponentsGeneralConfiguration() {
        List<JLabel> labels = assertDoesNotThrow(() -> Arrays.stream(defaultTimePanel.getComponents()).map(c -> (JLabel) c).collect(Collectors.toList()));
        labels.forEach(c -> assertEquals(SwingConstants.CENTER, c.getAlignmentX()));
        assertEquals(3, defaultTimePanel.getComponentCount());
    }

    @Test
    void testCenterLabelFont() {
        JLabel centerLabel = getCenterLabel();
        Font centerLabelFont = centerLabel.getFont();

        assertEquals(Font.SANS_SERIF, centerLabelFont.getFamily());
        assertEquals(Font.BOLD, centerLabelFont.getStyle());
        assertEquals(22, centerLabelFont.getSize());
    }

    @Test
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

    @Test
    void testSideLabelsFont() {
        List<JLabel> sideLabels = getSideLabels();
        sideLabels.forEach(sideLabel -> {
            Font sideLabelFont = sideLabel.getFont();
            assertEquals(Font.SANS_SERIF, sideLabelFont.getFamily());
            assertEquals(Font.BOLD, sideLabelFont.getStyle());
            assertEquals(17, sideLabelFont.getSize());
        });
    }

    @Test
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

    @Test
    void testDefaultLabelValues() {
        List<JLabel> labels = getLabels();
        labels.forEach(label -> assertEquals("", label.getText()));
    }

    @Test
    void setCenterLabelValue() {
        TimePanel timePanel = new TimePanel();
        JLabel centerLabel = (JLabel) timePanel.getComponents()[1];
        String centerLabelTextBefore = centerLabel.getText();

        timePanel.setCenterLabelValue("12:23:05.0");

        String centerLabelTextAfter = centerLabel.getText();

        assertEquals("", centerLabelTextBefore);
        assertEquals("12:23:05.0", centerLabelTextAfter);
    }

    @Test
    void setLeftLabelValue() {
        TimePanel timePanel = new TimePanel();
        JLabel leftLabel = (JLabel) timePanel.getComponents()[0];
        String leftLabelTextBefore = leftLabel.getText();

        BreakPoint breakPoint = BreakPointFactory.fromDescription("23:58 - message");
        timePanel.setLeftLabelValue(breakPoint);

        String leftLabelTextAfter = leftLabel.getText();

        assertEquals("", leftLabelTextBefore);
        assertEquals("23:58", leftLabelTextAfter);
    }

    @Test
    void setRightLabelValue() {
        TimePanel timePanel = new TimePanel();
        JLabel leftLabel = (JLabel) timePanel.getComponents()[2];
        String leftLabelTextBefore = leftLabel.getText();

        BreakPoint breakPoint = BreakPointFactory.fromDescription("23:59 - message");
        timePanel.setRightLabelValue(breakPoint);

        String leftLabelTextAfter = leftLabel.getText();

        assertEquals("", leftLabelTextBefore);
        assertEquals("23:59", leftLabelTextAfter);
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