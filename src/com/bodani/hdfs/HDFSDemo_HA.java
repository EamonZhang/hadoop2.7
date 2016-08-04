package com.bodani.hdfs;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

import util.ConfigurationUtil;

public class HDFSDemo_HA {
	/**
	 * 
	 * @param args
	 * @throws IOException
	 * @throws URISyntaxException
	 * @author eamon
	 * 
	 * 本地系统和hdfs系统之间 文件拷贝
	 * 
	 * demo 
	 * bin/hadoop jar runjar/hadoop_hdfs-2.7.jar put datademo/temperature input/temperature
	 * 
	 * bin/hadoop jar runjar/hadoop_hdfs-2.7.jar get input/temperature datademo/tem
	 * 
	 */
	public static void main(String[] args) throws IOException, URISyntaxException {
        Configuration conf = ConfigurationUtil.getConfigurationHA();
        // hdfs 拷贝文件到本地系统
        if(args[0].endsWith("get")){
//            hdfs 文件系统
        	FileSystem fs = FileSystem.get(URI.create("hdfs://mycluster:8020"),conf);
            InputStream open = fs.open(new Path(args[1]));
          //本地 文件系统
            OutputStream out = new FileOutputStream(args[2]);
            IOUtils.copyBytes(open, out, 4096, true);
//             本地系统上传文件到 hdfs
        }else if(args[0].endsWith("put")){
        	   //hdfs 文件系统
            FileSystem fs = FileSystem.get(URI.create("hdfs://mycluster:8020"),conf);
            OutputStream out = fs.create(new Path(args[2]),new Progressable() {
				
				@Override
				public void progress() {
						System.out.println("...");
				}
			});
          //本地 文件系统
            InputStream in = new FileInputStream(args[1]);
            IOUtils.copyBytes(in, out, 4096, true);
            IOUtils.closeStream(in);
            IOUtils.closeStream(out);
        }else{
        	System.out.println("please ser type put or get");
        	
        }
        
        System.out.println("do well");
        
    }
}