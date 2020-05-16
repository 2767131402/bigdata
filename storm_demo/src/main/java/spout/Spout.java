package spout;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import kafka.utils.Utils;

public class Spout implements IRichSpout {

	private SpoutOutputCollector collector;
	private Random random = new Random();
	private String name = "www.baidu.com";
	private String[] session = {"SDKVNDJKBNDJ28978","DKNVJKDNVJ34UT834UH","NVDIUSHRIUURVREE8HVID8989","KVJDINSJBNSJBNJ939302","DNBVKJNBJFNHDBJHSDNKJI889"};
	private String[] time = {"2018-01-05 10:10:10","2018-01-05 10:13:10","2018-01-05 10:15:10","2018-01-05 10:17:10","2018-01-05 10:19:10"};
	private Map<String, String> map = new HashMap<String, String>();
	@Override
	public void close() {
		map.clear();
	}

	@Override
	public void activate() {
		
	}

	@Override
	public void deactivate() {
		
	}


	@Override
	public void ack(Object msgId) {
		map.remove(msgId);
		System.err.println(msgId+"\t+ack");
	}

	@Override
	public void fail(Object msgId) {
		collector.emit(new Values(map.get(msgId)), msgId);
		System.err.println(msgId+"\t+重发");
	}


	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
	
	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void nextTuple() {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String message = name+"\t"+session[random.nextInt(session.length)]+"\t"+time[random.nextInt(time.length)];
		map.put(uuid, message);
		collector.emit(new Values(message), uuid);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("test"));
	}
}
