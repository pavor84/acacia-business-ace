/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.beansbinding.validation.BaseValidator;
import java.awt.Color;
import javax.swing.JTextField;
import org.jdesktop.beansbinding.AbstractBindingListener;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.beansbinding.PropertyStateEvent;
import org.jdesktop.beansbinding.Validator;

/**
 *
 * @author Miro
 */
public class JBTextField
    extends JTextField
{
    private Binding binding;
    private String propertyName;
    private Object beanEntity;

    public Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            PropertyDetails propertyDetails)
    {
        return bind(bindingGroup, beanEntity, propertyDetails, AutoBinding.UpdateStrategy.READ_WRITE);
    }

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
        
        bind(bindingGroup, beanEntity, propertyDetails.getPropertyName(), updateStrategy);
        setEditable(propertyDetails.isEditable());
        setEnabled(!propertyDetails.isReadOnly());

        Validator validator = propertyDetails.getValidator();
        if(validator != null)
        {
            binding.setValidator(validator);
        }

        binding.addBindingListener(new BindingValidationListener());

        return binding;
    }

    private Binding bind(
            BindingGroup bindingGroup,
            Object beanEntity,
            String propertyName,
            AutoBinding.UpdateStrategy updateStrategy)
    {
        this.propertyName = propertyName;
        this.beanEntity = beanEntity;

        ELProperty elProperty = ELProperty.create("${" + propertyName + "}");
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

    public class BindingValidationListener
        extends AbstractBindingListener
    {
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
        setBackground(Color.PINK);
        setToolTipText(tooltip);
    }

    public void setStyleInvalid(String tooltip) {
        setBackground(Color.YELLOW);
        setToolTipText(tooltip);
    }
    
    public void setStyleValid() {
        setBackground(Color.GREEN);
        setToolTipText(null);
    }
    
    public void setStyleNormal() {
        setBackground(Color.WHITE);   
        setToolTipText(null);
    }
}