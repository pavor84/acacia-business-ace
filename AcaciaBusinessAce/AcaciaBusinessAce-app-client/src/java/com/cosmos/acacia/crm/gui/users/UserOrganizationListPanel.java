/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.users;

import com.cosmos.acacia.crm.data.users.User;
import com.cosmos.acacia.crm.data.users.UserOrganization;
import com.cosmos.acacia.gui.entity.DetailEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;
import java.util.List;

/**
 *
 * @author Miro
 */
public class UserOrganizationListPanel extends DetailEntityListPanel<User, UserOrganization> {

    public UserOrganizationListPanel() {
        this(getAcaciaSession().getUser());
    }

    public UserOrganizationListPanel(User user) {
        super(user, UserOrganization.class);
    }

    public UserOrganizationListPanel(EntityPanel<User> mainEntityPanel) {
        super(mainEntityPanel, UserOrganization.class);
    }

    @Override
    protected EntityPanel<UserOrganization> getEntityPanel(UserOrganization entity) {
        return new UserOrganizationPanel(this, entity);
    }

    public User getUser() {
        return getMainEntity();
    }

    public void setUser(User user) {
        setMainEntity(user);
        refresh();
    }
}
