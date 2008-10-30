/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JCheckBox;
import javax.swing.KeyStroke;
import org.jdesktop.swingx.JXList;

/**
 *
 * @author Miro
 */
public class JBList
    extends JXList
{
    private static final String SPACE_PRESSED = "pressed SPACE";
    private static final String SPACE_RELEASED = "released SPACE";


    public JBList()
    {
        init();
    }

    private void init()
    {
        addMouseListener(new MouseHandler());
        setCellRenderer(new CheckBoxListCellRenderer());

        SpacePressedAction spacePressedAction = new SpacePressedAction();
        InputMap inputMap = getInputMap();
        inputMap.put(KeyStroke.getKeyStroke(SPACE_PRESSED),
                spacePressedAction.getValue(Action.NAME));
        ActionMap actionMap = getActionMap();
        actionMap.put(spacePressedAction.getValue(Action.NAME), spacePressedAction);

        SpaceReleasedAction spaceReleasedAction = new SpaceReleasedAction();
        inputMap.put(KeyStroke.getKeyStroke(SPACE_RELEASED),
                spaceReleasedAction.getValue(Action.NAME));
        actionMap.put(spaceReleasedAction.getValue(Action.NAME), spaceReleasedAction);
    }

    protected void setSelectedCheckBox(int index)
    {
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

    private class MouseHandler
        extends MouseAdapter
    {
        @Override
        public void mouseClicked(MouseEvent event)
        {
            if(event.getClickCount() < 2)
                return;

            JBList list = JBList.this;
            if(!list.isEnabled())
                return;

            int index = list.locationToIndex(event.getPoint());
            setSelectedCheckBox(index);
        }
    }

    private class SpacePressedAction
        extends AbstractAction
    {

        public SpacePressedAction()
        {
            super(SPACE_PRESSED);
        }

        public void actionPerformed(ActionEvent event)
        {
        }
    }

    private class SpaceReleasedAction
        extends AbstractAction
    {
        public SpaceReleasedAction()
        {
            super(SPACE_RELEASED);
        }

        public void actionPerformed(ActionEvent event)
        {
            JBList list = JBList.this;
            if(!list.isEnabled())
                return;

            int index = list.getSelectedIndex();
            setSelectedCheckBox(index);
        }
    }
}
