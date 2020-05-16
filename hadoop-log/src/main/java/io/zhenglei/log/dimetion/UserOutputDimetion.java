package io.zhenglei.log.dimetion;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class UserOutputDimetion implements WritableComparable<UserOutputDimetion>  {
	private String uUd;
	private String uMid;
	private Long uTime;
	private Boolean uNewadd;
	private Boolean uNewvip;
	private String uCountry;
	private String uProvince;
	private String uCity;
	private String browser;
	private String ip;
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
	public String getuUd() {
		return uUd;
	}
	public void setuUd(String uUd) {
		this.uUd = uUd;
	}
	public String getuMid() {
		return uMid;
	}
	public void setuMid(String uMid) {
		this.uMid = uMid;
	}
	public Long getuTime() {
		return uTime;
	}
	public void setuTime(Long uTime) {
		this.uTime = uTime;
	}
	public Boolean getuNewadd() {
		return uNewadd;
	}
	public void setuNewadd(Boolean uNewadd) {
		this.uNewadd = uNewadd;
	}
	public Boolean getuNewvip() {
		return uNewvip;
	}
	public void setuNewvip(Boolean uNewvip) {
		this.uNewvip = uNewvip;
	}
	public String getuCountry() {
		return uCountry;
	}
	public void setuCountry(String uCountry) {
		this.uCountry = uCountry;
	}
	public String getuProvince() {
		return uProvince;
	}
	public void setuProvince(String uProvince) {
		this.uProvince = uProvince;
	}
	public String getuCity() {
		return uCity;
	}
	public void setuCity(String uCity) {
		this.uCity = uCity;
	}
	
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(uUd);
		out.writeLong(uTime);
		out.writeBoolean(uNewadd);
		out.writeBoolean(uNewvip);
		out.writeUTF(uMid);
		out.writeUTF(uCountry);
		out.writeUTF(uProvince);
		out.writeUTF(uCity);
		out.writeUTF(browser);
		out.writeUTF(ip);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.uUd = in.readUTF();
		this.uTime = in.readLong();
		this.uNewadd = in.readBoolean();
		this.uNewvip = in.readBoolean();
		this.uMid = in.readUTF();
		this.uCountry = in.readUTF();
		this.uProvince = in.readUTF();
		this.uCity = in.readUTF();
		this.browser = in.readUTF();
		this.ip = in.readUTF();
	}

	@Override
	public int compareTo(UserOutputDimetion o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
