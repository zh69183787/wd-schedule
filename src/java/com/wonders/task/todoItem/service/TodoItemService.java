/**
 * 
 */
package com.wonders.task.todoItem.service;

import java.util.List;

import com.wonders.task.todoItem.model.bo.TodoItem;

/** 
 * @ClassName: TodoItemService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-1-22 下午03:50:41 
 *  
 */
public interface TodoItemService {
	public void saveBatch(List<TodoItem> list);
	@SuppressWarnings("unchecked")
	public Object load(String id,Class clazz) ;
	public int deleteBatch();
}
