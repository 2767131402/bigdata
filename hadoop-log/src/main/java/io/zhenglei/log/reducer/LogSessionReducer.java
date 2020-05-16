package io.zhenglei.log.reducer;

import java.io.IOException;

import org.apache.hadoop.mapreduce.Reducer;

import io.zhenglei.log.dimetion.LongStringDimetion;
import io.zhenglei.log.dimetion.TimeUsdDimetion;


public class LogSessionReducer extends Reducer<TimeUsdDimetion, LongStringDimetion, TimeUsdDimetion, LongStringDimetion> {
	LongStringDimetion dimetion = new LongStringDimetion();
	@Override
	protected void reduce(TimeUsdDimetion key, Iterable<LongStringDimetion> values,
			Reducer<TimeUsdDimetion, LongStringDimetion, TimeUsdDimetion, LongStringDimetion>.Context context)
			throws IOException, InterruptedException {
		Long minTime = 0L;
		Long maxTime = 0L;
		int i = 1;
		for (LongStringDimetion lw : values) {
			if(i==1){
				minTime = lw.getTime();
				maxTime = lw.getTime();
			}else{
				if(lw.getTime()>maxTime){
					maxTime = lw.getTime();
				}
				if(lw.getTime()<minTime){
					minTime = lw.getTime();
				}
			}
			i++;
			dimetion.setBrowser(lw.getBrowser());
		}
		dimetion.setTime(maxTime-minTime);
		context.write(key, dimetion);
	}
}
