package io.zhenglei.hadoop.dimetion.tell;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import io.zhenglei.hadoop.dimetion.DatePhoneDimetion;
import io.zhenglei.hadoop.dimetion.UpDownDimetion;
import io.zhenglei.hadoop.jdbc.MySqlOutPutFormat;
import io.zhenglei.hadoop.partitioner.MyPartitioner;
 
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
		job.setJarByClass(Jobs.class);
		
		job.setMapperClass(LogMapper.class);
		job.setMapOutputKeyClass(DatePhoneDimetion.class);
		job.setMapOutputValueClass(UpDownDimetion.class);
		
		job.setReducerClass(LogReduce.class);
		job.setOutputKeyClass(DatePhoneDimetion.class);
		job.setOutputValueClass(UpDownDimetion.class);
//		/**
//		 * 实现分区
//		 * 将结果分开存储
//		 */
//		job.setPartitionerClass(MyPartitioner.class);
//		job.setNumReduceTasks(4);
		//存储到其他数据库
		job.setOutputFormatClass(MySqlOutPutFormat.class);
		
		FileInputFormat.setInputPaths(job, new Path("hdfs://192.168.44.132:9000/test/mobile.dat"));
		
		FileOutputFormat.setOutputPath(job, new Path("hdfs://192.168.44.132:9000/test2/mobile"));	
		if(job.waitForCompletion(true)) {
			return 1;
		}
		return 0;
	}

}
