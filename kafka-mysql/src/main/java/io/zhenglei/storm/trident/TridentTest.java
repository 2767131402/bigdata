package io.zhenglei.storm.trident;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.trident.TridentState;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.BaseFilter;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.operation.builtin.MapGet;
import org.apache.storm.trident.operation.builtin.Sum;
import org.apache.storm.trident.state.StateType;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import io.zhenglei.storm.mysql.MySqlStateFactory;

public class TridentTest {

	static class MyFielter extends BaseFilter {

		@Override
		public boolean isKeep(TridentTuple tuple) {
			if(tuple.getString(0).contains("tom")){
				return false;
			}
			return true;
		}
		
	}
	
	public static void main(String[] args) {
		TridentTopology tt = new TridentTopology();
		FixedBatchSpout fixedBatchSpout = new FixedBatchSpout(new Fields("words"), 2, 
				new Values("hello word"),new Values("hello tom"),new Values("hello zlp"),new Values("hello abc"));
		fixedBatchSpout.setCycle(true);//循环发送
		TridentState persistentAggregate = tt.newStream("spout", fixedBatchSpout)
				.each(new Fields("words"), new Split(), new Fields("word", "count")).project(new Fields("words","word","count"))
				.parallelismHint(2)
				.groupBy(new Fields("word"))
				.persistentAggregate(new MySqlStateFactory(StateType.NON_TRANSACTIONAL), new Fields("count"), new Sum(), new Fields("wcount"));
		
		LocalDRPC drpc = new LocalDRPC();
		tt.newDRPCStream("fun",drpc).stateQuery(persistentAggregate, new Fields("args"), new MapGet(),new Fields("wcount"));
		
		Config config = new Config();
		config.setNumWorkers(2);
		config.setDebug(false);
		LocalCluster lc = new LocalCluster();
		lc.submitTopology("storm", config, tt.build());
	}
	
}

class Split extends BaseFunction {

	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		String[] split = tuple.getString(0).split(" ");
		for (String s : split) {
			collector.emit(new Values(s, 1));
		}
	}

}