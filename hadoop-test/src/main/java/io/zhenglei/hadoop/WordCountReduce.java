package io.zhenglei.hadoop;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Reducer;

import io.zhenglei.hadoop.dimetion.WordArticleDimetion;

/**
 * 前两个：输出结果key，value的类型
 */
public class WordCountReduce extends Reducer<WordArticleDimetion, LongWritable, WordArticleDimetion, LongWritable> {
	
	@Override
	protected void reduce(WordArticleDimetion arg0, Iterable<LongWritable> arg1,
			Reducer<WordArticleDimetion, LongWritable, WordArticleDimetion, LongWritable>.Context context)
			throws IOException, InterruptedException {
		long sum = 0L;
		for (LongWritable l : arg1) {
			sum += l.get();
		}
		context.write(arg0, new LongWritable(sum));
	}
}
