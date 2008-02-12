/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.beansbinding;

import java.util.Collection;
import java.util.List;
import javax.swing.JTable;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.JTableBinding.ColumnBinding;
import org.jdesktop.swingbinding.SwingBindings;

/**
 *
 * @author miro
 */
public class BeansBinding {

    public static JTableBinding createTableBinding(
            JTable targetTable,
            List data,
            EntityProperties entityProperties) {
        ;
        AutoBinding.UpdateStrategy updateStrategy = entityProperties.getUpdateStrategy();
        if(updateStrategy == null)
            updateStrategy = AutoBinding.UpdateStrategy.READ;
        return createTableBinding(targetTable, data, entityProperties, updateStrategy);
    }

    public static JTableBinding createTableBinding(
            JTable targetTable,
            List data,
            EntityProperties entityProperties,
            AutoBinding.UpdateStrategy updateStrategy) {
        JTableBinding tableBinding = SwingBindings.createJTableBinding(updateStrategy, data, targetTable);
        createColumnsBinding(tableBinding, entityProperties);

        return tableBinding;
    }

    public static void createColumnsBinding(
            JTableBinding tableBinding,
            EntityProperties entityProperties)
    {
        createColumnsBinding(tableBinding, entityProperties.getValues());
    }

    public static void createColumnsBinding(
            JTableBinding tableBinding,
            Collection<PropertyDetails> properties)
    {
        for(PropertyDetails property : properties)
        {
            createColumnBinding(tableBinding, property);
        }
    }

    public static ColumnBinding createColumnBinding(
            JTableBinding tableBinding,
            PropertyDetails propertyDetails)
    {
        String expression = "${" + propertyDetails.getPropertyName() + "}";
        ELProperty elProperty = ELProperty.create(expression);
        ColumnBinding columnBinding = tableBinding.addColumnBinding(elProperty);
        columnBinding.setColumnName(propertyDetails.getPropertyTitle());
        columnBinding.setColumnClass(propertyDetails.getPropertyClass());

        Boolean b = propertyDetails.isEditable();
        if(b != null)
            columnBinding.setEditable(b.booleanValue());

        b = propertyDetails.isVisible();
        if(b != null)
            columnBinding.setVisible(b.booleanValue());

        Object obj = propertyDetails.getSourceUnreadableValue();
        if(obj != null)
            columnBinding.setSourceUnreadableValue(obj);

        //columnBinding.setConverter()
        //columnBinding.setValidator(arg0)

        return columnBinding;
    }
}
