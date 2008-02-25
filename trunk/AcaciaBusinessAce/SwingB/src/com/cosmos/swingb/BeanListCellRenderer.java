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
        value = beanResource.getValue(value);
        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    }

}
