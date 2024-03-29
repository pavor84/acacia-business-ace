/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.service;

import com.cosmos.impl.acacia.service.ServiceManagerImpl;

/**
 *
 * @author Miro
 */
public class ServiceManager {

//    private static final InheritableThreadLocal<ServiceManagerImpl> instance =
//            new InheritableThreadLocal<ServiceManagerImpl>();
//
//    public static ServiceManagerImpl getInstance() {
//        ServiceManagerImpl serviceManager;
//        if ((serviceManager = instance.get()) == null) {
//            serviceManager = new ServiceManagerImpl();
//            instance.set(serviceManager);
//        }
//
//        return serviceManager;
//    }
//
//    public static void removeInstance() {
//        instance.remove();
//    }

    private static ServiceManagerImpl instance;

    public static ServiceManagerImpl getInstance() {
        if (instance == null) {
            instance = new ServiceManagerImpl();
        }

        return instance;
    }

    public static void removeInstance() {
        instance = null;
    }

    public static Object getService(String serviceName) {
        return getInstance().getService(serviceName);
    }

    public static <T> T getRemoteService(Class<T> serviceInterface) {
        return getInstance().getRemoteService(serviceInterface);
    }

    public static <T> T getRemoteService(Class<T> serviceInterface, boolean checkPermissions) {
        ServiceManagerImpl serviceManagerImpl = getInstance();
        return serviceManagerImpl.getRemoteService(serviceInterface, checkPermissions);
    }
}
