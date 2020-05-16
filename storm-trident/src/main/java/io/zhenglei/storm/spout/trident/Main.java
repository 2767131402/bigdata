package io.zhenglei.storm.spout.trident;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.operation.builtin.Count;
import org.apache.storm.trident.state.StateType;
import org.apache.storm.trident.testing.MemoryMapState;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import io.zhenglei.storm.mysql.MySqlStateFactory;

public class Main {

	static class MyFunction extends BaseFunction{

		@Override
		public void execute(TridentTuple tuple, TridentCollector collector) {
			String[] split = tuple.getString(0).split("\t");
			System.out.println(tuple.getString(0));
			collector.emit(new Values(split[2],1));
		}
		
	}
	public static void main(String[] args) {
		TridentTopology tt = new TridentTopology();
		
		tt.newStream("spout", new MyTridentSpout()).shuffle()
			.each(new Fields("log"), new MyFunction(), new Fields("time","count"))
			.parallelismHint(2).project(new Fields("time","count"))
			.groupBy(new Fields("time"))
//			.persistentAggregate(new MemoryMapState.Factory(), new Fields("count"), new Count(), new Fields("wcount"));
			.persistentAggregate(new MySqlStateFactory(StateType.OPAQUE), new Fields("count"), new Count(), new Fields("wcount"));
			
		LocalCluster lc = new LocalCluster();
		Config config = new Config();
		config.setNumWorkers(2);
		config.setDebug(false);
		lc.submitTopology("storm", config, tt.build());
	}

}
