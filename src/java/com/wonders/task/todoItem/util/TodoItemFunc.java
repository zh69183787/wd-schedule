package com.wonders.task.todoItem.util;


public class TodoItemFunc {
	
	public static String generateSql(String userId, String loginName,String userName,String userDeptId){
		String deptSql = "";
		deptSql = " and (instr(t.helpurl,'ST/" + loginName + ":"+userDeptId+"<+>') >0 or t.helpurl is null or instr(t.helpurl,'ST/" + loginName + ":<+>') >0) ";		
		String strSQL = "";
       
        strSQL=
        	" select inci.summary, "+
        	" '' as memo,"+
        	userDeptId+" as deptId, "+//拿到处理人部门 id
        	" inci.priorities, " +
        	" decode(inci.priorities,'急件','<font color=\"red\"><b>急件</b></font>','')||(case when (to_date(to_char(part.overduetime,'YYYY.MM.DD'),'YYYY.MM.DD')-to_date(to_char(sysdate,'YYYY.MM.DD'),'YYYY.MM.DD'))<0 and part.overduetime is not null then '<font color=\"red\"><b>(超时'||to_char(to_date(to_char(sysdate,'YYYY.MM.DD'),'YYYY.MM.DD')-to_date(to_char(part.overduetime,'YYYY.MM.DD'),'YYYY.MM.DD'))||'天)</b></font>' when (to_date(to_char(sysdate,'YYYY.MM.DD HH24:MI:SS'),'YYYY.MM.DD HH24:MI:SS') - to_date(to_char(part.overduetime, 'YYYY.MM.DD HH24:MI:SS'),'YYYY.MM.DD HH24:MI:SS')) > 0 and to_date(to_char(sysdate,'YYYY.MM.DD HH24:MI:SS'),'YYYY.MM.DD HH24:MI:SS') - to_date(to_char(part.overduetime, 'YYYY.MM.DD HH24:MI:SS'),'YYYY.MM.DD HH24:MI:SS') < 1 and part.overduetime is not null then '<font color=\"red\"><b>(今天超时)</b></font>' end) as priorities_show, "+
        	
        	" part.*, "+
        	" '0' as task_type, "+
        	" '1' as 连接字符串, "+
        	" inci.initiator, "+
        	" e.name as apply_username, "+
        	" '"+userName+"' as taskuser_name, "+
        	" 'ST/"+loginName+"' as UserName"+

        	" from incidents inci, "+
        	" cs_user e, "+
        	" ((select t.processname as pname, "+
        	" to_char(t.incident) as pincident, "+
        	" t.processname as processname, "+
        	" to_char(t.incident) as incident, "+
        	" t.steplabel, "+
        	" t.overduetime, "+
        	" to_char(t.endtime, 'yyyy-mm-dd hh24:mi:ss') as endtime, "+
        	" t.taskid, "+
        	" t.assignedtouser "+
        	" from tasks t "+
        	" where exists (select '' "+
        	" from processes a "+
        	" where t.processname = a.processname "+
        	" and a.launchtype = 0 "+
        	" and a.processname <> '拟办子流程' "+
        	" and a.processname <> '办结子流程' "+
        	" ) "+
        	
        	
        	" and not exists (" +
        	" select ''" +
        	" from t_job_contact tj" +
        	" where t.processname = tj.processname" +
        	" and t.incident = tj.instanceid " +
        	" and tj.group_attribute=1" +
        	" )" +
        	
        	" and t.status = 1 and t.assignedtouser like '%ST/%' "+
        	" and t.assignedtouser = 'ST/" + loginName + "' "+
        	deptSql + //暂时
        	" ) " +
        	
        	"union "+
        	
        	" (select b.pname as pname, "+
        	" b.pincident as pincident, "+
        	" b.cname as processname, "+
        	" b.cincident as incident, "+
        	" t.steplabel, "+
        	" t.overduetime, "+
        	" to_char(t.endtime, 'yyyy-mm-dd hh24:mi:ss') as endtime, "+
        	" t.taskid, "+
        	" t.assignedtouser "+
        	" from t_subprocess b, tasks t "+
        	" where b.cname = t.processname "+
        	" and b.cincident = t.incident "+
        	deptSql +//暂时
        	" and not exists "+
        	" (select '' "+
        	" from processes a "+
        	" where t.processname = a.processname "+
        	" and a.launchtype = 0 "+
        	" and a.processname <> '拟办子流程' "+
        	" and a.processname <> '办结子流程' "+
        	" ) "+
        	
        	" and not exists(" +
        	" select ''" +
        	" from t_job_contact tj" +
        	" where b.pname = tj.processname" +
        	" and b.pincident = tj.instanceid " +
        	" and tj.group_attribute =1" +
        	" )"+
        	
        	//" and t.status = 1 "+
        	" and t.status = 1 and t.assignedtouser like '%ST/%' "+
        	" and t.assignedtouser = 'ST/" + loginName + "' "+
        	" )) part "+
        	" where part.pname = inci.processname "+
        	" and part.pincident = inci.incident  and e.removed=0 "+
        	" and upper(inci.initiator) = 'ST/' || upper(e.login_name)" +
        	"";
                
        strSQL = "select main.*,deptInfo.deptName from (("
        		+strSQL
        		+ ") main left join  ( select ta.processname, ta.incident,"
        		+ "(select co.name from cs_organ_node co where replace(substr(ta.helpurl, instr(ta.helpurl, ':') + 1), '<+>', '') = co.id ) as deptName "
        		+ " from tasks ta, (select distinct processname, processversion, steplabel from processsteps where stepid like '%B' ) tb "
        		+ " where ta.processname = tb.processname and ta.processversion = tb.processversion and ta.steplabel = tb.steplabel "
        		+ ")deptInfo on(main.pname = deptInfo.processname and main.pincident = to_char(deptInfo.incident)))" ;
        
        String strJobSQL="";//工作联系单新版本查询用SQL      
        
        /**/
        strJobSQL=
        " select " +
    	" '<div class=summary groupid='||groupid||' ptype=1 dbxtype=process>'||tjcg.summary||'</div> ' as summary,"+
    	" '<div class=memo dbxtype=process groupid='||groupid||'></div>' as memo,"+
    	userDeptId+" as deptId, "+//拿到处理人部门 id
    	" tjcg.priorities,"+
		" decode(tjcg.priorities,'急件','<font color=\"red\"><b>急件</b></font>','')||(case when (to_date(to_char(tjcg.overdue_time,'YYYY.MM.DD'),'YYYY.MM.DD')-to_date(to_char(sysdate,'YYYY.MM.DD'),'YYYY.MM.DD'))<0 and tjcg.overdue_time is not null then '<font color=\"red\"><b>(超时'||to_char(to_date(to_char(sysdate,'YYYY.MM.DD'),'YYYY.MM.DD')-to_date(to_char(tjcg.overdue_time,'YYYY.MM.DD'),'YYYY.MM.DD'))||'天)</b></font>' when (to_date(to_char(sysdate,'YYYY.MM.DD HH24:MI:SS'),'YYYY.MM.DD HH24:MI:SS') - to_date(to_char(tjcg.overdue_time, 'YYYY.MM.DD HH24:MI:SS'),'YYYY.MM.DD HH24:MI:SS')) > 0 and to_date(to_char(sysdate,'YYYY.MM.DD HH24:MI:SS'),'YYYY.MM.DD HH24:MI:SS') - to_date(to_char(tjcg.overdue_time, 'YYYY.MM.DD HH24:MI:SS'),'YYYY.MM.DD HH24:MI:SS') < 1 and tjcg.overdue_time is not null then '<font color=\"red\"><b>(今天超时)</b></font>' end) as priorities_show,"+
		" '工作联系单' as pname,"+
		" '' as pincident,"+
		" '工作联系单' as processname,"+
		" '' as incident,"+
		" '<div class=steplabel dbxtype=process groupid='||groupid||'></div>' as steplabel,"+
		" to_date(tjcg.overdue_time) as overduetime,"+
		" to_char(part1.endtime, 'yyyy-mm-dd hh24:mi:ss') as endtime,"+
		" groupid as taskid,"+
		" 'ST/" + loginName + "' as assignedtouser,"+
		" '0' as task_type, "+
		" '1' as 连接字符串, "+
		" tjcg.initiator,"+
		" tjcg.initiator_name as apply_username,"+
		" '"+userName+"' as taskuser_name, "+
		" 'ST/"+loginName+"' as UserName,"+
		" tjcg.create_deptname as deptname"+
		" "+
		" from ("+
		" select groupid,max(endtime) as endtime from ("+
		" select part.pname,part.incident,part.endtime"+
		" ,(select tjcr.business_id from t_job_contact_reference tjcr where tjcr.jc_id in ("+
		" select tjc.id from t_job_contact tjc where tjc.processname=part.pname "+
		" and tjc.instanceid = part.pincident and tjc.group_attribute=1 and tjc.removed != '1'"+
		" )and tjcr.business_table_name='T_JOB_CONTACT_GROUP' and tjcr.removed!=1) as groupid"+
		" "+
		" from("+
		" (select t.processname as pname, to_char(t.incident) as pincident, t.processname as processname, to_char(t.incident) as incident, t.endtime from tasks t"+
		" where t.processname='工作联系单' and exists (select '' from t_job_contact tj"+
		" where t.processname = tj.processname and t.incident = tj.instanceid and tj.group_attribute =1 and tj.removed != '1')" +
		//deptSql +//暂时
		" and (instr(t.helpurl,'ST/" + loginName + ":"+userDeptId+"<+>') >0 or t.helpurl is null or instr(t.helpurl,'ST/" + loginName + ":<+>') >0) " +
		" and t.assignedtouser like '%ST/%'and t.status = 1 and t.assignedtouser = 'ST/" + loginName + "' )"+
		" union"+
		" (select b.pname as pname,b.pincident as pincident,b.cname as processname,b.cincident as incident,t.endtime "+
		" from t_subprocess b, tasks t where b.cname = t.processname and b.cincident = t.incident "+
		" and exists (select '' from t_job_contact tj where b.pname = tj.processname and b.pincident = tj.instanceid and tj.group_attribute =1 and tj.removed != '1')"+
		" and t.assignedtouser like '%ST/%'"+
		" and b.pname='工作联系单' and b.cname='部门内部子流程'"+
		//deptSql +//暂时
		" and (instr(t.helpurl,'ST/" + loginName + ":"+userDeptId+"<+>') >0 or t.helpurl is null or instr(t.helpurl,'ST/" + loginName + ":<+>') >0) " +
		" and t.status = 1 and t.assignedtouser = 'ST/" + loginName + "' "+
		" ))part)"+
		" group by groupid)part1,t_job_contact_group tjcg where part1.groupid = tjcg.id";
        
        String dyxSQL = "";
        
        dyxSQL = 
        	" select '<div class=summary groupid=' || twr.business_id ||' ptype=0 dbxtype=dyx>' || tjcg.summary || '</div> ' as summary," +
        	" '<div class=memo dbxtype=dyx groupid=' || twr.business_id || '></div>' as memo," +
        	userDeptId+" as deptId, "+//拿到处理人部门 id
        	" tjcg.priorities," +
        	" '' as priorities_show," +
        	" '工作联系单' as pname," +
        	" '' as pincident," +
        	" '工作联系单' as processname," +
        	" '' as incident," +
        	" '传阅' as steplabel," +
        	" to_date(tjcg.overdue_time) as overduetime," +
        	" twr.operate_date as endtime," +
        	" twr.business_id as taskid," +
        	" 'ST/"+loginName+"' as assignedtouser," +
        	" '0' as task_type," +
        	" '1' as 连接字符串," +
        	" tjcg.initiator," +
        	" tjcg.initiator_name as apply_username," +
        	" '"+userName+"' as taskuser_name," +
        	" 'ST/"+loginName+"' as UserName," +
        	" tjcg.create_deptname as deptname" +
        	" from t_job_contact_group tjcg," +
        	" (select twr.business_id,max(twr.operate_date) as operate_date from t_wait_read twr where " +
        	" twr.removed != 1" +
        	" and twr.status = '0'" +
        	//" and twr.wr_user = 'ST/"+user+"'" +
        	" and twr.wr_user = '"+userId+"'" +
        	
        	" and twr.wr_deptid = '"+userDeptId+"'" +
        	" group by twr.business_id" +
        	" ) twr " +
        	" where tjcg.id = twr.business_id" +
        	"";
        
		strSQL = "select * from (("+strSQL+")union("+strJobSQL+")union("+dyxSQL+")) where 1=1";	
		strSQL = " select * from ("+strSQL+")  order by endtime desc";
		
		//System.out.println(strSQL);
		
		return strSQL;
		
	}
}
