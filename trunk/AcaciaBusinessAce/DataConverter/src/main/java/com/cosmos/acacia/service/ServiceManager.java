/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.service;

import com.cosmos.acacia.converter.DataConverter;
import com.cosmos.impl.acacia.converter.DataConverterImpl;
import java.util.HashMap;

/**
 *
 * @author Miro
 */
public final class ServiceManager {

    private static InheritableThreadLocal<ServiceManager> instance = new InheritableThreadLocal<ServiceManager>();

    private static ServiceManager getInstance() {
        ServiceManager serviceManager;
        if ((serviceManager = instance.get()) == null) {
            serviceManager = new ServiceManager();
            instance.set(serviceManager);
        }

        return serviceManager;
    }

    private static void removeInstance() {
        instance.remove();
    }

    public static <S> S getService(Class<S> serviceClass) {
        return getInstance().getServiceImpl(serviceClass);
    }

    /**
     * 
     */

    private HashMap<Class, Object> services = new HashMap<Class, Object>();

    private ServiceManager() {
    }

    private <S> S getServiceImpl(Class<S> serviceClass) {
        S service;
        if((service = (S) services.get(serviceClass)) == null) {
            if(DataConverter.class == serviceClass) {
                service = (S) new DataConverterImpl();
            } else {
                throw new UnsupportedOperationException("Unsupported service " + serviceClass);
            }

            services.put(serviceClass, service);
        }

        return service;
    }
}
