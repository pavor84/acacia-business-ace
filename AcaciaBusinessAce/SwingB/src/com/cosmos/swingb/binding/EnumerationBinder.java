/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.swingb.binding;

import com.cosmos.beansbinding.EntityProperty;
import com.cosmos.swingb.SelectableListDialog;
import java.util.List;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JComboBoxBinding;

/**
 *
 * @author Miro
 */
public interface EnumerationBinder extends Refreshable, Clearable {

    JComboBoxBinding bind(
            BindingGroup bindingGroup,
            List data,
            Object beanEntity,
            EntityProperty propertyDetails);

    JComboBoxBinding bind(
            BindingGroup bindingGroup,
            List data,
            Object beanEntity,
            EntityProperty propertyDetails,
            AutoBinding.UpdateStrategy updateStrategy);

    JComboBoxBinding bind(
            BindingGroup bindingGroup,
            SelectableListDialog selectableListDialog,
            Object beanEntity,
            EntityProperty propertyDetails);

    JComboBoxBinding bind(
            BindingGroup bindingGroup,
            SelectableListDialog selectableListDialog,
            Object beanEntity,
            EntityProperty propertyDetails,
            AutoBinding.UpdateStrategy updateStrategy);
}