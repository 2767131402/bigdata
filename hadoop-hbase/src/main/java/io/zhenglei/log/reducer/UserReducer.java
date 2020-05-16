package io.zhenglei.log.reducer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import io.zhenglei.log.dimetion.StringStringDimetion;

public class UserReducer extends Reducer<StringStringDimetion, Text, StringStringDimetion, LongWritable> {
	@Override
	protected void reduce(StringStringDimetion key, Iterable<Text> values,
			Reducer<StringStringDimetion, Text, StringStringDimetion, LongWritable>.Context context)
			throws IOException, InterruptedException {
		Set<Text> list = new HashSet<>();
		for (Text text : values) {
			list.add(text);
		}
		context.write(key, new LongWritable(list.size()));
	}
}
