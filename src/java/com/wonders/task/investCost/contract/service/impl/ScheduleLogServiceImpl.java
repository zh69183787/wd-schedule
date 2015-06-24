package com.wonders.task.investCost.contract.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.task.investCost.contract.dao.ScheduleLogDao;
import com.wonders.task.investCost.contract.service.ScheduleLogService;

@Transactional(value = "txManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Repository("scheduleLogService")
public class ScheduleLogServiceImpl implements ScheduleLogService {
	private ScheduleLogDao scheduleLogDao;

	@Autowired(required=false)
	public void setScheduleLogDao(@Qualifier(value="scheduleLogDao")ScheduleLogDao scheduleLogDao) {
		this.scheduleLogDao = scheduleLogDao;
	}

	@Override
	public Date findLastExecuteDate(String name,String process) {
		return scheduleLogDao.findLastExecuteDate(name,process);
	}

	
}
