package io.zhenglei.log.reducer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.mapreduce.Reducer;

import io.zhenglei.log.dimetion.LongLongDimetion;
import io.zhenglei.log.dimetion.StringStringDimetion;

public class BrowserPvReducer extends Reducer<StringStringDimetion, StringStringDimetion, StringStringDimetion, LongLongDimetion> {

	LongLongDimetion dimetion = new LongLongDimetion();
	@Override
	protected void reduce(StringStringDimetion key, Iterable<StringStringDimetion> values,
			Reducer<StringStringDimetion, StringStringDimetion, StringStringDimetion, LongLongDimetion>.Context context)
			throws IOException, InterruptedException {
		Long pvNum = 0L;
		Map<String, Long> map = new HashMap<>();
		for (StringStringDimetion ssd : values) {
			pvNum++;
			Long l = map.get(ssd.getUd());
			if(l==null){
				map.put(ssd.getUd(), 0L);
			}
		}
		
		dimetion.setPvNum(pvNum);
		dimetion.setBrNum(Long.valueOf(map.size()));
		context.write(key, dimetion);
	}

}
