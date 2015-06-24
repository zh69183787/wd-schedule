/**
 * 
 */
package com.wonders.task.todoItem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.task.todoItem.dao.TodoItemDao;
import com.wonders.task.todoItem.model.bo.TodoItem;
import com.wonders.task.todoItem.service.TodoItemService;

/** 
 * @ClassName: TodoItemServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-1-22 下午03:50:48 
 *  
 */

@Transactional(value = "txManager2",propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
@Service("todoItemService")
public class TodoItemServiceImpl implements TodoItemService{
	private TodoItemDao todoItemDao;

	public void saveBatch(List<TodoItem> list){
		this.todoItemDao.saveBatch(list);
	}
	
	@SuppressWarnings("unchecked")
	public Object load(String id,Class clazz) {
		return this.todoItemDao.load(id, clazz);
	}
	
	
	public int deleteBatch(){
		return this.todoItemDao.deleteBatch();
	}
	
	
	
	
	
	
	
	
	
	public TodoItemDao getTodoItemDao() {
		return todoItemDao;
	}
	@Autowired(required=false)
	public void setTodoItemDao(@Qualifier("todoItemDao")TodoItemDao todoItemDao) {
		this.todoItemDao = todoItemDao;
	}
	
}
