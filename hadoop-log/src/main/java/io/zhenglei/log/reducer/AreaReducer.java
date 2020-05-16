package io.zhenglei.log.reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import io.zhenglei.log.dimetion.StringLongLongDimetion;

public class AreaReducer extends Reducer<Text, Text, StringLongLongDimetion, LongWritable> {
	StringLongLongDimetion dimetion = new StringLongLongDimetion();
	
	@Override
	protected void reduce(Text key, Iterable<Text> values,
			Reducer<Text, Text, StringLongLongDimetion, LongWritable>.Context context)
			throws IOException, InterruptedException {
		
		Map<String, Long> mapTiao = new HashMap<>();
		Map<String, Long> mapSession = new HashMap<>();
		Map<String, List<String>> pvSession = new HashMap<>();
		String[] k = key.toString().split("&");
		for (Text text : values) {
			String[] split = text.toString().split("&");
			Long l = mapTiao.get(split[0]+"&"+split[1]+"&"+k[0]);
			if(l!=null){
				mapTiao.put(split[0]+"&"+split[1]+"&"+k[0], l+1);
			}else{
				mapTiao.put(split[0]+"&"+split[1]+"&"+k[0], 0L);
			}
			
			Long ip = mapSession.get(split[0]+"&"+k[0]);
			if(ip!=null){
				mapSession.put(split[0]+"&"+k[0], ip+1);
			}else{
				mapSession.put(split[0]+"&"+k[0], 0L);
			}
			
			List<String> pv = pvSession.get(split[1]+"&"+k[0]);
			if(pv!=null){
				pv.add(split[2]);
				pvSession.put(split[1]+"&"+k[0], pv);
			}else{
				List<String> pv2 = new ArrayList<>();
				pv2.add(split[2]);
				pvSession.put(split[1]+"&"+k[0], pv2);
			}			
			
		}
		Long tiao = 0L;
		for (Entry<String, Long> text : mapTiao.entrySet()) {
			Long value = text.getValue();
			if(value==1){
				tiao++;
			}
		}
		Long pv = 0L;
		for (Entry<String, List<String>> text : pvSession.entrySet()) {
			List<String> value = text.getValue();
			if(value.size()==1){
				pv++;
			}
		}
		dimetion.setTiaoNum(tiao);
		dimetion.setPvNum(pv);
		dimetion.setTime(k[0]);
		dimetion.setArea(k[1]);
		context.write(dimetion, new LongWritable(mapSession.size()));
		
	}
}
