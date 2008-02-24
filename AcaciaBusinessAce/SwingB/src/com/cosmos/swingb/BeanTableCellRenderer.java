/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import com.cosmos.resource.BeanResource;
import com.cosmos.resource.EnumResource;
import com.cosmos.resource.TextResource;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import org.jdesktop.application.Application;

/**
 *
 * @author Miro
 */
public class BeanTableCellRenderer
    extends DefaultTableCellRenderer
{
    private BeanResource beanResource;

    public BeanTableCellRenderer(Class<? extends Application> applicationClass)
    {
        beanResource = new BeanResource(applicationClass);
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column)
    {
        Component component;
        if(value instanceof EnumResource)
        {
            String valueName = beanResource.getFullName((EnumResource)value);
            component = super.getTableCellRendererComponent(table, valueName, isSelected, hasFocus, row, column);
        }
        else if(value instanceof TextResource)
        {
            String valueName = beanResource.getFullName((TextResource)value);
            component = super.getTableCellRendererComponent(table, valueName, isSelected, hasFocus, row, column);
        }
        else
        {
            System.out.println("value: " + value + ", class: " + (value != null ? value.getClass().getName() : "null"));
            component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }

        return component;
    }

    
}
