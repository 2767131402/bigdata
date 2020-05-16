package io.zhenglei.storm.drpc;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.drpc.DRPCSpout;
import org.apache.storm.drpc.ReturnResults;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class StormDrpc2 {
	static class Mybolt extends BaseBasicBolt {

		@Override
		public void execute(Tuple input, BasicOutputCollector collector) {
			Object obj = input.getValue(1);
			//包含了请求编号，主机等信息,这是规定，放在第二个参数
			String s = input.getString(0) + "!!!";
			collector.emit(new Values(s, obj));
		}

		@Override
		public void declareOutputFields(OutputFieldsDeclarer declarer) {
			declarer.declare(new Fields("id", "result"));
		}

	}

	public static void main(String[] args) {
		TopologyBuilder tb = new TopologyBuilder();
		LocalDRPC drpc = new LocalDRPC();

		DRPCSpout spout = new DRPCSpout("zl", drpc);
		tb.setSpout("spout", spout);
		tb.setBolt("bolt1", new Mybolt(),3).shuffleGrouping("spout");
		tb.setBolt("bolt2", new ReturnResults(),3).shuffleGrouping("bolt1");;

		LocalCluster cluster = new LocalCluster();
		Config conf = new Config();
		conf.setDebug(false);
		cluster.submitTopology("exclaim", conf, tb.createTopology());

		System.err.println(drpc.execute("zl", "aaa"));
		System.err.println(drpc.execute("zl", "bbb"));
	}
}
