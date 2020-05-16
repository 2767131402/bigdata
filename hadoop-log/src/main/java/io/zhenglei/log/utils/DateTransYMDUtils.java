package io.zhenglei.log.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class DateTransYMDUtils {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public static int getYear(String time){
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf.parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar.get(Calendar.YEAR);
		
	}
	public static int getMonth(String time){
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf.parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar.get(Calendar.MONTH)+1;
	}
	public static int getDay(String time){
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf.parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar.get(Calendar.DATE);
	}
	public static int getHour(String time){
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf.parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
}
