/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.swingb;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.SwingConstants;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationActionMap;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.swingbinding.JComboBoxBinding;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.binding.EntityListBinder;
import com.cosmos.swingb.validation.Validatable;
import org.jdesktop.application.Task;

/**
 *
 * @author Miro
 */
public class JBComboList extends JXPanel
        implements ItemSelectable, ActionListener, Validatable, EntityListBinder {

    private JXButton unselectButton;
    private JXButton lookupButton;
    private JBComboBox comboBox;
    private JBPanel buttonPanel;

    public JBComboList() {
        initComponents();
    }

    private void initComponents() {
        unselectButton = new JXButton();
        lookupButton = new JXButton();
        comboBox = new ComboBox();

        setName("JBComboList"); // NOI18N
        setLayout(new BorderLayout());

        buttonPanel = new JBPanel();
        buttonPanel.setLayout(new BorderLayout());

        unselectButton.setAction(getApplicationActionMap().get("unselectButtonAction")); // NOI18N
        unselectButton.setAlignmentX(0.5F);
        unselectButton.setAlignmentY(0.5F);
        unselectButton.setHorizontalAlignment(SwingConstants.CENTER);
        unselectButton.setVerticalAlignment(SwingConstants.CENTER);
        unselectButton.setHorizontalTextPosition(SwingConstants.CENTER);
        unselectButton.setVerticalTextPosition(SwingConstants.CENTER);
        unselectButton.setName("unselectButton"); // NOI18N
        unselectButton.setPreferredSize(new Dimension(25, 25));
        setEnabledUnselectButton(false);

        lookupButton.setAction(getApplicationActionMap().get("lookupButtonAction")); // NOI18N
        lookupButton.setAlignmentX(0.5F);
        lookupButton.setAlignmentY(0.5F);
        lookupButton.setHorizontalAlignment(SwingConstants.CENTER);
        lookupButton.setVerticalAlignment(SwingConstants.CENTER);
        lookupButton.setHorizontalTextPosition(SwingConstants.CENTER);
        lookupButton.setVerticalTextPosition(SwingConstants.CENTER);
        lookupButton.setName("lookupButton"); // NOI18N
        lookupButton.setPreferredSize(new Dimension(25, 25));

        buttonPanel.add(unselectButton, BorderLayout.WEST);
        buttonPanel.add(lookupButton, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.EAST);

        ResourceMap rm = getResourceMap();
        Font font = rm.getFont("comboBox.font");
        if (font != null) {
            comboBox.setFont(font); // NOI18N
        }
        comboBox.setName("comboBox"); // NOI18N
        comboBox.addItemListener(new ComboListItemListener(), true);
        add(comboBox, BorderLayout.CENTER);
    }

    public JBComboBox getComboBox() {
        return comboBox;
    }

    public JXButton getLookupButton() {
        return lookupButton;
    }

    public JXButton getUnselectButton() {
        return unselectButton;
    }

    @Action
    public void lookupButtonAction() {
        SelectableListDialog selectableListDialog;
        if ((selectableListDialog = comboBox.getSelectableListDialog()) != null) {
            selectableListDialog.setEditable(isEditable());
            Object selectedItem = comboBox.getSelectedItem();
            selectableListDialog.setSelectedRowObject(selectedItem);
            DialogResponse response = selectableListDialog.showDialog(this);

            if (isEditable()) {
                ObservableList observableData = comboBox.getData();
                observableData.clear();
                observableData.addAll(selectableListDialog.getListData());

                if (DialogResponse.SELECT.equals(response)) {
                    Object result = selectableListDialog.getSelectedRowObject();
                    comboBox.setSelectedItem(result);
                    comboBox.addItem(result);
                } else {
                    comboBox.setSelectedItem(selectedItem);
                }
            }
        }
    }

    protected Object getPropertyValue() {
        return comboBox.getPropertyValue();
    }

    protected void setPropertyValue(Object value) {
        comboBox.setPropertyValue(value);
    }

    @Action
    public void unselectButtonAction() {
        comboBox.setSelectedItem(null);
        if(getPropertyValue() != null) {
            setPropertyValue(null);
        }
    }

    @Override
    public JComboBoxBinding bind(
            BindingGroup bindingGroup,
            SelectableListDialog selectableListDialog,
            Object beanEntity,
            PropertyDetails propertyDetails) {
        return bind(
                bindingGroup,
                selectableListDialog,
                beanEntity,
                propertyDetails,
                (ObjectToStringConverter)null);
    }

    @Override
    public JComboBoxBinding bind(
            BindingGroup bindingGroup,
            SelectableListDialog selectableListDialog,
            Object beanEntity,
            PropertyDetails propertyDetails,
            UpdateStrategy updateStrategy) {
        return bind(bindingGroup, selectableListDialog, beanEntity, propertyDetails, null, updateStrategy);
    }

    protected String getExpression(PropertyDetails propertyDetails) {
        String expression;
        if ((expression = propertyDetails.getCustomDisplay()) != null && expression.length() > 0) {
            return expression;
        }

        return "${" + propertyDetails.getPropertyName() + "}";
    }

    public JComboBoxBinding bind(
            BindingGroup bindingGroup,
            SelectableListDialog selectableListDialog,
            Object beanEntity,
            PropertyDetails propertyDetails,
            ObjectToStringConverter converter) {
        return bind(
                bindingGroup,
                selectableListDialog,
                beanEntity,
                propertyDetails,
                converter,
                AutoBinding.UpdateStrategy.READ_WRITE);
    }

    public JComboBoxBinding bind(
            BindingGroup bindingGroup,
            SelectableListDialog selectableListDialog,
            Object beanEntity,
            PropertyDetails propertyDetails,
            ObjectToStringConverter converter,
            AutoBinding.UpdateStrategy updateStrategy) {
        if(converter != null) {
            comboBox.setConverter(converter);
        }

        ComboListBindingValidationListener bindingValidationListener =
                new ComboListBindingValidationListener(this, propertyDetails);
        JComboBoxBinding binding;
        if((binding = comboBox.bind(
                bindingGroup,
                selectableListDialog,
                beanEntity,
                propertyDetails,
                updateStrategy,
                getName(),
                bindingValidationListener)) == null) {
            return null;
        }

        return binding;
    }

    protected void setEnabledUnselectButton(boolean enabled) {
        if (comboBox.getSelectedItem() == null) {
            unselectButton.setEnabled(false);
        } else {
            unselectButton.setEnabled(enabled);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        comboBox.setEnabled(enabled);
        setEnabledUnselectButton(enabled);
        lookupButton.setEnabled(enabled);
    }

    public boolean isEditable() {
        return comboBox.isEditable();
    }

    public void setEditable(boolean editable) {
        comboBox.setEditable(editable);
        setEnabledUnselectButton(editable);
        lookupButton.setEnabled(editable);

        SelectableListDialog selectableListDialog;
        if ((selectableListDialog = comboBox.getSelectableListDialog()) != null) {
            selectableListDialog.setEditable(editable);
            selectableListDialog.setVisibleSelectButtons(editable);
        }
    }

    public ObservableList getData() {
        return comboBox.getData();
    }

    public String getPropertyName() {
        return comboBox.getPropertyName();
    }

    public Object getBeanEntity() {
        return comboBox.getBeanEntity();
    }

    public JComboBoxBinding getComboBoxBinding() {
        return comboBox.getComboBoxBinding();
    }

    @Override
    public Object[] getSelectedObjects() {
        return comboBox.getSelectedObjects();
    }

    public void addItemListener(ItemListener listener, boolean ignoreSelectEvents) {
        comboBox.addItemListener(listener, ignoreSelectEvents);
    }

    @Override
    public void addItemListener(ItemListener listener) {
        comboBox.addItemListener(listener);
    }

    @Override
    public void removeItemListener(ItemListener listener) {
        comboBox.removeItemListener(listener);
    }

    public Object getSelectedItem() {
        return comboBox.getSelectedItem();
    }

    public void setSelectedItem(Object selectedItem) {
        comboBox.setSelectedItem(selectedItem);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        comboBox.actionPerformed(event);
    }

    public void addActionListener(ActionListener listener) {
        comboBox.addActionListener(listener);
    }

    public void removeActionListener(ActionListener listener) {
        comboBox.removeActionListener(listener);
    }

    public ActionListener[] getActionListeners() {
        return comboBox.getActionListeners();
    }

    public ApplicationContext getContext() {
        return comboBox.getContext();
    }

    public ApplicationActionMap getApplicationActionMap() {
        return comboBox.getApplicationActionMap();
    }

    protected Class getResourceStartClass() {
        return this.getClass();
    }

    protected Class getResourceStopClass() {
        return this.getClass();
    }

    protected Class getActionsClass() {
        return JBComboList.class;
    }

    protected Object getActionsObject() {
        return this;
    }

    @Override
    public ResourceMap getResourceMap() {
        return comboBox.getResourceMap();
    }

    public void setResourceMap(ResourceMap resourceMap) {
        comboBox.setResourceMap(resourceMap);
    }

    public Application getApplication() {
        return comboBox.getApplication();
    }

    public void setApplication(Application application) {
        comboBox.setApplication(application);
    }

    @Override
    public void setStyleRequired(String tooltip) {
        comboBox.setStyleRequired(tooltip);
    }

    public void setStyleInvalid() {
        comboBox.setStyleInvalid();
    }

    @Override
    public void setStyleInvalid(String tooltip) {
        comboBox.setStyleInvalid(tooltip);
    }

    @Override
    public void setStyleValid() {
        comboBox.setStyleValid();
    }

    @Override
    public void setStyleNormal() {
        comboBox.setStyleNormal();
    }

    public void initUnbound(
            SelectableListDialog selectableListDialog,
            ObjectToStringConverter converter) {
        ObservableList observableData = comboBox.getData();
        comboBox.removeAllItems();
        observableData.clear();
        comboBox.setConverter(converter);
        comboBox.setSelectableListDialog(selectableListDialog);
        observableData.addAll(selectableListDialog.getListData());

        comboBox.setSelectedItem(null);
    }

    public void initUnbound(SelectableListDialog selectableListDialog) {
        initUnbound(selectableListDialog, null);
    }

    public ObjectToStringConverter getConverter() {
        return comboBox.getConverter();
    }

    public void setConverter(ObjectToStringConverter converter) {
        comboBox.setConverter(converter);
    }

    public Binding getBinding() {
        return comboBox.getBinding();
    }

    @Override
    public Task refresh() {
        return comboBox.refresh();
    }

    @Override
    public void clear() {
        setSelectedItem(null);
    }

    private class ComboListItemListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent event) {
            if (isEnabled()) {
                setEnabledUnselectButton(comboBox.getSelectedItem() != null);
            } else {
                setEnabledUnselectButton(false);
            }
        }
    }

    protected class ComboBox extends JBComboBox {

        @Override
        protected Class getResourceStartClass() {
            return JBComboList.this.getResourceStartClass();
        }

        @Override
        protected Class getResourceStopClass() {
            return JBComboList.this.getResourceStopClass();
        }

        @Override
        protected Class getActionsClass() {
            return JBComboList.this.getActionsClass();
        }

        @Override
        protected Object getActionsObject() {
            return JBComboList.this.getActionsObject();
        }
    }
}
