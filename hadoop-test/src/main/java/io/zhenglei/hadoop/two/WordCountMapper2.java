package io.zhenglei.hadoop.two;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import io.zhenglei.hadoop.dimetion.WordSortDimetion;

public class WordCountMapper2 extends Mapper<LongWritable, Text, Text, WordSortDimetion> {
	WordSortDimetion dimetion = new WordSortDimetion();
	
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, WordSortDimetion>.Context context)
			throws IOException, InterruptedException {
		String[] words = value.toString().split("\t");
		dimetion.setArticle(words[1]);
		dimetion.setCount(Long.parseLong(words[2]));
		context.write(new Text(words[0]), dimetion);
	}
}
