package bolt;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.FailedException;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class Bolt2 implements IRichBolt {

	private Map<Integer, Integer> map = new HashMap<>();
	private OutputCollector collector;
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		try {
			int taskId = input.getInteger(0);
			int count = input.getInteger(1);
			map.put(taskId, count);
			int sum = 0;
			for (Integer s : map.values()) {
				sum += s;
			}
			System.err.println(sum);
			Integer.parseInt("sdv");
			collector.ack(input);
		} catch (Exception e) {
			collector.fail(input);//调用spout的fail方法
//			throw new FailedException();
		}		
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}
