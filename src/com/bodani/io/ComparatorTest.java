package com.bodani.io;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.hadoop.io.DataOutputOutputStream;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.RawComparator;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparator;

public class ComparatorTest {

	public static void main(String[] args) throws IOException {
		//比较
		RawComparator<IntWritable> comparator = WritableComparator.get(IntWritable.class);
		int result = comparator.compare(new IntWritable(7), new IntWritable(6));
		System.out.println(result);
		//序列化
		IntWritable writable1 = new IntWritable(111);
		byte[]  bytes = serialize(writable1);
		//反序列化
		IntWritable writable2 = new IntWritable();
		deserialize(writable2, bytes);
		System.out.println(writable2.get());
	}
	// 序列化 io >> Object
	public static byte[] serialize(Writable writable) throws IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream dataout = new DataOutputStream(out);
		writable.write(dataout);
		dataout.close();
		return out.toByteArray();
	}
	//反序列化 Object >> io
	public static void deserialize(Writable writable,byte[] bytes) throws IOException{
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		DataInputStream datain = new DataInputStream(in);
		writable.readFields(datain);
		datain.close();
	}
}
