package com.wonders.task.marquee.dao;

import java.util.Collection;
import java.util.List;

public interface OldDao {
	public void saveOrUpdateAll(Collection cols);
	
	public void updateBySql(String sql);
	
	public List<Object[]> findBySql(String sql);
}
