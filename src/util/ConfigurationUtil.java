package util;

import org.apache.hadoop.conf.Configuration;

public class ConfigurationUtil {
	private static final Configuration conf = new Configuration();
	public static Configuration getConfigurationHA() {
		// 配置和/cloud/hadoop-2.7.1/etc/hadoop/hdfs-site.xml中一样
		conf.set("dfs.nameservices", "mycluster");
		conf.set("dfs.ha.namenodes.mycluster", "nn1,nn2");
		conf.set("dfs.namenode.rpc-address.mycluster.nn1", "master:8020");
		conf.set("dfs.namenode.rpc-address.mycluster.nn2", "slave:8020");
		conf.set("dfs.client.failover.proxy.provider.mycluster",
				"org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
		return conf;
	}
	
	public static Configuration getConfiguration() {
		return conf;
	}
}
