/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.users;

import com.cosmos.acacia.crm.data.users.User;
import com.cosmos.acacia.gui.entity.AbstractEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class UserPanel extends EntityPanel<User> {

    public UserPanel(AbstractEntityListPanel entityListPanel, User entity) {
        super(entityListPanel, entity);
    }
}
