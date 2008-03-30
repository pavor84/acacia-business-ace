/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.resource;

import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationActionMap;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;

/**
 *
 * @author Miro
 */
public class BeanResource
{
    private Application application;
    private ApplicationContext applicationContext;
    private ApplicationActionMap applicationActionMap;
    private ResourceMap resourceMap;


    public BeanResource(Class<? extends Application> applicationClass)
    {
        this(Application.getInstance(applicationClass));
    }

    public BeanResource(Application application)
    {
        super();
        this.application = application;
    }

    public ApplicationContext getContext()
    {
        if(applicationContext == null)
        {
            Application app = getApplication();
            if(app != null)
            {
                applicationContext = app.getContext();
            }
        }

        return applicationContext;
    }

    public ApplicationActionMap getApplicationActionMap()
    {
        if(applicationActionMap == null)
        {
            ApplicationContext context = getContext();
            if(context != null)
            {
                applicationActionMap = context.getActionMap(this);
            }
        }

        return applicationActionMap;
    }

    public ResourceMap getResourceMap()
    {
        if(resourceMap == null)
        {
            ApplicationContext context = getContext();
            if(context != null)
            {
                resourceMap = context.getResourceMap(this.getClass());
            }
        }

        return resourceMap;
    }

    public ResourceMap getResourceMap(Class beanClass)
    {
        ApplicationContext context = getContext();
        if(context != null)
        {
            return context.getResourceMap(beanClass);
        }

        return null;
    }

    public void setResourceMap(ResourceMap resourceMap) {
        this.resourceMap = resourceMap;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public String getShortName(EnumResource enumResource)
    {
        Enum enumValue = enumResource.getEnumValue();
        return getShortName(enumValue.getClass(), enumValue.name());
    }

    public String getShortName(Class beanClass, String key)
    {
        ResourceMap resource = getResourceMap(beanClass);
        if(resource != null)
        {
            String value;
            if((value = resource.getString(key + ".shortText")) != null)
            {
                return value;
            }
        }

        return key;
    }

    public String getShortName(TextResource TextResource)
    {
        return TextResource.toShortText();
    }

    public String getName(EnumResource enumResource)
    {
        Enum enumValue = enumResource.getEnumValue();
        return getName(enumValue.getClass(), enumValue.name());
    }

    public String getName(Class beanClass, String key)
    {
        ResourceMap resource = getResourceMap(beanClass);
        if(resource != null)
        {
            String value;
            if((value = resource.getString(key + ".text")) != null)
            {
                return value;
            }
        }

        return key;
    }

    public String getName(TextResource TextResource)
    {
        return TextResource.toText();
    }

    public String getFullName(EnumResource enumResource)
    {
        Enum enumValue = enumResource.getEnumValue();
        return getFullName(enumValue.getClass(), enumValue.name());
    }

    public String getFullName(Class beanClass, String key)
    {
        String shortName = getShortName(beanClass, key);
        String name = getName(beanClass, key);
        if(shortName != null || name != null)
        {
            return getFullName(shortName, name);
        }

        return key;
    }

    public String getFullName(TextResource TextResource)
    {
        String shortName = getShortName(TextResource);
        String name = getName(TextResource);
        return getFullName(shortName, name);
    }

    protected String getFullName(String shortName, String name)
    {
        if(shortName == null)
            return name;

        if(name == null)
            return shortName;

        return shortName + ", " + name;
    }

    public String getFullNameValue(Object sourceValue)
    {
        if(sourceValue == null)
            return null;

        if(sourceValue instanceof EnumResource)
        {
            return getFullName((EnumResource)sourceValue);
        }
        else if(sourceValue instanceof TextResource)
        {
            return getFullName((TextResource)sourceValue);
        }

        return sourceValue.toString();
    }

    public String getShortNameValue(Object sourceValue)
    {
        if(sourceValue == null)
            return null;

        if(sourceValue instanceof EnumResource)
        {
            return getShortName((EnumResource)sourceValue);
        }
        else if(sourceValue instanceof TextResource)
        {
            return getShortName((TextResource)sourceValue);
        }

        return sourceValue.toString();
    }

    public String getNameValue(Object sourceValue)
    {
        if(sourceValue == null)
            return null;

        if(sourceValue instanceof EnumResource)
        {
            return getName((EnumResource)sourceValue);
        }
        else if(sourceValue instanceof TextResource)
        {
            return getName((TextResource)sourceValue);
        }

        return sourceValue.toString();
    }
}
