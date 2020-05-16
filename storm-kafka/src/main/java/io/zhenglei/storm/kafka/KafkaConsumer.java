package io.zhenglei.storm.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class KafkaConsumer extends Thread {

	private Queue<String> queue = new ConcurrentLinkedQueue<>();
	private ConsumerConnector consumer;
	
	public Queue<String> getQueue() {
		return queue;
	}
	public void setQueue(Queue<String> queue) {
		this.queue = queue;
	}

	private String topic;
	public KafkaConsumer(String topic) {
		this.topic = topic;
		consumer = Consumer.createJavaConsumerConnector(createConfig());
	}

	private ConsumerConfig createConfig() {
		Properties props = new Properties();
		props.put("zookeeper.connect",KafkaUtils.ZK_HOSTS);
		props.put("group.id", "111");
		//props.put("auto.offset.reset", "smallest");
		props.put("zookeeper.session.timeout.ms", "400");
		props.put("zookeeper.sync.time.ms", "200");
		props.put("zookeeper.commit.intarval.ms", "1000");//每隔1秒钏向zookeeper写一次消费偏移量
		ConsumerConfig cfg = new ConsumerConfig(props);
		return cfg;
	}
	
	@Override
	public void run() {
		Map<String, Integer> map = new HashMap<>();
		map.put("mytopic", 1);
		
		Map<String, List<KafkaStream<byte[], byte[]>>> streams = consumer.createMessageStreams(map);
		KafkaStream<byte[], byte[]> kafkaStream = streams.get(topic).get(0);
		ConsumerIterator<byte[], byte[]> iterator = kafkaStream.iterator();
		while (iterator.hasNext()) {
			String message = new String(iterator.next().message());
			queue.add(message);
		}
	}

}
