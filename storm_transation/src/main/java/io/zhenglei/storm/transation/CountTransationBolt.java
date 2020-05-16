package io.zhenglei.storm.transation;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.coordination.BatchOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseTransactionalBolt;
import org.apache.storm.transactional.TransactionAttempt;
import org.apache.storm.tuple.Tuple;

import io.zhenglei.storm.domain.Mystack;

public class CountTransationBolt extends BaseTransactionalBolt {

	static String GLOBALKEY = "key";
	static Map<String, Mystack> maps = new HashMap<>();
	int count = 0;
	TransactionAttempt id;
	
	@Override
	public void prepare(Map conf, TopologyContext context, BatchOutputCollector collector, TransactionAttempt id) {
		this.id = id;
	}

	@Override
	public void execute(Tuple tuple) {
		Integer i = tuple.getInteger(1);
		count += i;
	}

	@Override
	public void finishBatch() {
		Mystack mystack = maps.get(GLOBALKEY);
		if(mystack==null || mystack.txId != id.getTransactionId().intValue()){
			if (mystack == null) {
				mystack = new Mystack();				
			}
			mystack.count = mystack.count+count;
			mystack.txId = id.getTransactionId().intValue();
//			mystack.setCount(mystack.getCount()+count);
//			mystack.setTxId(mystack.getTxId());
		}
		maps.put(GLOBALKEY, mystack);
		System.err.println(mystack.count+"  ===============================");
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {

	}

}
