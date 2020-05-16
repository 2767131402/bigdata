package io.zhenglei.log.reducer;

import java.io.IOException;

import org.apache.hadoop.mapreduce.Reducer;

import io.zhenglei.log.dimetion.TimeUudDimetion;
import io.zhenglei.log.dimetion.UserOutputDimetion;

public class UserFormatReducer extends Reducer<TimeUudDimetion, UserOutputDimetion, TimeUudDimetion, UserOutputDimetion> {

	@Override
	protected void reduce(TimeUudDimetion key, Iterable<UserOutputDimetion> values,
			Reducer<TimeUudDimetion, UserOutputDimetion, TimeUudDimetion, UserOutputDimetion>.Context context)
			throws IOException, InterruptedException {
		for (UserOutputDimetion userOutputDimetion : values) {
			context.write(key, userOutputDimetion);
		}
	}
}
