package io.zhenglei.log.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormateUtils {
	
	public static String format(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
}
