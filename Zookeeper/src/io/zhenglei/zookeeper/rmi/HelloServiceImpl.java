package io.zhenglei.zookeeper.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloServiceImpl extends UnicastRemoteObject implements HelloService {
	private static final long serialVersionUID = -4143915583541039538L;

	public HelloServiceImpl() throws RemoteException {
	}

	@Override
	public String hello(String name) throws RemoteException {
		name = "你好"+name+"!";
		return name;
	}

}
