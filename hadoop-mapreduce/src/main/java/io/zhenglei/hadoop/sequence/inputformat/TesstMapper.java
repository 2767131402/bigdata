package io.zhenglei.hadoop.sequence.inputformat;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TesstMapper extends Mapper<IntWritable, Text,Text, LongWritable> {
	@Override
	protected void map(IntWritable key, Text value, Mapper<IntWritable, Text, Text, LongWritable>.Context context)
			throws IOException, InterruptedException {
		context.write(value, new LongWritable(key.get()));
	}
}
