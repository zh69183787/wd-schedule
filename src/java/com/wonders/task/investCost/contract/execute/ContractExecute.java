package com.wonders.task.investCost.contract.execute;

import com.wonders.schedule.common.util.SimpleLogger;
import com.wonders.schedule.util.PropertyUtil;
import com.wonders.schedule.util.SpringBeanUtil;
import com.wonders.task.audit.dbx.execute.DbxExecute;
import com.wonders.task.investCost.contract.model.bo.Attach;
import com.wonders.task.investCost.contract.model.bo.Contract;
import com.wonders.task.investCost.contract.model.bo.HtBa;
import com.wonders.task.investCost.contract.model.bo.HtXx;
import com.wonders.task.investCost.contract.service.*;
import com.wonders.task.investCost.contract.util.HtXxUtil;
import com.wonders.task.sample.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service("contractExecute")
@Scope("prototype")
public class ContractExecute implements ITaskService{
	
	
	static SimpleLogger log = new SimpleLogger(DbxExecute.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private HtXxService htXxService;
	private ScheduleLogService scheduleLogService;
	private IncidentsService incidentsService;
	private ContractService contractService; 
	private AttachService_HT attachService_HT;
	private AttachService_IC attachService_IC;
	private HtBaService htBaService;
	public final static String url = PropertyUtil.getValueByKey("config.properties", "attachpath");
	
	@Autowired(required=false)
	public void setHtBaService(@Qualifier(value="htBaService")HtBaService htBaService) {
		this.htBaService = htBaService;
	}
	@Autowired(required=false)
	public void setHtXxService(@Qualifier(value="htXxService")HtXxService htXxService) {
		this.htXxService = htXxService;
	}
	@Autowired(required=false)
	public void setScheduleLogService(@Qualifier(value="scheduleLogService")ScheduleLogService scheduleLogService) {
		this.scheduleLogService = scheduleLogService;
	}
	@Autowired(required=false)
	public void setIncidentsService(@Qualifier(value="incidentsService")IncidentsService incidentsService) {
		this.incidentsService = incidentsService;
	}
	@Autowired(required=false)
	public void setContractService(@Qualifier(value="contractService")ContractService contractService) {
		this.contractService = contractService;
	}
	@Autowired(required=false)
	public void setAttachService_HT(@Qualifier(value="attachServiceImpl_HT")AttachService_HT attachService_HT) {
		this.attachService_HT = attachService_HT;
	}
	@Autowired(required=false)
	public void setAttachService_IC(@Qualifier(value="attachServiceImpl_IC")AttachService_IC attachService_IC) {
		this.attachService_IC = attachService_IC;
	}
	@Override
	public String exec(String param) {
	//	Date lastExecuteDate = scheduleLogService.findLastExecuteDate("合同审批","end");		//合同审批最后一次执行的时间
//		List<HtXx> htxxList = incidentsService.findIncidentsByDate(lastExecuteDate); 
		List<HtXx> htxxList = htXxService.findByTransferStatusAndFlag("0","1"); 		//未传输过的，预归档的合同
		if(htxxList!=null && htxxList.size()>0){
			HtXxUtil htXxUtil = new HtXxUtil();
			List<Contract> contractList = new ArrayList<Contract>();
			int count = 0;
			List<Attach> attachList = null;
			String attachIds = "";
			try {
				for(int i=0; i<htxxList.size(); i++){
                    attachList = new ArrayList<Attach>();
					HtXx tempHtXx = htxxList.get(i);
					if(tempHtXx.getContractNum()!=null && !tempHtXx.getContractNum().startsWith("维保")
							&& (tempHtXx.getContractNum().startsWith("ST") || tempHtXx.getContractNum().startsWith("技术") 
									|| tempHtXx.getContractNum().startsWith("教培") || tempHtXx.getContractNum().startsWith("资产"))
					){
						//合同附件处理
						if(tempHtXx.getHtAttach()!=null && !"".equals(tempHtXx.getHtAttach())){
							List<Attach> list = attachService_HT.findBYGroupId(tempHtXx.getHtAttach());
							if(list!=null && list.size()>0){
								for(int m=0; m<list.size(); m++){
									Attach attach = list.get(m);
									Attach tempAttach =  new Attach();
									org.springframework.beans.BeanUtils.copyProperties(attach, tempAttach);
									if(attach.getPath()!=null && attach.getPath().indexOf("http")>= 0){
										tempAttach.setPath(attach.getPath());
									}else{
										tempAttach.setPath(url + attach.getId());
									}
									
									tempAttach.setVersion("1");
									tempAttach.setAppname("contractSP");
									tempAttach.setStatus("upload");
									tempAttach.setStatus("eam");
									tempAttach.setRemoved(0L);									
									attachList.add(tempAttach);	
								}
								attachService_IC.saveAll(attachList);	
								for(Attach bb:attachList){
									attachIds += bb.getId() + ",";
								}
								attachIds = (attachIds.length() ==0?"":attachIds.substring(0,attachIds.length()-1));

							}
						}
						//合同处理
						Contract c = htXxUtil.convertHtXxToContract(tempHtXx);
						c.setContractAttachment(attachIds);
						c.setCreateType("transfer");
						c.setContractStatus("0");		//合同状态，已备案
						tempHtXx.setTransferStatus("1");		//变为已传输过的数据
						HtBa htBa = htBaService.findByHtXxId(tempHtXx.getId());
						if(htBa!=null){
							c.setContractPassedDate(htBa.getExaminePassTime());		//合同审批通过时间
							c.setContractSignedDate(htBa.getContractSignTime());	//合同签约时间
							c.setContractStartDate(htBa.getContractSignTime());		//合同开始时间
							Calendar calendar = Calendar.getInstance();
							try {
								
								if(this.isNum(htBa.getPerformTimelimit())){
									calendar.setTime(sdf.parse(htBa.getExaminePassTime()));
									calendar.set(Calendar.DATE, Integer.valueOf(htBa.getPerformTimelimit()));
									c.setContractEndDate(sdf.format(calendar.getTime()));
								}else{
									Date tempDate = sdf.parse(htBa.getPerformTimelimit());
									c.setContractEndDate(htBa.getPerformTimelimit());
								}
							} catch (ParseException e) {
								count++;
							} catch(NumberFormatException e2){
								count++;
							}
						}
						contractList.add(c);
					}
				}
//				attachService_IC.saveAll(attachList);				//这句需要
//				System.out.println("HtBa中“合同履行期限”字段不符合格式的共有"+count+"个");
				htXxService.updateAll(htxxList);
				contractService.saveAll(contractList);
System.out.println("***********合同审批表更新了"+htxxList.size()+"条数据***********");
System.out.println("***********造价合同表更新了"+htxxList.size()+"条数据***********");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "";
	}
	
	public boolean isNum(String str){
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		ApplicationContext applicationContext = null;  
		String[] fileUrl = new String[]{"classpath*:*Context*.xml"};  
		applicationContext = new ClassPathXmlApplicationContext(fileUrl);  
		ITaskService task = (ITaskService) SpringBeanUtil.getBean("contractExecute");
		
		task.exec("");
		
	}
}
