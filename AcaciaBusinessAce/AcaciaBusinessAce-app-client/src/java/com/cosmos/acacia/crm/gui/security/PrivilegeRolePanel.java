/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.security;

import com.cosmos.acacia.crm.data.security.PrivilegeRole;
import com.cosmos.acacia.gui.entity.AbstractEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;
import com.cosmos.acacia.security.AccessRight;
import java.util.List;

/**
 *
 * @author Miro
 */
public class PrivilegeRolePanel extends EntityPanel<PrivilegeRole> {

    public PrivilegeRolePanel(AbstractEntityListPanel entityListPanel, PrivilegeRole entity) {
        super(entityListPanel, entity, null);
    }

    @Override
    protected List getResources(Class<? extends Enum> enumClass, Object... categoryClassifiers) {
        if(AccessRight.class == enumClass) {
            return super.getResources(enumClass, getEntity());
        }

        return super.getResources(enumClass, categoryClassifiers);
    }
}
