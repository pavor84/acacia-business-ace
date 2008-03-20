/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.beansbinding.validation;

import org.jdesktop.beansbinding.Validator;


/**
 *
 * @author Miro
 */
public enum ValidationError
{
    EmptyValue("1001", "The value can not be empty."),
    TextValueLengthIsLessThanMin("1002", "The text value length is less than the required minimum."),
    TextValueLengthIsGreaterThanMax("1003", "The text value length is greater than the required maximum."),
    TextNotMatch("1004", "The text value doesn't match to the pattern."),

    NotNumericValue("1101", "The value is not Numeric type (byte, short, integer, long, float and double)."),
    NumericValueIsLessThanMin("1102", "The value is less than the required minimum value."),
    NumericValueIsGreaterThanMax("1103", "The value is greater than the required maximum value."),
    NotIntegerValue("1104", "The value is not Integer type (byte, short, integer, long)."),

    NotDateValue("1201", "The value is not Date type."),
    DateValueIsLessThanMin("1202", "The date value is less than the required minimum value."),
    DateValueIsGreaterThanMax("1203", "The date value is greater than the required maximum value."),
    ;

    private ValidationError(String errorCode, String description)
    {
        this.errorCode = errorCode;
        this.description = description;
    }

    private String errorCode;
    private String description;
    private Validator.Result validatorResult;

    public String getDescription() {
        return description;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Validator.Result getValidatorResult()
    {
        if(validatorResult == null)
        {
            validatorResult = new Validator.Result(errorCode, description);
        }

        return validatorResult;
    }
}
