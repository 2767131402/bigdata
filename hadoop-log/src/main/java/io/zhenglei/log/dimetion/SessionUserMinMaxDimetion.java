package io.zhenglei.log.dimetion;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class SessionUserMinMaxDimetion implements WritableComparable<SessionUserMinMaxDimetion> {

	private Long sessionSize;
	private Long userSize;
	private Long minSession;
	private Long maxSession;
	private Long sum;
	private Double avg;
	public Long getSum() {
		return sum;
	}
	public void setSum(Long sum) {
		this.sum = sum;
	}
	public Double getAvg() {
		return avg;
	}
	public void setAvg(Double avg) {
		this.avg = avg;
	}
	public Long getSessionSize() {
		return sessionSize;
	}
	public void setSessionSize(Long sessionSize) {
		this.sessionSize = sessionSize;
	}
	public Long getUserSize() {
		return userSize;
	}
	public void setUserSize(Long userSize) {
		this.userSize = userSize;
	}
	public Long getMinSession() {
		return minSession;
	}
	public void setMinSession(Long minSession) {
		this.minSession = minSession;
	}
	public Long getMaxSession() {
		return maxSession;
	}
	public void setMaxSession(Long maxSession) {
		this.maxSession = maxSession;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeLong(sessionSize);
		out.writeLong(userSize);
		out.writeLong(minSession);
		out.writeLong(maxSession);
		out.writeLong(sum);
		out.writeDouble(avg);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.sessionSize = in.readLong();
		this.userSize = in.readLong();
		this.minSession = in.readLong();
		this.sum = in.readLong();
		this.avg = in.readDouble();
	}

	@Override
	public int compareTo(SessionUserMinMaxDimetion arg0) {
		return 0;
	}

}
