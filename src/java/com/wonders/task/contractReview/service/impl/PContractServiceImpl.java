/**
 * 
 */
package com.wonders.task.contractReview.service.impl;

import java.util.List;

import com.wonders.task.contractReview.model.bo.PContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wonders.task.contractReview.dao.PContractDao;
import com.wonders.task.contractReview.service.PContractService;

/** 
 * @ClassName: PContractServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年1月21日 下午3:22:54 
 *  
 */

@Service("pContractService")
public class PContractServiceImpl implements PContractService{
	private PContractDao dao;

	public PContractDao getDao() {
		return dao;
	}
	@Autowired(required=false)
	public void setDao(@Qualifier("pContractDao")PContractDao dao) {
		this.dao = dao;
	}
	
	public void save(Object entity){
		this.dao.save(entity);
	}

    @Override
    public List<PContract> getContract(String selfNo) {
        return dao.findBySelfNo(selfNo);
    }

    public void saveBatch(List<?> c){

		this.dao.saveBatch(c);
	}
	
}
