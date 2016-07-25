HDFS HBASE HIVE 部署说明

一,HDFS HA (version Hadoop 2.7.2)

部署分布

zookeeper | node2 ,node3, node4 (version zookeeper-3.4.8)

namenode,zkfc  | node1 ,node2

journalnode | node2 ,node3 ,node4

datanode | node3 ,node4 ,node5


1 启动zk的服务：zkServer.sh start，之后可以输入zkServer.sh status查看启动状态，本次我们配置了三个DN节点，会出现一个leader和两个follower。输入jps，会显示启动进程：QuorumPeerMain

2 在NN节点上（选一台即可，这里我选择的是一台预NNA节点），然后启动journalnode服务，命令如下：hadoop-daemons.sh start journalnode。或者单独进入到每个DN输入启动命令：hadoop-daemon.sh start journalnode。输入jps显示启动进程：JournalNode。

3 接着若是配置后，我们首次启动，需要格式化HDFS，命令如下：hadoop namenode -format。

4 之后我们需要格式化ZK，命令如下：hdfs zkfc -formatZK。
接着我们启动hdfs和yarn，命令如下：start-dfs.sh和start-yarn.sh，我们在nna输入jps查看进程，显示如下：DFSZKFailoverController，NameNode，ResourceManager。

5 需要同步NNA节点的元数据，命令如下：hdfs namenode -bootstrapStandby，若执行正常，日志最后显示如下信息：

6 接着我们在NNS输入jps查看，发现只有DFSZKFailoverController ,NameNode 进程，这里我们需要手动启动NNS上的namenode和ResourceManager进程，命令如下：hadoop-daemon.sh start namenode和yarn-daemon.sh start resourcemanager。

* 需要注意的是，在NNS上的yarn-site.xml中，需要配置指向NNS，属性配置为rm2，在NNA中配置的是rm1。

$bin/hdfs haadmin -getServiceState nn1
active
$bin/hdfs haadmin -getServiceState nn2
standby

测试 任意节点 (namenode,datanode)

$ bin/hdfs dfs -mkdir /user
$ bin/hdfs dfs -mkdir /user/<username>
$ bin/hdfs dfs -put etc/hadoop input

$ bin/hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-2.7.2.jar grep input output 'dfs[a-z.]+'

$ tail -500f logs/hadoop-root-namenode-node1.log


WEB URL

node1:50070
node2:50070
node1:8188
node2:8188

遇到问题 ：

java.io.IOException: Timed out waiting 20000ms for a quorum of nodes to respond.

原因 :

full GC 导致写journalnode超时。

解决方法 ：

修改mapred-site.xml中内存参数

7 启动JobhistoryServer可以通过历史服务器查看已经运行完的Mapreduce作业记录

$ sbin/mr-jobhistory-daemon.sh  start historyserver

WEB URL
node1:19888

8 启动WebAppProxy Server

$ sbin/yarn-daemon.sh start proxyserver

二,Hbase HA, (version hbase-1.1.5)

部署分布
master| node1 node2 
regionservers| node3 node4 node5

1 启动 ,
node1 $ start-hbase.sh ,
node2 $ hbase-daemon.sh start master 

WEB URL

node1:16010
node2:16010

2 启动 ThriftServer ，

$ hbase-deamon.sh start thrift2 

3 启动 RESTServer

$ hbase-deamon.sh start rest 

三, Hive (version 2.0.1)

1 启动

$ bin/hiveserver2 &

WEB URL

node:10002


