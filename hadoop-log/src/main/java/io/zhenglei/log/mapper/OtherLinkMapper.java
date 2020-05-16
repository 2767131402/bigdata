package io.zhenglei.log.mapper;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class OtherLinkMapper extends Mapper<LongWritable, Text, Text, Text> {
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		
		String[] nbrowser = value.toString().split("b_iev=");
		String browser = null;
		if(nbrowser.length>1){
			browser = nbrowser[1].substring(0, nbrowser[1].indexOf("&"));
		}
		
		String[] pref = value.toString().split("p_ref=");
		String ref = null;
		if(pref.length>1){
			ref = pref[1].substring(0, pref[1].indexOf("&"));
		}
		
		String[] ctime = value.toString().split("c_time=");
		String time = null;
		if(ctime.length>1){
			time = ctime[1].substring(0, ctime[1].indexOf("&"));
		}
		
		String[] usd = value.toString().split("u_sd=");
		String sd = null;
		if(usd.length>1){
			sd = usd[1].substring(0, usd[1].indexOf("&"));
		}
		
		if(sd!=null && time!=null && ref!=null && browser!=null){
			
		}
	}
}
