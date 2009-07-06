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
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationActionMap;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.beansbinding.Validator;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.swingbinding.JComboBoxBinding;
import org.jdesktop.swingbinding.SwingBindings;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.binding.EntityListBinder;
import com.cosmos.swingb.validation.Validatable;
import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 * @author Miro
 */
public class JBComboList
        extends JXPanel
        implements ItemSelectable, ActionListener, Validatable, EntityListBinder {

    private Application application;
    private ApplicationContext applicationContext;
    private ApplicationActionMap applicationActionMap;
    private ResourceMap resourceMap;
    private JXButton unselectButton;
    private JXButton lookupButton;
    private JBComboBox comboBox;
    private JBPanel buttonPanel;
    //edit-able by default
    private boolean editable = true;
    private ObservableList observableData;
    private String propertyName;
    private Object beanEntity;
    private JComboBoxBinding comboBoxBinding;
    private SelectableListDialog selectableListDialog;
    private Binding binding;

    public JBComboList() {
        initComponents();
    }

    private void initComponents() {
        unselectButton = new JXButton();
        lookupButton = new JXButton();
        comboBox = new JBComboBox();

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
        if (selectableListDialog != null) {
            selectableListDialog.setEditable(isEditable());
            Object selectedItem = comboBox.getSelectedItem();
            selectableListDialog.setSelectedRowObject(selectedItem);
            DialogResponse response = selectableListDialog.showDialog(this);

            if (isEditable()) {
                List listData = selectableListDialog.getListData();
                observableData.clear();
                observableData.addAll(listData);

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
        try {
            return PropertyUtils.getProperty(beanEntity, propertyName);
        } catch(Exception ex) {
            throw new RuntimeException("beanEntity=" + beanEntity + ", propertyName=" + propertyName, ex);
        }
    }

    protected void setPropertyValue(Object value) {
        try {
            PropertyUtils.setProperty(beanEntity, propertyName, value);
        } catch(Exception ex) {
            throw new RuntimeException("beanEntity=" + beanEntity + ", propertyName=" + propertyName +
                    ", value=" + value, ex);
        }
    }

    @Action
    public void unselectButtonAction() {
        comboBox.setSelectedItem(null);
        if(getPropertyValue() != null) {
            setPropertyValue(null);
        }
    }

    private class ComboListItemListener
            implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent event) {
            if (isEnabled()) {
                setEnabledUnselectButton(comboBox.getSelectedItem() != null);
            } else {
                setEnabledUnselectButton(false);
            }
        }
    }

    /*
    public JComboBoxBinding bind(
    BindingGroup bindingGroup,
    List data,
    Object beanEntity,
    PropertyDetails propertyDetails,
    ObjectToStringConverter converter)
    {
    AutoCompleteDecorator.decorate(this, converter);
    return super.bind(bindingGroup, data, beanEntity, propertyDetails, AutoBinding.UpdateStrategy.READ_WRITE);
    }*/
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
        if (propertyDetails == null || propertyDetails.isHiden()) {
            setEditable(false);
            setEnabled(false);
            return null;
        }

        if(converter != null) {
            setConverter(converter);
        }

        this.selectableListDialog = selectableListDialog;

        List data = new ArrayList(selectableListDialog.getListData());
        observableData = ObservableCollections.observableList(data);
        this.propertyName = propertyDetails.getPropertyName();
        this.beanEntity = beanEntity;

        String name;
        if((name = getName()) != null) {
            name = name + ".ComboBox";
        }
        comboBoxBinding = SwingBindings.createJComboBoxBinding(
                updateStrategy,
                observableData,
                comboBox,
                name);
        bindingGroup.addBinding(comboBoxBinding);

        name = getName();
        ELProperty displayELProperty = ELProperty.create("${" + propertyName + "}");
        BeanProperty beanProperty = BeanProperty.create("selectedItem");
        binding = Bindings.createAutoBinding(
                updateStrategy,
                beanEntity,
                displayELProperty,
                comboBox,
                beanProperty,
                name);
        Validator validator = propertyDetails.getValidator();
        if (validator != null) {
            binding.setValidator(validator);
        }
        binding.addBindingListener(new BindingValidationListener(this));

        bindingGroup.addBinding(binding);

        setEditable(propertyDetails.isEditable());
        setEnabled(!propertyDetails.isReadOnly());

        return comboBoxBinding;
    }

    public boolean isEditable() {
        return editable;
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

    public void setEditable(boolean editable) {
        this.editable = editable;
        comboBox.setEditable(editable);
        setEnabledUnselectButton(editable);
        lookupButton.setEnabled(editable);

        if (selectableListDialog != null) {
            selectableListDialog.setEditable(editable);
            selectableListDialog.setVisibleSelectButtons(editable);
        }
    }

    public ObservableList getData() {
        return observableData;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Object getBeanEntity() {
        return beanEntity;
    }

    public JComboBoxBinding getComboBoxBinding() {
        return comboBoxBinding;
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
        if (applicationContext == null) {
            Application app = getApplication();
            if (app != null) {
                applicationContext = app.getContext();
            }
        }

        return applicationContext;
    }

    public ApplicationActionMap getApplicationActionMap() {
        if (applicationActionMap == null) {
            ApplicationContext context = getContext();
            if (context != null) {
                applicationActionMap = context.getActionMap(getActionsClass(), getActionsObject());
            }
        }

        return applicationActionMap;
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
        if (resourceMap == null) {
            ApplicationContext context = getContext();
            if (context != null) {
                resourceMap = context.getResourceMap(getResourceStartClass(), getResourceStopClass());
            }
        }

        return resourceMap;
    }

    public void setResourceMap(ResourceMap resourceMap) {
        this.resourceMap = resourceMap;
    }

    public Application getApplication() {
        if (application == null) {
            application = Application.getInstance();
        }

        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    @Override
    public void setStyleRequired(String tooltip) {
        comboBox.setToolTipText(tooltip);
        Color color = getResourceMap().getColor("validation.field.required.background");
        comboBox.getEditor().getEditorComponent().setBackground(color);
    }

    public void setStyleInvalid() {
        setStyleInvalid(null);
    }

    @Override
    public void setStyleInvalid(String tooltip) {
        comboBox.setToolTipText(tooltip);
        Color color = getResourceMap().getColor("validation.field.invalid.background");
        comboBox.getEditor().getEditorComponent().setBackground(color);
    }

    @Override
    public void setStyleValid() {
        comboBox.setToolTipText(null);
        Color color = getResourceMap().getColor("validation.field.valid.background");
        comboBox.getEditor().getEditorComponent().setBackground(color);
    }

    @Override
    public void setStyleNormal() {
        comboBox.setToolTipText(null);
        Color color = getResourceMap().getColor("validation.field.normal.background");
        comboBox.getEditor().getEditorComponent().setBackground(color);
    }

    public void initUnbound(
            SelectableListDialog selectableListDialog,
            ObjectToStringConverter converter) {
        comboBox.removeAllItems();
        setConverter(converter);
        this.selectableListDialog = selectableListDialog;

        List data = new ArrayList(selectableListDialog.getListData());
        observableData = ObservableCollections.observableList(data);

        for (Object obj : observableData) {
            comboBox.addItem(obj);
        }
        comboBox.setSelectedIndex(-1);
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
        return binding;
    }

    @Override
    public void refresh() {
        if (selectableListDialog != null) {
            Object selectedItem = comboBox.getSelectedItem();

            List listData = selectableListDialog.getListData();
            observableData.clear();
            observableData.addAll(listData);

            if(selectedItem != null && listData.contains(selectedItem)) {
                comboBox.setSelectedItem(selectedItem);
            }
        }
    }
}
