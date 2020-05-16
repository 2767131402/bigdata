package io.zhenglei.log.mapper;

import java.io.IOException;
import java.net.URLDecoder;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import io.zhenglei.log.dimetion.StringStringDimetion;
import io.zhenglei.log.utils.DateFormatUtils;
import io.zhenglei.log.utils.browser.BrowserUtils;

public class BrowserPvMapper extends Mapper<LongWritable, Text, StringStringDimetion, StringStringDimetion> {
	StringStringDimetion dimetion = new StringStringDimetion();
	StringStringDimetion sdimetion = new StringStringDimetion();
	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, StringStringDimetion, StringStringDimetion>.Context context)
			throws IOException, InterruptedException {
		String[] ctime = value.toString().split("c_time=");
		String time = null;
		if(ctime.length>1){
			time = ctime[1].substring(0, ctime[1].indexOf("&"));
		}
		
		String[] uud = value.toString().split("u_ud=");
		String ud = null;
		if(uud.length>1){
			ud = uud[1].substring(0, uud[1].indexOf("&"));
		}
		
		String[] newvip = value.toString().split("p_url=");
		String vip = null;
		if(newvip.length>1){
			vip = newvip[1].substring(0, newvip[1].indexOf("&"));
		}else{
			vip = "";
		}
		
		String[] nbrowser = value.toString().split("b_iev=");
		String browser = null;
		if(nbrowser.length>1){
			browser = nbrowser[1].substring(0, nbrowser[1].indexOf("&"));
		}else{
			browser = "";
		}
		
		if(time!=null && ud!=null && vip!=null && browser!=null){
			sdimetion.setUrl(BrowserUtils.getBrowser(browser));
			sdimetion.setUd(DateFormatUtils.format1(Long.parseLong(time)));
			dimetion.setUd(ud);
			dimetion.setUrl(URLDecoder.decode(vip));
			
			context.write(sdimetion, dimetion);
		}
	}
}
