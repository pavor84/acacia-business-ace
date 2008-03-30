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

    public MaskFormatterValidator()
    {
    }

    @Override
    public Validator.Result validate(Object value) {
        
        //if something else, but string - fail
        if (value!=null && !(value instanceof String)){
            return ValidationError.InvalidMaskFormatterString.getValidatorResult();
        }
        
        try{
            new MaskFormatter((String) value);
        }catch (Exception e){
            return ValidationError.InvalidMaskFormatterString.getValidatorResult();
        }
        
        return null;
    }
}
