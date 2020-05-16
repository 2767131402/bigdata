package io.zhenglei.storm.kafka2;

import java.util.Map;
import java.util.Queue;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.spout.ITridentSpout;
import org.apache.storm.trident.topology.TransactionAttempt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import io.zhenglei.storm.kafka.KafkaConsumer;

public class MyKafkaSpout implements ITridentSpout<MyData> {

	private int BACH_NUM = 10;
	private Queue<String> queue;
	private KafkaConsumer consumer;
	private String topic;
	public MyKafkaSpout(String topic) {
		this.topic = topic;
		consumer = new KafkaConsumer(topic);
		consumer.start();
		queue = consumer.getQueue();
	}
	
	@Override
	public org.apache.storm.trident.spout.ITridentSpout.BatchCoordinator<MyData> getCoordinator(String txStateId,
			Map conf, TopologyContext context) {
		
		return new BatchCoordinator<MyData>() {
			
			@Override
			public void success(long txid) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isReady(long txid) {
				Utils.sleep(1000);
				return true;
			}
			
			@Override
			public MyData initializeTransaction(long txid, MyData prevMetadata, MyData currMetadata) {
				int startPoint = 0;
				if(prevMetadata!=null){
					startPoint = prevMetadata.getStartPoint() + prevMetadata.getNum();
				}
				return new MyData(BACH_NUM, startPoint);
			}
			
			@Override
			public void close() {
				// TODO Auto-generated method stub
				
			}
		};
	}

	@Override
	public org.apache.storm.trident.spout.ITridentSpout.Emitter<MyData> getEmitter(String txStateId, Map conf,
			TopologyContext context) {
		
		return new Emitter<MyData>() {
			
			@Override
			public void success(TransactionAttempt tx) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void emitBatch(TransactionAttempt tx, MyData coordinatorMeta, TridentCollector collector) {
				for (int i = coordinatorMeta.getStartPoint(); i < coordinatorMeta.getStartPoint()+coordinatorMeta.getNum(); i++) {
					if(queue.poll()==null){
						return;
					}
					collector.emit(new Values(tx,queue.poll()));
				}
			}
			
			@Override
			public void close() {
				// TODO Auto-generated method stub
				
			}
		};
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fields getOutputFields() {
		return new Fields("tx","message");
	}

}
