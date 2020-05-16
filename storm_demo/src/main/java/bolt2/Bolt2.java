package bolt2;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.FailedException;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

/**
 * BaseBasicBolt 自动调用fail方法
 * 
 * @author ii_zh
 *
 */
public class Bolt2 extends BaseBasicBolt {

	private Map<Integer, Integer> map = new HashMap<>();

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		try {
			int taskId = input.getInteger(0);
			int count = input.getInteger(1);
			map.put(taskId, count);
			int sum = 0;
			for (Integer s : map.values()) {
				sum += s;
			}
			System.err.println(sum+"\t"+taskId);
		} catch (Exception e) {
			throw new FailedException();
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
	}

}
