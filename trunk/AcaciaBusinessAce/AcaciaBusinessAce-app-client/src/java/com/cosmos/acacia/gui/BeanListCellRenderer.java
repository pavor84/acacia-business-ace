/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.gui;

import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.gui.AcaciaApplication;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;


/**
 *
 * @author miro
 */
public class BeanListCellRenderer
    extends DefaultListCellRenderer
{
    private BeanResource beanResource = new BeanResource(AcaciaApplication.class);

    @Override
    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus)
    {
        Component component;
        if(value instanceof DbResource)
        {
            String valueName = beanResource.getFullName((DbResource)value);
            component = super.getListCellRendererComponent(list, valueName, index, isSelected, cellHasFocus);
        }
        else
            component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        return component;
    }

}
