/**   
* @Title: ExternalOldDaoImpl.java 
* @Package com.wonders.task.workflowDiscardExternal.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年7月1日 下午1:16:50 
* @version V1.0   
*/
package com.wonders.task.workflowDiscardExternal.dao;

import java.util.List;

import com.wonders.task.workflowDiscardExternal.model.bo.DiscardBo;

/** 
 * @ClassName: ExternalOldDaoImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年7月1日 下午1:16:50 
 *  
 */
public interface ExternalOldDao {
	public DiscardBo getMainBo(String pname,String pincident);
}
