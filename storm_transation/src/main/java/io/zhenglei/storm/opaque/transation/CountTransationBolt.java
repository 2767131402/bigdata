package io.zhenglei.storm.opaque.transation;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.coordination.BatchOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseTransactionalBolt;
import org.apache.storm.transactional.ICommitter;
import org.apache.storm.transactional.TransactionAttempt;
import org.apache.storm.tuple.Tuple;

public class CountTransationBolt extends BaseTransactionalBolt implements ICommitter {

	private int count = 0;
	private String KEY = "key";
	private BatchOutputCollector collector;
	private TransactionAttempt id;
	static Map<String, PVValues> maps = new HashMap<>();
	
	@Override
	public void prepare(Map conf, TopologyContext context, BatchOutputCollector collector, TransactionAttempt id) {
		this.collector = collector;
		this.id = id;
	}

	@Override
	public void execute(Tuple tuple) {
		count += tuple.getInteger(1);
	}

	@Override
	public void finishBatch() {
		PVValues pvValues = maps.get(KEY);
		if(pvValues==null || pvValues.getTxid()!=id.getTransactionId().intValue()){
			if(pvValues==null){
				pvValues = new PVValues();
			}
			pvValues.setTxid(id.getTransactionId().intValue());
			pvValues.setCount(pvValues.getCount()+count);
			pvValues.setPre(pvValues.getCount());
		}else{
			pvValues.setPre(pvValues.getCount()+count);
		}
		maps.put(KEY, pvValues);
		System.out.println(pvValues.getCount()+"\t"+"============");
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
	}

}
