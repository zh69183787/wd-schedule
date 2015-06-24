/**
 * 
 */
package com.wonders.task.todoItem.constants;

/** 
 * @ClassName: WorkFlowConstants 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-1-22 上午11:29:29 
 *  
 */
public class TodoItemConstants {
	/**
	 * 待办状态
	 */
	public static final int STATUS_TODO = 0;
	
	/**
	 * 已办状态
	 */
	public static final int STATUS_DONE = 1;
	
	//-------------------------------------------------------------
	
	/**
	 * 待办事项类型（工作联系单 新）
	 */
	public static final int TYPE_DEPTCONTACT_NEW = 1;
	
	/**
	 * 待办事项类型（工作联系单）
	 */
	public static final int TYPE_DEPTCONTACT = 2;
	
	/**
	 * 待办事项类型（收文流程）
	 */
	public static final int TYPE_DOCRECEIVE = 3;
	
	/**
	 * 待办事项类型（发文流程）【包括 党委 纪委】
	 */
	public static final int TYPE_DOCSEND = 4;
	
	/**
	 * 待办事项类型（呈批件流程）
	 */
	public static final int TYPE_DIRECTIVE = 5;
	
	/**
	 * 待办事项类型（合同流程）
	 */
	public static final int TYPE_CONTRACT = 6;
	
	/**
	 * 待办事项类型（督办流程）
	 */
	public static final int TYPE_DOCDB = 7;
	
	/**
	 * 待办事项类型（工会发文流程）
	 */
	public static final int TYPE_GHSEND = 8;
	
	/**
	 * 待办事项类型（工会收文流程）
	 */
	public static final int TYPE_GHRECEIVE = 9;
	
	/**
	 * 待办事项类型（项目立项流程）
	 */
	public static final int TYPE_PROJECT = 10;
	
	
	/**
	 * 待办事项URL 公共（除工作联系单外）
	 */
	/**task_id=012115ab1591b4bdfbd0e068e459a9&task_user_name=ST/G00100000161
	&model_id=%E6%94%B6%E6%96%87%E6%B5%81%E7%A8%8B&instance_id=4890
	&step_name=%E9%83%A8%E9%97%A8%E6%8E%A5%E5%8F%97%E4%BA%BA%E5%B7%A5%E4%BD%9C%E5%88%86%E5%8F%91
	&pinstance_id=44523&processName=%E9%83%A8%E9%97%A8%E5%86%85%E9%83%A8%E5%AD%90%E6%B5%81%E7%A8%8B
	&task_type=0**/ //1 已办  0 待办 
	public static final String COMMON_TODO_URL = "http://10.1.44.18/openflowform.action"
											+ "?task_id=@taskid@&task_user_name=@Username@"
											+ "&model_id=@pname@&instance_id=@pincident@"//主流程 及 实例号
											+ "&step_name=@steplabel@"
											+ "&pinstance_id=@incident@&processName=@processname@"//子流程 及 实例号
											+ "&task_type=@task_type@";
	
	
	public static final String COMMON_TODO_SCAN = "http://10.1.44.17/sLogin/workflow/TaskStatus.aspx"
		 									+ "?TaskID=@taskid@";
	
	
	public static final String COMMON_TODO_SHOW = "priorities_show|processname|summary|steplabel|apply_username|deptName|memo";
}
