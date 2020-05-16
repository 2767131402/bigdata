package io.zhenglei.log.dimetion;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class TimeUsdDimetion implements WritableComparable<TimeUsdDimetion> {

	private String time;
	private String usd;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getUsd() {
		return usd;
	}
	public void setUsd(String usd) {
		this.usd = usd;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(time);
		out.writeUTF(usd);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.time = in.readUTF();
		this.usd = in.readUTF();
	}

	@Override
	public int compareTo(TimeUsdDimetion o) {
		if(this==o){
			return 0;
		}
		int tmp = this.getTime().compareTo(o.getTime());
		if(tmp!=0){
			return tmp;
		}
		tmp = this.getUsd().compareTo(o.getUsd());
		if(tmp!=0){
			return tmp;
		}
		return 0;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((usd == null) ? 0 : usd.hashCode());
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
		TimeUsdDimetion other = (TimeUsdDimetion) obj;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (usd == null) {
			if (other.usd != null)
				return false;
		} else if (!usd.equals(other.usd))
			return false;
		return true;
	}
	
}
