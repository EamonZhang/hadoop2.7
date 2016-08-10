package util;

import java.io.IOException;

import org.apache.hadoop.mapred.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;

public class RunJob {
	public static void main(String[] args) {
	}
	/** 
     * job2 依赖于 job1 
     * @param job1 
     * @param job2 
     * @param chainName 
     * @return 
     * @throws IOException 
     */  
    public static void handleJobChain(Job job1 ,Job job2, String chainName) throws IOException{  
        ControlledJob controlledJob1 = new ControlledJob(job1.getConfiguration());  
        controlledJob1.setJob(job1);  
          
        ControlledJob controlledJob2 = new ControlledJob(job2.getConfiguration());  
        controlledJob2.setJob(job2);  
        controlledJob2.addDependingJob(controlledJob1);  
          
        JobControl jc = new JobControl(chainName);  
        jc.addJob(controlledJob1);  
        jc.addJob(controlledJob2);
        jc.run();
    }
}
