/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.bl.security;

import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.security.Privilege;
import com.cosmos.acacia.crm.data.security.PrivilegeCategory;
import com.cosmos.acacia.crm.data.security.PrivilegeRole;
import com.cosmos.acacia.crm.data.security.SecurityRole;
import com.cosmos.acacia.entity.AbstractEntityService;
import com.cosmos.acacia.security.PrivilegeType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Miro
 */
@Stateless
public class SecurityServiceBean extends AbstractEntityService implements SecurityServiceRemote, SecurityServiceLocal {

    public static final String PK_ORGANIZATION = "organization";
    public static final String PK_PRIVILEGE_TYPE = "privilegeType";

    public List<SecurityRole> getSecurityRoles() {
        return new ArrayList<SecurityRole>();
    }

    public List<PrivilegeCategory> getPrivilegeCategories(PrivilegeType privilegeType) {
        Query q;
        if(privilegeType != null) {
            q = em.createNamedQuery(PrivilegeCategory.NQ_FIND_BY_TYPE);
            q.setParameter(PK_PRIVILEGE_TYPE, privilegeType.getDbResource());
        } else {
            q = em.createNamedQuery(PrivilegeCategory.NQ_FIND_ALL);
        }
        q.setParameter(PK_ORGANIZATION, session.getOrganization());
        return new ArrayList<PrivilegeCategory>(q.getResultList());
    }

    public List<Privilege> getPrivileges(SecurityRole securityRole) {
        return new ArrayList<Privilege>();
    }

    public List<PrivilegeRole> getPrivilegeRoles(Privilege privilege) {
        return new ArrayList<PrivilegeRole>();
    }

    @Override
    public <E> List<E> getEntities(Class<E> entityClass, Object... extraParameters) {
        if(!canRead(entityClass, extraParameters)) {
            return Collections.emptyList();
        }

        if(SecurityRole.class == entityClass) {
            return (List<E>) getSecurityRoles();
        } else if(PrivilegeCategory.class == entityClass) {
            if(extraParameters.length == 0) {
                return (List<E>) getPrivilegeCategories(null);
            } else {
                return (List<E>) getPrivilegeCategories((PrivilegeType) extraParameters[0]);
            }
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E, I> List<I> getEntityItems(E entity, Class<I> itemClass, Object... extraParameters) {
        if(!canRead(entity, itemClass, extraParameters)) {
            return Collections.emptyList();
        }

        if(Privilege.class == itemClass) {
            return (List<I>) getPrivileges((SecurityRole) entity);
        } else if(PrivilegeRole.class == itemClass) {
            return (List<I>) getPrivilegeRoles((Privilege) entity);
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected <E> void initEntity(E entity) {
        if(entity instanceof PrivilegeCategory) {
            PrivilegeCategory category = (PrivilegeCategory) entity;
            category.setOrganization(session.getOrganization());
        }
    }
}
