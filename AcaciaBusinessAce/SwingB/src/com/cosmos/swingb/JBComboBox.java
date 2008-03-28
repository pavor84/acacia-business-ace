/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.beansbinding.validation.BaseValidator;
import java.awt.Color;
import java.util.List;
import javax.swing.JComboBox;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationActionMap;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.AbstractBindingListener;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.Binding.SyncFailure;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.beansbinding.PropertyStateEvent;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.swingbinding.JComboBoxBinding;
import org.jdesktop.swingbinding.SwingBindings;

/**
 *
 * @author Miro
 */
public class JBComboBox
    extends JComboBox
{
    private Application application;
    private ApplicationContext applicationContext;
    private ApplicationActionMap applicationActionMap;
    private ResourceMap resourceMap;

    private ObservableList observableData;
    private String propertyName;
    private Object beanEntity;

    public JBComboBox()
    {
        super();
    }

    public JBComboBox(Class<? extends Application> applicationClass)
    {
        this(Application.getInstance(applicationClass));
    }

    public JBComboBox(Application application)
    {
        this();
        this.application = application;
        setRenderer(new BeanListCellRenderer(application.getClass()));
    }

    public Binding bind(
            BindingGroup bindingGroup,
            List data,
            Object beanEntity,
            PropertyDetails propertyDetails)
    {
        return bind(bindingGroup, data, beanEntity, propertyDetails, AutoBinding.UpdateStrategy.READ_WRITE);
    }

    public Binding bind(
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
        
        Binding binding = bind(bindingGroup, data, beanEntity, propertyDetails.getPropertyName(), updateStrategy);
        setEditable(propertyDetails.isEditable());
        setEnabled(!propertyDetails.isReadOnly());

        binding.addBindingListener(new BindingValidationListener());

        return binding;
    }

    public JComboBoxBinding bind(
            BindingGroup bindingGroup,
            List data,
            Object beanEntity,
            String propertyName)
    {
        return bind(bindingGroup, data, beanEntity, propertyName, AutoBinding.UpdateStrategy.READ_WRITE);
    }

    public JComboBoxBinding bind(
            BindingGroup bindingGroup,
            List data,
            Object beanEntity,
            String propertyName,
            AutoBinding.UpdateStrategy updateStrategy)
    {
        if(!(data instanceof ObservableList))
            observableData = ObservableCollections.observableList(data);
        else
            observableData = (ObservableList)data;
        this.propertyName = propertyName;
        this.beanEntity = beanEntity;

        JComboBoxBinding comboBoxBinding = SwingBindings.createJComboBoxBinding(updateStrategy, observableData, this);
        bindingGroup.addBinding(comboBoxBinding);

        ELProperty elProperty = ELProperty.create("${" + propertyName + "}");
        BeanProperty beanProperty = BeanProperty.create("selectedItem");
        Binding binding = Bindings.createAutoBinding(updateStrategy, beanEntity, elProperty, this, beanProperty);
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
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }


    public class BindingValidationListener
        extends AbstractBindingListener
    {
        @Override
        public void bindingBecameBound(Binding binding) {
            System.out.println("bindingBecameBound");
            validate(binding);
        }

        @Override
        public void targetChanged(Binding binding, PropertyStateEvent event) {
            System.out.println("targetChanged");
            validate(binding);
        }

        @Override
        public void bindingBecameUnbound(Binding arg0) {
            System.out.println("bindingBecameUnbound");
        }

        @Override
        public void sourceChanged(Binding arg0, PropertyStateEvent arg1) {
            System.out.println("sourceChanged");
        }

        @Override
        public void sourceEdited(Binding arg0) {
            System.out.println("sourceEdited");
        }

        @Override
        public void syncFailed(Binding arg0, SyncFailure arg1) {
            System.out.println("syncFailed");
        }

        @Override
        public void synced(Binding arg0) {
            System.out.println("synced");
        }

        @Override
        public void targetEdited(Binding arg0) {
            System.out.println("targetEdited");
        }


        public void validate(Binding binding)
        {
            System.out.println("ComboBox.binding: " + binding);
            System.out.println("binding.getName(): " + binding.getName());
            System.out.println("getSourceObject: " + binding.getSourceObject());
            System.out.println("getSourceProperty: " + binding.getSourceProperty());
            System.out.println("getSourceValueForTarget: " + binding.getSourceValueForTarget());
            JComboBox comboBox = (JComboBox)binding.getTargetObject();
            System.out.println("getTargetObject: " + comboBox);
            System.out.println("comboBox.getSelectedItem(): " + comboBox.getSelectedItem());
            System.out.println("getTargetProperty: " + binding.getTargetProperty());
            System.out.println("getTargetValueForSource: " + binding.getTargetValueForSource());
            boolean required = false;
            String tooltip = null;
            BaseValidator validator = (BaseValidator)binding.getValidator();
            if(validator != null)
            {
                tooltip = validator.getTooltip();
                required = validator.isRequired();
            }

            if(!binding.isContentValid())
            {
                if(required)
                    setStyleRequired(tooltip);
                else
                    setStyleInvalid(tooltip);
            }
            else
            {
                if(required)
                    setStyleValid();
                else
                    setStyleNormal();
            }
        }
    }

    public void setStyleRequired(String tooltip) {
        System.out.println("setStyleRequired");
        setBackground(Color.PINK);
        setToolTipText(tooltip);
    }

    public void setStyleInvalid() {
        setStyleInvalid(null);
    }

    public void setStyleInvalid(String tooltip) {
        System.out.println("setStyleInvalid");
        setBackground(Color.YELLOW);
        setToolTipText(tooltip);
    }
    
    public void setStyleValid() {
        System.out.println("setStyleValid");
        setBackground(Color.GREEN);
        setToolTipText(null);
    }
    
    public void setStyleNormal() {
        System.out.println("setStyleNormal");
        setBackground(Color.WHITE);   
        setToolTipText(null);
    }
}
