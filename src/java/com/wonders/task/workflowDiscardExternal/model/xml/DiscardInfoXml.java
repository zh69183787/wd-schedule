/**   
* @Title: DiscardInfoXml.java 
* @Package com.wonders.task.workflowDiscardExternal.model.xml 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年7月1日 下午2:35:31 
* @version V1.0   
*/
package com.wonders.task.workflowDiscardExternal.model.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.wonders.task.workflowDiscardExternal.model.bo.DiscardBo;
import com.wonders.task.workflowDiscardExternal.model.vo.ApproveVoList;
import com.wonders.task.workflowDiscardExternal.model.vo.RegisterVo;
import com.wonders.task.workflowDiscardExternal.model.vo.DiscardVo;

/** 
 * @ClassName: DiscardInfoXml 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年7月1日 下午2:35:31 
 *  
 */

@XmlRootElement(name = "root")
public class DiscardInfoXml {
	
	@XmlElement(name = "basicData")
	public DiscardVo mainBo;
	
	@XmlElement(name = "approveData")
	public ApproveVoList approveVo;
	
	@XmlElement(name = "registerData")
	public RegisterVo registerVo;
	
	
}
