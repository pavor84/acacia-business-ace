/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.beansbinding.validation;

import java.io.Serializable;

import javax.swing.text.MaskFormatter;

import org.jdesktop.beansbinding.Validator;

/**
 *
 * @author Miro
 */
public class MaskFormatterValidator
    extends BaseValidator
    implements Serializable
{
    
    private TextLengthValidator textLengthValidator;
    boolean validateTextLength = false;

    public MaskFormatterValidator()
    {
        textLengthValidator = new TextLengthValidator();
    }

    @Override
    public Validator.Result validate(Object value) {
        
        //if something else, but string - fail
        if (value!=null && !(value instanceof String)){
            return ValidationError.InvalidMaskFormatterString.getValidatorResult();
        }
        
        if ( validateTextLength ){
            Result lengthValidation = textLengthValidator.validate(value);
            if ( lengthValidation!=null )
                return lengthValidation;
        }
        
        try{
            new MaskFormatter((String) value);
        }catch (Exception e){
            return ValidationError.InvalidMaskFormatterString.getValidatorResult();
        }
        
        return null;
    }

    /**
     * 
     * @param maxLength
     */
    public void setMaxLength(int maxLength) {
        validateTextLength = true;
        textLengthValidator.setMaxLength(maxLength);
    }

    /**
     * 
     * @param minLength
     */
    public void setMinLength(int minLength) {
        validateTextLength = true;
        textLengthValidator.setMinLength(minLength);
    }
}
