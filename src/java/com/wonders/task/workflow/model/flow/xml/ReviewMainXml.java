/**
 * 
 */
package com.wonders.task.workflow.model.flow.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.wonders.task.workflow.model.common.xml.AttachDataVo;
import com.wonders.task.workflow.model.common.xml.OptionDataVo;
import com.wonders.task.workflow.model.common.xml.RegisterDataVo;
import com.wonders.task.workflow.model.flow.vo.ReviewMainVo;


/** 
 * @ClassName: ReviewMainXml 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年3月12日 下午2:25:13 
 *  
 */

@XmlRootElement(name = "root")
public class ReviewMainXml {
	
	@XmlElement(name = "basicData")
	public ReviewMainVo mainVo = new ReviewMainVo();
	
	@XmlElement(name = "attachData")
	public AttachDataVo attachVo = new AttachDataVo();
	
	@XmlElement(name = "registerData")
	public RegisterDataVo registerVo = new RegisterDataVo();
	
	@XmlElement(name = "optionData")
	public OptionDataVo optionVo = new OptionDataVo();
	
	@XmlTransient
	public ReviewMainVo getMainVo() {
		return mainVo;
	}
	public void setMainVo(ReviewMainVo mainVo) {
		this.mainVo = mainVo;
	}
	
	@XmlTransient
	public AttachDataVo getAttachVo() {
		return attachVo;
	}
	public void setAttachVo(AttachDataVo attachVo) {
		this.attachVo = attachVo;
	}
	
	@XmlTransient
	public RegisterDataVo getRegisterVo() {
		return registerVo;
	}
	public void setRegisterVo(RegisterDataVo registerVo) {
		this.registerVo = registerVo;
	}
	
	@XmlTransient
	public OptionDataVo getOptionVo() {
		return optionVo;
	}
	public void setOptionVo(OptionDataVo optionVo) {
		this.optionVo = optionVo;
	}
	
	
	
}
