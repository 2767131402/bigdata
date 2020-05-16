package io.zhenglei.storm.domain;

import java.io.Serializable;

public class MateData implements Serializable {
	private static final long serialVersionUID = -2160961854670139733L;
	/**
	 * 开始位置
	 */
	private int startPoint;
	/**
	 * 一批有多少条数据
	 */
	private int bach_num;

	public MateData() {
	}

	public MateData(int startPoint, int bach_num) {
		this.startPoint = startPoint;
		this.bach_num = bach_num;
	}

	public int getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(int startPoint) {
		this.startPoint = startPoint;
	}

	public int getBach_num() {
		return bach_num;
	}

	public void setBach_num(int bach_num) {
		this.bach_num = bach_num;
	}

}
