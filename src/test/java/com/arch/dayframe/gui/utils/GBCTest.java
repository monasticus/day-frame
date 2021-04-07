package com.arch.dayframe.gui.utils;

import org.junit.jupiter.api.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

@Tag("GBC")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GBCTest {

    private static final int DEFAULT = 0;
    private static GridBagConstraints gbc;

    @Test @Order(1)
    @DisplayName("01. new GBC(gridx, gridy)")
    void testConstructorOnlyGridXY() {
        int gridx = 98;
        int gridy = 99;
        gbc = new GBC(gridx, gridy);

        testGBC(gridx, gridy, DEFAULT, DEFAULT, DEFAULT, DEFAULT);
    }

    @Test @Order(2)
    @DisplayName("02. new GBC(gridx, gridy, gridwidth, gridheight)")
    void testConstructorEntireGrid() {
        int gridx = 96;
        int gridy = 97;
        int gridwidth = 98;
        int gridheight = 99;
        gbc = new GBC(gridx, gridy, gridwidth, gridheight);

        testGBC(gridx, gridy, gridwidth, gridheight, DEFAULT, DEFAULT);
    }

    @Test @Order(3)
    @DisplayName("03. new GBC(gridx, gridy, gridwidth, gridheight, weightx, weighty)")
    void testConstructorEntireGridAndWeightXY() {
        int gridx = 94;
        int gridy = 95;
        int gridwidth = 96;
        int gridheight = 97;
        int weightx = 98;
        int weighty = 99;
        gbc = new GBC(gridx, gridy, gridwidth, gridheight, weightx, weighty);

        testGBC(gridx, gridy, gridwidth, gridheight, weightx, weighty);
    }

    @Test @Order(4)
    @DisplayName("04. setAnchor() - correct")
    void testSetAnchor() {
        int anchor = GBC.NORTHWEST;
        gbc = new GBC(DEFAULT, DEFAULT).setAnchor(anchor);

        assertNotNull(gbc);
        assertEquals(anchor, gbc.anchor);
    }

    @Test @Order(5)
    @DisplayName("05. setAnchor() - incorrect")
    void testSetAnchorIncorrect() {
        int anchor = 999999;
        gbc = assertDoesNotThrow(() -> new GBC(DEFAULT, DEFAULT).setAnchor(anchor));

        assertNotNull(gbc);
        assertNotEquals(anchor, gbc.anchor);
        assertEquals(GBC.CENTER, gbc.anchor);
    }

    @Test @Order(6)
    @DisplayName("06. setFill() - correct")
    void testSetFill() {
        int fill = GBC.HORIZONTAL;
        gbc = new GBC(DEFAULT, DEFAULT).setFill(fill);

        assertNotNull(gbc);
        assertEquals(fill, gbc.fill);
    }

    @Test @Order(7)
    @DisplayName("07. setFill() - incorrect")
    void testSetFillIncorrect() {
        int fill = 999999;
        gbc = assertDoesNotThrow(() -> new GBC(DEFAULT, DEFAULT).setFill(fill));

        assertNotNull(gbc);
        assertNotEquals(fill, gbc.fill);
        assertEquals(GBC.NONE, gbc.fill);
    }

    @Test @Order(8)
    @DisplayName("08. setWeight()")
    void testSetWeight() {
        int weightx = 98;
        int weighty = 99;
        gbc = new GBC(DEFAULT, DEFAULT).setWeight(weightx, weighty);

        assertNotNull(gbc);
        assertEquals(weightx, gbc.weightx);
        assertEquals(weighty, gbc.weighty);
    }

    @Test @Order(9)
    @DisplayName("09. setInsets(insets)")
    void testSetInsets() {
        int insets = 99;
        gbc = new GBC(DEFAULT, DEFAULT).setInsets(insets);
        Insets expectedInsets = new Insets(insets, insets, insets, insets);

        assertNotNull(gbc);
        assertEquals(expectedInsets, gbc.insets);
    }

    @Test @Order(10)
    @DisplayName("10. setInsets(top, left, bottom, right)")
    void testSetInsetsExpanded() {
        int top = 96;
        int left = 97;
        int bottom = 98;
        int right = 99;
        gbc = new GBC(DEFAULT, DEFAULT).setInsets(top, left, bottom, right);
        Insets expectedInsets = new Insets(top, left, bottom, right);

        assertNotNull(gbc);
        assertEquals(expectedInsets, gbc.insets);
    }

    @Test @Order(11)
    @DisplayName("11. setIpad(ipad)")
    void testSetIpad() {
        int ipad = 99;
        gbc = new GBC(DEFAULT, DEFAULT).setIpad(ipad);

        assertNotNull(gbc);
        assertEquals(ipad, gbc.ipadx);
        assertEquals(ipad, gbc.ipady);
    }

    @Test @Order(12)
    @DisplayName("12. setIpad(ipadx, ipady)")
    void testSetIpadExpanded() {
        int ipadx = 98;
        int ipady = 99;
        gbc = new GBC(DEFAULT, DEFAULT).setIpad(ipadx, ipady);

        assertNotNull(gbc);
        assertEquals(ipadx, gbc.ipadx);
        assertEquals(ipady, gbc.ipady);
    }

    @Test @Order(13)
    @DisplayName("13. Chaining Test")
    void testChaining() {
        gbc = assertDoesNotThrow(() ->
                new GBC(DEFAULT, DEFAULT)
                .setAnchor(DEFAULT)
                .setFill(DEFAULT)
                .setWeight(DEFAULT, DEFAULT)
                .setInsets(DEFAULT)
                .setInsets(DEFAULT, DEFAULT, DEFAULT, DEFAULT)
                .setIpad(DEFAULT)
                .setIpad(DEFAULT, DEFAULT)
        );
    }

    private void testGBC(int gridx, int gridy, int gridwidth, int gridheight, double weightx, double weighty) {
        assertNotNull(gbc);
        assertEquals(gridx, gbc.gridx);
        assertEquals(gridy, gbc.gridy);
        assertEquals(gridwidth, gbc.gridwidth);
        assertEquals(gridheight, gbc.gridheight);
        assertEquals(weightx, gbc.weightx);
        assertEquals(weighty, gbc.weighty);
    }
}