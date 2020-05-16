package io.zhenglei.zookeeper.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloService extends Remote {
	String hello(String name) throws RemoteException;
}
