/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.security;

import com.cosmos.acacia.crm.data.security.Privilege;
import com.cosmos.acacia.crm.data.security.SecurityRole;
import com.cosmos.acacia.gui.entity.DetailEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.item.OptionalItem;

/**
 *
 * @author Miro
 */
public class PrivilegeListPanel extends DetailEntityListPanel<SecurityRole, Privilege> {

    public PrivilegeListPanel(EntityPanel<SecurityRole> mainEntityPanel, Class<Privilege> itemEntityClass) {
        super(mainEntityPanel, itemEntityClass);
    }

    @Override
    protected EntityPanel getEntityPanel(Privilege entity) {
        return new PrivilegePanel(this, entity);
    }

    @Override
    protected Privilege newEntity() {
        PrivilegeTypeSelectionDialog selectionDialog = new PrivilegeTypeSelectionDialog();
        if(DialogResponse.SELECT.equals(selectionDialog.showDialog())) {
            OptionalItem item = (OptionalItem) selectionDialog.getSelectedValue();
            return newEntity((Class) item.getItemValue());
        }

        return newEntity(null);
    }
}
