/**   
* @Title: KPIContext.java 
* @Package com.wonders.task.workflow.strategy 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年8月4日 下午3:06:10 
* @version V1.0   
*/
package com.wonders.task.workflow.strategy;

import com.wonders.task.workflow.model.flow.xml.ReviewMainXml;

/** 
 * @ClassName: KPIContext 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年8月4日 下午3:06:10 
 *  
 */
public class KPIContext {
	private IKPIControl strategy;
	
	public KPIContext (IKPIControl strategy){
		this.strategy = strategy;
	}
	
	 public boolean operate(ReviewMainXml xml){  
		 return this.strategy.operate(xml);  
	 } 
}
