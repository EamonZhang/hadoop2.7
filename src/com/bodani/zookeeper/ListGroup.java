package com.bodani.zookeeper;

import java.util.List;

import org.apache.zookeeper.KeeperException;

public class ListGroup extends ConnectionWatcher {

	public void list(String groupName) throws KeeperException, InterruptedException{
		String path = "/"+groupName;
		List<String> children = zk.getChildren(path, false);
		if(children.isEmpty()){
			System.out.printf("No members in groups %s n", groupName);
		}
		for (String child : children) {
			System.out.println(child);
		}
	}
	public static void main(String[] args) throws KeeperException, InterruptedException {
		ListGroup listGroup = new ListGroup();
		listGroup.connent("node2");
		listGroup.list("");
		listGroup.close();
	}
}
