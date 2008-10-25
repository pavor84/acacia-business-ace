/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.util;

import java.io.Serializable;
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
    private TreeMap<String, Object> data = new TreeMap<String, Object>();

    private TreeMap<Integer, Properties> levels =
            new TreeMap<Integer, Properties>();


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
        return data.put(key, value);
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
        data.putAll(sourceMap);
    }

    public void clear()
    {
        data.clear();
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

    public Properties removeProperties(int level)
    {
        return levels.remove(level);
    }
}
