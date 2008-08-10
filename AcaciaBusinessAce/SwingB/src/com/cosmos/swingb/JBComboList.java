/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationActionMap;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.AutoBinding;
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
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.listeners.ComboListEventListener;
import com.cosmos.swingb.validation.Validatable;

/**
 *
 * @author Miro
 */
public class JBComboList
    extends JXPanel
    implements ItemSelectable, ActionListener, Validatable
{
    private Application application;
    private ApplicationContext applicationContext;
    private ApplicationActionMap applicationActionMap;
    private ResourceMap resourceMap;

    private JXButton lookupButton;
    private JBComboBox comboBox;

    //edit-able by default
    private boolean editable = true;

    private ObservableList observableData;
    private String propertyName;
    private Object beanEntity;
    private JComboBoxBinding comboBoxBinding;

    private SelectableListDialog selectableListDialog;
    private ObjectToStringConverter converter;

    public JBComboList()
    {
        initComponents();
    }

    public JBComboList(Class<? extends Application> applicationClass)
    {
        this(Application.getInstance(applicationClass));
    }

    public JBComboList(Application application)
    {
        this();
        this.application = application;
    }

    private void initComponents()
    {
        lookupButton = new JXButton();
        comboBox = new ListComboBox();

        setName("JBComboList"); // NOI18N
        setLayout(new BorderLayout());

        lookupButton.setAction(getApplicationActionMap().get("lookupButtonAction")); // NOI18N
        lookupButton.setAlignmentX(0.5F);
        lookupButton.setAlignmentY(0.5F);
        lookupButton.setHorizontalAlignment(SwingConstants.CENTER);
        lookupButton.setVerticalAlignment(SwingConstants.CENTER);
        lookupButton.setHorizontalTextPosition(SwingConstants.CENTER);
        lookupButton.setVerticalTextPosition(SwingConstants.CENTER);
        lookupButton.setName("lookupButton"); // NOI18N
        lookupButton.setPreferredSize(new Dimension(25, 25));
        add(lookupButton, BorderLayout.EAST);

        ResourceMap rm = getResourceMap();
        Font font = rm.getFont("comboBox.font");
        if(font != null)
            comboBox.setFont(font); // NOI18N
        comboBox.setName("comboBox"); // NOI18N
        add(comboBox, BorderLayout.CENTER);
    }

    public JBComboBox getComboBox()
    {
        return comboBox;
    }

    public JXButton getLookupButton()
    {
        return lookupButton;
    }

    @Action
    public void lookupButtonAction()
    {
        if(selectableListDialog != null)
        {
            selectableListDialog.setEditable(isEditable());
            Object selectedItem = comboBox.getSelectedItem();
            selectableListDialog.setSelectedRowObject(selectedItem);
            DialogResponse response = selectableListDialog.showDialog(this);
            
            if ( isEditable() ){
                List listData = selectableListDialog.getListData();
                observableData.clear();
                observableData.addAll(listData);
    
                if(DialogResponse.SELECT.equals(response))
                {
                    Object result = selectableListDialog.getSelectedRowObject();
                    comboBox.setSelectedItem(result);
                    // Miro Debug
                    // org.jdesktop.swingbinding.JComboBoxBinding$BindingComboBoxModel
                    // RuntimeException: Cannot use this method with a non-Mutable data model.
                    // at javax.swing.JComboBox.checkMutableComboBoxModel(JComboBox.java:775)
                    // at javax.swing.JComboBox.addItem(JComboBox.java:697)
                    // at com.cosmos.swingb.JBComboList.lookupButtonAction(JBComboList.java:154)
                    comboBox.addItem(result);
                }
                else
                {
                    comboBox.setSelectedItem(selectedItem);
                }
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

    public JComboBoxBinding bind(
            BindingGroup bindingGroup,
            SelectableListDialog selectableListDialog,
            Object beanEntity,
            PropertyDetails propertyDetails)
    {
        return bind(bindingGroup, selectableListDialog, beanEntity, propertyDetails, null);
    }

    public JComboBoxBinding bind(
            BindingGroup bindingGroup,
            SelectableListDialog selectableListDialog,
            Object beanEntity,
            PropertyDetails propertyDetails,
            ObjectToStringConverter converter)
    {
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
            AutoBinding.UpdateStrategy updateStrategy)
    {
        if(propertyDetails == null || propertyDetails.isHiden())
        {
            setEditable(false);
            setEnabled(false);
            return null;
        }

        if(converter == null)
            converter = new BeanResourceToStringConverter(getApplication());
        AutoCompleteDecorator.decorate(comboBox, converter);
        this.selectableListDialog = selectableListDialog;

        List data = new ArrayList(selectableListDialog.getListData());
        observableData = ObservableCollections.observableList(data);
        this.propertyName = propertyDetails.getPropertyName();
        this.beanEntity = beanEntity;

        comboBoxBinding = SwingBindings.createJComboBoxBinding(
                updateStrategy,
                observableData,
                comboBox);
        bindingGroup.addBinding(comboBoxBinding);
        
        ELProperty elProperty = ELProperty.create("${" + propertyName + "}");
        BeanProperty beanProperty = BeanProperty.create("selectedItem");
        Binding binding = Bindings.createAutoBinding(
                updateStrategy,
                beanEntity,
                elProperty,
                comboBox,
                beanProperty);

        Validator validator = propertyDetails.getValidator();
        if(validator != null)
        {
            binding.setValidator(validator);
        }
        binding.addBindingListener(new BindingValidationListener(this));

        bindingGroup.addBinding(binding);

        setEditable(propertyDetails.isEditable());
        setEnabled(!propertyDetails.isReadOnly());

        return comboBoxBinding;
    }

    public boolean isEditable()
    {
        return editable;
    }

    public void setEditable(boolean editable)
    {
        this.editable = editable;
        comboBox.setEditable(editable);
        comboBox.setEnabled(editable);
        if ( selectableListDialog!=null )
            selectableListDialog.setVisibleSelectButtons(editable);
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

    public JComboBoxBinding getComboBoxBinding()
    {
        return comboBoxBinding;
    }

    public Object[] getSelectedObjects()
    {
        return comboBox.getSelectedObjects();
    }

    public void addItemListener(ItemListener listener)
    {
        comboBox.addItemListener(new ComboListEventListener(listener));
    }

    public void removeItemListener(ItemListener listener)
    {
        comboBox.removeItemListener(listener);
    }

    public Object getSelectedItem()
    {
        return comboBox.getSelectedItem();
    }

    public void setSelectedItem(Object selectedItem)
    {
        comboBox.setSelectedItem(selectedItem);
    }

    public void actionPerformed(ActionEvent event)
    {
        comboBox.actionPerformed(event);
    }

    public void addActionListener(ActionListener listener)
    {
        comboBox.addActionListener(listener);
    }

    public void removeActionListener(ActionListener listener)
    {
        comboBox.removeActionListener(listener);
    }

    public ActionListener[] getActionListeners()
    {
        return comboBox.getActionListeners();
    }

    public ApplicationContext getContext()
    {
        if(applicationContext == null)
        {
            Application app = getApplication();
            if(app != null)
            {
                applicationContext = app.getContext();
            }
        }

        return applicationContext;
    }

    public ApplicationActionMap getApplicationActionMap()
    {
        if(applicationActionMap == null)
        {
            ApplicationContext context = getContext();
            if(context != null)
            {
                applicationActionMap = context.getActionMap(getActionsClass(), getActionsObject());
            }
        }

        return applicationActionMap;
    }

    protected Class getResourceStartClass()
    {
        return this.getClass();
    }

    protected Class getResourceStopClass()
    {
        return this.getClass();
    }

    protected Class getActionsClass()
    {
        return JBComboList.class;
    }

    protected Object getActionsObject()
    {
        return this;
    }

    public ResourceMap getResourceMap()
    {
        if(resourceMap == null)
        {
            ApplicationContext context = getContext();
            if(context != null)
            {
                resourceMap = context.getResourceMap(getResourceStartClass(), getResourceStopClass());
            }
        }

        return resourceMap;
    }

    public void setResourceMap(ResourceMap resourceMap)
    {
        this.resourceMap = resourceMap;
    }

    public Application getApplication()
    {
        if(application == null)
            application = Application.getInstance();

        return application;
    }

    public void setApplication(Application application)
    {
        this.application = application;
    }

    public void setStyleRequired(String tooltip)
    {
        comboBox.setToolTipText(tooltip);
        Color color = getResourceMap().getColor("validation.field.required.background");
        comboBox.getEditor().getEditorComponent().setBackground(color);
    }

    public void setStyleInvalid()
    {
        setStyleInvalid(null);
    }

    public void setStyleInvalid(String tooltip)
    {
        comboBox.setToolTipText(tooltip);
        Color color = getResourceMap().getColor("validation.field.invalid.background");
        comboBox.getEditor().getEditorComponent().setBackground(color);
    }

    public void setStyleValid()
    {
        comboBox.setToolTipText(null);
        Color color = getResourceMap().getColor("validation.field.valid.background");
        comboBox.getEditor().getEditorComponent().setBackground(color);
    }

    public void setStyleNormal()
    {
        comboBox.setToolTipText(null);
        Color color = getResourceMap().getColor("validation.field.normal.background");
        comboBox.getEditor().getEditorComponent().setBackground(color);
    }

    private class ListComboBox
        extends JBComboBox
    {

        @Override
        public void setSelectedItem(Object anObject)
        {
            Object oldSelectedItem = getSelectedItem();
            super.setSelectedItem(anObject);
            Object newSelectedItem = getSelectedItem();
            if(oldSelectedItem != null)
            {
                if(!oldSelectedItem.equals(newSelectedItem))
                    selectedItemChanged(newSelectedItem);
            }
            else if(newSelectedItem != null)
            {
                selectedItemChanged(newSelectedItem);
            }
        }

        protected void selectedItemChanged(Object selectedItem)
        {
            ItemEvent event;
            if(selectedItem != null)
                event = new ItemEvent(this,
                    ItemEvent.ITEM_STATE_CHANGED,
                    selectedItem,
                    ItemEvent.SELECTED + 0x700);
            else
                event = new ItemEvent(this,
                    ItemEvent.ITEM_STATE_CHANGED,
                    selectedItem,
                    ItemEvent.DESELECTED + 0x700);
            fireItemStateChanged(event);
        }
    }


    public void initUnbound(
            SelectableListDialog selectableListDialog,
            ObjectToStringConverter converter)
    {
        
        comboBox.removeAllItems();
        
        if(converter == null)
            converter = new BeanResourceToStringConverter(getApplication());
        
        this.converter = converter;
        
        this.selectableListDialog = selectableListDialog;

        List data = new ArrayList(selectableListDialog.getListData());
        observableData = ObservableCollections.observableList(data);

        for (Object obj : observableData) {
            comboBox.addItem(obj);
        }
        comboBox.setSelectedIndex(-1);
        
        AutoCompleteDecorator.decorate(comboBox, converter);
    }

    public void initUnbound(SelectableListDialog selectableListDialog) {
        initUnbound(selectableListDialog, null);
    }

    public ObjectToStringConverter getConverter() {
        return converter; 
    }

    public void setConverter(ObjectToStringConverter converter) {
        this.converter = converter;
    }    
}
