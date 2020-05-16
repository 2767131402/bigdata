package bolt2;

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

public class Bolt extends BaseBasicBolt {

	private OutputCollector collector;
	private int count = 0;
	private int taskId;
	
	@Override
	public void prepare(Map stormConf, TopologyContext context) {
		this.taskId = context.getThisTaskId();
	}
	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		try {
			count++;
			collector.emit(new Values(taskId,count));
		} catch (Exception e) {
			throw new FailedException();
		}		
	}
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("taskId","count"));
	}

}
