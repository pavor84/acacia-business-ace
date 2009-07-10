/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.bl.users;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.users.User;
import com.cosmos.acacia.crm.data.users.UserOrganization;
import com.cosmos.beansbinding.EntityProperties;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Miro
 */
@Stateless
public class UsersServiceBean implements UsersServiceRemote, UsersServiceLocal {

    public static final String ORGANIZATION_KEY = "organization";

    @PersistenceContext
    private EntityManager em;
    //
    @EJB
    private EntityStoreManagerLocal esm;
    //
    @EJB
    private AcaciaSessionLocal session;

    public List<User> getUsers(Organization organization) {
        if(organization == null || organization.getId() == null) {
            Query q = em.createNamedQuery("User.findAll");
            return new ArrayList<User>(q.getResultList());
        }

        Query q = em.createNamedQuery("UserOrganization.findByOrganization");
        q.setParameter(ORGANIZATION_KEY, organization);
        List<UserOrganization> uoList = q.getResultList();
        List<User> users = new ArrayList<User>(uoList.size());
        for (UserOrganization userOrganization : uoList) {
            User user = userOrganization.getUser();
            if (userOrganization.getBranch() != null) {
                user.setBranchName(userOrganization.getBranch().getAddressName());
            }

            user.setActive(userOrganization.isUserActive());
            users.add(user);
        }

        return users;
    }

    @Override
    public <E> E newEntity(Class<E> entityClass) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E, I> I newItem(E entity, Class<I> itemClass) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E> List<E> getEntities(Class<E> entityClass, Object... extraParameters) {
        if(entityClass == User.class) {
            return (List<E>)getUsers(session.getOrganization());
        }

        if(true) {
            return Collections.emptyList();
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E, I> List<I> getEntityItems(E entity, Class<I> itemClass, Object... extraParameters) {
        if(true) {
            return Collections.emptyList();
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E> E save(E entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E> E confirm(E entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E> E cancel(E entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E> void delete(E entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E> EntityProperties getEntityProperties(Class<E> entityClass) {
        return esm.getEntityProperties(entityClass);
    }

    @Override
    public List getResources(Class<? extends Enum> enumClass, Class<? extends Enum>... enumCategoryClasses) {
        return esm.getResources(em, enumClass, enumClass);
    }
}
