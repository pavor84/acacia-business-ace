/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.users;

import com.cosmos.acacia.crm.data.users.UserSecurityRole;
import com.cosmos.acacia.gui.entity.AbstractEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class UserSecurityRolePanel extends EntityPanel<UserSecurityRole> {

    public UserSecurityRolePanel(AbstractEntityListPanel entityListPanel, UserSecurityRole entity) {
        super(entityListPanel, entity, null);
    }
}
