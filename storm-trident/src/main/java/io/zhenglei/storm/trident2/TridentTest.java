package io.zhenglei.storm.trident2;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.shade.org.json.simple.JSONObject;
import org.apache.storm.trident.TridentState;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.CombinerAggregator;
import org.apache.storm.trident.operation.ReducerAggregator;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.operation.builtin.MapGet;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.trident.testing.MemoryMapState;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

public class TridentTest {

	public static void main(String[] args) {
		TridentTopology tt = new TridentTopology();
		FixedBatchSpout fixedBatchSpout = new FixedBatchSpout(new Fields("id","money","time"), 2, new Values(1,10,"2018-10-10"), new Values(2,30,"2018-06-10"), new Values(3,50,"2018-10-11"));
//		fixedBatchSpout.setCycle(true);
		
		TridentState persistentAggregate = tt.newStream("spout", fixedBatchSpout)
			.each(new Fields("id","money","time"),new info(),new Fields("orderId","orderMoney","orderTime","orderCount"))
			.groupBy(new Fields("orderTime"))
			.persistentAggregate(new MemoryMapState.Factory(), new Fields("orderMoney","orderCount"), new info2(), new Fields("wcount"));
		
		LocalDRPC drpc = new LocalDRPC();
		tt.newDRPCStream("fun",drpc)
			.each(new Fields("args"),new BaseFunction() {
				
				@Override
				public void execute(TridentTuple tuple, TridentCollector collector) {
					String[] split = tuple.getString(0).split(" ");
					for (String s : split) {
						collector.emit(new Values(s));
					}
				}
			}, new Fields("cdate"))
			.stateQuery(persistentAggregate, new Fields("cdate"), new MapGet(),new Fields("wcount"));
		
		
		Config config = new Config();
		config.setNumWorkers(2);
		config.setDebug(false);
		if(args.length==0||args==null){
			LocalCluster lc = new LocalCluster();
			lc.submitTopology("storm", config, tt.build());
			for (int i = 0; i < 10; i++) {
				String str = drpc.execute("fun", "2018-10-10 2018-06-10 2018-10-11");
//				System.out.println(str);
				Utils.sleep(2000);
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

class info extends BaseFunction{

	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		int id = tuple.getInteger(0);
		int money = tuple.getInteger(1);
		String time = tuple.getString(2);
		collector.emit(new Values(id,money,time,1));
	}
	
}

class info2 implements CombinerAggregator<JSONObject>{

	@Override
	public JSONObject init(TridentTuple tuple) {
		System.out.println("init----------------------------------");
		JSONObject jsonObject = new JSONObject();
		int money = tuple.getInteger(0);
		int count = tuple.getInteger(1);
		jsonObject.put("m", money);
		jsonObject.put("c", count);
		return jsonObject;
	}

	@Override
	public JSONObject combine(JSONObject val1, JSONObject val2) {
		System.out.println("combine++++++++++++++++++++++++++++++++++++++++++");
		int money = (int) val2.get("m");
		int count = (int) val2.get("c");
		
		int oldMoney = val1.get("m")==null?0:(int)val1.get("m");
		int oldCount = val1.get("c")==null?0:(int)val1.get("c");
		
		val1.put("m", oldMoney+money);
		val1.put("c", oldCount+count);
		return val1;
	}

	@Override
	public JSONObject zero() {
		System.out.println("zero=================================");
		JSONObject jsonObject = new JSONObject();
		return jsonObject;
	}
	
}

class info3 implements ReducerAggregator<JSONObject>{

	@Override
	public JSONObject init() {
		return new JSONObject();
	}

	@Override
	public JSONObject reduce(JSONObject curr, TridentTuple tuple) {
		int money = tuple.getInteger(0);
		int count = tuple.getInteger(1);

		int oldMoney = curr.get("m")==null?0:(int)curr.get("m");
		int oldCount = curr.get("c")==null?0:(int)curr.get("c");
		
		curr.put("m", oldMoney+money);
		curr.put("c", oldCount+count);
		return curr;
	}
	
}