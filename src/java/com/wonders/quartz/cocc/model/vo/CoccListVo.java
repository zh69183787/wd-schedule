/**
 * 
 */
package com.wonders.quartz.cocc.model.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/** 
 * @ClassName: CoccReport 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013年12月10日 下午2:12:33 
 *  
 */
@XmlRootElement(name = "bbbb")
@XmlAccessorType(XmlAccessType.FIELD)
public class CoccListVo {
	@XmlElement(name = "cocc")
	public List<CoccReportVo> list;
	
}
