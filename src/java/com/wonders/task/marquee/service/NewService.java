package com.wonders.task.marquee.service;

import java.util.Collection;
import java.util.List;

public interface NewService {
	public void saveOrUpdateAll(Collection cols);
	
	public void updateBySql(String sql);
	
	public List<Object[]> findBySql(String sql);
	
	public String findUniqueString(String name,String sql,List<Object> sss);
	
	public List<Object[]> findBySql(String sql,List<Object> args);
}
