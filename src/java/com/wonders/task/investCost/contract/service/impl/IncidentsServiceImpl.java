package com.wonders.task.investCost.contract.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.wonders.task.investCost.contract.dao.IncidentsDao;
import com.wonders.task.investCost.contract.model.bo.HtXx;
import com.wonders.task.investCost.contract.service.IncidentsService;

@Repository("incidentsService")
public class IncidentsServiceImpl implements IncidentsService {
	private IncidentsDao incidentsDao;

	@Autowired(required=false)
	public void setIncidentsDao(@Qualifier(value="incidentsDao")IncidentsDao incidentsDao) {
		this.incidentsDao = incidentsDao;
	}

	@Override
	public List<HtXx> findIncidentsByDate(Date date) {
		
		return incidentsDao.findIncidentsByDate(date);
	}

	

	
}
