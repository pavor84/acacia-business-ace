/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;
import org.jdesktop.swingx.JXList;

/**
 *
 * @author Miro
 */
public class JBList
    extends JXList
{

    public JBList()
    {
        addMouseListener(new MouseHandler());
        setCellRenderer(new CheckBoxListCellRenderer());
    }


    private class MouseHandler
        extends MouseAdapter
    {
        @Override
        public void mouseClicked(MouseEvent event)
        {
            int index = locationToIndex(event.getPoint());
            if(index < 0)
                return;

            Object item = getModel().getElementAt(index);
            if(item instanceof JCheckBox)
            {
                JCheckBox checkBox = (JCheckBox)item;
                checkBox.setSelected(!checkBox.isSelected());
                Rectangle rect = getCellBounds(index, index);
                repaint(rect);
            }
        }
    }
}
