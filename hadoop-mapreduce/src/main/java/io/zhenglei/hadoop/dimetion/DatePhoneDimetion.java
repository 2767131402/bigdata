package io.zhenglei.hadoop.dimetion;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class DatePhoneDimetion extends BaseDimetion {

	private String mobileDate;
	private String mobilePhone;
	public String getMobileDate() {
		return mobileDate;
	}
	public void setMobileDate(String mobileDate) {
		this.mobileDate = mobileDate;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(mobileDate);
		out.writeUTF(mobilePhone);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.mobileDate = in.readUTF();
		this.mobilePhone = in.readUTF();
	}

	@Override
	public int compareTo(BaseDimetion o) {
		if(this == o){
			return 0;
		}
		DatePhoneDimetion datePhoneDimetion = (DatePhoneDimetion) o;
		int tmp = this.mobileDate.compareTo(datePhoneDimetion.mobileDate);
		if(tmp != 0){
			return tmp;
		}
		tmp = this.mobilePhone.compareTo(datePhoneDimetion.mobilePhone);
		if(tmp != 0){
			return tmp;
		}
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mobileDate == null) ? 0 : mobileDate.hashCode());
		result = prime * result + ((mobilePhone == null) ? 0 : mobilePhone.hashCode());
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
		DatePhoneDimetion other = (DatePhoneDimetion) obj;
		if (mobileDate == null) {
			if (other.mobileDate != null)
				return false;
		} else if (!mobileDate.equals(other.mobileDate))
			return false;
		if (mobilePhone == null) {
			if (other.mobilePhone != null)
				return false;
		} else if (!mobilePhone.equals(other.mobilePhone))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return mobileDate + "\t\t" + mobilePhone;
	}
	
}
