package io.zhenglei.log.dimetion;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class String4Boolean2 implements WritableComparable<String4Boolean2> {
	private String u_ud;
	private String u_mid = "";
	private String ip;
	private String browser;
	private Boolean newuser;
	private Boolean newvip;
	public String getU_ud() {
		return u_ud;
	}
	public void setU_ud(String u_ud) {
		this.u_ud = u_ud;
	}
	public String getU_mid() {
		return u_mid;
	}
	public void setU_mid(String u_mid) {
		this.u_mid = u_mid;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public Boolean getNewuser() {
		return newuser;
	}
	public void setNewuser(Boolean newuser) {
		this.newuser = newuser;
	}
	public Boolean getNewvip() {
		return newvip;
	}
	public void setNewvip(Boolean newvip) {
		this.newvip = newvip;
	}
	
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(u_ud);
		out.writeUTF(u_mid);
		out.writeUTF(ip);
		out.writeUTF(browser);
		out.writeBoolean(newuser);
		out.writeBoolean(newvip);
	}
	@Override
	public void readFields(DataInput in) throws IOException {
		this.u_ud = in.readUTF();
		this.u_mid = in.readUTF();
		this.ip = in.readUTF();
		this.browser = in.readUTF();
		this.newuser = in.readBoolean();
		this.newvip = in.readBoolean();
	}
	@Override
	public int compareTo(String4Boolean2 o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
