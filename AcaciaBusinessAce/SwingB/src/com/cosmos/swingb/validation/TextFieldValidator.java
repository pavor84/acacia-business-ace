/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb.validation;

import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.JBTextField;

    
/**
 *
 * @author Bozhidar Bozhanov
 */
public class TextFieldValidator implements Validator {
    
    private boolean isValidationRequired = false;
    private JBTextField component;
    private PropertyDetails details;
    
    public TextFieldValidator(PropertyDetails details, JBTextField textField) {
        if (!ValidationType.NONE.equals(details.getValidationType()))
            setValidationRequired(true);
        
        this.details = details;
        this.component = textField;
    }

    public boolean isValid() {

        switch (details.getValidationType()){
            case NONE:
                return true;
                
            case REQUIRED:
                return component.getText().trim().length() > 0;
            
            case RANGE:
                try {
                    double value = Double.parseDouble(component.getText());
                    return (value >= details.getValidationRangeStart()
                            && value <= details.getValidationRangeEnd());
                } catch (NumberFormatException ex){
                    return false;
                }
                
            case CUSTOM:
                return false;
        }
        
        if (details.getValidationRegex() != null && details.getValidationRegex().length() > 0)
            return component.getText().matches(details.getValidationRegex());
        
        return false;
    }

    public int getEvent() {
        return details.getValidationEvent();
    }
    
    public void setValidationRequired(boolean required){
        isValidationRequired = required;
    }
    
    public boolean isValidationRequired(){
        return isValidationRequired;
    }
        
    public String getTooltipText(){
        //return details.getValidationTooltip();
        return "Bozhidar Bozhanov ima oshte dosta hliab da iade.";
    }
    
    public ValidationType getValidationType(){
        return details.getValidationType();
    }
    
}