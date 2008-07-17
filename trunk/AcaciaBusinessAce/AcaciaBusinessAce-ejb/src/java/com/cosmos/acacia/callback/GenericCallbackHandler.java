package com.cosmos.acacia.callback;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.rmi.PortableRemoteObject;

import org.apache.log4j.Logger;

public class GenericCallbackHandler extends PortableRemoteObject
        implements CallbackHandler, Serializable, Remote {

    protected static transient Logger log = Logger.getLogger(GenericCallbackHandler.class);
    private transient Callbackable callbackable;

    public GenericCallbackHandler(Callbackable callbackable) throws RemoteException {
        this.callbackable = callbackable;
    }

    public GenericCallbackHandler() throws RemoteException {
        super();
    }

    @Override
    public CallbackTransportObject handle(CallbackTransportObject req)
            throws RemoteException {
        
        log.info("Classpath: " + System.getProperty("java.class.path"));
        Class clazz = null;
        try {
            clazz = this.getClass().getClassLoader().loadClass("com.cosmos.acacia.app.AcaciaSessionRemote");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return callbackable.callback(req);
    }

    /**
     * Factory method for creating a new callback handler
     */
    public static CallbackHandler createCallbackHandler(Callbackable callbackable) {
        try {
            return new GenericCallbackHandler(callbackable);
        } catch (RemoteException ex) {
            log.error("Error in creation of callback handler", ex);
            throw new RuntimeException(ex.getMessage());
        }
    }

}
