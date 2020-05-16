package com.oracle.dimention;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class ResultValue implements Writable {

	private long total;
    public ResultValue()
    {
    	super();
    }
	public ResultValue(long total, long up, long donw) {
		super();
		this.total = total;
		this.up = up;
		this.donw = donw;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getUp() {
		return up;
	}

	public void setUp(long up) {
		this.up = up;
	}

	public long getDonw() {
		return donw;
	}

	public void setDonw(long donw) {
		this.donw = donw;
	}

	private long up;
	private long donw;

	@Override
	public void readFields(DataInput arg0) throws IOException {
		this.up = arg0.readLong();
		this.donw = arg0.readLong();
		this.total = arg0.readLong();
	}

	@Override
	public void write(DataOutput arg0) throws IOException {
		arg0.writeLong(up);
		arg0.writeLong(donw);
		arg0.writeLong(total);

	}
@Override
public String toString() {
	// TODO Auto-generated method stub
	return this.total+" "+this.up+" "+this.donw;
}
}
