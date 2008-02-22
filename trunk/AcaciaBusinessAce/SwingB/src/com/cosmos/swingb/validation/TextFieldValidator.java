/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb.validation;

import com.cosmos.swingb.JBTextField;
import java.lang.reflect.Field;

/**
 *
 * @author Bozhidar Bozhanov
 */
public class TextFieldValidator implements Validator {
    
    private int event;
    private boolean isValidationRequired = false;
    
    public TextFieldValidator(Object entity, String property, JBTextField textField)
            throws NoSuchFieldException {
        Field field = entity.getClass().getDeclaredField(property);
        if (field.isAnnotationPresent(Validation.class)){
            Validation validation = field.getAnnotation(Validation.class);
            setEvent(validation.event());
            if (validation.type() == ValidationType.REQUIRED)
                setValidationRequired(true);
        }
    }
    
    public boolean isValid() {
        return true;
    }

    public int getEvent() {
        return event;
    }
    
     public void setEvent(int event) {
        this.event = event;
    }

    public void setValidationRequired(boolean required){
        isValidationRequired = required;
    }
    
    public boolean isValidationRequired() {
        return isValidationRequired;
    }
}
