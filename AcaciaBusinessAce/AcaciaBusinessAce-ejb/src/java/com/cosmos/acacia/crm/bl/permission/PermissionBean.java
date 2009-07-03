/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.permission;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.bl.impl.DataObjectTypeLocal;
import com.cosmos.acacia.crm.bl.users.RightsManagerLocal;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.data.permission.DataObjectPermission;
import com.cosmos.acacia.crm.data.permission.DataObjectPermissionPK;
import com.cosmos.acacia.crm.data.permission.DataObjectTypePermission;
import com.cosmos.acacia.crm.data.permission.DataObjectTypePermissionPK;
import com.cosmos.acacia.crm.enums.SpecialPermission;
import com.cosmos.acacia.crm.enums.UserRightType;
import java.math.BigInteger;
import java.security.AccessControlException;
import java.util.Set;
import java.util.TreeSet;
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
public class PermissionBean implements PermissionLocal {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private AcaciaSessionLocal session;

    @EJB
    private RightsManagerLocal rightsManager;

    @EJB
    private DataObjectTypeLocal dotService;

    @Override
    public DataObjectPermission newDataObjectPermission() {
        accessCheck();
        DataObjectPermissionPK pk = new DataObjectPermissionPK();
        pk.setOrganizationId(session.getOrganization().getId());
        return new DataObjectPermission(pk);
    }

    @Override
    public DataObjectPermission saveDataObjectPermission(DataObjectPermission dataObjectPermission) {
        accessCheck();
        if(dataObjectPermission == null) {
            throw new NullPointerException("dataObjectPermission can not be null.");
        }

        DataObjectPermissionPK pk;
        if((pk = dataObjectPermission.getDataObjectPermissionPK()) == null) {
            throw new NullPointerException("dataObjectPermissionPK can not be null.");
        }

        BigInteger organizationId = session.getOrganization().getId();
        BigInteger id;
        if((id = pk.getOrganizationId()) == null || !id.equals(organizationId)) {
            pk.setOrganizationId(organizationId);
        }

        em.persist(dataObjectPermission);

        return dataObjectPermission;
    }

    @Override
    public void deleteDataObjectPermission(DataObjectPermission dataObjectPermission) {
        accessCheck();
        em.remove(dataObjectPermission);
    }

    @Override
    public Set<DataObjectPermission> getDataObjectPermissions() {
        Query q = em.createNamedQuery(DataObjectPermission.NQ_FIND_ALL);
        q.setParameter("organizationId", session.getOrganization().getId());
        return new TreeSet<DataObjectPermission>(q.getResultList());
    }

    @Override
    public Set<DataObjectPermission> getDataObjectPermissions(DataObject dataObject) {
        Query q = em.createNamedQuery(DataObjectPermission.NQ_FIND_BY_DO);
        q.setParameter("organizationId", session.getOrganization().getId());
        q.setParameter("dataObjectId", dataObject.getDataObjectId());
        return new TreeSet<DataObjectPermission>(q.getResultList());
    }

    @Override
    public Set<DataObjectPermission> getDataObjectPermissions(DataObject dataObject, UserRightType userRightType) {
        Query q = em.createNamedQuery(DataObjectPermission.NQ_FIND_BY_DO_AND_URT);
        q.setParameter("organizationId", session.getOrganization().getId());
        q.setParameter("dataObjectId", dataObject.getDataObjectId());
        q.setParameter("userRightTypeId", userRightType.getDbResource().getResourceId());
        return new TreeSet<DataObjectPermission>(q.getResultList());
    }

    @Override
    public DataObjectTypePermission newDataObjectTypePermission() {
        accessCheck();
        DataObjectTypePermissionPK pk = new DataObjectTypePermissionPK();
        pk.setOrganizationId(session.getOrganization().getId());
        return new DataObjectTypePermission(pk);
    }

    @Override
    public DataObjectTypePermission saveDataObjectTypePermission(DataObjectTypePermission dataObjectTypePermission) {
        accessCheck();
        if(dataObjectTypePermission == null) {
            throw new NullPointerException("dataObjectTypePermission can not be null.");
        }

        DataObjectTypePermissionPK pk;
        if((pk = dataObjectTypePermission.getDataObjectTypePermissionPK()) == null) {
            throw new NullPointerException("dataObjectTypePermissionPK can not be null.");
        }

        BigInteger organizationId = session.getOrganization().getId();
        BigInteger id;
        if((id = pk.getOrganizationId()) == null || !id.equals(organizationId)) {
            pk.setOrganizationId(organizationId);
        }

        em.persist(dataObjectTypePermission);

        return dataObjectTypePermission;
    }

    @Override
    public void deleteDataObjectTypePermission(DataObjectTypePermission dataObjectTypePermission) {
        accessCheck();
        em.remove(dataObjectTypePermission);
    }

    @Override
    public Set<DataObjectTypePermission> getDataObjectTypePermissions() {
        Query q = em.createNamedQuery(DataObjectTypePermission.NQ_FIND_ALL);
        q.setParameter("organizationId", session.getOrganization().getId());
        return new TreeSet<DataObjectTypePermission>(q.getResultList());
    }

    @Override
    public Set<DataObjectTypePermission> getDataObjectTypePermissions(DataObjectType dataObjectType) {
        Query q = em.createNamedQuery(DataObjectTypePermission.NQ_FIND_BY_DOT);
        q.setParameter("organizationId", session.getOrganization().getId());
        q.setParameter("dataObjectTypeId", dataObjectType.getDataObjectTypeId());
        return new TreeSet<DataObjectTypePermission>(q.getResultList());
    }

    @Override
    public Set<DataObjectTypePermission> getDataObjectTypePermissions(DataObjectType dataObjectType, UserRightType userRightType) {
        Query q = em.createNamedQuery(DataObjectTypePermission.NQ_FIND_BY_DOT_AND_URT);
        q.setParameter("organizationId", session.getOrganization().getId());
        q.setParameter("dataObjectTypeId", dataObjectType.getDataObjectTypeId());
        q.setParameter("userRightTypeId", userRightType.getDbResource().getResourceId());
        return new TreeSet<DataObjectTypePermission>(q.getResultList());
    }

    @Override
    public Set<DataObjectTypePermission> getDataObjectTypePermissions(
            Class<? extends DataObjectBean> entityClass) {
        return getDataObjectTypePermissions(dotService.getDataObjectType(entityClass.getName()));
    }

    @Override
    public Set<DataObjectTypePermission> getDataObjectTypePermissions(
            Class<? extends DataObjectBean> entityClass, UserRightType userRightType) {
        return getDataObjectTypePermissions(dotService.getDataObjectType(entityClass.getName()), userRightType);
    }

    private void accessCheck() {
        if(!rightsManager.isAllowed(SpecialPermission.SystemAdministrator)) {
            throw new AccessControlException("Unauthorized access. The user MUST have SystemAdministrator permission.");
        }
    }
}
