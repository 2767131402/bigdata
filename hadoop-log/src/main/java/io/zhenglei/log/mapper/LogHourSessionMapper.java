package io.zhenglei.log.mapper;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import io.zhenglei.log.dimetion.UsdUudTimeDimetion;
import io.zhenglei.log.utils.DateFormatUtils;

public class LogHourSessionMapper extends Mapper<LongWritable, Text, Text, UsdUudTimeDimetion> {
	UsdUudTimeDimetion dimetion = new UsdUudTimeDimetion();
	
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, UsdUudTimeDimetion>.Context context)
			throws IOException, InterruptedException {
		String[] uud = value.toString().split("u_ud=");
		String ud = null;
		if(uud.length>1){
			ud = uud[1].substring(0, uud[1].indexOf("&"));
		}
		
		String[] usd = value.toString().split("u_sd=");
		String sd = null;
		if(usd.length>1){
			sd = usd[1].substring(0, usd[1].indexOf("&"));
		}
		
		String[] ctime = value.toString().split("c_time=");
		String time = null;
		if(ctime.length>1){
			time = ctime[1].substring(0, ctime[1].indexOf("&"));
		}
		
		if(ud!=null && sd!=null && time!=null){
			dimetion.setUsd(sd);
			dimetion.setUud(ud);
			dimetion.setTime(Long.parseLong(time));
			context.write(new Text(DateFormatUtils.format2(Long.parseLong(time))), dimetion);
		}
	}
}
