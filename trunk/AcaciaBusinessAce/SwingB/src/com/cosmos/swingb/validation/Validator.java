/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb.validation;

/**
 *
 * @author Bozhidar Bozhanov
 */
public interface Validator {
    
   public boolean isValid();
   public boolean isValidationRequired();
   public int getEvent();
}
