/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.assembling.Algorithm;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.EnumClass;
import com.cosmos.acacia.crm.enums.CommunicationType;
import com.cosmos.acacia.crm.enums.Currency;
import com.cosmos.acacia.crm.enums.DatabaseResource;
import com.cosmos.acacia.crm.enums.Gender;
import com.cosmos.acacia.crm.enums.MeasurementUnit;
import com.cosmos.acacia.crm.enums.OrganizationType;
import com.cosmos.acacia.crm.enums.PassportType;
import com.cosmos.acacia.crm.enums.ProductColor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
public class DatabaseResourceBean
    implements DatabaseResourceLocal
{

    private static boolean initialized = false;

    private static Map<String, EnumClass> enumClassMap;
    private static Map<Enum, Map<String, DbResource>> dbResourceMap;

    @PersistenceContext
    private EntityManager em;


    @Override
    public synchronized void initDatabaseResource() {
        if(!initialized)
        {
            System.out.println("initDatabaseResource()");
            getDbResources(Gender.class);
            getDbResources(MeasurementUnit.class);
            getDbResources(OrganizationType.class);
            getDbResources(ProductColor.class);
            getDbResources(CommunicationType.class);
            getDbResources(Currency.class);
            getDbResources(PassportType.class);
            getDbResources(Algorithm.Type.class);
            initialized = true;
        }
    }

    public List<DbResource> getDbResources(Class<? extends DatabaseResource> dbResourceClass)
    {
        DatabaseResource[] dbResourcesArray = dbResourceClass.getEnumConstants();
        int size;
        if(dbResourcesArray == null || (size = dbResourcesArray.length) == 0)
            return Collections.<DbResource>emptyList();

        List<DbResource> dbResources = new ArrayList<DbResource>(size);

        for(DatabaseResource dbr : dbResourcesArray)
        {
            Enum enumValue = (Enum)dbr;
            DbResource resource = getDbResource(enumValue);
            dbr.setDbResource(resource);
            dbResources.add(resource);
        }

        return dbResources;
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
            System.out.println("Enum Class Name: " + className);
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
            dbResourceMap = new HashMap<Enum, Map<String, DbResource>>();
        }

        Map<String, DbResource> enumMap = dbResourceMap.get(dbEnum);
        if(enumMap == null)
        {
            enumMap = new HashMap<String, DbResource>(dbEnum.getClass().getEnumConstants().length);
        }

        String enumName = dbEnum.name();
        DbResource dbResource = enumMap.get(enumName);
        if(dbResource == null)
        {
            EnumClass enumClass = getEnumClass(dbEnum);
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

            enumMap.put(enumName, dbResource);
        }

        return dbResource;
    }
}