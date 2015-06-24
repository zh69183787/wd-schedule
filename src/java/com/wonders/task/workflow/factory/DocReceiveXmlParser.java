/**   
* @Title: ContractReviewXmlParser.java 
* @Package com.wonders.dataExchange.factory 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年4月16日 下午8:28:06 
* @version V1.0   
*/
package com.wonders.task.workflow.factory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.GsonBuilder;
import com.wonders.task.workflow.constants.WorkflowConstants;
import com.wonders.task.workflow.model.common.bo.ApprovedInfoBo;
import com.wonders.task.workflow.model.common.bo.AttachFile;
import com.wonders.task.workflow.model.common.bo.DwWorkflowInterface;
import com.wonders.task.workflow.model.common.xml.RegisterDataVo;
import com.wonders.task.workflow.model.flow.bo.RecvMainBo;
import com.wonders.task.workflow.model.flow.vo.RecvMainVo;
import com.wonders.task.workflow.model.flow.xml.RecvMainXml;
import com.wonders.task.workflow.model.vo.MessageBo;
import com.wonders.task.workflow.model.vo.ResultInfo;
import com.wonders.task.workflow.service.WorkflowService;
import com.wonders.task.workflow.util.DateUtil;
import com.wonders.task.workflow.util.FlowUtil;
import com.wonders.task.workflow.util.PWSUtil;
import com.wonders.task.workflow.util.WorkflowUtil;

/** 
 * @ClassName: DocReceiveXmlParser 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年4月16日 下午8:28:06 
 *  
 */
@Transactional(value = "txManager2", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service("docReceiveXmlParser")
public class DocReceiveXmlParser implements XmlParser{
	
	private static final String pname = WorkflowConstants.WORKFLOW_RECEIVE_RECV;
	private static final String typeId = "120404";
	private final static String STEPNAME_REGISTER="Begin";
	private static final String STEPNAME_GET_SERIALNO="文件审核及编号";
	private ResultInfo result = null;
	private WorkflowService workflowService;
	
	private RecvMainVo mainVo = null;
	private RegisterDataVo registerVo = null;
	private RecvMainBo mainBo = null;
	private DwWorkflowInterface bo = null;
	/** 
	* @Title: parse 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param params
	* @param @param content    设定文件 
	* @throws 
	*/
	
	public void autoLaunch(DwWorkflowInterface temp){
		bo = temp;
		result = new ResultInfo();
		mainVo = new RecvMainVo();
		registerVo = new RegisterDataVo();
		mainBo = new RecvMainBo();
		
		if(parse()){
			if(register() && addSuggest()){
				updateStatus();
			}
		};
		
	}
	//解析xml
	public boolean parse() {
		boolean flag = false;
		try{
			String content = bo.getContent();
			RecvMainXml xml = (RecvMainXml) WorkflowUtil.parseXml(content,RecvMainXml.class);
			mainVo = xml.getMainVo();
			registerVo = xml.getRegisterVo();
		    List<AttachFile> attachList = xml.getAttachVo().getAttachList();
		    String groupId = mainVo.getAttach();
		    
		    if(groupId == null || groupId.length() == 0){
		    	groupId = this.workflowService.uploadHttpFiles(attachList);
		    	mainVo.setAttach(groupId);
		    	bo.setContent(WorkflowUtil.rebuildXml(content,groupId));
		    	//System.out.println(bo.getContent());
				this.workflowService.update(bo);
				flag = true;
		    }	    
		}catch(Exception e){
			e.printStackTrace();
			result.addErrors(new MessageBo("XML转换失败！"));
			updateStatus();
		}
		
		return flag;
	}
	
	//保存业务信息
	private boolean register(){
		boolean flag = false;
		try {
			BeanUtils.copyProperties(mainBo, mainVo);
			mainBo.setOperator(registerVo.getLoginName()==null? "" : registerVo.getLoginName().replace("ST/", ""));
			mainBo.setOrdinaryDep(registerVo.getDeptName());
			
			int i = launch();
			if(i > 0){
				mainBo.setModleId(pname);
				mainBo.setInstanceId(i+"");
				mainBo.setExternalLaunch("1");
				this.workflowService.save(mainBo);
				flag = true;
			}
		}catch(Exception e){
			e.printStackTrace();
			result.addErrors(new MessageBo("流程发起保留失败！"));
			updateStatus();
		}
		return flag;
	}
	
	
	private boolean addSuggest(){
		boolean flag = false;
		try {
			ApprovedInfoBo approve = new ApprovedInfoBo();
			approve.setDept(registerVo.getDeptName());
			approve.setDeptId(registerVo.getDeptId());
			approve.setStepname(STEPNAME_REGISTER);
			approve.setUsername(registerVo.getLoginName());
			approve.setUserfullname(registerVo.getUserName());	
			approve.setIncidentno(Long.parseLong(mainBo.getInstanceId()));
			approve.setProcess(mainBo.getModleId());
			approve.setUpddate(new Date());
			approve.setRemark("");
			approve.setStatus(2L);
			this.workflowService.save(approve);
			flag = true;
		}catch(Exception e){
			e.printStackTrace();
			result.addErrors(new MessageBo("意见保留失败！"));
			updateStatus();
		}
		return flag;
	}
	
	//发起流程 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private int launch(){
		String summary = pname+" "+mainBo.getSwId()+" "+mainBo.getTitle();
		Map<String,Object> nodeMap = 
				FlowUtil.getNodeUsersByConfig(pname, STEPNAME_GET_SERIALNO, typeId);
		Map map = new HashMap();
		map.put("最终领导秘书账号", nodeMap.get("username"));
		map.put("业务表单ID1", typeId);
		map.put("业务表ID", mainBo.getId());
		map.put("最终领导账号", registerVo.getLoginName());
		map.put("发起人部门ID", registerVo.getDeptId());
		map.put("最终领导姓名", registerVo.getUserName());
		map.put("短信内容", summary);
		
		int incidentNo = PWSUtil.launchIncident(pname, registerVo.getLoginName(), summary, map);
		return incidentNo;
	}
	
	
	//更新状态
	private void updateStatus(){
		try{
			DwWorkflowInterface dwBo = (DwWorkflowInterface) this.workflowService.load(bo.getId(), DwWorkflowInterface.class);
			dwBo.setLogInfo(
					new GsonBuilder().serializeNulls().disableHtmlEscaping().
					excludeFieldsWithoutExposeAnnotation().create()
					.toJson(result.getErrors()));
			dwBo.setStatus("1");
			dwBo.setOperateTime(DateUtil.getCurrDate(DateUtil.TIME_FORMAT));
			this.workflowService.update(dwBo);
		}catch(Exception e){
			e.printStackTrace();
			result.addErrors(new MessageBo("更新状态失败"));
		}
	}
	
	public WorkflowService getWorkflowService() {
		return workflowService;
	}
	
	@Autowired(required=false)
	public void setWorkflowService(@Qualifier("workflowService")WorkflowService workflowService) {
		this.workflowService = workflowService;
	}
	
	
	


}
