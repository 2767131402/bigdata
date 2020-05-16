package io.zhenglei.log.dimetion;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class StringStringDimetion implements WritableComparable<StringStringDimetion> {

	private String arg0;
	private String arg1;
	public String getArg0() {
		return arg0;
	}
	public void setArg0(String arg0) {
		this.arg0 = arg0;
	}
	public String getArg1() {
		return arg1;
	}
	public void setArg1(String arg1) {
		this.arg1 = arg1;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(arg0);
		out.writeUTF(arg1);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.arg0 = in.readUTF();
		this.arg1 = in.readUTF();
	}

	@Override
	public int compareTo(StringStringDimetion o) {
		if(this == o){
			return 0;
		}
		int tmp = this.getArg0().compareTo(o.getArg0());
		if(tmp!=0){
			return tmp;
		}
		tmp = this.getArg1().compareTo(o.getArg1());
		if(tmp!=0){
			return tmp;
		}
		return 0;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arg0 == null) ? 0 : arg0.hashCode());
		result = prime * result + ((arg1 == null) ? 0 : arg1.hashCode());
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
		StringStringDimetion other = (StringStringDimetion) obj;
		if (arg0 == null) {
			if (other.arg0 != null)
				return false;
		} else if (!arg0.equals(other.arg0))
			return false;
		if (arg1 == null) {
			if (other.arg1 != null)
				return false;
		} else if (!arg1.equals(other.arg1))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return arg0 + "\t" + arg1;
	}

}
