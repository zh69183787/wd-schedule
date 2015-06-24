/**   
* @Title: ContractReviewXmlParser.java 
* @Package com.wonders.dataExchange.factory 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年4月16日 下午8:28:06 
* @version V1.0   
*/
package com.wonders.task.workflow.factory;

import java.sql.Types;
import java.util.ArrayList;
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
import com.wonders.schedule.util.DbUtil;
import com.wonders.schedule.util.StringUtil;
import com.wonders.task.workflow.constants.WorkflowConstants;
import com.wonders.task.workflow.model.common.bo.ApprovedInfoBo;
import com.wonders.task.workflow.model.common.bo.AttachFile;
import com.wonders.task.workflow.model.common.bo.DwWorkflowInterface;
import com.wonders.task.workflow.model.common.xml.OptionDataVo;
import com.wonders.task.workflow.model.common.xml.RegisterDataVo;
import com.wonders.task.workflow.model.flow.bo.DiscardAssetBo;
import com.wonders.task.workflow.model.flow.bo.DiscardMainBo;
import com.wonders.task.workflow.model.flow.bo.ReviewMainBo;
import com.wonders.task.workflow.model.flow.vo.DiscardMainVo;
import com.wonders.task.workflow.model.flow.xml.DiscardMainXml;
import com.wonders.task.workflow.model.vo.MessageBo;
import com.wonders.task.workflow.model.vo.ResultInfo;
import com.wonders.task.workflow.service.WorkflowService;
import com.wonders.task.workflow.strategy.AttachCount;
import com.wonders.task.workflow.strategy.BasicInfo;
import com.wonders.task.workflow.strategy.BudgetEstimate;
import com.wonders.task.workflow.strategy.ExecuteRisk;
import com.wonders.task.workflow.strategy.KPIContext;
import com.wonders.task.workflow.util.DateUtil;
import com.wonders.task.workflow.util.FlowUtil;
import com.wonders.task.workflow.util.PWSUtil;
import com.wonders.task.workflow.util.WorkflowUtil;
/** 
 * @ClassName: ContractReviewXmlParser 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年4月16日 下午8:28:06 
 *  
 */
@Transactional(value = "txManager2", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service("projectDiscardXmlParser")
public class ProjectDiscardXmlParser implements XmlParser{
	
	private static final String pname = WorkflowConstants.WORKFLOW_PROJECT_DISCARD;
	private static final String typeId = "141008";
	private final static String STEPNAME_PRE_TRIAL="投资部收发员";
	//提交
	private static final String CHOICE_MODIFY_SUBMIT_DEPT_LEADER = "1";
	
	private ResultInfo result = null;
	private WorkflowService workflowService;
	
	private DwWorkflowInterface bo = null;
	private DiscardMainVo mainVo = null;
	private	RegisterDataVo registerVo = null;
	private DiscardMainBo mainBo = null;
	private OptionDataVo optionVo = null;
	private DiscardMainXml xml =null;
	
	@Override
	public void autoLaunch(DwWorkflowInterface temp){
		bo = temp;
		result = new ResultInfo();
		mainVo = new DiscardMainVo();
		registerVo = new RegisterDataVo();
		mainBo = new DiscardMainBo();
		optionVo = new OptionDataVo();
		
		if(parse()){
			if("Begin".equals(bo.getStep()) && !existsRegister()){
				if(initData() && register() && addSuggest()){
					updateStatus();
				}
			}else if("返回修改".equals(bo.getStep()) && !existsModify() ){
				if(initData() && complete() && addSuggest()){
					updateStatus();
				}
			}
			
		};
	}
	
	private boolean initData(){
		boolean flag = false;
		try{
			List<String> projectIds = DbUtil.getJdbcTemplate("stptdemo").queryForList(
					"select ID from C_PROJECT where PROJECT_NAME = ?", 
					new Object[]{mainVo.getProjectName()}, 
					new int[]{Types.VARCHAR}, String.class);
			if(projectIds != null && projectIds.size() > 0){
				mainVo.setProjectId(projectIds.get(0));
			}else{
				result.addErrors(new MessageBo("未能在项目台帐中找到项目名称（"+mainVo.getProjectName()+"）Begin！"));
				updateStatus();
				return false;
			}
			
			List<DiscardAssetBo> assets = mainVo.getAssets();
			if(assets == null || assets.size() ==0){
				result.addErrors(new MessageBo("没有待审核资产（"+mainVo.getProjectName()+"）Begin！"));
				updateStatus();
				return false;
			}else{
				for(DiscardAssetBo asset : assets){
					List<String> assetRecordIds = DbUtil.getJdbcTemplate("stzc").queryForList(
							"select t1.ID from T_ASSET_RECORD t1,T_ASSET t2 where t1.ASSET_ID = t2.ID and t1.PROJECT_APP_NO = ? and t2.ASSET_CODE = ?", 
							new Object[]{mainVo.getDispatchNo(),asset.getAssetNo()}, 
							new int[]{Types.VARCHAR,Types.VARCHAR}, String.class);
					if(assetRecordIds != null && assetRecordIds.size() > 0){
						asset.setAssetRecordId(assetRecordIds.get(0));
					}else{
						result.addErrors(new MessageBo("未能在资产履历表找到立项批文号为（"+mainVo.getDispatchNo()+"）资产编号为（"+asset.getAssetNo()+"）的资产履历Begin！"));
						updateStatus();
						return false;
					}					
				}
			}
			
			flag = true;			
		}catch(Exception e){
			result.addErrors(new MessageBo("查找项目出错！"));
			updateStatus();
		}

		return flag;
	}
	
	/**
	 * 判断是否存在相同自有编号
	 * @return true 
	 */
	private boolean existsRegister(){
		boolean flag = false;
		try{
			String hql = "from DiscardMainBo t where t.removed=0 and t.indexNum = ?";
			int count = this.workflowService.findCountByHql(hql, new Object[]{mainVo.getIndexNum()});
			if(count > 0){
				flag = true;
				result.addErrors(new MessageBo("当前项目已存在销项记录Begin！"));
				updateStatus();
			}else{
			
			}
		}catch(Exception e){
			flag = true;
			result.addErrors(new MessageBo("按indexNum查询销项记录出错 Begin！"));
			updateStatus();
		}
		return flag;
	}
	
	//判断是否存在相同自有编号
	private boolean existsModify(){
		boolean flag = false;
		try{
			String hql = "from DiscardMainBo t where t.removed=0 and t.indexNum = ?";
			int count = this.workflowService.findCountByHql(hql, new Object[]{mainVo.getIndexNum()});
			if(count > 1){
				flag = true;
				result.addErrors(new MessageBo("当前项目已存在Complete！"));
				updateStatus();
			}else{
				
			}
		}catch(Exception e){
			flag = true;
			result.addErrors(new MessageBo("按indexNum查询销项记录出错 Complete！"));
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
			xml = (DiscardMainXml) WorkflowUtil.parseXml(content,DiscardMainXml.class);
			mainVo = xml.getMainVo();
			registerVo = xml.getRegisterVo();
			optionVo = xml.getOptionVo();
			
			mainVo.setAssets(xml.getAssetVo().getAssets());
			
		    List<AttachFile> attachList = xml.getAttachVo().getAttachList();
		    String groupId = mainVo.getAttach();
		    
		    if(groupId == null || groupId.length() == 0){
		    	groupId = this.workflowService.uploadHttpFiles(attachList);
		    	mainVo.setAttach(groupId);
		    	bo.setContent(WorkflowUtil.rebuildXml(content,groupId));
		    	//System.out.println(bo.getContent());
				this.workflowService.update(bo);
		    }
		    flag = true;
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
			mainBo.setOperator(registerVo.getLoginName());
			mainBo.setOperatorName(registerVo.getUserName());
			mainBo.setOperateTime(DateUtil.getNowTime());
			int i = launch();
			if(i > 0){
				mainBo.setModleId(pname);
				mainBo.setInstanceId(i+"");
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
				mainBo = (DiscardMainBo) this.workflowService.load(mainVo.getId(), DiscardMainBo.class);
				BeanUtils.copyProperties(mainBo, mainVo);
				mainBo.setOperator(registerVo.getLoginName());
				mainBo.setOperatorName(registerVo.getUserName());
				mainBo.setOperateTime(DateUtil.getNowTime());
				boolean i = modify();
				if(i){
					if(CHOICE_MODIFY_SUBMIT_DEPT_LEADER.equals(optionVo.getChoice())){					
					}else{
						mainBo.setFlag("3");
						mainBo.setRemoved(1);
						mainBo.setProjectName(mainBo.getProjectName()+"<font style='display:inline;color:red;'>（此流程已终止）</font>");
					}
					DbUtil.getJdbcTemplate("stptinc").update("delete from PCL_PROJECT_DISCARD_ASSET where MAIN_ID = '"+mainBo.getId()+"'");
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
		String summary = mainBo.getDispatchNo()+" "+mainBo.getProjectName();
		Map<String,Object> preMap = 
				FlowUtil.getNodeUsersByConfig(pname, 
						STEPNAME_PRE_TRIAL, 
						typeId);

		Map map = new HashMap();
		map.put("外部系统发起", "1");
		map.put("项目销项.发起人账号", registerVo.getLoginName());
		map.put("项目销项.发起人账号拼接", FlowUtil.getLoginNameLink(registerVo.getLoginName(),registerVo.getDeptId()));
		map.put("项目销项.投资部收发员账号", StringUtil.getNotNullValueString(preMap.get("username")));
		map.put("项目销项.投资部收发员账号拼接", FlowUtil.getLoginNameLink(StringUtil.getNotNullValueString(preMap.get("username")), 
				StringUtil.getNotNullValueString(preMap.get("parent_dept"))));		
		
		int incidentNo = PWSUtil.launchIncident(pname, registerVo.getLoginName(), summary, map);
		return incidentNo;
	}
	
	//提交流程
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean modify(){
		String summary = mainBo.getDispatchNo()+" "+mainBo.getProjectName();
		String choice = optionVo.getChoice();
		Map map = new HashMap();
		map.put("外部系统发起", "1");
		map.put("项目销项.销项是否取消", choice);
		
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
			approve.setIncidentno(Long.parseLong(mainBo.getInstanceId()));
			approve.setProcess(mainBo.getModleId());
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
