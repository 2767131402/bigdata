package io.zhenglei.storm.kafka;

import java.util.Map;
import java.util.Queue;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

public class KafkaSpout extends BaseRichSpout {

	private String topic;
	public KafkaSpout(String topic) {
		this.topic = topic;
	}
	
	private Queue<String> queue;
	private SpoutOutputCollector collector;
	private int taskId;
	private KafkaConsumer consumer;
	
	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		consumer = new KafkaConsumer(topic);
		consumer.start();
		this.collector = collector;
		taskId = context.getThisTaskId();
		queue = consumer.getQueue();
	}

	@Override
	public void nextTuple() {
		if(queue.size()>0){
			String message = queue.poll();
			collector.emit(new Values(message));
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("message"));
	}

}
