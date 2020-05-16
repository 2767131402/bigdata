package io.zhenglei.storm.mystate;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.shade.org.apache.http.impl.auth.BasicSchemeFactory;
import org.apache.storm.trident.TridentState;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.operation.builtin.MapGet;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

public class TestMyFactory {

	private static BaseFunction function = new BaseFunction() {
		@Override
		public void execute(TridentTuple tuple, TridentCollector collector) {
			String words[] = tuple.getString(0).split(" ");
			for (String w : words) {
				collector.emit(new Values(w));
			}
		}
	};

	public static void main(String[] args) {

		TridentTopology top = new TridentTopology();
		// FixedBatchSpout spout=new FixedBatchSpout(new Fields("year","times"),
		// 3, new Values("2012",1),new Values("2013",2),new Values("2014",3),new
		// Values("2012",4),new Values("2013",5),new Values("2014",6));
		// top.newStream("spout1", spout).groupBy(new Fields("year")).
		Map<String, String> maps = new HashMap<>();
		maps.put("a", "1");
		maps.put("c", "12");
		TridentState state = top.newStaticState(new SimpleState.Factory(maps));
		LocalDRPC drpc = new LocalDRPC();
		
		top.newDRPCStream("getValue", drpc).each(new Fields("args"), function, new Fields("word")).stateQuery(state,
				new Fields("word"), new MapGet(), new Fields("count"));
		LocalCluster lc = new LocalCluster();
		lc.submitTopology("xx", new Config(), top.build());

		for (int i = 0; i < 200; i++) {
			String result = drpc.execute("getValue", "a c");
			System.err.println(result);
			Utils.sleep(1000);
		}

	}
}
