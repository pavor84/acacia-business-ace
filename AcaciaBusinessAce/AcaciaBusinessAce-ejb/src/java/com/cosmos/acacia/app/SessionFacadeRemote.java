package com.cosmos.acacia.app;

import java.util.UUID;
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
     * @param sessionId
     * @param checkPermissions
     * @return
     */
    @SuppressWarnings("unchecked")
    Object call(Object bean,
            String methodName,
            Object[] args,
            Class[] parameterTypes,
            UUID sessionId,
            boolean checkPermissions) throws Throwable;
}
