package io.zhenglei.log.job;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import io.zhenglei.log.constants.HbaseConstants;
import io.zhenglei.log.dimetion.StringStringDimetion;
import io.zhenglei.log.mapper.UserMapper;
import io.zhenglei.log.reducer.UserReducer;

public class UserJob implements Tool {

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
		job.setJarByClass(UserJob.class);
		
		job.setMapperClass(UserMapper.class);
		job.setMapOutputKeyClass(StringStringDimetion.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setReducerClass(UserReducer.class);
		job.setOutputKeyClass(StringStringDimetion.class);
		job.setOutputValueClass(LongWritable.class);
		
		TableMapReduceUtil.initTableMapperJob(HbaseConstants.TABLE_NAME, getScan(), UserMapper.class, StringStringDimetion.class, Text.class, job);
		FileOutputFormat.setOutputPath(job, new Path(conf.get(HbaseConstants.FS_DEFAULTFS)+"/one"));
		
		if(job.waitForCompletion(true)){
			return 1;
		}
		return 0;
	}
	
	private Scan getScan() {
		Scan scan = new Scan();
//		PrefixFilter prefixFilter = new PrefixFilter(Bytes.toBytes(args[0]));
//		scan.setFilter(prefixFilter);
		return scan;
	}
	
	public static void main(String[] args) {
		try {
			int i = ToolRunner.run(HBaseConfiguration.create(), new UserJob(), args);
			System.out.println(i);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

}
