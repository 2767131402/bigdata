package com.oracle.mr;

import java.io.IOException;
import org.apache.hadoop.mapreduce.Reducer;
import com.oracle.dimention.CommonDimetion;
import com.oracle.dimention.ResultValue;

public class PhoneComputerReducer
		extends org.apache.hadoop.mapreduce.Reducer<CommonDimetion, ResultValue, CommonDimetion, ResultValue> {

	@Override
	protected void reduce(CommonDimetion arg0, Iterable<ResultValue> arg1,
			Reducer<CommonDimetion, ResultValue, CommonDimetion, ResultValue>.Context arg2)
			throws IOException, InterruptedException {

		int total=0;
		int up=0;
		int down=0;
		for(ResultValue rs:arg1)
		{
			total+=rs.getTotal();
			up+=rs.getUp();
			down+=rs.getDonw();
		}
		ResultValue rv=new ResultValue(total, up, down);
		arg2.write(arg0, rv);
	}
}
