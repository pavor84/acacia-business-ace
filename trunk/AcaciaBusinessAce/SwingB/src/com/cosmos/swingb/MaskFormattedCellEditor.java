/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.swingb;

import java.text.ParseException;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Miro
 */
public class MaskFormattedCellEditor
    extends FormattedCellEditor
{
    private MaskFormatter maskFormatter;

    public MaskFormattedCellEditor(String mask)
        throws ParseException
    {
        this(new MaskFormatter(mask));
    }

    public MaskFormattedCellEditor(MaskFormatter maskFormatter)
    {
        super(maskFormatter);
        this.maskFormatter = maskFormatter;
        maskFormatter.setValueContainsLiteralCharacters(false);
    }

    protected MaskFormatter getMaskFormatter()
    {
        return maskFormatter;
    }

    public void setValueContainsLiteralCharacters(boolean containsLiteralChars)
    {
        maskFormatter.setValueContainsLiteralCharacters(containsLiteralChars);
    }

    public boolean getValueContainsLiteralCharacters()
    {
        return maskFormatter.getValueContainsLiteralCharacters();
    }
}
