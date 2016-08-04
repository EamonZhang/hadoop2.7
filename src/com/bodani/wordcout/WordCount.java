package com.bodani.wordcout;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCount {

	public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {

		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();

		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			StringTokenizer itr = new StringTokenizer(value.toString());
			while (itr.hasMoreTokens()) {
				word.set(itr.nextToken());
				context.write(word, one);
			}
		}
	}

	public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
		private IntWritable result = new IntWritable();

		public void reduce(Text key, Iterable<IntWritable> values, Context context)
				throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable val : values) {
				sum += val.get();
			}
			result.set(sum);
			context.write(key, result);
		}
	} 

	public static void main(String[] args) throws Exception {
//		String inputdata = "hdfs://localhost:9000/user/input/word";
//		String outputdata = "hdfs://localhost:9000/user/putout/result";
		
		//--------------HA---------------
		
//		String inputdata = "hdfs://mycluster:8020/user/root/input/word";
//		String outputdata = "hdfs://mycluster:8020/user/root/putout/result";
		String inputdata = "input/word";
		String outputdata = "out/result";
		Configuration conf = new Configuration();
		
		 //配置和/cloud/hadoop-2.7.1/etc/hadoop/hdfs-site.xml中一样
        conf.set("dfs.nameservices", "mycluster");
        conf.set("dfs.ha.namenodes.mycluster", "nn1,nn2");
        conf.set("dfs.namenode.rpc-address.mycluster.nn1", "master:8020");
        conf.set("dfs.namenode.rpc-address.mycluster.nn2", "slave:8020");
        conf.set("dfs.client.failover.proxy.provider.mycluster", "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
		
		Job job = Job.getInstance(conf, "word count");
		job.setJarByClass(WordCount.class);
		job.setMapperClass(TokenizerMapper.class);
		job.setCombinerClass(IntSumReducer.class);
		job.setReducerClass(IntSumReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job, new Path(inputdata));
		FileOutputFormat.setOutputPath(job, new Path(outputdata));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		System.out.println("------");
	}
}
