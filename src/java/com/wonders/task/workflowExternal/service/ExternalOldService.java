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

import com.wonders.task.workflowExternal.model.bo.ReviewBo;
import com.wonders.task.workflowExternal.model.vo.ApproveVo;
import com.wonders.task.workflowExternal.model.vo.ProcessInfoVo;
import com.wonders.task.workflowExternal.model.vo.ReviewVo;

/** 
 * @ClassName: ExternalNewService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年7月1日 下午1:45:50 
 *  
 */
public interface ExternalOldService {
	public List<ProcessInfoVo> getInfo();
	public List<ApproveVo> getApproveInfo(String pname,String pincident);
	public ReviewVo getMainBo(String pname, String pincident);
}
