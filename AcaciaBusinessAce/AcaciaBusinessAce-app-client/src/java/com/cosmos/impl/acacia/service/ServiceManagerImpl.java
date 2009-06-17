/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.impl.acacia.service;

import com.cosmos.acacia.crm.bl.currency.CurrencyRemote;
import com.cosmos.acacia.service.LocalServiceException;
import com.cosmos.acacia.service.NoSuchServiceException;
import com.cosmos.acacia.service.ServiceDescriptor;
import com.cosmos.acacia.service.ServiceException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.TreeMap;
import javax.naming.InitialContext;

/**
 *
 * @author Miro
 */
public class ServiceManagerImpl {

    private static Map<String, ServiceDescriptor> serviceDescriptorMap =
            new TreeMap<String, ServiceDescriptor>();
    static {
        addServiceDescriptor(new ServiceDescriptor<CurrencyRemote>(CurrencyRemote.class));
        addServiceDescriptor(new ServiceDescriptor<CurrencyRemote>("currency", CurrencyRemote.class, CurrencyServiceImpl.class));
    }
    //
    private ClassLoader classLoader;
    private Map<String, Object> serviceMap = new TreeMap<String, Object>();

    public Object getService(String serviceName) {
        Object service;
        if((service = serviceMap.get(serviceName)) == null) {
            ServiceDescriptor sd;
            if((sd = serviceDescriptorMap.get(serviceName)) == null) {
                throw new NoSuchServiceException(serviceName);
            }

            if(sd.isRemote()) {
                service = getRemoteService(sd);
            } else {
                try {
                    service = sd.getServiceImplementation().newInstance();
                } catch(Exception ex) {
                    throw new LocalServiceException(ex, sd);
                }
            }

            if(!serviceMap.containsKey(serviceName)) {
                serviceMap.put(serviceName, service);
            }
        }

        return service;
    }

    protected <T> T getRemoteService(ServiceDescriptor<T> sd) {
        return getRemoteService(sd.getServiceInterface(), sd.isCheckPermissions());
    }

    public <T> T getRemoteService(Class<T> serviceInterface) {
        return getRemoteService(serviceInterface, true);
    }

    public <T> T getRemoteService(Class<T> serviceInterface, boolean checkPermissions) {
        String serviceName = serviceInterface.getName();
        T service;
        if((service = (T)serviceMap.get(serviceName)) != null) {
            return service;
        }

        try {
            InitialContext ctx = new InitialContext();
            service = (T) ctx.lookup(serviceInterface.getName());
            InvocationHandler handler = new RemoteBeanInvocationHandler(service, checkPermissions);

            T proxy = (T) Proxy.newProxyInstance(getClassLoader(),
                    new Class[]{serviceInterface}, handler);

            serviceMap.put(serviceName, proxy);

            return proxy;
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    public ClassLoader getClassLoader() {
        if (classLoader == null) {
            classLoader = getClass().getClassLoader();
        }

        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    protected static void addServiceDescriptor(ServiceDescriptor serviceDescriptor) {
        serviceDescriptorMap.put(serviceDescriptor.getServiceName(), serviceDescriptor);
    }
}
