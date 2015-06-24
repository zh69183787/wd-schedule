package com.wonders.task.marquee.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.wonders.schedule.util.DbUtil;
import com.wonders.task.marquee.service.StfbService;

@Service("stfbService")
public class StfbServiceImpl implements StfbService{
	public List<Map<String,Object>> findBySql(String sql){
		return DbUtil.getJdbcTemplate("stfb").queryForList(sql);
	}
	
	public void updateSql(String sql){
		DbUtil.getJdbcTemplate("stfb").update(sql);
	}
}
