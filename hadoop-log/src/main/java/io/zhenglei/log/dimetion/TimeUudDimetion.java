package io.zhenglei.log.dimetion;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class TimeUudDimetion implements WritableComparable<TimeUudDimetion> {

	private Long time;
	private String en;
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public String getEn() {
		return en;
	}
	public void setEn(String en) {
		this.en = en;
	}
	
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeLong(time);
		out.writeUTF(en);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.time = in.readLong();
		this.en = in.readUTF();
	}

	@Override
	public int compareTo(TimeUudDimetion o) {
		if(this==o){
			return 0;
		}
		int tmp = this.getEn().compareTo(o.getEn());
		if(tmp!=0){
			return tmp;
		}
		tmp = this.getTime().compareTo(o.getTime());
		if(tmp!=0){
			return tmp;
		}
		return 0;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((en == null) ? 0 : en.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimeUudDimetion other = (TimeUudDimetion) obj;
		if (en == null) {
			if (other.en != null)
				return false;
		} else if (!en.equals(other.en))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}
}
