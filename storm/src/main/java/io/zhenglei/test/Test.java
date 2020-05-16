package io.zhenglei.test;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

import io.zhenglei.bolt1.AreaWordsCountBolt;
import io.zhenglei.bolt1.SplitBolt;
import io.zhenglei.bolt1.SumBolt;
import io.zhenglei.spout.Spout;

public class Test {
	public static void main(String[] args) {
		TopologyBuilder tb = new TopologyBuilder();
		tb.setSpout("spout1", new Spout(),2);
		tb.setBolt("splitbolt", new SplitBolt(), 2).shuffleGrouping("spout1");
		tb.setBolt("areawordscountbolt", new AreaWordsCountBolt(), 2).fieldsGrouping("splitbolt", new Fields("word"));
		tb.setBolt("sumbolt", new SumBolt(), 1).shuffleGrouping("areawordscountbolt");
		
		StormTopology st = tb.createTopology();
		LocalCluster lc = new LocalCluster();
		Config conf = new Config();
		if(args.length==0){
			conf.setNumWorkers(2);
			lc.submitTopology("storm", conf, st);
		}else{
			try {
				StormSubmitter.submitTopology(args[0], conf, tb.createTopology());
			} catch (AlreadyAliveException | InvalidTopologyException | AuthorizationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
