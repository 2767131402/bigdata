package io.zhenglei.hadoop;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import io.zhenglei.hadoop.dimetion.WordArticleDimetion;

public class WordCountMapper extends Mapper<LongWritable, Text, WordArticleDimetion, LongWritable> {
	WordArticleDimetion dimetion = new WordArticleDimetion();
	
	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, WordArticleDimetion, LongWritable>.Context context)
			throws IOException, InterruptedException {
		
		String[] words = value.toString().split(" ");
		FileSplit inputSplit = (FileSplit) context.getInputSplit();
		String name = inputSplit.getPath().getName();
		for (String s : words) {
			dimetion.setWord(s);
			dimetion.setArticle(name);
			context.write(dimetion, new LongWritable(1));
		}
	}
}
