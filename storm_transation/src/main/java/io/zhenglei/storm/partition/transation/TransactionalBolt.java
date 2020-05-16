package io.zhenglei.storm.partition.transation;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.coordination.BatchOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseTransactionalBolt;
import org.apache.storm.transactional.TransactionAttempt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import scala.reflect.ValDef;

public class TransactionalBolt extends BaseTransactionalBolt {

	BatchOutputCollector collector;
	TransactionAttempt id;
	Map<String, Integer> map = new HashMap<>();
	
	@Override
	public void prepare(Map conf, TopologyContext context, BatchOutputCollector collector, TransactionAttempt id) {
		this.id = id;
		this.collector = collector;
	}

	@Override
	public void execute(Tuple tuple) {
		String[] split = tuple.getString(1).split("\t");
		int oldCount = map.get(split[2])==null?0:map.get(split[2]);
		int newCount = oldCount+1;
		map.put(split[2], newCount);		
	}

	@Override
	public void finishBatch() {
		for (String m : map.keySet()) {
			collector.emit(new Values(id,m,map.get(m)));
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("tx1","time","count"));
	}

}
