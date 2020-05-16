package io.zhenglei.log.dimetion;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class StringStringDimetion implements WritableComparable<StringStringDimetion> {

	private String url;
	private String ud;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUd() {
		return ud;
	}
	public void setUd(String ud) {
		this.ud = ud;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(url);
		out.writeUTF(ud);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.url = in.readUTF();
		this.ud = in.readUTF();
	}

	@Override
	public int compareTo(StringStringDimetion o) {
		if(this==o){
			return 0;
		}
		int tmp = this.getUd().compareTo(o.getUd());
		if(tmp!=0){
			return tmp;
		}
		tmp = this.getUrl().compareTo(o.getUrl());
		if(tmp!=0){
			return tmp;
		}
		return 0;
	}
	
}
