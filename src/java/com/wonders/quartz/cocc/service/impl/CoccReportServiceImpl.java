/**
 * 
 */
package com.wonders.quartz.cocc.service.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.wonders.quartz.cocc.model.vo.CoccReportVo;
import com.wonders.quartz.cocc.service.CoccReportService;
import com.wonders.schedule.util.DbUtil;

/** 
 * @ClassName: CoccReportServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013年12月10日 下午2:27:47 
 *  
 */
@Service("coccReportService")
public class CoccReportServiceImpl implements CoccReportService{
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CoccReportVo> getResult(String sql ){
		List<CoccReportVo> list = DbUtil.getJdbcTemplate("stfb").
				query(sql, new BeanPropertyRowMapper(CoccReportVo.class));
		
		return list;
	}
}
