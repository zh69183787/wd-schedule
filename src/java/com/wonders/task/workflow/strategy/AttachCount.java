/**   
* @Title: BudgetEstimate.java 
* @Package com.wonders.task.workflow.strategy 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年8月4日 下午3:22:30 
* @version V1.0   
*/
package com.wonders.task.workflow.strategy;

import java.util.List;

import com.wonders.task.workflow.model.common.bo.AttachFile;
import com.wonders.task.workflow.model.flow.xml.ReviewMainXml;

/** 
 * @ClassName: BudgetEstimate 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年8月4日 下午3:22:30 
 *  
 */
public class AttachCount implements IKPIControl{

	/** 
	* @Title: operate 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param     设定文件 
	* @throws 
	*/
	@Override
	public boolean operate(ReviewMainXml xml) {
		// TODO Auto-generated method stub
		List<AttachFile> attachList = xml.getAttachVo().getAttachList();
		if(attachList != null && attachList.size() < 3 ){
			return true;
		}
		
		return false;
	}

}
