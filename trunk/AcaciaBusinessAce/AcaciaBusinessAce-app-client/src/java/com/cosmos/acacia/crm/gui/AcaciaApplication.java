/*
 * DesktopApplication1.java
 */

package com.cosmos.acacia.crm.gui;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;


/**
 * The main class of the application.
 */
public class AcaciaApplication extends SingleFrameApplication {

    /**
     * Store here the seesionid, which has application scope.
     */
    private static Integer sessionid = null;

    public static Integer getSessionId(){
        return sessionid;
    }

    public static void setSessionId(Integer sid){
        sessionid = sid;
    }

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        AcaciaApplicationView view = new AcaciaApplicationView(this);
        view.login();
        view.init();
        show(view);
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
        //System.out.println("configureWindow()");
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
