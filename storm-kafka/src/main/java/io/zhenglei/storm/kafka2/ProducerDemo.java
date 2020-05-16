package io.zhenglei.storm.kafka2;

import java.util.Properties;
import java.util.Random;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class ProducerDemo {
	public static void main(String[] args) {
		Random random = new Random();
		String[] str = {"hello world","hello tom","hello abc","hello jerry"};
		
		Properties originalProps = new Properties();
		originalProps.put("metadata.broker.list", "192.168.6.121:9092,192.168.6.122:9092,192.168.6.123:9092");		
		originalProps.put("serializer.class", "kafka.serializer.StringEncoder");
		ProducerConfig conf = new ProducerConfig(originalProps);
		Producer<String, String> producer = new Producer<>(conf);
		while(true){
			producer.send(new KeyedMessage<String, String>("mytopic", str[random.nextInt(str.length)]));
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
