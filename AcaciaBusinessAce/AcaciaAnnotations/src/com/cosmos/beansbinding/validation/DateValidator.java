/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.beansbinding.validation;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.jdesktop.beansbinding.Validator;

/**
 *
 * @author Miro
 */
public class DateValidator
    extends BaseValidator
    implements Serializable
{
    private static final Result NOT_DATE_VALUE =
            ValidationError.NotDateValue.getValidatorResult();

    private String datePattern;
    private SimpleDateFormat dateFormat;

    public DateValidator()
    {
    }

    public String getDatePattern() {
        return datePattern;
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

    protected SimpleDateFormat getDateFormat()
    {
        if(dateFormat == null)
        {
            if(datePattern != null)
                dateFormat = new SimpleDateFormat(datePattern);
            else
                dateFormat = new SimpleDateFormat();
        }

        return dateFormat;
    }

    @Override
    public Validator.Result validate(Object value) {

        if(value instanceof Date)
            return null;
        
        Date dateValue = toDate(value);
        if(dateValue == null)
            return NOT_DATE_VALUE;

        return null;
    }

    protected Date toDate(Object value)
    {
        if(value == null)
            return null;

        if(value instanceof Date)
            return (Date)value;

        try
        {
            return parse(toString(value));
        }
        catch(Exception ex)
        {
            return null;
        }
    }

    protected Date parse(String source)
    {
        try
        {
            return getDateFormat().parse(source);
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }
}
