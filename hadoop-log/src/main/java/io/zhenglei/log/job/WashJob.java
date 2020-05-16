package io.zhenglei.log.job;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import io.zhenglei.log.mapper.LogWashMapper;
import io.zhenglei.log.partitioner.LogWashPartitioner;
import io.zhenglei.log.utils.PartitionUtils;

public class WashJob implements Tool {
	
	public static void main(String[] args) {
		try {
			ToolRunner.run(new WashJob(), args);
			System.out.println("WashJob end...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Configuration conf;
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
		Job job = Job.getInstance(getConf());
		job.setJarByClass(WashJob.class);
		
		job.setMapperClass(LogWashMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(NullWritable.class);
		
		/**
		 * 实现分区
		 * 将结果分开存储
		 */
		job.setPartitionerClass(LogWashPartitioner.class);
		job.setNumReduceTasks(PartitionUtils.getInstance().size());
		
		FileInputFormat.setInputPaths(job, new Path("hdfs://192.168.44.132:9000/log/localhost_access_log.*.txt"));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://192.168.44.132:9000/log/washlog"));
		
		if(job.waitForCompletion(true)){
			return 1;
		}
		return 0;
	}

}
