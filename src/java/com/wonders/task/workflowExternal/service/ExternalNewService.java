/**   
* @Title: ExternalNewService.java 
* @Package com.wonders.task.workflowExternal.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年7月1日 下午1:45:50 
* @version V1.0   
*/
package com.wonders.task.workflowExternal.service;

import java.util.List;

import com.wonders.task.workflowExternal.model.vo.RegisterVo;

/** 
 * @ClassName: ExternalNewService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年7月1日 下午1:45:50 
 *  
 */
public interface ExternalNewService {
	public RegisterVo getInfo(String loginName);
}
