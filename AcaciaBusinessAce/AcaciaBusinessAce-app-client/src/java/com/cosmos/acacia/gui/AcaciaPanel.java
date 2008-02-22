/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.gui;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.gui.AcaciaApplication;
import com.cosmos.swingb.JBPanel;

/**
 *
 * @author miro
 */
public abstract class AcaciaPanel
    extends JBPanel
{

    private DataObject parentDataObject;


    AcaciaPanel() {
        super(AcaciaApplication.class);
    }
 
    public AcaciaPanel(DataObject parentDataObject) {
        this();
        this.parentDataObject = parentDataObject;
    }

    public DataObject getParentDataObject() {
        return parentDataObject;
    }

    public void setParentDataObject(DataObject parentDataObject) {
        this.parentDataObject = parentDataObject;
    }

    protected abstract void initData();
}