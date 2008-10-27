/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.util;

import com.cosmos.acacia.crm.data.properties.DbProperty;
import com.cosmos.acacia.crm.data.properties.DbPropertyPK;
import com.cosmos.util.Properties;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author Miro
 */
public class AcaciaProperties
    extends Properties
    implements Serializable
{
    private static final long serialVersionUID = 3758888654566816952L;

    private static final Logger logger = Logger.getLogger(AcaciaProperties.class);

    public enum Level
    {
        System(100000000),
        SystemAdministrator(200000000),
        Organization(300000000),
        OrganizationAdministrator(400000000),
        Branch(500000000),
        BranchAdministrator(600000000),
        User(700000000),
        Client(800000000),
        Current(Integer.MAX_VALUE), //2,147,483,647 = 0x7fff ffff
        ;

        private Level(int priority)
        {
            this.priority = priority;
        }

        private int priority;

        public int getPriority()
        {
            return priority;
        }

    };


    private BigInteger relatedObjectId;


    public AcaciaProperties(Level level, BigInteger relatedObjectId)
    {
        super(level.getPriority());
        this.relatedObjectId = relatedObjectId;
    }

    public AcaciaProperties()
    {
        this(Level.Current, null);
    }

    public BigInteger getRelatedObjectId()
    {
        return relatedObjectId;
    }

    public Object setProperty(Level level, String key, Object value)
    {
        return setProperty(level.getPriority(), key, value);
    }

    public Object removeProperty(Level level, String key)
    {
        return removeProperty(level.getPriority(), key);
    }

    public void updateProperties(AcaciaProperties properties)
    {
        data.clear();
        data.putAll(properties.data);
    }

    public Properties getProperties(Level level)
    {
        return getProperties(level.getPriority());
    }

    public Properties putProperties(Level level, Properties properties)
    {
        return putProperties(level.getPriority(), properties);
    }

    public Properties putProperties(
            Level level,
            BigInteger relatedObjectId,
            List<DbProperty> dbProperties)
    {
        AcaciaProperties properties = new AcaciaProperties(level, relatedObjectId);
        Map<String, Object> propertiesData = properties.data;
        for(DbProperty property : dbProperties)
        {
            String key = property.getDbPropertyPK().getPropertyKey();
            Object value = property.getPropertyValue();
            propertiesData.put(key, value);
        }

        return putProperties(level, properties);
    }

    public void save(EntityManager em)
    {
        if(!isChanged())
            return;

        if(Level.Current.getPriority() != getLevel())
        {
            removeDeleted(em);
            insertOrUpdate(em);

            deletedItems.clear();
            newItems.clear();
        }

        for(Properties props : levels.values())
        {
            if(props instanceof AcaciaProperties)
                ((AcaciaProperties)props).save(em);
        }

        changed = false;
    }

    private void removeDeleted(EntityManager em)
    {
        if(deletedItems.size() == 0)
            return;

        int levelId = getLevel();
        Query q = em.createNamedQuery("DbProperty.removeByLevelIdAndRelatedObjectIdAndPropertyKeys");
        q.setParameter("levelId", levelId);
        q.setParameter("relatedObjectId", relatedObjectId);
        q.setParameter("propertyKeys", deletedItems);
        q.executeUpdate();
    }

    private void insertOrUpdate(EntityManager em)
    {
        if(newItems.size() == 0)
            return;

        int levelId = getLevel();
        DbPropertyPK propertyPK = new DbPropertyPK(levelId, relatedObjectId, null);
        for(String key : newItems)
        {
            propertyPK.setPropertyKey(key);
            DbProperty property = em.find(DbProperty.class, propertyPK);
            if(property == null)
            {
                property = new DbProperty(levelId, relatedObjectId, key);
            }
            property.setPropertyValue((Serializable)data.get(key));
            em.persist(property);
        }
    }
}
