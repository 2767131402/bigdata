package io.zhenglei.log.reducer;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.zip.CRC32;

import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Reducer;

import io.zhenglei.log.constants.HbaseConstants;
import io.zhenglei.log.dimetion.String4Boolean2;
import io.zhenglei.log.dimetion.StringStringDimetion;
import io.zhenglei.log.utils.DateFormateUtils;
import io.zhenglei.log.utils.ip.IPSeekerExt;
import io.zhenglei.log.utils.ip.IPSeekerExt.RegionInfo;

public class UserReducer2 extends TableReducer<StringStringDimetion, String4Boolean2, WritableComparable<Object>> {
	CRC32 crc32 = new CRC32();
	
	@Override
	protected void reduce(StringStringDimetion key, Iterable<String4Boolean2> values,
			Reducer<StringStringDimetion, String4Boolean2, WritableComparable<Object>, Mutation>.Context context) throws IOException, InterruptedException {
		
		for (String4Boolean2 text : values) {
			Put put = new Put(Bytes.toBytes(key.getArg0()+"_"+UUID.randomUUID().toString().replace("-", "")));
			put.addColumn(Bytes.toBytes(HbaseConstants.USER_FAMILY), Bytes.toBytes(HbaseConstants.USER_U_UD), Bytes.toBytes(text.getU_ud()));
			put.addColumn(Bytes.toBytes(HbaseConstants.USER_FAMILY), Bytes.toBytes(HbaseConstants.USER_U_MID), Bytes.toBytes(text.getU_mid()));
			put.addColumn(Bytes.toBytes(HbaseConstants.USER_FAMILY), Bytes.toBytes(HbaseConstants.USER_U_TIME), Bytes.toBytes(key.getArg0()));
			put.addColumn(Bytes.toBytes(HbaseConstants.USER_FAMILY), Bytes.toBytes(HbaseConstants.USER_U_IP), Bytes.toBytes(text.getIp()));
			
			IPSeekerExt ipSeekerExt = new IPSeekerExt();
			RegionInfo info = ipSeekerExt.analyticIp(text.getIp());
			put.addColumn(Bytes.toBytes(HbaseConstants.USER_FAMILY), Bytes.toBytes(HbaseConstants.USER_U_COUNTRY), Bytes.toBytes(info.getCountry()));
			put.addColumn(Bytes.toBytes(HbaseConstants.USER_FAMILY), Bytes.toBytes(HbaseConstants.USER_U_PROVINCE), Bytes.toBytes(info.getProvince()));
			put.addColumn(Bytes.toBytes(HbaseConstants.USER_FAMILY), Bytes.toBytes(HbaseConstants.USER_U_CITY), Bytes.toBytes(info.getCity()));
			put.addColumn(Bytes.toBytes(HbaseConstants.USER_FAMILY), Bytes.toBytes(HbaseConstants.USER_U_NEWUSER), Bytes.toBytes(text.getNewuser()));
			put.addColumn(Bytes.toBytes(HbaseConstants.USER_FAMILY), Bytes.toBytes(HbaseConstants.USER_U_NEWVIP), Bytes.toBytes(text.getNewvip()));
			put.addColumn(Bytes.toBytes(HbaseConstants.USER_FAMILY), Bytes.toBytes(HbaseConstants.USER_B_IEV), Bytes.toBytes(text.getBrowser()));
			
			context.write(null, put);
		}
		
	}
}
