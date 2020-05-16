package io.zhenglei.storm.kafka2;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.trident.TridentState;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.operation.builtin.Count;
import org.apache.storm.trident.operation.builtin.FilterNull;
import org.apache.storm.trident.operation.builtin.MapGet;
import org.apache.storm.trident.testing.MemoryMapState;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

public class Main2 {

	static class MyFunction extends BaseFunction {

		@Override
		public void execute(TridentTuple tuple, TridentCollector collector) {
			System.out.println(tuple.getString(0));
			String[] split = tuple.getString(0).split(" ");
			for (String s : split) {
				collector.emit(new Values(s,1));
			}
		}
		
	}
	public static void main(String[] args) {
		TridentTopology tt = new TridentTopology();
		TridentState state = tt.newStream("spout", new MyKafkaSpout("mytopic"))
			.each(new Fields("message"), new MyFunction(), new Fields("word","count"))
			.project(new Fields("word","count"))
			.groupBy(new Fields("word"))
			.persistentAggregate(new MemoryMapState.Factory(), new Fields("count"), new Count(), new Fields("wcount"));
		
		LocalDRPC drpc = new LocalDRPC();
		tt.newDRPCStream("fun", drpc).stateQuery(state, new Fields("args"), new MapGet(), new Fields("wcount"))
			.filter(new FilterNull());
		
		LocalCluster lc = new LocalCluster();
		Config config = new Config();
		config.setNumWorkers(2);
		config.setDebug(false);
		lc.submitTopology("storm", config, tt.build());
	}

}
