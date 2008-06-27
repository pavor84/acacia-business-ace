/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.swingb;

import java.io.Serializable;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Miro
 */
public class MaskFormattedCellRenderer
    extends FormattedCellRenderer
{
    private MaskFormattedCellEditor cellEditor;

    public MaskFormattedCellRenderer(MaskFormattedCellEditor cellEditor)
    {
        this(cellEditor, "");
    }

    public MaskFormattedCellRenderer(MaskFormattedCellEditor cellEditor, String nullText)
    {
        this(cellEditor, nullText, "");
    }

    public MaskFormattedCellRenderer(
        MaskFormattedCellEditor cellEditor,
        String nullText,
        String invalidText)
    {
        super(new MaskFormat(cellEditor.getMaskFormatter()), nullText, invalidText);
    }

    protected MaskFormattedCellEditor getCellEditor()
    {
        return cellEditor;
    }

    protected MaskFormatter getMaskFormatter()
    {
        MaskFormat maskFormat = (MaskFormat)getFormat();
        return maskFormat.getMaskFormatter();
    }

    private static class MaskFormat
        extends Format
        implements Serializable
    {
        private MaskFormatter maskFormatter;

        public MaskFormat(MaskFormatter maskFormatter)
        {
            this.maskFormatter = maskFormatter;
        }

        @Override
        public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition fieldPosition)
        {
            try
            {
                return toAppendTo.append(maskFormatter.valueToString(obj));
            }
            catch(ParseException ex)
            {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }

        @Override
        public Object parseObject(String source, ParsePosition pos)
        {
            try
            {
                return maskFormatter.stringToValue(source);
            }
            catch(ParseException ex)
            {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }

        public MaskFormatter getMaskFormatter()
        {
            return maskFormatter;
        }
    }

}
