/**   
* @Title: WorkflowServiceImpl.java 
* @Package com.wonders.task.workflow.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年5月5日 下午12:50:33 
* @version V1.0   
*/
package com.wonders.task.workflow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.task.workflow.dao.WorkflowDao;
import com.wonders.task.workflow.model.common.bo.AttachFile;
import com.wonders.task.workflow.service.WorkflowService;

/** 
 * @ClassName: WorkflowServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年5月5日 下午12:50:33 
 *  
 */

@Transactional(value = "txManager2", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service("workflowService")
public class WorkflowServiceImpl implements WorkflowService{
	private WorkflowDao dao;

	public WorkflowDao getDao() {
		return dao;
	}

	@Autowired(required=false)
	public void setDao(@Qualifier("workflowDao")WorkflowDao dao) {
		this.dao = dao;
	}

	public List findWorkflows(String hql){
		return this.dao.findWorkflows(hql);
	}
	/** 
	* @Title: load 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param id
	* @param @param clazz
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public Object load(String id, Class clazz) {
		return this.dao.load(id, clazz);
	}

	/** 
	* @Title: update 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param obj    设定文件 
	* @throws 
	*/
	@Override
	public void update(Object obj) {
		this.dao.update(obj);
	}

	/** 
	* @Title: uploadHttpFiles 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param attachList
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public String uploadHttpFiles(List<AttachFile> attachList) {
		return this.dao.uploadHttpFiles(attachList);
	}
	
	@Override
	public void save(Object obj){
		this.dao.save(obj);
	}
	
	
	public int findCountByHql(String hql,Object[] o){
		return this.dao.findCountByHql(hql, o);
	}
}
