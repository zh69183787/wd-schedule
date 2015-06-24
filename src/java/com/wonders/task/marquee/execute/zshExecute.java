package com.wonders.task.marquee.execute;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.schedule.util.PropertyUtil;
import com.wonders.schedule.util.SpringBeanUtil;
import com.wonders.task.autoUrge.model.bo.TShortMsg;
import com.wonders.task.marquee.model.bo.MarqueeMsg;
import com.wonders.task.marquee.service.NewService;
import com.wonders.task.marquee.service.OldService;
import com.wonders.task.marquee.util.HelpUtil;
import com.wonders.task.sample.ITaskService;
import com.wonders.task.todoItem.util.StringUtil;

@Transactional(value = "txManager2", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
/* 功能模块入口点，beanid即数据库配置中的name */
@Service("zshExecute")
public class zshExecute implements ITaskService {
	private OldService oldService;
	@Autowired(required = false)
	public void setOldService(
			@Qualifier(value = "oldService") OldService oldService) {
		this.oldService = oldService;
	}

	

	@Override
	public String exec(String param) {
		String mobile = "13601734664";
		//mobile = "13917168061";
		String content = "";
		
		
		
		String sql1 = "select count(i.incident),'' a from incidents i where (i.processname,i.incident) in ("
				+ " select t.cname,t.cincident from t_subprocess t where t.pname='多级工作联系单' and t.pincident=4347"
				+ " and t.cname='部门内部子流程') and i.status=1";
		
		String sql2 = "select distinct v.Dept,v.name,'' a from tasks k,stptnew.v_dept_leaders v,"
				+ " (select i.processname,i.incident from incidents i where (i.processname,i.incident) in ("
				+ " select t.cname,t.cincident from t_subprocess t where t.pname='多级工作联系单' and t.pincident=4347"
				+ " and t.cname='部门内部子流程') and i.status=1"
				+ " )m where k.status=1 and k.steplabel='部门领导审核' and k.assignedtouser like '%ST/%' "
				+ " and k.processname=m.processname "
				+ " and k.incident=m.incident and 'ST/'||v.login_name = k.assignedtouser";
		
		String sql3 = "select distinct v.Dept,'' a from tasks k,stptnew.v_dept_leaders v,"
				+ " ("
				+ " select i.processname,i.incident from incidents i where (i.processname,i.incident) in ("
				+ " select t.cname,t.cincident from t_subprocess t where t.pname='多级工作联系单' and t.pincident=4347"
				+ " and t.cname='部门内部子流程') and i.status=2"
				+ " )m where k.status=3 and k.steplabel='部门领导审核' and k.assignedtouser like '%ST/%' "
				+ " and k.processname=m.processname and k.incident=m.incident and 'ST/'||v.login_name = k.assignedtouser"
				+ " ";
		
		List<Object[]> list1 = this.oldService.findBySql(sql1);
		List<Object[]> list2 = this.oldService.findBySql(sql2);
		List<Object[]> list3 = this.oldService.findBySql(sql3);
		
		if(Integer.valueOf(list1.get(0)[0].toString()) > 0){
			if(list2!=null && list2.size() > 0){
				content += "终端安全隔离联系单 当前流转至下列部门领导处：";
				for(Object[] oo: list2){
					content += oo[0] +"("+oo[1]+") " ;
				}
				content += "；";
			}
			if(list3!=null && list3.size() > 0){
				content += "当前流转完成部门为：";
				for(Object[] oo: list3){
					content += oo[0] + " " ;
				}
			}
		}
		
		List<TShortMsg> list = new ArrayList<TShortMsg>();
		TShortMsg t = new TShortMsg();
		t.setMobile(mobile);
		t.setContent(content);
		list.add(t);
		oldService.saveOrUpdateAll(list);

		return "";
	}

	public static void main(String[] args) {
		ApplicationContext applicationContext = null;
		String[] fileUrl = new String[] { "classpath*:*Context*.xml" };
		applicationContext = new ClassPathXmlApplicationContext(fileUrl);
		ITaskService task = (ITaskService) SpringBeanUtil
				.getBean("zshExecute");
		task.exec(""); 

	}
}
