package io.zhenglei.log.constants;

public interface HbaseConstants {
	String TABLE_NAME = "t_log";
	String FAMILY = "c1";
	
	/**
	 * IP地址
	 */
	String COLUMN_IP = "ip";
	/**
	 * 当前页面的url
	 */
	String COLUMN_P_URL = "p_url";
	/**
	 * 用户/访客唯一标识符
	 */
	String COLUMN_U_UD = "u_ud";
	/**
	 * 会话id
	 */
	String COLUMN_U_SD = "u_sd";
	/**
	 * 客户端时间
	 */
	String COLUMN_C_TIME = "c_time";
	/**
	 * 浏览器信息useragent
	 */
	String COLUMN_B_IEV = "b_iev";
	/**
	 * 会员id，
	 */
	String COLUMN_U_MID = "u_mid";
	/**
	 * 事件名称
	 */
	String COLUMN_EN = "en";
	/**
	 * 上一个页面的url
	 */
	String COLUMN_P_REF = "p_ref";
	/**
	 * 订单id
	 */
	String COLUMN_OID = "oid";
	/**
	 * 订单名称
	 */
	String COLUMN_ON = "on";
	/**
	 * 支付金额
	 */
	String COLUMN_CUA = "cua";
	
	
	String FS_DEFAULTFS = "fs.defaultFS";
	String YARN_RESOURCEMANAGER_HOSTNAME = "yarn.resourcemanager.hostname";
	String HBASE_ZOOKEEPER_QUORUM = "hbase.zookeeper.quorum";
	
	//-------------------------------------------------------------
	String TABLE_NAME_USER = "t_user";
	String USER_FAMILY = "c1";
	
	String USER_U_UD = "u_ud";
	String USER_U_MID = "u_mid";
	String USER_U_TIME = "u_time";
	String USER_U_IP = "u_ip";
	String USER_U_COUNTRY = "u_country";
	String USER_U_PROVINCE = "u_province";
	String USER_U_CITY = "u_city";
	String USER_U_NEWUSER = "u_newuser";
	String USER_U_NEWVIP = "u_newvip";
	String USER_B_IEV = "b_iev";
	
}
