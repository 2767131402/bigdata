package io.zhenglei.log.dimetion;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class StringLongLongDimetion implements WritableComparable<StringLongLongDimetion> {

	private String time;
	private Long tiaoNum;
	private Long pvNum;
	private String area;
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Long getTiaoNum() {
		return tiaoNum;
	}
	public void setTiaoNum(Long tiaoNum) {
		this.tiaoNum = tiaoNum;
	}
	public Long getPvNum() {
		return pvNum;
	}
	public void setPvNum(Long pvNum) {
		this.pvNum = pvNum;
	}
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(time);
		out.writeLong(tiaoNum);
		out.writeLong(pvNum);
		out.writeUTF(area);
	}
	@Override
	public void readFields(DataInput in) throws IOException {
		this.time = in.readUTF();
		this.tiaoNum = in.readLong();
		this.pvNum = in.readLong();
		this.area = in.readUTF();
	}
	@Override
	public int compareTo(StringLongLongDimetion arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
