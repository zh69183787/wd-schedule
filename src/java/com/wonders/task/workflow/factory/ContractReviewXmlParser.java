/**   
* @Title: ContractReviewXmlParser.java 
* @Package com.wonders.dataExchange.factory 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年4月16日 下午8:28:06 
* @version V1.0   
*/
package com.wonders.task.workflow.factory;

import com.google.gson.GsonBuilder;
import com.wonders.schedule.util.StringUtil;
import com.wonders.task.workflow.constants.WorkflowConstants;
import com.wonders.task.workflow.model.common.bo.ApprovedInfoBo;
import com.wonders.task.workflow.model.common.bo.AttachFile;
import com.wonders.task.workflow.model.common.bo.DwWorkflowInterface;
import com.wonders.task.workflow.model.common.xml.OptionDataVo;
import com.wonders.task.workflow.model.common.xml.RegisterDataVo;
import com.wonders.task.workflow.model.flow.bo.ReviewMainBo;
import com.wonders.task.workflow.model.flow.vo.ReviewMainVo;
import com.wonders.task.workflow.model.flow.xml.ReviewMainXml;
import com.wonders.task.workflow.model.vo.MessageBo;
import com.wonders.task.workflow.model.vo.ResultInfo;
import com.wonders.task.workflow.service.WorkflowService;
import com.wonders.task.workflow.strategy.*;
import com.wonders.task.workflow.util.DateUtil;
import com.wonders.task.workflow.util.FlowUtil;
import com.wonders.task.workflow.util.PWSUtil;
import com.wonders.task.workflow.util.WorkflowUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
/** 
 * @ClassName: ContractReviewXmlParser 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年4月16日 下午8:28:06 
 *  
 */
@Transactional(value = "txManager2", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service("contractReviewXmlParser")
public class ContractReviewXmlParser implements XmlParser{
	
	private static final String pname = WorkflowConstants.WORKFLOW_CONTRACT_REVIEW;
	private static final String typeId = "120501";
	private final static String STEPNAME_PRE_TRIAL="合约部初审";
	private final static String STEPNAME_CONTRACT_SIMULATE="合约部拟办";
	private final static String STEPNAME_CONTRACT_FINISH="合约部办结";
	//提交
	private static final String CHOICE_MODIFY_SUBMIT_DEPT_LEADER = "1";
	
	private ResultInfo result = null;
	private WorkflowService workflowService;
	
	private DwWorkflowInterface bo = null;
	private ReviewMainVo mainVo = null;
	private	RegisterDataVo registerVo = null;
	private ReviewMainBo mainBo = null;
	private OptionDataVo optionVo = null;
	private KPIContext context = null;
	private ReviewMainXml xml =null;
	private List<String> kpiList = null;
	private StringBuilder sb = null; 
	
	@Override
	public void autoLaunch(DwWorkflowInterface temp){
		bo = temp;
		result = new ResultInfo();
		mainVo = new ReviewMainVo();
		registerVo = new RegisterDataVo();
		mainBo = new ReviewMainBo();
		optionVo = new OptionDataVo();
		
		if(parse()){
			if("Begin".equals(bo.getStep()) && !existsRegister()){
				
				if(kpiConstruct() && register() && addSuggest()){
					updateStatus();
				}
			}else if("返回修改".equals(bo.getStep()) && !existsModify() ){
				if(kpiConstruct() && complete() && addSuggest()){
					updateStatus();
				}
			}
			
		};
	}
	
	private boolean kpiConstruct(){
		boolean flag = false;
		try{
			kpiList = new ArrayList<String>();
			sb = new StringBuilder();
			context = new KPIContext(new BudgetEstimate());
			if(context.operate(xml)){
				kpiList.add("#1#");
			}
			context = new KPIContext(new ExecuteRisk());
			if(context.operate(xml)){
				kpiList.add("#2#");
			}
			context = new KPIContext(new AttachCount());
			if(context.operate(xml)){
				kpiList.add("#3#");
			}
			context = new KPIContext(new BasicInfo());
			if(context.operate(xml)){
				kpiList.add("#4#");
			}
			
			if (kpiList != null && kpiList.size() > 0) {
				for (int i = 0; i < kpiList.size(); i++) {
		 			if (i < kpiList.size() - 1) {
		 				sb.append(kpiList.get(i) + ",");
		 			} else {
		 				sb.append(kpiList.get(i));  
		 			}
			 	}
			 }else{
				 sb.append("#0#");
			 }
			flag = true;
		}catch(Exception e){
			result.addErrors(new MessageBo("KPI计算错误！"));
			updateStatus();
		}

		return flag;
	}
	
	
	//判断是否存在相同自有编号
	private boolean existsRegister(){
		boolean flag = false;
		try{
			String hql = "from ReviewMainBo t where t.removed=0 and t.contractSelfNum = ?";
			int count = this.workflowService.findCountByHql(hql, new Object[]{mainVo.getContractSelfNum()});
			if(count > 0){
				flag = true;
				result.addErrors(new MessageBo("当前自有编号已存在Begin！"));
				updateStatus();
			}else{
			
			}
		}catch(Exception e){
			flag = true;
			result.addErrors(new MessageBo("自有编号查询逻辑出错 Begin！"));
			updateStatus();
		}
		return flag;
	}
	
	//判断是否存在相同自有编号
	private boolean existsModify(){
		boolean flag = false;
		try{
			String hql = "from ReviewMainBo t where t.removed=0 and t.contractSelfNum = ?";
			int count = this.workflowService.findCountByHql(hql, new Object[]{mainVo.getContractSelfNum()});
			if(count > 1){
				flag = true;
				result.addErrors(new MessageBo("当前自有编号已存在Complete！"));
				updateStatus();
			}else{
				
			}
		}catch(Exception e){
			flag = true;
			result.addErrors(new MessageBo("自有编号查询逻辑出错 Complete！"));
			updateStatus();
		}
		return flag;
	}
	
	/** 
	* @Title: parse 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param params
	* @param @param content    设定文件 
	* @throws 
	*/
	
	//解析xml
	private boolean parse() {
		boolean flag = false;
		try{
			String content = bo.getContent();
			xml = (ReviewMainXml) WorkflowUtil.parseXml(content,ReviewMainXml.class);
			mainVo = xml.getMainVo();
			registerVo = xml.getRegisterVo();
			optionVo = xml.getOptionVo();
			
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
			mainBo.setRegLoginName(registerVo.getLoginName());
			mainBo.setRegPerson(registerVo.getUserName());
			mainBo.setTypeId(typeId);
			mainBo.setRegTime(DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
			mainBo.setContractType(mainVo.getContractType1()+"-"+mainVo.getContractType2());
			mainBo.setKpiControl(sb.toString());
			int i = launch();
			if(i > 0){
				mainBo.setProcess(pname);
				mainBo.setIncident(i+"");
				mainBo.setExternalLaunch("1");
				this.workflowService.save(mainBo);
				flag = true;
			}else{
				throw new RuntimeException("ultimus流程launch失败！");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.addErrors(new MessageBo("流程发起保留失败！"));
			updateStatus();
		}
		return flag;
	}
	
	//完成业务信息
	private boolean complete(){
		boolean flag = false;
		if(mainVo.getId() != null && mainVo.getId().length() > 0){
			try {
				mainBo = (ReviewMainBo) this.workflowService.load(mainVo.getId(), ReviewMainBo.class);
                org.springframework.beans.BeanUtils.copyProperties(mainVo,mainBo);
				//BeanUtils.copyProperties(mainBo, mainVo);
				mainBo.setRegLoginName(registerVo.getLoginName());
				mainBo.setRegPerson(registerVo.getUserName());
				mainBo.setTypeId(typeId);
				mainBo.setOperateTime(DateUtil.getNowTime());
				mainBo.setContractType(mainVo.getContractType1()+"-"+mainVo.getContractType2());
				mainBo.setKpiControl(sb.toString());
				boolean i = modify();
				if(i){
					if(CHOICE_MODIFY_SUBMIT_DEPT_LEADER.equals(optionVo.getChoice())){					
					}else{
						mainBo.setFlag("3");
						mainBo.setRemoved(1);
						mainBo.setContractName(mainBo.getContractName()+"<font style='display:inline;color:red;'>（此流程已终止）</font>");
					}
					this.workflowService.update(mainBo);
					flag = true;
				}else{
					throw new RuntimeException("XXXXXXXXXXXXXXXXX！");
				}
			}catch(Exception e){
				e.printStackTrace();
				result.addErrors(new MessageBo("流程goon失败！"));
				updateStatus();
			}
		}else{
			result.addErrors(new MessageBo("流程信息错误！"));
			updateStatus();
		}
		return flag;
	}
	
	//发起流程 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private int launch(){
		String summary = mainBo.getContractIdentifier()+" "+mainBo.getContractName();
		Map<String,Object> preMap = 
				FlowUtil.getNodeUsersByConfig(pname, 
						STEPNAME_PRE_TRIAL, 
						typeId);
		Map<String,Object> simulateMap = 
				FlowUtil.getNodeUsersByConfig(pname, 
						STEPNAME_CONTRACT_SIMULATE, 
						typeId);
		Map<String,Object> finishMap = 
				FlowUtil.getNodeUsersByConfig(pname, 
						STEPNAME_CONTRACT_FINISH, 
						typeId);
		Map map = new HashMap();
		//String deptLeader = StringUtil.getNotNullValueString(vo.getDeptLeader());
		String deptLeader = "ST/G010060013462925";
		map.put("外部系统发起", "1");
		map.put("后审发起人账号", registerVo.getLoginName());
		map.put("后审发起人账号拼接", FlowUtil.getLoginNameLink(registerVo.getLoginName(),registerVo.getDeptId()));
		map.put("后审申报部门领导账号", deptLeader);
		map.put("后审申报部门领导账号拼接", FlowUtil.getLoginNameLink(deptLeader,registerVo.getDeptId()));
		map.put("后审合约部初审账号", StringUtil.getNotNullValueString(preMap.get("username")));
		map.put("后审合约部初审账号拼接", FlowUtil.getLoginNameLink(StringUtil.getNotNullValueString(preMap.get("username")), 
				StringUtil.getNotNullValueString(preMap.get("parent_dept"))));
		map.put("后审合约部拟办账号", StringUtil.getNotNullValueString(simulateMap.get("username")));
		map.put("后审合约部拟办账号拼接", FlowUtil.getLoginNameLink(StringUtil.getNotNullValueString(simulateMap.get("username")), 
				StringUtil.getNotNullValueString(simulateMap.get("parent_dept"))));
		map.put("后审合约部办结账号", StringUtil.getNotNullValueString(finishMap.get("username")));
		map.put("后审合约部办结账号拼接", FlowUtil.getLoginNameLink(StringUtil.getNotNullValueString(finishMap.get("username")), 
				StringUtil.getNotNullValueString(finishMap.get("parent_dept"))));
		
		
		int incidentNo = PWSUtil.launchIncident(pname, registerVo.getLoginName(), summary, map);
		return incidentNo;
	}
	
	//提交流程
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean modify(){
		String summary = mainBo.getContractIdentifier()+" "+mainBo.getContractName();
		String choice = optionVo.getChoice();
		Map map = new HashMap();
		map.put("外部系统发起", "1");
		if(CHOICE_MODIFY_SUBMIT_DEPT_LEADER.equals(choice)){
			map.put("后审是否取消", "0");
		}else{
			map.put("后审是否取消", "1");
		}
		boolean flag = PWSUtil.completeStepTest(registerVo.getProcessName(), registerVo.getLoginName(),
				Integer.parseInt(registerVo.getIncidentNo()), registerVo.getStepName(), summary, "", map);
		return flag;
	}
	
	
	private boolean addSuggest(){
		boolean flag = false;
		try {
			ApprovedInfoBo approve = new ApprovedInfoBo();
			approve.setDept(registerVo.getDeptName());
			approve.setDeptId(registerVo.getDeptId());
			approve.setStepname(registerVo.getStepName());
			approve.setUsername(registerVo.getLoginName());
			approve.setUserfullname(registerVo.getUserName());	
			approve.setIncidentno(Long.parseLong(mainBo.getIncident()));
			approve.setProcess(mainBo.getProcess());
			approve.setUpddate(new Date());
			approve.setRemark(optionVo == null ? "" : optionVo.getRemark());
			if("Begin".equals(registerVo.getStepName())){
				approve.setStatus(2L);
			}else{
				approve.setStatus(1L);
			}
			
			this.workflowService.save(approve);
			flag = true;
		}catch(Exception e){
			e.printStackTrace();
			result.addErrors(new MessageBo("意见保留失败！"));
			updateStatus();
		}
		return flag;
	}
		
	//更新状态
	private void updateStatus(){
		//int a = 0/0;
		try{
			DwWorkflowInterface dwBo = (DwWorkflowInterface) this.workflowService.load(bo.getId(), DwWorkflowInterface.class);
			dwBo.setLogInfo(
					new GsonBuilder().serializeNulls().disableHtmlEscaping().
					excludeFieldsWithoutExposeAnnotation().create()
					.toJson(result.getErrors()));
			if(result.getErrors().size() > 0){
				dwBo.setStatus("2");
			}else{
				dwBo.setStatus("1");
			}
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
