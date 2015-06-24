/**
 * 
 */
package com.wonders.task.workflow.model.flow.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.wonders.task.workflow.model.flow.bo.DiscardAssetBo;


/** 
 * @ClassName: DiscardAssetVo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-7-19 上午9:38:00 
 *  
 */
@XmlRootElement(name="DiscardAssetVo")
@XmlAccessorType(XmlAccessType.FIELD)
public class DiscardAssetVo implements Serializable{

	@XmlElement(name = "asset")
	private List<DiscardAssetBo> assets = new ArrayList<DiscardAssetBo>();

	@XmlTransient
	public List<DiscardAssetBo> getAssets() {
		return assets;
	}

	public void setAssets(List<DiscardAssetBo> assets) {
		this.assets = assets;
	}

}
