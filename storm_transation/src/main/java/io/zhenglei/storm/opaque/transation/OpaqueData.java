package io.zhenglei.storm.opaque.transation;

import java.io.Serializable;

public class OpaqueData implements Serializable {
	private static final long serialVersionUID = -2160961854670139733L;

	private int num;
	private int startPoint;
	
	public OpaqueData(){
		
	}
	
	public OpaqueData(int num, int startPoint) {
		this.num = num;
		this.startPoint = startPoint;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(int startPoint) {
		this.startPoint = startPoint;
	}

}
