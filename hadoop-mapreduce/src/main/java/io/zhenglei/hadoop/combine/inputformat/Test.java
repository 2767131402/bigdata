package io.zhenglei.hadoop.combine.inputformat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(Test.class);
		job.setInputFormatClass(MyCombinedFilesInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		job.setMapperClass(TesstMapper.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);		
		FileInputFormat.setInputPaths(job, new Path("hdfs://192.168.8.101:9000/"));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://192.168.8.101:9000/urltest"));
		if (job.waitForCompletion(true)) {
			System.out.println("xxxxxxxxxxxxxxx");
		}
	}

}
