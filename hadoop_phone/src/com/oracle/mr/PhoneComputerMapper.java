package com.oracle.mr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.oracle.dimention.CommonDimetion;
import com.oracle.dimention.DateDimention;
import com.oracle.dimention.ResultValue;
import com.oracle.time.common.DateType;
import com.oracle.time.utils.TimUtils;

public class PhoneComputerMapper extends Mapper<LongWritable, Text, CommonDimetion, ResultValue> {
	

	CommonDimetion commonDimetion = new CommonDimetion();	
	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, CommonDimetion, ResultValue>.Context context)
			throws IOException, InterruptedException {
		
		String str = value.toString();
		String info[] = str.split("\t");
		long time = Long.valueOf(info[0].trim());
		int up = Integer.parseInt(info[8]);
		int down = Integer.parseInt(info[9]);
		String phone = info[1];	
		
		List<DateDimention> list = getDateDimetions(time);
		ResultValue rv = new ResultValue(up + down, up, down);
		for (DateDimention date : list) {
			commonDimetion.setDateDimetion(date);						
			commonDimetion.setPhone(phone);
			context.write(commonDimetion, rv);
		}
	}

	public List<DateDimention> getDateDimetions(long time) {

		int day = TimUtils.getTime(time, DateType.day);
		int season = TimUtils.getTime(time, DateType.season);
		int year = TimUtils.getTime(time, DateType.year);
		int month = TimUtils.getTime(time, DateType.month);
		
		DateDimention dim = new DateDimention(day, 0, year, month);
		List<DateDimention> list = new ArrayList<DateDimention>();
		
		dim.setType(DateType.day);
		list.add(dim);
		
		DateDimention dim1 = new DateDimention(0, season, year, 0);
		dim1.setType(DateType.season);
		list.add(dim1);
		
		
		DateDimention dim2 = new DateDimention(0, 0, year, month);
		dim2.setType(DateType.month);
		list.add(dim2);
		
		DateDimention dim3 = new DateDimention(0, 0, year, 0);
		dim3.setType(DateType.year);
		list.add(dim3);
		return list;
	}
}
