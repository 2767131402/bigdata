package io.zhenglei.hadoop.dimetion.tell;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import io.zhenglei.hadoop.dimetion.DatePhoneDimetion;
import io.zhenglei.hadoop.dimetion.UpDownDimetion;
import io.zhenglei.hadoop.utils.DateFormiteUtils;

public class LogMapper extends Mapper<LongWritable, Text, DatePhoneDimetion, UpDownDimetion> {
	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, DatePhoneDimetion, UpDownDimetion>.Context context)
			throws IOException, InterruptedException {

		String[] str = value.toString().split("##");
		String date = str[0];
		String phone = str[1];
		String up = str[8];
		String down = str[9];
		
		DatePhoneDimetion datePhoneDimetion = new DatePhoneDimetion();
		datePhoneDimetion.setMobileDate(DateFormiteUtils.formatDate(date));
		datePhoneDimetion.setMobilePhone(phone);
		
		UpDownDimetion upDownDimetion = new UpDownDimetion(Long.parseLong(up), Long.parseLong(down));
		context.write(datePhoneDimetion, upDownDimetion);
	}
}
