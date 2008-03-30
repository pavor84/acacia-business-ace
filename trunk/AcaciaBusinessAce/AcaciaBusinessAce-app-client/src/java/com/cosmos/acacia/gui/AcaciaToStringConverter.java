/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.gui;

import com.cosmos.acacia.crm.gui.AcaciaApplication;
import com.cosmos.swingb.BeanResourceToStringConverter;

/**
 *
 * @author Miro
 */
public class AcaciaToStringConverter
    extends BeanResourceToStringConverter
{
    public AcaciaToStringConverter()
    {
        super(AcaciaApplication.class);
    }
}
