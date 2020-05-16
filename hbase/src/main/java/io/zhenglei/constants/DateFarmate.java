package io.zhenglei.constants;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFarmate {
	private static SimpleDateFormat SIMPLEDATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static String format(){
		return SIMPLEDATEFORMAT.format(new Date());
	}
}
