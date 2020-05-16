package io.zhenglei.hadoop.dimetion;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

/**
 * @author ii_zh
 *
 */
public class WordArticleDimetion implements WritableComparable<WordArticleDimetion> {

	private String word;
	private String article;
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getArticle() {
		return article;
	}
	public void setArticle(String article) {
		this.article = article;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(word);
		out.writeUTF(article);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.word = in.readUTF();
		this.article = in.readUTF();
	}

	@Override
	public int compareTo(WordArticleDimetion o) {
		if(this==o){
			return 0;
		}
		int tmp = this.getWord().compareTo(o.getWord());
		if(tmp!=0){
			return tmp;
		}
		tmp = this.getArticle().compareTo(o.getArticle());
		if(tmp!=0){
			return tmp;
		}
		return 0;
	}
	@Override
	public String toString() {
		return word + "\t" + article;
	}
	
	

}
