package com.wonders.task.audit.dbx.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.task.audit.dbx.dao.DbxDao;
import com.wonders.task.audit.tools.Common;


@Repository("dbxDao")
public class DbxDaoImpl implements DbxDao{
	private HibernateTemplate hibernateTemplate;
	private Common comm = new Common();
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	//注入hibernateTemplate
	@Resource(name="hibernateTemplate2")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@SuppressWarnings("unchecked")
	public List<String[]> findTasksingByLoginName(String loginName){
		List<String[]> returnList = null;
		if(comm.isNull(loginName)) return null;
		String sql = " select inci.processname, inci.incident,part.helpurl,inci.priorities,part.substatus,part.overduetime "+
			" from incidents inci, "+
			" ((select t.processname as pname,t.substatus,t.overduetime, "+
			" to_char(t.incident) as pincident, "+
			" t.processname as processname, "+
			" to_char(t.incident) as incident, "+
			" t.helpurl "+
			" from tasks t "+
			" where exists (select '' "+
			" from processes a "+
			" where t.processname = a.processname "+
			" and a.launchtype = 0 "+
			" and a.processname <> '拟办子流程' "+
			" and a.processname <> '办结子流程') "+
			" and t.status = 1 "+
			" and t.assignedtouser like '%ST/%' "+
			" and t.assignedtouser = 'ST/"+loginName+"') union "+
			" (select b.pname     as pname,t.substatus,t.overduetime, "+
			" b.pincident as pincident, "+
			" b.cname     as processname, "+
			" b.cincident as incident, "+
			" t.helpurl "+
			" from t_subprocess b, tasks t "+
			" where b.cname = t.processname "+
			" and b.cincident = t.incident "+
			" and not exists (select '' "+
			" from processes a "+
			" where t.processname = a.processname "+
			" and a.launchtype = 0 "+
			" and a.processname <> '拟办子流程' "+
			" and a.processname <> '办结子流程') "+
			" and t.status = 1 "+
			" and t.assignedtouser like '%ST/%' "+
			" and t.assignedtouser = 'ST/"+loginName+"')) part "+
			" where part.pname = inci.processname "+
			" and part.pincident = inci.incident ";
		SQLQuery query = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.addScalar("processname", Hibernate.STRING);
		query.addScalar("incident", Hibernate.INTEGER);
		query.addScalar("helpurl", Hibernate.STRING);
		query.addScalar("priorities", Hibernate.STRING);
		query.addScalar("substatus", Hibernate.INTEGER);
		query.addScalar("overduetime", Hibernate.STRING);
		List list = query.list();
		if(list == null || list.isEmpty()) return null;
//		list = new ArrayList<Incidents>();
		returnList = new ArrayList<String[]>();
		Iterator iter = list.iterator();
		Object[] obj = null;
		
//		Incidents inci = null;
//		String key = null;
		String[] value = null;
		while(iter.hasNext()){
			obj = (Object[])iter.next();
			value = new String[6];
			value[0] = (String)obj[0];
			value[1] = (Integer)obj[1] +"";
			value[2] = (String)obj[2];
			value[3] = (String)obj[3];
			value[4] = (Integer)obj[4] +"";
			value[5] = (String)obj[5];
			returnList.add(value);
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	public Map<Long,Integer> getDeptsLevel(List<String> deptIds){
		Map<Long, Integer> map = null;
		if(deptIds == null || deptIds.size() == 0) return null;
		String deptIdString = "";
		for(String strList:deptIds){
			deptIdString +=strList+",";
		}
		if(comm.isNull(deptIdString)) return null;
		
		deptIdString = deptIdString.substring(0, deptIdString.length()-1);
		
		String sql = "  select n1.id,level "+
			"  from cs_organ_model m "+
			" inner join cs_organ_node n1 on m.org_node_id = n1.id "+
			" inner join cs_organ_node n2 on m.parent_node_id = n2.id "+
			" where m.org_tree_id = 1040 "+
			" and n1.organ_node_type_id = 3 "+
			" and n1.id in ("+deptIdString+") "+
			"  start with n2.id = 2100 "+
			" connect by prior n1.id = n2.id  ";
		
		SQLQuery query = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.addScalar("id", Hibernate.LONG);
		query.addScalar("level", Hibernate.INTEGER);
		List list = query.list();
		Iterator iter = list.iterator();
		map = new HashMap<Long, Integer>();
		Object[] obj = null;
		while(iter.hasNext()){
			obj = (Object[])iter.next();
			map.put((Long)obj[0],(Integer)obj[1]);
		}
			
		return map;
	}
	
	public String countTimeOut(String loginName)
	{
		String sql = "select (select max(nam) as maxTime from (select inci.processname, inci.incident,part.helpurl,inci.priorities,part.substatus,part.overduetime,(case when (to_date(to_char(part.overduetime,'YYYY.MM.DD'),'YYYY.MM.DD')-to_date(to_char(sysdate,'YYYY.MM.DD'),'YYYY.MM.DD'))<0 then to_date(to_char(sysdate,'YYYY.MM.DD'),'YYYY.MM.DD')-to_date(to_char(part.overduetime,'YYYY.MM.DD'),'YYYY.MM.DD') end) as nam"+
		" from incidents inci, "+
		" ((select t.processname as pname,t.substatus,t.overduetime,"+
		" to_char(t.incident) as pincident, "+
		" t.processname as processname, "+
		" to_char(t.incident) as incident, "+
		" t.helpurl "+
		" from tasks t "+
		" where exists (select '' "+
		" from processes a "+
		" where t.processname = a.processname "+
		" and a.launchtype = 0 "+
		" and a.processname <> '拟办子流程' "+
		" and a.processname <> '办结子流程') "+
		" and t.status = 1 "+
		" and t.assignedtouser like '%ST/%' "+
		" and t.assignedtouser = 'ST/"+loginName+"') union "+
		" (select b.pname     as pname,t.substatus,t.overduetime,"+
		" b.pincident as pincident, "+
		" b.cname     as processname, "+
		" b.cincident as incident, "+
		" t.helpurl "+
		" from t_subprocess b, tasks t "+
		" where b.cname = t.processname "+
		" and b.cincident = t.incident "+
		" and not exists (select '' "+
		" from processes a "+
		" where t.processname = a.processname "+
		" and a.launchtype = 0 "+
		" and a.processname <> '拟办子流程' "+
		" and a.processname <> '办结子流程') "+
		" and t.status = 1 "+
		" and t.assignedtouser like '%ST/%' "+
		" and t.assignedtouser = 'ST/"+loginName+"')) part "+
		" where part.pname = inci.processname "+
		" and part.pincident = inci.incident and part.substatus='3')) as maxTime from dual";//统计超时流程的最大时间

		SQLQuery query = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.addScalar("maxTime", Hibernate.STRING);
		String str = (String)query.uniqueResult();
		return str;		
	}
	
	public int getUrgeCount(String userName,String nowDeptId,boolean falg,List<String> deptIds){
		int count = 0;
		//查找登陆人所在的部门的等级

		Map<Long,Integer> deptLevelMap = getDeptsLevel(deptIds);
		String deptSql = "";
		if(falg){
			Integer intTemp = deptLevelMap.get(Long.parseLong(nowDeptId));//PublicFunction.getDeptLevel(user_dept_id,dm);
			if(intTemp != null &&intTemp > 1){//如果当前登陆部门不是集团部门
				deptSql = " and instr(t.helpurl,'ST/" + userName + ":"+nowDeptId+"<+>') >0 ";
			}else if(intTemp != null &&intTemp == 1){
				deptSql = " and (instr(t.helpurl,'ST/" + userName + ":"+nowDeptId+"<+>') >0 or t.helpurl is null or instr(t.helpurl,'ST/" + userName + ":<+>') >0) ";
			}else{
				deptSql = " and 1<>1 ";
			}
		}
		String sql ="";
		sql = "select part3.*  from ( select part1.processname, "+
		" part1.incident, "+
		" i.summary, "+
		" (select h.name "+
		" from cs_user_organnode g, cs_organ_node h, cs_organ_model j "+
		" where g.security_user_id = "+
		" (select id "+
		" from cs_user csu "+
		" where removed = 0 "+
		" and 'ST/' || csu.login_name = i.initiator) "+
		" and j.org_node_id = g.organ_node_id "+
		" and j.org_tree_id = '1040' "+
		" and j.parent_node_id = h.id "+
		" and rownum = 1) as deptname, "+
		" (select name "+
		" from cs_user csu "+
		" where removed = 0 "+
		" and 'ST/' || csu.login_name = i.initiator) as username "+
		" FROM ((select t.processname, to_char(t.incident) incident "+
		" from tasks t "+
		" where exists (select '' "+
		" from processes e "+
		" where T.processname = e.processname "+
		" and e.launchtype = 0 "+
		" and e.processname <> '拟办子流程' "+
		" AND e.processname <> '办结子流程') "+
		" and t.substatus = 3 "+
		" and t.status = 1 AND t.RECIPIENTTYPE<>19) union "+
		" (select a.pname, a.pincident "+
		" from t_subprocess a, "+
		" (select c.processname, c.incident "+
		" from tasks c "+
		" where not exists (select '' "+
		" from processes b "+
		" where c.processname = b.processname "+
		" and b.launchtype = 0 "+
		" and b.processname <> '拟办子流程' "+
		" AND b.processname <> '办结子流程') "+
		" and c.substatus = 3 "+
		" and c.status = 1 AND c.RECIPIENTTYPE<>19) d "+
		" where d.processname = a.cname "+
		" and to_char(d.incident) = a.cincident)) PART1, "+
		" ((select t.processname processname, to_char(t.incident) incident "+ //--经历的主流程 
		" from tasks t "+
		" where t.assignedtouser = 'ST/"+userName+"' "+
		deptSql+
		" AND EXISTS (select '' "+
		" from processes a "+
		" where T.processname = a.processname "+
		" and a.launchtype = 0 "+
		" and a.processname <> '拟办子流程' "+
		" AND a.processname <> '办结子流程') "+
		" AND t.status = 3 AND t.RECIPIENTTYPE<>19) union "+
		" (select b.pname processname, b.pincident incident "+ // --经历的子流程
		" from T_SUBPROCESS b, tasks t "+
		" where b.cname = t.processname "+
		" and b.cincident = t.incident "+
		" and t.assignedtouser = 'ST/"+userName+"' "+
		deptSql+
		" AND NOT EXISTS (select '' "+
		" from processes a "+
		" where T.processname = a.processname "+
		" and a.launchtype = 0 "+
		" and a.processname <> '拟办子流程' "+
		" AND a.processname <> '办结子流程') "+
		" AND t.status = 3 AND t.RECIPIENTTYPE<>19) "+
		" ) PART2, "+
		" incidents i "+
		" where part1.processname = part2.processname "+
		" and part1.incident = part2.incident "+
		" and part1.processname = i.processname "+
		" and part1.incident = to_char(i.incident) ) part3" +
		" where " +
		" part3.processname || ':' || part3.incident not in (select tfuf.processname || ':' || tfuf.incidents from  " +
		" t_flow_urge_filter tfuf where tfuf .removed = 0) ";
		sql="select count(*) urgecount from ("+sql+") where 1=1";
		
		SQLQuery query = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.addScalar("urgecount", Hibernate.INTEGER);
		Integer countTemp = (Integer)query.uniqueResult();
		if(countTemp != null){
			return  countTemp.intValue();
		}
		return count;
	}
	
	public long countMessage(String login_name)	{	
		String sql="select count(*) as na from (select (select t.name from cs_user t where t.id=A.EMPIDSEND) as sender, (select t.name from cs_user t where t.id=A.EMPIDREC) as receiver,A.TITLE,SENDDATE,SEEDATE, A.STATE,A.SID,A.EMPIDREC from T_MSG_USERMESSAGE A,cs_user u where u.id=A.EMPIDREC and A.RECRDELETE='1' and A.RECRSTATE='1' and u.login_name='"+login_name+"' and A.STATE=0) where 1=1  order by STATE,SENDDATE desc";
		//System.out.println(s);
		SQLQuery query = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.addScalar("na", Hibernate.LONG);
		long countTemp = (Long)query.uniqueResult();
		return countTemp;
		//return (Long) this.getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(s).addScalar("na",Hibernate.LONG).uniqueResult();
		
	}
	
	public int countJbx(String loginName,String dept_id){
		int count = 0;
		boolean bumen = false;
		boolean jituan = false;
		String sql = "";
		sql="select count(*) as na from v_superinfo t where t.sup_login_name= 'ST/"+loginName+"'";		
		SQLQuery query = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.addScalar("na", Hibernate.INTEGER);
		int countTemp = (Integer)query.uniqueResult();
		//System.out.println("sql1======"+sql);
		//System.out.println("num1======"+countTemp);
		if(countTemp>0){
			jituan = true;
		}
		
		sql = "select count(*) as na from (select a.id,'ST/'||a.login_name as login_name,a.name,c.department_id from cs_user_group c,"
			+" (select distinct v.id,v.LOGIN_NAME,v.NAME from v_userdep v where v.REMOVED=0) a where c.security_group_id =" 
     		+" (select c.id from cs_group c where c.code = 'bumenlingdao') and a.id=c.security_user_id"
     		+" and c.department_id='"+dept_id+"' order by a.id)where login_name ='ST/"+loginName+"'";
		SQLQuery query1 = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query1.addScalar("na", Hibernate.INTEGER);
		countTemp = (Integer)query1.uniqueResult();
		//System.out.println("sql2======"+sql);
		//System.out.println("num2======"+countTemp);
		if(countTemp>0){
			bumen = true;
		}
		
		if(bumen){
			sql = "select count(*) as na from t_future t,incidents i where t.processname=i.processname and to_char(t.incident) = to_char(i.incident) and i.status = '1' and" 
			+ " (','||t.dept_id_list||',' like '%,"+dept_id+",%' ) "
			+ " and (i.processname,i.incident) not in  ("
				+ " select inci.processname,inci.incident from ((select t.processname processname, to_char(t.incident) incident from tasks t "
				+ " where" 
				+ " t.assignedtouser = 'ST/"+loginName+"' and EXISTS "
				+ " (select '' from processes a where T.processname = a.processname and a.launchtype = 0 and a.processname <> '拟办子流程' AND a.processname <> '办结子流程'))"
				+ " union "
				+ " (select b.pname processname, b.pincident incident from T_SUBPROCESS b, tasks t where b.cname =t.processname and b.cincident = t.incident "
				+ " and t.assignedtouser = 'ST/"+loginName+"' AND NOT EXISTS "
			+ " (select '' from processes a where T.processname = a.processname and a.launchtype = 0 and a.processname <> '拟办子流程' AND a.processname <> '办结子流程') "
				+ " union "
       		+ " select to_char(ta.process),to_char(ta.incidentno) from t_approvedinfo ta where ta.dept=( "
       		+ " select t.name from cs_organ_node t where t.id = '"+dept_id+"' "
       		+ " ) and ta.option_code in ('DEPT_LEADER_CHECK_PASS','DEPT_LEADER_CHECK_FAIL_NEW_NOTION','DEPT_SUBSCRIBE_LEADER_CHECK_PASS_NOT_MODIFICATION','DEPT_SUBSCRIBE_LEADER_INPUT_MODIFICATION_DELIVER_BACK') " 
				+ " )) part1,(select i.processname processname,i.incident from incidents i where i.status = 1 or i.status = 2 OR I.STATUS=8) inci "
				+ " where part1.processname = inci.processname and part1.incident = inci.incident)";
			SQLQuery query2 = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql);
			query2.addScalar("na", Hibernate.INTEGER);
			count = (Integer)query2.uniqueResult();
			//System.out.println("sql3======"+sql);
			//System.out.println("num3======"+count);
		}
		
		if(jituan){
			sql = "select count(*) as na from t_future t,incidents i where t.processname=i.processname and to_char(t.incident) = to_char(i.incident) and i.status = '1' and" 
				+ " t.leaderlist like '%ST/"+loginName+"%'"
				+ " and (i.processname,i.incident) not in  ("
  				+ " select inci.processname,inci.incident from ((select t.processname processname, to_char(t.incident) incident from tasks t "
 				+ " where" 
 				+ " t.assignedtouser = 'ST/"+loginName+"' and EXISTS "
 				+ " (select '' from processes a where T.processname = a.processname and a.launchtype = 0 and a.processname <> '拟办子流程' AND a.processname <> '办结子流程'))"
 				+ " union "
 				+ " (select b.pname processname, b.pincident incident from T_SUBPROCESS b, tasks t where b.cname =t.processname and b.cincident = t.incident "
 				+ " and t.assignedtouser = 'ST/"+loginName+"' AND NOT EXISTS "
				+ " (select '' from processes a where T.processname = a.processname and a.launchtype = 0 and a.processname <> '拟办子流程' AND a.processname <> '办结子流程') "
 				+ " union "
           		+ " select to_char(ta.process),to_char(ta.incidentno) from t_approvedinfo ta where ta.dept=( "
           		+ " select t.name from cs_organ_node t where t.id = '"+dept_id+"' "
           		+ " ) and ta.option_code in ('DEPT_LEADER_CHECK_PASS','DEPT_LEADER_CHECK_FAIL_NEW_NOTION','DEPT_SUBSCRIBE_LEADER_CHECK_PASS_NOT_MODIFICATION','DEPT_SUBSCRIBE_LEADER_INPUT_MODIFICATION_DELIVER_BACK') " 				
 				+ " )) part1,(select i.processname processname,i.incident from incidents i where i.status = 1 or i.status = 2 OR I.STATUS=8) inci "
 				+ " where part1.processname = inci.processname and part1.incident = inci.incident)";
			SQLQuery query3 = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql);
			query3.addScalar("na", Hibernate.INTEGER);
			count = (Integer)query3.uniqueResult();
			//System.out.println("sql4======"+sql);
			//System.out.println("num4======"+count);
		}
		return count;
	}
}
