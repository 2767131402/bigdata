package io.zhenglei.hadoop.partitioner;

import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.mapreduce.Partitioner;

import io.zhenglei.hadoop.dimetion.DatePhoneDimetion;
import io.zhenglei.hadoop.dimetion.UpDownDimetion;

public class MyPartitioner extends Partitioner<DatePhoneDimetion, UpDownDimetion> {

	private Map<String, Integer> map = new HashMap<>();
	public MyPartitioner(){
		map.put("13", 0);
		map.put("15", 1);
		map.put("18", 2);
		map.put("84", 3);
	}
	
	@Override
	public int getPartition(DatePhoneDimetion key, UpDownDimetion value, int numPartitions) {
		String keyValue = key.getMobilePhone().substring(0, 2);
		Integer val = map.get(keyValue);
		if(val != null){
			return val;
		}else{
			return 0;
		}
	}

}
