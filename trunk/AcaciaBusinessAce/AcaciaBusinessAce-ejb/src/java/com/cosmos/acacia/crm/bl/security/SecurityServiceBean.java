/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.bl.security;

import com.cosmos.acacia.crm.data.security.Privilege;
import com.cosmos.acacia.crm.data.security.PrivilegeCategory;
import com.cosmos.acacia.crm.data.security.PrivilegeRole;
import com.cosmos.acacia.crm.data.security.SecurityRole;
import com.cosmos.acacia.entity.AbstractEntityService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Miro
 */
@Stateless
public class SecurityServiceBean extends AbstractEntityService implements SecurityServiceRemote, SecurityServiceLocal {

    public List<SecurityRole> getSecurityRoles() {
        return new ArrayList<SecurityRole>();
    }

    public List<PrivilegeCategory> getPrivilegeCategories() {
        return new ArrayList<PrivilegeCategory>();
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
            return (List<E>) getPrivilegeCategories();
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
}
