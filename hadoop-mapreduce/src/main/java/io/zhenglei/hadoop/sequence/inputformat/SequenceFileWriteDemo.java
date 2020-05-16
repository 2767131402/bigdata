package io.zhenglei.hadoop.sequence.inputformat;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.CompressionType;
import org.apache.hadoop.io.SequenceFile.Reader;
import org.apache.hadoop.io.SequenceFile.Writer;
import org.apache.hadoop.io.Text;

/**
 * @version 1.0
 * @author Fish
 */
public class SequenceFileWriteDemo {
	private static final String[] DATA = { "fish1", "fish2", "fish3", "fish4" };
	public static void main(String[] args) throws IOException {
		/**
		 * 写SequenceFile
		 */
		String uri = "hdfs://192.168.8.101:9000/seq.txt";
		Configuration conf = new Configuration();
		Path path = new Path(uri);

		IntWritable key = new IntWritable();
		Text value = new Text();
		
		Writer writer = null;
		try {
			/**
			 * CompressionType.NONE 不压缩<br>
			 * CompressionType.RECORD 只压缩value<br>
			 * CompressionType.BLOCK 压缩很多记录的key/value组成块
			 */
			writer = SequenceFile.createWriter(conf, Writer.file(path), Writer.keyClass(key.getClass()),
					Writer.valueClass(value.getClass()), Writer.compression(CompressionType.BLOCK));

			for (int i = 0; i < DATA.length; i++) {
				value.set(DATA[i]);
				key.set(i);
				System.out.printf("[%s]\t%s\t%s\n", writer.getLength(), key, value);
				writer.append(key, value);

			}
		} finally {
			IOUtils.closeStream(writer);
		}

		/**
		 * 读SequenceFile 有序k(Text=a.txt) v("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
		 * 通常用于统计小文件
		 * 
		 */
		SequenceFile.Reader reader = new SequenceFile.Reader(conf, Reader.file(path));
		IntWritable key1 = new IntWritable();
		Text value1 = new Text();
		while (reader.next(key1, value1)) {
			System.out.println(key1 + "----" + value1);
		}
		IOUtils.closeStream(reader);// 关闭read流
		
		/**
		 * 用于排序
		 */
//		SequenceFile.Sorter sorter = new SequenceFile.Sorter(fs, comparator, IntWritable.class, Text.class, conf);
	}
}