/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.data.DeliveryCertificate;
import com.cosmos.acacia.crm.data.Invoice;
import com.cosmos.acacia.crm.data.ProductSupplier;
import com.cosmos.acacia.crm.data.PurchaseOrder;
import com.cosmos.acacia.crm.data.ReceiptCertificate;
import com.cosmos.beansbinding.BeansBindingHelper;
import com.cosmos.beansbinding.EntityProperties;
import java.lang.Object;
import java.lang.Object;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;

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

                    dataObject.setDataObjectTypeId(dot.getDataObjectTypeId());
                    dataObject.setDataObjectVersion(1);
                    em.persist(dataObject);
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
                dataObject.setDataObjectVersion(version + 1);
                em.persist(dataObject);
            }
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
        return version;
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

    private static final Class[] noParameterTypes = new Class[] {};
    private static final Object[] noParameters = new Object[] {};
    private static TreeMap<String, List<String>> manySidedProperties;
    static
    {
        manySidedProperties = new TreeMap<String, List<String>>();

        List<String> properties = Arrays.asList("Deliverer");
        manySidedProperties.put(ReceiptCertificate.class.getName(), properties);

        properties = Arrays.asList("Recipient");
        manySidedProperties.put(DeliveryCertificate.class.getName(), properties);

        properties = Arrays.asList("Recipient", "ShippingAgent");
        manySidedProperties.put(Invoice.class.getName(), properties);

        properties = Arrays.asList("Supplier");
        manySidedProperties.put(PurchaseOrder.class.getName(), properties);

        manySidedProperties.put(ProductSupplier.class.getName(), properties);
    }

    public void populate(EntityManager em, DataObjectBean dob)
    {
        List<String> properties = manySidedProperties.get(dob.getClass().getName());
        if(properties != null && properties.size() > 0)
        {
            for(String property : properties)
            {
                try
                {
                    Class cls = dob.getClass();
                    String methodName = "get" + property + "Id";
                    Method method = cls.getMethod(methodName, noParameterTypes);
                    BigInteger id = (BigInteger)method.invoke(dob, noParameters);
                    if(id != null)
                    {
                        DataObject dataObject = em.find(DataObject.class, id);
                        if(dataObject == null)
                            continue;
                        DataObjectTypeLocal dotLocal = getDataObjectTypeLocal();
                        DataObjectType dot = dotLocal.getDataObjectType(dataObject.getDataObjectTypeId());
                        Class valueClass = Class.forName(dot.getDataObjectType());
                        Object entity = em.find(valueClass, id);
                        if(entity == null)
                            continue;

                        methodName = "set" + property;
                        method = cls.getMethod(methodName, noParameterTypes);
                        method.invoke(dob, new Object[] {entity});
                    }
                }
                catch(Exception ex)
                {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

}
