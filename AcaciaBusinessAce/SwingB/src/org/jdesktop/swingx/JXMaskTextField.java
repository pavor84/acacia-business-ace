/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.swingx;

import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import org.jdesktop.swingx.text.XMaskFormatter;

/**
 *
 * @author Miro
 */
public class JXMaskTextField extends JFormattedTextField {

    private XMaskFormatter maskFormatter;
    private DefaultFormatterFactory maskFormatterFactory;

    public JXMaskTextField() {
        super(new XMaskFormatter());
        setFocusLostBehavior(COMMIT);
    }

    public void setMask(String mask) throws ParseException {
        if(mask == null) {
            AbstractFormatter formatter;
            if((formatter = getFormatter()) != null) {
                formatter.uninstall();
                setFormatterFactory(null);
            }

            return;
        }

        MaskFormatter formatter = (MaskFormatter)getFormatter();
        if((formatter = (MaskFormatter)getFormatter()) == null) {
            setFormatterFactory(getMaskFormatterFactory());
            formatter = (MaskFormatter)getFormatter();
        }

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

    public XMaskFormatter getMaskFormatter() {
        if(maskFormatter == null) {
            maskFormatter = new XMaskFormatter();
        }

        return maskFormatter;
    }

    public void setMaskFormatter(XMaskFormatter maskFormatter) {
        this.maskFormatter = maskFormatter;
    }

    public DefaultFormatterFactory getMaskFormatterFactory() {
        if(maskFormatterFactory == null) {
            maskFormatterFactory = new DefaultFormatterFactory(getMaskFormatter());
        }

        return maskFormatterFactory;
    }

    public void setMaskFormatterFactory(DefaultFormatterFactory maskFormatterFactory) {
        this.maskFormatterFactory = maskFormatterFactory;
    }
}
