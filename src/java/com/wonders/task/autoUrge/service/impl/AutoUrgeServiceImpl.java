/**
 * 
 */
package com.wonders.task.autoUrge.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import com.wonders.schedule.util.DbUtil;
import com.wonders.task.autoUrge.dao.AutoUrgeDao;
import com.wonders.task.autoUrge.model.vo.UserMobileVo;
import com.wonders.task.autoUrge.model.vo.UserUrgeVo;
import com.wonders.task.autoUrge.service.AutoUrgeService;
import com.wonders.task.autoUrge.util.GenerateSqlUtil;
/** 
 * @ClassName: AutoUrgeServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-4-29 上午11:37:49 
 *  
 */

@Service("autoUrgeService")
public class AutoUrgeServiceImpl implements AutoUrgeService{
	private AutoUrgeDao dao;

	public AutoUrgeDao getDao() {
		return dao;
	}

	@Autowired(required=false)
	public void setDao(@Qualifier("autoUrgeDao") AutoUrgeDao dao) {
		this.dao = dao;
	}
	
	public void saveBatch(List<?> c){
		this.dao.saveBatch(c);
	}
	
	public List<UserUrgeVo> listUrgeInfo(String overdate){
		String sql = GenerateSqlUtil.gengerateUrgeInfoSql(overdate);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<UserUrgeVo> list = DbUtil.getJdbcTemplate("stptinc").query(sql, new BeanPropertyRowMapper(UserUrgeVo.class));
		return list;
	}
	
	public Map<String,String> userMobileMap(){
		String sql = GenerateSqlUtil.generateMobleInfoSql();
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<UserMobileVo> list = DbUtil.getJdbcTemplate("stptinc").query(sql,new BeanPropertyRowMapper(UserMobileVo.class));
		Map<String,String> mobileMap = new HashMap<String,String>();
		if(list != null && list.size() > 0){
			for(UserMobileVo vo : list){
				mobileMap.put(vo.getUserId(), vo.getMobile());
			}
		}
		
		return mobileMap;
	}
	

}
