/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.security;

import com.cosmos.acacia.crm.data.security.SecurityRole;
import com.cosmos.acacia.gui.entity.EntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class SecurityRoleListPanel extends EntityListPanel<SecurityRole> {

    public SecurityRoleListPanel() {
        super(SecurityRole.class);
    }

    @Override
    protected EntityPanel getEntityPanel(SecurityRole entity) {
        return new SecurityRolePanel(this, entity);
    }
}
