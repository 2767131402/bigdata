package com.oracle.time.utils;

import java.util.Calendar;
import java.util.Date;

import com.oracle.time.common.DateType;

public class TimUtils {
	public static int getTime(long time, int type) {
		int timeValue = 0;
		Calendar timeCalentdar = Calendar.getInstance();
		timeCalentdar.setTimeInMillis(time);
		switch (type) {
		case DateType.day:
			timeValue = timeCalentdar.get(Calendar.DAY_OF_MONTH);
			break;
		case DateType.year:
			timeValue = timeCalentdar.get(Calendar.YEAR);
			break;
		case DateType.month:
			timeValue = timeCalentdar.get(Calendar.MONTH) + 1;
			break;
		case DateType.season:
			int month = timeCalentdar.get(Calendar.MONTH) + 1;
			if (month % 3 == 0) {
				timeValue = month / 3;
			} else {
				timeValue = month / 3 + 1;
			}
			break;

		default:
			throw new RuntimeException("没无此日期类型!");

		}
		return timeValue;
	}

	public static void main(String[] args) {
		System.out.println("xxaasx...xx");
		// System.out.println(System.currentTimeMillis());
		long xx = getTime(Long.parseLong("1363157993055"), DateType.month);
		System.out.println(xx);
		long xxx = getTime(Long.parseLong("1363157992093"), DateType.day);
		System.out.println(xxx);
	}
}
