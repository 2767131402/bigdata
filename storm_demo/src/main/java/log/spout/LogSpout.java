package log.spout;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

public class LogSpout extends BaseRichSpout {

	private BufferedReader br = null;
	private FileInputStream input = null;
	private SpoutOutputCollector collector;
	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
		try {
			input = new FileInputStream("data.log");
			br = new BufferedReader(new InputStreamReader(input));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void nextTuple() {
		String line = null;
		try {
			while((line = br.readLine())!=null){
				collector.emit(new Values(line));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("log"));
	}

	@Override
	public void close() {
		if (br!=null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
