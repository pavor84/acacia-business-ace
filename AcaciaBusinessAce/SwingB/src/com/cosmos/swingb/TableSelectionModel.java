/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import java.util.BitSet;

/**
 *
 * @author miro
 */
public class TableSelectionModel
    extends DefaultTableSelectionModel
{
    private BitSet lastValues;

    @Override
    protected void fireValueChanged(int firstIndex, int lastIndex, boolean isAdjusting) {
        if(!isAdjusting)
        {
            if(!value.equals(lastValues))
            {
                lastValues = (BitSet)value.clone();
                super.fireValueChanged(firstIndex, lastIndex, isAdjusting);
            }
        }
        else
            super.fireValueChanged(firstIndex, lastIndex, isAdjusting);
    }
}
