/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import com.cosmos.resource.TextResource;
import com.cosmos.resource.BeanResource;
import com.cosmos.resource.EnumResource;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import org.jdesktop.application.Application;


/**
 *
 * @author miro
 */
public class BeanListCellRenderer
    extends DefaultListCellRenderer
{
    private BeanResource beanResource;

    public BeanListCellRenderer(Class<? extends Application> applicationClass)
    {
        beanResource = new BeanResource(applicationClass);
    }

    @Override
    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus)
    {
        Component component;
        if(value instanceof EnumResource)
        {
            String valueName = beanResource.getFullName((EnumResource)value);
            component = super.getListCellRendererComponent(list, valueName, index, isSelected, cellHasFocus);
        }
        else if(value instanceof TextResource)
        {
            String valueName = beanResource.getFullName((TextResource)value);
            component = super.getListCellRendererComponent(list, valueName, index, isSelected, cellHasFocus);
        }
        else
            component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        return component;
    }

}
