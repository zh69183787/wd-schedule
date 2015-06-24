/**   
* @Title: WorkflowExternalMain.java 
* @Package com.wonders.task.workflowExternal.main 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年7月1日 下午3:16:45 
* @version V1.0   
*/
package com.wonders.task.workflowExternal.main;

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
import com.wonders.task.workflowExternal.model.bo.ReviewBo;
import com.wonders.task.workflowExternal.model.vo.ApproveVo;
import com.wonders.task.workflowExternal.model.vo.ApproveVoList;
import com.wonders.task.workflowExternal.model.vo.ProcessInfoVo;
import com.wonders.task.workflowExternal.model.vo.RegisterVo;
import com.wonders.task.workflowExternal.model.vo.ReviewVo;
import com.wonders.task.workflowExternal.model.xml.ReviewInfoXml;
import com.wonders.task.workflowExternal.service.ExternalNewService;
import com.wonders.task.workflowExternal.service.ExternalOldService;

/** 
 * @ClassName: WorkflowExternalMain 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年7月1日 下午3:16:45 
 *  
 */

@Transactional(value = "txManager2",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service("workflowExternalMain")
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
			ReviewInfoXml xml = new ReviewInfoXml();
			ReviewVo a = this.oldService.getMainBo(vo.getProcess(), vo.getIncident());
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
				
				service(xml,a.getContractType1());
			}
		}
		return "";
	}
	
	public void service(ReviewInfoXml xml,String type){
		String xmlStr = null;
		if("建设类".equals(type)){
			xmlStr = getResult(xml,"greataContractReview");
			ServiceClient.setDataInfo("greata", "greata2013!", xmlStr);
		}else if("运维类".equals(type)){
			xmlStr = getResult(xml,"eamContractReview");
			ServiceClient.setDataInfo("eam", "eam2013!", xmlStr);
		}
	}
	
	public String getResult(ReviewInfoXml xml,String type){
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
		String[] fileUrl = new String[]{"classpath*:*Context*.xml"};  
		applicationContext = new ClassPathXmlApplicationContext(fileUrl);  
		ITaskService task = (ITaskService) SpringBeanUtil.getBean("workflowExternalMain");
		task.exec("5");
		
	}
	
}
