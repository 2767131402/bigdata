package io.zhenglei.pvbolt;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import io.zhenglei.hbase.dao.HbaseDao;
import io.zhenglei.hbase.dao.impl.HbaseDaoImpl;

public class PvBolt3 extends BaseRichBolt {
	private Map<String, Integer> map = new HashMap<>();
	private HbaseDao hbaseDao;
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		hbaseDao = new HbaseDaoImpl();
	}

	long startTime = System.currentTimeMillis();
	long endTime = 0L;
	
	@Override
	public void execute(Tuple input) {
		String session = input.getString(0);
		int count = input.getInteger(1);
		map.put(session, count);
		endTime = System.currentTimeMillis();
		if((endTime-startTime)>5000){
			for (String s : map.keySet()) {
				System.err.println(s+"\t"+map.get(s));
				hbaseDao.put(session, map.get(s));
			}
			startTime = System.currentTimeMillis();
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
	}

}
