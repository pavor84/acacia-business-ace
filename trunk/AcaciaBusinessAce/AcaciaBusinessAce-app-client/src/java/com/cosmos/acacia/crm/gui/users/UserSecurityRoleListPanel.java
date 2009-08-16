/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.users;

import com.cosmos.acacia.crm.data.users.User;
import com.cosmos.acacia.crm.data.users.UserSecurityRole;
import com.cosmos.acacia.gui.entity.DetailEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class UserSecurityRoleListPanel extends DetailEntityListPanel<User, UserSecurityRole> {

    public UserSecurityRoleListPanel(EntityPanel<User> mainEntityPanel, Class<UserSecurityRole> itemEntityClass) {
        super(mainEntityPanel, itemEntityClass);
    }

    @Override
    protected EntityPanel getEntityPanel(UserSecurityRole entity) {
        return new UserSecurityRolePanel(this, entity);
    }
}
