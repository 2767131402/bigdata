package io.zhenglei.log.dimetion;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class LongStringDimetion implements WritableComparable<LongStringDimetion> {

	private Long time;
	private String browser;
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeLong(time);
		out.writeUTF(browser);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.time = in.readLong();
		this.browser = in.readUTF();
	}

	@Override
	public int compareTo(LongStringDimetion o) {
		if(this==o){
			return 0;
		}
		int tmp = this.getTime().compareTo(o.getTime());
		if(tmp!=0){
			return tmp;
		}
		tmp = this.getBrowser().compareTo(o.getBrowser());
		if(tmp!=0){
			return tmp;
		}
		return 0;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((browser == null) ? 0 : browser.hashCode());
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
		LongStringDimetion other = (LongStringDimetion) obj;
		if (browser == null) {
			if (other.browser != null)
				return false;
		} else if (!browser.equals(other.browser))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}
	
}
