package io.zhenglei.log.mapper;

import java.io.IOException;
import java.net.URLDecoder;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.mapreduce.Mapper;

import io.zhenglei.log.constants.HbaseConstants;
import io.zhenglei.log.dimetion.String4Boolean2;
import io.zhenglei.log.dimetion.StringStringDimetion;

public class UserMapper extends TableMapper<StringStringDimetion, String4Boolean2> {
	StringStringDimetion input = new StringStringDimetion();
	String4Boolean2 output = new String4Boolean2();
	@Override
	protected void map(ImmutableBytesWritable key, Result value,
			Mapper<ImmutableBytesWritable, Result, StringStringDimetion, String4Boolean2>.Context context)
			throws IOException, InterruptedException {
		String time = Bytes.toString(value.getValue(Bytes.toBytes(HbaseConstants.FAMILY), Bytes.toBytes(HbaseConstants.COLUMN_C_TIME)));
		String en = Bytes.toString(value.getValue(Bytes.toBytes(HbaseConstants.FAMILY), Bytes.toBytes(HbaseConstants.COLUMN_EN)));
		
		String u_ud = Bytes.toString(value.getValue(Bytes.toBytes(HbaseConstants.FAMILY), Bytes.toBytes(HbaseConstants.COLUMN_U_UD)));
		String u_mid = Bytes.toString(value.getValue(Bytes.toBytes(HbaseConstants.FAMILY), Bytes.toBytes(HbaseConstants.COLUMN_U_MID)));
		String p_url = Bytes.toString(value.getValue(Bytes.toBytes(HbaseConstants.FAMILY), Bytes.toBytes(HbaseConstants.COLUMN_P_URL)));
		String ip = Bytes.toString(value.getValue(Bytes.toBytes(HbaseConstants.FAMILY), Bytes.toBytes(HbaseConstants.COLUMN_IP)));
		String b_iev = Bytes.toString(value.getValue(Bytes.toBytes(HbaseConstants.FAMILY), Bytes.toBytes(HbaseConstants.COLUMN_B_IEV)));
		
		if("e_l".equals(en) && time!=null){
			input.setArg0(time.split(" ")[0]);
			input.setArg1(en);
			
			if(URLDecoder.decode("http%3A%2F%2F172.16.0.150%3A8080%2FBIG_DATA_LOG2%2Fdemo4.jsp").equals(p_url)){
				output.setNewvip(true);
			} else{
				output.setNewvip(false);
			}
			output.setNewuser(true);
			output.setBrowser(b_iev==null?"":b_iev);
			output.setIp(ip==null?"":ip);
			output.setU_mid(u_mid==null?"":u_mid);
			output.setU_ud(u_ud==null?"":u_ud);
			context.write(input, output);
		}
		if("e_pv".equals(en) && time!=null){
			input.setArg0(time.split(" ")[0]);
			input.setArg1(en);
			
			if(URLDecoder.decode("http%3A%2F%2F172.16.0.150%3A8080%2FBIG_DATA_LOG2%2Fdemo4.jsp").equals(p_url)){
				output.setNewvip(true);
			} else{
				output.setNewvip(false);
			}
			output.setNewuser(false);
			output.setBrowser(b_iev==null?"":b_iev);
			output.setIp(ip==null?"":ip);
			output.setU_mid(u_mid==null?"":u_mid);
			output.setU_ud(u_ud==null?"":u_ud);
			context.write(input, output);
		}
	}
}
