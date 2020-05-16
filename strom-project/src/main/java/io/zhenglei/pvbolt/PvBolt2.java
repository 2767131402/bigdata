package io.zhenglei.pvbolt;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class PvBolt2 extends BaseRichBolt {
	private OutputCollector collector;
	private Map<String, Integer> map = new HashMap<>();

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		String session = input.getString(0);
//		String time = input.getString(1);
		int old = map.get(session) == null ? 0 : map.get(session);
		int count = input.getInteger(2);
		int n = old + count;
		map.put(session, n);
		collector.emit(new Values(session,n));
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("sess","ncount"));
	}

}
