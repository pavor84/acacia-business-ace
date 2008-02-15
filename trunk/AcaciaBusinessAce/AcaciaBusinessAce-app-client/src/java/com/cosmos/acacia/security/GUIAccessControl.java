/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.security;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;

/**
 *
 * @author miro
 */
public abstract class GUIAccessControl {

    public boolean canCreate(DataObjectBean entity)
    {
        return canCreate(entity.getDataObject());
    }

    public boolean canModify(DataObjectBean entity)
    {
        return canModify(entity.getDataObject());
    }

    public boolean canDelete(DataObjectBean entity)
    {
        return canDelete(entity.getDataObject());
    }

    public abstract boolean canCreate(DataObject parentDataObject);
    public abstract boolean canModify(DataObject dataObject);
    public abstract boolean canDelete(DataObject dataObject);
}
