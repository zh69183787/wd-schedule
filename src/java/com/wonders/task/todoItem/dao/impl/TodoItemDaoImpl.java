/**
 * 
 */
package com.wonders.task.todoItem.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.task.todoItem.dao.TodoItemDao;
import com.wonders.task.todoItem.model.bo.TodoItem;

/** 
 * @ClassName: TodoItemDaoImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-1-22 下午03:48:53 
 *  
 */

@Repository("todoItemDao")
public class TodoItemDaoImpl implements TodoItemDao{
	private HibernateTemplate hibernateTemplate;
	
	public void saveBatch(List<TodoItem> list){
		this.getHibernateTemplate().saveOrUpdateAll(list);
	}
	
	@SuppressWarnings("unchecked")
	public Object load(String id,Class clazz) {
		Object instance = getHibernateTemplate().get(clazz, id);
		return instance;
	}
	
	public int deleteBatch(){
		int count = 0;
		String hql = "delete from TodoItem t where t.removed = 0 and t.app = 'schedule'";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		count =  query.executeUpdate();
		return count;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	//注入hibernateTemplate
	@Resource(name="hibernateTemplate2")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
}
