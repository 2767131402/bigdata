package io.zhenglei.hadoop.job;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import io.zhenglei.hadoop.WordCountMapper;
import io.zhenglei.hadoop.WordCountReduce;
import io.zhenglei.hadoop.dimetion.WordArticleDimetion;
import io.zhenglei.hadoop.dimetion.WordSortDimetion;
import io.zhenglei.hadoop.two.WordCountMapper2;
import io.zhenglei.hadoop.two.WordCountReduce2;

public class Jobs implements Tool {

	private Configuration conf;
	 
	public static void main(String[] args) {
		try {
			ToolRunner.run(new Jobs(), args);
			System.out.println("end");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	@Override
	public Configuration getConf() {
		return conf;
	}

	@Override
	public int run(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		
		Job job1 = Job.getInstance(getConf());
		job1.setJarByClass(Jobs.class);
		
		job1.setMapperClass(WordCountMapper.class);
		job1.setMapOutputKeyClass(WordArticleDimetion.class);
		job1.setMapOutputValueClass(LongWritable.class);
		
		job1.setReducerClass(WordCountReduce.class);
		job1.setOutputKeyClass(WordArticleDimetion.class);
		job1.setOutputValueClass(LongWritable.class);
		FileInputFormat.setInputPaths(job1, new Path("hdfs://192.168.44.132:9000/test/word"));
		FileOutputFormat.setOutputPath(job1, new Path("hdfs://192.168.44.132:9000/test/word/result1"));	
	
		Job job2 = Job.getInstance(getConf());
		job2.setJarByClass(Jobs.class);
		
		job2.setMapperClass(WordCountMapper2.class);
		job2.setMapOutputKeyClass(Text.class);
		job2.setMapOutputValueClass(WordSortDimetion.class);
		
		job2.setReducerClass(WordCountReduce2.class);
		job2.setOutputKeyClass(Text.class);
		job2.setOutputValueClass(Text.class);
		FileInputFormat.setInputPaths(job2, new Path("hdfs://192.168.44.132:9000/test/word/result1/part-r-00000"));
		FileOutputFormat.setOutputPath(job2, new Path("hdfs://192.168.44.132:9000/test/zl"));	
		
		
		ControlledJob job = new ControlledJob(getConf());
		job.setJob(job1);
		
		ControlledJob jobs = new ControlledJob(getConf());
		jobs.setJob(job2);
		jobs.addDependingJob(job);
		
		JobControl control = new JobControl("word");
		control.addJob(job);
		control.addJob(jobs);
		
		new Thread(control).start();
		while(true){
			if(control.allFinished()){
				System.out.println("success");
				control.stop();
				break;
			}
			if(control.getFailedJobList().size()>0){
				System.out.println("false");
				control.stop();
			}
		}
		return 0;
	}

}
