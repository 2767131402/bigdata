package io.zhenglei.log.reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import io.zhenglei.log.dimetion.SessionUserMinMaxDimetion;
import io.zhenglei.log.dimetion.UsdUudTimeDimetion;

public class LogHourSessionReducer extends Reducer<Text, UsdUudTimeDimetion, Text, SessionUserMinMaxDimetion> {
	SessionUserMinMaxDimetion dimetion = new SessionUserMinMaxDimetion();
	
	@Override
	protected void reduce(Text key, Iterable<UsdUudTimeDimetion> values,
			Reducer<Text, UsdUudTimeDimetion, Text, SessionUserMinMaxDimetion>.Context context)
			throws IOException, InterruptedException {
		List<String> usdList = new ArrayList<>();
		List<String> userList = new ArrayList<>();
		List<Long> longList = new ArrayList<>();
		Map<String, Long> max = new HashMap<String, Long>();
		Map<String, Long> min = new HashMap<String, Long>();
		for (UsdUudTimeDimetion uutd : values) {
			
			if(!usdList.contains(uutd.getUsd())){
				usdList.add(uutd.getUsd());
			}
			
			if(!userList.contains(uutd.getUud())){
				userList.add(uutd.getUud());
			}
			
			Long ma = max.get(uutd.getUsd());
			if(ma!=null){
				if(uutd.getTime()>ma){
					max.put(uutd.getUsd(), uutd.getTime());
				}
			}else{
				max.put(uutd.getUsd(), uutd.getTime());
			}
			
			Long mi = min.get(uutd.getUsd());
			if(mi!=null){
				if(uutd.getTime()<mi){
					min.put(uutd.getUsd(), uutd.getTime());
				}
			}else{
				min.put(uutd.getUsd(), uutd.getTime());
			}

		}
		for (Entry<String, Long> s : max.entrySet()) {
			Long a = min.get(s.getKey());
			Long b = max.get(s.getKey());
			Long c = b-a;
			longList.add(c);
		}
		Long sum = 0L;
		for (Long l : longList) {
			sum = sum + l;
		}
		dimetion.setSum(sum);
		dimetion.setAvg(sum/longList.size()*1.0);
		dimetion.setSessionSize(Long.valueOf(usdList.size()));
		dimetion.setUserSize(Long.valueOf(userList.size()));
		dimetion.setMaxSession(Collections.max(longList));
		dimetion.setMinSession(Collections.min(longList));
		context.write(key, dimetion);
	}
}
