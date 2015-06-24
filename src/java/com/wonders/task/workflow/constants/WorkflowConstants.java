/**   
* @Title: WorkflowConstants.java 
* @Package com.wonders.task.workflow.constants 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年5月5日 下午2:40:56 
* @version V1.0   
*/
package com.wonders.task.workflow.constants;

/** 
 * @ClassName: WorkflowConstants 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年5月5日 下午2:40:56 
 *  
 */
public class WorkflowConstants {
	public static final String WORKFLOW_CONTRACT_REVIEW = "合同后审流程";
	public static final String WORKFLOW_RECEIVE_RECV = "新收文流程";
	public static final String WORKFLOW_RECEIVE_REDV = "新收呈批件";
	public static final String WORKFLOW_PROJECT_DISCARD = "项目销项流程";
	
	public static String getType(String in){
		if(WORKFLOW_CONTRACT_REVIEW.equals(in)){
			return "contractReview";
		}else if(WORKFLOW_RECEIVE_RECV.equals(in)){
			return "docReceive";
		}else if(WORKFLOW_RECEIVE_REDV.equals(in)){
			return "docDirective";
		}else if(WORKFLOW_PROJECT_DISCARD.equals(in)){
			return "projectDiscard";
		}
		
		return "";
	}
}
