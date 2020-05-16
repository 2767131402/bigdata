package bolt;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.FailedException;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class Bolt implements IRichBolt {

	private OutputCollector collector;
	private int count = 0;
	private int taskId;
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		taskId = context.getThisTaskId();
	}

	@Override
	public void execute(Tuple input) {
		try {
			count++;
			collector.emit(input, new Values(taskId,count));
			collector.ack(input);
		} catch (Exception e) {
			collector.fail(input);
//			throw new FailedException();
		}
	}

	@Override
	public void cleanup() {
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("taskId","count"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

}
