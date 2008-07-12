package com.cosmos.acacia.app;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.rmi.ServerException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.cosmos.acacia.crm.validation.ValidationException;

@Stateless
public class SessionFacadeBean implements  SessionFacadeRemote, SessionFacadeLocal {

    protected Logger log = Logger.getLogger(SessionFacadeBean.class);

    @SuppressWarnings("unchecked")
    @Override
    public Object call(Object bean, String methodName, Object[] args, Class[] parameterTypes, Integer sessionId) throws Throwable {

        //find the session
        SessionContext sessionContext = SessionRegistry.getSession(sessionId);
        //set it as current
        SessionRegistry.setLocalSession(sessionContext);

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
                    EJBException tmp = (EJBException) t;
                    t = tmp.getCausedByException();
                    if (t == null)
                        t = tmp.getCause();

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
}
