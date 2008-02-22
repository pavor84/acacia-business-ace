/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.gui;

import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.TextResource;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationActionMap;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;

/**
 *
 * @author miro
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

    public ResourceMap getResourceMap(String beanClassName)
    {
        try
        {
            return getResourceMap(Class.forName(beanClassName));
        }
        catch(ClassNotFoundException ex)
        {
            ex.printStackTrace();
            return null;
        }
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

    public String getShortName(DbResource dbResource)
    {
        ResourceMap resource = getResourceMap(dbResource.getEnumClass().getEnumClassName());
        if(resource != null)
        {
            String value;
            if((value = resource.getString(dbResource.getEnumName() + ".shortName")) != null)
            {
                return value;
            }
        }

        return getShortName((TextResource)dbResource.getEnumValue());
    }

    public String getShortName(TextResource TextResource)
    {
        return TextResource.getShortName();
    }

    public String getName(DbResource dbResource)
    {
        ResourceMap resource = getResourceMap(dbResource.getEnumClass().getEnumClassName());
        if(resource != null)
        {
            String value;
            if((value = resource.getString(dbResource.getEnumName() + ".name")) != null)
            {
                return value;
            }
        }

        return getName((TextResource)dbResource.getEnumValue());
    }

    public String getName(TextResource TextResource)
    {
        return TextResource.getName();
    }

    public String getFullName(DbResource dbResource)
    {
        String shortName = getShortName(dbResource);
        String name = getName(dbResource);
        if(shortName != null || name != null)
        {
            return getFullName(shortName, name);
        }

        return getFullName((TextResource)dbResource.getEnumValue());
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
}
