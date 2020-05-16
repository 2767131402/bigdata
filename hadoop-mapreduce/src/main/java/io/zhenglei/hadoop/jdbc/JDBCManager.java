package io.zhenglei.hadoop.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.hadoop.conf.Configuration;

import io.zhenglei.hadoop.constants.MobileConstants;

public class JDBCManager {
	
	private static Connection connection;
	
	public static Connection getConnection(Configuration conf){
		try {
			connection = DriverManager.getConnection(conf.get(MobileConstants.URL), conf.get(MobileConstants.USERNAME), conf.get(MobileConstants.PASSWORD));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public static void close(){
		if(connection!=null){
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
