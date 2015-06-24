/**
 * 
 */
package com.wonders.task.todoItem.service.impl;

import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.wonders.schedule.util.DbUtil;
import com.wonders.task.todoItem.model.vo.FlowInfo;
import com.wonders.task.todoItem.model.vo.UserInfo;
import com.wonders.task.todoItem.service.QueryService;
import com.wonders.task.todoItem.util.TodoItemFunc;


/** 
 * @ClassName: WorkFlowServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-1-22 下午01:55:33 
 *  
 */
@Service("queryService")
public class QueryServiceImpl implements QueryService{
	
	//获得活跃用户工号
	public String findActiveUser(){
		String result = "";
		String sql = "select distinct u.login_name from ca_visit_log c,t_user_relation t,t_user u where c.appname='AUTO_LOGIN' and c.visit_time>sysdate-14 and t.c_id = c.userid and u.id = t.t_id";
		List<String> list = DbUtil.getJdbcTemplate("stptdemo").queryForList(sql,String.class);
		if(list != null && list.size()>0){
			for(String temp:list){
				result += "'"+temp +"',";
			}
			result = result.substring(0,result.length()-1);
		}
		return result;
	}
	
	//获得用户信息
	@SuppressWarnings("unchecked")
	public List<UserInfo> findUserInfo(){
		String param = findActiveUser();
		String sql = "select distinct id as userId, login_name as loginName, name as userName, parent_node_id as deptId from v_userdep where login_name in ("+param+")";
		//System.out.println(sql);
		List<UserInfo> list = DbUtil.getJdbcTemplate("stptinc").query(sql, new BeanPropertyRowMapper(UserInfo.class));
		return list;
	}
	
	
	//获得用户待办项信息
	@SuppressWarnings("unchecked")
	public List<FlowInfo> findTodoItems(UserInfo u){
		String sql = TodoItemFunc.generateSql(u.getUserId(), u.getLoginName(), u.getUserName(), u.getDeptId());
		List<FlowInfo> list = DbUtil.getJdbcTemplate("stptinc").query(sql, new BeanPropertyRowMapper(FlowInfo.class));
		return list;
	}
	
}
