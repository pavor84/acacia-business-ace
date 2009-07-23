/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.security;

import com.cosmos.acacia.crm.data.security.Privilege;
import com.cosmos.acacia.gui.entity.AbstractEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class PrivilegePanel extends EntityPanel<Privilege> {

    public PrivilegePanel(AbstractEntityListPanel entityListPanel, Privilege entity) {
        super(entityListPanel, entity);
    }
}
