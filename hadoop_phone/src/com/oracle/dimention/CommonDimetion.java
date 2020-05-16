package com.oracle.dimention;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class CommonDimetion extends BaseDimetion {

	private DateDimention dateDimetion=new DateDimention();
	private String phone;
	public DateDimention getDateDimetion() {
		return dateDimetion;
	}

	public CommonDimetion()
	{
		super();
	}
	public CommonDimetion(DateDimention dateDimetion,String phone)
	{
		super();
		this.dateDimetion=dateDimetion;
		this.phone=phone;
	}
	public void setDateDimetion(DateDimention dateDimetion) {
		this.dateDimetion = dateDimetion;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	

	@Override
	public void readFields(DataInput arg0) throws IOException {
		
		this.dateDimetion.readFields(arg0);
		this.phone = arg0.readUTF();
	}

	@Override
	public void write(DataOutput arg0) throws IOException {
		this.dateDimetion.write(arg0);
		arg0.writeUTF(phone);
	}

	@Override
	public int compareTo(BaseDimetion o) {
		// TODO Auto-generated method stub
		if (this == o) {
			return 0;
		}
		CommonDimetion other=(CommonDimetion)o;
		int tmp=this.dateDimetion.compareTo(other.getDateDimetion());
		if(tmp!=0)
			return tmp;
		tmp=this.phone.compareTo(other.phone);
		if(tmp!=0)
			return tmp;
		
		return 0;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.dateDimetion.toString()+" "+this.phone;
	}
}
