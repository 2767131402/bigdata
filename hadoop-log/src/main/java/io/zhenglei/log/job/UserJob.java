package io.zhenglei.log.job;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import io.zhenglei.log.dimetion.TimeUudDimetion;
import io.zhenglei.log.dimetion.UserOutputDimetion;
import io.zhenglei.log.jdbc.OutPutUserFormat;
import io.zhenglei.log.mapper.UserFormatMapper;
import io.zhenglei.log.reducer.UserFormatReducer;

public class UserJob implements Tool {
	
	public static void main(String[] args) {
		try {
			ToolRunner.run(new UserJob(), args);
			System.out.println("UserJob end...");
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
		job.setJarByClass(UserJob.class);
		
		job.setMapperClass(UserFormatMapper.class);
		job.setMapOutputKeyClass(TimeUudDimetion.class);
		job.setMapOutputValueClass(UserOutputDimetion.class);
		
		job.setReducerClass(UserFormatReducer.class);
		job.setOutputKeyClass(TimeUudDimetion.class);
		job.setOutputValueClass(UserOutputDimetion.class);
		//存储到其他数据库
		job.setOutputFormatClass(OutPutUserFormat.class);
		
		FileInputFormat.setInputPaths(job, new Path("hdfs://192.168.44.132:9000/log/washlog"));
		
		if(job.waitForCompletion(true)){
			return 1;
		}
		return 0;
	}

}
