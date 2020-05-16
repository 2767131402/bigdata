package com.oracle.mr;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.oracle.dimention.CommonDimetion;
import com.oracle.dimention.ResultValue;

public class TestMapperReducer {

	public static void main(String[] args) {
		Configuration cfg = new Configuration();
		try {
			
			Job job = Job.getInstance(cfg);
			job.setJarByClass(TestMapperReducer.class);
			job.setMapperClass(PhoneComputerMapper.class);
			job.setMapOutputKeyClass(CommonDimetion.class);
			job.setMapOutputValueClass(ResultValue.class);
			job.setReducerClass(PhoneComputerReducer.class);
			job.setOutputKeyClass(CommonDimetion.class);
			job.setOutputValueClass(ResultValue.class);
			FileInputFormat.setInputPaths(job, new Path("hdfs://yuhui4:9000/HTTP_20130313143750.dat"));
			FileOutputFormat.setOutputPath(job, new Path("hdfs://yuhui4:9000/wc"));
	        job.waitForCompletion(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
