package io.zhenglei.hbase;

import java.util.zip.CRC32;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.util.Bytes;

public class HbaseDao {
	
	CRC32 crc32 = new CRC32();
	
	Configuration conf;
	public HbaseDao() {
		conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "192.168.59.110:2181,192.168.59.120:2181,192.168.59.130:2181");
	}
	
	public String toString(byte[] bytes){
		return Bytes.toString(bytes);
	}
	
	public byte[] toByte(String str){
		return Bytes.toBytes(str);
	}
}
