package io.zhenglei.hbase.dao;

public interface HbaseDao {
	void put(String time, int value);
	void delete(String key);
}
