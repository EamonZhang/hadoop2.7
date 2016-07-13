package com.bodani.zookeeper;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;

public class ConnectionWatcher implements Watcher{ 
	private static final int SESSION_TIMEOUT = 5000;
	protected ZooKeeper zk;
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
	
	public void close() throws InterruptedException{
		zk.close();
	}
}
