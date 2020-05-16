package io.zhenglei.hbase.dao.impl;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;

import io.zhenglei.hbase.constants.HbaseConstants;
import io.zhenglei.hbase.dao.BaseDao;
import io.zhenglei.hbase.dao.HbaseDao;

public class HbaseDaoImpl extends BaseDao implements HbaseDao{

	@Override
	public void put(String time, int value) {
		HTable table = null;
		try {
			table = new HTable(conf, HbaseConstants.TABLE_NAME);
			Put put = new Put(toByte(time));
			put.addColumn(toByte(HbaseConstants.FAMILY), toByte(HbaseConstants.COUNT), toByte(value));
			table.put(put);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				table.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public void delete(String key){
		HTable table = null;
		try {
			table = new HTable(conf, HbaseConstants.TABLE_NAME);
			Delete delete = new Delete(toByte(key));
//			delete.addColumn(family, qualifier);	//删除列
//			delete.addFamily(family);				//删除列族
			table.delete(delete);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				table.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void main(String[] args) {
		HbaseDaoImpl daoImpl = new HbaseDaoImpl();
//		daoImpl.put("2018-10-10 10:10:10", 3);
		daoImpl.delete("2018-01-05");
		System.out.println("end");
	}
}
