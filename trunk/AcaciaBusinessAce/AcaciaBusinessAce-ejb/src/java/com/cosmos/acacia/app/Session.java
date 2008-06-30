package com.cosmos.acacia.app;

import com.cosmos.acacia.crm.validation.ValidationException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


import java.rmi.RemoteException;
import java.rmi.ServerException;
import javax.ejb.EJBException;
import javax.ejb.Stateless;

@Stateless
public class Session implements  SessionFacadeRemote, SessionFacadeLocal {

    private static ThreadLocal<Integer> sessionId = new ThreadLocal<Integer>();

    @Override
    public Object call(Object bean, String methodName, Object[] args, Class[] parameterTypes, Integer sessionId) throws Throwable {
        this.sessionId.set(sessionId);
        Method method = bean.getClass().getMethod(methodName, parameterTypes);
        try {
            return method.invoke(bean, args);
        } catch (InvocationTargetException ex){
            Throwable t = getRootCause(ex);
            while(t != null)
            {
                if(t instanceof ValidationException)
                {
                    throw (ValidationException)t;
                }
                else if(t instanceof ServerException || t instanceof RemoteException)
                {
                    t = t.getCause();
                }
                else if(t instanceof EJBException)
                {
                    t = ((EJBException)t).getCausedByException();
                } else
                    break;
            }
            throw ex.getCause();
        }
    }

    private Throwable getRootCause(Throwable t) {
        while (t.getCause() != null)
            t = t.getCause();
        
        return t;
    }
    public static Integer getSessionId() {
        return sessionId.get();
    }

    public static AcaciaSession getSession() {
        return SessionRegistry.getSession(getSessionId());
    }

    @Override
    public void addSession(Integer sessionId, AcaciaSession session) {
        SessionRegistry.addSession(sessionId, session);
    }

}
