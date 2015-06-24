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
import com.wonders.task.workflow.model.flow.vo.DiscardAssetVo;
import com.wonders.task.workflow.model.flow.vo.DiscardMainVo;


/** 
 * @ClassName: DiscardMainXml 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年11月28日 下午2:25:13 
 *  
 */

@XmlRootElement(name = "root")
public class DiscardMainXml {
	
	@XmlElement(name = "basicData")
	public DiscardMainVo mainVo = new DiscardMainVo();
	
	@XmlElement(name = "assetData")
	public DiscardAssetVo assetVo = new DiscardAssetVo();
	
	@XmlElement(name = "attachData")
	public AttachDataVo attachVo = new AttachDataVo();
	
	@XmlElement(name = "registerData")
	public RegisterDataVo registerVo = new RegisterDataVo();
	
	@XmlElement(name = "optionData")
	public OptionDataVo optionVo = new OptionDataVo();
	
	@XmlTransient
	public DiscardMainVo getMainVo() {
		return mainVo;
	}
	public void setMainVo(DiscardMainVo mainVo) {
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
	
	@XmlTransient
	public DiscardAssetVo getAssetVo() {
		return assetVo;
	}
	public void setAssetVo(DiscardAssetVo assetVo) {
		this.assetVo = assetVo;
	}
	
}
