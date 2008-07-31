/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.menus.JBContextMenuCreaetor;
import com.cosmos.swingb.validation.Validatable;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyListener;
import java.util.List;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxEditor;
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
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

/**
 *
 * @author Miro
 */
public class JBComboBox
    extends JComboBox
    implements Validatable
{
    private Application application;
    private ApplicationContext applicationContext;
    private ApplicationActionMap applicationActionMap;
    private ResourceMap resourceMap;

    private ObservableList observableData;
    private String propertyName;
    private Object beanEntity;

    private JComboBoxBinding comboBoxBinding;


    public JBComboBox()
    {
        super();
        setEditor(new JBComboBoxEditor());
        Class appClass = null;
        if (application != null)
            appClass = application.getClass();
        
        setRenderer(new CustomCellRenderer(appClass));
    }

    public JBComboBox(Class<? extends Application> applicationClass)
    {
        this(Application.getInstance(applicationClass));
    }

    public JBComboBox(Application application)
    {
        this();
        this.application = application;
    }

    public JComboBoxBinding bind(
            BindingGroup bindingGroup,
            List data,
            Object beanEntity,
            PropertyDetails propertyDetails)
    {
        return bind(bindingGroup, data, beanEntity, propertyDetails, AutoBinding.UpdateStrategy.READ_WRITE);
    }

    public JComboBoxBinding bind(
            BindingGroup bindingGroup,
            List data,
            Object beanEntity,
            PropertyDetails propertyDetails,
            AutoBinding.UpdateStrategy updateStrategy)
    {
        if(propertyDetails == null || propertyDetails.isHiden())
        {
            setEditable(false);
            setEnabled(false);
            return null;
        }

        if(!(data instanceof ObservableList))
            observableData = ObservableCollections.observableList(data);
        else
            observableData = (ObservableList)data;
        this.propertyName = propertyDetails.getPropertyName();
        this.beanEntity = beanEntity;

        comboBoxBinding = SwingBindings.createJComboBoxBinding(
                updateStrategy,
                observableData,
                this);
        bindingGroup.addBinding(comboBoxBinding);

        ELProperty elProperty = ELProperty.create("${" + propertyName + "}");
        BeanProperty beanProperty = BeanProperty.create("selectedItem");
        Binding binding = Bindings.createAutoBinding(
                updateStrategy,
                beanEntity,
                elProperty,
                this,
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
                applicationActionMap = context.getActionMap(this);
            }
        }

        return applicationActionMap;
    }

    public ResourceMap getResourceMap()
    {
        if(resourceMap == null)
        {
            ApplicationContext context = getContext();
            if(context != null)
            {
                resourceMap = context.getResourceMap(this.getClass());
            }
        }

        return resourceMap;
    }

    public void setResourceMap(ResourceMap resourceMap) {
        this.resourceMap = resourceMap;
    }

    public Application getApplication() {
        if(application == null)
            application = Application.getInstance();

        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public void setStyleRequired(String tooltip) {
        setToolTipText(tooltip);
        Color color = getResourceMap().getColor("validation.field.required.background");
        getEditor().getEditorComponent().setBackground(color);
    }

    public void setStyleInvalid() {
        setStyleInvalid(null);
    }

    public void setStyleInvalid(String tooltip) {
        setToolTipText(tooltip);
        Color color = getResourceMap().getColor("validation.field.invalid.background");
        getEditor().getEditorComponent().setBackground(color);
    }
    
    public void setStyleValid() {
        setToolTipText(null);
        Color color = getResourceMap().getColor("validation.field.valid.background");
        getEditor().getEditorComponent().setBackground(color);
    }
    
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
    
    private class JBComboBoxEditor
        extends BasicComboBoxEditor
    {
        public JBComboBoxEditor() {
            super();
            getEditorComponent().addMouseListener(new JBContextMenuCreaetor());
        }
    }
    
    /** Handling coversions */
    
    private ObjectToStringConverter converter;
    
    public ObjectToStringConverter getConverter() {
        return converter;
    }

    public void setConverter(ObjectToStringConverter converter) {
        this.converter = converter;
    }
    
    
    class CustomCellRenderer extends BeanListCellRenderer {
        
        public CustomCellRenderer(Class appClass) {
            super(appClass);
        }
        
        @Override
        public Component getListCellRendererComponent(JList list,
                Object value, int index, boolean isSelected, boolean cellHasFocus)
        {
            if (getConverter() != null)
                value = getConverter().getPreferredStringForItem(value);
            
            return super.getListCellRendererComponent(list, value,
                    index, isSelected, cellHasFocus);
        }
    }
}