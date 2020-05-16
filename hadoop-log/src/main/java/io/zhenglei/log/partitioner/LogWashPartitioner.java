package io.zhenglei.log.partitioner;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import io.zhenglei.log.utils.PartitionUtils;

public class LogWashPartitioner extends Partitioner<Text, NullWritable> {

	@Override
	public int getPartition(Text key, NullWritable value, int numPartitions) {
		String splitStr = key.toString().split("en=")[1];
		String str = splitStr.substring(0, splitStr.indexOf("&"));
		if(str!=null){
			return PartitionUtils.getInstance().get(str);
		}
		return 0;
	}

}
