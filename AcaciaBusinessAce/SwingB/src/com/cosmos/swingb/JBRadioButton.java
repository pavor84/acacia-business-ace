/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import com.cosmos.beansbinding.PropertyDetails;
import javax.swing.Icon;
import javax.swing.JRadioButton;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;

/**
 *
 * @author Miro
 */
public class JBRadioButton
    extends JRadioButton
{
    private String propertyName;
    private Object beanEntity;

    public JBRadioButton() {
    }

    public JBRadioButton(String text, Icon icon, boolean selected) {
        super(text, icon, selected);
    }

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
            //setEditable(false);
            setEnabled(false);
            return null;
        }
        
        Binding binding = bind(bindingGroup, beanEntity, propertyDetails.getPropertyName(), updateStrategy);
        //setEditable(propertyDetails.isEditable());
        setEnabled(!propertyDetails.isReadOnly());

        return binding;
    }

    public Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            String propertyName)
    {
        return bind(bindingGroup, beanEntity, propertyName, AutoBinding.UpdateStrategy.READ_WRITE);
    }

    public Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            String propertyName,
            AutoBinding.UpdateStrategy updateStrategy)
    {
        this.propertyName = propertyName;
        this.beanEntity = beanEntity;
        ELProperty elProperty = ELProperty.create("${" + propertyName + "}");
        BeanProperty beanProperty = BeanProperty.create("selected");
        Binding binding = Bindings.createAutoBinding(updateStrategy,
                beanEntity,
                elProperty,
                this, beanProperty);
        bindingGroup.addBinding(binding);
        return binding;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Object getBeanEntity() {
        return beanEntity;
    }

}
