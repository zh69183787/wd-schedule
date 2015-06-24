/**   
* @Title: ApproveVo.java 
* @Package com.wonders.task.workflowDiscardExternal.model.vo 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年7月1日 下午2:44:40 
* @version V1.0   
*/
package com.wonders.task.workflowDiscardExternal.model.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/** 
 * @ClassName: ApproveVo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年7月1日 下午2:44:40 
 *  
 */

@XmlRootElement(name = "a")
public class ApproveVoList {
	
	@XmlElement(name = "approve")
	public List<ApproveVo> list;
}
