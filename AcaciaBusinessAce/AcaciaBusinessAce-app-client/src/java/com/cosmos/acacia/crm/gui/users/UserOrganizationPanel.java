/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.users;

import com.cosmos.acacia.crm.data.users.UserOrganization;
import com.cosmos.acacia.gui.entity.AbstractEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class UserOrganizationPanel extends EntityPanel<UserOrganization> {

    public UserOrganizationPanel(AbstractEntityListPanel entityListPanel, UserOrganization entity) {
        super(entityListPanel, entity);
    }
}
