package io.zhenglei.log.dimetion;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class UsdUudTimeDimetion implements WritableComparable<UsdUudTimeDimetion> {

	private String usd;
	private String uud;
	private Long time;
	public String getUsd() {
		return usd;
	}
	public void setUsd(String usd) {
		this.usd = usd;
	}
	public String getUud() {
		return uud;
	}
	public void setUud(String uud) {
		this.uud = uud;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(usd);
		out.writeUTF(uud);
		out.writeLong(time);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.usd = in.readUTF();
		this.uud = in.readUTF();
		this.time = in.readLong();
	}

	@Override
	public int compareTo(UsdUudTimeDimetion o) {
		return 0;
	}

}
