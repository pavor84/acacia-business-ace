/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.security;

import com.cosmos.acacia.crm.data.security.SecurityRole;
import com.cosmos.acacia.crm.data.users.BusinessUnit;
import com.cosmos.acacia.crm.data.users.User;
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
        super(SecurityRole.class);
    }

    public SecurityRoleListPanel(BusinessUnit businessUnit) {
        super(SecurityRole.class, businessUnit);
    }

    public SecurityRoleListPanel(User user, UserSecurityRole userSecurityRole) {
        super(SecurityRole.class, user, userSecurityRole);
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
        User user;
        Class entityClass = getEntityClass();
        if((businessUnit = getBusinessUnit()) != null) {
            return getEntityService().getEntities(entityClass, businessUnit);
        } else if((user = getUser()) != null) {
            return getEntityService().getEntities(entityClass, user, getUserSecurityRole());
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

    public User getUser() {
        if(parameters != null && parameters.length > 0 && parameters[0] instanceof User) {
            return (User) parameters[0];
        }

        return null;
    }

    public void setUser(User user) {
        if(parameters == null) {
            parameters = new Object[2];
        }
        parameters[0] = user;
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
