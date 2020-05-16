package io.zhenglei.storm.opaque.transation;

import java.util.Map;

import org.apache.storm.coordination.BatchOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseTransactionalBolt;
import org.apache.storm.transactional.TransactionAttempt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

/**
 * 没一批创建一个对象
 * @author ii_zh
 *
 */
public class TransactionalBolt extends BaseTransactionalBolt {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8609002200408938885L;
	int count = 0;
	BatchOutputCollector collector;
	TransactionAttempt id;
	@Override
	public void prepare(Map conf, TopologyContext context, BatchOutputCollector collector, TransactionAttempt id) {
		this.collector = collector;
		this.id = id;
	}

	@Override
	public void execute(Tuple tuple) {
		count++;
	}

	@Override
	public void finishBatch() {
		collector.emit(new Values(id,count));
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("tx1","count"));
	}

}
