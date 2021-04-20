package com.arch.dayframe.controller.listeners;

import com.arch.dayframe.testutils.TestUtils;
import com.arch.dayframe.gui.DayFrameFrame;
import com.arch.dayframe.gui.dialog.BreakDialog;
import com.arch.dayframe.gui.panel.BreakPointsPanel;
import com.arch.dayframe.gui.panel.TimePanel;
import com.arch.dayframe.model.bp.BreakPoint;
import com.arch.dayframe.model.bp.BreakPoints;
import org.junit.jupiter.api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Tag("PostponementListener")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostponementListenerTest {

    private static final String BREAK_POINTS_PATH = "src/test/resources/controller/listeners/break-points.txt";

    private static BreakPoints breakPoints;
    private static DayFrameFrame dayFrame;
    private static JPanel dayFrameTimePanel;
    private static JPanel dayFrameBreakPointsPanel;
    private static BreakDialog breakDialog;
    private static PostponementListener listener;
    private static ActionEvent mockEvent;

    @BeforeAll
    static void createBreakPointsSourceFile() throws IOException {
        String fileContent = TestUtils.getBreakPointsFileContent(3, 0, 10, 20);
        TestUtils.createTestFile(BREAK_POINTS_PATH, fileContent);
    }

    @AfterAll
    static void removeBreakPointsSourceFile() throws IOException {
        TestUtils.removeTestFileAndAllEmptyDirs(BREAK_POINTS_PATH);
    }

    @BeforeEach
    void initializeFieldsAndSimulateBreakPointsTimerAction() throws IOException {
        dayFrame = new DayFrameFrame();
        breakPoints = new BreakPoints(BREAK_POINTS_PATH);
        dayFrameTimePanel = getTimePanel(dayFrame);
        dayFrameBreakPointsPanel = getBreakPointsPanel(dayFrame);
        breakDialog = new BreakDialog(dayFrame);
        listener = new PostponementListener(breakPoints, dayFrame, breakDialog);
        mockEvent = new ActionEvent(-1, -1, "");
        dayFrame.setBreakPointsList(breakPoints.getFutureWithoutNext());
        simulateBreakPointsTimerListenerAction();
    }

    /**
     * PostponementListener performs action only after BreakPointsTimerListener.
     * BreakPointsTimerListener action is performed whenever current time will be the time of next break point.
     * It moves break points and shows BreakDialog that allow to postpone break point.
     * In the simulation we do not complete dialog view, since PostponementListener do not use it.
     */
    private static void simulateBreakPointsTimerListenerAction() {
        BreakPoint recentBreakPoint = breakPoints.moveForward();
        BreakPoint nextBreakPoint = breakPoints.getNext();
        updateBreakPointsInDayFrame(recentBreakPoint, nextBreakPoint);
        breakDialog.setVisible(true);
    }

    private static void updateBreakPointsInDayFrame(BreakPoint recentBreakPoint, BreakPoint nextBreakPoint) {
        dayFrame.setRecentBreakPoint(recentBreakPoint);
        dayFrame.setNextBreakPoint(nextBreakPoint);
        dayFrame.removeBreakPointFromList(nextBreakPoint);
    }

    @Test @Order(1)
    @Tag("BreakPoints")
    @DisplayName("01. BreakPoints (Model) Test - one postponed break point after")
    void testBreakPointsPostponementAfterActionPerformed() {
        setPostponementMinutes(5);
        List<BreakPoint> breakPointsBefore = breakPoints.getBreakPointsList();
        listener.actionPerformed(mockEvent);
        List<BreakPoint> breakPointsAfter = breakPoints.getBreakPointsList();

        BreakPoint postponedBreakPointAfter = findFirstPostponedBreakPointInList(breakPointsAfter);
        assertNoPostponedBreakPointBefore(breakPointsBefore);
        assertOnePostponedBreakPointAfter(breakPointsAfter);
        identifyBreakPointByOrdinalNumber(postponedBreakPointAfter, 1);
    }

    @Test @Order(2)
    @Tag("BreakPoints")
    @DisplayName("02. BreakPoints (Model) Test - the recent break point was postponed")
    void testBreakPointsRecentAfterActionPerformed() {
        setPostponementMinutes(5);
        BreakPoint recentBreakPointBefore = breakPoints.getPrevious();
        listener.actionPerformed(mockEvent);
        BreakPoint recentBreakPointsAfter = breakPoints.getPrevious();

        BreakPoint postponedBreakPointAfter = findFirstPostponedBreakPointInList(breakPoints.getBreakPointsList());

        assertPostponedBreakPointAfterIsRecentBreakPointBefore(recentBreakPointBefore, postponedBreakPointAfter);
        assertRecentBreakPointAfterIsNull(recentBreakPointsAfter);
    }

    @Test @Order(3)
    @Tag("BreakPoints")
    @DisplayName("03. BreakPoints (Model) Test - the recent break point became the next")
    void testBreakPointsNextAfterActionPerformed() {
        setPostponementMinutes(5);
        BreakPoint nextBreakPointBefore = breakPoints.getNext();
        listener.actionPerformed(mockEvent);
        BreakPoint nextBreakPointAfter = breakPoints.getNext();

        identifyBreakPointByOrdinalNumber(nextBreakPointBefore, 2);
        identifyBreakPointByOrdinalNumber(nextBreakPointAfter, 1);
    }

    @Test @Order(4)
    @Tag("TimePanel")
    @DisplayName("04. TimePanel (View) Test - the left label is empty after listener's action")
    void testTimePanelLeftLabelAfterActionPerformedRecentBreakPointAsNext() {
        setPostponementMinutes(5);
        JLabel timePanelLeftLabel = getTimePanelLeftLabel();

        BreakPoint recentBreakPointBefore = breakPoints.getPrevious();
        String leftLabelTextBefore = timePanelLeftLabel.getText();
        listener.actionPerformed(mockEvent);
        BreakPoint recentBreakPointAfter = breakPoints.getPrevious();
        String leftLabelTextAfter = timePanelLeftLabel.getText();

        assertNotNull(recentBreakPointBefore);
        assertNull(recentBreakPointAfter);
        assertEquals(recentBreakPointBefore.getTimeValue(), leftLabelTextBefore);
        assertEquals("", leftLabelTextAfter);
        identifyBreakPointByOrdinalNumber(recentBreakPointBefore, 1);
    }

    @Test @Order(5)
    @Tag("TimePanel")
    @DisplayName("05. TimePanel (View) Test - postponed time from left label became RED time of right label")
    void testTimePanelRightLabelAfterActionPerformedRecentBreakPointAsNext() {
        setPostponementMinutes(5);
        BreakPoint recentBreakPointBefore = breakPoints.getPrevious();
        recentBreakPointBefore.postpone(5);
        JLabel timePanelRightLabel = getTimePanelRightLabel();

        BreakPoint nextBreakPointBefore = breakPoints.getNext();
        String rightLabelTextBefore = timePanelRightLabel.getText();
        Color rightLabelTextColorBefore = timePanelRightLabel.getForeground();
        listener.actionPerformed(mockEvent);
        BreakPoint nextBreakPointAfter = breakPoints.getNext();
        String rightLabelTextAfter = timePanelRightLabel.getText();
        Color rightLabelTextColorAfter = timePanelRightLabel.getForeground();

        identifyBreakPointByOrdinalNumber(nextBreakPointBefore, 2);
        identifyBreakPointByOrdinalNumber(nextBreakPointAfter, 1);
        assertEquals(nextBreakPointBefore.getTimeValue(), rightLabelTextBefore);
        assertEquals(recentBreakPointBefore.getTimeValue(), rightLabelTextAfter);
        assertEquals(Color.BLACK, rightLabelTextColorBefore);
        assertEquals(Color.RED, rightLabelTextColorAfter);
    }

    @Test @Order(6)
    @Tag("TimePanel")
    @DisplayName("06. TimePanel (View) Test - postponed time from left label didn't change the right label's time (postponed too much)")
    void testTimePanelRightLabelAfterActionPerformedRecentBreakPointAsNextAfterNext() {
        setPostponementMinutes(15);
        JLabel timePanelRightLabel = getTimePanelRightLabel();

        BreakPoint nextBreakPointBefore = breakPoints.getNext();
        String rightLabelTextBefore = timePanelRightLabel.getText();
        Color rightLabelTextColorBefore = timePanelRightLabel.getForeground();
        listener.actionPerformed(mockEvent);
        BreakPoint nextBreakPointAfter = breakPoints.getNext();
        String rightLabelTextAfter = timePanelRightLabel.getText();
        Color rightLabelTextColorAfter = timePanelRightLabel.getForeground();

        identifyBreakPointByOrdinalNumber(nextBreakPointBefore, 2);
        identifyBreakPointByOrdinalNumber(nextBreakPointAfter, 2);
        assertEquals(nextBreakPointBefore.getTimeValue(), rightLabelTextBefore);
        assertEquals(nextBreakPointBefore.getTimeValue(), rightLabelTextAfter);
        assertEquals(Color.BLACK, rightLabelTextColorBefore);
        assertEquals(Color.BLACK, rightLabelTextColorAfter);
    }

    @Test @Order(7)
    @Tag("BreakPointsPanel")
    @DisplayName("07. BreakPointsPanel (View) Test - time panel's RIGHT label became BLACK break point of the break points panel's list")
    void testDayFrameBreakPointsPanelAfterActionPerformedRecentBreakPointAsNext() {
        setPostponementMinutes(5);
        String timePanelRightLabelTextBefore = getTimePanelRightLabel().getText();

        List<JLabel> breakPointsListBefore = getBreakPointsPanelLabels();
        listener.actionPerformed(mockEvent);
        List<JLabel> breakPointsListAfter = getBreakPointsPanelLabels();

        assertEquals(1, breakPointsListBefore.size());
        assertEquals(2, breakPointsListAfter.size());
        assertEquals(timePanelRightLabelTextBefore, breakPointsListAfter.get(0).getText());
        assertEquals(breakPointsListBefore.get(0).getText(), breakPointsListAfter.get(1).getText());
        assertEquals(Color.BLACK, breakPointsListAfter.get(0).getForeground());
        assertEquals(Color.BLACK, breakPointsListAfter.get(1).getForeground());
    }

    @Test @Order(8)
    @Tag("BreakPointsPanel")
    @DisplayName("08. BreakPointsPanel (View) Test - time panel's LEFT label became RED break point of the break points panel's list")
    void testDayFrameBreakPointsPanelAfterActionPerformedRecentBreakPointAsNextAfterNext() {
        setPostponementMinutes(15);
        BreakPoint recentBreakPointBefore = breakPoints.getPrevious();
        recentBreakPointBefore.postpone(15);

        List<JLabel> breakPointsListBefore = getBreakPointsPanelLabels();
        listener.actionPerformed(mockEvent);
        List<JLabel> breakPointsListAfter = getBreakPointsPanelLabels();

        assertEquals(1, breakPointsListBefore.size());
        assertEquals(2, breakPointsListAfter.size());
        assertEquals(recentBreakPointBefore.getTimeValue(), breakPointsListAfter.get(0).getText());
        assertEquals(breakPointsListBefore.get(0).getText(), breakPointsListAfter.get(1).getText());
        assertEquals(Color.RED, breakPointsListAfter.get(0).getForeground());
        assertEquals(Color.BLACK, breakPointsListAfter.get(1).getForeground());
    }

    @Test @Order(9)
    @Tag("BreakDialog")
    @DisplayName("09. BreakDialog (View) Test - dialog became invisible and postponement list selected item was reset")
    void testBreakDialogAfterActionPerformed() {
        setPostponementMinutes(15);

        boolean isVisibleBefore = breakDialog.isVisible();
        String minutesSelectedBefore = String.valueOf(breakDialog.postponeList.getSelectedItem());
        listener.actionPerformed(mockEvent);
        boolean isVisibleAfter = breakDialog.isVisible();
        String minutesSelectedAfter = String.valueOf(breakDialog.postponeList.getSelectedItem());

        assertTrue(isVisibleBefore);
        assertFalse(isVisibleAfter);
        assertEquals("15", minutesSelectedBefore);
        assertEquals("5", minutesSelectedAfter);
    }

    private void identifyBreakPointByOrdinalNumber(BreakPoint breakPoint, int ordinalNumber) {
        String expectedMessage = String.format("break point %d", ordinalNumber);
        String actualMessage = breakPoint.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    private BreakPoint findFirstPostponedBreakPointInList(List<BreakPoint> breakPointsAfter) {
        return breakPointsAfter.stream().filter(BreakPoint::isPostponed).findFirst().orElse(null);
    }

    private void assertRecentBreakPointAfterIsNull(BreakPoint recentBreakPointsAfter) {
        assertNull(recentBreakPointsAfter);
    }

    private void assertPostponedBreakPointAfterIsRecentBreakPointBefore(BreakPoint recentBreakPointBefore, BreakPoint breakPoint) {
        assertEquals(recentBreakPointBefore.getMessage(), breakPoint.getMessage());
    }

    private void assertNoPostponedBreakPointBefore(List<BreakPoint> breakPointsBefore) {
        breakPointsBefore.forEach(bp -> assertFalse(bp.isPostponed()));
    }

    private void assertOnePostponedBreakPointAfter(List<BreakPoint> breakPointsAfter) {
        long postponedBPCount = breakPointsAfter.stream().filter(BreakPoint::isPostponed).count();
        assertEquals(1, postponedBPCount);
    }

    private static JPanel getTimePanel(DayFrameFrame dayFrame) {
        JRootPane jRootPane = (JRootPane) dayFrame.getComponent(0);
        JLayeredPane jLayeredPane = Arrays.stream(jRootPane.getComponents()).filter(c -> c instanceof JLayeredPane).map(c -> (JLayeredPane) c).findFirst().orElse(null);
        JPanel borderPanelsParent = (JPanel) jLayeredPane.getComponent(0);
        List<Component> borderPanels = Arrays.stream(borderPanelsParent.getComponents()).collect(Collectors.toList());
        return (JPanel) borderPanels.stream().filter(c -> c instanceof TimePanel).findFirst().get();
    }

    private static JPanel getBreakPointsPanel(DayFrameFrame dayFrame) {
        JRootPane jRootPane = (JRootPane) dayFrame.getComponent(0);
        JLayeredPane jLayeredPane = Arrays.stream(jRootPane.getComponents()).filter(c -> c instanceof JLayeredPane).map(c -> (JLayeredPane) c).findFirst().orElse(null);
        JPanel borderPanelsParent = (JPanel) jLayeredPane.getComponent(0);
        List<Component> borderPanels = Arrays.stream(borderPanelsParent.getComponents()).collect(Collectors.toList());
        return (JPanel) borderPanels.stream().filter(c -> c instanceof BreakPointsPanel).findFirst().get();
    }

    private JLabel getTimePanelLeftLabel() {
        return getTimePanelLabels().get(0);
    }

    private JLabel getTimePanelRightLabel() {
        return getTimePanelLabels().get(2);
    }

    private List<JLabel> getTimePanelLabels() {
        return Arrays.stream(dayFrameTimePanel.getComponents()).map(c -> (JLabel) c).collect(Collectors.toList());
    }

    private List<JLabel> getBreakPointsPanelLabels() {
        return Arrays.stream(dayFrameBreakPointsPanel.getComponents()).map(c -> (JLabel) c).collect(Collectors.toList());
    }

    private void setPostponementMinutes(int postponementMinutes) {
        if (postponementMinutes == 5)
            breakDialog.postponeList.setSelectedIndex(0);
        else if (postponementMinutes == 10)
            breakDialog.postponeList.setSelectedIndex(1);
        else if (postponementMinutes == 15)
            breakDialog.postponeList.setSelectedIndex(2);
        else
            throw new InvalidParameterException("You can choose postponement minutes only between 5, 10 and 15");
    }
}