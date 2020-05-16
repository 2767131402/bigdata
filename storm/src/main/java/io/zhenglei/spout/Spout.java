package io.zhenglei.spout;

import java.util.Map;
import java.util.Random;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

/**
 * 数据源 spout->bolt1(拆分单词)->bolt2(局部个数)->bolt3(汇总)
 * @author ii_zh
 *
 */
public class Spout extends BaseRichSpout {

	private SpoutOutputCollector collector;
	private String[] words;
	private Random random;
	/**
	 * 初始化
	 */
	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
		words = new String[]{"hello abc","hello def","hello jio"};
		random = new Random();
	}

	/**
	 * 相当于hadoop的map
	 */
	@Override
	public void nextTuple() {
		collector.emit(new Values(words[random.nextInt(words.length)]));
		
	}

	/**
	 * 发射字段名
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("words"));
	}

}
