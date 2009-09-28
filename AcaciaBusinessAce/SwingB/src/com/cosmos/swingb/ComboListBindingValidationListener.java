/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.swingb;

import com.cosmos.beansbinding.EntityProperty;
import org.jdesktop.beansbinding.Binding;

/**
 *
 * @author Miro
 */
public class ComboListBindingValidationListener extends BindingValidationListener {

    private EntityProperty propertyDetails;

    public ComboListBindingValidationListener(JBComboList comboList, EntityProperty propertyDetails) {
        super(comboList);
        this.propertyDetails = propertyDetails;
    }

    @Override
    public void bindingBecameBound(Binding binding) {
        super.bindingBecameBound(binding);
        JBComboList comboList = ((JBComboList) validatable);
        comboList.setEditable(propertyDetails.isEditable());
        comboList.setEnabled(!propertyDetails.isReadOnly());
    }
}
