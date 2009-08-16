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
import com.cosmos.acacia.crm.data.users.BusinessUnit;
import com.cosmos.acacia.crm.data.users.User;
import com.cosmos.acacia.crm.data.users.UserSecurityRole;
import com.cosmos.acacia.entity.AbstractEntityService;
import com.cosmos.acacia.entity.Operation;
import com.cosmos.acacia.security.AccessRight;
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
    public static final String PK_PRIVILEGE = "privilege";
    public static final String PK_PRIVILEGE_TYPE = "privilegeType";
    public static final String PK_BUSINESS_UNIT = "businessUnit";
    public static final String PK_USER = "user";
    public static final String PK_SECURITY_ROLE = "securityRole";
    public static final String PK_EXPIRES = "expires";
    //
    public static final int ACCESS_RIGHT_COUNT = AccessRight.values().length;

    public List<SecurityRole> getSecurityRoles() {
        Query q = em.createNamedQuery(SecurityRole.NQ_FIND_ALL);
        q.setParameter(PK_ORGANIZATION, session.getOrganization());

        return new ArrayList<SecurityRole>(q.getResultList());
    }

    public List<SecurityRole> getSecurityRoles(BusinessUnit businessUnit) {
        Query q = em.createNamedQuery(SecurityRole.NQ_FIND_BY_BUSINESS_UNIT);
        q.setParameter(PK_BUSINESS_UNIT, businessUnit);

        return new ArrayList<SecurityRole>(q.getResultList());
    }

    public List<SecurityRole> getSecurityRoles(User user, UserSecurityRole userSecurityRole) {
        Query q = em.createNamedQuery(SecurityRole.NQ_FIND_BY_USER);
        q.setParameter(PK_ORGANIZATION, session.getOrganization());
        q.setParameter(PK_USER, user);
        List<SecurityRole> securityRoles = new ArrayList<SecurityRole>(q.getResultList());
        SecurityRole securityRole;
        if(userSecurityRole != null && (securityRole = userSecurityRole.getSecurityRole()) != null
                && !securityRoles.contains(securityRole)) {
            securityRoles.add(0, securityRole);
        }

        return securityRoles;
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

    public List<Privilege> getPrivileges(SecurityRole securityRole, Class<? extends Privilege> itemClass) {
        Query q = em.createNamedQuery(Privilege.NQ_FIND_ALL);
        q.setParameter(PK_SECURITY_ROLE, securityRole);
        return new ArrayList<Privilege>(q.getResultList());
    }

    public List<PrivilegeRole> getPrivilegeRoles(Privilege privilege) {
        Query q = em.createNamedQuery(PrivilegeRole.NQ_FIND_ALL);
        q.setParameter(PK_PRIVILEGE, privilege);
        return new ArrayList<PrivilegeRole>(q.getResultList());
    }

    public Long countPrivilegeRoles(Privilege privilege) {
        Query q = em.createNamedQuery(PrivilegeRole.NQ_COUNT_ROLES);
        q.setParameter(PK_PRIVILEGE, privilege);
        return (Long)q.getSingleResult();
    }

    @Override
    public <E> List<E> getEntities(Class<E> entityClass, Object... extraParameters) {
        if(!canDo(Operation.Read, null, entityClass, extraParameters)) {
            return Collections.emptyList();
        }

        if(SecurityRole.class == entityClass) {
            Object parameter;
            if(extraParameters.length == 0) {
                return (List<E>) getSecurityRoles();
            } else if((parameter = extraParameters[0]) instanceof BusinessUnit) {
                return (List<E>) getSecurityRoles((BusinessUnit) parameter);
            } else if(parameter instanceof User) {
                return (List<E>) getSecurityRoles((User) parameter, (UserSecurityRole) extraParameters[1]);
            }
        } else if(PrivilegeCategory.class == entityClass) {
            if(extraParameters.length == 0) {
                return (List<E>) getPrivilegeCategories(null);
            } else {
                return (List<E>) getPrivilegeCategories((PrivilegeType) extraParameters[0]);
            }
        }

        return super.getEntities(entityClass, extraParameters);
    }

    @Override
    public <E, I> List<I> getEntityItems(E entity, Class<I> itemClass, Object... extraParameters) {
        if(!canDo(Operation.Read, entity, itemClass, extraParameters)) {
            return Collections.emptyList();
        }

        if(Privilege.class.isAssignableFrom(itemClass)) {
            return (List<I>) getPrivileges((SecurityRole) entity, (Class<? extends Privilege>)itemClass);
        } else if(PrivilegeRole.class == itemClass) {
            return (List<I>) getPrivilegeRoles((Privilege) entity);
        }

        return super.getEntityItems(entity, itemClass, extraParameters);
    }

    @Override
    protected <E> void initEntity(E entity) {
        if(entity instanceof PrivilegeCategory) {
            PrivilegeCategory category = (PrivilegeCategory) entity;
            category.setOrganization(session.getOrganization());
        } else if(entity instanceof SecurityRole) {
            SecurityRole securityRole = (SecurityRole) entity;
            securityRole.setOrganization(session.getOrganization());
        }
    }

    @Override
    protected <E, I> void initItem(E entity, I item) {
    }

    @Override
    public List<DbResource> getResources(Class<? extends Enum> enumClass, Object... categoryClassifiers) {
        if(AccessRight.class == enumClass) {
            List<DbResource> resources = super.getResources(enumClass);
            PrivilegeRole privilegeRole = (PrivilegeRole)categoryClassifiers[0];
            return getAccessRights(resources, privilegeRole);
        }

        return super.getResources(enumClass, categoryClassifiers);
    }

    private List<DbResource> getAccessRights(List<DbResource> resources, PrivilegeRole privilegeRole) {
        List<PrivilegeRole> privilegeRoles = getPrivilegeRoles(privilegeRole.getPrivilege());

        int size;
        if((size = privilegeRoles.size()) == 0 || (size == 1 && privilegeRoles.contains(privilegeRole))) {
            return resources;
        }

        privilegeRoles.remove(privilegeRole);
        List<DbResource> usedResources = new ArrayList<DbResource>(size);
        for(PrivilegeRole pr : privilegeRoles) {
            usedResources.add(pr.getAccessRight());
        }

        return getUnusedItems(resources, usedResources);
    }

    @Override
    public <E, I> boolean canDo(Operation operation, E entity, Class<I> itemClass, Object... extraParameters) {
        boolean canDo = super.canDo(operation, entity, itemClass, extraParameters);
        if(canDo && Operation.Create.equals(operation) && entity instanceof Privilege && PrivilegeRole.class == itemClass) {
            return countPrivilegeRoles((Privilege) entity) < ACCESS_RIGHT_COUNT;
        }

        return canDo;
    }
}
