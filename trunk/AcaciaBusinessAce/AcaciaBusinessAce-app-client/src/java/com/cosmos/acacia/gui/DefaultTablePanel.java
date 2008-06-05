/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.gui;

import java.math.BigInteger;

/**
 *
 * @author Miro
 */
public class DefaultTablePanel
    extends AbstractTablePanel
{
    public DefaultTablePanel(BigInteger parentDataObjectId)
    {
        super(parentDataObjectId);
    }

    @Override
    protected boolean deleteRow(Object rowObject) {
        return false;
    }

    @Override
    protected Object modifyRow(Object rowObject) {
        return null;
    }

    @Override
    protected Object newRow() {
        return null;
    }

    @Override
    public boolean canCreate() {
        return false;
    }

    @Override
    public boolean canModify(Object rowObject) {
        return false;
    }

    @Override
    public boolean canDelete(Object rowObject) {
        return false;
    }

}
