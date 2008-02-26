/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb.validation;

import com.cosmos.swingb.JBComboBox;
import com.cosmos.swingb.JBTextField;
import java.util.Map;
import java.util.TreeMap;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;

/**
 *
 * @author Bozhidar Bozhanov
 */
public abstract class FormValidator {
    
    public Map<String, Object> getFieldValues(BindingGroup group){
        Map<String, Object> values = new TreeMap<String, Object>();
        
        for(Binding binding: group.getBindings()){
            Object targetObject = binding.getTargetObject();
            if (targetObject instanceof JBTextField){
                values.put(binding.getName(), ((JBTextField) targetObject).getText());
            }
            if (targetObject instanceof JBComboBox){
                values.put(binding.getName(), ((JBComboBox) targetObject).getSelectedItem());
            }
        }
        
        return values;
    }
    
    public abstract boolean validateForm(BindingGroup group);
}
