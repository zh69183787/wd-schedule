/**   
* @Title: WorkflowDaoImpl.java 
* @Package com.wonders.task.workflow.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年5月5日 下午12:49:04 
* @version V1.0   
*/
package com.wonders.task.workflow.dao.impl;

import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import com.wonders.task.workflow.dao.WorkflowDao;
import com.wonders.task.workflow.model.common.bo.AttachFile;

/** 
 * @ClassName: WorkflowDaoImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年5月5日 下午12:49:04 
 *  
 */

@Repository("workflowDao")
public class WorkflowDaoImpl implements WorkflowDao{
	private HibernateTemplate hibernateTemplate;
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	//注入hibernateTemplate
	@Resource(name="hibernateTemplate2")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object load(String id,Class clazz) {
		Object instance = getHibernateTemplate().get(clazz, id);
		return instance;
	}
	
	public List findWorkflows(String hql){
		return this.getHibernateTemplate().find(hql);
	}
	
	public void update(Object obj) {
		getHibernateTemplate().update(obj);
	}
	
	public void save(Object obj) {
		getHibernateTemplate().save(obj);
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
		String fileGroupCode =	"{"+UUID.randomUUID().toString()+"}";
		if(attachList !=null && attachList.size() > 0){
			for(AttachFile bo : attachList){
				bo.setGroupId(fileGroupCode);
				bo.setAppName("workflow");
			}
			try{
				this.getHibernateTemplate().saveOrUpdateAll(attachList);;	//文件信息保存至数据库
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return fileGroupCode;
	}
	
	public int findCountByHql(String hql,Object[] o){
		return this.getHibernateTemplate().find(hql, o).size();
	}
}
