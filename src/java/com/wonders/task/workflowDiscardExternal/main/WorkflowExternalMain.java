/**   
* @Title: WorkflowExternalMain.java 
* @Package com.wonders.task.workflowDiscardExternal.main 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年7月1日 下午3:16:45 
* @version V1.0   
*/
package com.wonders.task.workflowDiscardExternal.main;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.schedule.util.SpringBeanUtil;
import com.wonders.service.util.ServiceClient;
import com.wonders.task.sample.ITaskService;
import com.wonders.task.workflowDiscardExternal.model.bo.DiscardBo;
import com.wonders.task.workflowDiscardExternal.model.vo.ApproveVo;
import com.wonders.task.workflowDiscardExternal.model.vo.ApproveVoList;
import com.wonders.task.workflowDiscardExternal.model.vo.ProcessInfoVo;
import com.wonders.task.workflowDiscardExternal.model.vo.RegisterVo;
import com.wonders.task.workflowDiscardExternal.model.vo.DiscardVo;
import com.wonders.task.workflowDiscardExternal.model.xml.DiscardInfoXml;
import com.wonders.task.workflowDiscardExternal.service.ExternalNewService;
import com.wonders.task.workflowDiscardExternal.service.ExternalOldService;

/** 
 * @ClassName: WorkflowExternalMain 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年7月1日 下午3:16:45 
 *  
 */

@Transactional(value = "txManager2",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service("workflowExternalDiscardMain")
public class WorkflowExternalMain implements ITaskService{
	@Autowired
	private ExternalOldService oldService;
	@Autowired
	private ExternalNewService newService;
	/** 
	* @Title: exec 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param param
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public String exec(String param) {	
		List<ProcessInfoVo> list = oldService.getInfo();
		String xmlStr = null;
		for(ProcessInfoVo vo : list){
			DiscardInfoXml xml = new DiscardInfoXml();
			DiscardVo a = this.oldService.getMainBo(vo.getProcess(), vo.getIncident());
			RegisterVo b = this.newService.getInfo(vo.getLoginName());
			if(b!=null){
				b.setProcessName(vo.getProcess());b.setIncidentNo(vo.getIncident());
				b.setStepName(vo.getStepName());
			}
			List<ApproveVo> c = this.oldService.getApproveInfo(vo.getProcess(), vo.getIncident());
			if(a!=null && b!=null && c!=null && c.size() > 0){
				xml.registerVo = b;
				xml.mainBo = a;
				ApproveVoList d = new ApproveVoList();
				d.list = c;
				xml.approveVo = d;
				
				service(xml);
			}
		}
		return "";
	}
	
	public void service(DiscardInfoXml xml){
		String xmlStr = getResult(xml,"eamProjectDiscard");
		ServiceClient.setDataInfo("eam", "eam2013!", xmlStr);
	}
	
	public String getResult(DiscardInfoXml xml,String type){
		String toDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String result = "";
		try {
		JAXBContext context = JAXBContext.newInstance(xml.getClass());
		Marshaller m;
		
			m = context.createMarshaller();
		
		
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); 
		m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8"); 
		StringWriter sw = new StringWriter();
		m.marshal(xml, sw);
		result = sw.toString().replace("<root>", "<root type=\""+type+"\" date=\""+toDate+"\">");
		System.out.println(result);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static void main(String[] args){
		ApplicationContext applicationContext = null;  
		//String[] fileUrl = new String[]{"classpath*:*Context*.xml"};  
		try {
			applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		ITaskService task = (ITaskService) SpringBeanUtil.getBean("workflowExternalDiscardMain");
		task.exec("5");
		
	}
	
}
