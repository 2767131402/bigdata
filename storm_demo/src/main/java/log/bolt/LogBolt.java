package log.bolt;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class LogBolt extends BaseRichBolt {

	private int count = 0;
	private int taskId;
	private OutputCollector collector;
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.taskId = context.getThisTaskId();
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		count++;
		collector.emit(new Values(taskId,count));
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("taskId","count"));
	}

}
