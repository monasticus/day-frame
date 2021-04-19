package com.arch.dayframe.gui.dialog;

import com.arch.dayframe.gui.utils.GBC;
import com.arch.dayframe.model.bp.BreakPoint;
import com.arch.dayframe.model.bp.BreakPointFactory;
import com.arch.dayframe.testutils.state.BreakDialogStateDTO;
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
    private static BreakDialogStateDTO breakDialogState;

    @BeforeEach
    void setUp() {
        breakDialog = new BreakDialog(new Frame());
        breakDialogState = getBreakDialogState();
    }

    @Test @Order(1)
    @DisplayName("01. Break Dialog Title Test")
    void testDialogTitle() {
        assertEquals("Break", breakDialogState.title);
    }

    @Test @Order(2)
    @DisplayName("02. Break Dialog Modality Test")
    void testModality() {
        assertFalse(breakDialogState.modal);
    }

    @Test @Order(3)
    @DisplayName("03. Default Close Operation Test")
    void testDefaultCloseOperation() {
        assertEquals(WindowConstants.DO_NOTHING_ON_CLOSE, breakDialogState.defaultCloseOperation);
    }

    @Test @Order(4)
    @DisplayName("04. Default Preferred Size Test")
    void testDefaultPreferredSize() {
        assertEquals(600, breakDialogState.preferredSizeWidth);
        assertEquals(375, breakDialogState.preferredSizeHeight);
    }

    @Test @Order(5)
    @DisplayName("05. Default Location Test")
    void testDefaultLocation() {
        Point expectedLocation = getDefaultLocation();
        assertEquals(expectedLocation, breakDialogState.location);
    }

    @Test @Order(6)
    @DisplayName("06. Resizable Test")
    void testResizable() {
        assertFalse(breakDialogState.resizable);
    }

    @Test @Order(7)
    @DisplayName("07. Default Button Test")
    void testDefaultButton() {
        assertSame(breakDialog.okButton, breakDialogState.defaultButton);
    }

    @Test @Order(8)
    @DisplayName("08. Default Background Test")
    void testDefaultBackground() {
        assertEquals(Color.WHITE, breakDialogState.background);
    }

    @Test @Order(9)
    @DisplayName("09. Default Layout Test")
    void testDefaultLayout() {
        GridBagLayout layout = (GridBagLayout) breakDialogState.layout;
        assertNull(layout.rowHeights);
        assertNull(layout.rowWeights);
        assertNull(layout.columnWidths);
        assertNull(layout.columnWeights);
    }

    @Test @Order(10)
    @DisplayName("10. Component's General Configuration Test")
    void testComponentsGeneralConfiguration() {
        List<Component> labels = breakDialogState.labels;
        long labelsCount = labels.stream().filter(c -> c instanceof JLabel).count();
        long panelsCount = labels.stream().filter(c -> c instanceof JPanel).count();
        long comboBoxCount = labels.stream().filter(c -> c instanceof JComboBox).count();
        assertEquals(4, breakDialogState.componentsCount);
        assertEquals(2L, labelsCount);
        assertEquals(1L, panelsCount);
        assertEquals(1L, comboBoxCount);
    }

    @Test @Order(11)
    @DisplayName("11. Time Label - Border Test")
    void testTimeLabelBorder() {
        MatteBorder timeLabelBorder = (MatteBorder) breakDialogState.timeLabelBorder;
        Color borderColor = timeLabelBorder.getMatteColor();
        Insets borderInsets = timeLabelBorder.getBorderInsets();

        assertEquals(Color.BLACK, borderColor);
        assertEquals(1, borderInsets.top);
        assertEquals(0, borderInsets.left);
        assertEquals(1, borderInsets.bottom);
        assertEquals(0, borderInsets.right);
    }

    @Test @Order(12)
    @DisplayName("12. Time Label - Font Test")
    void testTimeLabelFont() {
        assertEquals(Font.SANS_SERIF, breakDialogState.timeLabelFontFamily);
        assertEquals(Font.BOLD, breakDialogState.timeLabelFontStyle);
        assertEquals(40, breakDialogState.timeLabelFontSize);
    }

    @Test @Order(13)
    @DisplayName("13. Time Label - GridBagConstraints Test")
    void testTimeLabelGridBagConstraints() {
        GridBagConstraints constraints = breakDialogState.timeLabelConstraints;

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
        assertNull(breakDialogState.messageLabelBorder);
    }

    @Test @Order(15)
    @DisplayName("15. Message Label - Font Test")
    void testMessageLabelFont() {
        assertEquals(Font.SANS_SERIF, breakDialogState.messageLabelFontFamily);
        assertEquals(Font.ITALIC + Font.BOLD, breakDialogState.messageLabelFontStyle);
        assertEquals(17, breakDialogState.messageLabelFontSize);
    }

    @Test @Order(16)
    @DisplayName("16. Message Label - GridBagConstraints Test")
    void testMessageLabelGridBagConstraints() {
        GridBagConstraints constraints = breakDialogState.messageLabelConstraints;

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
        assertNull(breakDialogState.postponeListBorder);
    }

    @Test @Order(18)
    @DisplayName("18. Postpone List - Font Test")
    void testPostponeListFont() {
        assertEquals(Font.SANS_SERIF, breakDialogState.postponeListFontFamily);
        assertEquals(Font.BOLD, breakDialogState.postponeListFontStyle);
        assertEquals(11, breakDialogState.postponeListFontSize);
    }

    @Test @Order(19)
    @DisplayName("19. Postpone List - GridBagConstraints Test")
    void testPostponeListGridBagConstraints() {
        GridBagConstraints constraints = breakDialogState.postponeListConstraints;

        int gridx = 0;
        int gridy = 4;
        int gridwidth = 1;
        int gridheight = 1;
        Insets insets = new Insets(2, 2, 2, 2);

        testGBC(constraints, gridx, gridy, gridwidth, gridheight, DEFAULT, DEFAULT, DEFAULT_ANCHOR, DEFAULT_FILL, insets, DEFAULT, DEFAULT);
    }

    @Test @Order(20)
    @DisplayName("20. Postpone List - List Items Test")
    void testPostponeListValues() {
        assertEquals(3, breakDialogState.postponeListItemCount);
        assertEquals(5,  breakDialogState.postponeListItems.get(0));
        assertEquals(10, breakDialogState.postponeListItems.get(1));
        assertEquals(15, breakDialogState.postponeListItems.get(2));
        assertEquals(5, breakDialogState.postponeListSelectedItem);
    }

    @Test @Order(21)
    @DisplayName("21. Button Panel - Border Test")
    void testButtonPanelBorder() {
        assertNull(breakDialogState.buttonPanelBorder);
    }

    @Test @Order(22)
    @DisplayName("22. Button Panel - GridBagConstraints Test")
    void testButtonPanelGridBagConstraints() {
        GridBagConstraints constraints = breakDialogState.buttonPanelConstraints;

        int gridx = 0;
        int gridy = 5;
        int gridwidth = 1;
        int gridheight = 1;
        Insets insets = new Insets(2, 2, 2, 2);

        testGBC(constraints, gridx, gridy, gridwidth, gridheight, DEFAULT, DEFAULT, DEFAULT_ANCHOR, DEFAULT_FILL, insets, DEFAULT, DEFAULT);
    }

    @Test @Order(23)
    @DisplayName("23. OK Button Test")
    void testOkButton() {
        assertEquals("OK", breakDialogState.okButtonText);
        assertEquals(Font.DIALOG, breakDialogState.okButtonFontFamily);
        assertEquals(Font.BOLD, breakDialogState.okButtonFontStyle);
        assertEquals(12, breakDialogState.okButtonFontSize);
    }

    @Test @Order(24)
    @DisplayName("24. Postpone Button Test")
    void testPostponeButton() {
        assertEquals("Postpone", breakDialogState.postponeButtonText);
        assertEquals(Font.DIALOG, breakDialogState.postponeButtonFontFamily);
        assertEquals(Font.BOLD, breakDialogState.postponeButtonFontStyle);
        assertEquals(12, breakDialogState.postponeButtonFontSize);
    }

    @Test @Order(25)
    @DisplayName("25. isMoved() - default")
    void isMovedByDefault() {
        assertFalse(breakDialog.isMoved());
    }

    @Test @Order(26)
    @DisplayName("26. isMoved() - default when visible")
    void isMovedByDefaultWhenVisible() {
        breakDialog.setVisible(true);
        assertFalse(breakDialog.isMoved());
    }

    @Test @Order(27)
    @DisplayName("27. isMoved() - default when invisible")
    void isMovedByDefaultWhenInvisible() {
        breakDialog.setVisible(false);
        assertFalse(breakDialog.isMoved());
    }

    @Test @Order(28)
    @DisplayName("28. isMoved() - after location change when visible")
    void isMovedAfterLocationChangeWhenVisible() {
        breakDialog.setVisible(true);
        breakDialog.setLocation(1, 1);
        assertTrue(breakDialog.isMoved());
    }

    @Test @Order(29)
    @DisplayName("29. isMoved() - after location change when invisible")
    void isMovedAfterLocationChangeWhenInvisible() {
        breakDialog.setVisible(false);
        breakDialog.setLocation(1, 1);
        assertFalse(breakDialog.isMoved());
    }

    @Test @Order(30)
    @DisplayName("30. resetLocation() - without location change")
    void resetLocationWithoutLocationChange() {
        BreakDialogStateDTO breakDialogStateBefore = getBreakDialogState();
        breakDialog.resetLocation();
        BreakDialogStateDTO breakDialogStateAfter = getBreakDialogState();

        assertEquals(breakDialogStateBefore.location, breakDialogStateAfter.location);
        assertEquals(getDefaultLocation(), breakDialogStateAfter.location);
    }

    @Test @Order(31)
    @DisplayName("31. resetLocation() - with location change")
    void resetLocationWithLocationChange() {
        Point defaultLocation = getDefaultLocation();
        Point customizedLocation = new Point(1, 1);
        breakDialog.setLocation(customizedLocation);

        BreakDialogStateDTO breakDialogStateBefore = getBreakDialogState();
        breakDialog.resetLocation();
        BreakDialogStateDTO breakDialogStateAfter = getBreakDialogState();

        assertNotEquals(breakDialogStateBefore.location, breakDialogStateAfter.location);
        assertEquals(customizedLocation, breakDialogStateBefore.location);
        assertEquals(defaultLocation, breakDialogStateAfter.location);
    }

    @Test @Order(32)
    @DisplayName("32. completeDialog() - with ordinary break point")
    void completeDialogWithOrdinaryBreakPoint() {
        BreakPoint breakPoint = BreakPointFactory.fromDescription("23:59 - message");

        BreakDialogStateDTO breakDialogStateBefore = getBreakDialogState();
        breakDialog.completeDialog(breakPoint);
        BreakDialogStateDTO breakDialogStateAfter = getBreakDialogState();

        assertEquals("", breakDialogStateBefore.timeLabelText);
        assertEquals("", breakDialogStateBefore.messageLabelText);
        assertEquals("23:59", breakDialogStateAfter.timeLabelText);
        assertEquals("message", breakDialogStateAfter.messageLabelText);
        assertTrue(breakDialogStateBefore.postponeListEnabled);
        assertTrue(breakDialogStateBefore.postponeButtonEnabled);
        assertTrue(breakDialogStateAfter.postponeListEnabled);
        assertTrue(breakDialogStateAfter.postponeButtonEnabled);
    }

    @Test @Order(33)
    @DisplayName("33. completeDialog() - with postponed break point")
    void completeDialogWithPostponedBreakPoint() {
        BreakPoint breakPoint = BreakPointFactory.fromDescription("23:55 - message");
        breakPoint.postpone(3);

        BreakDialogStateDTO breakDialogStateBefore = getBreakDialogState();
        breakDialog.completeDialog(breakPoint);
        BreakDialogStateDTO breakDialogStateAfter = getBreakDialogState();

        assertEquals("", breakDialogStateBefore.timeLabelText);
        assertEquals("", breakDialogStateBefore.messageLabelText);
        assertEquals("23:58", breakDialogStateAfter.timeLabelText);
        assertEquals("message", breakDialogStateAfter.messageLabelText);
        assertTrue(breakDialogStateBefore.postponeListEnabled);
        assertTrue(breakDialogStateBefore.postponeButtonEnabled);
        assertFalse(breakDialogStateAfter.postponeListEnabled);
        assertFalse(breakDialogStateAfter.postponeButtonEnabled);
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

    private BreakDialogStateDTO getBreakDialogState() {
        return new BreakDialogStateDTO(breakDialog);
    }
}