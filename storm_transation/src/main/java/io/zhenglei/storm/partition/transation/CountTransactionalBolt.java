package io.zhenglei.storm.partition.transation;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.coordination.BatchOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseTransactionalBolt;
import org.apache.storm.transactional.ICommitter;
import org.apache.storm.transactional.TransactionAttempt;
import org.apache.storm.tuple.Tuple;

import io.zhenglei.storm.domain.Mystack;

public class CountTransactionalBolt extends BaseTransactionalBolt implements ICommitter {

	BatchOutputCollector collector;
	TransactionAttempt id;
	Map<String, Integer> map = new HashMap<>();
	static Map<String, Mystack> maps = new HashMap<>();
	int count = 0;
	
	@Override
	public void prepare(Map conf, TopologyContext context, BatchOutputCollector collector, TransactionAttempt id) {
		this.id = id;
		this.collector = collector;
	}

	@Override
	public void execute(Tuple tuple) {
		String date = tuple.getString(1);
		int count = tuple.getInteger(2);
		int oldCount = map.get(date)==null?0:map.get(date);
		int newCount = oldCount + count;
		map.put(date, newCount);
	}

	@Override
	public void finishBatch() {
		for (String key : map.keySet()) {
			Mystack mystack = maps.get(key);
			if(mystack==null || mystack.txId != id.getTransactionId().intValue()){
				if(mystack==null){
					mystack = new Mystack();
				}
				mystack.count = mystack.count + map.get(key);
				mystack.txId = id.getTransactionId().intValue();
			}
			maps.put(key, mystack);
			System.out.println(key+"\t"+mystack.count);
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
	}

}
