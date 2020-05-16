package io.zhenglei.storm.spout.trident;

public class MyData {
	private int num;
	private int startPoint;

	public MyData(){
		
	}
	
	public MyData(int num, int startPoint) {
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
