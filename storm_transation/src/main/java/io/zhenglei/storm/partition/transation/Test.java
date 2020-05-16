package io.zhenglei.storm.partition.transation;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.transactional.TransactionalTopologyBuilder;

public class Test {
	public static void main(String[] args) {
		TransactionalTopologyBuilder tb=new TransactionalTopologyBuilder("tb1", "spout", new PartitionedTransactionalSpout());
		tb.setBolt("bolt1", new TransactionalBolt()).shuffleGrouping("spout");
		tb.setBolt("bolt2", new CountTransactionalBolt()).shuffleGrouping("bolt1");
		LocalCluster lc=new LocalCluster();
		Config config=new Config();
		config.setNumAckers(2);
		config.setDebug(false);
		lc.submitTopology("submit", config, tb.buildTopology());
	}
}
