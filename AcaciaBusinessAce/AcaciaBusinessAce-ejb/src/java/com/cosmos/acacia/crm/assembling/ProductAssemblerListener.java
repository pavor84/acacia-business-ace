/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

import java.util.EventListener;

/**
 *
 * @author Miro
 */
public interface ProductAssemblerListener
    extends EventListener
{
    void productAssemblerEvent(ProductAssemblerEvent event);
}
