package io.zhenglei.log.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.junit.Before;
import org.junit.Test;

public class TestHbase {
	
	Configuration conf;
	@Before
    public void before() throws Exception {  
		conf = HBaseConfiguration.create();;
		conf.set("hbase.zookeeper.quorum", "192.168.6.121:2181,192.168.6.122:2181,192.168.6.123:2181");
    } 
	
	@Test
	public void test01(){
		HTable hTable = null;
		List<String> list = new ArrayList<>();
		try {
			hTable = new HTable(conf, "t_user");
			Scan scan = new Scan();
			SingleColumnValueFilter columnValueFilter = new SingleColumnValueFilter(Bytes.toBytes("c1"), Bytes.toBytes("u_newuser"), CompareOp.EQUAL, Bytes.toBytes(true));
			scan.setFilter(columnValueFilter);
			
			ResultScanner rs = hTable.getScanner(scan);
			Iterator<Result> iterator = rs.iterator();
			while(iterator.hasNext()){
				Result next = iterator.next();
				String u_ud = Bytes.toString(next.getValue(Bytes.toBytes("c1"), Bytes.toBytes("u_ud")));
				list.add(u_ud);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				hTable.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(list.size());
	}
	
}
