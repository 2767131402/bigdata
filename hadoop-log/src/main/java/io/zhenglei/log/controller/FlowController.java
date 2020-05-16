package io.zhenglei.log.controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import io.zhenglei.log.dimetion.LongLongDimetion;
import io.zhenglei.log.dimetion.LongStringDimetion;
import io.zhenglei.log.dimetion.SessionUserMinMaxDimetion;
import io.zhenglei.log.dimetion.StringLongLongDimetion;
import io.zhenglei.log.dimetion.StringStringDimetion;
import io.zhenglei.log.dimetion.TimeUsdDimetion;
import io.zhenglei.log.dimetion.UserOutputDimetion;
import io.zhenglei.log.utils.DateTransYMDUtils;
import io.zhenglei.log.utils.DateTransYMDUtils2;

public class FlowController implements IFlowController {

	@Override
	public void add(PreparedStatement ps, UserOutputDimetion text) {
		try {
			ps.setString(1, text.getuUd());
			ps.setString(2, text.getuMid());
			ps.setTimestamp(3, new Timestamp(text.getuTime()));
			ps.setBoolean(4, text.getuNewadd());
			ps.setBoolean(5, text.getuNewvip());
			
			ps.setString(6, text.getuCountry());
			ps.setString(7, text.getuProvince());
			ps.setString(8, text.getuCity());
			ps.setString(9, text.getBrowser());
			ps.setString(10, text.getIp());
	
			ps.addBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void addSession(PreparedStatement ps, TimeUsdDimetion key, LongStringDimetion value) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			ps.setString(1, key.getUsd());
			ps.setTimestamp(2, new Timestamp(sdf.parse(key.getTime()).getTime()));
			ps.setInt(3, DateTransYMDUtils.getYear(key.getTime()));
			ps.setInt(4, DateTransYMDUtils.getMonth(key.getTime()));
			ps.setInt(5, DateTransYMDUtils.getDay(key.getTime()));
			ps.setInt(6, value.getTime().intValue());
			ps.setString(7, value.getBrowser());
			
			ps.addBatch();
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void addUserSession(PreparedStatement ps, Text key, SessionUserMinMaxDimetion value) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
		try {
			
			ps.setTimestamp(1, new Timestamp(sdf.parse(key.toString()).getTime()));
			ps.setInt(2, DateTransYMDUtils2.getYear(key.toString()));
			ps.setInt(3, DateTransYMDUtils2.getMonth(key.toString()));
			ps.setInt(4, DateTransYMDUtils2.getDay(key.toString()));
			ps.setInt(5, DateTransYMDUtils2.getHour(key.toString()));
			ps.setInt(6, Integer.valueOf(value.getSessionSize().toString()));
			ps.setInt(7, Integer.valueOf(value.getUserSize().toString()));
			ps.setInt(8, Integer.valueOf(value.getMaxSession().toString()));
			ps.setInt(9, Integer.valueOf(value.getMinSession().toString()));
			ps.setInt(10,value.getSum().intValue());
			ps.setDouble(11, value.getAvg());
			
			ps.addBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void addbrowserPv(PreparedStatement ps, StringStringDimetion key, LongLongDimetion value) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			ps.setTimestamp(1, new Timestamp(sdf.parse((key.getUd())).getTime()));
			ps.setString(2, key.getUrl());
			ps.setInt(3, value.getPvNum().intValue());
			ps.setInt(4, value.getBrNum().intValue());
			
			ps.addBatch();
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void addArea(PreparedStatement ps, StringLongLongDimetion key, LongWritable value) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			ps.setTimestamp(1, new Timestamp(sdf.parse(key.getTime()).getTime()));
			ps.setInt(2, key.getTiaoNum().intValue());
			ps.setInt(3, (int)value.get());
			ps.setInt(4, key.getPvNum().intValue());
			ps.setString(5, key.getArea());
			
			ps.addBatch();
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void addUserDeepth(PreparedStatement ps, StringStringDimetion key, LongWritable value) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			ps.setTimestamp(1, new Timestamp(sdf.parse(key.getUrl()).getTime()));
			ps.setString(2, key.getUd());
			ps.setInt(3, (int)value.get());
			
			ps.addBatch();
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
	}

}
