/**
 * 
 */
package com.wonders.task.autoUrge.service;

import java.util.List;
import java.util.Map;

import com.wonders.task.autoUrge.model.vo.UserUrgeVo;

/** 
 * @ClassName: AutoUrgeService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-4-29 上午11:37:22 
 *  
 */
public interface AutoUrgeService {
	public void saveBatch(List<?> c);
	public List<UserUrgeVo> listUrgeInfo(String overdate);
	public Map<String,String> userMobileMap();
}
