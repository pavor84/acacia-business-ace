/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.swingb;

import com.cosmos.beans.PropertyChangeNotificationBroadcaster;
import com.cosmos.beansbinding.EntityProperty;
import com.cosmos.swingb.binding.EntityBinder;
import com.cosmos.swingb.validation.Validatable;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigInteger;
import org.apache.commons.beanutils.PropertyUtils;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationActionMap;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.beansbinding.Validator;
import org.jdesktop.swingx.JXIntegerField;

/**
 *
 * @author Miro
 */
public class JBIntegerField extends JXIntegerField
        implements Validatable, EntityBinder, PropertyChangeListener {

    public JBIntegerField() {
    }

    public JBIntegerField(BigInteger value) {
        super(value);
    }
    private Application application;
    private ApplicationContext applicationContext;
    private ApplicationActionMap applicationActionMap;
    private ResourceMap resourceMap;
    private Binding binding;
    private String propertyName;
    private Object beanEntity;

    @Override
    public Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            EntityProperty propertyDetails) {
        return bind(bindingGroup, beanEntity, propertyDetails, getExpression(propertyDetails),
                propertyDetails.getUpdateStrategy());
    }

    protected String getExpression(EntityProperty propertyDetails) {
        String expression;
        if ((expression = propertyDetails.getCustomDisplay()) != null && expression.length() > 0) {
            return expression;
        }

        return "${" + propertyDetails.getPropertyName() + "}";
    }

    public Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            EntityProperty propertyDetails,
            AutoBinding.UpdateStrategy updateStrategy) {
        return bind(bindingGroup, beanEntity, propertyDetails, getExpression(propertyDetails), updateStrategy);
    }

    public Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            EntityProperty propertyDetails,
            String elProperyDisplay) {
        return bind(bindingGroup, beanEntity, propertyDetails, elProperyDisplay, AutoBinding.UpdateStrategy.READ_WRITE);
    }

    public Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            EntityProperty propertyDetails,
            String elProperyDisplay,
            AutoBinding.UpdateStrategy updateStrategy) {
        if (propertyDetails == null || propertyDetails.isHiden()) {
            setEditable(false);
            setEnabled(false);
            return null;
        }

        this.propertyName = propertyDetails.getPropertyName();
        ELProperty elProperty = ELProperty.create(elProperyDisplay);

        if (propertyDetails.isShowOnly()) {
            validate(beanEntity);
            if(beanEntity instanceof PropertyChangeNotificationBroadcaster) {
                ((PropertyChangeNotificationBroadcaster)beanEntity).addPropertyChangeListener(this);
            }
            setEditable(false);
            setEnabled(false);
        } else {
            bind(bindingGroup, beanEntity, elProperty, updateStrategy);
            setEditable(propertyDetails.isEditable());
            setEnabled(!propertyDetails.isReadOnly());

            Validator validator = propertyDetails.getValidator();
            if (validator != null) {
                binding.setValidator(validator);
            }

            binding.addBindingListener(new BindingValidationListener(this));
        }

        return binding;
    }

    private Binding bind(
            BindingGroup bindingGroup,
            Object beanEntity,
            ELProperty elProperty,
            AutoBinding.UpdateStrategy updateStrategy) {
        this.beanEntity = beanEntity;

        BeanProperty beanProperty = BeanProperty.create("value");
        binding = Bindings.createAutoBinding(
                updateStrategy,
                beanEntity,
                elProperty,
                getNumericField(),
                beanProperty,
                getName());
        bindingGroup.addBinding(binding);

        return binding;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public Object getBeanEntity() {
        return beanEntity;
    }

    @Override
    public Binding getBinding() {
        return binding;
    }

    protected Number getPropertyValue() {
        return getPropertyValue(beanEntity);
    }

    protected Number getPropertyValue(Object beanEntity) {
        try {
            return (Number)PropertyUtils.getProperty(beanEntity, propertyName);
        } catch(Exception ex) {
            throw new RuntimeException("beanEntity=" + beanEntity + ", propertyName=" + propertyName, ex);
        }
    }

    @Override
    public void setStyleRequired(String tooltip) {
        setToolTipText(tooltip);
        setBackground(getResourceMap().getColor("validation.field.required.background"));
    }

    @Override
    public void setStyleInvalid(String tooltip) {
        setToolTipText(tooltip);
        setBackground(getResourceMap().getColor("validation.field.invalid.background"));
    }

    @Override
    public void setStyleValid() {
        setToolTipText(null);
        setBackground(getResourceMap().getColor("validation.field.valid.background"));
    }

    @Override
    public void setStyleNormal() {
        setToolTipText(null);
        setBackground(getResourceMap().getColor("validation.field.normal.background"));
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
                applicationActionMap = context.getActionMap(this);
            }
        }

        return applicationActionMap;
    }

    @Override
    public ResourceMap getResourceMap() {
        if (resourceMap == null) {
            ApplicationContext context = getContext();
            if (context != null) {
                resourceMap = context.getResourceMap(this.getClass());
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
    public void propertyChange(PropertyChangeEvent event) {
        if (propertyName != null && propertyName.equals(event.getPropertyName())) {
            validate(beanEntity);
        }
    }

    private void validate(Object beanEntity) {
        setValue(getPropertyValue(beanEntity));
    }

    @Override
    public Task refresh() {
        RefreshTask task = new RefreshTask();
        task.run();
        return task;
    }

    @Override
    public void clear() {
        setValue(null);
    }

    private class RefreshTask extends Task<Object, Void> {

        public RefreshTask() {
            super(Application.getInstance());
        }

        @Override
        protected Object doInBackground() throws Exception {
            Number value = getPropertyValue();
            setValue(value);
            return value;
        }
    }
}
