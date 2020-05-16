package io.zhenglei.hadoop.dimetion;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

/**
 * @author ii_zh
 *
 */
public class WordSortDimetion implements WritableComparable<WordSortDimetion> {
	
	private String article;
	private Long count = 0L;
	public String getArticle() {
		return article;
	}
	public void setArticle(String article) {
		this.article = article;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(article);
		out.writeLong(count);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.article = in.readUTF();
		this.count = in.readLong();
	}

	@Override
	public int compareTo(WordSortDimetion o) {
		if(this == o){
			return 0;
		}
		int tmp = this.count.compareTo(o.getCount());
		if(tmp!=0){
			return tmp;
		}
		return 0;
	}
	@Override
	public String toString() {
		return article + "\t" + count;
	}

}
