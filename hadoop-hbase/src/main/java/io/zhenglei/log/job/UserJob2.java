package io.zhenglei.log.job;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import io.zhenglei.log.constants.HbaseConstants;
import io.zhenglei.log.dimetion.String4Boolean2;
import io.zhenglei.log.dimetion.StringStringDimetion;
import io.zhenglei.log.mapper.UserMapper;
import io.zhenglei.log.reducer.UserReducer2;

public class UserJob2 implements Tool {

	Configuration conf;
	
	@Override
	public void setConf(Configuration conf) {
		this.conf = conf;
		conf.addResource("collector.xml");
	}

	@Override
	public Configuration getConf() {
		return conf;
	}

	@Override
	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(getConf());
		job.setJarByClass(UserJob2.class);
		
		job.setMapperClass(UserMapper.class);
		job.setMapOutputKeyClass(StringStringDimetion.class);
		job.setMapOutputValueClass(String4Boolean2.class);
		
		job.setReducerClass(UserReducer2.class);
		job.setOutputKeyClass(StringStringDimetion.class);
		job.setOutputValueClass(String4Boolean2.class);
		
		TableMapReduceUtil.initTableMapperJob(HbaseConstants.TABLE_NAME, getScan(), UserMapper.class, StringStringDimetion.class, String4Boolean2.class, job);
		TableMapReduceUtil.initTableReducerJob(HbaseConstants.TABLE_NAME_USER, UserReducer2.class, job);
		
		if(job.waitForCompletion(true)){
			return 1;
		}
		return 0;
	}
	
	private Scan getScan() {
		Scan scan = new Scan();
		return scan;
	}
	
	public static void main(String[] args) {
		try {
			int i = ToolRunner.run(HBaseConfiguration.create(), new UserJob2(), args);
			System.out.println(i);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

}
