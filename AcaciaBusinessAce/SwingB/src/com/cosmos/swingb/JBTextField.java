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
import java.awt.print.PrinterException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JTextField;
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
    private ELProperty elProperty;
    private BeanResource beanResource;

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
        this.elProperty = ELProperty.create(elProperyDisplay);

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

    public String getPropertyName() {
        return propertyName;
    }

    public Object getBeanEntity() {
        return beanEntity;
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

    public void setStyleRequired(String tooltip) {
        setToolTipText(tooltip);
        setBackground(getResourceMap().getColor("validation.field.required.background"));
    }

    public void setStyleInvalid(String tooltip) {
        setToolTipText(tooltip);
        setBackground(getResourceMap().getColor("validation.field.invalid.background"));
    }

    public void setStyleValid() {
        setToolTipText(null);
        setBackground(getResourceMap().getColor("validation.field.valid.background"));
    }

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
        try {
            Object propertyValue;
            if ((propertyValue = elProperty.getValue(beanEntity)) != null) {
                String text;
                if(propertyValue instanceof EnumResource) {
                    text = getBeanResource().getName((EnumResource)propertyValue);
                } else if(propertyValue instanceof TextResource) {
                    text = getBeanResource().getName((TextResource)propertyValue);
                } else {
                    text = String.valueOf(propertyValue);
                }
                setText(text);
            } else {
                setText(null);
            }
        } catch(RuntimeException ex) {
            throw new RuntimeException("beanEntity: " + beanEntity +
                    ", elProperty: " + elProperty, ex);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if(propertyName != null && propertyName.equals(event.getPropertyName())) {
            validate(beanEntity);
        }
    }
}