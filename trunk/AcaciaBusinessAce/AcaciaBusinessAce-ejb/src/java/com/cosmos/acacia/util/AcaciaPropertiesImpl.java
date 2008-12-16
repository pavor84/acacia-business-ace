/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.util;

import com.cosmos.acacia.crm.data.properties.DbProperty;
import com.cosmos.acacia.security.AccessLevel;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author Miro
 */
public class AcaciaPropertiesImpl
    implements ManageableAcaciaProperties, Serializable
{
    private AccessLevel accessLevel;
    private BigInteger relatedObjectId;
    protected AcaciaProperties parentProperties;
    protected TreeMap<String, Serializable> data = new TreeMap<String, Serializable>();

    protected TreeSet<String> deletedItems = new TreeSet<String>();
    protected TreeSet<String> newItems = new TreeSet<String>();
    protected boolean changed;


    public AcaciaPropertiesImpl(
            AccessLevel accessLevel,
            BigInteger relatedObjectId)
    {
        this.accessLevel = accessLevel;
        this.relatedObjectId = relatedObjectId;
    }

    public AcaciaPropertiesImpl(
        AccessLevel accessLevel,
        BigInteger relatedObjectId,
        List<DbProperty> dbProperties)
    {
        this(accessLevel, relatedObjectId);
        for(DbProperty prop : dbProperties)
        {
            data.put(prop.getDbPropertyPK().getPropertyKey(), prop.getPropertyValue());
        }
    }

    @Override
    public AccessLevel getAccessLevel()
    {
        return accessLevel;
    }

    @Override
    public List<AccessLevel> getAccessLevels()
    {
        if(parentProperties == null)
            return Arrays.asList(accessLevel);

        List<AccessLevel> accessLevels = new ArrayList<AccessLevel>(2);
        accessLevels.add(accessLevel);
        accessLevels.addAll(parentProperties.getAccessLevels());

        return accessLevels;
    }

    @Override
    public List<AcaciaProperties> getAcaciaProperties()
    {
        if(parentProperties == null)
            return Arrays.asList((AcaciaProperties)this);

        List<AcaciaProperties> properties = new ArrayList<AcaciaProperties>(2);
        properties.add(this);
        properties.addAll(parentProperties.getAcaciaProperties());

        return properties;
    }

    @Override
    public BigInteger getRelatedObjectId()
    {
        return relatedObjectId;
    }

    @Override
    public AcaciaProperties getParentProperties()
    {
        return parentProperties;
    }

    @Override
    public void setParentProperties(AcaciaProperties parentProperties)
    {
        this.parentProperties = parentProperties;
    }

    @Override
    public AcaciaProperties getProperties(AccessLevel accessLevel)
    {
        if(this.accessLevel.equals(accessLevel))
            return this;

        if(parentProperties == null)
            return null;

        return parentProperties.getProperties(accessLevel);
    }

    @Override
    public AcaciaProperties getProperties(String key)
    {
        if(data.containsKey(key))
            return this;

        if(parentProperties == null)
            return null;

        return parentProperties.getProperties(key);
    }

    @Override
    public List<AcaciaProperties> getPropertiesStartsWith(String keyPrefix)
    {
        if(data.isEmpty() && parentProperties == null)
            return Collections.emptyList();

        List<AcaciaProperties> properties = new ArrayList<AcaciaProperties>();
        for(String key : data.keySet())
        {
            if(key.startsWith(keyPrefix))
            {
                properties.add(this);
                break;
            }
        }

        if(parentProperties != null)
            properties.addAll(parentProperties.getPropertiesStartsWith(keyPrefix));

        return properties;
    }

    @Override
    public List<AcaciaProperties> getPropertiesEndsWith(String keySuffix)
    {
        if(data.isEmpty() && parentProperties == null)
            return Collections.emptyList();

        List<AcaciaProperties> properties = new ArrayList<AcaciaProperties>();
        for(String key : data.keySet())
        {
            if(key.endsWith(keySuffix))
            {
                properties.add(this);
                break;
            }
        }

        if(parentProperties != null)
            properties.addAll(parentProperties.getPropertiesEndsWith(keySuffix));

        return properties;
    }

    @Override
    public List<AcaciaProperties> getPropertiesContains(String keyValue)
    {
        if(data.isEmpty() && parentProperties == null)
            return Collections.emptyList();

        List<AcaciaProperties> properties = new ArrayList<AcaciaProperties>();
        for(String key : data.keySet())
        {
            if(key.contains(keyValue))
            {
                properties.add(this);
                break;
            }
        }

        if(parentProperties != null)
            properties.addAll(parentProperties.getPropertiesContains(keyValue));

        return properties;
    }

    @Override
    public Serializable getProperty(String key)
    {
        return get(key);
    }

    @Override
    public Serializable get(Object key)
    {
        if(data.isEmpty() && parentProperties == null)
            return null;

        if(data.containsKey(key))
            return data.get(key);

        if(parentProperties != null)
            return parentProperties.get(key);

        return null;
    }

    @Override
    public Serializable setProperty(String key, Serializable value)
    {
        return put(key, value);
    }

    @Override
    public Serializable put(String key, Serializable value)
    {
        if(key == null || (key = key.trim()).length() == 0)
            throw new NullPointerException("The key can not be null or empty.");

        if(!changed)
            changed = true;

        deletedItems.remove(key);
        newItems.add(key);

        return data.put(key, value);
    }

    @Override
    public Serializable removeProperty(String key)
    {
        return remove(key);
    }

    @Override
    public Serializable remove(Object key)
    {
        String keyString = (String)key;
        if(keyString == null || (keyString = keyString.trim()).length() == 0)
            throw new NullPointerException("The key can not be null or empty.");

        if(!data.containsKey(keyString))
            return null;

        if(!changed)
            changed = true;

        deletedItems.add(keyString);
        newItems.remove(keyString);

        return data.remove(keyString);
    }

    @Override
    public int size()
    {
        if(parentProperties == null)
            return data.size();

        return keySet().size();
    }

    @Override
    public boolean isEmpty()
    {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key)
    {
        String keyString =(String)key;
        if(keyString == null || (keyString = keyString.trim()).length() == 0)
            throw new NullPointerException("The key can not be null or empty.");

        if(data.containsKey(keyString))
            return true;

        if(parentProperties != null && parentProperties.containsKey(key))
            return true;

        return false;
    }

    @Override
    public boolean containsValue(Object value)
    {
        if(data.containsValue(value))
            return true;

        if(parentProperties != null && parentProperties.containsValue(value))
            return true;

        return false;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Serializable> sourceMap)
    {
        if(!changed)
            changed = true;

        Set<String> keys = (Set<String>)sourceMap.keySet();
        deletedItems.removeAll(keys);
        newItems.addAll(keys);

        data.putAll(sourceMap);
    }

    @Override
    public void clear()
    {
        if(!changed)
            changed = true;

        deletedItems.addAll(data.keySet());
        newItems.clear();

        data.clear();
    }

    @Override
    public Set<String> keySet()
    {
        if(parentProperties == null)
            return data.keySet();

        TreeSet keySet = new TreeSet(data.keySet());
        for(String key : parentProperties.keySet())
        {
            if(!keySet.contains(key))
            {
                keySet.add(key);
            }
        }

        return keySet;
    }

    @Override
    public Collection<Serializable> values()
    {
        if(parentProperties == null)
            return data.values();

        List<Serializable> values = new ArrayList<Serializable>(data.values());
        for(Serializable value : parentProperties.values())
        {
            if(!values.contains(value))
            {
                values.add(value);
            }
        }

        return values;
    }

    @Override
    public Set<Entry<String, Serializable>> entrySet()
    {
        if(parentProperties == null)
            return data.entrySet();

        HashSet<Map.Entry<String, Serializable>> entrySet = new HashSet<Entry<String, Serializable>>();
        for(Map.Entry<String, Serializable> entry : parentProperties.entrySet())
        {
            if(!entrySet.contains(entry))
            {
                entrySet.add(entry);
            }
        }
        

        return entrySet;
    }

    @Override
    public boolean isChanged()
    {
        return changed;
    }

    @Override
    public void clearChanges()
    {
        deletedItems.clear();
        newItems.clear();
        changed = false;
    }

    @Override
    public Set<String> getDeletedItems()
    {
        return deletedItems;
    }

    @Override
    public Set<String> getNewItems()
    {
        return newItems;
    }

}
