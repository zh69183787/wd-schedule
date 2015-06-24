package com.wonders.task.audit.dbx.dao;

import java.util.List;
import java.util.Map;

public interface DbxDao {
	public List<String[]> findTasksingByLoginName(String loginName);
	public Map<Long,Integer> getDeptsLevel(List<String> deptIds);
	public String countTimeOut(String loginName);
	public int getUrgeCount(String userName,String nowDeptId,boolean falg,List<String> deptIds);
	public long countMessage(String login_name);
	public int countJbx(String loginName,String dept_id);
}
