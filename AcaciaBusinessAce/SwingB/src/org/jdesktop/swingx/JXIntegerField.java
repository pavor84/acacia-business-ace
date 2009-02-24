/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.swingx;

import java.math.BigInteger;

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
        setValueClass(BigInteger.class);
        setPattern("#,##0");
        super.init();
    }
}
