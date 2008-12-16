/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.assembling;

import com.cosmos.acacia.security.AccessLevel;
import com.cosmos.acacia.util.AcaciaProperties;
import java.io.Serializable;

/**
 *
 * @author Miro
 */
public class AssemblingProperty
    implements Serializable
{
    private AcaciaProperties acaciaProperties;
    private AccessLevel accessLevel;
    private String propertyKey;

    public AssemblingProperty(
            AcaciaProperties acaciaProperties,
            AccessLevel accessLevel,
            String propertyKey)
    {
        this.acaciaProperties = acaciaProperties;
        this.accessLevel = accessLevel;
        this.propertyKey = propertyKey;
    }

    public AcaciaProperties getAcaciaProperties()
    {
        return acaciaProperties;
    }

    public void setAcaciaProperties(AcaciaProperties acaciaProperties)
    {
        this.acaciaProperties = acaciaProperties;
    }

    public AccessLevel getAccessLevel()
    {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel)
    {
        this.accessLevel = accessLevel;
    }

    public String getPropertyKey()
    {
        return propertyKey;
    }

    public void setPropertyKey(String propertyKey)
    {
        this.propertyKey = propertyKey;
    }

    public boolean containsKey()
    {
        return acaciaProperties.containsKey(propertyKey);
    }

    public Serializable getPropertyValue()
    {
        return acaciaProperties.getProperty(propertyKey);
    }

    public void setPropertyValue(Serializable value)
    {
        acaciaProperties.setProperty(propertyKey, value);
    }

}
