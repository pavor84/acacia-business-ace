package com.cosmos.acacia.callback;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * Implement this interface to provide specific callback
 * processing functionality on the client side
 *
 * @author Bozhidar Bozhanov
 *
 */
public interface CallbackHandler extends Remote {

    /**
     * Handles a callback request and returns the result
     *
     * @param req the request
     * @return the result
     */
    public CallbackTransportObject handle(CallbackTransportObject req) throws RemoteException;
}
