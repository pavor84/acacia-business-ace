/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.swingb;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.TreeCellEditor;

/**
 *
 * @author Miro
 */
public class ComboListCellEditor
    extends AbstractCellEditor
    implements TableCellEditor, TreeCellEditor
{

    /** The Swing component being edited. */
    protected JComponent editorComponent;

    /**
     * The delegate class which handles all methods sent from the
     * <code>CellEditor</code>.
     */
    protected EditorDelegate delegate;
    /**
     * An integer specifying the number of clicks needed to start editing.
     * Even if <code>clickCountToStart</code> is defined as zero, it
     * will not initiate until a click occurs.
     */
    protected int clickCountToStart = 1;


    /**
     * Constructs a <code>DefaultCellEditor</code> object that uses a
     * combo box.
     *
     * @param comboBox  a <code>JComboBox</code> object
     */
    public ComboListCellEditor(final JBComboList comboList)
    {
        editorComponent = comboList;
        
        comboList.getComboBox().putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        delegate = new EditorDelegate()
        {

            public void setValue(Object value)
            {
                comboList.setSelectedItem(value);
            }

            public Object getCellEditorValue()
            {
                return comboList.getSelectedItem();
            }

            public boolean shouldSelectCell(EventObject anEvent)
            {
                if(anEvent instanceof MouseEvent)
                {
                    MouseEvent e = (MouseEvent) anEvent;
                    return e.getID() != MouseEvent.MOUSE_DRAGGED;
                }
                return true;
            }

            public boolean stopCellEditing()
            {
                if(comboList.isEditable())
                {
                    // Commit edited value.
                    comboList.actionPerformed(new ActionEvent(
                        ComboListCellEditor.this, 0, ""));
                }
                return super.stopCellEditing();
            }
        };
        comboList.addActionListener(delegate);
    }

    /**
     * Returns a reference to the editor component.
     *
     * @return the editor <code>Component</code>
     */
    public Component getComponent()
    {
        return editorComponent;
    }

//
//  Modifying
//
    /**
     * Specifies the number of clicks needed to start editing.
     *
     * @param count  an int specifying the number of clicks needed to start editing
     * @see #getClickCountToStart
     */
    public void setClickCountToStart(int count)
    {
        clickCountToStart = count;
    }

    /**
     * Returns the number of clicks needed to start editing.
     * @return the number of clicks needed to start editing
     */
    public int getClickCountToStart()
    {
        return clickCountToStart;
    }

//
//  Override the implementations of the superclass, forwarding all methods 
//  from the CellEditor interface to our delegate. 
//
    /**
     * Forwards the message from the <code>CellEditor</code> to
     * the <code>delegate</code>.
     * @see EditorDelegate#getCellEditorValue
     */
    public Object getCellEditorValue()
    {
        return delegate.getCellEditorValue();
    }

    /**
     * Forwards the message from the <code>CellEditor</code> to
     * the <code>delegate</code>.
     * @see EditorDelegate#isCellEditable(EventObject)
     */
    public boolean isCellEditable(EventObject anEvent)
    {
        return delegate.isCellEditable(anEvent);
    }

    /**
     * Forwards the message from the <code>CellEditor</code> to
     * the <code>delegate</code>.
     * @see EditorDelegate#shouldSelectCell(EventObject)
     */
    public boolean shouldSelectCell(EventObject anEvent)
    {
        return delegate.shouldSelectCell(anEvent);
    }

    /**
     * Forwards the message from the <code>CellEditor</code> to
     * the <code>delegate</code>.
     * @see EditorDelegate#stopCellEditing
     */
    public boolean stopCellEditing()
    {
        return delegate.stopCellEditing();
    }

    /**
     * Forwards the message from the <code>CellEditor</code> to
     * the <code>delegate</code>.
     * @see EditorDelegate#cancelCellEditing
     */
    public void cancelCellEditing()
    {
        delegate.cancelCellEditing();
    }

//
//  Implementing the TreeCellEditor Interface
//
    /** Implements the <code>TreeCellEditor</code> interface. */
    public Component getTreeCellEditorComponent(JTree tree, Object value,
        boolean isSelected,
        boolean expanded,
        boolean leaf, int row)
    {
        String stringValue = tree.convertValueToText(value, isSelected,
            expanded, leaf, row, false);

        delegate.setValue(stringValue);
        return editorComponent;
    }

//
//  Implementing the CellEditor Interface
//
    /** Implements the <code>TableCellEditor</code> interface. */
    public Component getTableCellEditorComponent(JTable table, Object value,
        boolean isSelected,
        int row, int column)
    {
        delegate.setValue(value);
        return editorComponent;
    }


    /**
     * The protected <code>EditorDelegate</code> class.
     */
    protected class EditorDelegate
        implements ActionListener, ItemListener, Serializable
    {

        /**  The value of this cell. */
        protected Object value;

        /**
         * Returns the value of this cell. 
         * @return the value of this cell
         */
        public Object getCellEditorValue()
        {
            return value;
        }

        /**
         * Sets the value of this cell. 
         * @param value the new value of this cell
         */
        public void setValue(Object value)
        {
            this.value = value;
        }

        /**
         * Returns true if <code>anEvent</code> is <b>not</b> a
         * <code>MouseEvent</code>.  Otherwise, it returns true
         * if the necessary number of clicks have occurred, and
         * returns false otherwise.
         *
         * @param   anEvent         the event
         * @return  true  if cell is ready for editing, false otherwise
         * @see #setClickCountToStart
         * @see #shouldSelectCell
         */
        public boolean isCellEditable(EventObject anEvent)
        {
            if(anEvent instanceof MouseEvent)
            {
                return ((MouseEvent) anEvent).getClickCount() >= clickCountToStart;
            }
            return true;
        }

        /**
         * Returns true to indicate that the editing cell may
         * be selected.
         *
         * @param   anEvent         the event
         * @return  true 
         * @see #isCellEditable
         */
        public boolean shouldSelectCell(EventObject anEvent)
        {
            return true;
        }

        /**
         * Returns true to indicate that editing has begun.
         *
         * @param anEvent          the event
         */
        public boolean startCellEditing(EventObject anEvent)
        {
            return true;
        }

        /**
         * Stops editing and
         * returns true to indicate that editing has stopped.
         * This method calls <code>fireEditingStopped</code>.
         *
         * @return  true 
         */
        public boolean stopCellEditing()
        {
            fireEditingStopped();
            return true;
        }

        /**
         * Cancels editing.  This method calls <code>fireEditingCanceled</code>.
         */
        public void cancelCellEditing()
        {
            fireEditingCanceled();
        }

        /**
         * When an action is performed, editing is ended.
         * @param e the action event
         * @see #stopCellEditing
         */
        public void actionPerformed(ActionEvent e)
        {
            ComboListCellEditor.this.stopCellEditing();
        }

        /**
         * When an item's state changes, editing is ended.
         * @param e the action event
         * @see #stopCellEditing
         */
        public void itemStateChanged(ItemEvent e)
        {
            ComboListCellEditor.this.stopCellEditing();
        }
    }
}
