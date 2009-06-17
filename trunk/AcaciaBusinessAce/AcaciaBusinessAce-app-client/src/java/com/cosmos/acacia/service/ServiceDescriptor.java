/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.service;

/**
 *
 * @author Miro
 */
public class ServiceDescriptor<SD> {

    private String serviceName;
    private Class<SD> serviceInterface;
    private boolean remote;
    private Class<? extends SD> serviceImplementation;
    private boolean checkPermissions = true;

    /**
     * Default constructor for Remote Service description
     * @param serviceInterface
     */
    public ServiceDescriptor(Class<SD> serviceInterface) {
        this.serviceInterface = serviceInterface;
        serviceName = serviceInterface.getName();
        remote = true;
    }

    /**
     * Default constructor for Local Service description
     * @param serviceInterface
     */
    public ServiceDescriptor(String serviceName, Class<SD> serviceInterface, Class<? extends SD> serviceImplementation) {
        this(serviceName, serviceInterface, false, serviceImplementation);
    }

    public ServiceDescriptor(String serviceName, Class<SD> serviceInterface, boolean remote, Class<? extends SD> serviceImplementation) {
        this.serviceName = serviceName;
        this.serviceInterface = serviceInterface;
        this.remote = remote;
        this.serviceImplementation = serviceImplementation;
    }

    public boolean isRemote() {
        return remote;
    }

    public Class<? extends SD> getServiceImplementation() {
        return serviceImplementation;
    }

    public Class<SD> getServiceInterface() {
        return serviceInterface;
    }

    public String getServiceName() {
        return serviceName;
    }

    public boolean isCheckPermissions() {
        return checkPermissions;
    }

    public void setCheckPermissions(boolean checkPermissions) {
        this.checkPermissions = checkPermissions;
    }
}
