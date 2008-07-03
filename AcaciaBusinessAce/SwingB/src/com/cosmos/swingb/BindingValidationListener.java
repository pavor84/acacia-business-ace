package com.cosmos.swingb;

import com.cosmos.beansbinding.validation.BaseValidator;
import com.cosmos.swingb.validation.Validatable;
import org.jdesktop.beansbinding.AbstractBindingListener;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.PropertyStateEvent;

/**
 *
 * @author Bozhidar Bozhanov
 */
public class BindingValidationListener extends AbstractBindingListener {
    
    Validatable validatable;
    
    public BindingValidationListener(Validatable validatable) {
        this.validatable = validatable;
    }
    
    @Override
    public void bindingBecameBound(Binding binding) {
        validate(binding);
    }

    @Override
    public void targetChanged(Binding binding, PropertyStateEvent event) {
        validate(binding);
    }

    public void validate(Binding binding)
    {
        boolean required = false;
        String tooltip = null;
        BaseValidator validator = (BaseValidator) binding.getValidator();
        if(validator != null)
        {
            tooltip = validator.getTooltip();
            if (tooltip != null)
                tooltip = validatable.getResourceMap().getString(tooltip);
            required = validator.isRequired();
        }

        if(!binding.isContentValid())
        {
            if(required)
                validatable.setStyleRequired(tooltip);
            else
                validatable.setStyleInvalid(tooltip);
        }
        else
        {
            if(required)
                validatable.setStyleValid();
            else
                validatable.setStyleNormal();
        }
    }
}
