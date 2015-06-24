/**
 * 
 */
package com.wonders.quartz.cocc.service;

import java.util.List;

import com.wonders.quartz.cocc.model.vo.CoccReportVo;

/** 
 * @ClassName: CoccReportService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013年12月10日 下午2:28:14 
 *  
 */
public interface CoccReportService {
	public List<CoccReportVo> getResult(String sql);
}
