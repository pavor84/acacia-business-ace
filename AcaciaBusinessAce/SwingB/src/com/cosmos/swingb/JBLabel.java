/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import com.cosmos.beansbinding.EntityProperty;
import com.cosmos.swingb.validation.Validatable;
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
import org.jdesktop.swingx.JXLabel;

/**
 *
 * @author Miro
 */
public class JBLabel
    extends JXLabel
    implements Validatable
{

    public JBLabel(String text) {
        super(text);
    }

    public JBLabel() {
    }

    private Application application;
    private ApplicationContext applicationContext;
    private ApplicationActionMap applicationActionMap;
    private ResourceMap resourceMap;

    private Binding binding;
    private String propertyName;
    private Object beanEntity;

    public Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            EntityProperty propertyDetails)
    {
        return bind(bindingGroup, beanEntity, propertyDetails, propertyDetails.getPropertyName());
    }

    public Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            EntityProperty propertyDetails,
            String elProperyDisplay)
    {
        return bind(bindingGroup, beanEntity, propertyDetails, elProperyDisplay, AutoBinding.UpdateStrategy.READ);
    }

    private Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            EntityProperty propertyDetails,
            String elProperyDisplay,
            AutoBinding.UpdateStrategy updateStrategy)
    {
        if(propertyDetails == null || propertyDetails.isHiden())
        {
            setEnabled(false);
            return null;
        }

        this.propertyName = elProperyDisplay;
        ELProperty elProperty = ELProperty.create("${" + elProperyDisplay + "}");
        bind(bindingGroup, beanEntity, elProperty, updateStrategy);
        setEnabled(!propertyDetails.isReadOnly());

        Validator validator = propertyDetails.getValidator();
        if(validator != null)
        {
            binding.setValidator(validator);
        }

        binding.addBindingListener(new BindingValidationListener(this));

        return binding;
    }

    private Binding bind(
            BindingGroup bindingGroup,
            Object beanEntity,
            ELProperty elProperty,
            AutoBinding.UpdateStrategy updateStrategy)
    {
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
}
