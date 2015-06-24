/**
 * 
 */
package com.wonders.task.todoItem.service;

import java.util.List;

import com.wonders.task.todoItem.model.vo.FlowInfo;
import com.wonders.task.todoItem.model.vo.UserInfo;

/** 
 * @ClassName: TodoItemService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-1-22 下午01:57:08 
 *  
 */
public interface QueryService {
	public List<UserInfo> findUserInfo();
	public List<FlowInfo> findTodoItems(UserInfo u);
}
