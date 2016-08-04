package com.bodani.mr.temperature.sort;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;

public class CreateTemperatureData extends Configured implements Tool{

	@Override
	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(getConf());
		
		return 0;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
