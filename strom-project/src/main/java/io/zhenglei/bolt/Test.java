package io.zhenglei.bolt;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

import io.zhenglei.spout.Spout;

public class Test {
	public static void main(String[] args) {
		TopologyBuilder tb = new TopologyBuilder();
		tb.setSpout("spot", new Spout());
		tb.setBolt("bolt1", new Bolt(), 2).shuffleGrouping("spot");
		tb.setBolt("bolt2", new Bolt2(), 2).fieldsGrouping("bolt1", new Fields("data"));
		tb.setBolt("bolt3", new Bolt3(), 1).shuffleGrouping("bolt2");
		
		LocalCluster lc = new LocalCluster();
		Config conf = new Config();
		conf.setDebug(false);
		conf.setNumWorkers(2);
		lc.submitTopology("storm", conf, tb.createTopology());
	}
}
