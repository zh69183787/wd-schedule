/**
 * 
 */
package com.wonders.task.workflow.model.common.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/** 
 * @ClassName: RegisterDataVo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年3月12日 下午8:52:26 
 *  
 */

@XmlRootElement(name="OptionDataVo")
@XmlAccessorType(XmlAccessType.FIELD)
public class OptionDataVo {
	private String choice;
	private String remark;
	public String getChoice() {
		return choice;
	}
	public void setChoice(String choice) {
		this.choice = choice;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
