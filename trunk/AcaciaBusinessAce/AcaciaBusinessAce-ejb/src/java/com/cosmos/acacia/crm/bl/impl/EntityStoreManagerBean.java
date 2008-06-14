/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.beansbinding.BeansBindingHelper;
import com.cosmos.beansbinding.EntityProperties;

import java.math.BigInteger;
import java.sql.BatchUpdateException;
import java.util.Map;
import java.util.TreeMap;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

/**
 *
 * @author miro
 */
@Stateless
public class EntityStoreManagerBean implements EntityStoreManagerLocal {

    @EJB
    private DataObjectTypeLocal dotLocal;

    private Map<String, EntityProperties> entityPropertiesMap = new TreeMap<String, EntityProperties>();

    public void persist(EntityManager em, Object entity) {
        boolean mustMerge = false;
        if(entity instanceof DataObjectBean)
        {
            DataObjectBean doBean = (DataObjectBean)entity;
            BigInteger id = doBean.getId();
            DataObject dataObject = doBean.getDataObject();
            BigInteger parentId = doBean.getParentId();
            if(id == null)
            {
                System.out.println("Insert Entity");
                if(dataObject == null || dataObject.getDataObjectId() == null)
                {
                    DataObjectTypeLocal dotLocal = getDataObjectTypeLocal();
                    DataObjectType dot = dotLocal.getDataObjectType(entity.getClass().getName());
                    System.out.println("dotLocal: " + dotLocal);
                    System.out.println("dot: " + dot);

                    if(dataObject == null)
                        dataObject = new DataObject();

                    dataObject.setParentDataObjectId(parentId);
                    dataObject.setDataObjectType(dot);
                    dataObject.setDataObjectVersion(1);
                    em.persist(dataObject);
                }
                else
                {
                    BigInteger doParentId = dataObject.getParentDataObjectId();
                    boolean mustUpdateDO = false;
                    if(parentId != null)
                    {
                        if(doParentId == null || !parentId.equals(doParentId))
                        {
                            dataObject.setParentDataObjectId(parentId);
                            mustUpdateDO = true;
                        }
                    }
                    else if(doParentId != null)
                    {
                        dataObject.setParentDataObjectId(parentId);
                        mustUpdateDO = true;
                    }

                    if(mustUpdateDO)
                    {
                        dataObject = em.merge(dataObject);
                        em.persist(dataObject);
                    }
                }

                //id = new BigInteger(dataObject.getDataObjectId().toByteArray());
                id = dataObject.getDataObjectId();
                System.out.println("id: " + id);
                System.out.println("1. doBean: " + doBean);
                doBean.setId(id);
                doBean.setDataObject(dataObject);
                System.out.println("2. doBean: " + doBean);
            }
            else
            {
                System.out.println("Update Entity");
                mustMerge = true;
                if(dataObject == null)
                    dataObject = em.find(DataObject.class, id);
                else
                    dataObject = em.merge(dataObject);
                int version = dataObject.getDataObjectVersion();
                dataObject.setParentDataObjectId(parentId);
                dataObject.setDataObjectVersion(version + 1);
                em.persist(dataObject);
            }
        } else {
            mustMerge = true;
        }

        if(mustMerge)
            entity = em.merge(entity);
        em.persist(entity);
    }

    public int remove(EntityManager em, Object entity) {
        int version = -1;
        if(entity instanceof DataObjectBean)
        {
            DataObjectBean doBean = (DataObjectBean)entity;
            DataObject dataObject = doBean.getDataObject();
            if(dataObject == null)
                dataObject = em.find(DataObject.class, doBean.getId());
            else
                dataObject = em.merge(dataObject);
            version = dataObject.getDataObjectVersion() + 1;
            dataObject.setDataObjectVersion(version);
            dataObject.setDeleted(true);
            em.persist(dataObject);
        }

        entity = em.merge(entity);
        em.remove(entity);
        try {
            em.flush();
        } catch (PersistenceException pe) {
            throw new ValidationException(getRootCauseMessage(pe));
        }
        return version;
    }

    private String getRootCauseMessage(Throwable ex)
    {
        if (ex.getCause() != null) {
            return getRootCauseMessage(ex.getCause());
        }
        
        if (ex instanceof BatchUpdateException) {
            BatchUpdateException bue = (BatchUpdateException) ex;
            String message = bue.getNextException().getMessage();
            message = message.substring(
                    message.lastIndexOf("from table "));
            message = message.replaceAll("\"", "");
            message = message.replaceAll("\\.", "");
            message = message.replaceAll("from table ", "");
            return message;
        }
        return "";
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
 

    public EntityProperties getEntityProperties(Class entityClass)
    {
        String entityClassName = entityClass.getName();
        EntityProperties entityProperties = entityPropertiesMap.get(entityClassName);
        if(entityProperties == null)
        {
            entityProperties = BeansBindingHelper.createEntityProperties(entityClass);
            entityPropertiesMap.put(entityClassName, entityProperties);
        }

        return (EntityProperties)entityProperties.clone();
    }

}
