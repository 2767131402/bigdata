package com.oracle.dimention;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class DateDimention extends BaseDimetion {

	private int day;
    private int type;
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getDay() {
		return day;
	}

	public DateDimention() {
	}

	public DateDimention(int day, int season, int year, int month) {
		super();
		this.day = day;
		this.season = season;
		this.year = year;
		this.month = month;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getSeason() {
		return season;
	}

	public void setSeason(int season) {
		this.season = season;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	private int season;
	private int year;
	private int month;

	@Override
	public void readFields(DataInput arg0) throws IOException {
		this.year = arg0.readInt();
		this.season = arg0.readInt();
		this.month = arg0.readInt();
		this.day = arg0.readInt();
		this.type = arg0.readInt();
	}

	@Override
	public void write(DataOutput arg0) throws IOException {
		// TODO Auto-generated method stub
		arg0.writeInt(year);
		arg0.writeInt(season);
		arg0.writeInt(month);
		arg0.writeInt(day);
		arg0.writeInt(type);
	}

	@Override
	public int hashCode() {
		return this.day + this.month + this.year+this.type;
	}

	@Override
	public int compareTo(BaseDimetion o) {
		if (this == o) {
			return 0;
		}
		int tmp = 0;
		DateDimention dateDime = (DateDimention) o;		
		tmp = Integer.compare(this.year, dateDime.year);
		if (tmp != 0)
			return tmp;
		tmp = Integer.compare(this.season, dateDime.season);
		if (tmp != 0)
			return tmp;
		tmp = Integer.compare(this.month, dateDime.month);
		if (tmp != 0)
			return tmp;
		tmp = Integer.compare(this.day, dateDime.day);
		if (tmp != 0)
			return tmp;
		
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.year+" "+this.season+" "+this.month+" "+this.day;
	}
}
