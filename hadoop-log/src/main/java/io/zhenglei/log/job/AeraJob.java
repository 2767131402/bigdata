package io.zhenglei.log.job;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import io.zhenglei.log.dimetion.StringLongLongDimetion;
import io.zhenglei.log.jdbc.AreaFormat;
import io.zhenglei.log.mapper.AreaMapper;
import io.zhenglei.log.reducer.AreaReducer;

public class AeraJob implements Tool {
	
	public static void main(String[] args) {
		try {
			ToolRunner.run(new AeraJob(), args);
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
		job.setJarByClass(AeraJob.class);
		
		job.setMapperClass(AreaMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setReducerClass(AreaReducer.class);
		job.setOutputKeyClass(StringLongLongDimetion.class);
		job.setOutputValueClass(LongWritable.class);
		//存储到其他数据库
		job.setOutputFormatClass(AreaFormat.class);
		
		FileInputFormat.setInputPaths(job, new Path("hdfs://192.168.44.132:9000/log/washlog"));
		
		if(job.waitForCompletion(true)){
			return 1;
		}
		return 0;
	}

}
