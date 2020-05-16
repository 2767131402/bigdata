package io.zhenglei.log.dimetion;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class LongLongDimetion implements WritableComparable<LongLongDimetion> {

	private Long pvNum;
	private Long brNum;
	
	public Long getPvNum() {
		return pvNum;
	}

	public void setPvNum(Long pvNum) {
		this.pvNum = pvNum;
	}

	public Long getBrNum() {
		return brNum;
	}

	public void setBrNum(Long brNum) {
		this.brNum = brNum;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeLong(pvNum);
		out.writeLong(brNum);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.pvNum = in.readLong();
		this.brNum = in.readLong();
	}

	@Override
	public int compareTo(LongLongDimetion arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}
