/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import javax.swing.ListSelectionModel;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author Miro
 */
public class JBTable
    extends JXTable
{
    public JBTable()
    {
        internalInitialization();
    }

    protected void internalInitialization()
    {
        setAutoResizeMode(AUTO_RESIZE_OFF);
        setColumnControlVisible(true);
        getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setHorizontalScrollEnabled(true);
        setEditable(false);
    }
}
