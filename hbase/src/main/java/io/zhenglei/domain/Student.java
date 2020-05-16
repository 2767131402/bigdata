package io.zhenglei.domain;

public class Student {
	private String stuKey;
	private String stuName;
	private String stuSex;
	
	public Student() {
	}
	
	public Student(String stuName, String stuSex) {
		this.stuName = stuName;
		this.stuSex = stuSex;
	}

	public String getStuKey() {
		return stuKey;
	}

	public void setStuKey(String stuKey) {
		this.stuKey = stuKey;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getStuSex() {
		return stuSex;
	}

	public void setStuSex(String stuSex) {
		this.stuSex = stuSex;
	}

}
