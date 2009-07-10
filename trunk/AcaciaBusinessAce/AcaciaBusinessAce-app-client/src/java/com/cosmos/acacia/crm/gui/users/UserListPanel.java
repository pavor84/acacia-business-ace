/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.users;

import com.cosmos.acacia.crm.data.users.User;
import com.cosmos.acacia.gui.entity.EntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class UserListPanel extends EntityListPanel<User> {

    public UserListPanel() {
        super(User.class);
    }

    @Override
    protected EntityPanel getEntityPanel(User entity) {
        return new UserPanel(this, entity);
    }
}
