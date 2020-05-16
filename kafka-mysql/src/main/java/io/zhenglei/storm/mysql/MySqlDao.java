package io.zhenglei.storm.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlDao {

	protected Connection con;

	public KeyValue getKeyValue(String key) {
		openConnection();
		KeyValue kv = null;
		try {
			PreparedStatement ps = con.prepareStatement("select * from order_num where orderdate=?");
			ps.setString(1, key);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				kv = new KeyValue();
				kv.setKey(key);
				kv.setValue(rs.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return kv;
	}

	public void insert(String key, String value) {
		openConnection();
		try {
			PreparedStatement ps = con
					.prepareStatement("insert into order_num values(?,?) ON DUPLICATE KEY UPDATE orderkey=? ");
			ps.setString(1, key);
			ps.setString(2, value);
			ps.setString(3, value);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public void openConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://192.168.6.120:3306/solr", "root", "123");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
