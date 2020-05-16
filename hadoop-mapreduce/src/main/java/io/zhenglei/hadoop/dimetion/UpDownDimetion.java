package io.zhenglei.hadoop.dimetion;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author ii_zh
 *
 */
public class UpDownDimetion extends BaseDimetion {

	private Long upPayLoad = 0L;
	private Long downPayLoad = 0L;
	
	public UpDownDimetion(Long upPayLoad, Long downPayLoad) {
		this.upPayLoad = upPayLoad;
		this.downPayLoad = downPayLoad;
	}
	public UpDownDimetion() {
	}
	public long getUpPayLoad() {
		return upPayLoad;
	}
	public void setUpPayLoad(long upPayLoad) {
		this.upPayLoad = upPayLoad;
	}
	public long getDownPayLoad() {
		return downPayLoad;
	}
	public void setDownPayLoad(long downPayLoad) {
		this.downPayLoad = downPayLoad;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeLong(upPayLoad);
		out.writeLong(downPayLoad);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.upPayLoad = in.readLong();
		this.downPayLoad = in.readLong();
	}

	@Override
	public int compareTo(BaseDimetion arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String toString() {
		return upPayLoad + "\t\t" + downPayLoad;
	}

}
