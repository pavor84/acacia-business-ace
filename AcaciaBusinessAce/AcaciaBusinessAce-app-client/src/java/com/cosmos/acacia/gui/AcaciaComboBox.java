/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.gui;

import com.cosmos.swingb.JBComboBox;

/**
 *
 * @author miro
 */
public class AcaciaComboBox
    extends JBComboBox
{

    public AcaciaComboBox()
    {
        setRenderer(new BeanListCellRenderer());
    }
}
