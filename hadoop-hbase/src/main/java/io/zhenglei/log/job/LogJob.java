package io.zhenglei.log.job;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import io.zhenglei.log.constants.HbaseConstants;
import io.zhenglei.log.mapper.LogMapper;

public class LogJob implements Tool {

	Configuration conf;
	
	@Override
	public void setConf(Configuration conf) {
		this.conf = conf;
		conf.addResource("collector.xml");
//		conf.set(HbaseConstants.FS_DEFAULTFS, conf.get(HbaseConstants.FS_DEFAULTFS));
//		conf.set(HbaseConstants.YARN_RESOURCEMANAGER_HOSTNAME, conf.get(HbaseConstants.YARN_RESOURCEMANAGER_HOSTNAME));
//		conf.set(HbaseConstants.HBASE_ZOOKEEPER_QUORUM, conf.get(HbaseConstants.HBASE_ZOOKEEPER_QUORUM));
	}

	@Override
	public Configuration getConf() {
		return conf;
	}

	@Override
	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(conf);
		job.setJarByClass(LogJob.class);
		
		job.setMapperClass(LogMapper.class);
		job.setMapOutputKeyClass(NullWritable.class);
		job.setMapOutputValueClass(Put.class);
		
		TableMapReduceUtil.initTableReducerJob(HbaseConstants.TABLE_NAME, null, job);
		FileInputFormat.setInputPaths(job, new Path(conf.get(HbaseConstants.FS_DEFAULTFS)+"/log/"));
		
		if(job.waitForCompletion(true)){
			return 1;
		}
		
		return 0;
	}
	
	public static void main(String[] args) {
		try {
			int i = ToolRunner.run(HBaseConfiguration.create(), new LogJob(), args);
			System.out.println(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
