/**
 * 
 */
package com.wonders.task.build.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/** 
 * @ClassName: GenerateSqlUtil 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-4-29 下午7:01:01 
 *  
 */
public class GenerateSqlUtil {
	public static String taskSql = "";
	public static String dateSql = "";
	private static String yearSql = "";
	private static String monthSql = "";
	private static String totalSql = "";
	
	public static String getNowYear(){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.DAY_OF_YEAR, 1);
		return new java.text.SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}
	
	public static String getLastYear(){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.YEAR, -1);
		c.set(Calendar.DAY_OF_YEAR, 1);
		return new java.text.SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}
	
	public static void main(String[] args){
		System.out.println(getLastYear());
		System.out.println(StringUtils.isNotEmpty("    "));
		System.out.println(StringUtils.isNotBlank("            "));
	}
	public static String getTaskInfoSql(String date){		
		taskSql = "select "
				+ " a.project_id as projectId,"
				+ " b.single_id as singleId,"
				+ " c.unit_id as companyId,"
				+ " d.plan_type_id as planId,"
				+ " a.milestone,"
				+ " a.plan_begin_time as pbtime,"
				+ " a.plan_finish_time as pftime,"
				+ " a.real_begin_time as rbtime,"
				+ " a.real_finish_time as rftime"
				+ " from ("
				+ " select * from greata_plan_task t where t.removed=0 "
				+ " and t.create_time = '"+date+"' and t.plan_type='3') a left join "
				+ " greata_single b on a.plan_id=b.plan_id  left join "
				+ " greata_construction_unit c on a.plan_id=c.plan_id   left join "
				+ " greata_plan_type d on a.plan_id=d.plan_id";
		//System.out.println(taskSql);
		return taskSql;
	}
	
	public static String getMaxDate(){
		dateSql = "select max(t.create_time) as maxdate from greata_plan_task t where t.removed=0";
		return dateSql;
	}
	
	public static String statTotal(){
		totalSql = "select v.projectId,v.typeId,v.projectName,v.typeName,"
				+ " sum(planTotal) planTotal from ("
				+ " select b.project_id projectId ,b.project_name projectName ,"
				+ " p.plan_type_id as typeId,p.plan_type_name as typeName,"
				+ " t.plan_completion as planTotal"
				+ " from greata_plan_task t,"
				+ " greata_plan_type p ,"
				+ " greata_project b"
				+ " where t.removed=0 and p.removed=0 and b.removed=0"
				+ " and t.project_id=b.project_id"
				+ " and t.plan_type='0'"
				+ " and t.plan_id=p.plan_id "
				+ " and t.create_time = "
				+ " (select max(t.create_time) "
				+ " from greata_plan_task t )"
				+ " ) v group by v.projectId,v.typeId,v.projectName,v.typeName";
		//System.out.println(totalSql);
		return totalSql;
	}
	
	public static String statYear(String date){
		yearSql = "select b.project_id projectId ,b.project_name as projectName,"
				+ " p.plan_type_id as typeId,p.plan_type_name as typeName,"
				+ " t.plan_completion as planFinish,"
				+ " t.total_completion as totalFinish"
				+ " from greata_plan_task t,"
				+ " greata_plan_type p ,"
				+ " greata_project b"
				+ " where t.removed=0 and p.removed=0 and b.removed=0"
				+ " and t.project_id=b.project_id"
				+ " and t.plan_type='0'"
				+ " and t.plan_id=p.plan_id and t.version_date = '"+date+"'"
				+ " and t.create_time = "
				+ " (select max(t.create_time) "
				+ " from greata_plan_task t where t.version_date = '"+date+"')";
		//System.out.println(yearSql);
		return yearSql;
	}
	
	public static String statMonth(String date){
		monthSql = "select b.project_id projectId ,b.project_name as projectName,"
				+ " p.plan_type_id as typeId,p.plan_type_name as typeName,"
				+ " s.single_id as singleId,"
				+ " s.single_name as singleName,"
				+ " t.plan_finish_time as planFinish,"
				+ " t.real_finish_time as realFinish"
				+ " from greata_plan_task t,"
				+ " greata_plan_type p ,"
				+ " greata_project b,"
				+ " greata_single s"
				+ " where t.removed=0 and p.removed=0 and b.removed=0 and s.removed=0"
				+ " and t.project_id=b.project_id"
				+ " and t.plan_type='1'"
				+ " and t.plan_id=s.plan_id"
				+ " and t.plan_id=p.plan_id "
				+ " and t.version_date like '%"+date+"%'"
				+ " and t.create_time = "
				+ " (select max(t.create_time) "
				+ " from greata_plan_task t)";
		System.out.println(monthSql);
		return monthSql;
	}
}
