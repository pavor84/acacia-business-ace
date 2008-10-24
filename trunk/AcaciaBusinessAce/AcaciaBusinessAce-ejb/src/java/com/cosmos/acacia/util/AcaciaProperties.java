/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.util;

import com.cosmos.util.Properties;
import java.io.Serializable;

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
        System(1000),
        SystemAdministrator(2000),
        Organization(3000),
        OrganizationAdministrator(4000),
        Branch(5000),
        BranchAdministrator(6000),
        User(7000),
        Client(8000),
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

    private String schemaName;


    public AcaciaProperties(Level level)
    {
        super(level.getPriority());
    }

    public AcaciaProperties()
    {
        this(Level.Current);
    }

    public String getSchemaName()
    {
        return schemaName;
    }

    public void setSchemaName(String schemaName)
    {
        this.schemaName = schemaName;
    }

    public Properties getProperties(Level level)
    {
        return getProperties(level.getPriority());
    }

    public Properties putProperties(Level level, Properties properties)
    {
        return putProperties(level.getPriority(), properties);
    }
}
