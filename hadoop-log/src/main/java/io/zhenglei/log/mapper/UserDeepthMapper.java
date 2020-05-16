package io.zhenglei.log.mapper;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import io.zhenglei.log.dimetion.StringStringDimetion;
import io.zhenglei.log.utils.DateFormatUtils;

public class UserDeepthMapper extends Mapper<LongWritable, Text, StringStringDimetion, Text> {
	StringStringDimetion dimetion = new StringStringDimetion();
	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, StringStringDimetion, Text>.Context context)
			throws IOException, InterruptedException {
		
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
		
		String[] uud = value.toString().split("u_ud=");
		String ud = null;
		if(uud.length>1){
			ud = uud[1].substring(0, uud[1].indexOf("&"));
		}
		
		if(ud!=null && vip!=null && time!=null){
			dimetion.setUd(ud);
			dimetion.setUrl(DateFormatUtils.format1(Long.parseLong(time)));
			context.write(dimetion, new Text(vip));
		}
		
	}
}
