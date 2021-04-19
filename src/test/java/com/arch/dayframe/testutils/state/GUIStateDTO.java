package com.arch.dayframe.testutils.state;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GUIStateDTO {

    protected LayoutManager getLayoutManager(Container container) {
        return container.getLayout();
    }

    protected Border getBorder(JComponent component) {
        return component.getBorder();
    }

    protected GridBagConstraints getGridBagConstraints(LayoutManager layout, Component component) {
        return ((GridBagLayout) layout).getConstraints(component);
    }

    protected Color getBackground(Component component) {
        return component.getBackground();
    }

    protected Point getLocation(Component component) {
        return component.getLocation();
    }

    protected boolean isModal(Dialog dialog) {
        return dialog.isModal();
    }

    protected boolean isResizable(Dialog dialog) {
        return dialog.isResizable();
    }

    protected boolean isVisible(Component component) {
        return component.isVisible();
    }

    protected boolean isEnabled(Component component) {
        return component.isEnabled();
    }

    protected int getDefaultCloseOperation(JDialog dialog) {
        return dialog.getDefaultCloseOperation();
    }

    protected JButton getDefaultButton(JDialog dialog) {
        return dialog.getRootPane().getDefaultButton();
    }

    protected int getPreferredSizeWidth(Component component) {
        return component.getPreferredSize().width;
    }

    protected int getPreferredSizeHeight(Component component) {
        return component.getPreferredSize().height;
    }

    protected List<Component> getComponents(Container container) {
        return Arrays.stream(container.getComponents()).collect(Collectors.toList());
    }

    protected int getComponentCount(JComponent component) {
        return component.getComponentCount();
    }

    protected ArrayList<Integer> getListItems(JComboBox<Integer> list) {
        ArrayList<Integer> postponeListItems = new ArrayList<>();
        for (int i = 0; i < getListItemCount(list); i++)
            postponeListItems.add(list.getItemAt(i));

        return postponeListItems;
    }

    protected int getListItemCount(JComboBox<Integer> list) {
        return list.getItemCount();
    }

    protected String getListSelectedItem(JComboBox<Integer> list) {
        return String.valueOf(list.getSelectedItem());
    }

    protected int getFontStyle(Component component) {
        return component.getFont().getStyle();
    }

    protected int getFontSize(Component component) {
        return component.getFont().getSize();
    }

    protected String getFontFamily(Component component) {
        return component.getFont().getFamily();
    }

    protected String getTitle(Dialog dialog) {
        return dialog.getTitle();
    }

    protected String getText(JLabel label) {
        return label.getText();
    }

    protected String getText(AbstractButton button) {
        return button.getText();
    }
}
