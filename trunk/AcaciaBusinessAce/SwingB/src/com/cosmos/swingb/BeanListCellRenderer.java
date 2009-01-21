/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import com.cosmos.resource.BeanResource;
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

    public BeanListCellRenderer()
    {
        beanResource = new BeanResource();
    }

    public BeanListCellRenderer(Application application)
    {
        beanResource = new BeanResource(application);
    }

    @Override
    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus)
    {
        value = beanResource.getFullNameValue(value);
        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    }

}
