/**   
* @Title: ReviewInfoXml.java 
* @Package com.wonders.task.workflowExternal.model.xml 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年7月1日 下午2:35:31 
* @version V1.0   
*/
package com.wonders.task.workflowExternal.model.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.wonders.task.workflowExternal.model.bo.ReviewBo;
import com.wonders.task.workflowExternal.model.vo.ApproveVoList;
import com.wonders.task.workflowExternal.model.vo.RegisterVo;
import com.wonders.task.workflowExternal.model.vo.ReviewVo;

/** 
 * @ClassName: ReviewInfoXml 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年7月1日 下午2:35:31 
 *  
 */

@XmlRootElement(name = "root")
public class ReviewInfoXml {
	
	@XmlElement(name = "basicData")
	public ReviewVo mainBo;
	
	@XmlElement(name = "approveData")
	public ApproveVoList approveVo;
	
	@XmlElement(name = "registerData")
	public RegisterVo registerVo;
	
	
}
