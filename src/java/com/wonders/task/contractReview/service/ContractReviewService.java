/**
 * 
 */
package com.wonders.task.contractReview.service;

import java.util.List;
import java.util.Map;

import com.wonders.task.contractReview.model.vo.CompanyRoute;
import com.wonders.task.contractReview.model.bo.PAttach;
import com.wonders.task.contractReview.model.bo.PContract;

/** 
 * @ClassName: ContractReviewService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年1月21日 下午12:07:07 
 *  
 */
public interface ContractReviewService {
	public List<PContract> getInfo();
	public List<PAttach> getAttachInfo(String groupId);
    Map<String,CompanyRoute> getLine();
    Map<String,CompanyRoute> getCompany();
    Map getCorporation();

    Map<String,String> getDept();
}
