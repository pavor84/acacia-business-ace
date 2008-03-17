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
public class TextLengthValidator
    extends RequiredValidator
    implements Serializable
{

    private static final Result TEXT_VALUE_LENGTH_IS_LESS_THAN_MIN_VALUE =
            ValidationError.TextValueLengthIsLessThanMin.getValidatorResult();

    private static final Result TEXT_VALUE_LENGTH_IS_GREATER_THAN_MAX_VALUE =
            ValidationError.TextValueLengthIsGreaterThanMax.getValidatorResult();

    private int minLength;
    private int maxLength;

    public TextLengthValidator()
    {
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    @Override
    public Validator.Result validate(Object value) {
        Validator.Result result = super.validate(value);
        if(result != null)
            return result;

        int length = toString(value).length();
        if(length < minLength)
            return TEXT_VALUE_LENGTH_IS_LESS_THAN_MIN_VALUE;

        if(length > maxLength)
            return TEXT_VALUE_LENGTH_IS_GREATER_THAN_MAX_VALUE;

        return null;
    }

}
