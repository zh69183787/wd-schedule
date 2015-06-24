
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

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.task.investCost.contract.dao.HtXxDao;
import com.wonders.task.investCost.contract.model.bo.HtXx;


@Repository("htXxDao")
public class HtXxDaoImpl implements HtXxDao {
	
	private HibernateTemplate hibernateTemplate;
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	//注入hibernateTemplate
	@Resource(name="hibernateTemplate2")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	@Override
	public List<HtXx> findByFlag(String flag) {
		String hql ="from HtXx t where t.falg='"+flag+"' and t.removed='0'";
		return getHibernateTemplate().find(hql);
	}
	@Override
	public void updateAll(List<HtXx> htXxList) {
		getHibernateTemplate().saveOrUpdateAll(htXxList);
	}
	@Override
	public List<HtXx> findByTransferStatusAndFlag(String status,String flag) {
		String hql ="from HtXx t where t.removed='0' and t.transferStatus='"+status+"' and t.flag='"+flag+"'";
		return getHibernateTemplate().find(hql);
	}
	
	
}
	
