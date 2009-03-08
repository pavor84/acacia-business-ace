/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.swingx;

import java.math.BigInteger;
import java.text.NumberFormat;

/**
 *
 * @author Miro
 */
public class JXIntegerField extends JXNumberField {

    public JXIntegerField(BigInteger value) {
        super(value);
    }

    public JXIntegerField() {
    }

    @Override
    protected void init() {
        NumberFormat format = getNumberFormat();
        format.setParseIntegerOnly(true);
        setValueClass(BigInteger.class);
        setPattern("#,##0");
        super.init();
    }
}
