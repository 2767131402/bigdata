package log.bolt;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

import log.spout.LogSpout;

public class Test {
	public static void main(String[] args) {
		TopologyBuilder tb = new TopologyBuilder();
		tb.setSpout("spout", new LogSpout());
		tb.setBolt("bolt1", new LogBolt()).shuffleGrouping("spout");
		tb.setBolt("bolt2", new LogBolt2()).shuffleGrouping("bolt1");
		
		LocalCluster lc = new LocalCluster();
		Config conf = new Config();
		conf.setDebug(false);
		conf.setNumWorkers(2);
		conf.setNumAckers(2);
		lc.submitTopology("storm", conf, tb.createTopology());
	}
}
