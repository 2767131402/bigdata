package io.zhenglei.storm.kafka;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.kafka.trident.TransactionalTridentKafkaSpout;
import org.apache.storm.kafka.trident.TridentKafkaConfig;
import org.apache.storm.spout.SchemeAsMultiScheme;
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

public class Main {

	public static void main(String[] args) {
		BrokerHosts hosts = new ZkHosts(KafkaUtils.ZK_HOSTS);
		TridentKafkaConfig config = new TridentKafkaConfig(hosts, KafkaUtils.TOPIC);
		config.fetchMaxWait = 100;
		config.ignoreZkOffsets = false;
//		config.
		config.scheme = new SchemeAsMultiScheme(new StringScheme());
		
		TransactionalTridentKafkaSpout kafkaSpout = new TransactionalTridentKafkaSpout(config);
		
		TridentTopology tt = new TridentTopology();
		TridentState state = tt.newStream("spout1", kafkaSpout)
			.each(new Fields(StringScheme.STRING_SCHEME_KEY), new Split(), new Fields("word","count"))
			.project(new Fields("word","count"))
			.groupBy(new Fields("word"))
			.persistentAggregate(new MemoryMapState.Factory(), new Fields("count"), new Count(), new Fields("wcount"));
		
		LocalDRPC drpc = new LocalDRPC();
		tt.newDRPCStream("fun", drpc)
			.stateQuery(state, new Fields("args"), new MapGet(), new Fields("wcount"))
			.filter(new FilterNull());
		LocalCluster lc = new LocalCluster();
		Config config2 = new Config();
		config2.setNumWorkers(2);
		config2.setDebug(false);
		
		lc.submitTopology("storm", config2, tt.build());
		
	}
	
	static class Split extends BaseFunction {

		@Override
		public void execute(TridentTuple tuple, TridentCollector collector) {
			String[] split = tuple.getString(0).split(" ");
			System.out.println(tuple.getString(0));
			for (String s : split) {
				collector.emit(new Values(s,1));
			}
		}
		
	}

}
