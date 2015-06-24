/**   
* @Title: ExternalNewServiceImpl.java 
* @Package com.wonders.task.workflowDiscardExternal.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年7月1日 下午1:17:52 
* @version V1.0   
*/
package com.wonders.task.workflowDiscardExternal.service.impl;

import com.wonders.schedule.util.DbUtil;
import com.wonders.task.workflowDiscardExternal.model.vo.RegisterVo;
import com.wonders.task.workflowDiscardExternal.service.ExternalNewService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/** 
 * @ClassName: ExternalNewServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年7月1日 下午1:17:52 
 *  
 */

@Transactional(value = "txManager",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service("externalDiscardNewService")
public class ExternalNewServiceImpl implements ExternalNewService{
	public RegisterVo getInfo(String loginName){
		RegisterVo vo = null;
		String sql = "select  distinct 'ST/'||login_name loginName,"
				+ " name userName,"
				+ " dept_id deptId, "
				+ " dept_name deptName "
				+ " from v_userdep v where  'ST/'||v.LOGIN_NAME = ?";
		try{
			vo = DbUtil.getJdbcTemplate("stptdemo").
				queryForObject(sql,new Object[]{loginName},new BeanPropertyRowMapper<RegisterVo>(RegisterVo.class));
		}catch(Exception e){
			
		}
		return vo;
	}

}
