/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.util;

import java.io.Serializable;
import java.util.TreeMap;


/**
 *
 * @author Miro
 */
public class Properties
    implements Serializable
{
    private static final long serialVersionUID = 8527276141572799592L;

    private int level;

    private TreeMap<Integer, Properties> levels =
            new TreeMap<Integer, Properties>();


    public Properties()
    {
        this(Integer.MAX_VALUE);
    }

    public Properties(int level)
    {
        this.level = level;
        levels.put(level, this);
    }

    /*public Object getProperty(String key)
    {
        return getProperty(key, null);
    }

    public Object getProperty(String key, Object defaultValue)
    {
        Object value;
        if((value = getProperties().get(key)) != null)
            return value;

        if(defaults != null && (value = defaults.getProperty(key)) != null)
        {
            return value;
        }

        return defaultValue;
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

    public Object setProperty(String key, Object value)
    {
        return map.put(key, value);
    }

    public void putAll(Map<String, Object> sourceMap)
    {
        map.putAll(sourceMap);
    }

    public boolean containsKey(String key)
    {
        if(map.containsKey(key))
            return true;

        if(defaults == null)
            return false;

        return defaults.containsKey(key);
    }

    public boolean containsValue(Object value)
    {
        if(map.containsValue(value))
            return true;

        if(defaults == null)
            return false;

        return defaults.containsValue(value);
    }

    public void clear()
    {
        map.clear();
    }

    public Set<String> keySet()
    {
        if(defaults == null)
            return map.keySet();

        TreeSet keySet = new TreeSet(map.keySet());

        Set<String> defaultsKeySet = defaults.keySet();
        for(String key : defaultsKeySet)
        {
            if(!keySet.contains(key))
            {
                keySet.add(key);
            }
        }

        return keySet;
    }*/

    private Properties getProperties()
    {
        return getProperties(level);
    }

    public Properties getProperties(int level)
    {
        return levels.get(level);
    }

    public Properties putProperties(int level, Properties properties)
    {
        return levels.put(level, properties);
    }
}
