package com.cosmos.acacia.crm.security;

import java.lang.reflect.Method;

import javax.ejb.Local;

@Local
public interface PermissionsManagerLocal {


    /**
     * Checks if the user has permissions to call the method
     *
     * @param method
     * @param args
     *
     * @return false if the call is disallowed; true otherwise.
     */
    public boolean isAllowedPreCall(
            Method method,
            Object[] args);

    /**
     * Checks if the user has permissions to see the result
     *  of the method invocation
     *
     * @param result
     *
     * @return false if the call is disallowed; true otherwise.
     */
    public boolean isAllowedPostCall(Object result);


    /**
     * Filter a collection of items, hiding those
     * which are not allowed for reading
     *
     * @param result
     * @return
     */
    public Object filterResult(Object result);
}
