/**
 * 
 */
package com.wonders.task.build.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.wonders.schedule.util.DbUtil;
import com.wonders.task.build.util.GenerateSqlUtil;
import com.wonders.task.build.dao.BuildDao;
import com.wonders.task.build.model.vo.BuildPlanMonthVo;
import com.wonders.task.build.model.vo.BuildPlanTotalVo;
import com.wonders.task.build.model.vo.BuildPlanVo;
import com.wonders.task.build.model.vo.BuildPlanYearVo;
import com.wonders.task.build.service.BuildService;
import com.wonders.task.todoItem.util.DateUtil;

/** 
 * @ClassName: BuildServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-10-29 上午9:59:52 
 *  
 */

@Service("buildService")
public class BuildServiceImpl implements BuildService{
	private BuildDao dao;

	public BuildDao getBuildDao() {
		return dao;
	}

	@Autowired(required=false)
	public void setBuildDao(@Qualifier("buildDao")BuildDao dao) {
		this.dao = dao;
	}
	
	public void saveBatch(List<?> c){
		this.dao.saveBatch(c);
	}
	
	@Override
	public List<BuildPlanVo> getTaskInfo(){
		String timeSql = GenerateSqlUtil.getMaxDate();
		String date = DbUtil.getJdbcTemplate("").queryForObject(timeSql, String.class);
		if(date==null || date.length()==0){
			date = DateUtil.getNowDate();
		}
		
		String sql = GenerateSqlUtil.getTaskInfoSql(date);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<BuildPlanVo> list = DbUtil.getJdbcTemplate("").query(sql,new BeanPropertyRowMapper(BuildPlanVo.class));
		
		return list;
	}

	
	
	@Override
	public List<BuildPlanYearVo> getLastYearInfo() {
		String sql = GenerateSqlUtil.statYear(GenerateSqlUtil.getLastYear());
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<BuildPlanYearVo> list = DbUtil.getJdbcTemplate("").query(sql,new BeanPropertyRowMapper(BuildPlanYearVo.class));
		
		return list;
	}
	
	@Override
	public List<BuildPlanYearVo> getNowYearInfo() {
		String sql = GenerateSqlUtil.statYear(GenerateSqlUtil.getNowYear());
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<BuildPlanYearVo> list = DbUtil.getJdbcTemplate("").query(sql,new BeanPropertyRowMapper(BuildPlanYearVo.class));
		
		return list;
	}
	
	@Override
	public List<BuildPlanTotalVo> getTotalInfo() {
		String sql = GenerateSqlUtil.statTotal();
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<BuildPlanTotalVo> list = DbUtil.getJdbcTemplate("").query(sql,new BeanPropertyRowMapper(BuildPlanTotalVo.class));
		
		return list;
	}

	
	@Override
	public List<BuildPlanMonthVo> getMonthInfo() {
		String sql = GenerateSqlUtil.statMonth(Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00")).get(Calendar.YEAR)+"");
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<BuildPlanMonthVo> list = DbUtil.getJdbcTemplate("").query(sql,new BeanPropertyRowMapper(BuildPlanMonthVo.class));
		
		return list;
	}
	
	
	
}
