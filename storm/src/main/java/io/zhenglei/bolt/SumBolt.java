package io.zhenglei.bolt;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

public class SumBolt extends BaseRichBolt {

	private Map<String, Integer> map;
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		map = new HashMap<>();
	}

	@Override
	public void execute(Tuple input) {
		String s = input.getString(0);
		Integer i = input.getInteger(1);
		map.put(s, i);
		System.out.println("========================================");
		for (String str : map.keySet()) {
			System.err.println(str+"\t"+map.get(str));
		}
		System.out.println("========================================");
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		
	}

}
