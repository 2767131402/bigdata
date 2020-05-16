package io.zhenglei.log.mapper;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import io.zhenglei.log.dimetion.LongStringDimetion;
import io.zhenglei.log.dimetion.TimeUsdDimetion;
import io.zhenglei.log.utils.DateFormatUtils;
import io.zhenglei.log.utils.browser.BrowserUtils;

public class LogSessionMapper extends Mapper<LongWritable, Text, TimeUsdDimetion, LongStringDimetion> {
	TimeUsdDimetion dimetion = new TimeUsdDimetion();
	LongStringDimetion ldimetion = new LongStringDimetion();
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, TimeUsdDimetion, LongStringDimetion>.Context context)
			throws IOException, InterruptedException {
		
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
		
		String[] nbrowser = value.toString().split("b_iev=");
		String browser = null;
		if(nbrowser.length>1){
			browser = nbrowser[1].substring(0, nbrowser[1].indexOf("&"));
		}else{
			browser = "";
		}
		
		if(sd!=null && time!=null){
			dimetion.setUsd(sd);
			dimetion.setTime(DateFormatUtils.format1(Long.parseLong(time)));
			ldimetion.setTime(Long.parseLong(time));
			ldimetion.setBrowser(BrowserUtils.getBrowser(browser));
			context.write(dimetion, ldimetion);
		}
	}
}
