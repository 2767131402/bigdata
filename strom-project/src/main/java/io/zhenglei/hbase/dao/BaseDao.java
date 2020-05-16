package io.zhenglei.hbase.dao;

import java.util.zip.CRC32;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.util.Bytes;

public class BaseDao {
	
	CRC32 crc32 = new CRC32();
	
	protected Configuration conf;
	public BaseDao() {
		conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "192.168.6.121:2181,192.168.6.122:2181,192.168.6.123:2181");
	}
	
	public String toString(byte[] bytes){
		return Bytes.toString(bytes);
	}
	
	public byte[] toByte(String str){
		return Bytes.toBytes(str);
	}
	
	public byte[] toByte(int str){
		return Bytes.toBytes(str);
	}
}
