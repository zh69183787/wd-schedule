/**   
* @Title: XmlParser.java 
* @Package com.wonders.dataExchange.factory 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年4月16日 下午8:26:18 
* @version V1.0   
*/
package com.wonders.task.workflow.factory;

import com.wonders.task.workflow.model.common.bo.DwWorkflowInterface;
/** 
 * @ClassName: XmlParser 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年4月16日 下午8:26:18 
 *  
 */
public interface XmlParser {
	public void autoLaunch(DwWorkflowInterface bo);
}
