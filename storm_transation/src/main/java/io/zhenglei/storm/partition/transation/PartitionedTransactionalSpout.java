package io.zhenglei.storm.partition.transation;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.storm.coordination.BatchOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BasePartitionedTransactionalSpout;
import org.apache.storm.transactional.TransactionAttempt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import io.zhenglei.storm.domain.MateData;

public class PartitionedTransactionalSpout extends BasePartitionedTransactionalSpout<MateData> {

	Map<Integer, Map<Integer,String>> map = new HashMap<>();
	int BATCH_NUM = 10;
	int PARTITION = 5;
	public PartitionedTransactionalSpout() {
		Random random = new Random();
		String hosts = "www.taobao.com";
		String sessionid[] = { "AADJJDDJJSFSLDKGIGIG334S", "ADKFLSDKDIFIFIFI3563333", "DKSDLDAFKASDKFSLLDFKLD334",
				"KDFLSFDSLDFKXNCXCVNE342K", "DSFASDLCXKVLZCVNLSKDFK453" };
		String times[] = { "2018-01-05 10:52:00", "2018-01-05 10:54:00", "2018-01-05 10:55:00", "2018-01-05 10:56:00",
				"2018-01-05 10:58:00", "2018-01-05 10:59:00" };
		for (int i = 0; i < 5; i++) {
			Map<Integer,String> m = new HashMap<>();
			for (int j = 0; j < 100; j++) {
				m.put(j, hosts + "\t" + sessionid[random.nextInt(sessionid.length)] + "\t"
						+ times[random.nextInt(times.length)]);
			}
			map.put(i, m);
		}
	}
	/**
	 * 元数据
	 */
	@Override
	public org.apache.storm.transactional.partitioned.IPartitionedTransactionalSpout.Coordinator getCoordinator(
			Map conf, TopologyContext context) {
		
		return new Coordinator() {
			
			@Override
			public int numPartitions() {
				return PARTITION;
			}
			
			@Override
			public boolean isReady() {
				Utils.sleep(1000);
				return true;
			}
			
			@Override
			public void close() {
				
			}
		};
	}

	@Override
	public org.apache.storm.transactional.partitioned.IPartitionedTransactionalSpout.Emitter<MateData> getEmitter(
			Map conf, TopologyContext context) {
		return new Emitter<MateData>() {
			
			/**
			 * 新消息
			 */
			@Override
			public MateData emitPartitionBatchNew(TransactionAttempt tx, BatchOutputCollector collector, int partition,
					MateData lastPartitionMeta) {
				int startPoint = 0;
				if(lastPartitionMeta!=null){
					startPoint = lastPartitionMeta.getStartPoint()+lastPartitionMeta.getBach_num();
				}
				MateData mateData = new MateData(startPoint, BATCH_NUM);
				emitPartitionBatch(tx, collector, partition, mateData);
				return mateData;
			}
			
			/**
			 * 旧消息
			 */
			@Override
			public void emitPartitionBatch(TransactionAttempt tx, BatchOutputCollector collector, int partition,
					MateData partitionMeta) {
				Map<Integer, String> m = map.get(partition);
				for (int i = partitionMeta.getStartPoint(); i < partitionMeta.getStartPoint()+partitionMeta.getBach_num(); i++) {
					if(m.get(i)==null){
						return;
					}
					collector.emit(new Values(tx,m.get(i)));
				}
			}
			
			@Override
			public void close() {
				
			}
		};
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("tx","data"));
	}

}
