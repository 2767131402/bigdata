package io.zhenglei.zookeeper.write;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import io.zhenglei.zookeeper.constants.ZookeeperConstants;
import io.zhenglei.zookeeper.rmi.HelloService;

public class Reader {
	CountDownLatch countDownLatch = new CountDownLatch(1);
	
	List<String> listUrl = new ArrayList<>();
	ZooKeeper zooKeeper = null;
	public Reader() {
		zooKeeper = createZooKeeper();
	}
	private ZooKeeper createZooKeeper() {
		ZooKeeper zk = null;
		try {
			zk = new ZooKeeper(ZookeeperConstants.CONNECT, ZookeeperConstants.TIMEOUT, new Watcher() {
				@Override
				public void process(WatchedEvent event) {
					
					if(event.getState()==KeeperState.SyncConnected){
						countDownLatch.countDown();
					}
					if(event.getType()==EventType.NodeChildrenChanged){
						getList();
						System.out.println("被删除...");
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return zk;
	}
	
	public List<String> getList() {
		listUrl.clear();
		try {
			List<String> list = zooKeeper.getChildren(ZookeeperConstants.PATH, true);
			for (String s : list) {
				System.out.println(s);
				listUrl.add(new String(zooKeeper.getData(ZookeeperConstants.PATH+"/"+s, null, new Stat())));
			}
		} catch (KeeperException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listUrl;
	}
	
	public static void main(String[] args) {
		Reader reader = new Reader();
		List<String> list = reader.getList();
		Random random = new Random();
		
		while(list.size()>0){
			int i = random.nextInt(list.size());
			try {
				HelloService service = (HelloService) Naming.lookup(list.get(i));
				String hello = service.hello("aaa");
				System.out.println(hello);
				
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			} catch (MalformedURLException | RemoteException | NotBoundException e) {
				e.printStackTrace();
			}
		}
	}
}
