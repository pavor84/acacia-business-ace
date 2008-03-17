/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.beansbinding.validation;

import java.io.Serializable;
import java.util.Date;
import org.jdesktop.beansbinding.Validator;

/**
 *
 * @author Miro
 */
public class DateRangeValidator
    extends DateValidator
    implements Serializable
{

    private static final Result VALUE_IS_LESS_THAN_MIN_VALUE =
            ValidationError.DateValueIsLessThanMin.getValidatorResult();

    private static final Result VALUE_IS_GREATER_THAN_MAX_VALUE =
            ValidationError.DateValueIsGreaterThanMax.getValidatorResult();

    private Date fromDate;
    private Date toDate;

    public DateRangeValidator()
    {
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public void setFromDate(String fromDate) {
        setFromDate(parse(fromDate));
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        setToDate(parse(toDate));
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }


    @Override
    public Validator.Result validate(Object value) {
        Validator.Result result = super.validate(value);
        if(result != null)
            return result;

        Date date = (Date)value;

        if(fromDate != null && date.before(fromDate))
            return VALUE_IS_LESS_THAN_MIN_VALUE;

        if(toDate != null && date.after(toDate))
            return VALUE_IS_GREATER_THAN_MAX_VALUE;

        return null;
    }

}
