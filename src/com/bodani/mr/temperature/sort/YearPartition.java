package com.bodani.mr.temperature.sort;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * 分区 
 */
public class YearPartition extends Partitioner<KeyPari, Text> {
    @Override
    public int getPartition(KeyPari key, Text value, int numPartitions) {
        return (key.getYear() * 127) % numPartitions;
    }
}