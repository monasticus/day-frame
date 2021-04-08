package com.arch.dayframe.gui.dialog;

import com.arch.dayframe.gui.utils.GBC;
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

@Tag("BreakDialog")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BreakDialogTest {

    private static final int DEFAULT = 0;
    private static final int DEFAULT_ANCHOR = GBC.CENTER;
    private static final int DEFAULT_FILL = GBC.NONE;
    private static BreakDialog breakDialog;
    private static JPanel mainPanel;
    private static JLabel timeLabel;
    private static JLabel messageLabel;
    private static JComboBox<Integer> postponeList;
    private static JPanel buttonPanel;
    private static JButton okButton;
    private static JButton postponeButton;

    @BeforeEach
    void setUp() {
        breakDialog = new BreakDialog(new Frame());
        mainPanel = breakDialog.mainPanel;
        timeLabel = breakDialog.timeLabel;
        messageLabel = breakDialog.messageLabel;
        postponeList = breakDialog.postponeList;
        buttonPanel = breakDialog.buttonPanel;
        okButton = breakDialog.okButton;
        postponeButton = breakDialog.postponeButton;
    }

    @Test @Order(1)
    @DisplayName("01. Break Dialog Title Test")
    void testDialogTitle() {
        String title = breakDialog.getTitle();
        assertEquals("Break", title);
    }

    @Test @Order(2)
    @DisplayName("02. Break Dialog Modality Test")
    void testModality() {
        assertFalse(breakDialog.isModal());
    }

    @Test @Order(3)
    @DisplayName("03. Default Close Operation Test")
    void testDefaultCloseOperation() {
        int defaultCloseOperation = breakDialog.getDefaultCloseOperation();
        assertEquals(WindowConstants.DO_NOTHING_ON_CLOSE, defaultCloseOperation);
    }

    @Test @Order(4)
    @DisplayName("04. Default Preferred Size Test")
    void testDefaultPreferredSize() {
        Dimension preferredSize = breakDialog.getPreferredSize();
        assertEquals(600, preferredSize.width);
        assertEquals(375, preferredSize.height);
    }

    @Test @Order(5)
    @DisplayName("05. Default Location Test")
    void testDefaultLocation() {
        Point expectedLocation = getDefaultLocation();
        Point actualLocation = breakDialog.getLocation();

        assertEquals(expectedLocation, actualLocation);
    }

    @Test @Order(6)
    @DisplayName("06. Resizable Test")
    void testResizable() {
        assertFalse(breakDialog.isResizable());
    }

    @Test @Order(7)
    @DisplayName("07. Default Button Test")
    void testDefaultButton() {
        JButton defaultButton = breakDialog.getRootPane().getDefaultButton();
        assertSame(okButton, defaultButton);
    }

    @Test @Order(8)
    @DisplayName("08. Default Background Test")
    void testDefaultBackground() {
        Color mainPanelBgColor = mainPanel.getBackground();
        assertEquals(Color.WHITE, mainPanelBgColor);
    }

    @Test @Order(9)
    @DisplayName("09. Default Layout Test")
    void testDefaultLayout() {
        GridBagLayout layout = assertDoesNotThrow(() -> (GridBagLayout) mainPanel.getLayout());
        assertNull(layout.rowHeights);
        assertNull(layout.rowWeights);
        assertNull(layout.columnWidths);
        assertNull(layout.columnWeights);
    }

    @Test @Order(10)
    @DisplayName("10. Component's General Configuration Test")
    void testComponentsGeneralConfiguration() {
        List<Component> labels = assertDoesNotThrow(() -> Arrays.stream(mainPanel.getComponents()).collect(Collectors.toList()));
        long labelsCount = labels.stream().filter(c -> c instanceof JLabel).count();
        long panelsCount = labels.stream().filter(c -> c instanceof JPanel).count();
        long comboBoxCount = labels.stream().filter(c -> c instanceof JComboBox).count();
        assertEquals(4, mainPanel.getComponentCount());
        assertEquals(2L, labelsCount);
        assertEquals(1L, panelsCount);
        assertEquals(1L, comboBoxCount);
    }

    @Test @Order(11)
    @DisplayName("11. Time Label - Border Test")
    void testTimeLabelBorder() {
        MatteBorder border = assertDoesNotThrow(() -> (MatteBorder) timeLabel.getBorder());
        Insets borderInsets = border.getBorderInsets();
        Color borderColor = border.getMatteColor();

        assertEquals(Color.BLACK, borderColor);
        assertEquals(1, borderInsets.top);
        assertEquals(0, borderInsets.left);
        assertEquals(1, borderInsets.bottom);
        assertEquals(0, borderInsets.right);
    }

    @Test @Order(12)
    @DisplayName("12. Time Label - Font Test")
    void testTimeLabelFont() {
        Font font = timeLabel.getFont();

        assertEquals(Font.SANS_SERIF, font.getFamily());
        assertEquals(Font.BOLD, font.getStyle());
        assertEquals(40, font.getSize());
    }

    @Test @Order(13)
    @DisplayName("13. Time Label - GridBagConstraints Test")
    void testTimeLabelGridBagConstraints() {
        GridBagLayout layout = (GridBagLayout) mainPanel.getLayout();
        GridBagConstraints constraints = layout.getConstraints(timeLabel);
        int gridx = 0;
        int gridy = 1;
        int gridwidth = 1;
        int gridheight = 2;
        Insets insets = new Insets(2, 2, 2, 2);

        testGBC(constraints, gridx, gridy, gridwidth, gridheight, DEFAULT, DEFAULT, DEFAULT_ANCHOR, DEFAULT_FILL, insets, DEFAULT, DEFAULT);
    }

    @Test @Order(14)
    @DisplayName("14. Message Label - Border Test")
    void testMessageLabelBorder() {
        assertNull(messageLabel.getBorder());
    }

    @Test @Order(15)
    @DisplayName("15. Message Label - Font Test")
    void testMessageLabelFont() {
        Font font = messageLabel.getFont();

        assertEquals(Font.SANS_SERIF, font.getFamily());
        assertEquals(Font.ITALIC + Font.BOLD, font.getStyle());
        assertEquals(17, font.getSize());
    }

    @Test @Order(16)
    @DisplayName("16. Message Label - GridBagConstraints Test")
    void testMessageLabelGridBagConstraints() {
        GridBagLayout layout = (GridBagLayout) mainPanel.getLayout();
        GridBagConstraints constraints = layout.getConstraints(messageLabel);
        int gridx = 0;
        int gridy = 3;
        int gridwidth = 1;
        int gridheight = 1;
        Insets insets = new Insets(2, 2, 2, 2);

        testGBC(constraints, gridx, gridy, gridwidth, gridheight, DEFAULT, DEFAULT, DEFAULT_ANCHOR, DEFAULT_FILL, insets, DEFAULT, DEFAULT);
    }

    @Test @Order(17)
    @DisplayName("17. Postpone List - Border Test")
    void testPostponeListBorder() {
        assertNull(postponeList.getBorder());
    }

    @Test @Order(18)
    @DisplayName("18. Postpone List - Font Test")
    void testPostponeListFont() {
        Font font = postponeList.getFont();

        assertEquals(Font.SANS_SERIF, font.getFamily());
        assertEquals(Font.BOLD, font.getStyle());
        assertEquals(11, font.getSize());
    }

    @Test @Order(19)
    @DisplayName("19. Postpone List - GridBagConstraints Test")
    void testPostponeListGridBagConstraints() {
        GridBagLayout layout = (GridBagLayout) mainPanel.getLayout();
        GridBagConstraints constraints = layout.getConstraints(postponeList);
        int gridx = 0;
        int gridy = 4;
        int gridwidth = 1;
        int gridheight = 1;
        Insets insets = new Insets(2, 2, 2, 2);

        testGBC(constraints, gridx, gridy, gridwidth, gridheight, DEFAULT, DEFAULT, DEFAULT_ANCHOR, DEFAULT_FILL, insets, DEFAULT, DEFAULT);
    }

    @Test @Order(20)
    @DisplayName("20. Button Panel - Border Test")
    void testButtonPanelBorder() {
        assertNull(buttonPanel.getBorder());
    }

    @Test @Order(21)
    @DisplayName("21. Button Panel - GridBagConstraints Test")
    void testButtonPanelGridBagConstraints() {
        GridBagLayout layout = (GridBagLayout) mainPanel.getLayout();
        GridBagConstraints constraints = layout.getConstraints(buttonPanel);
        int gridx = 0;
        int gridy = 5;
        int gridwidth = 1;
        int gridheight = 1;
        Insets insets = new Insets(2, 2, 2, 2);

        testGBC(constraints, gridx, gridy, gridwidth, gridheight, DEFAULT, DEFAULT, DEFAULT_ANCHOR, DEFAULT_FILL, insets, DEFAULT, DEFAULT);
    }

    @Test @Order(22)
    @DisplayName("22. OK Button Test")
    void testOkButton() {
        Font font = okButton.getFont();

        assertEquals("OK", okButton.getText());
        assertEquals(Font.DIALOG, font.getFamily());
        assertEquals(Font.BOLD, font.getStyle());
        assertEquals(12, font.getSize());
    }

    @Test @Order(23)
    @DisplayName("23. Postpone Button Test")
    void testPostponeButton() {
        Font font = postponeButton.getFont();

        assertEquals("Postpone", postponeButton.getText());
        assertEquals(Font.DIALOG, font.getFamily());
        assertEquals(Font.BOLD, font.getStyle());
        assertEquals(12, font.getSize());
    }

    @Test @Order(24)
    @DisplayName("24. isMoved() - default")
    void isMovedByDefault() {
        assertFalse(breakDialog.isMoved());
    }

    @Test @Order(25)
    @DisplayName("25. isMoved() - default when visible")
    void isMovedByDefaultWhenVisible() {
        breakDialog.setVisible(true);
        assertFalse(breakDialog.isMoved());
    }

    @Test @Order(26)
    @DisplayName("26. isMoved() - default when invisible")
    void isMovedByDefaultWhenInvisible() {
        breakDialog.setVisible(false);
        assertFalse(breakDialog.isMoved());
    }

    @Test @Order(27)
    @DisplayName("27. isMoved() - after location change when visible")
    void isMovedAfterLocationChangeWhenVisible() {
        breakDialog.setVisible(true);
        breakDialog.setLocation(1, 1);
        assertTrue(breakDialog.isMoved());
    }

    @Test @Order(28)
    @DisplayName("28. isMoved() - after location change when invisible")
    void isMovedAfterLocationChangeWhenInvisible() {
        breakDialog.setVisible(false);
        breakDialog.setLocation(1, 1);
        assertFalse(breakDialog.isMoved());
    }

    @Test @Order(29)
    @DisplayName("29. resetLocation() - without location change")
    void resetLocationWithoutLocationChange() {
        Point locationBefore = breakDialog.getLocation();
        breakDialog.resetLocation();
        Point locationAfter = breakDialog.getLocation();

        assertEquals(locationBefore, locationAfter);
        assertEquals(getDefaultLocation(), locationAfter);
    }

    @Test @Order(30)
    @DisplayName("30. resetLocation() - with location change")
    void resetLocationWithLocationChange() {
        Point defaultLocation = getDefaultLocation();
        Point customizedLocation = new Point(1, 1);
        breakDialog.setLocation(customizedLocation);

        Point locationBefore = breakDialog.getLocation();
        breakDialog.resetLocation();
        Point locationAfter = breakDialog.getLocation();

        assertNotEquals(locationBefore, locationAfter);
        assertEquals(customizedLocation, locationBefore);
        assertEquals(defaultLocation, locationAfter);
    }

    @Test @Order(31)
    @DisplayName("31. completeDialog() - with ordinary break point")
    void completeDialogWithOrinaryBreakPoint() {
        BreakPoint breakPoint = BreakPointFactory.fromDescription("23:59 - message");

        String timeLabelTextBefore = timeLabel.getText();
        String messageLabelTextBefore = messageLabel.getText();
        boolean postponeListEnabledBefore = postponeList.isEnabled();
        boolean postponeButtonEnabledBefore = postponeButton.isEnabled();

        breakDialog.completeDialog(breakPoint);

        String timeLabelTextAfter = timeLabel.getText();
        String messageLabelTextAfter = messageLabel.getText();
        boolean postponeListEnabledAfter = postponeList.isEnabled();
        boolean postponeButtonEnabledAfter = postponeButton.isEnabled();

        assertEquals("", timeLabelTextBefore);
        assertEquals("", messageLabelTextBefore);
        assertTrue(postponeListEnabledBefore);
        assertTrue(postponeButtonEnabledBefore);
        assertEquals("23:59", timeLabelTextAfter);
        assertEquals("message", messageLabelTextAfter);
        assertTrue(postponeListEnabledAfter);
        assertTrue(postponeButtonEnabledAfter);
    }

    @Test @Order(32)
    @DisplayName("32. completeDialog() - with postponed break point")
    void completeDialogWithPostponedBreakPoint() {
        BreakPoint breakPoint = BreakPointFactory.fromDescription("23:55 - message");
        breakPoint.postpone(3);

        String timeLabelTextBefore = timeLabel.getText();
        String messageLabelTextBefore = messageLabel.getText();
        boolean postponeListEnabledBefore = postponeList.isEnabled();
        boolean postponeButtonEnabledBefore = postponeButton.isEnabled();

        breakDialog.completeDialog(breakPoint);

        String timeLabelTextAfter = timeLabel.getText();
        String messageLabelTextAfter = messageLabel.getText();
        boolean postponeListEnabledAfter = postponeList.isEnabled();
        boolean postponeButtonEnabledAfter = postponeButton.isEnabled();

        assertEquals("", timeLabelTextBefore);
        assertEquals("", messageLabelTextBefore);
        assertTrue(postponeListEnabledBefore);
        assertTrue(postponeButtonEnabledBefore);
        assertEquals("23:58", timeLabelTextAfter);
        assertEquals("message", messageLabelTextAfter);
        assertFalse(postponeListEnabledAfter);
        assertFalse(postponeButtonEnabledAfter);
    }

    private Point getDefaultLocation() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension preferredSize = breakDialog.getPreferredSize();
        int x = (int) (screenSize.getWidth() / 2 - preferredSize.width / 2);
        int y = (int) (screenSize.getHeight() / 2 - preferredSize.height / 2);
        return new Point(x, y);
    }

    private void testGBC(GridBagConstraints gbc, int gridx, int gridy, int gridwidth, int gridheight, double weightx, double weighty, int anchor, int fill, Insets insets, int ipadx, int ipady) {
        assertNotNull(gbc, "GridBagConstraints instance is null");
        assertTrue(gbc instanceof GBC, "GridBagConstraints instance is not type of com.arch.dayframe.gui.utils.GBC");
        assertEquals(gridx, gbc.gridx, "Wrong gridx");
        assertEquals(gridy, gbc.gridy, "Wrong gridy");
        assertEquals(gridwidth, gbc.gridwidth, "Wrong gridwidth");
        assertEquals(gridheight, gbc.gridheight, "Wrong gridheight");
        assertEquals(weightx, gbc.weightx, "Wrong weightx");
        assertEquals(weighty, gbc.weighty, "Wrong weighty");
        assertEquals(anchor, gbc.anchor, "Wrong anchor");
        assertEquals(fill, gbc.fill, "Wrong fill");
        assertEquals(insets, gbc.insets, "Wrong insets");
        assertEquals(ipadx, gbc.ipadx, "Wrong ipadx");
        assertEquals(ipady, gbc.ipady, "Wrong ipady");
    }
}