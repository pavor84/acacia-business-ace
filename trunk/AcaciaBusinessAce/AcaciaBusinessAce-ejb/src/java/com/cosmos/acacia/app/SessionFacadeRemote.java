package com.cosmos.acacia.app;

import javax.ejb.Remote;

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
    @SuppressWarnings("unchecked")
	Object call(Object bean, String methodName, Object[] args, Class[] parameterTypes, Integer sesionId) throws Throwable;
}
