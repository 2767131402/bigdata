package io.zhenglei.hadoop.controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import io.zhenglei.hadoop.dimetion.DatePhoneDimetion;
import io.zhenglei.hadoop.dimetion.UpDownDimetion;

public class FlowController implements IFlowController {

	@Override
	public void add(PreparedStatement ps, DatePhoneDimetion datePhoneDimetion, UpDownDimetion dimetion) {
		try {
			ps.setString(1, datePhoneDimetion.getMobileDate());
			ps.setString(2, datePhoneDimetion.getMobilePhone());
			ps.setLong(3, dimetion.getUpPayLoad());
			ps.setLong(4, dimetion.getDownPayLoad());
			ps.setLong(5, dimetion.getUpPayLoad());
			ps.setLong(6, dimetion.getDownPayLoad());
			
			ps.addBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
