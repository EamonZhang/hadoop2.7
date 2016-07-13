package com.bodani.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;

public class ConfigWatcher implements Watcher {

	 private ActiveKeyValueStore store;
	 
	 public ConfigWatcher(String hosts) {
		 store = new ActiveKeyValueStore();
		 store.connent(hosts);
	}
	 
	 public void displayConfig() throws KeeperException, InterruptedException{
		 String value = store.read(ConfigUpdater.PATH, this);
		 System.out.printf("Read %s as %s \n",ConfigUpdater.PATH,value);
	 }

	@Override
	public void process(WatchedEvent event) {
		if(event.getType() == EventType.NodeDataChanged){
			try {
				displayConfig();
			} catch (KeeperException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws KeeperException, InterruptedException {
		ConfigWatcher configWatcher = new ConfigWatcher("node2,node3");
		configWatcher.displayConfig();
		Thread.sleep(Long.MAX_VALUE);
	}
}
