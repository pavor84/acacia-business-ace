/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.service;

/**
 *
 * @author Miro
 */
public class NoSuchServiceException extends ServiceException {

    private String serviceName;

    public NoSuchServiceException(String serviceName) {
        super("No such service: \"" + serviceName + "\"");
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }
}
