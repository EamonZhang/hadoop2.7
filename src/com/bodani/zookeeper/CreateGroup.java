package com.bodani.zookeeper;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;

public class CreateGroup implements Watcher {

	private static final int SESSION_TIMEOUT = 5000;
	private ZooKeeper zk;
	private CountDownLatch connectedSignal = new CountDownLatch(1);

	public void connent(String hosts) {
		try {
			zk = new ZooKeeper(hosts, SESSION_TIMEOUT, this);
			connectedSignal.countDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void process(WatchedEvent event) {
		if (event.getState() == KeeperState.SyncConnected) {
			connectedSignal.countDown();
			System.out.println("connection succes !");
		}
	}

	public void create(String groupName){
		try {
			String path = "/"+groupName;
			byte[] data = new byte[]{1,2,4};
//			new ACL(Perms.CREATE, new Id("ip",""));
			String createPath = zk.create(path, "mydata".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println("Created "+ createPath);
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {
			zk.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		CreateGroup cg = new CreateGroup();
		cg.connent("node2");
		cg.create("test1");
		cg.close();
	}
}
