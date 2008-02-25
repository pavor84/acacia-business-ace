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
        value = beanResource.getValue(value);
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }

}
