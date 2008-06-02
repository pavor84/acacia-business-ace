package com.cosmos.acacia.callback;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Callback extends Remote, Serializable
{
  public static final String NAME = "Callback";
  public static final int FAILURE = -1;
  public static final int SUCCESS = 0;

  public int register(CallbackClient callbackObj) throws RemoteException;
  public CallbackResult askClient(CallbackRequest request) throws RemoteException;
  public void init(int id) throws RemoteException;
} // end Callback