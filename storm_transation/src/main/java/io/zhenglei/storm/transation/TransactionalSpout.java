package io.zhenglei.storm.transation;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.storm.coordination.BatchOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseTransactionalSpout;
import org.apache.storm.transactional.TransactionAttempt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import io.zhenglei.storm.domain.MateData;

/**
 * 批量发送
 * 已经实现自己的ack机制
 * @author ii_zh
 *
 */
public class TransactionalSpout extends BaseTransactionalSpout<MateData> {

	int BATCH_NUM = 10;
	Map<Integer, String> maps = new HashMap<>();
	public TransactionalSpout() {
		// TODO Auto-generated constructor stub
		Random random = new Random();
		String hosts = "www.taobao.com";
		String sessionid[] = { "AADJJDDJJSFSLDKGIGIG334S", "ADKFLSDKDIFIFIFI3563333", "DKSDLDAFKASDKFSLLDFKLD334",
				"KDFLSFDSLDFKXNCXCVNE342K", "DSFASDLCXKVLZCVNLSKDFK453" };
		String times[] = { "2018-01-05 10:52:00", "2018-01-05 10:54:00", "2018-01-05 10:55:00", "2018-01-05 10:56:00",
				"2018-01-05 10:58:00", "2018-01-05 10:59:00" };
		for (int i = 0; i < 100; i++) {
			maps.put(i, hosts + "\t" + sessionid[random.nextInt(sessionid.length)] + "\t"
					+ times[random.nextInt(times.length)]);
		}
	}

	/**
	 * 元数据
	 */
	@Override
	public org.apache.storm.transactional.ITransactionalSpout.Coordinator<MateData> getCoordinator(Map conf,
			TopologyContext context) {
		return new MyCoordinator();
	}

	class MyCoordinator implements org.apache.storm.transactional.ITransactionalSpout.Coordinator<MateData> {
		@Override
		public MateData initializeTransaction(BigInteger txid, MateData prevMetadata) {
			int startPoint = 0;
			if (prevMetadata != null) {
				startPoint = prevMetadata.getStartPoint() + prevMetadata.getBach_num();
			}
			return new MateData(startPoint, BATCH_NUM);
		}
		/**
		 * 准备好批量
		 */
		@Override
		public boolean isReady() {
			Utils.sleep(1000);
			return true;
		}
		@Override
		public void close() {
		}

	}
	@Override
	public org.apache.storm.transactional.ITransactionalSpout.Emitter<MateData> getEmitter(Map conf,
			TopologyContext context) {	
		return new MyEmitter();
	}
	class MyEmitter implements org.apache.storm.transactional.ITransactionalSpout.Emitter<MateData> {
		@Override
		public void emitBatch(TransactionAttempt tx, MateData coordinatorMeta, BatchOutputCollector collector) {
			int end = coordinatorMeta.getStartPoint() + coordinatorMeta.getBach_num();
			for (int i = coordinatorMeta.getStartPoint(); i < end; i++) {
				if(maps.get(i)==null){
					return;
				}
				String log = maps.get(i);
				collector.emit(new Values(tx, log));
			}
		}

		@Override
		public void cleanupBefore(BigInteger txid) {

		}

		@Override
		public void close() {
			// TODO Auto-generated method stub

		}

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("tx", "log"));
	}

}
