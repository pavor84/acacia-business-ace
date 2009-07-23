/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.security;

import com.cosmos.acacia.crm.data.security.Privilege;
import com.cosmos.acacia.crm.data.security.PrivilegeRole;
import com.cosmos.acacia.gui.entity.DetailEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class PrivilegeRoleListPanel extends DetailEntityListPanel<Privilege, PrivilegeRole> {

    public PrivilegeRoleListPanel(EntityPanel<Privilege> mainEntityPanel, Class<PrivilegeRole> itemEntityClass) {
        super(mainEntityPanel, itemEntityClass);
    }

    @Override
    protected EntityPanel getEntityPanel(PrivilegeRole entity) {
        return new PrivilegeRolePanel(this, entity);
    }
}
