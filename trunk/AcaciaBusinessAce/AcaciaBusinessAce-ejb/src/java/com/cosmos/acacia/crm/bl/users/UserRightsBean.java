package com.cosmos.acacia.crm.bl.users;

import java.math.BigInteger;
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
import com.cosmos.acacia.crm.data.User;
import com.cosmos.acacia.crm.data.UserGroup;
import com.cosmos.acacia.crm.data.UserOrganization;
import com.cosmos.acacia.crm.data.UserOrganizationPK;
import com.cosmos.acacia.crm.data.UserRight;
import com.cosmos.acacia.crm.enums.SpecialPermission;
import com.cosmos.beansbinding.EntityProperties;

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
    private ClassifiersLocal classifiersSession;

    @EJB
    private DataObjectTypeLocal dataObjectTypesSession;

    @Override
    public void assignGroupToPosition(UserGroup group, PositionType position) {
        position.setUserGroup(group);
        esm.persist(em, position);

    }

    @Override
    public void assignRightsToGroup(Set<UserRight> rights, UserGroup group) {
        Set<UserRight> currentRights = getUserRights(group);
        assignRights(currentRights, rights);
    }

    @Override
    public void assignRightsToUser(Set<UserRight> rights, User user) {
        Set<UserRight> currentRights = getUserRights(user);
        assignRights(currentRights, rights);
    }

    private void assignRights(Set<UserRight> currentRights, Set<UserRight> rights) {
        // Logic for optimal queries to DB. First remove all existing rights
        // which do not match any of the new set, and then persist only those
        // of the new set, which are not already present in the DB.

        Set<UserRight> currentRightsMirror = new HashSet<UserRight>(currentRights);
        for (UserRight right : currentRightsMirror) {
            if (!rights.contains(right)) {
                esm.remove(em, right);
                currentRights.remove(right);
            }
        }

        for (UserRight right : rights) {
            if (!currentRights.contains(right))
                esm.persist(em, right);
        }
    }

    @Override
    public void assignSpecialPermissionsToGroup(
            Set<UserRight> permissions, UserGroup group)
    {
        Set<UserRight> currentRights = getSpecialPermissions(group);
        assignRights(currentRights, permissions);
    }

    @Override
    public void assignSpecialPermissionsToUser(
            Set<UserRight> permissions, User user)
    {
        Set<UserRight> currentRights = getSpecialPermissions(user);
        assignRights(currentRights, permissions);
    }

    @Override
    public void assingGroupToUser(UserGroup group, User user) {
        UserOrganization uo = em.find(UserOrganization.class, new UserOrganizationPK(
                user.getId(), session.getOrganization().getId()));

        uo.setUserGroup(group);
        esm.persist(em, uo);
    }

    @Override
    public Set<UserRight> getSpecialPermissions(User user) {
        Query q = em.createNamedQuery("UserRight.findSpecialByUser");
        q.setParameter("user", user);

        return getUserRightsWithInfo(q.getResultList());
    }

    @Override
    public Set<UserRight> getSpecialPermissions(UserGroup userGroup) {
        Query q = em.createNamedQuery("UserRight.findSpecialByUserGroup");
        q.setParameter("userGroup", userGroup);
        
        return getUserRightsWithInfo(q.getResultList());
    }

    @Override
    public Set<UserRight> getUserRights(User user) {
        Query q = em.createNamedQuery("UserRight.findByUser");
        q.setParameter("user", user);

        return getUserRightsWithInfo(q.getResultList());
    }

    @Override
    public Set<UserRight> getUserRights(UserGroup userGroup) {
        Query q = em.createNamedQuery("UserRight.findByUserGroup");
        q.setParameter("userGroup", userGroup);
        
        return getUserRightsWithInfo(q.getResultList());
    }

    @SuppressWarnings("unchecked")
    private Set<UserRight> getUserRightsWithInfo(List list) {
        Set<UserRight> rights = new HashSet<UserRight>(list);
        for (UserRight right : rights) {
            DataObjectBean dob = getDataObjectBean(right.getDataObject());
            if (dob != null)
                right.setObjectInfo(dob.getInfo());
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
    public UserGroup newUserGroup(BigInteger parentId) {
        UserGroup group = new UserGroup();
        group.setParentId(parentId);
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
    public List<UserGroup> getUserGroups(BigInteger parentId) {
         Query q;
        if(parentId != null)
        {
            q = em.createNamedQuery("UserGroup.findByParentDataObjectAndDeleted");
            q.setParameter("parentDataObjectId", parentId);
        }
        else
        {
            q = em.createNamedQuery("UserGroup.findByParentDataObjectIsNullAndDeleted");
        }
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
    public List<DataObjectType> getDataObjectTypes() {
        return classifiersSession.getDataObjectTypes();
    }

    @Override
    public List<DataObjectBean> getDataObjectBeans(DataObjectType dataObjectType) {
        return dataObjectTypesSession.getDataObjectBeans(dataObjectType);
    }

    @Override
    public DataObjectBean getDataObjectBean(DataObject dataObject) {
        return classifiersSession.getDataObjectBean(dataObject);
    }

    @Override
    public void removeRights(Set<UserRight> rights) {
        for (UserRight right : rights) {
            esm.remove(em, right);
        }
    }
    
    @Override
    public List<DbResource> getSpecialPermissions() {
        return SpecialPermission.getDbResources();
    }    
}
