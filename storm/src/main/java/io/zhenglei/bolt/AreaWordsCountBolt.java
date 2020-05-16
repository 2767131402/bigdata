package io.zhenglei.bolt;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class AreaWordsCountBolt extends BaseRichBolt {
	private OutputCollector collector;
	private Map<String, Integer> map;
	
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		map = new HashMap<>();
	}

	@Override
	public void execute(Tuple input) {
		String s = input.getString(0);
		int old = map.get(s)==null?0:map.get(s);
		map.put(s, old+1);
		collector.emit(new Values(s,old+1));
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("wordkey","wordcount"));
	}

}
