package com.bodani.zookeeper;

import java.nio.charset.Charset;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

public class ActiveKeyValueStore extends ConnectionWatcher {
	
		private static final Charset CHARSET = Charset.forName("UTF-8");
		
		public void write(String path, String value) throws KeeperException, InterruptedException {
			Stat stat = zk.exists(path, false);
			if(stat == null){
				zk.create(path, value.getBytes(CHARSET), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}else{
//				zk.setData(path, value.getBytes(CHARSET),stat.getVersion());
				zk.setData(path, value.getBytes(CHARSET),-1);
			}
		}
		
		public String read(String path,Watcher watcher) throws KeeperException, InterruptedException{
			byte[] data = zk.getData(path, watcher, null);
			return new String(data,CHARSET);
		}
}
