/** 
 * Copyright (c) 1995-2011 Wonders Information Co.,Ltd. 
 * 1518 Lianhang Rd,Shanghai 201112.P.R.C.
 * All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of WondersGroup.
 * You shall not disclose such Confidential Information and shall use it only in accordance 
 * with the terms of the license agreement you entered into with WondersGroup. 
 *
 */

package com.wonders.task.investCost.contract.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.task.investCost.contract.dao.ScheduleLogDao;
import com.wonders.task.investCost.contract.model.bo.ScheduleLog;



@Repository("scheduleLogDao")
public class ScheduleLogDaoImpl implements ScheduleLogDao {
	private HibernateTemplate hibernateTemplate;
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	//注入hibernateTemplate
	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	@Override
	public Date findLastExecuteDate(String name,String process) {
		
		String hql ="from ScheduleLog t where t.name='"+name+"' and t.process='"+process+"' order by t.execTime desc";
		List<ScheduleLog> list = getHibernateTemplate().find(hql);
		if(list!=null && list.size()>0){
			String time = list.get(0).getExecTime(); 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return sdf.parse(time);
			} catch (ParseException e) {
				return null;
			}
		}
		return null;
	}
	
	
}
