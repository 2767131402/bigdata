package com.oracle.storm.partition.aggregator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.task.IMetricsContext;
import org.apache.storm.trident.TridentState;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.Aggregator;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.operation.TridentOperationContext;
import org.apache.storm.trident.operation.builtin.MapGet;
import org.apache.storm.trident.state.BaseStateUpdater;
import org.apache.storm.trident.state.QueryFunction;
import org.apache.storm.trident.state.State;
import org.apache.storm.trident.state.StateFactory;
import org.apache.storm.trident.state.map.ReadOnlyMapState;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

public class PartitionPersistTest {

	/**
	 * 泛型指的是init返回的类型及传递给complete的类型
	 * 
	 * @author oracle
	 *
	 */
	static class PartitionAggregate implements Aggregator<Map<String, Integer>> {
		@Override
		public void prepare(Map conf, TridentOperationContext context) {
			
		}
		@Override
		public void cleanup() {
			
		}

		@Override
		public Map<String, Integer> init(Object batchId, TridentCollector collector) {			
			return new HashMap<String, Integer>();
		}

		@Override
		public void aggregate(Map<String, Integer> val, TridentTuple tuple, TridentCollector collector) {
			String key = tuple.getString(0);
			Integer value = tuple.getInteger(1);
			Integer v = val.get(key) == null ? 0 : val.get(key);
			val.put(key, value + v);
		}

		@Override
		public void complete(Map<String, Integer> val, TridentCollector collector) {
			for (String key : val.keySet()) {
				System.out.println(key+"  completecompletecompletecompletecomplete  "+ val.get(key));
				collector.emit(new Values(key, val.get(key)));
			}
		}

	}

	/**
	 * 
	 * @author oracle
	 *
	 */
	static class NameSumState implements State {
		private Map<String, Integer> map = new HashMap<>();

		@Override
		public void beginCommit(Long txid) {

		}

		@Override
		public void commit(Long txid) {

		}

		public void setBulk(Map<String, Integer> map) {
			// 将新到的tuple累加至map中
			for (Entry<String, Integer> entry : map.entrySet()) {
				String key = entry.getKey();
				if (this.map.containsKey(key)) {
					this.map.put(key, this.map.get(key) + map.get(key));
				} else {
					this.map.put(key, entry.getValue());
				}
			}
		}

		public Map<String, Integer> getBulk() {
			return map;
		}
	}

	static class NameSumStateFactory implements StateFactory {

		private static final long serialVersionUID = 8753337648320982637L;

		@Override
		public State makeState(Map arg0, IMetricsContext arg1, int arg2, int arg3) {
			return new NameSumState();
		}
	}

	static class NameSumUpdater extends BaseStateUpdater<NameSumState> {

		private static final long serialVersionUID = -6108745529419385248L;

		/**
		 * 每个批次的数据调用一次updateState方法
		 */
		public void updateState(NameSumState state, List<TridentTuple> tuples, TridentCollector collector) {
			Map<String, Integer> map = new HashMap<String, Integer>();
			for (TridentTuple t : tuples) {
				System.out.println(t.getString(0)+"   "+t.getInteger(1)+": updateStateupdateStateupdateStateupdateState");
				map.put(t.getString(0), t.getInteger(1));
			}
			state.setBulk(map);
		}
	}

	static class QuerySumFunction implements QueryFunction<NameSumState, String> {
		@Override
		public void prepare(Map conf, TridentOperationContext context) {
		}
		@Override
		public void cleanup() {

		}
		@Override
		public List<String> batchRetrieve(NameSumState state, List<TridentTuple> args) {
			ArrayList<String> list = new ArrayList<String>();
			for (TridentTuple tuple : args) {

				
				list.add("" + state.getBulk().get(tuple.getString(0)));
			}
			return list;
		}

		@Override
		public void execute(TridentTuple tuple, String result, TridentCollector collector) {

			System.out.println(result);
			collector.emit(new Values(result));
		}

	}

	static class Split extends BaseFunction {

		@Override
		public void execute(TridentTuple tuple, TridentCollector collector) {
			String str[] = tuple.getString(0).split("\t");
			for (String s : str) {
				collector.emit(new Values(s));
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/**
		 * 3条记录一个批次
		 */
		FixedBatchSpout spout = new FixedBatchSpout(new Fields("year", "times"), 3, new Values("2012", 1),
				new Values("2012", 2), new Values("2013", 3), new Values("2013", 4), new Values("2014", 5),
				new Values("2014", 6));

		spout.setCycle(true);
		TridentTopology top = new TridentTopology();
		TridentState state = top.newStream("spout1", spout)				
				.partitionAggregate(new Fields("year", "times"), new PartitionAggregate(),
						new Fields("yearKey", "timesValue"))
				.partitionPersist(new NameSumStateFactory(), new Fields("yearKey", "timesValue"), new NameSumUpdater());
		LocalDRPC ll = new LocalDRPC();
		top.newDRPCStream("getValue", ll).each(new Fields("args"), new Split(), new Fields("date")).stateQuery(state,
				new Fields("date"), new QuerySumFunction(), new Fields("sum"));
		LocalCluster lc = new LocalCluster();
		lc.submitTopology("xx", new Config(), top.build());
		for (int i = 0; i < 1000; i++) {
			System.err.println(ll.execute("getValue", "2012	2013") + "===============================");
			Utils.sleep(1000);
		}

	}

}
