/**   
* @Title: WorkflowMain.java 
* @Package com.wonders.task.workflow.main 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年5月5日 下午2:23:51 
* @version V1.0   
*/
package com.wonders.task.workflow.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.schedule.util.SpringBeanUtil;
import com.wonders.task.sample.ITaskService;
import com.wonders.task.workflow.constants.WorkflowConstants;
import com.wonders.task.workflow.factory.XmlParser;
import com.wonders.task.workflow.model.common.bo.DwWorkflowInterface;
import com.wonders.task.workflow.model.vo.ResultInfo;
import com.wonders.task.workflow.service.WorkflowService;

/** 
 * @ClassName: WorkflowMain 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年5月5日 下午2:23:51 
 *  
 */


@Transactional(value = "txManager2",propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
@Service("workflowMain")
public class WorkflowMain implements ITaskService{

	private WorkflowService service ; 
	
	public WorkflowService getService() {
		return service;
	}
	@Autowired(required=false)
	public void setService(@Qualifier("workflowService")WorkflowService service) {
		this.service = service;
	}

	/** 
	* @Title: exec 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param param
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public String exec(String param) {
		// TODO Auto-generated method stub
		String type = "";
		XmlParser parse = null;
		String hql = "from DwWorkflowInterface t where t.removed='0' and t.status=0 and t.autoLaunch='1'";
		List<DwWorkflowInterface> list = this.service.findWorkflows(hql);
		if(list != null && list.size() > 0){
			for(DwWorkflowInterface bo : list){
				type = WorkflowConstants.getType(bo.getType());
				parse = (XmlParser) SpringBeanUtil.getBean(type+"XmlParser");
				try {
					parse.autoLaunch(bo);
						//System.out.println(isSuccess);
					}catch(Exception e){
						e.printStackTrace();
					}
				
			}
		}
		
		
		return "";
	}
	
	
	public static void main(String[] args){
		ApplicationContext applicationContext = null;  
		String[] fileUrl = new String[]{"classpath*:*Context*.xml"};  
		applicationContext = new ClassPathXmlApplicationContext(fileUrl);  
		ITaskService task = (ITaskService) SpringBeanUtil.getBean("workflowMain");
		task.exec("5");
		
	}

}
