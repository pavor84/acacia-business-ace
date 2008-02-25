/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb.validation;

import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.JBTextField;

    
/**
 *
 * @author Bozhidar Bozhanov
 */
public class TextFieldValidator implements Validator {
    
    private int event;
    private boolean isValidationRequired = false;
    
    public TextFieldValidator(PropertyDetails details, JBTextField textField) {
        if (true){
        //setEvent(validation.event());
        //if (details.type() == ValidationType.REQUIRED)
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
