package io.zhenglei.storm.top;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.trident.TridentState;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.BaseFilter;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.operation.builtin.FilterNull;
import org.apache.storm.trident.operation.builtin.FirstN;
import org.apache.storm.trident.operation.builtin.MapGet;
import org.apache.storm.trident.operation.builtin.Sum;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.trident.testing.MemoryMapState;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

/**
 * 
 * @author ii_zh
 *
 */
public class TridentTest {

	static class MyFielter extends BaseFilter {

		@Override
		public boolean isKeep(TridentTuple tuple) {
			if(tuple.getString(0).contains("tom")){
				return false;
			}
			return true;
		}
		
	}
	public static TridentTopology build(LocalDRPC drpc){
		TridentTopology tt = new TridentTopology();
		FixedBatchSpout fixedBatchSpout = new FixedBatchSpout(new Fields("words"), 2, 
				new Values("hello word"),new Values("hello tom"),new Values("hello zlp"),new Values("hello abc"));
		fixedBatchSpout.setCycle(true);//循环发送
		TridentState persistentAggregate = tt.newStream("spout", fixedBatchSpout)
//				.each(new Fields("words"), new Split(), new Fields("word", "count")).filter(new Fields("words"),new MyFielter())
				//相当于投影查询，将前面的字段选择性的输出
//				.each(new Fields("words"), new Split(), new Fields("word", "count")).project(new Fields("words","word","count"))
				.each(new Fields("words"), new Split(), new Fields("word", "count"))
				.parallelismHint(2)
				.groupBy(new Fields("word"))
				.persistentAggregate(new MemoryMapState.Factory(), new Fields("count"), new Sum(), new Fields("wcount"));
				//第一个参数 状态存储 new MemoryMapState.Factory() Map之前一般会groupBy,只有groupBy才有可以，value
		
		tt.newDRPCStream("fun",drpc)
			.each(new Fields("args"), new BaseFunction() {
				@Override
				public void execute(TridentTuple tuple, TridentCollector collector) {
					String[] split = tuple.getString(0).split(" ");
					for (String s : split) {
						collector.emit(new Values(s));
					}
				}
			}, new Fields("word"))
			.stateQuery(persistentAggregate, new Fields("word"), new MapGet(),new Fields("wcount"))
			//过滤为空的结果
			.filter(new FilterNull())
			//查询结果前两个，根据“wcount”排序，true降序
			.applyAssembly(new FirstN(2, "wcount", true));
		return tt;
	}
	public static void main(String[] args) {
	
		Config config = new Config();
		config.setNumWorkers(2);
		config.setDebug(false);
		LocalDRPC drpc = new LocalDRPC();
		if(args.length==0||args==null){
			LocalCluster lc = new LocalCluster();
			lc.submitTopology("storm", config, build(drpc).build());
			for (int i = 0; i < 100; i++) {
				String str = drpc.execute("fun", "abc word tom zlp");
				System.out.println(str);
				Utils.sleep(1000);
			}			
		}else{
			try {
				StormSubmitter.submitTopology(args[0], config, build(null).build());
			} catch (AlreadyAliveException | InvalidTopologyException | AuthorizationException e) {
				e.printStackTrace();
			}
		}
	}
	
}

class Split extends BaseFunction {

	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		String[] split = tuple.getString(0).split(" ");
		for (String s : split) {
			collector.emit(new Values(s, 1));
		}
	}

}