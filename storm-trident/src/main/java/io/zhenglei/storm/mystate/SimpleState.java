package io.zhenglei.storm.mystate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.storm.task.IMetricsContext;
import org.apache.storm.trident.state.State;
import org.apache.storm.trident.state.map.ReadOnlyMapState;

public class SimpleState implements ReadOnlyMapState<String> {
	private Map<String, String> maps;

	@Override
	public void beginCommit(Long txid) {
	}
	static class Factory implements org.apache.storm.trident.state.StateFactory
	{

		private Map<String, String> maps;		
		public Factory(Map<String, String> maps) {
			super();
			this.maps = maps;
		}
		@Override
		public State makeState(Map conf, IMetricsContext metrics, int partitionIndex, int numPartitions) {
			// TODO Auto-generated method stub
			return new SimpleState(maps);		
		}		
	}
	@Override
	public void commit(Long txid) {

	}

	public SimpleState(Map<String, String> maps) {
		super();
		this.maps = maps;
	}

	@Override
	public List<String> multiGet(List<List<Object>> keys) {
		List<String> list1 = new ArrayList<>();
		for (List<Object> list : keys) {
			list1.add(maps.get(list.get(0)));
		}
		return list1;
	}

}
