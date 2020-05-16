package io.zhenglei.storm.partition.order;

import java.util.Map;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.shade.org.json.simple.JSONObject;
import org.apache.storm.trident.TridentState;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.Aggregator;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.CombinerAggregator;
import org.apache.storm.trident.operation.ReducerAggregator;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.operation.TridentOperationContext;
import org.apache.storm.trident.operation.builtin.MapGet;
import org.apache.storm.trident.state.StateType;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.trident.testing.MemoryMapState;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

public class TridentOrder1 {
	static class OrderInfo extends BaseFunction {
		@Override
		public void execute(TridentTuple tuple, TridentCollector collector) {
			int orderId = tuple.getInteger(0);
			int orderMoney = tuple.getInteger(1);
			String date = tuple.getString(2);

			collector.emit(new Values(orderId, orderMoney, date, 1));
		}
	}
	/*
	 * static class OrderSumCount implements CombinerAggregator<String> {
	 *//**
		 * 每一条记录都会被调用
		 */
	/*
	 * @Override public String init(TridentTuple tuple) { // TODO Auto-generated
	 * method stub return tuple.getInteger(0) + "\t" + tuple.getInteger(1); }
	 *//**
		 * 参数val1:是一组中当前和
		 */

	/*
	 * @Override public String combine(String val1, String val2) {
	 * System.out.println(this); String beforeSumCount[] = val1.split("\t");
	 * String currentResult[] = val2.split("\t"); return
	 * (Double.parseDouble(beforeSumCount[0]) +
	 * Double.parseDouble(currentResult[0])) + "\t" +
	 * (Double.parseDouble(beforeSumCount[1]) +
	 * Double.parseDouble(currentResult[1])); }
	 *//**
		* 
		*//*
		 * @Override public String zero() { // TODO Auto-generated method stub
		 * return "0\t0"; }
		 * 
		 * }
		 */
	// 分批->shuffle->并行度
	/*static class OrderSumCount implements CombinerAggregator<JSONObject> {
		*//**
		 * 每一条记录都会被调用
		 *//*
		@Override
		public JSONObject init(TridentTuple tuple) {
			Integer money = tuple.getInteger(0);
			Integer count = tuple.getInteger(1);
			JSONObject json = new JSONObject();
			json.put("m", money);
			json.put("c", count);
			return json;
		}

		*//**
		 * 参数val1:是一组中当前和
		 *//*
		@Override
		public JSONObject combine(JSONObject val1, JSONObject val2) {

			Integer money = (Integer) val2.get("m") == null ? 0 : (Integer) val2.get("m");
			Integer count = (Integer) val2.get("c") == null ? 0 : (Integer) val2.get("c");
			Integer oldMoney = val1.get("m") == null ? 0 : (Integer) val1.get("m");
			Integer oldCount = val1.get("c") == null ? 0 : (Integer) val1.get("c");
			JSONObject vv = new JSONObject();
			vv.put("m", oldMoney + money);
			vv.put("c", oldCount + count);
			return vv;
		}

		*//**
		 * 
		 *//*
		@Override
		public JSONObject zero() {
			// TODO Auto-generated method stub
			return new JSONObject();
		}

	}
*/
	static class OrderSumCountReducer implements ReducerAggregator<JSONObject> {
		//
		/**
		 * 每组
		 */
		@Override
		public JSONObject init() {

			return new JSONObject();
		}

		@Override
		public JSONObject reduce(JSONObject curr, TridentTuple tuple) {
			Long money = tuple.getLong(0);
			Long count = tuple.getLong(1);
			Long oldMoney = curr.get("m") == null ? 0 : (Long) curr.get("m");
			Long oldCount = curr.get("c") == null ? 0 : (Long) curr.get("c");
			curr.put("m", money + oldMoney);
			curr.put("c", count + oldCount);
			return curr;
		}
	}
	static class MyAggregator implements Aggregator<JSONObject> {

		@Override
		public void prepare(Map conf, TridentOperationContext context) {
			// TODO Auto-generated method stub

		}

		@Override
		public void cleanup() {
			// TODO Auto-generated method stub

		}

		@Override
		public JSONObject init(Object batchId, TridentCollector collector) {
			// TODO Auto-generated method stub
			System.out.println("开始=========================================");
			return new JSONObject();
		}

		@Override
		public void aggregate(JSONObject curr, TridentTuple tuple, TridentCollector collector) {
			int money = tuple.getInteger(0);
			int count = tuple.getInteger(1);
			String date = tuple.getString(2);
			System.err.println(this + "   " + tuple.getString(2));
			Long oldMoney = curr.get("m") == null ? 0 : (Long) curr.get("m");
			Long oldCount = curr.get("c") == null ? 0 : (Long) curr.get("c");
			curr.put("m", money + oldMoney);
			curr.put("c", count + oldCount);
			curr.put("date", date);

		}
		@Override
		public void complete(JSONObject curr, TridentCollector collector) {
			Long oldMoney = curr.get("m") == null ? 0 : (Long) curr.get("m");
			Long oldCount = curr.get("c") == null ? 0 : (Long) curr.get("c");
			collector.emit(new Values(oldMoney, oldCount, curr.get("date").toString()));
			System.out.println("提交=========================================" + oldMoney + "  " + oldCount);
		}

	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FixedBatchSpout fixedBatchSpout = new FixedBatchSpout(new Fields("id", "money", "odate"), 2,
				new Values(1, 20, "2018-01-05"), new Values(2, 10, "2018-01-05"), new Values(3, 50, "2018-01-06"),new Values(3, 70, "2018-01-08"));
		fixedBatchSpout.setCycle(true);
		
		TridentTopology tt = new TridentTopology();
		TridentState state = tt.newStream("spout1", fixedBatchSpout)
				.each(new Fields("id", "money", "odate"), new OrderInfo(),
						new Fields("orderId", "orderMoney", "date", "orderCount"))
				.partitionBy(new Fields("date"))
				.aggregate(new Fields("orderMoney", "orderCount", "date"), new MyAggregator(),
						new Fields("orderMoney1", "orderCount2", "date"))				
				.parallelismHint(2).groupBy(new Fields("date")).persistentAggregate(new MemoryMapState.Factory(),
						new Fields("orderMoney1", "orderCount2"), new OrderSumCountReducer(), new Fields("roder"));
		LocalDRPC drpc = new LocalDRPC();
		tt.newDRPCStream("fun", drpc).stateQuery(state, new Fields("args"), new MapGet(), new Fields("count"));

		Config conf = new Config();
		conf.setNumWorkers(2);
		LocalCluster lc = new LocalCluster();
		lc.submitTopology("xx", conf, tt.build());

		for (int i = 0; i < 100; i++) {
			String result = drpc.execute("fun", "2018-01-05");

			System.out.println(result);
			Utils.sleep(1000);
		}

	}

}
