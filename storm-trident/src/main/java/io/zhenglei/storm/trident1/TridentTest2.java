package io.zhenglei.storm.trident1;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.trident.TridentState;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.operation.builtin.MapGet;
import org.apache.storm.trident.operation.builtin.Sum;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.trident.testing.MemoryMapState;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

public class TridentTest2 {

	public static void main(String[] args) {
		TridentTopology tt = new TridentTopology();
		FixedBatchSpout fixedBatchSpout = new FixedBatchSpout(new Fields("words"), 2, 
				new Values("2012 30"),new Values("2013 40"),new Values("2015 50"),new Values("2012 50"));
		TridentState persistentAggregate = tt.newStream("spout", fixedBatchSpout)
				.each(new Fields("words"), new Split(), new Fields("year", "sum"))
				.groupBy(new Fields("year"))
				.persistentAggregate(new MemoryMapState.Factory(), new Fields("sum"), new Sum(), new Fields("wsum"));
				//第一个参数 状态存储 new MemoryMapState.Factory() Map之前一般会groupBy,只有groupBy才有可以，value
		
		LocalDRPC drpc = new LocalDRPC();
		tt.newDRPCStream("fun",drpc).stateQuery(persistentAggregate, new Fields("args"), new MapGet(),new Fields("wsum"));
		
		Config config = new Config();
		config.setNumWorkers(2);
		config.setDebug(false);
		if(args.length==0||args==null){
			LocalCluster lc = new LocalCluster();
			lc.submitTopology("storm", config, tt.build());
			for (int i = 0; i < 50; i++) {
				String str = drpc.execute("fun", "2012");
				System.out.println(str);
				Utils.sleep(1000);
			}			
		}else{
			try {
				StormSubmitter.submitTopology(args[0], config, tt.build());
			} catch (AlreadyAliveException | InvalidTopologyException | AuthorizationException e) {
				e.printStackTrace();
			}
		}
	}
	
}

class Split extends BaseFunction {
	
	Map<String, Integer> map = new HashMap<>();
	
	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		String[] split = tuple.getString(0).split(" ");
//		int oldCount = map.get(split[0])==null?0:map.get(split[0]);
//		map.put(split[0], oldCount+Integer.parseInt(split[1]));
		collector.emit(new Values(split[0], Integer.parseInt(split[1])));
	}

}