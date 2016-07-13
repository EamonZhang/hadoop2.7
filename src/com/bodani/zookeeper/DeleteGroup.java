package com.bodani.zookeeper;

import java.util.List;

import org.apache.zookeeper.KeeperException;

public class DeleteGroup extends ConnectionWatcher {
	public void delete(String groupName) throws KeeperException, InterruptedException{
		String path = "/"+groupName;
		List<String> children = zk.getChildren(path, false);
		for (String child : children) {
			zk.delete(path+"/"+child, -1);
		}
		zk.delete(path, -1);
		System.out.println("deleted "+path);
	}
	
	public static void main(String[] args) throws KeeperException, InterruptedException {
		DeleteGroup deleteGroup = new DeleteGroup();
		deleteGroup.connent("node2");
		deleteGroup.delete("test1");
		deleteGroup.close();
	}
}
