package io.zhenglei.hadoop.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormiteUtils {
	private static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
	
	public static String formatDate(String date){
		return SDF.format(new Date(Long.valueOf(date).longValue()));
	}
}
