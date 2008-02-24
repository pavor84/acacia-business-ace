/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import javax.swing.JCheckBox;
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
public class JBCheckBox
    extends JCheckBox
{
    private String propertyName;
    private Object beanEntity;

//binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.isComplex}"), complexProductCheckBox, org.jdesktop.beansbinding.BeanProperty.create("selected"));

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
