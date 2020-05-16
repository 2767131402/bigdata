package io.zhenglei.zookeeper.write;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import io.zhenglei.zookeeper.constants.ZookeeperConstants;
import io.zhenglei.zookeeper.rmi.HelloServiceImpl;

public class Write implements Watcher {
	static Logger logger = Logger.getLogger(Write.class.getSimpleName()); 
	CountDownLatch countDownLatch = new CountDownLatch(1);
	ZooKeeper zooKeeper = null;
	public Write() {
		zooKeeper = createZooKeeper();
	}

	private ZooKeeper createZooKeeper() {
		ZooKeeper zk = null;
		try {
			zk = new ZooKeeper(ZookeeperConstants.CONNECT, ZookeeperConstants.TIMEOUT, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.debug("zk连接中.....");
		try {
			countDownLatch.await();
			logger.debug("已经连接上zookeeper" + zk);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return zk;
	}

	public void registerUrl(int port, String url){
		try {
			LocateRegistry.createRegistry(port);
			Naming.rebind(url, new HelloServiceImpl());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void writeZookeeper(int port, String url){
		if (zooKeeper == null || url == null) {
			throw new RuntimeException("无法连接zookeeper或url为null");
		}
		registerUrl(port, url);
		logger.debug(url + "已经注册");
		try {
			String znode = zooKeeper.create(ZookeeperConstants.SUB_PATH, url.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			logger.warn("已创建节点" + znode);
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void process(WatchedEvent event) {
		if(event.getState()==KeeperState.SyncConnected){
			countDownLatch.countDown();
		}
	}
	
	public static void main(String[] args) {
		Write write = new Write();
		write.writeZookeeper(8888, "rmi://localhost:8888/zk");
//		write.writeZookeeper(8889, "rmi://localhost:8889/zk");
	}

}
