/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.EnumClass;
import com.cosmos.acacia.crm.enums.DatabaseResource;
import com.cosmos.acacia.crm.enums.MeasurementUnit;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author miro
 */
@Stateless
public class DatabaseResourceBean implements DatabaseResourceLocal {

    private static boolean initialized = false;

    private static Map<String, EnumClass> enumClassMap;
    private static Map<String, DbResource> dbResourceMap;

    @PersistenceContext
    private EntityManager em;


    public synchronized void initDatabaseResource() {
        if(!initialized)
        {
            System.out.println("initDatabaseResource()");
            initDatabaseResource(MeasurementUnit.class);
            initialized = true;
        }
    }

    public synchronized void initDatabaseResource(Class<? extends DatabaseResource> dbResourceClass) {
        if(!(dbResourceClass.isEnum()))
            throw new IllegalArgumentException("The DatabaseResource MUST be an Enum instance: " + dbResourceClass.getName());

        DatabaseResource[] dbResources = dbResourceClass.getEnumConstants();
        if(dbResources == null || dbResources.length == 0)
            return;

        DatabaseResource dbResource = dbResources[0];
        if(dbResource.isInitialized())
            return;

        for(DatabaseResource dbr : dbResources)
        {
            DbResource resource = getDbResource((Enum)dbr);
            System.out.println("resource: " + resource);
            dbr.setResourceId(resource.getResourceId());
        }
        dbResource.setInitialized(true);
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")


    private EnumClass getEnumClass(Enum dbEnum)
    {
        if(enumClassMap == null)
        {
            enumClassMap = new HashMap<String, EnumClass>();
        }

        String className = dbEnum.getClass().getName();
        EnumClass enumClass = enumClassMap.get(className);
        if(enumClass == null)
        {
            Query q = em.createNamedQuery("EnumClass.findByEnumClassName");
            q.setParameter("enumClassName", className);
            try
            {
                enumClass = (EnumClass)q.getSingleResult();
            }
            catch(NoResultException ex)
            {
                enumClass = new EnumClass();
                enumClass.setEnumClassName(className);
                em.persist(enumClass);
            }

            enumClassMap.put(className, enumClass);
        }

        return enumClass;
    }

    private DbResource getDbResource(Enum dbEnum)
    {
        if(dbResourceMap == null)
        {
            dbResourceMap = new HashMap<String, DbResource>();
        }

        EnumClass enumClass = getEnumClass(dbEnum);
        String enumName = dbEnum.name();
        DbResource dbResource = dbResourceMap.get(enumName);
        if(dbResource == null)
        {
            Query q = em.createNamedQuery("DbResource.findByEnumClassAndName");
            q.setParameter("enumClass", enumClass);
            q.setParameter("enumName", enumName);
            try
            {
                dbResource = (DbResource)q.getSingleResult();
            }
            catch(NoResultException ex)
            {
                dbResource = new DbResource();
                dbResource.setEnumClass(enumClass);
                dbResource.setEnumName(enumName);
                em.persist(dbResource);
            }

            dbResourceMap.put(enumName, dbResource);
        }

        return dbResource;
    }
}
