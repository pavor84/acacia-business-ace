/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.swingb;

import com.cosmos.beansbinding.PropertyDetails;
import org.jdesktop.beansbinding.Binding;

/**
 *
 * @author Miro
 */
public class ComboBoxBindingValidationListener extends BindingValidationListener {

    private PropertyDetails propertyDetails;

    public ComboBoxBindingValidationListener(JBComboBox comboBox, PropertyDetails propertyDetails) {
        super(comboBox);
        this.propertyDetails = propertyDetails;
    }

    @Override
    public void bindingBecameBound(Binding binding) {
        super.bindingBecameBound(binding);
        JBComboBox comboBox = ((JBComboBox) validatable);
        comboBox.setEditable(propertyDetails.isEditable());
        comboBox.setEnabled(!propertyDetails.isReadOnly());
    }
}
