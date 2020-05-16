package io.zhenglei.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据库连接工具类(hive)
 * @author 郑磊
 *
 */
public class ConnectionUtils {
	
	private static ThreadLocal<Connection> local = new ThreadLocal<>();
	
	static {
		try {
			Class.forName(PropertiesUtils.getProperty("driverClassName"));
		} catch (ClassNotFoundException e) {
			System.out.println("驱动类加载失败！");
			e.printStackTrace();
		}
	}
	/**
	 * 获取链接
	 */
	public static Connection getConnection(){
		try {
			Connection connection = DriverManager.getConnection(
					PropertiesUtils.getProperty("url"), 
					PropertiesUtils.getProperty("username"), 
					PropertiesUtils.getProperty("password"));
			local.set(connection);
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 关闭连接
	 */
	public static void close() {
		try {
			Connection connection = local.get();
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			local.set(null);
		}
	}
	
	public static void main(String[] args) throws SQLException {
		Connection connection = getConnection();
		PreparedStatement ps = connection.prepareStatement("select * from stu");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString(1)+"\t"+rs.getString(2));
		}
	}
}
