/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.security;

import com.cosmos.acacia.crm.data.security.SecurityRole;
import com.cosmos.acacia.crm.data.users.BusinessUnit;
import com.cosmos.acacia.crm.data.users.UserOrganization;
import com.cosmos.acacia.crm.data.users.UserSecurityRole;
import com.cosmos.acacia.gui.entity.EntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;
import java.util.List;

/**
 *
 * @author Miro
 */
public class SecurityRoleListPanel extends EntityListPanel<SecurityRole> {

    public SecurityRoleListPanel() {
        super(SecurityRole.class, null);
    }

    public SecurityRoleListPanel(BusinessUnit businessUnit) {
        super(SecurityRole.class, null, businessUnit);
    }

    public SecurityRoleListPanel(UserOrganization userOrganization, UserSecurityRole userSecurityRole) {
        super(SecurityRole.class, null, userOrganization, userSecurityRole);
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
        UserOrganization userOrganization;
        Class entityClass = getEntityClass();
        if((businessUnit = getBusinessUnit()) != null) {
            return getEntityService().getEntities(entityClass, businessUnit);
        } else if((userOrganization = getUserOrganization()) != null) {
            return getEntityService().getEntities(entityClass, userOrganization, getUserSecurityRole());
        } else {
            return getEntityService().getEntities(entityClass);
        }
    }

    public BusinessUnit getBusinessUnit() {
        if(parameters != null && parameters.length > 0 && parameters[0] instanceof BusinessUnit) {
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

    public UserOrganization getUserOrganization() {
        if(parameters != null && parameters.length > 0 && parameters[0] instanceof UserOrganization) {
            return (UserOrganization) parameters[0];
        }

        return null;
    }

    public void setUserOrganization(UserOrganization userOrganization) {
        if(parameters == null) {
            parameters = new Object[2];
        }
        parameters[0] = userOrganization;
        refresh();
    }

    public UserSecurityRole getUserSecurityRole() {
        if(parameters != null && parameters.length > 1 && parameters[1] instanceof UserSecurityRole) {
            return (UserSecurityRole) parameters[1];
        }

        return null;
    }

    public void setUserSecurityRole(UserSecurityRole userSecurityRole) {
        if(parameters == null) {
            parameters = new Object[2];
        }
        parameters[1] = userSecurityRole;
        refresh();
    }
}
