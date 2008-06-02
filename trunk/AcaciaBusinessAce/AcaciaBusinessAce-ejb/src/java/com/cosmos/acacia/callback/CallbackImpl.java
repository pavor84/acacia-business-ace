package com.cosmos.acacia.callback;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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

    /** Indicating whether the instance is acting as server or client */
    public boolean client = false;

    protected CallbackImpl() throws RemoteException {
        super();
    }

    public static Callback getInstance() {
        try {
            CallbackImpl impl = new CallbackImpl();
            impl.init();
            System.out.println("on init obj is: " + impl);
            return impl;
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
    private Object callbackObj;  // Object on client to verify parameters.

    public Object getObj() {
        return callbackObj;
    }
    public void init() throws RemoteException {
        try {
            Context ctx = new InitialContext();
            ctx.rebind(Callback.NAME, this);
        } catch (NamingException ex){
            ex.printStackTrace();
        }
    }

  // remember clientobject sent to server
    public int register(Object callbackObj) throws RemoteException
    {
        try {
            System.out.println("Registering callback object " + callbackObj);
            if (callbackObj == null) return Callback.FAILURE;
            this.callbackObj = callbackObj;
            System.out.println("on registering obj is " + callbackObj);
            System.out.println("on registering this is " + this);
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

        if (!(callbackObj instanceof CallbackClient))
            return null;

        CallbackResult res = ((CallbackClient) callbackObj).serveResult(req);

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
} // end CallbackImpl
