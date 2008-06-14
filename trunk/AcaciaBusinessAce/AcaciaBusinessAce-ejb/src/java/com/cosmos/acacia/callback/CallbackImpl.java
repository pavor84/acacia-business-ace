package com.cosmos.acacia.callback;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.apache.log4j.Logger;

/**
 * Implements the methods defined in the Callback interfaces.
 */
public class CallbackImpl extends PortableRemoteObject
    implements Callback, CallbackClient, Serializable
{

    private static final Logger log = Logger.getLogger(CallbackImpl.class);

    private static final long serialVersionUID = -1422941713617179007L;

    /** Indicating whether the instance is acting as server or client */
    public boolean client = false;

    /** Identifier for this object */
    private int id;

    protected CallbackImpl() throws RemoteException {
        super();
    }

    /**
     * Gets a server instance of the Callback object.
     * @param id the identifier returned by prepareCallback()
     * @return the server callback object
     */
    public static Callback getInstance(int id){
        if (id == 0)
            log.error("You should first call prepareCallback()" +
                    " and pass the result as an id");
        try {
            Object o = InitialContext.doLookup(Callback.NAME + id);
            Callback server = (Callback) PortableRemoteObject.narrow(o, Callback.class);
            System.out.println("getting " + server);
            return server;
        } catch (NamingException ex) {
            int result = prepareCallback();
            if (result != 0)
                return getInstance(result);

            ex.printStackTrace();
            return null;
        }
    }

    public static int prepareCallback() {
        try {
            int id = (int) (Math.random() * 65535) + 1;
            CallbackImpl impl = new CallbackImpl();
            impl.init(id);
            return id;
        } catch (RemoteException ex){
            return 0;
        }
    }

    /**
     * Gets a client instance of the Callback object.
     *
     * @param id the identifier returned by CallbackEnabled.prepareCallback();
     * @param handler the request handler, specific for each case
     * @return the client callback object
     */
    public static CallbackImpl getClientInstance(int id, CallbackHandler handler) {
        if (id == 0)
            log.error("You should first call prepareCallback()" +
                    " and pass the result as an id");
        if (handler == null)
            log.error("You should provide a non-null handler for this callback");

        try {
            CallbackImpl impl = new CallbackImpl();
            impl.setCallbackHandler(handler);
            impl.initClient(id);
            impl.client = true;
            return impl;
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    private CallbackClient callbackObj;  // Object on client to verify parameters.

    public void init(int id) throws RemoteException {
        try {
            this.id = id;
            Properties props = new Properties();
            props.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
            props.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
            props.setProperty("java.naming.factory.state","com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");

            InitialContext ctx = new InitialContext(props);

            ctx.rebind(Callback.NAME + id, this);
        } catch (NamingException ex){
            ex.printStackTrace();
        }
    }

  // remember clientobject sent to server
    public int register(CallbackClient callbackObj) throws RemoteException
    {
        try {
            if (callbackObj == null) return Callback.FAILURE;
            System.out.println("Registering callback object handler " + ((CallbackImpl)callbackObj).getCallbackHandler());
            this.callbackObj = callbackObj;
            return Callback.SUCCESS;
        } catch (Exception ex){
            ex.printStackTrace();
            return 0;
        }
    }

    /**
     * Call this method when requesting information from the client
     *
     * @param req the request object
     * @return a result object
     */
    public CallbackResult askClient(CallbackRequest req) throws RemoteException {
        System.out.println("Calling on object " + callbackObj);

        CallbackResult res = callbackObj.serveResult(req);

        return res;
  }

    // Client methods below

    private Context getContext(String url) throws NamingException
    {
       return new InitialContext();
    }

    private CallbackHandler callbackHandler;
    @Override
    public CallbackResult serveResult(CallbackRequest req) {
       return callbackHandler.handle(req);
    }

    public void initClient(int id)
       {
            Callback server = null;  // An instance of the CallbackClientIntf
            try
            {
                Object o = getContext(null).lookup(Callback.NAME + id);
                server = (Callback) PortableRemoteObject.narrow(o, Callback.class);
            } catch(Exception e) {
              e.printStackTrace();
            }

            System.out.println(server);
            //1: register ClientObject to server
             try
             {
                 int s = server.register(this);
                 if (s == Callback.FAILURE)
                 {
                    System.out.println("1. Couldn't send client object to server");
                 }
                 else
                    System.out.println("1. Success sending ClientObject to server");
             }
             catch(Exception e)
             {
                 System.out.println("exception in rmiRegister: "+e);
             }

       }

    @Override
    public String toString(){
        return super.toString() + ": " + client;
    }

    public CallbackHandler getCallbackHandler() {
        return callbackHandler;
    }

    public void setCallbackHandler(CallbackHandler handler) {
        this.callbackHandler = handler;
    }

    @Override
    public void finalize() {
        try {
            getContext(null).unbind(Callback.NAME + id);
        } catch (NamingException ex) {
            //
        }
    }
} // end CallbackImpl
