/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.swingb;

import java.awt.Color;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.DefaultCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author Miro
 */
public class FormattedCellEditor
    extends DefaultCellEditor
{
    protected Color editStateBackground = Color.WHITE;
    protected Color invalidStateBackground = Color.PINK;

    public FormattedCellEditor(AbstractFormatter formatter)
    {
        this((new JFormattedTextField(formatter)));

        JFormattedTextField textField = (JFormattedTextField)getComponent();
        if(formatter instanceof NumberFormatter)
        {
            textField.setHorizontalAlignment(JTextField.RIGHT);
        }
    }

    public FormattedCellEditor(Format format)
    {
        this((new JFormattedTextField(format)));

        JFormattedTextField textField = (JFormattedTextField)getComponent();
        if(format instanceof NumberFormat)
        {
            textField.setHorizontalAlignment(JTextField.RIGHT);
        }
    }

    private FormattedCellEditor(final JFormattedTextField textField)
    {
        super(textField);

        delegate = new EditorDelegate()
        {
            @Override
            public void setValue(Object value)
            {
                textField.setValue(value);

                if(textField.isEditValid())
                {
                    textField.setBackground(editStateBackground);
                }
                else
                {
                    textField.setBackground(invalidStateBackground);
                }
            }

            @Override
            public Object getCellEditorValue()
            {
                return textField.getValue();
            }

            @Override
            public boolean stopCellEditing()
            {
                if(textField.isEditValid())
                {
                    try
                    {
                        textField.commitEdit();
                        textField.setBackground(editStateBackground);
                    }
                    catch(ParseException ex)
                    {
                    }
                }
                else
                {
                    textField.setBackground(invalidStateBackground);
                    return false;
                }

                return super.stopCellEditing();
            }
        };

        textField.addActionListener(delegate);
    }

    public Color getEditStateBackground()
    {
        return editStateBackground;
    }

    public void setEditStateBackground(Color editStateBackground)
    {
        this.editStateBackground = editStateBackground;
    }

    public Color getInvalidStateBackground()
    {
        return invalidStateBackground;
    }

    public void setInvalidStateBackground(Color invalidStateBackground)
    {
        this.invalidStateBackground = invalidStateBackground;
    }

}
