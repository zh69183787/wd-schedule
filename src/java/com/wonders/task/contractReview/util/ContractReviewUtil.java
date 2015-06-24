/**
 * 
 */
package com.wonders.task.contractReview.util;

import com.wonders.schedule.util.PropertyUtil;

/** 
 * @ClassName: ContractReviewUtil 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年1月21日 下午3:37:07 
 *  
 */
public class ContractReviewUtil {
	public final static String url = PropertyUtil.getValueByKey("config.properties", "attachpath");
}
