/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.swingb;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxEditor;

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
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.binding.EnumerationBinder;
import com.cosmos.swingb.binding.Refreshable;
import com.cosmos.swingb.listeners.ComboListEventListener;
import com.cosmos.swingb.menus.JBContextMenuCreaetor;
import com.cosmos.swingb.validation.Validatable;
import org.apache.commons.beanutils.PropertyUtils;
import org.jdesktop.application.Task;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Miro
 */
public class JBComboBox extends JComboBox implements Validatable, EnumerationBinder {

    public static final String SELECTED_ITEM = "selectedItem";
    private Application application;
    private ApplicationContext applicationContext;
    private ApplicationActionMap applicationActionMap;
    private ResourceMap resourceMap;
    private ObservableList observableData;
    private String propertyName;
    private Object beanEntity;
    private JComboBoxBinding comboBoxBinding;
    private SelectableListDialog selectableListDialog;

    public JBComboBox() {
        super();
        init();
    }

    public JBComboBox(Object[] items) {
        super(items);
        init();
    }

    private void init() {
        setEditor(new JBComboBoxEditor());
        setRenderer(new CustomCellRenderer());
        setPrototypeDisplayValue("1234567890");
    }

    @Override
    public JComboBoxBinding bind(
            BindingGroup bindingGroup,
            SelectableListDialog selectableListDialog,
            Object beanEntity,
            PropertyDetails propertyDetails) {
        this.selectableListDialog = selectableListDialog;
        return bind(bindingGroup, selectableListDialog.getListData(), beanEntity, propertyDetails);
    }

    @Override
    public JComboBoxBinding bind(
            BindingGroup bindingGroup,
            SelectableListDialog selectableListDialog,
            Object beanEntity,
            PropertyDetails propertyDetails,
            UpdateStrategy updateStrategy) {
        this.selectableListDialog = selectableListDialog;
        return bind(bindingGroup, selectableListDialog.getListData(), beanEntity, propertyDetails, updateStrategy);
    }

    JComboBoxBinding bind(
            BindingGroup bindingGroup,
            SelectableListDialog selectableListDialog,
            Object beanEntity,
            PropertyDetails propertyDetails,
            UpdateStrategy updateStrategy,
            String componentName,
            BindingValidationListener bindingValidationListener) {
        this.selectableListDialog = selectableListDialog;
        return bind(bindingGroup, selectableListDialog.getListData(),
                beanEntity, propertyDetails, updateStrategy,
                componentName, bindingValidationListener);
    }

    @Override
    public JComboBoxBinding bind(
            BindingGroup bindingGroup,
            List data,
            Object beanEntity,
            PropertyDetails propertyDetails) {
        return bind(bindingGroup, data, beanEntity, propertyDetails, propertyDetails.getUpdateStrategy());
    }

    @Override
    public JComboBoxBinding bind(
            BindingGroup bindingGroup,
            List data,
            Object beanEntity,
            PropertyDetails propertyDetails,
            AutoBinding.UpdateStrategy updateStrategy) {
        ComboBoxBindingValidationListener bindingValidationListener =
                new ComboBoxBindingValidationListener(this, propertyDetails);
        String componentName = getName();
        return bind(bindingGroup, data, beanEntity, propertyDetails, updateStrategy,
                componentName, bindingValidationListener);
    }

    private JComboBoxBinding bind(
            BindingGroup bindingGroup,
            List data,
            Object beanEntity,
            PropertyDetails propertyDetails,
            AutoBinding.UpdateStrategy updateStrategy,
            String componentName,
            BindingValidationListener bindingValidationListener) {
        if (propertyDetails == null || propertyDetails.isHiden()) {
            setEditable(false);
            setEnabled(false);
            return null;
        }

        if (converter == null) {
            converter = new BeanResourceToStringConverter(getApplication());
        }
        AutoCompleteDecorator.decorate(this, converter);

        if (!(data instanceof ObservableList)) {
            observableData = ObservableCollections.observableList(data);
        } else {
            observableData = (ObservableList) data;
        }
        this.propertyName = propertyDetails.getPropertyName();
        this.beanEntity = beanEntity;

        String name;
        if(componentName != null) {
            name = componentName + ".ComboBox";
        } else {
            name = null;
        }
        comboBoxBinding = SwingBindings.createJComboBoxBinding(
                updateStrategy,
                observableData,
                this,
                name);
        bindingGroup.addBinding(comboBoxBinding);

        name = componentName;
        ELProperty elProperty = ELProperty.create("${" + propertyName + "}");
        BeanProperty beanProperty = BeanProperty.create("selectedItem");
        binding = Bindings.createAutoBinding(
                updateStrategy,
                beanEntity,
                elProperty,
                this,
                beanProperty,
                name);
        Validator validator = propertyDetails.getValidator();
        if (validator != null) {
            binding.setValidator(validator);
        }

        binding.addBindingListener(bindingValidationListener);

        bindingGroup.addBinding(binding);

        return comboBoxBinding;
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

    public SelectableListDialog getSelectableListDialog() {
        return selectableListDialog;
    }

    public void setSelectableListDialog(SelectableListDialog selectableListDialog) {
        this.selectableListDialog = selectableListDialog;
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
        setToolTipText(tooltip);
        Color color = getResourceMap().getColor("validation.field.required.background");
        getEditor().getEditorComponent().setBackground(color);
    }

    public void setStyleInvalid() {
        setStyleInvalid(null);
    }

    @Override
    public void setStyleInvalid(String tooltip) {
        setToolTipText(tooltip);
        Color color = getResourceMap().getColor("validation.field.invalid.background");
        getEditor().getEditorComponent().setBackground(color);
    }

    @Override
    public void setStyleValid() {
        setToolTipText(null);
        Color color = getResourceMap().getColor("validation.field.valid.background");
        getEditor().getEditorComponent().setBackground(color);
    }

    @Override
    public void setStyleNormal() {
        setToolTipText(null);
        Color color = getResourceMap().getColor("validation.field.normal.background");
        getEditor().getEditorComponent().setBackground(color);
    }

    @Override
    public void addKeyListener(KeyListener listener) {
        super.addKeyListener(listener);
        getEditor().getEditorComponent().addKeyListener(listener);
    }

    @Override
    public void removeKeyListener(KeyListener listener) {
        super.removeKeyListener(listener);
        getEditor().getEditorComponent().removeKeyListener(listener);
    }

    /** Handling coversions */
    private ObjectToStringConverter converter;
    @SuppressWarnings("unchecked")
    private Binding binding;

    public ObjectToStringConverter getConverter() {
        return converter;
    }

    public void setConverter(ObjectToStringConverter converter) {
        this.converter = converter;
    }

    public void addItemListener(ItemListener listener, boolean ignoreSelectEvents) {
        if (ignoreSelectEvents) {
            super.addItemListener(new ComboListEventListener(listener));
        } else {
            super.addItemListener(listener);
        }
    }

    @Override
    public void setSelectedItem(Object anObject) {
        Object oldSelectedItem = getSelectedItem();
        super.setSelectedItem(anObject);
        Object newSelectedItem = getSelectedItem();
        if (oldSelectedItem != null) {
            if (!oldSelectedItem.equals(newSelectedItem)) {
                selectedItemChanged(newSelectedItem, oldSelectedItem);
            }
        } else if (newSelectedItem != null) {
            selectedItemChanged(newSelectedItem, oldSelectedItem);
        }
    }

    protected void selectedItemChanged(Object selectedItem, Object oldSelectedItem) {
        ItemEvent event;
        if (selectedItem != null) {
            event = new ItemEvent(this,
                    ItemEvent.ITEM_STATE_CHANGED,
                    selectedItem,
                    ItemEvent.SELECTED + 0x700);

            firePropertyChange(SELECTED_ITEM,
                    selectedItem,
                    oldSelectedItem);
        } else {
            event = new ItemEvent(this,
                    ItemEvent.ITEM_STATE_CHANGED,
                    selectedItem,
                    ItemEvent.DESELECTED + 0x700);
        }

        fireItemStateChanged(event);
    }

    public Binding getBinding() {
        return binding;
    }

    public void setBinding(Binding binding) {
        this.binding = binding;
    }

    public void removeFromBindingGroup(BindingGroup bindGroup) {
        bindGroup.removeBinding(binding);
        bindGroup.removeBinding(comboBoxBinding);
    }

    public void addToBindingGroup(BindingGroup bindGroup) {
        bindGroup.addBinding(comboBoxBinding);
        bindGroup.addBinding(binding);
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

    @Override
    public Task refresh() {
        RefreshTask task = new RefreshTask();
        task.run();
        return task;
    }

    @Override
    public void clear() {
        setSelectedItem(null);
    }

    private class RefreshTask extends Task<Object, Void> {

        public RefreshTask() {
            super(Application.getInstance());
        }

        @Override
        protected Object doInBackground() throws Exception {
            if (selectableListDialog != null) {
                Object selectedItem = getSelectedItem();

                List listData;
                if(selectableListDialog instanceof Refreshable) {
                    Task task = ((Refreshable) selectableListDialog).refresh();
                    listData = (List)task.get();
                } else {
                    listData = selectableListDialog.getListData();
                }
                observableData.clear();
                observableData.addAll(listData);

                if(selectedItem != null && listData.contains(selectedItem)) {
                    setSelectedItem(selectedItem);
                }
            }

            setSelectedItem(getPropertyValue());

            return getData();
        }
    }

    private class JBComboBoxEditor extends BasicComboBoxEditor {

        public JBComboBoxEditor() {
            super();
            getEditorComponent().addMouseListener(new JBContextMenuCreaetor());
        }
    }

    class CustomCellRenderer extends BeanListCellRenderer {

        public CustomCellRenderer() {
        }

        @Override
        public Component getListCellRendererComponent(JList list,
                Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (getConverter() != null) {
                value = getConverter().getPreferredStringForItem(value);
            }

            return super.getListCellRendererComponent(list, value,
                    index, isSelected, cellHasFocus);
        }
    }
}
