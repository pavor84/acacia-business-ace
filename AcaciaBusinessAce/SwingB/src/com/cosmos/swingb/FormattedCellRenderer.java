/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.swingb;

import java.text.Format;
import java.text.NumberFormat;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Miro
 */
public class FormattedCellRenderer
    extends DefaultTableCellRenderer
{
    protected Format format;
    protected String nullText;
    protected String invalidText;

    public FormattedCellRenderer(Format format)
    {
        this(format, "");
    }

    public FormattedCellRenderer(Format format, String nullText)
    {
        this(format, nullText, "");
    }

    public FormattedCellRenderer(
        Format format,
        String nullText,
        String invalidText)
    {
        this.format = format;
        this.nullText = nullText;
        this.invalidText = invalidText;
        if(format instanceof NumberFormat)
        {
            setHorizontalAlignment(JTextField.RIGHT);
        }
    }

    public void setFormat(Format format)
    {
        this.format = format;
    }

    public Format getFormat()
    {
        return format;
    }

    public void setNullText(String nullText)
    {
        this.nullText = (nullText != null ? nullText : this.nullText);
    }

    public String getNullText()
    {
        return nullText;
    }

    public void setInvalidText(String invalidText)
    {
        this.invalidText = (invalidText != null ? invalidText : this.invalidText);
    }

    public String getInvalidText()
    {
        return invalidText;
    }

    @Override
    public void setValue(Object value)
    {
        String text;
        try
        {
            if(value == null)
            {
                text = nullText;
            }
            else if(format == null)
            {
                text = value.toString();
            }
            else
            {
                text = format.format(value);
            }
        }
        catch(Exception ex)
        {
            text = invalidText;
        }
        setText(text);
    }
}
