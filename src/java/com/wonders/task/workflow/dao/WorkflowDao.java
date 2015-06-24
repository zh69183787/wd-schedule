/**   
* @Title: WorkflowDao.java 
* @Package com.wonders.task.workflow.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年5月5日 下午12:49:16 
* @version V1.0   
*/
package com.wonders.task.workflow.dao;

import java.util.List;

import com.wonders.task.workflow.model.common.bo.AttachFile;


/** 
 * @ClassName: WorkflowDao 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年5月5日 下午12:49:16 
 *  
 */
public interface WorkflowDao {
	public Object load(String id,Class clazz);
	public void update(Object obj);
	public String uploadHttpFiles(List<AttachFile> attachList);
	public List findWorkflows(String hql);
	public void save(Object obj);
	public int findCountByHql(String hql,Object[] o);
}
