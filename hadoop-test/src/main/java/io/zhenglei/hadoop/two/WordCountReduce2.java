package io.zhenglei.hadoop.two;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import io.zhenglei.hadoop.dimetion.WordSortDimetion;

/**
 * 前两个：输出结果key，value的类型
 */
public class WordCountReduce2 extends Reducer<Text, WordSortDimetion, Text, Text> {
	
	@Override
	protected void reduce(Text key, Iterable<WordSortDimetion> values,
			Reducer<Text, WordSortDimetion, Text, Text>.Context context) throws IOException, InterruptedException {
		String s = "";
		for (WordSortDimetion ws : values) {
			s += ws.getArticle()+"-->"+ws.getCount()+"\t";
		}
		context.write(key, new Text(s));
	}
}
