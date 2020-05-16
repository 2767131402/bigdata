package io.zhenglei.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateformatUtils {

	public static String format(String time) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			return format.format(format.parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public static void main(String[] args) {
		String s = format("2000-10-10 10:10:10");
		System.out.println(s);
	}
}
