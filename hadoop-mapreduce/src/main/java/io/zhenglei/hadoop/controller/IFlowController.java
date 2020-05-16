package io.zhenglei.hadoop.controller;

import java.sql.PreparedStatement;

import io.zhenglei.hadoop.dimetion.DatePhoneDimetion;
import io.zhenglei.hadoop.dimetion.UpDownDimetion;

public interface IFlowController {
	public void add(PreparedStatement ps, DatePhoneDimetion datePhoneDimetion, UpDownDimetion dimetion);

}
