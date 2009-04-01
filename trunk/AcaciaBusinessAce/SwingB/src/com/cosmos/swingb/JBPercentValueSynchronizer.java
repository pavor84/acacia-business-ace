/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.swingb;

import org.jdesktop.swingx.JXPercentValueSynchronizer;

/**
 *
 * @author Miro
 */
public class JBPercentValueSynchronizer extends JXPercentValueSynchronizer {

    public JBPercentValueSynchronizer(JBPercentField percentField, JBDecimalField decimalField) {
        super(percentField, decimalField);
    }
}
