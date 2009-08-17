/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.swingb.binding;

import com.cosmos.beansbinding.PropertyDetails;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;

/**
 *
 * @author Miro
 */
public interface EntityBinder extends Refreshable, Clearable {

    Binding bind(
            BindingGroup bindingGroup,
            Object beanEntity,
            PropertyDetails propertyDetails);

    Binding bind(
            BindingGroup bindingGroup,
            Object beanEntity,
            PropertyDetails propertyDetails,
            AutoBinding.UpdateStrategy updateStrategy);

    String getPropertyName();

    Object getBeanEntity();

    Binding getBinding();
}
