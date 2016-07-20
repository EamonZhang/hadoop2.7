package com.bodani.hdfs;

import java.io.InputStream;
import java.net.URI;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;

public class FileSystemCat {
    public static void main(String[] args) throws Exception {
//        String uri = args[0];
    	String uri = "hdfs://node2:9000/user/root/input/word";
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem. get(URI.create (uri), conf);
        InputStream in = null;
    try {
            in = fs.open( new Path(uri));
            IOUtils.copyBytes(in, System.out, 4096, false);
        } finally {
            IOUtils.closeStream(in);
        }
    }
}