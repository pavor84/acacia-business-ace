/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;


/**
 *
 * @author Miro
 */
public class Properties
    implements Serializable
{
    private static final long serialVersionUID = 8527276141572799592L;

    private int level;
    protected HashMap<String, Object> data = new HashMap<String, Object>();

    protected TreeMap<Integer, Properties> levels =
            new TreeMap<Integer, Properties>();

    protected TreeSet<String> deletedItems = new TreeSet<String>();
    protected TreeSet<String> newItems = new TreeSet<String>();

    protected boolean changed;


    public Properties()
    {
        this(Integer.MAX_VALUE);
    }

    public Properties(int level)
    {
        this.level = level;
    }

    public int getLevel()
    {
        return level;
    }

    public boolean isChanged()
    {
        if(changed)
            return true;

        for(Integer levelId : levels.descendingKeySet())
        {
            Properties properties = levels.get(levelId);
            if(properties.isChanged())
                return true;
        }

        return changed;
    }


    public Object getProperty(String key)
    {
        return getProperty(key, null);
    }

    public Object getProperty(String key, Object defaultValue)
    {
        if(data.containsKey(key))
            return data.get(key);

        for(Integer levelId : levels.descendingKeySet())
        {
            Properties properties = levels.get(levelId);
            if(properties.containsKey(key))
                return properties.getProperty(key, defaultValue);
        }

        return defaultValue;
    }

    public Object setProperty(String key, Object value)
    {
        if(!changed)
            changed = true;

        deletedItems.remove(key);
        newItems.add(key);

        return data.put(key, value);
    }

    public Object setProperty(int level, String key, Object value)
    {
        Properties properties;
        if((properties = getProperties(level)) == null)
            throw new IllegalArgumentException("No such Properties for level " + level);

        return properties.setProperty(key, value);
    }

    public Object removeProperty(String key)
    {
        if(!data.containsKey(key))
            return null;

        if(!changed)
            changed = true;

        deletedItems.add(key);
        newItems.remove(key);

        return data.remove(key);
    }

    public Object removeProperty(int level, String key)
    {
        Properties properties;
        if((properties = getProperties(level)) == null)
            throw new IllegalArgumentException("No such Properties for level " + level);

        return properties.removeProperty(key);
    }

    public boolean containsKey(String key)
    {
        if(data.containsKey(key))
            return true;

        for(Integer levelId : levels.descendingKeySet())
        {
            if(levels.get(levelId).containsKey(key))
                return true;
        }

        return false;
    }

    public boolean containsValue(Object value)
    {
        if(data.containsValue(value))
            return true;

        for(Integer levelId : levels.descendingKeySet())
        {
            if(levels.get(levelId).containsValue(value))
                return true;
        }

        return false;
    }

    public String getPropertyString(String key)
    {
        return getPropertyString(key, null);
    }

    public String getPropertyString(String key, String defaultValue)
    {
        Object value;
        if((value = getProperty(key, defaultValue)) == null)
            return null;

        if(value instanceof CharSequence)
            return ((CharSequence)value).toString();

        return String.valueOf(value);
    }

    public void putAll(Map<String, Object> sourceMap)
    {
        if(!changed)
            changed = true;

        Set<String> keys = sourceMap.keySet();
        deletedItems.removeAll(keys);
        newItems.addAll(keys);

        data.putAll(sourceMap);
    }

    public void clear()
    {
        if(!changed)
            changed = true;

        deletedItems.addAll(data.keySet());
        newItems.clear();

        data.clear();
    }

    public int size()
    {
        if(levels.size() == 0)
            return data.size();

        return keySet().size();
    }

    public Set<String> keySet()
    {
        if(levels.size() == 0)
            return data.keySet();

        TreeSet keySet = new TreeSet(data.keySet());

        for(Integer levelId : levels.descendingKeySet())
        {
            Properties properties = levels.get(levelId);
            for(String key : properties.keySet())
            {
                if(!keySet.contains(key))
                {
                    keySet.add(key);
                }
            }
        }

        return keySet;
    }

    public Properties getProperties(int level)
    {
        return levels.get(level);
    }

    public Properties putProperties(int level, Properties properties)
    {
        return levels.put(level, properties);
    }

    public Set<Integer> getLevels()
    {
        return levels.keySet();
    }

    public Properties removeLevel(int level)
    {
        return levels.remove(level);
    }

    public void removeAllLevels()
    {
        levels.clear();
    }

}
