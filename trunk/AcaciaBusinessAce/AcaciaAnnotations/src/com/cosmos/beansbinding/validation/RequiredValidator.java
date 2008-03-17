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
public class RequiredValidator
    extends Validator
    implements Serializable
{
    private static final Result EMPTY_VALUE =
            ValidationError.EmptyValue.getValidatorResult();

    private String tooltip;

    @Override
    public Result validate(Object value)
    {
        System.out.println("RequiredValidator.validate: " + value);
        if(value == null)
            return EMPTY_VALUE;

        if(value instanceof CharSequence &&
            ((CharSequence)value).toString().trim().length() == 0)
            return EMPTY_VALUE;

        return null;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    protected String toString(Object value)
    {
        return toString(value, true);
    }

    private String toString(Object value, boolean useValueOf)
    {
        if(value == null)
            return null;

        if(value instanceof CharSequence)
            return ((CharSequence)value).toString();

        if(useValueOf)
            return String.valueOf(value);

        return value.toString();
    }
}
