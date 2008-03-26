/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.beansbinding.validation;

import java.io.Serializable;
import org.jdesktop.beansbinding.Validator;

/**
 *
 * @author Miro
 */
public class NumericValidator
    extends BaseValidator
    implements Serializable
{
    private static final Result NOT_NUMERIC_VALUE =
            ValidationError.NotNumericValue.getValidatorResult();
    private static final Result NOT_INTEGER_VALUE =
            ValidationError.NotIntegerValue.getValidatorResult();

    private boolean isFloating;

    
    public NumericValidator()
    {
    }

    @Override
    public Validator.Result validate(Object value) {
        if(!isRequired() && isEmptyString(value))
            return null;

        if (isFloating()) {
            Double doubleValue = toDouble(value);
            if(doubleValue == null)
                return NOT_NUMERIC_VALUE;
        } else {
            Integer intValue = toInteger(value);
            if (intValue == null)
                return NOT_INTEGER_VALUE;
        }

        return null;
    }

    protected Double toDouble(Object value)
    {
        if(value == null)
            return null;

        if(value instanceof Number)
            return ((Number)value).doubleValue();

        try
        {
            return Double.parseDouble(toString(value));
        }
        catch(Exception ex)
        {
            return null;
        }
    }
    
    protected Integer toInteger(Object value)
    {
        if(value == null)
            return null;

        if(value instanceof Number)
            return ((Number)value).intValue();

        try
        {
            return Integer.parseInt(toString(value));
        }
        catch(Exception ex)
        {
            return null;
        }
    }
    
    public boolean isFloating() {
        return isFloating;
    }

    public void setFloating(boolean isFloating) {
        this.isFloating = isFloating;
    }
}
