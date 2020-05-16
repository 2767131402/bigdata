package log.bolt;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

public class LogBolt2 extends BaseRichBolt {

	private Map<Integer, Integer> map = new HashMap<>();
	
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		
	}

	@Override
	public void execute(Tuple input) {
		int taskId = input.getInteger(0);
		int count = input.getInteger(1);
		map.put(taskId, count);
		int sum = 0;
		for (int i : map.values()) {
			sum+=i;
		}
		System.err.println(sum);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		
	}

}
