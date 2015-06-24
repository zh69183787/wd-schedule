/**
 * 
 */
package com.wonders.task.todoItem.main;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.wonders.schedule.common.util.SimpleLogger;
import com.wonders.task.sample.ITaskService;
import com.wonders.task.todoItem.constants.TodoItemConstants;
import com.wonders.task.todoItem.model.bo.TodoItem;
import com.wonders.task.todoItem.model.vo.FlowInfo;
import com.wonders.task.todoItem.model.vo.UserInfo;
import com.wonders.task.todoItem.service.QueryService;
import com.wonders.task.todoItem.service.TodoItemService;
import com.wonders.task.todoItem.util.StringUtil;
import com.wonders.task.todoItem.util.TypeUtil;

import java.util.ArrayList;

/** 
 * @ClassName: TodoItemMain 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-1-22 下午02:57:13 
 *  
 */
@Transactional(value = "txManager2",propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
@Service("todoItemMain")
public class TodoItemMain implements ITaskService{
	static SimpleLogger log = new SimpleLogger(TodoItemMain.class);
	private QueryService queryService;
	private	TodoItemService todoItemService;
	private Gson gson = new Gson();
	/** 
	* @Title: exec 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param param
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public String exec(String param) {
		List<TodoItem> todoList = new ArrayList<TodoItem>();
		List<FlowInfo> flowList = new ArrayList<FlowInfo>();
		List<UserInfo> userList = this.queryService.findUserInfo();
		//System.out.println("userList.size()="+userList.size());
		log.info("活跃用户数： "+userList.size());
		for(UserInfo u : userList){
			List<FlowInfo> tmpList = this.queryService.findTodoItems(u);
			//System.out.println("tmpList.size()="+tmpList.size());
			if(tmpList !=null && tmpList.size()>0){
				flowList.addAll(tmpList);
			}	
		}
		
		int delCount = this.todoItemService.deleteBatch();
		log.info("删除待办项条数： "+delCount);
		for(FlowInfo f : flowList){
			TodoItem t = new TodoItem();
			t.setDeptId(StringUtil.getNotNullValueString(f.getDeptId()));
			t.setStatus(TodoItemConstants.STATUS_TODO);
			t.setKey("");
			t.setLoginName(StringUtil.getNotNullValueString(f.getUserName()));
			t.setOccurTime(StringUtil.getNotNullValueString(f.getEndtime()));
			t.setTitle(StringUtil.getNotNullValueString(f.getSummary()));
			t.setData(gson.toJson(f).replace("\\u003c", "<").replace("\\u003e", ">").replace("\\u003d", "="));
			t.setType(TypeUtil.getType(f.getPname()));
			t.setTypename(f.getPname());
			t.setApplydept(f.getDeptname());
			//System.out.println(gson.toJson(f));
			todoList.add(t);
			//System.out.println("todoList.size()"+todoList.size());
		}
		log.info("插入待办项条数：  "+todoList.size());
		this.todoItemService.saveBatch(todoList);
		
		//TodoItem d = (TodoItem)this.todoItemService.load("8a81a97c3c624c79013c624dcb5000a6", TodoItem.class);
		//System.out.println(d.getData());
		return "";
	}
	
	
	
	public TodoItemService getTodoItemService() {
		return todoItemService;
	}
	@Autowired(required=false)
	public void setTodoItemService(@Qualifier("todoItemService")TodoItemService todoItemService) {
		this.todoItemService = todoItemService;
	}

	public QueryService getQueryService() {
		return queryService;
	}
	@Autowired(required=false)
	public void setQueryService(@Qualifier("queryService")QueryService queryService) {
		this.queryService = queryService;
	}
}
