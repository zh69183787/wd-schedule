/**
 * 
 */
package com.wonders.quartz.cocc.model.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.wonders.quartz.cocc.model.vo.CoccListVo;
import com.wonders.quartz.cocc.model.vo.CoccReportVo;

/** 
 * @ClassName: CoccReportXml 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013年12月10日 下午2:31:33 
 *  
 */
@XmlRootElement(name = "root")
public class CoccReportXml {
	@XmlAttribute(name = "type")
	public String type="coccMetroWeekReport";
	@XmlAttribute(name = "date")
	public String date= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
	@XmlElement(name = "list")
	public CoccListVo list;

	
	
}
