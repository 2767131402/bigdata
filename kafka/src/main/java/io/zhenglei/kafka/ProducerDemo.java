package io.zhenglei.kafka;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class ProducerDemo {
	public static void main(String[] args) {
		Properties originalProps = new Properties();
		originalProps.put("metadata.broker.list", "192.168.6.121:9092,192.168.6.122:9092,192.168.6.123:9092");		
		originalProps.put("serializer.class", "kafka.serializer.StringEncoder");
		ProducerConfig conf = new ProducerConfig(originalProps);
		Producer<String, String> producer = new Producer<>(conf);
		int i=1;
		while(true){
			producer.send(new KeyedMessage<String, String>("mytopic", "哈哈哈"+i));
			producer.send(new KeyedMessage<String, String>("mytopic", "嘿嘿嘿"+i));
			i++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
