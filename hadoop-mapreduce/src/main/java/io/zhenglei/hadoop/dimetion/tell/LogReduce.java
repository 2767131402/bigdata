package io.zhenglei.hadoop.dimetion.tell;

import java.io.IOException;

import org.apache.hadoop.mapreduce.Reducer;

import io.zhenglei.hadoop.dimetion.DatePhoneDimetion;
import io.zhenglei.hadoop.dimetion.UpDownDimetion;

/**
 * 前两个：输出结果key，value的类型
 */
public class LogReduce extends Reducer<DatePhoneDimetion, UpDownDimetion, DatePhoneDimetion, UpDownDimetion> {
	
	@Override
	protected void reduce(DatePhoneDimetion key, Iterable<UpDownDimetion> values,
			Reducer<DatePhoneDimetion, UpDownDimetion, DatePhoneDimetion, UpDownDimetion>.Context context)
			throws IOException, InterruptedException {
		long upSum = 0L;
		long downSum = 0L;
		for (UpDownDimetion ud : values) {
			upSum += ud.getUpPayLoad();
			downSum += ud.getDownPayLoad();
		}
		context.write(key, new UpDownDimetion(upSum, downSum));
	}
}
