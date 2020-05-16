package io.zhenglei.storm.spout.trident;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.spout.ITridentSpout;
import org.apache.storm.trident.topology.TransactionAttempt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

public class MyTridentSpout implements ITridentSpout<MyData> {

	int BATCH_NUM = 10;
	Map<Integer, String> maps = new HashMap<>();
	public MyTridentSpout() {
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
				return new MyData(BATCH_NUM, startPoint);
			}
			
			@Override
			public void close() {
				
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
					if (maps.get(i)==null) {
						return;
					}
					collector.emit(new Values(tx,maps.get(i)));
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
		// TODO Auto-generated method stub
		return new Fields("tx","log");
	}

}
