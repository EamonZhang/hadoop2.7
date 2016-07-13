package com.bodani.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper.States;

public class GetData extends ConnectionWatcher {
	public byte[] getData(String groupName) throws KeeperException, InterruptedException{
		String path = "/"+groupName;
		byte[] data = zk.getData(path, false, null);
		return data;
	}
	
	public static void main(String[] args) throws KeeperException, InterruptedException {
		GetData getData = new GetData();
		getData.connent("node2");
		byte[] data =  getData.getData("test1");
		for (byte b : data) {
			System.out.println(b);
		}
		getData.close();
	}
}
