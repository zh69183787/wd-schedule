/**
 * 
 */
package com.wonders.task.contractReview.service;

import com.wonders.task.contractReview.model.bo.PContract;

import java.util.List;

/** 
 * @ClassName: PContractServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年1月21日 下午3:22:54 
 *
 */
public interface PContractService {
	public void saveBatch(List<?> c);
	public void save(Object entity);
    public List<PContract> getContract(String selfNo);
}
