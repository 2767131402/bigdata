package io.zhenglei.log.mapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.CRC32;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import cz.mallat.uasparser.UserAgentInfo;
import io.zhenglei.log.constants.HbaseConstants;
import io.zhenglei.log.utils.browser.UserAgentUtils;

public class LogMapper extends Mapper<LongWritable, Text, NullWritable, Put> {
	private CRC32 crc32 = new CRC32();
	
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, NullWritable, Put>.Context context)
			throws IOException, InterruptedException {
		String[] split = value.toString().split("\\?");
		Map<String, String> map = new HashMap<>();
		if(split.length>1){
			String ip = split[0].split(" ")[0];
			String val = "ip="+ip+"&"+split[1];
			handlerLine(val,map);
			handlerHbase(map, context);
		}		
	}
	
	private void handlerHbase(Map<String, String> map, Mapper<LongWritable, Text, NullWritable, Put>.Context context) {
		String ip = map.get(HbaseConstants.COLUMN_IP);
		String p_url = map.get(HbaseConstants.COLUMN_P_URL);
		String u_ud = map.get(HbaseConstants.COLUMN_U_UD);
		String u_sd = map.get(HbaseConstants.COLUMN_U_SD);
		String c_time = map.get(HbaseConstants.COLUMN_C_TIME);
		String b_iev = map.get(HbaseConstants.COLUMN_B_IEV); 
		String u_mid = map.get(HbaseConstants.COLUMN_U_MID);
		String en = map.get(HbaseConstants.COLUMN_EN);
		String p_ref = map.get(HbaseConstants.COLUMN_P_REF); 
		String oid = map.get(HbaseConstants.COLUMN_OID); 
		String on = map.get(HbaseConstants.COLUMN_ON); 
		String cua = map.get(HbaseConstants.COLUMN_CUA); 
		
		String rowKey = genrateKey(ip, p_url, u_ud, u_sd, c_time, b_iev, u_mid, en, p_ref, oid, on, cua);
		Put put = new Put(Bytes.toBytes(rowKey));
		
		if(ip!=null){
			put.addColumn(Bytes.toBytes(HbaseConstants.FAMILY), Bytes.toBytes(HbaseConstants.COLUMN_IP), Bytes.toBytes(ip));
		}
		if(b_iev!=null){
			put.addColumn(Bytes.toBytes(HbaseConstants.FAMILY), Bytes.toBytes(HbaseConstants.COLUMN_B_IEV), Bytes.toBytes(b_iev));
		}
		if(c_time!=null){
			put.addColumn(Bytes.toBytes(HbaseConstants.FAMILY), Bytes.toBytes(HbaseConstants.COLUMN_C_TIME), Bytes.toBytes(c_time));
		}
		if(en!=null){
			put.addColumn(Bytes.toBytes(HbaseConstants.FAMILY), Bytes.toBytes(HbaseConstants.COLUMN_EN), Bytes.toBytes(en));
		}
		if(p_url!=null){
			put.addColumn(Bytes.toBytes(HbaseConstants.FAMILY), Bytes.toBytes(HbaseConstants.COLUMN_P_URL), Bytes.toBytes(p_url));
		}
		if(u_mid!=null){
			put.addColumn(Bytes.toBytes(HbaseConstants.FAMILY), Bytes.toBytes(HbaseConstants.COLUMN_U_MID), Bytes.toBytes(u_mid));
		}
		if(u_sd!=null){
			put.addColumn(Bytes.toBytes(HbaseConstants.FAMILY), Bytes.toBytes(HbaseConstants.COLUMN_U_SD), Bytes.toBytes(u_sd));
		}
		if(u_ud!=null){
			put.addColumn(Bytes.toBytes(HbaseConstants.FAMILY), Bytes.toBytes(HbaseConstants.COLUMN_U_UD), Bytes.toBytes(u_ud));
		}
		if(p_ref!=null){
			put.addColumn(Bytes.toBytes(HbaseConstants.FAMILY), Bytes.toBytes(HbaseConstants.COLUMN_P_REF), Bytes.toBytes(p_ref));
		}
		if(oid!=null){
			put.addColumn(Bytes.toBytes(HbaseConstants.FAMILY), Bytes.toBytes(HbaseConstants.COLUMN_OID), Bytes.toBytes(oid));
		}
		if(on!=null){
			put.addColumn(Bytes.toBytes(HbaseConstants.FAMILY), Bytes.toBytes(HbaseConstants.COLUMN_ON), Bytes.toBytes(on));
		}
		if(cua!=null){
			put.addColumn(Bytes.toBytes(HbaseConstants.FAMILY), Bytes.toBytes(HbaseConstants.COLUMN_CUA), Bytes.toBytes(cua));
		}
		
		try {
			context.write(NullWritable.get(), put);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	private String genrateKey(String ip, String p_url, String u_ud, String u_sd, String c_time, String b_iev, String u_mid, String en, String p_ref, String oid, String on, String cua) {
		crc32.reset();
		if(!StringUtils.isEmpty(ip)){
			crc32.update(Bytes.toBytes(ip));
		}
		if(!StringUtils.isEmpty(p_url)){
			crc32.update(Bytes.toBytes(p_url));
		}
		if(!StringUtils.isEmpty(u_ud)){
			crc32.update(Bytes.toBytes(u_ud));
		}
		if(!StringUtils.isEmpty(u_sd)){
			crc32.update(Bytes.toBytes(u_sd));
		}
		if(!StringUtils.isEmpty(c_time)){
			crc32.update(Bytes.toBytes(c_time));
		}
		if(!StringUtils.isEmpty(b_iev)){
			crc32.update(Bytes.toBytes(b_iev));
		}
		if(!StringUtils.isEmpty(en)){
			crc32.update(Bytes.toBytes(en));
		}
		if(!StringUtils.isEmpty(u_mid)){
			crc32.update(Bytes.toBytes(u_mid));
		}
		if(!StringUtils.isEmpty(p_ref)){
			crc32.update(Bytes.toBytes(p_ref));
		}
		if(!StringUtils.isEmpty(oid)){
			crc32.update(Bytes.toBytes(oid));
		}
		if(!StringUtils.isEmpty(on)){
			crc32.update(Bytes.toBytes(on));
		}
		if(!StringUtils.isEmpty(cua)){
			crc32.update(Bytes.toBytes(cua));
		}
		String rowKey = c_time + "_" + crc32.getValue();
		return rowKey;
	}

	@SuppressWarnings("deprecation")
	private void handlerLine(String val, Map<String, String> map) {
		String[] split = val.split("&");
		for (String s : split) {
			String[] s2 = s.split("=");
			if("ip".equals(s2[0])){
				map.put(HbaseConstants.COLUMN_IP, s2[1]);
			}
			if("p_url".equals(s2[0])){
				String url = URLDecoder.decode(s2[1]);
				map.put(HbaseConstants.COLUMN_P_URL, url);
			}
			if("u_ud".equals(s2[0])){
				map.put(HbaseConstants.COLUMN_U_UD, s2[1]);
			}
			if("u_sd".equals(s2[0])){
				map.put(HbaseConstants.COLUMN_U_SD, s2[1]);
			}
			if("c_time".equals(s2[0])){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date = sdf.format(new Date(Long.parseLong(s2[1])));
				map.put(HbaseConstants.COLUMN_C_TIME, date);
			}
			if("b_iev".equals(s2[0])){
				String agentDecoder=URLDecoder.decode(s2[1]);
				UserAgentInfo userAgentInfo=UserAgentUtils.getUserAgentInfo(agentDecoder);
				map.put(HbaseConstants.COLUMN_B_IEV, userAgentInfo.getUaFamily());
			}
			if("u_mid".equals(s2[0])){
				map.put(HbaseConstants.COLUMN_U_MID, s2[1]);
			}
			if("en".equals(s2[0])){
				map.put(HbaseConstants.COLUMN_EN, s2[1]);
			}
			if("p_ref".equals(s2[0])){
				String url = URLDecoder.decode(s2[1]);
				map.put(HbaseConstants.COLUMN_P_REF, url);
			}
			if("oid".equals(s2[0])){
				map.put(HbaseConstants.COLUMN_OID, s2[1]);
			}
			if("on".equals(s2[0])){
				String str = null;
				try {
					str = URLDecoder.decode(s2[1],"UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				map.put(HbaseConstants.COLUMN_ON, str);
			}
			if("cua".equals(s2[0])){
				map.put(HbaseConstants.COLUMN_CUA, s2[1]);
			}
			
		}
	}
}
