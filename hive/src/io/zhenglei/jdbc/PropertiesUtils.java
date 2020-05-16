package io.zhenglei.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {
	private PropertiesUtils() {	}
	
	private static final String FILEPATH = "/hive.properties";
	private static final Properties PROPERTIES = new Properties();
	static {
		InputStream is = PropertiesUtils.class.getResourceAsStream(FILEPATH);
		try {
			PROPERTIES.load(is);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("资源文件加载失败！");
		}
	}
	
	/**
	 * 加载方法，用于对Properties对象进行重新加载
	 * @throws IOException
	 */
	public static void load() throws IOException {
		InputStream is = PropertiesUtils.class.getResourceAsStream(FILEPATH);
		PROPERTIES.load(is);
	}
	/**
	 * 根据key获取value值
	 */
	public static String getProperty(String key) {
		return PROPERTIES.getProperty(key);
	}
}
