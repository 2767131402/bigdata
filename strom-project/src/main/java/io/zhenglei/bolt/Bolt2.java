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

public class Bolt2 extends BaseRichBolt {
	private OutputCollector collector;
	private Map<String, Integer> map = new HashMap<>();

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		String s = input.getString(0);
		int old = map.get(s) == null ? 0 : map.get(s);
		int count = input.getInteger(1);
		int n = old + count;
		map.put(s, n);
		collector.emit(new Values(s,n));
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("time","ncount"));
	}

}
