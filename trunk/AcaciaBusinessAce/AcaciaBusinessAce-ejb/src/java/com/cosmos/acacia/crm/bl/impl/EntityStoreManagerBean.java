/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DataObjectType;
import java.math.BigInteger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author miro
 */
@Stateless
public class EntityStoreManagerBean implements EntityStoreManagerLocal {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private DataObjectTypeLocal dotLocal;

    public void persist(Object entity) {
        persist(em, entity);
        
    }

    public void persist(EntityManager entityManager, Object entity) {
        boolean mustMerge = false;
        if(entity instanceof DataObjectBean)
        {
            DataObjectBean doBean = (DataObjectBean)entity;
            BigInteger id = doBean.getId();
            DataObject dataObject = doBean.getDataObject();
            if(id == null)
            {
                if(dataObject == null)
                {
                    dataObject = new DataObject();

                    DataObjectTypeLocal dotLocal = getDataObjectTypeLocal();
                    DataObjectType dot = dotLocal.getDataObjectType(entity.getClass().getName());
                    System.out.println("dotLocal: " + dotLocal);
                    System.out.println("dot: " + dot);

                    dataObject.setDataObjectTypeId(dot.getDataObjectTypeId());
                    em.persist(dataObject);
                }

                id = new BigInteger(dataObject.getDataObjectId().toByteArray());
                //id = dataObject.getDataObjectId();
                System.out.println("id: " + id);
                System.out.println("1. doBean: " + doBean);
                doBean.setId(id);
                doBean.setDataObject(dataObject);
                System.out.println("2. doBean: " + doBean);
            }
            else
            {
                mustMerge = true;
                if(dataObject == null)
                    dataObject = em.find(DataObject.class, id);
                int version = dataObject.getDataObjectVersion();
                dataObject.setDataObjectVersion(version + 1);
                em.persist(dataObject);
            }
        }

        if(mustMerge)
            entity = em.merge(entity);
        em.persist(entity);
    }

    public void remove(Object entity) {
        remove(em, entity);
    }

    public void remove(EntityManager entityManager, Object entity) {
        if(entity instanceof DataObjectBean)
        {
            DataObjectBean doBean = (DataObjectBean)entity;
            DataObject dataObject = doBean.getDataObject();
            if(dataObject == null)
                dataObject = em.find(DataObject.class, doBean.getId());
            int version = dataObject.getDataObjectVersion();
            dataObject.setDataObjectVersion(version + 1);
            dataObject.setDeleted(true);
            em.persist(dataObject);
        }

        entity = em.merge(entity);
        em.remove(entity);
    }

    private DataObjectTypeLocal getDataObjectTypeLocal()
    {
        if(dotLocal == null)
        {
            try
            {
                dotLocal = InitialContext.doLookup(DataObjectTypeLocal.class.getName());
            }
            catch(NamingException ex)
            {
                throw new RuntimeException(ex);
            }
        }

        return dotLocal;
    }

    public void prePersist(DataObjectBean entity)
    {
        System.out.println("EntityStoreManager.prePersist: " + entity);
    }

    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")
 
}
