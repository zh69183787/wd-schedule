/**
 * 
 */
package com.wonders.task.todoItem.util;

import com.wonders.task.todoItem.constants.TodoItemConstants;

/** 
 * @ClassName: TypeUtil 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-1-22 下午04:34:19 
 *  
 */
public class TypeUtil {
	public static int getType(String type){
		int result = 0;
		if(type ==null || type.length()==0){
			return 0;
		}
		if(type.equals("收文流程")){
			result = TodoItemConstants.TYPE_DOCRECEIVE;
		}else if(type.equals("发文流程")){
			result = TodoItemConstants.TYPE_DOCSEND;
		}else if(type.equals("呈批件流程")){
			result = TodoItemConstants.TYPE_DIRECTIVE;
		}else if(type.equals("合同流程")){
			result = TodoItemConstants.TYPE_CONTRACT;
		}else if(type.equals("督办流程")){
			result = TodoItemConstants.TYPE_DOCDB;
		}else if(type.equals("工会发文流程")){
			result = TodoItemConstants.TYPE_GHSEND;
		}else if(type.equals("工会收文流程")){
			result = TodoItemConstants.TYPE_GHRECEIVE;
		}else if(type.equals("项目立项流程")){
			result = TodoItemConstants.TYPE_PROJECT;
		}else if(type.equals("工作联系单")){
			result = TodoItemConstants.TYPE_DEPTCONTACT;
		}
		
		return result;
	}
}
