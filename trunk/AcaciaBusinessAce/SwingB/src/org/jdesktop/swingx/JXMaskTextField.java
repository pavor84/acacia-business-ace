/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.swingx;

import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;
import org.jdesktop.swingx.text.XMaskFormatter;

/**
 *
 * @author Miro
 */
public class JXMaskTextField extends JFormattedTextField {

    public JXMaskTextField() {
        super(new XMaskFormatter());
        setFocusLostBehavior(COMMIT);
    }

    public void setMask(String mask) throws ParseException {
        MaskFormatter formatter = (MaskFormatter)getFormatter();
        Object value = null;
        String text = getText();
        if(text != null && text.length() > 0) {
            try {
                value = formatter.stringToValue(text);
            } catch(Exception ex) {}
        }

        setValue(null);
        formatter.setMask(mask);
        formatter.install(this);
        if(value != null)
            setValue(value);
    }

    public String getMask() {
        MaskFormatter formatter = (MaskFormatter)getFormatter();
        return formatter.getMask();
    }
}
