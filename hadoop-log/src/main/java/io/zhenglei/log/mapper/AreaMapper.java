package io.zhenglei.log.mapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import io.zhenglei.log.utils.ip.IPSeekerExt;
import io.zhenglei.log.utils.ip.IPSeekerExt.RegionInfo;

public class AreaMapper extends Mapper<LongWritable, Text, Text, Text> {
	
	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		String uip[] = value.toString().split("u_ip=");
		String ip = null;
		RegionInfo info = null;
		if(uip.length>1){
			ip = uip[1].substring(0, uip[1].indexOf("&"));
			IPSeekerExt ipSeekerExt = new IPSeekerExt();
			info = ipSeekerExt.analyticIp(ip);
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
		
		String[] newvip = value.toString().split("p_url=");
		String vip = null;
		if(newvip.length>1){
			vip = newvip[1].substring(0, newvip[1].indexOf("&"));
		}
		
		if(ip!=null && sd!=null && time!=null && vip!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			time = sdf.format(new Date(Long.parseLong(time)));
			context.write(new Text(time+"&"+info.getProvince()), new Text(ip+"&"+sd+"&"+vip));
		}
	}
}
