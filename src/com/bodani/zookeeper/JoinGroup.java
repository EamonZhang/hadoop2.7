package com.bodani.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;

public class JoinGroup extends ConnectionWatcher{

	 public void join(String groupName,String memberName) throws KeeperException, InterruptedException{
		 String path = "/"+groupName + "/"+memberName;
		 String createdPath = zk.create(path, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT); 
		 System.out.print("Created "+ createdPath);
	 }
	 
	 public static void main(String[] args) throws KeeperException, InterruptedException {
		 JoinGroup joinGroup = new JoinGroup();
		 joinGroup.connent("node2");
		 joinGroup.join("test", "a");
	 }
}
