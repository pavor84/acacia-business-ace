/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.validation.Validatable;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.beansbinding.Validator;
import org.jdesktop.swingx.JXMaskTextField;

/**
 *
 * @author Miro
 */
public class JBMaskTextField extends JXMaskTextField  implements Validatable {

    public JBMaskTextField() {
    }

    private Application application;
    private ApplicationContext applicationContext;
    private ResourceMap resourceMap;

    private Binding binding;
    private String propertyName;
    private Object beanEntity;

    /**
     * @param bindingGroup
     * @param beanEntity
     * @param propertyDetails
     * @return
     */
    public Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            PropertyDetails propertyDetails)
    {
        return bind(bindingGroup, beanEntity, propertyDetails, AutoBinding.UpdateStrategy.READ_WRITE);
    }

    /**
     * @param bindingGroup
     * @param beanEntity
     * @param propertyDetails
     * @param updateStrategy
     * @return
     */
    @SuppressWarnings("unchecked")
    public Binding bind(BindingGroup bindingGroup,
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

        binding = bind(bindingGroup, beanEntity, propertyDetails.getPropertyName(), updateStrategy);
        setEditable(propertyDetails.isEditable());
        setEnabled(!propertyDetails.isReadOnly());

        Validator validator = propertyDetails.getValidator();
        if(validator != null)
        {
            binding.setValidator(validator);
        }

        binding.addBindingListener(new BindingValidationListener(this));

        return binding;
    }

    public Application getApplication() {
        if(application == null)
            application = Application.getInstance();

        return application;
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

    @SuppressWarnings("unchecked")
    private Binding bind(
            BindingGroup bindingGroup,
            Object beanEntity,
            String propertyName,
            AutoBinding.UpdateStrategy updateStrategy)
    {
        this.propertyName = propertyName;
        this.beanEntity = beanEntity;

        final ELProperty sourceProperty = ELProperty.create("${" + propertyName + "}");
        ELProperty targetProperty = ELProperty.create("${text}");
        binding = Bindings.createAutoBinding(updateStrategy, beanEntity, sourceProperty, this, targetProperty);

        bindingGroup.addBinding(binding);

        return binding;
    }

    private final Class<?> noPrimitiveType(Class<?> type) {
        if (!type.isPrimitive()) {
            return type;
        }

        if (type == Byte.TYPE) {
            return Byte.class;
        } else if (type == Short.TYPE) {
            return Short.class;
        } else if (type == Integer.TYPE) {
            return Integer.class;
        } else if (type == Long.TYPE) {
            return Long.class;
        } else if (type == Boolean.TYPE) {
            return Boolean.class;
        } else if (type == Character.TYPE) {
            return Character.class;
        } else if (type == Float.TYPE) {
            return Float.class;
        } else if (type == Double.TYPE) {
            return Double.class;
        }

        throw new IllegalArgumentException("Primitive not recognized!");
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Object getBeanEntity() {
        return beanEntity;
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

    public Binding getBinding() {
        return binding;
    }

}
