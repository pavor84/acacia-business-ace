/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.callback.assembling;

import com.cosmos.acacia.crm.assembling.AlgorithmException;

/**
 *
 * @author Miro
 */
public class MoreSelectedItemsThanAllowedException
    extends AlgorithmException
{
    int selected;
    int allowed;

    public MoreSelectedItemsThanAllowedException(int selected, int allowed)
    {
        super("The selected (" + selected + ") items are more than the allowed (" + allowed +")");
        this.selected = selected;
        this.allowed = allowed;
    }

    public int getAllowed() {
        return allowed;
    }

    public int getSelected() {
        return selected;
    }

}
