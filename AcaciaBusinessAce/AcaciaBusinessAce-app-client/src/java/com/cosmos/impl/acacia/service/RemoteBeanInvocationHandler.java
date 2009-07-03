/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.impl.acacia.service;

import com.cosmos.acacia.app.SessionFacadeRemote;
import com.cosmos.acacia.crm.gui.AcaciaApplication;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Miro
 */
public class RemoteBeanInvocationHandler<E> implements InvocationHandler {

    private E service;
    private SessionFacadeRemote sessionFacade;
    private boolean checkPermissions = true;

    public RemoteBeanInvocationHandler(E service, boolean checkPermissions) {
        this.service = service;
        this.checkPermissions = checkPermissions;
        try {
            sessionFacade = InitialContext.doLookup(SessionFacadeRemote.class.getName());
        } catch (NamingException ex) {
            ex.printStackTrace();
            //log.error("", ex);
            }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //log.info("Method called: " + method.getName() + " on bean: " + bean);
        try {
            return sessionFacade.call(
                    service,
                    method.getName(),
                    args,
                    method.getParameterTypes(),
                    AcaciaApplication.getSessionId(), checkPermissions);
        } catch(Exception ex) {
            throw new RuntimeException("proxy=" + proxy +
                    ", method=" + method.getName() +
                    ", args=" + (args != null ? Arrays.asList(args) : ""), ex);
        }
    }
}
