/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.swingb.binding;

import com.cosmos.beansbinding.PropertyDetails;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.ELProperty;

/**
 *
 * @author Miro
 */
public interface EntityBinder extends Refreshable {

    Binding bind(BindingGroup bindingGroup, Object beanEntity, PropertyDetails propertyDetails);

    String getPropertyName();

    Object getBeanEntity();

    ELProperty getELProperty();

    Binding getBinding();
}
