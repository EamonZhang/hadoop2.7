package com.bodani.zookeeper;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.KeeperException;

public class ConfigUpdater {
	
	public static final String PATH = "/config";
	
	private ActiveKeyValueStore store;
	private Random random = new Random();
	
	public ConfigUpdater(String hosts){
		store = new ActiveKeyValueStore();
		store.connent(hosts);
	
	}
	
	public void run() throws KeeperException, InterruptedException{
		while(true){
			String value = random.nextInt(100)+"";
			store.write(PATH, value);
			System.out.printf("Set %s to %s \n",PATH,value);
			TimeUnit.SECONDS.sleep(random.nextInt(10));
		}
	}
	
	public static void main(String[] args) throws KeeperException, InterruptedException {
		ConfigUpdater configUpdater = new ConfigUpdater("node3,node4");
		configUpdater.run();
	}
}
