/**   
* @Title: ExternalNewDaoImpl.java 
* @Package com.wonders.task.workflowDiscardExternal.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年7月1日 下午1:17:14 
* @version V1.0   
*/
package com.wonders.task.workflowDiscardExternal.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.task.workflowDiscardExternal.dao.ExternalOldDao;
import com.wonders.task.workflowDiscardExternal.model.bo.DiscardBo;

/** 
 * @ClassName: ExternalNewDaoImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年7月1日 下午1:17:14 
 *  
 */

@Repository("externalDiscardOldDao")
public class ExternalOldDaoImpl implements ExternalOldDao{
	@Resource(name="hibernateTemplate2")
	private HibernateTemplate hibernateTemplate;

	@Override
	public DiscardBo getMainBo(String pname, String pincident) {
		String hql = "from DiscardBo t where t.removed='0' "
				+ " and t.modleId = ? and t.instanceId = ?";
		List<DiscardBo> list = this.hibernateTemplate.find(hql, new Object[]{pname,pincident});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	
	
}
