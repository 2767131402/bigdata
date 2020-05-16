package io.zhenglei.bolt1;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

public class SumBolt extends BaseRichBolt {

	/**
	 * maps用于所有从局部发送过来的单词个数
	 */
	private Map<String, Integer> maps = new HashMap<String, Integer>();

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
	}

	@Override
	public void execute(Tuple input) {
		int taskId = input.getInteger(0);
		String word = input.getString(1);
		int count = input.getInteger(2);
		maps.put(word + "_" + taskId, count);

		Map<String, Integer> map = new HashMap<>();
		for (String key : maps.keySet()) {
			String keySplit[] = key.split("_");
			Integer oldCount = map.get(keySplit[0]) == null ? 0 : map.get(keySplit[0]);
			int newCount = maps.get(key) + oldCount;
			map.put(keySplit[0], newCount);
		}
		System.err.println("==============================================");
		for (String key : map.keySet()) {
			System.out.println(key + "   " + map.get(key));
		}
		System.err.println("==============================================");
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {

	}

}
