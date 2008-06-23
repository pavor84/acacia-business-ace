/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.swingb;

import java.awt.Color;
import java.awt.Component;
import java.text.Format;
import java.text.ParseException;
import javax.swing.DefaultCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author Miro
 */
public class FormattedCellEditor
    extends DefaultCellEditor
{
    protected Border editStateBorder = new LineBorder(Color.black);
    protected Border invalidStateBorder = new LineBorder(Color.red);

    public FormattedCellEditor(Format format)
    {
        this((new JFormattedTextField(format)));
    }

    public FormattedCellEditor(final JFormattedTextField textField)
    {
        super(textField);
        delegate = new EditorDelegate()
        {
            @Override
            public void setValue(Object value)
            {
                textField.setValue(value);
            }

            @Override
            public Object getCellEditorValue()
            {
                return textField.getValue();
            }

            @Override
            public boolean stopCellEditing()
            {
                /*if(textField.isEditValid())
                {
                    try
                    {
                        textField.commitEdit();
                    }
                    catch(ParseException ex)
                    {
                    }
                }
                else
                {
                    textField.setBorder(invalidStateBorder);
                    return false;
                }
                return super.stopCellEditing();*/

                try
                {
                    textField.commitEdit();
                }
                catch(ParseException ex)
                {
                    return false; // This will prevent the cell from losing focus.
                // (User will have to enter a valid value or hit escape to revert.)
                // Alternative is to not return anything here--cell will revert.
                }

                return super.stopCellEditing();
            }
        };

        textField.addActionListener(delegate);
    }

    /*@Override
    public Component getTableCellEditorComponent(
        JTable table,
        Object value,
        boolean isSelected,
        int row, int column)
    {
        delegate.setValue(value);
        if (((JFormattedTextField)editorComponent).isEditValid())
            editorComponent.setBorder(editStateBorder);
        else
            editorComponent.setBorder(invalidStateBorder);

        return editorComponent;
     }*/
}
