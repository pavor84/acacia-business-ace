/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import java.util.List;
import javax.swing.JComboBox;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.swingbinding.JComboBoxBinding;
import org.jdesktop.swingbinding.SwingBindings;

/**
 *
 * @author Miro
 */
public class JBComboBox
    extends JComboBox
{
    private ObservableList data;
    private String propertyName;
    private Object beanEntity;


    public JComboBoxBinding bind(BindingGroup bindingGroup,
            List data,
            Object beanEntity,
            String propertyName)
    {
        return bind(bindingGroup, data, beanEntity, propertyName, AutoBinding.UpdateStrategy.READ_WRITE);
    }

    public JComboBoxBinding bind(BindingGroup bindingGroup,
            List data,
            Object beanEntity,
            String propertyName,
            AutoBinding.UpdateStrategy updateStrategy)
    {
        if(!(data instanceof ObservableList))
            this.data = ObservableCollections.observableList(data);
        else
            this.data = (ObservableList)data;
        this.propertyName = propertyName;
        this.beanEntity = beanEntity;

        JComboBoxBinding comboBoxBinding = SwingBindings.createJComboBoxBinding(updateStrategy, this.data, this);
        bindingGroup.addBinding(comboBoxBinding);

        ELProperty elProperty = ELProperty.create("${" + propertyName + "}");
        BeanProperty beanProperty = BeanProperty.create("selectedItem");
        Binding binding = Bindings.createAutoBinding(updateStrategy, beanEntity, elProperty, this, beanProperty);
        bindingGroup.addBinding(binding);

        return comboBoxBinding;
    }

    public ObservableList getData() {
        return data;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Object getBeanEntity() {
        return beanEntity;
    }


}
