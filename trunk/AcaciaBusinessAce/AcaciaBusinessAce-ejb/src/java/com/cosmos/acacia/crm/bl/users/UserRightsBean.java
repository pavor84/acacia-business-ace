package com.cosmos.acacia.crm.bl.users;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.bl.impl.ClassifiersLocal;
import com.cosmos.acacia.crm.bl.impl.DataObjectTypeLocal;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.PositionType;
import com.cosmos.acacia.crm.data.Right;
import com.cosmos.acacia.crm.data.User;
import com.cosmos.acacia.crm.data.UserGroup;
import com.cosmos.acacia.crm.data.UserGroupRight;
import com.cosmos.acacia.crm.data.UserOrganization;
import com.cosmos.acacia.crm.data.UserOrganizationPK;
import com.cosmos.acacia.crm.data.UserRight;
import com.cosmos.acacia.crm.enums.SpecialPermission;
import com.cosmos.beansbinding.EntityProperties;
import java.util.Iterator;

@Stateless
public class UserRightsBean implements UserRightsRemote, UserRightsLocal {

    protected static Logger log = Logger.getLogger(UserRightsBean.class);
    @PersistenceContext
    private EntityManager em;
    @EJB
    private EntityStoreManagerLocal esm;
    @EJB
    private AcaciaSessionLocal session;
    @EJB
    private ClassifiersLocal classifiersManager;
    @EJB
    private DataObjectTypeLocal dataObjectTypesManager;

    @Override
    public void assignGroupToPosition(UserGroup group, PositionType position) {
        position.setUserGroup(group);
        esm.persist(em, position);

    }

    @Override
    public void assignRightsToGroup(Set<Right> rights, UserGroup group) {
        Set<Right> currentRights = getRights(group);
        assignRights(currentRights, rights);
    }

    @Override
    public void assignRightsToUser(Set<Right> rights, User user) {
        Set<Right> currentRights = getRights(user);
        assignRights(currentRights, rights);
    }

    private void assignRights(Set<Right> currentRights, Set<Right> rights) {
        // Logic for optimal queries to DB. First remove all existing rights
        // which do not match any of the new set, and then persist only those
        // of the new set, which are not already present in the DB.

        Iterator<Right> iterator = currentRights.iterator();
        while(iterator.hasNext()) {
            Right right = iterator.next();
            if (!rights.contains(right)) {
                esm.remove(em, right);
                iterator.remove();
            }
        }

        for (Right right : rights) {
            if (!currentRights.contains(right)) {
                esm.persist(em, right);
                currentRights.add(right);
            }
        }
    }

    @Override
    public void assignSpecialPermissionsToGroup(
            Set<Right> permissions, UserGroup group) {
        Set<Right> currentRights = getSpecialPermissions(group);
        assignRights(currentRights, permissions);
    }

    @Override
    public void assignSpecialPermissionsToUser(
            Set<Right> permissions, User user) {
        Set<Right> currentRights = getSpecialPermissions(user);
        assignRights(currentRights, permissions);
    }

    @Override
    public void assignGroupToUser(UserGroup group, User user) {
        UserOrganization uo = em.find(UserOrganization.class, new UserOrganizationPK(
                user.getId(), session.getOrganization().getId()));

        uo.setUserGroup(group);
        esm.persist(em, uo);
    }

    @Override
    public Set<Right> getSpecialPermissions(User user) {
        Query q = em.createNamedQuery(UserRight.NQ_FIND_BY_USER_AND_PERMISSION_NOT_NULL);
        q.setParameter("organizationId", session.getOrganization().getId());
        q.setParameter("user", user);

        return getRightsWithInfo(q.getResultList());
    }

    @Override
    public Set<Right> getSpecialPermissions(UserGroup userGroup) {
        Query q = em.createNamedQuery(UserGroupRight.NQ_FIND_BY_USER_GROUP_AND_PERMISSION_NOT_NULL);
        q.setParameter("organizationId", session.getOrganization().getId());
        q.setParameter("userGroup", userGroup);

        return getRightsWithInfo(q.getResultList());
    }

    @Override
    public Right newRight(User user) {
        UserRight right = new UserRight();
        right.setUser(user);
        return initRight(right);
    }

    @Override
    public Right newRight(UserGroup userGroup) {
        UserGroupRight right = new UserGroupRight();
        right.setUserGroup(userGroup);
        return initRight(right);
    }

    private Right initRight(Right right) {
        right.setOrganizationId(session.getOrganization().getId());
        return right;
    }

    @Override
    public Set<Right> getRights(User user) {
        Query q = em.createNamedQuery(UserRight.NQ_FIND_BY_USER_AND_PERMISSION_NULL);
        q.setParameter("organizationId", session.getOrganization().getId());
        q.setParameter("user", user);

        return getRightsWithInfo(q.getResultList());
    }

    @Override
    public Set<Right> getRights(UserGroup userGroup) {
        Query q = em.createNamedQuery(UserGroupRight.NQ_FIND_BY_USER_GROUP_AND_PERMISSION_NULL);
        q.setParameter("organizationId", session.getOrganization().getId());
        q.setParameter("userGroup", userGroup);

        return getRightsWithInfo(q.getResultList());
    }

    @SuppressWarnings("unchecked")
    private Set<Right> getRightsWithInfo(List list) {
        Set<Right> rights = new HashSet<Right>(list);
        for (Right right : rights) {
            DataObjectBean dob = getDataObjectBean(right.getDataObject());
            if (dob != null) {
                right.setObjectInfo(dob.getInfo());
            }
        }
        return rights;
    }

    @Override
    public EntityProperties getUserGroupEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(UserGroup.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    @Override
    public UserGroup newUserGroup() {
        UserGroup group = new UserGroup();
        group.setOrganizationId(session.getOrganization().getId());
        return group;
    }

    @Override
    public int deleteUserGroup(UserGroup group) {
        return esm.remove(em, group);
    }

    @Override
    public UserGroup saveUserGroup(UserGroup group) {
        esm.persist(em, group);
        return group;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserGroup> getUserGroups() {
        Query q = em.createNamedQuery(UserGroup.NQ_FIND_ALL);
        q.setParameter("organizationId", session.getOrganization().getId());
        q.setParameter("deleted", false);

        return new ArrayList<UserGroup>(q.getResultList());
    }

    @Override
    public EntityProperties getUserRightEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(UserRight.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    @Override
    public EntityProperties getUserGroupRightEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(UserGroupRight.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    @Override
    public List<DataObjectType> getDataObjectTypes() {
        return classifiersManager.getDataObjectTypes();
    }

    @Override
    public List<DataObjectBean> getDataObjectBeans(DataObjectType dataObjectType) {
        return dataObjectTypesManager.getDataObjectBeans(dataObjectType);
    }

    @Override
    public DataObjectBean getDataObjectBean(DataObject dataObject) {
        return classifiersManager.getDataObjectBean(dataObject);
    }

    @Override
    public void removeRights(Set<Right> rights) {
        for (Right right : rights) {
            esm.remove(em, right);
        }
    }

    @Override
    public List<DbResource> getSpecialPermissions() {
        return SpecialPermission.getDbResources();
    }
}
