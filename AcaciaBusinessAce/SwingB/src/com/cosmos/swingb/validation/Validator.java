/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb.validation;

import com.cosmos.acacia.annotation.ValidationType;

/**
 *
 * @author Bozhidar Bozhanov
 */
public interface Validator {
    
   public boolean isValid();
   public boolean isValidationRequired();
   public int getEvent();
   public String getTooltipText();
   public ValidationType getValidationType();
}
