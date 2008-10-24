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
import java.util.Map;
import java.util.TreeMap;
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

    /*
    private static CacheFactory cacheFactory;
    private static Cache cache;
    private static Fqn fqnDOTName =
        Fqn.fromString("/" + DataObjectType.class.getName().replace('.', '/') + "/name");
    private static Fqn fqnDOTId =
        Fqn.fromString("/" + DataObjectType.class.getName().replace('.', '/') + "/id");
    */
    private static Map cacheByName = new TreeMap();
    private static Map cacheById = new TreeMap();

    @PersistenceContext
    private EntityManager em;

    @EJB
    private AcaciaSessionLocal session;
    

    public DataObjectType getDataObjectType(int id) {
        //Node idCache = getCacheById();
        Map idCache = getCacheById();
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
                Map nameCache = getCacheByName();
                idCache.put(id, dot);
                nameCache.put(dot.getDataObjectType(), dot);
            }
        }

        return dot;
    }
    
    public DataObjectType getDataObjectType(String name) {
        Map nameCache = getCacheByName();
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
                Map idCache = getCacheById();
                nameCache.put(name, dot);
                idCache.put(dot.getDataObjectTypeId(), dot);
            }
        }

        return dot;
    }

    private Map getCacheByName()
    {
        return cacheByName;
    }

    private Map getCacheById()
    {
        return cacheById;
    }

    /*
    private Node getCacheByName()
    {
        return getNode(fqnDOTName);
    }

    private Node getCacheById()
    {
        return getNode(fqnDOTId);
    }
    */

    private void removeFromCache(DataObjectType dataObjectType)
    {
        Map nameCache = getCacheByName();
        Map idCache = getCacheById();
        nameCache.remove(dataObjectType.getDataObjectType());
        idCache.remove(dataObjectType.getDataObjectTypeId());
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

    public List<DataObjectBean> getDataObjectBeans(DataObjectType dataObjectType) {
        if (dataObjectType == null)
            return Collections.EMPTY_LIST;
        
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

    /*public CacheFactory getCacheFactory()
    {
        if(cacheFactory == null)
        {
            cacheFactory = new DefaultCacheFactory();
        }

        return cacheFactory;
    }

    public Cache getCache()
    {
        if(cache == null)
        {
            cache = getCacheFactory().createCache();
        }

        return cache;
    }

    public Node getNode(Fqn regionName)
    {
        Cache localCache = getCache();
        Node node = localCache.getNode(regionName);
        if(node == null)
        {
            Node rootNode = localCache.getRoot();
            node = rootNode.addChild(regionName);
        }

        return node;
    }*/
}
