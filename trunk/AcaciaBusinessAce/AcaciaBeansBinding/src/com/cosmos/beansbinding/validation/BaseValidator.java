/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.beansbinding.validation;

import java.util.ArrayList;
import java.util.List;
import org.jdesktop.beansbinding.Validator;

/**
 * Base abstract class for all validators. Provides a way
 * to make composite validators out of two or more existing ones
 * 
 * @author Bozhidar Bozhanov
 */
public class BaseValidator extends Validator {
    protected List<Validator> validators = new ArrayList<Validator>();
    private String tooltip;
    private boolean required;

    public Result validate(Object value)
    {
        return invokeValidators(value);
    }
    
    public void addValidator(Validator validator){
        validators.add(validator);
    }
    
    protected Result invokeValidators(Object value) {
        Result result = null;
        for (Validator validator: validators)
        {
            result = validator.validate(value);
            
            if (result != null)
                return result;
        }
        return null;
    }
    
        public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        if (required)
        {
            addValidator(new RequiredValidator());
        }
        
        this.required = required;
    }
    
    protected String toString(Object value)
    {
        return toString(value, true);
    }

    protected String toString(Object value, boolean useValueOf)
    {
        if(value == null)
            return null;

        if(value instanceof CharSequence)
            return ((CharSequence)value).toString();

        if(useValueOf)
            return String.valueOf(value);

        return value.toString();
    }

    protected boolean isEmptyString(Object value)
    {
        if(value == null)
            return true;

        String stringValue = toString(value, false);
        if(stringValue == null || (stringValue = stringValue.trim()).length() == 0)
            return true;

        return false;
    }

    @Override
    public String toString() {
        String toString = super.toString();

        if(BaseValidator.class.equals(this.getClass()))
        {
            return toString + ", validators: " + validators;
        }

        return toString;
    }
}
