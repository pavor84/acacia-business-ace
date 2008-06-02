package com.cosmos.acacia.callback;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Callback extends Remote
{
  public static final String NAME = "Callback";
  public static final int FAILURE = -1;
  public static final int SUCCESS = 0;

  public int register(Object callbackObj) throws RemoteException;
  public CallbackResult askClient(CallbackRequest request) throws RemoteException;
  public void init() throws RemoteException;

} // end Callback