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
public class NumericRangeValidator
    extends NumericValidator
    implements Serializable
{
    private static final Result VALUE_IS_LESS_THAN_MIN_VALUE =
            ValidationError.NumericValueIsLessThanMin.getValidatorResult();

    private static final Result VALUE_IS_GREATER_THAN_MAX_VALUE =
            ValidationError.NumericValueIsGreaterThanMax.getValidatorResult();

    private double minValue;
    private double maxValue;

    public NumericRangeValidator()
    {
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    @Override
    public Validator.Result validate(Object value) {
        Validator.Result result = super.validate(value);
        if(result != null)
            return result;

        if(!isRequired() && isEmptyString(value))
            return null;

        double doubleValue = toDouble(value);
        if(doubleValue < minValue)
            return VALUE_IS_LESS_THAN_MIN_VALUE;

        if(doubleValue > maxValue)
            return VALUE_IS_GREATER_THAN_MAX_VALUE;

        return null;
    }

}
