/*
 * DesktopApplication1.java
 */

package com.cosmos.acacia.crm.gui;

import com.cosmos.acacia.crm.bl.impl.DatabaseResourceRemote;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.enums.MeasurementUnit;
import java.util.List;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class AcaciaApplication extends SingleFrameApplication {

    @EJB
    private DatabaseResourceRemote databaseResource;

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new AcaciaApplicationView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
        System.out.println("configureWindow()");
        try
        {
            if(databaseResource == null)
                databaseResource = InitialContext.doLookup(DatabaseResourceRemote.class.getName());

            List<DbResource> dbResources = databaseResource.getDbResources(MeasurementUnit.class);
            for(DbResource dbResource : dbResources)
            {
                MeasurementUnit unit = MeasurementUnit.valueOf(dbResource.getEnumName());
                unit.setDbResource(dbResource);
            }
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of DesktopApplication1
     */
    public static AcaciaApplication getApplication() {
        return Application.getInstance(AcaciaApplication.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(AcaciaApplication.class, args);
    }
}
