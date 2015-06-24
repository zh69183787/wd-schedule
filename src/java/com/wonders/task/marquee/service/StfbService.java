package com.wonders.task.marquee.service;

import java.util.List;
import java.util.Map;

public interface StfbService {
	public List<Map<String,Object>> findBySql(String sql);
	public void updateSql(String sql);
}
