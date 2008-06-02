package com.cosmos.acacia.callback;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

/**
 * Implements the methods defined in the Callback remote interface.
 */
public class CallbackImpl extends PortableRemoteObject
    implements Callback, CallbackClient, Serializable
{

    private static final long serialVersionUID = -1422941713617179007L;

    private CallbackEnabled parent;

    /** Indicating whether the instance is acting as server or client */
    public boolean client = false;

    protected CallbackImpl() throws RemoteException {
        super();
    }

    public static Callback getInstance() {
        try {
            try {
                Object o = InitialContext.doLookup(Callback.NAME);
                Callback server = (Callback) PortableRemoteObject.narrow(o, Callback.class);
                System.out.println("getting " + server);
                return server;
            } catch (NamingException ex) {
                CallbackImpl impl = new CallbackImpl();
                impl.init();
                return impl;
            }
            //System.out.println("on init obj is: " + impl);
            //return impl;
        } catch (RemoteException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static CallbackImpl getClientInstance() {
        try {
            CallbackImpl impl = new CallbackImpl();
            impl.initClient();
            impl.client = true;
            return impl;
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    private CallbackClient callbackObj;  // Object on client to verify parameters.

    public void init() throws RemoteException {
        try {
            Properties props = new Properties();
            props.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
            props.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
            props.setProperty("java.naming.factory.state","com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");

            InitialContext ctx = new InitialContext(props);

            ctx.rebind(Callback.NAME, this);
        } catch (NamingException ex){
            ex.printStackTrace();
        }
    }

  // remember clientobject sent to server
    public int register(CallbackClient callbackObj) throws RemoteException
    {
        try {
            //System.out.println("Registering callback object " + callbackObj);
            if (callbackObj == null) return Callback.FAILURE;
            this.callbackObj = callbackObj;
            return Callback.SUCCESS;
        } catch (Exception ex){
            ex.printStackTrace();
            return 0;
        }
    }

  // send regular dataobject to server
  // This method returns empty string on success or else error message.
    public CallbackResult askClient(CallbackRequest req) throws RemoteException
    {
     // client call_back
        System.out.println("Calling on object " + callbackObj);

        CallbackResult res = callbackObj.serveResult(req);

        return res;
  }

    // Client methods below

    private Context getContext(String url) throws NamingException
    {
       return new InitialContext();
    }

    @Override
    public CallbackResult serveResult(CallbackRequest req) {
       CallbackResult res = new CallbackResult();
       res.setId(req.getId() * 5);
       return res;
    }

    public void initClient()
       {
            Callback server = null;  // An instance of the CallbackClientIntf
            try
            {
                Object o = getContext(null).lookup(Callback.NAME);
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

    public CallbackEnabled getParent() {
        return parent;
    }

    public void setParent(CallbackEnabled parent) {
        this.parent = parent;
    }
} // end CallbackImpl
