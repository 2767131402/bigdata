package io.zhenglei.storm.mysql;

import java.util.ArrayList;
import java.util.List;

import org.apache.storm.trident.state.Serializer;
import org.apache.storm.trident.state.map.IBackingMap;

public class MysqlState<T> extends MySqlDao implements IBackingMap<T> {

	Serializer ser;
	public MysqlState(Serializer ser) {
		this.ser =ser;
	}
	@Override
	public List<T> multiGet(List<List<Object>> keys) {
		List<T> list = new ArrayList<>();
		for (List<Object> key : keys) {
			String k = key.get(0).toString();
			KeyValue keyValue = getKeyValue(k);
			if(keyValue!=null){
				list.add((T)ser.deserialize(keyValue.getValue().getBytes()));
			}else{
				list.add(null);
			}
		}
		return list;
	}

	@Override
	public void multiPut(List<List<Object>> keys, List<T> vals) {
		for (int i = 0; i < keys.size(); i++) {
			String k = keys.get(i).get(0).toString();
			String v = new String(ser.serialize(vals.get(i)));
			insert(k, v);
		}
	}

}
