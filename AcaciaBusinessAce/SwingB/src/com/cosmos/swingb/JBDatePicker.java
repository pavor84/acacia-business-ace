/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import com.cosmos.beansbinding.PropertyDetails;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 * @author Miro
 */
public class JBDatePicker
    extends JXDatePicker
{

    public Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            PropertyDetails propertyDetails)
    {
        return bind(bindingGroup, beanEntity, propertyDetails, AutoBinding.UpdateStrategy.READ_WRITE);
    }

    public Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            PropertyDetails propertyDetails,
            AutoBinding.UpdateStrategy updateStrategy)
    {
        if(propertyDetails == null || propertyDetails.isHiden())
        {
            setEditable(false);
            setEnabled(false);
            return null;
        }

        Binding binding = bind(bindingGroup, beanEntity, propertyDetails.getPropertyName(), updateStrategy);
        setEditable(propertyDetails.isEditable());
        setEnabled(!propertyDetails.isReadOnly());

        return binding;
    }

     private Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            String propertyName,
            AutoBinding.UpdateStrategy updateStrategy)
     {
        ELProperty elProperty = ELProperty.create("${" + propertyName + "}");
        BeanProperty beanProperty = BeanProperty.create("date");
        Binding binding = Bindings.createAutoBinding(updateStrategy, beanEntity, elProperty, this, beanProperty);
        bindingGroup.addBinding(binding);

        return binding;

     }
}
