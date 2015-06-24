/**
 * 
 */
package com.wonders.task.autoUrge.util;

/** 
 * @ClassName: GenerateSqlUtil 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-4-29 下午7:01:01 
 *  
 */
public class GenerateSqlUtil {
	public static String urgeSql = "";
	public static String overSql = "";
	public static String mobileSql = "";
	public static String delaySql = "";
	public static String deptSql = "";	
	
	public static String generateOverInfoSql(String overdate){
		overSql =
		"select substr(t.helpurl,instr(t.helpurl,':')+1,instr(t.helpurl,'<+>')-instr(t.helpurl,':')-1) deptId, " +
		" i.summary," +
		" b.pname as pname," +
		" b.pincident as pincident, " +
		" b.cname as cname, " +
		" b.cincident as cincident," +
		" t.assignedtouser userId," +
		" c.name userName," +
		" v.login_name leaderId,v.name leaderName," +
		" v.dept_id deptIdd ,v.dept deptNamee ," +
		" to_char(" +
		" to_date(to_char(sysdate,'YYYY.MM.DD'),'YYYY.MM.DD')-to_date(to_char(t.starttime,'YYYY.MM.DD'),'YYYY.MM.DD')" +
		" ) delay" +
		" from t_subprocess b, tasks t , v_dept_leaders v ,incidents i , cs_user c" +
		" where b.cname = t.processname" +
		" and b.cname='部门内部子流程'" +
		" and b.cincident = t.incident" +
		" and (instr(t.helpurl,'<+>') >0)" +
		" and t.status = 1 " +
		" and c.removed = 0 and 'ST/'||c.login_name= t.assignedtouser" +
		" and b.pname=i.processname and b.pincident = i.incident and i.status != 33" +
		" and substr(t.helpurl,instr(t.helpurl,':')+1,instr(t.helpurl,'<+>')-instr(t.helpurl,':')-1) = v.dept_id" +
		" and t.overduetime is not null "+
		" and to_date(to_char(sysdate,'YYYY.MM.DD'),'YYYY.MM.DD')-to_date(to_char(t.overduetime,'YYYY.MM.DD'),'YYYY.MM.DD')>1" +
		" and mod(" +
		" to_date(to_char(sysdate,'YYYY.MM.DD'),'YYYY.MM.DD')-to_date(to_char(t.overduetime,'YYYY.MM.DD'),'YYYY.MM.DD'),"+overdate+
		" )=0" ;
		
		
		return overSql;
	}
	
	public static String generateDelayInfoSql(){
		delaySql = "select t.process,t.incident from t_delay_item t where " +
				" t.delay_date > to_char(sysdate,'yyyy-mm-dd') " +
				" and status = '0' and removed ='0'";
		
		return delaySql;
	}
	
	public static String generateMobleInfoSql(){
		mobileSql = "select 'ST/'||c.login_name as userId,c.name," +
				" case " +
				" when " +
				" c.mobile1 is not null then c.mobile1" +
				" else " +
				" c.mobile2 end mobile" +
				" from cs_user c " +
				" where c.removed=0 and (c.mobile1 is not null or c.mobile2 is not null)";
		
		return mobileSql;
	}
	
	public static String gengerateUrgeInfoSql(String overdate){
		urgeSql = "select o.summary, o.userId, o.leaderId,o.userName,o.deptIdd deptId, o.deptNamee deptName from ("+generateOverInfoSql(overdate)+") o "+
				 " where not exists (" +
				 " select '' from t_delay_item d where o.cname = d.process" +
				 " and o.cincident = d.incident and " +
				 " d.delay_date > to_char(sysdate,'yyyy-mm-dd') " +
				 " and d.status = '0' and d.removed ='0' )";
				
		//System.out.println("urgeSql=:"+urgeSql);		
		return urgeSql;
	}
}
