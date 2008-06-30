package com.cosmos.acacia.app;

import java.lang.reflect.Method;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.User;

@Remote
public interface SessionFacadeRemote {

    /**
     * Wraps all calls to methods of EJBs, providing them
     * with a way to access the user session
     *
     * @param bean
     * @param methodName
     * @param args
     * @return
     */
    Object call(Object bean, String methodName, Object[] args, Class[] parameterTypes, Integer sesionId) throws Throwable;

    /**
     * Adds a session to the SessionRegistry
     *
     * @param sessionId
     * @param session
     */
    void addSession(Integer sessionId, AcaciaSession session);
}
