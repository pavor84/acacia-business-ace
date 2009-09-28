/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.security;

import com.cosmos.acacia.crm.data.security.Privilege;
import com.cosmos.acacia.crm.data.security.PrivilegeRole;
import com.cosmos.acacia.entity.Operation;
import com.cosmos.acacia.gui.entity.DetailEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class PrivilegeRoleListPanel extends DetailEntityListPanel<Privilege, PrivilegeRole> {

    public PrivilegeRoleListPanel(EntityPanel<Privilege> mainEntityPanel) {
        super(mainEntityPanel, PrivilegeRole.class, null);
    }

    @Override
    protected EntityPanel getEntityPanel(PrivilegeRole entity) {
        return new PrivilegeRolePanel(this, entity);
    }

    @Override
    public boolean canCreate() {
        if(super.canCreate()) {
            return getEntityService().canDo(Operation.Create, getMainEntity(), getEntityClass());
        } else {
            return false;
        }
    }
}
