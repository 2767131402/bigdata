package io.zhenglei.log.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtils {

	public static Date format(String time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(time);
		} catch (ParseException e) {
			return null;
		}
	}
	public static String format1(Long time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date(time));
	}
	
	public static String format2(Long time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
		return sdf.format(new Date(time));
	}
	
}
