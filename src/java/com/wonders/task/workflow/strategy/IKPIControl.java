/**   
* @Title: IStrategy.java 
* @Package com.wonders.task.workflow.strategy 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年8月4日 下午3:05:57 
* @version V1.0   
*/
package com.wonders.task.workflow.strategy;

import com.wonders.task.workflow.model.flow.xml.ReviewMainXml;

/** 
 * @ClassName: IStrategy 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年8月4日 下午3:05:57 
 *  
 */
public interface IKPIControl {  
    public boolean operate(ReviewMainXml xml);  
}
