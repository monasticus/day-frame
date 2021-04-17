package com.arch.dayframe.testutils.state;

import com.arch.dayframe.gui.dialog.BreakDialog;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BreakDialogStateDTO {

    public String title;
    public boolean modal;
    public int defaultCloseOperation;
    public int preferredSizeWidth;
    public int preferredSizeHeight;
    public boolean resizable;
    public boolean visible;
    public Point location;
    public JButton defaultButton;
    public Color background;
    public LayoutManager layout;
    public int componentsCount;
    public List<Component> labels;
    public MatteBorder timeLabelBorder;
    public String timeLabelFontFamily;
    public int timeLabelFontStyle;
    public int timeLabelFontSize;
    public GridBagConstraints timeLabelConstraints;
    public String timeLabelText;
    public Border messageLabelBorder;
    public String messageLabelFontFamily;
    public int messageLabelFontStyle;
    public int messageLabelFontSize;
    public GridBagConstraints messageLabelConstraints;
    public String messageLabelText;
    public Border postponeListBorder;
    public String postponeListFontFamily;
    public int postponeListFontStyle;
    public int postponeListFontSize;
    public GridBagConstraints postponeListConstraints;
    public int postponeListItemCount;
    public List<Integer> postponeListItems;
    public Integer postponeListSelectedItem;
    public Border buttonPanelBorder;
    public GridBagConstraints buttonPanelConstraints;
    public String okButtonText;
    public String okButtonFontFamily;
    public int okButtonFontStyle;
    public int okButtonFontSize;
    public String postponeButtonText;
    public String postponeButtonFontFamily;
    public int postponeButtonFontStyle;
    public int postponeButtonFontSize;
    public boolean postponeListEnabled;
    public boolean postponeButtonEnabled;

    public BreakDialogStateDTO(BreakDialog breakDialog) {
        title = breakDialog.getTitle();
        modal = breakDialog.isModal();
        defaultCloseOperation = breakDialog.getDefaultCloseOperation();
        preferredSizeWidth = breakDialog.getPreferredSize().width;
        preferredSizeHeight = breakDialog.getPreferredSize().height;
        resizable = breakDialog.isResizable();
        visible = breakDialog.isVisible();
        location = breakDialog.getLocation();
        defaultButton = breakDialog.getRootPane().getDefaultButton();
        background = breakDialog.mainPanel.getBackground();
        layout = breakDialog.mainPanel.getLayout();
        componentsCount = breakDialog.mainPanel.getComponentCount();
        labels = Arrays.stream(breakDialog.mainPanel.getComponents()).collect(Collectors.toList());
        timeLabelBorder = ((MatteBorder) breakDialog.timeLabel.getBorder());
        timeLabelFontFamily = breakDialog.timeLabel.getFont().getFamily();
        timeLabelFontStyle = breakDialog.timeLabel.getFont().getStyle();
        timeLabelFontSize = breakDialog.timeLabel.getFont().getSize();
        timeLabelConstraints = ((GridBagLayout) layout).getConstraints(breakDialog.timeLabel);
        timeLabelText = breakDialog.timeLabel.getText();
        messageLabelBorder = breakDialog.messageLabel.getBorder();
        messageLabelFontFamily = breakDialog.messageLabel.getFont().getFamily();
        messageLabelFontStyle = breakDialog.messageLabel.getFont().getStyle();
        messageLabelFontSize = breakDialog.messageLabel.getFont().getSize();
        messageLabelConstraints = ((GridBagLayout) layout).getConstraints(breakDialog.messageLabel);
        messageLabelText = breakDialog.messageLabel.getText();
        postponeListBorder = breakDialog.postponeList.getBorder();
        postponeListFontFamily = breakDialog.postponeList.getFont().getFamily();
        postponeListFontStyle = breakDialog.postponeList.getFont().getStyle();
        postponeListFontSize = breakDialog.postponeList.getFont().getSize();
        postponeListConstraints = ((GridBagLayout) layout).getConstraints(breakDialog.postponeList);
        postponeListItemCount = breakDialog.postponeList.getItemCount();
        postponeListItems = getPostponeListItems(breakDialog);
        postponeListSelectedItem = (Integer) breakDialog.postponeList.getSelectedItem();
        buttonPanelBorder = breakDialog.buttonPanel.getBorder();
        buttonPanelConstraints = ((GridBagLayout) layout).getConstraints(breakDialog.buttonPanel);
        okButtonText = breakDialog.okButton.getText();
        okButtonFontFamily = breakDialog.okButton.getFont().getFamily();
        okButtonFontStyle = breakDialog.okButton.getFont().getStyle();
        okButtonFontSize = breakDialog.okButton.getFont().getSize();
        postponeButtonText = breakDialog.postponeButton.getText();
        postponeButtonFontFamily = breakDialog.postponeButton.getFont().getFamily();
        postponeButtonFontStyle = breakDialog.postponeButton.getFont().getStyle();
        postponeButtonFontSize = breakDialog.postponeButton.getFont().getSize();
        postponeListEnabled = breakDialog.postponeList.isEnabled();
        postponeButtonEnabled = breakDialog.postponeButton.isEnabled();
    }

    private ArrayList<Integer> getPostponeListItems(BreakDialog breakDialog) {
        ArrayList<Integer> postponeListItems = new ArrayList<>();
        for (int i = 0; i < breakDialog.postponeList.getItemCount(); i++)
            postponeListItems.add(breakDialog.postponeList.getItemAt(i));

        return postponeListItems;
    }
}
