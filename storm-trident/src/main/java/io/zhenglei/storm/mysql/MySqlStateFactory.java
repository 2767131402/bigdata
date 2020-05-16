package io.zhenglei.storm.mysql;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.task.IMetricsContext;
import org.apache.storm.trident.state.JSONNonTransactionalSerializer;
import org.apache.storm.trident.state.JSONOpaqueSerializer;
import org.apache.storm.trident.state.JSONTransactionalSerializer;
import org.apache.storm.trident.state.Serializer;
import org.apache.storm.trident.state.State;
import org.apache.storm.trident.state.StateFactory;
import org.apache.storm.trident.state.StateType;
import org.apache.storm.trident.state.map.NonTransactionalMap;
import org.apache.storm.trident.state.map.OpaqueMap;
import org.apache.storm.trident.state.map.TransactionalMap;

public class MySqlStateFactory implements StateFactory{

	private StateType stateType;
	Map<StateType, Serializer> map = new HashMap<>();
	
	public MySqlStateFactory(StateType stateType) {
		this.stateType = stateType;
		map.put(StateType.NON_TRANSACTIONAL, new JSONNonTransactionalSerializer());
		map.put(StateType.OPAQUE, new JSONOpaqueSerializer());
		map.put(StateType.TRANSACTIONAL, new JSONTransactionalSerializer());
	}
	
	@Override
	public State makeState(Map conf, IMetricsContext metrics, int partitionIndex, int numPartitions) {
		State state = null;
		
		if(stateType == StateType.NON_TRANSACTIONAL){
			state = NonTransactionalMap.build(new MysqlState(map.get(StateType.NON_TRANSACTIONAL)));
		}
		if(stateType == StateType.OPAQUE){
			state = OpaqueMap.build(new MysqlState(map.get(StateType.OPAQUE)));
		}
		if(stateType == StateType.TRANSACTIONAL){
			state = TransactionalMap.build(new MysqlState(map.get(StateType.TRANSACTIONAL)));
		}
		
		return state;
	}

}
