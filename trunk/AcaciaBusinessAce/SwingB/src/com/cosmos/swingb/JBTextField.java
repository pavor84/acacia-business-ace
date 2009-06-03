/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.swingb;

import com.cosmos.beans.PropertyChangeNotificationBroadcaster;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.resource.BeanResource;
import com.cosmos.resource.EnumResource;
import com.cosmos.resource.TextResource;
import com.cosmos.swingb.binding.EntityBinder;
import com.cosmos.swingb.validation.Validatable;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JTextField;
import org.apache.commons.beanutils.PropertyUtils;
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

/**
 *
 * @author Miro
 */
public class JBTextField
        extends JTextField
        implements Validatable, EntityBinder, PropertyChangeListener {

    private Application application;
    private ApplicationContext applicationContext;
    private ApplicationActionMap applicationActionMap;
    private ResourceMap resourceMap;
    private Binding binding;
    private String propertyName;
    private Object beanEntity;
    private BeanResource beanResource;

    @Override
    public Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            PropertyDetails propertyDetails) {
        return bind(bindingGroup, beanEntity, propertyDetails, getExpression(propertyDetails),
                propertyDetails.getUpdateStrategy());
    }

    protected String getExpression(PropertyDetails propertyDetails) {
        String expression;
        if ((expression = propertyDetails.getCustomDisplay()) != null && expression.length() > 0) {
            return expression;
        }

        return "${" + propertyDetails.getPropertyName() + "}";
    }

    public Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            PropertyDetails propertyDetails,
            AutoBinding.UpdateStrategy updateStrategy) {
        return bind(bindingGroup, beanEntity, propertyDetails, getExpression(propertyDetails), updateStrategy);
    }

    public Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            PropertyDetails propertyDetails,
            String elProperyDisplay) {
        return bind(bindingGroup, beanEntity, propertyDetails, elProperyDisplay, AutoBinding.UpdateStrategy.READ_WRITE);
    }

    public Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            PropertyDetails propertyDetails,
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
            Validator validator = propertyDetails.getValidator();
            if (validator != null) {
                binding.setValidator(validator);
            }
            binding.addBindingListener(new BindingValidationListener(this));
            setEditable(propertyDetails.isEditable());
            setEnabled(!propertyDetails.isReadOnly());
        }

        return binding;
    }

    private Binding bind(
            BindingGroup bindingGroup,
            Object beanEntity,
            ELProperty elProperty,
            AutoBinding.UpdateStrategy updateStrategy) {
        this.beanEntity = beanEntity;

        BeanProperty beanProperty = BeanProperty.create("text");
        binding = Bindings.createAutoBinding(updateStrategy, beanEntity, elProperty, this, beanProperty);
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

    @Override
    public void refresh() {
        setText(getPropertyValue());
    }

    protected String getPropertyValue() {
        return getPropertyValue(beanEntity);
    }

    protected String getPropertyValue(Object beanEntity) {
        try {
            Object propertyValue;
            if ((propertyValue = PropertyUtils.getProperty(beanEntity, propertyName)) != null) {
                if(propertyValue instanceof EnumResource) {
                    return getBeanResource().getName((EnumResource)propertyValue);
                } else if(propertyValue instanceof TextResource) {
                    return getBeanResource().getName((TextResource)propertyValue);
                } else {
                    return String.valueOf(propertyValue);
                }
            }

            return null;
        } catch(Exception ex) {
            throw new RuntimeException("beanEntity=" + beanEntity + ", propertyName=" + propertyName, ex);
        }
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

    protected BeanResource getBeanResource() {
        if(beanResource == null) {
            beanResource = new BeanResource(getApplication());
        }

        return beanResource;
    }

    private void validate(Object beanEntity) {
        setText(getPropertyValue(beanEntity));
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if(propertyName != null && propertyName.equals(event.getPropertyName())) {
            validate(beanEntity);
        }
    }
}