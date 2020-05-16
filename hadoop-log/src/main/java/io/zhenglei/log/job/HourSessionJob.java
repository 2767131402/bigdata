package io.zhenglei.log.job;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import io.zhenglei.log.dimetion.SessionUserMinMaxDimetion;
import io.zhenglei.log.dimetion.UsdUudTimeDimetion;
import io.zhenglei.log.jdbc.OutPutHourSessionFormat;
import io.zhenglei.log.mapper.LogHourSessionMapper;
import io.zhenglei.log.reducer.LogHourSessionReducer;

public class HourSessionJob implements Tool {
	public static void main(String[] args) {
		try {
			ToolRunner.run(new HourSessionJob(), args);
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
		job.setJarByClass(HourSessionJob.class);
		
		job.setMapperClass(LogHourSessionMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(UsdUudTimeDimetion.class);
		
		job.setReducerClass(LogHourSessionReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(SessionUserMinMaxDimetion.class);
		//存储到其他数据库
		job.setOutputFormatClass(OutPutHourSessionFormat.class);
		
		FileInputFormat.setInputPaths(job, new Path("hdfs://192.168.44.132:9000/log/washlog"));
		
		if(job.waitForCompletion(true)){
			return 1;
		}
		return 0;
	}

}
