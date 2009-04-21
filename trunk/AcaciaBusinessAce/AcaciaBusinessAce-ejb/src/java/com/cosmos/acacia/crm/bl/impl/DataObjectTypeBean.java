/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DataObjectType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Miro
 */
@Stateless
public class DataObjectTypeBean implements DataObjectTypeRemote, DataObjectTypeLocal {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private AcaciaSessionLocal session;

    @Override
    public DataObjectType getDataObjectType(int id) {
        Query q = em.createNamedQuery("DataObjectType.findByDataObjectTypeId");
        q.setParameter("dataObjectTypeId", id);
        try {
            return (DataObjectType) q.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public DataObjectType getDataObjectType(String name) {
        Query q = em.createNamedQuery("DataObjectType.findByDataObjectType");
        q.setParameter("dataObjectType", name);
        try {
            return (DataObjectType) q.getSingleResult();
        } catch (NoResultException ex) {
            DataObjectType dot = new DataObjectType();
            dot.setDataObjectType(name);
            em.persist(dot);
            return dot;
        }
    }

    @Override
    public List<DataObjectBean> getDataObjectBeans(DataObjectType dataObjectType) {
        if (dataObjectType == null) {
            return Collections.EMPTY_LIST;
        }

        String name = new String(dataObjectType.getDataObjectType());
        name = name.replaceAll(DataObjectType.class.getPackage().getName() + "\\.", "");

        try {
            Query q = em.createQuery("select o from " + name + " o" +
                    " where o.dataObject.parentDataObjectId = :parentDataObjectId" +
                    " or o.dataObject.dataObjectId = :parentDataObjectId");
            q.setParameter("parentDataObjectId", session.getOrganization().getId());
            return new ArrayList<DataObjectBean>(q.getResultList());
        } catch (Exception ex) {
            return Collections.EMPTY_LIST;
        }
    }
}
