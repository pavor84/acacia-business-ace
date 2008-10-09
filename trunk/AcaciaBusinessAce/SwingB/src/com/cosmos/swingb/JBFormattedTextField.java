/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import java.text.Format;
import javax.swing.JFormattedTextField;

/**
 *
 * @author Miro
 */
public class JBFormattedTextField
    extends JFormattedTextField
{

    public JBFormattedTextField(AbstractFormatterFactory factory, Object currentValue)
    {
        super(factory, currentValue);
    }

    public JBFormattedTextField(AbstractFormatterFactory factory)
    {
        super(factory);
    }

    public JBFormattedTextField(AbstractFormatter formatter)
    {
        super(formatter);
    }

    public JBFormattedTextField(Format format)
    {
        super(format);
    }

    public JBFormattedTextField(Object value)
    {
        super(value);
    }

    public JBFormattedTextField()
    {
    }

}
