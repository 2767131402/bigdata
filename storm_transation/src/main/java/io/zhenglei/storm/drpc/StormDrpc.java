package io.zhenglei.storm.drpc;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.StormSubmitter;
import org.apache.storm.drpc.LinearDRPCTopologyBuilder;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

public class StormDrpc {
	static class Mybolt extends BaseBasicBolt{

		@Override
		public void execute(Tuple input, BasicOutputCollector collector) {
			Object obj = input.getValue(0);
			String s = input.getString(1)+"!!!";
			collector.emit(new Values(obj,s));
		}

		@Override
		public void declareOutputFields(OutputFieldsDeclarer declarer) {
			declarer.declare(new Fields("id","result"));
		}
		
	}
	
	public static void main(String[] args) {
		LinearDRPCTopologyBuilder tb = new LinearDRPCTopologyBuilder("fun");
		tb.addBolt(new Mybolt());
		Config config = new Config();
		config.setNumWorkers(2);
		config.setDebug(false);
		
		if(args.length==0 || args==null){
			LocalDRPC ld = new LocalDRPC();
			LocalCluster lc = new LocalCluster();
			lc.submitTopology("storm", config, tb.createLocalTopology(ld));
			for (int i = 0; i < 100; i++) {
				String str = ld.execute("fun", "hello");
				System.out.println(str);
				Utils.sleep(3000);
			}
		}else{
			try {
				StormSubmitter.submitTopology(args[0], config, tb.createRemoteTopology());
			} catch (AlreadyAliveException | InvalidTopologyException | AuthorizationException e) {
				e.printStackTrace();
			}
		}
	}
}
