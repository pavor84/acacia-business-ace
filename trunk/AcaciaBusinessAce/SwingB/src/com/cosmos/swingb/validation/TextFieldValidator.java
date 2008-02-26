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
    private String tooltipText;
    public TextFieldValidator(PropertyDetails details, JBTextField textField) {
        if (details.getValidationType() != ValidationType.NONE)
            setValidationRequired(true);
        
        this.details = details;
        this.component = textField;
    }

    public boolean isValid() {

        switch (details.getValidationType()){
            case ValidationType.NONE:
                return true;
                
            case ValidationType.REQUIRED:
                return component.getText().trim().length() > 0;
            
            case ValidationType.REGEX:
                return component.getText().matches(details.getValidationRegex());
             
            case ValidationType.RANGE:
                try {
                    int value = Integer.parseInt(component.getText());
                    return (value >= details.getValidationRangeStart()
                            && value <= details.getValidationRangeEnd());
                } catch (NumberFormatException ex){
                    return false;
                }
                
            case ValidationType.CUSTOM:
                return false;
            
            default:
                return false;
        }
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
        return tooltipText;
    }
    
    public void setTooltipText(String tooltipText){
        this.tooltipText = tooltipText;
    }
    
    
}
