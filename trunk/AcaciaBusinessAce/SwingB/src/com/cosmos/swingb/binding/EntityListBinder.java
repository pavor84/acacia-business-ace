/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.swingb.binding;

import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.SelectableListDialog;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JComboBoxBinding;

/**
 *
 * @author Miro
 */
public interface EntityListBinder {

    JComboBoxBinding bind(BindingGroup bindingGroup, SelectableListDialog selectableListDialog,
            Object beanEntity, PropertyDetails propertyDetails);
}
