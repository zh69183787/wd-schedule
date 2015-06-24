package com.wonders.task.marquee.service;

import java.util.Collection;
import java.util.List;

public interface OldService {
	public void saveOrUpdateAll(Collection cols);
	
	public void updateBySql(String sql);
	
	public List<Object[]> findBySql(String sql);
}
