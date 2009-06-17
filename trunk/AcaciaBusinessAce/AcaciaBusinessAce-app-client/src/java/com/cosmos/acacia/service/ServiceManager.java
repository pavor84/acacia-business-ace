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

    private static InheritableThreadLocal<ServiceManagerImpl> instance =
            new InheritableThreadLocal<ServiceManagerImpl>();

    public static ServiceManagerImpl getInstance() {
        ServiceManagerImpl serviceManager;
        if ((serviceManager = instance.get()) == null) {
            serviceManager = new ServiceManagerImpl();
            instance.set(serviceManager);
        }

        return serviceManager;
    }

    public static void removeInstance() {
        instance.remove();
    }

    public static Object getService(String serviceName) {
        return getInstance().getService(serviceName);
    }

    public static <T> T getRemoteService(Class<T> serviceInterface) {
        return getInstance().getRemoteService(serviceInterface);
    }

    public static <T> T getRemoteService(Class<T> serviceInterface, boolean checkPermissions) {
        return getInstance().getRemoteService(serviceInterface, checkPermissions);
    }
}
