package io.zhenglei.log.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.OutputCommitter;
import org.apache.hadoop.mapreduce.OutputFormat;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import io.zhenglei.log.controller.FlowController;
import io.zhenglei.log.dimetion.StringStringDimetion;

public class UserDeepthFormat extends OutputFormat<StringStringDimetion, LongWritable> {

	@Override
	public RecordWriter<StringStringDimetion, LongWritable> getRecordWriter(TaskAttemptContext context)
			throws IOException, InterruptedException {
		Configuration conf = context.getConfiguration();
		Connection connection = JDBCManager.getConnection(conf);
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new recordWriter(connection,conf);
	}

	@Override
	public void checkOutputSpecs(JobContext context) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public OutputCommitter getOutputCommitter(TaskAttemptContext context) throws IOException, InterruptedException {
		return new FileOutputCommitter(FileOutputFormat.getOutputPath(context), context);
	}
	
	class recordWriter extends RecordWriter<StringStringDimetion, LongWritable>{

		private int count = 0;
		private Map<String, PreparedStatement> map = new HashMap<>();
		private Connection connection;
		private Configuration conf;
		
		public recordWriter(Connection connection, Configuration conf) {
			this.connection = connection;
			this.conf = conf;
		}

		@Override
		public void write(StringStringDimetion key, LongWritable value) throws IOException, InterruptedException {
			PreparedStatement ps = map.get("sql5");
			if(ps == null){
				try {
					ps = connection.prepareStatement(conf.get("sql5"));
					map.put("sql5", ps);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			count++;
			
			FlowController controller;
			try {
				controller = (FlowController) Class.forName(conf.get("collector0")).newInstance();
				controller.addUserDeepth(ps, key, value);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
			if(count%5==0){
				try {
					ps.executeBatch();
					connection.commit();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void close(TaskAttemptContext context) throws IOException, InterruptedException {
			try {
				for (String key : map.keySet()) {
					map.get(key).executeBatch();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					connection.commit();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						for (String key : map.keySet()) {
							map.get(key).close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						try {
							connection.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
		}
		
	}

}
