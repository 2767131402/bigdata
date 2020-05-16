package io.zhenglei.spout;

import java.util.Map;
import java.util.Random;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

public class Spout extends BaseRichSpout {

	private Random random = new Random();
	private String name = "www.baidu.com";
	private String[] session = {"SDKVNDJKBNDJ28978","DKNVJKDNVJ34UT834UH","NVDIUSHRIUURVREE8HVID8989","KVJDINSJBNSJBNJ939302","DNBVKJNBJFNHDBJHSDNKJI889"};
	private String[] time = {"2018-01-05 10:10:10","2018-01-05 10:13:10","2018-01-05 10:15:10","2018-01-05 10:17:10","2018-01-05 10:19:10"};
	private SpoutOutputCollector collector;
	
	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void nextTuple() {
		String message = name+"\t"+session[random.nextInt(session.length)]+"\t"+time[random.nextInt(time.length)];
		collector.emit(new Values(message));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("message"));
	}

}
