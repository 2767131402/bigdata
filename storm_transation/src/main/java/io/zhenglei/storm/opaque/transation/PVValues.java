package io.zhenglei.storm.opaque.transation;

import java.io.Serializable;

public class PVValues implements Serializable {
	private static final long serialVersionUID = -4384201209898883604L;
	private int count;
	private int pre;
	private int txid;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPre() {
		return pre;
	}

	public void setPre(int pre) {
		this.pre = pre;
	}

	public int getTxid() {
		return txid;
	}

	public void setTxid(int txid) {
		this.txid = txid;
	}
}
