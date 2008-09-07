/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationActionMap;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.swingx.JXTreeTable;

/**
 *
 * @author Miro
 */
public class JBTreeTable
    extends JXTreeTable
{
    private Application application;
    private ApplicationContext applicationContext;
    private ApplicationActionMap applicationActionMap;
    private ResourceMap resourceMap;


    public JBTreeTable()
    {
        this(Application.getInstance());
    }

    public JBTreeTable(Class<? extends Application> applicationClass)
    {
        this(Application.getInstance(applicationClass));
    }

    public JBTreeTable(Application application)
    {
        internalInitialization();
        this.application = application;
    }

    protected void internalInitialization()
    {
        setSelectionModel(new TableSelectionModel());
        setAutoResizeMode(AUTO_RESIZE_OFF);
        setColumnControlVisible(true);
        getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setHorizontalScrollEnabled(true);
        setEditable(false);
    }

    public void addListSelectionListener(ListSelectionListener listener)
    {
        ListSelectionModel selectionModel;
        if((selectionModel = getSelectionModel()) != null)
            selectionModel.addListSelectionListener(listener);
    }

    public void removeListSelectionListener(ListSelectionListener listener)
    {
        ListSelectionModel selectionModel;
        if((selectionModel = getSelectionModel()) != null)
            selectionModel.removeListSelectionListener(listener);
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

    public void setResourceMap(ResourceMap resourceMap) {
        this.resourceMap = resourceMap;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
