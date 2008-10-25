/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.util;

import com.cosmos.acacia.crm.data.properties.DbProperty;
import com.cosmos.util.Properties;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Miro
 */
public class AcaciaProperties
    extends Properties
    implements Serializable
{
    private static final long serialVersionUID = 3758888654566816952L;

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


    public AcaciaProperties(Level level)
    {
        super(level.getPriority());
    }

    public AcaciaProperties()
    {
        this(Level.Current);
    }

    public Properties getProperties(Level level)
    {
        return getProperties(level.getPriority());
    }

    public Properties putProperties(Level level, Properties properties)
    {
        return putProperties(level.getPriority(), properties);
    }

    public Properties putProperties(Level level, List<DbProperty> dbProperties)
    {
        AcaciaProperties properties = new AcaciaProperties(level);
        Map<String, Object> propertiesData = properties.data;
        for(DbProperty property : dbProperties)
        {
            String key = property.getDbPropertyPK().getPropertyKey();
            Object value = property.getPropertyValue();
            propertiesData.put(key, value);
        }

        return putProperties(level, properties);
    }
}
