package com.wonders.task.marquee.dao;

import java.util.Collection;
import java.util.List;

public interface NewDao {
	public void saveOrUpdateAll(Collection cols);
	
	public void updateBySql(String sql);
	
	public List<Object[]> findBySql(String sql);
	
	public List<Object[]> findBySql(String sql,List<Object> args);
	
	public String findUniqueString(String name,String sql,List<Object> sss);
}
