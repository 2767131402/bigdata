package io.zhenglei.log.mapper;

import java.io.IOException;
import java.net.URLDecoder;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import io.zhenglei.log.dimetion.TimeUudDimetion;
import io.zhenglei.log.dimetion.UserOutputDimetion;
import io.zhenglei.log.utils.browser.BrowserUtils;
import io.zhenglei.log.utils.ip.IPSeekerExt;
import io.zhenglei.log.utils.ip.IPSeekerExt.RegionInfo;
/**
 * 将日志中的用户信息保存到数据库
 * @author ii_zh
 *
 */
public class UserFormatMapper extends Mapper<LongWritable, Text, TimeUudDimetion, UserOutputDimetion> {
	
	TimeUudDimetion dimetion = new TimeUudDimetion();
	UserOutputDimetion output = new UserOutputDimetion();
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, TimeUudDimetion, UserOutputDimetion>.Context context)
			throws IOException, InterruptedException {
		String splitStr = value.toString().split("en=")[1];
		String str = splitStr.substring(0, splitStr.indexOf("&"));
		
		String time = value.toString().split("c_time=")[1];
		String date = time.substring(0, time.indexOf("&"));
		
		String[] uud = value.toString().split("u_ud=");
		String ud = null;
		if(uud.length>1){
			ud = uud[1].substring(0, uud[1].indexOf("&"));
		}
		
		String[] umid = value.toString().split("u_mid=");
		String mid = null;
		if(umid.length>1){
			mid = umid[1].substring(0, umid[1].indexOf("&"));
		}else{
			mid = "";
		}
		
		String[] newvip = value.toString().split("p_url=");
		String vip = null;
		if(newvip.length>1){
			vip = newvip[1].substring(0, newvip[1].indexOf("&"));
		}else{
			vip = "";
		}
		
		String uip[] = value.toString().split("u_ip=");
		String ip = null;
		RegionInfo info = null;
		if(uip.length>1){
			ip = uip[1].substring(0, uip[1].indexOf("&"));
			IPSeekerExt ipSeekerExt = new IPSeekerExt();
			info = ipSeekerExt.analyticIp(ip);
		}
		
		String[] nbrowser = value.toString().split("b_iev=");
		String browser = null;
		if(nbrowser.length>1){
			browser = nbrowser[1].substring(0, nbrowser[1].indexOf("&"));
		}else{
			browser = "";
		}
		
		
		if("e_l".equals(str) && date!=null && ud!=null && str!=null){
			output.setuNewadd(true);
			dimetion.setEn(str);
			dimetion.setTime(Long.parseLong(date));
			output.setuMid(mid);
			output.setuUd(ud);
			output.setuTime(Long.parseLong(date));
			if(URLDecoder.decode("http%3A%2F%2F172.16.0.150%3A8080%2FBIG_DATA_LOG2%2Fdemo4.jsp").equals(URLDecoder.decode(vip))){
				output.setuNewvip(true);
			} else{
				output.setuNewvip(false);
			}
			output.setuCountry(info.getCountry());
			output.setuProvince(info.getProvince());
			output.setuCity(info.getCity());
			output.setBrowser(BrowserUtils.getBrowser(browser));
			output.setIp(ip);
			
			context.write(dimetion, output);
		}
		if("e_pv".equals(str) && date!=null && ud!=null && str!=null){
			output.setuNewadd(false);
			dimetion.setEn(str);
			dimetion.setTime(Long.parseLong(date));
			output.setuMid(mid);
			output.setuUd(ud);
			output.setuTime(Long.parseLong(date));
			if(URLDecoder.decode("http%3A%2F%2F172.16.0.150%3A8080%2FBIG_DATA_LOG2%2Fdemo4.jsp").equals(URLDecoder.decode(vip))){
				output.setuNewvip(true);
			}else{
				output.setuNewvip(false);
			}
			output.setuCountry(info.getCountry());
			output.setuProvince(info.getProvince());
			output.setuCity(info.getCity());
			output.setBrowser(BrowserUtils.getBrowser(browser));
			output.setIp(ip);
			
			context.write(dimetion, output);
		}
	}
}
