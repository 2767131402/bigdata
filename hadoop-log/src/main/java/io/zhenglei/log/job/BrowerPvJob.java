package io.zhenglei.log.job;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import io.zhenglei.log.dimetion.LongLongDimetion;
import io.zhenglei.log.dimetion.StringStringDimetion;
import io.zhenglei.log.jdbc.OutPutBrowserPvFormat;
import io.zhenglei.log.mapper.BrowserPvMapper;
import io.zhenglei.log.reducer.BrowserPvReducer;

public class BrowerPvJob implements Tool {
	public static void main(String[] args) {
		try {
			ToolRunner.run(new BrowerPvJob(), args);
			System.out.println("SessionJob end...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Configuration conf;
	@Override
	public void setConf(Configuration conf) {
		this.conf = conf;
		//加载自己的配置文件
		conf.addResource("jdbc.xml");
		conf.addResource("output-collector.xml");
		conf.addResource("query-mapping.xml");
	}

	@Override
	public Configuration getConf() {
		return conf;
	}

	@Override
	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(getConf());
		job.setJarByClass(BrowerPvJob.class);
		
		job.setMapperClass(BrowserPvMapper.class);
		job.setMapOutputKeyClass(StringStringDimetion.class);
		job.setMapOutputValueClass(StringStringDimetion.class);
		
		job.setReducerClass(BrowserPvReducer.class);
		job.setOutputKeyClass(StringStringDimetion.class);
		job.setOutputValueClass(LongLongDimetion.class);
		//存储到其他数据库
		job.setOutputFormatClass(OutPutBrowserPvFormat.class);
		
		FileInputFormat.setInputPaths(job, new Path("hdfs://192.168.44.132:9000/log/washlog/part-r-00000"));
		
		if(job.waitForCompletion(true)){
			return 1;
		}
		return 0;
	}

}
