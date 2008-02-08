/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.DataObjectType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;

/**
 *
 * @author Miro
 */
@Stateless
public class DataObjectTypeBean implements DataObjectTypeRemote, DataObjectTypeLocal {

    @PersistenceContext
    private EntityManager em;

    private JCS cacheByName;
    private JCS cacheById;

    public DataObjectType getDataObjectType(int id) {
        JCS idCache = getCacheById();
        DataObjectType dot = (DataObjectType)idCache.get(id);
        if(dot == null)
        {
            Query q = em.createNamedQuery("DataObjectType.findByDataObjectTypeId");
            q.setParameter("dataObjectTypeId", id);
            try
            {
                dot = (DataObjectType)q.getSingleResult();
            }
            catch(NoResultException ex)
            {
                //ex.printStackTrace();
            }
            if(dot != null)
            {
                JCS nameCache = getCacheByName();
                try
                {
                    idCache.put(id, dot);
                    nameCache.put(dot.getDataObjectType(), dot);
                }
                catch(CacheException ex)
                {
                    throw new RuntimeException(ex);
                }
            }
        }

        return dot;
    }
    
    public DataObjectType getDataObjectType(String name) {
        JCS nameCache = getCacheByName();
        DataObjectType dot = (DataObjectType)nameCache.get(name);
        if(dot == null)
        {
            Query q = em.createNamedQuery("DataObjectType.findByDataObjectType");
            q.setParameter("dataObjectType", name);
            try
            {
                dot = (DataObjectType)q.getSingleResult();
            }
            catch(NoResultException ex)
            {
                dot = new DataObjectType();
                dot.setDataObjectType(name);
                em.persist(dot);
            }

            if(dot != null)
            {
                JCS idCache = getCacheById();
                try
                {
                    nameCache.put(name, dot);
                    idCache.put(dot.getDataObjectTypeId(), dot);
                }
                catch(CacheException ex)
                {
                    throw new RuntimeException(ex);
                }
            }
        }

        return dot;
    }

    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")
 

    private JCS getCacheByName()
    {
        if(cacheByName == null)
        {
            try
            {
                String regionName = DataObjectType.class.getName() + ".name";
                cacheByName = JCS.getInstance(regionName);
            }
            catch(CacheException ex)
            {
                throw new RuntimeException(ex);
            }
        }

        return cacheByName;
    }

    private JCS getCacheById()
    {
        if(cacheById == null)
        {
            try
            {
                String regionName = DataObjectType.class.getName() + ".id";
                cacheById = JCS.getInstance(regionName);
            }
            catch(CacheException ex)
            {
                throw new RuntimeException(ex);
            }
        }

        return cacheById;
    }

    private void removeFromCache(DataObjectType dataObjectType)
    {
        JCS nameCache = getCacheByName();
        JCS idCache = getCacheById();
        try
        {
            nameCache.remove(dataObjectType.getDataObjectType());
            idCache.remove(dataObjectType.getDataObjectTypeId());
        }
        catch(CacheException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public DataObjectType persist(DataObjectType dataObjectType) {
        if(dataObjectType.getDataObjectTypeId() != null)
            dataObjectType = em.merge(dataObjectType);
        em.persist(dataObjectType);
        return dataObjectType;
    }

    public void remove(DataObjectType dataObjectType) {
        Object mergedObject = em.merge(dataObjectType);
        em.remove(mergedObject);
        removeFromCache(dataObjectType);
    }


}
