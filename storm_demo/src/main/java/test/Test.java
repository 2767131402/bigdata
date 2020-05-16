package test;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

import bolt.Bolt;
import bolt.Bolt2;
import spout.Spout;

public class Test {

	public static void main(String[] args) {
		TopologyBuilder tb = new TopologyBuilder();
		tb.setSpout("spout1", new Spout(), 2);
		tb.setBolt("bolt1", new Bolt(), 2).shuffleGrouping("spout1");
		tb.setBolt("bolt2", new Bolt2(), 1).shuffleGrouping("bolt1");

		LocalCluster lc = new LocalCluster();
		Config conf = new Config();
		conf.setNumWorkers(2);
		conf.setDebug(false);
		lc.submitTopology("storm", conf, tb.createTopology());
		
	}

}
