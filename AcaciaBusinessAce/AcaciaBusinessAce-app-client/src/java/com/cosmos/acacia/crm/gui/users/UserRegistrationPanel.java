/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.users;

import com.cosmos.acacia.crm.data.users.UserRegistration;
import com.cosmos.acacia.gui.entity.EntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class UserRegistrationPanel extends EntityPanel<UserRegistration> {

    public UserRegistrationPanel(UserRegistration entity) {
        super(new UserRegistrationEntityListPanel(), entity);
    }

    private static class UserRegistrationEntityListPanel extends EntityListPanel<UserRegistration> {

        public UserRegistrationEntityListPanel() {
            super(UserRegistration.class);
        }
    }
}
