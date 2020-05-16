package io.zhenglei.storm.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

public class ConsumerDemo {
	public static void main(String[] args) {
		Properties originalProps = new Properties();
		originalProps.put("group.id", "zhenglei");		
		originalProps.put("zookeeper.connect", "192.168.6.121:2181,192.168.6.122:2181,192.168.6.123:2181");
		ConsumerConfig config = new ConsumerConfig(originalProps);
		ConsumerConnector connector = Consumer.createJavaConsumerConnector(config);
		Map<String, Integer> map = new HashMap<>();
		map.put("mytopic", 2);
		
		Map<String, List<KafkaStream<byte[], byte[]>>> streams = connector.createMessageStreams(map);
		List<KafkaStream<byte[], byte[]>> list = streams.get("mytopic");
		for (final KafkaStream<byte[], byte[]> kafkaStream : list) {
			
			new Thread(){
				public void run(){
					for (MessageAndMetadata<byte[], byte[]> s : kafkaStream) {
						System.out.println(new String(s.message()));
					}
				}
			}.start();
		}
	}
}
