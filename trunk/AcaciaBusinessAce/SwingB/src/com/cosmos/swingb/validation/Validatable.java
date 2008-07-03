/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb.validation;

import org.jdesktop.application.ResourceMap;

/**
 * Interface which all validatable components should implement.
 * They should add a binding listener like:
 * 
 * <code>binding.addBindingListener(new BindingValidationListener(this));</code>
 * 
 * @author Bozhidar Bozhanov
 */
public interface Validatable {

    void setStyleRequired(String tooltip);

    void setStyleInvalid(String tooltip);
    
    void setStyleValid();
    
    void setStyleNormal();
    
    ResourceMap getResourceMap();
        
}
