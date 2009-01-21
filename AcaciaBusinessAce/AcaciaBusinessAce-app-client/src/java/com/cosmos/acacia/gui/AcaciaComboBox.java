/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.gui;

import java.util.List;

import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JComboBoxBinding;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

import com.cosmos.acacia.crm.gui.AcaciaApplication;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.JBComboBox;

/**
 *
 * @author miro
 */
public class AcaciaComboBox extends JBComboBox {

    public AcaciaComboBox() {
    }

    @SuppressWarnings("unchecked")
    @Override
    public JComboBoxBinding bind(BindingGroup bindingGroup, List data, Object beanEntity,
                                 PropertyDetails propertyDetails, UpdateStrategy updateStrategy) {
        AcaciaToStringConverter resourceToStringConverter = createToStringConverter(propertyDetails);
        
        AutoCompleteDecorator.decorate(this, resourceToStringConverter);
        setConverter(resourceToStringConverter);
        return super.bind(bindingGroup, data, beanEntity, propertyDetails,
            AutoBinding.UpdateStrategy.READ_WRITE);
    }
    
    private AcaciaToStringConverter createToStringConverter(PropertyDetails propertyDetails) {
        String customElProperty = propertyDetails.getCustomDisplay();
        if ( customElProperty==null )
            return new AcaciaToStringConverter();
        else{
            //cut the first part of all properties, since it is the name of the property in the entity object.
            customElProperty = customElProperty.replaceAll(propertyDetails.getPropertyName()+".", "");
            return new AcaciaToStringConverter(customElProperty);
        }
    }

   @SuppressWarnings("unchecked")
    @Override
    public JComboBoxBinding bind(
            BindingGroup bindingGroup,
            List data,
            Object beanEntity,
            PropertyDetails propertyDetails)
    {
        AcaciaToStringConverter resourceToStringConverter = new AcaciaToStringConverter();
        AutoCompleteDecorator.decorate(this, resourceToStringConverter);
        setConverter(resourceToStringConverter);
        return super.bind(bindingGroup, data, beanEntity, propertyDetails, AutoBinding.UpdateStrategy.READ_WRITE);
    }

    @SuppressWarnings("unchecked")
    public JComboBoxBinding bind(
            BindingGroup bindingGroup,
            List data,
            Object beanEntity,
            PropertyDetails propertyDetails,
            ObjectToStringConverter converter)
    {
        AutoCompleteDecorator.decorate(this, converter);
        setConverter(converter);
        return super.bind(bindingGroup, data, beanEntity, propertyDetails, AutoBinding.UpdateStrategy.READ_WRITE);
    }
}
