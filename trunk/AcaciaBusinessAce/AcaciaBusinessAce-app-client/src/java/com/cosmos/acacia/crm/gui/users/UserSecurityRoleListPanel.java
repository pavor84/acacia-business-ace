/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.users;

import com.cosmos.acacia.crm.data.users.User;
import com.cosmos.acacia.crm.data.users.UserOrganization;
import com.cosmos.acacia.crm.data.users.UserSecurityRole;
import com.cosmos.acacia.gui.entity.DetailEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class UserSecurityRoleListPanel extends DetailEntityListPanel<UserOrganization, UserSecurityRole> {

    public UserSecurityRoleListPanel(EntityPanel<UserOrganization> mainEntityPanel) {
        super(mainEntityPanel, UserSecurityRole.class);
    }

    public UserSecurityRoleListPanel(UserOrganization userOrganization) {
        super(userOrganization, UserSecurityRole.class);
    }

    @Override
    protected EntityPanel getEntityPanel(UserSecurityRole entity) {
        return new UserSecurityRolePanel(this, entity);
    }

    public UserOrganization getUserOrganization() {
        return getMainEntity();
    }

    public void setUserOrganization(UserOrganization userOrganization) {
        setMainEntity(userOrganization);
        refresh();
    }
}
