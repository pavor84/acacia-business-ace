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
    extends RequiredValidator
    implements Serializable
{
    private static final Result NOT_NUMERIC_VALUE =
            ValidationError.NotNumericValue.getValidatorResult();


    public NumericValidator()
    {
    }

    @Override
    public Validator.Result validate(Object value) {
        Validator.Result result = super.validate(value);
        if(result != null)
            return result;

        Double doubleValue = toDouble(value);
        if(doubleValue == null)
            return NOT_NUMERIC_VALUE;

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
}
