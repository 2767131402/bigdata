package io.zhenglei.log.mapper;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
/**
 * 日志清洗
 * @author ii_zh
 *
 */
public class LogWashMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, NullWritable>.Context context)
			throws IOException, InterruptedException {
		String[] split = value.toString().split("\\?");
		if(split.length>1){
			String ip = split[0].split(" ")[0];
			context.write(new Text("u_ip="+ip+"&"+split[1]), NullWritable.get());
		}
	}
}
