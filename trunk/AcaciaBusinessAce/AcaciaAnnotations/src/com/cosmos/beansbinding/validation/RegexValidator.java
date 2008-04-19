/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.beansbinding.validation;

import java.io.Serializable;
import java.util.regex.Pattern;
import org.jdesktop.beansbinding.Validator;

/**
 *
 * @author Miro
 */
public class RegexValidator
    extends BaseValidator
    implements Serializable
{
    private static final Result TEXT_NOT_MATCH =
            ValidationError.TextNotMatch.getValidatorResult();

    private Pattern pattern;


    public RegexValidator()
    {
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(String regex) {
        setPattern(Pattern.compile(regex));
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public Validator.Result validate(Object value) {
        
        if(pattern == null)
            new IllegalArgumentException("The pattern for regular expression is not set.");

        if(!isRequired() && isEmptyString(value))
            return null;

        if(!pattern.matcher(toString(value)).matches())
            return TEXT_NOT_MATCH;

        return null;
    }
}
