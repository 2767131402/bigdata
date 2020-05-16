package io.zhenglei.log.controller;

import java.sql.PreparedStatement;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import io.zhenglei.log.dimetion.LongLongDimetion;
import io.zhenglei.log.dimetion.LongStringDimetion;
import io.zhenglei.log.dimetion.SessionUserMinMaxDimetion;
import io.zhenglei.log.dimetion.StringLongLongDimetion;
import io.zhenglei.log.dimetion.StringStringDimetion;
import io.zhenglei.log.dimetion.TimeUsdDimetion;
import io.zhenglei.log.dimetion.UserOutputDimetion;

public interface IFlowController {
	public void add(PreparedStatement ps, UserOutputDimetion text);
	public void addSession(PreparedStatement ps, TimeUsdDimetion key, LongStringDimetion value);
	public void addUserSession(PreparedStatement ps, Text key, SessionUserMinMaxDimetion value);
	public void addbrowserPv(PreparedStatement ps, StringStringDimetion key, LongLongDimetion value);
	public void addArea(PreparedStatement ps, StringLongLongDimetion key, LongWritable value);
	public void addUserDeepth(PreparedStatement ps, StringStringDimetion key, LongWritable value);
}
