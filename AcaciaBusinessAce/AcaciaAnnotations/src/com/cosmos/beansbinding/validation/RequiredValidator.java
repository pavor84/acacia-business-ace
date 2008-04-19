/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.beansbinding.validation;

import java.io.Serializable;

/**
 *
 * @author Miro
 */
public class RequiredValidator
    extends BaseValidator
    implements Serializable
{
    private static final Result EMPTY_VALUE =
            ValidationError.EmptyValue.getValidatorResult();

    @Override
    public Result validate(Object value)
    {
        if(value == null)
            return EMPTY_VALUE;

        if(value instanceof CharSequence &&
            ((CharSequence)value).toString().trim().length() == 0)
            return EMPTY_VALUE;

        return null;
    }
}
