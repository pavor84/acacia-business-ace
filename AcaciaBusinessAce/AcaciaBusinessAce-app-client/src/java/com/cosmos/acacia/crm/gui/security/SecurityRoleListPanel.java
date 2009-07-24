/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.security;

import com.cosmos.acacia.crm.data.security.SecurityRole;
import com.cosmos.acacia.crm.data.users.BusinessUnit;
import com.cosmos.acacia.gui.entity.EntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;
import java.util.List;

/**
 *
 * @author Miro
 */
public class SecurityRoleListPanel extends EntityListPanel<SecurityRole> {

    public SecurityRoleListPanel() {
        super(SecurityRole.class);
    }

    public SecurityRoleListPanel(BusinessUnit businessUnit) {
        super(SecurityRole.class, businessUnit);
    }

    @Override
    protected EntityPanel getEntityPanel(SecurityRole entity) {
        BusinessUnit businessUnit;
        if(entity.getSecurityRoleId() == null && entity.getBusinessUnit() == null
                && (businessUnit = getBusinessUnit()) != null) {
            entity.setBusinessUnit(businessUnit);
        }

        return new SecurityRolePanel(this, entity);
    }

    @Override
    public List<SecurityRole> getEntities() {
        BusinessUnit businessUnit;
        if((businessUnit = getBusinessUnit()) != null) {
            return getEntityService().getEntities(getEntityClass(), businessUnit);
        } else {
            return getEntityService().getEntities(getEntityClass());
        }
    }

    public BusinessUnit getBusinessUnit() {
        if(parameters != null && parameters.length > 0) {
            return (BusinessUnit) parameters[0];
        }

        return null;
    }

    public void setBusinessUnit(BusinessUnit businessUnit) {
        if(parameters == null) {
            parameters = new Object[1];
        }
        parameters[0] = businessUnit;
        refresh();
    }
}
