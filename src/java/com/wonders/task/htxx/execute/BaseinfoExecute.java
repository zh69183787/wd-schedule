package com.wonders.task.htxx.execute;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.task.htxx.model.bo.DwContractBaseinfo;
import com.wonders.task.htxx.service.BaseinfoService;
import com.wonders.task.sample.ITaskService;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service("htxxBaseinfoExecute")
@Scope("prototype")
public class BaseinfoExecute implements ITaskService{

	@Autowired(required=false)
	private BaseinfoService baseinfoService;
	
	public void setBaseinfoService(@Qualifier(value="baseinfoService")BaseinfoService baseinfoService) {
		this.baseinfoService = baseinfoService;
	}


	@Override
	public String exec(String param) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String currentMonth = sdf.format(new Date());
		
		DwContractBaseinfo baseinfo = baseinfoService.findByType("2");
		if(baseinfo==null) baseinfo = new DwContractBaseinfo(); 
		String sql1 ="select count(*) from c_project p where p.removed = '0' and p.project_type in ('3','2','4') and p.approval_date like '"+currentMonth+"%'";
		String sql2 ="select sum(p.project_budget_all) from c_project p where p.removed = '0' and p.project_type in ('3','2','4') and p.approval_date like '"+currentMonth+"%'";
		String sql3 ="select count(*) from c_project p where p.removed = '0' and p.project_type in ('3','2','4')";		//项目总数
		String sql4 ="select sum(p.project_budget_all) from c_project p where p.removed = '0' and p.project_type in ('3','2','4')";		//项目总金额
		String sql5 ="select count(*) from c_project p where p.project_type in (2,3,4) and p.removed=0"+ 
			" and (p.dispatch_no is null or  p.approval_date is null "+ 
			" or p.project_moneysource is null or"+ 
			" p.money_source_type  is null or  p.professional_type is null )";			//待补全信息的项目
		
		String sql6 ="select count(*) from c_contract c where c.removed ='0' and c.contract_type in('2,1','2,2','2,3') and c.contract_signed_date like '"+currentMonth+"%'";
		String sql7 ="select sum(c.contract_price) from c_contract c where c.removed ='0' and c.contract_type in('2,1','2,2','2,3') and c.contract_signed_date like '"+currentMonth+"%'";
		String sql8 ="select count(*) from c_contract c where c.removed ='0' and c.contract_type in('2,1','2,2','2,3') ";	//合同总数
		String sql9 ="select sum(c.contract_price) from c_contract c where c.removed ='0' and c.contract_type in('2,1','2,2','2,3')";	//合同总金额
		String sql10 ="select count(*) from c_contract c where c.contract_type in ('2','2,1','2,2','2,3') and c.removed=0"+ 
				" and (c.contract_name is null or c.contract_no is null or c.contract_price is null"+ 
				" or c.contract_owner_name is null or c.contract_owner_execute_name is null or c.build_supplier_name  is null"+
				" or c.pay_type is null or c.contract_passed_date is null or c.contract_start_date is null  or c.contract_signed_date is null"+
				" or c.contract_end_date is null or c.contract_status is null or c.invite_bid_type is null or c.project_name is null)";		//待补全信息的合同
		
		String a1 = baseinfoService.findOneData(sql1);
		String a2 = baseinfoService.findOneData(sql2);
		String a3 = baseinfoService.findOneData(sql3);
		String a4 = baseinfoService.findOneData(sql4);
		String a5 = baseinfoService.findOneData(sql5);
		
		String a6 = baseinfoService.findOneData(sql6);
		String a7 = baseinfoService.findOneData(sql7);
		String a8 = baseinfoService.findOneData(sql8);
		String a9 = baseinfoService.findOneData(sql9);
		String a10 = baseinfoService.findOneData(sql10);
		
		baseinfo.setType("2");		//运维类
		
		baseinfo.setProjectCountCurrentMonth(a1);
		baseinfo.setProjectMoneyCurrentMonth(formatStringData(a2));
		baseinfo.setProjectCountAll(a3);
		baseinfo.setProjectMoneyAll(formatStringData(a4));
		baseinfo.setNeedToCompleteProject(a5);
		
		baseinfo.setContractCountCurrentMonth(a6);
		baseinfo.setContractMoneyCurrentMonth(formatStringData(a7));
		baseinfo.setContractCountAll(a8);
		baseinfo.setContractMoneyAll(formatStringData(a9));
		baseinfo.setNeedToCompleteContract(a10);
		
		this.baseinfoService.save(baseinfo);
		
		//更新合同价、合同数量表DW_CONTRACT_P_PRICE_NUMBER
		updateDwContractPriceNumber();
		
		//写死页面上11个公司的id
		List<String> list = new ArrayList<String>();
		list.add("2541");		//运一公司
		list.add("2542");		//运二公司
		list.add("2543");		//运三公司
		list.add("2544");		//运四公司
		list.add("2540");		//运管中心
		list.add("2546");		//运管中心
		
		list.add("2718");		//车辆公司
		list.add("2719");		//供电公司
		list.add("2720");		//通号公司
		list.add("2721");		//工务公司
		list.add("2722");		//物资公司
		list.add("2545");		//维护保障中心
		
		updateBusinessControlInfo(null);	//总数
		for(int i=0; i<list.size(); i++){
			updateBusinessControlInfo(list.get(i));
		}
		updateBusinessControlInfo("other");	//其他
		updateBusinessControlInfo("center");	//维保中心，运营6家公司的总和
		
		
		return "";
	}
	
	//合同业务管控信息提示
	public void updateBusinessControlInfo(String companyId){
		
		if(companyId!=null && "other".equals(companyId)){	//设置“其他”的值
			//1.立项后合同签订超时提示
			String all1Sql = "select t.value from DW_CONTRACT_P_MANAGEMENT t where t.name ='立项后合同签订超时提示' and t.company_id is null";
			String rest1Sql = "select sum(t.value) from DW_CONTRACT_P_MANAGEMENT t where t.name ='立项后合同签订超时提示' and t.company_id is not null and t.company_id!='other'";
			int other1 = Integer.valueOf(baseinfoService.findOneData(all1Sql))-Integer.valueOf(baseinfoService.findOneData(rest1Sql));
			if(other1<0){
				other1 = 0;
			}
			String other1Sql = "";
			if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name='立项后合同签订超时提示' and t.company_id='other'")){
				other1Sql = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+other1+"',t.create_date=sysdate where t.name='立项后合同签订超时提示' and t.company_id='other'";
			}else{
				other1Sql = "insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'立项后合同签订超时提示',"+other1+",sysdate,1,'#','other')";
			}
			baseinfoService.executeUpdateUpdate(other1Sql);
			//2.项目批复概算超额告警提示,各公司不为0
			String all2Sql = "select t.value from DW_CONTRACT_P_MANAGEMENT t where t.name ='项目批复概算超额告警提示' and t.company_id is null";
			String rest2Sql = "select sum(t.value) from DW_CONTRACT_P_MANAGEMENT t where t.name ='项目批复概算超额告警提示' and t.company_id is not null and t.company_id!='other'";
			int other2 = Integer.valueOf(baseinfoService.findOneData(all2Sql))-Integer.valueOf(baseinfoService.findOneData(rest2Sql));
			if(other2<0){
				other2=0;
			}
			String other2Sql = "";
			if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name='项目批复概算超额告警提示' and t.company_id='other'")){
				other2Sql = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+other2+"',t.create_date=sysdate where t.name='项目批复概算超额告警提示' and t.company_id='other'";
			}else{
				other2Sql = "insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'项目批复概算超额告警提示',"+other2+",sysdate,2,'#','other')";
			}
			baseinfoService.executeUpdateUpdate(other2Sql);
			
			
			//3.采购竞价合同申报待办提示--未实现功能，默认设置为0,各公司为0
			String all3Sql = "select t.value from DW_CONTRACT_P_MANAGEMENT t where t.name ='采购竞价合同申报待办提示' and t.company_id is null";
			String rest3Sql = "select sum(t.value) from DW_CONTRACT_P_MANAGEMENT t where t.name ='采购竞价合同申报待办提示' and t.company_id is not null and t.company_id!='other'";
			int other3 = Integer.valueOf(baseinfoService.findOneData(all3Sql))-Integer.valueOf(baseinfoService.findOneData(rest3Sql));
			if(other3<0){
				other3 = 0;
			}
			String other3Sql = "";
			if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name='采购竞价合同申报待办提示' and t.company_id='other'")){
				other3Sql = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+other3+"',t.create_date=sysdate where t.name='采购竞价合同申报待办提示' and t.company_id='other'";
			}else{
				other3Sql = "insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'采购竞价合同申报待办提示',"+other3+",sysdate,3,'#','other')";
			}
			baseinfoService.executeUpdateUpdate(other3Sql);
			
			//4.合同审批流程超时提示,各公司为0
			String all4Sql = "select t.value from DW_CONTRACT_P_MANAGEMENT t where t.name ='合同审批流程超时提示' and t.company_id is null";
			String rest4Sql = "select sum(t.value) from DW_CONTRACT_P_MANAGEMENT t where t.name ='合同审批流程超时提示' and t.company_id is not null and t.company_id!='other'";
			int other4 = Integer.valueOf(baseinfoService.findOneData(all4Sql))-Integer.valueOf(baseinfoService.findOneData(rest4Sql));
			if(other4<0){
				other4 = 0;
			}
			String other4Sql = "";
			if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name='合同审批流程超时提示' and t.company_id='other'")){
				other4Sql = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+other4+"',t.create_date=sysdate where t.name='合同审批流程超时提示' and t.company_id='other'";
			}else{
				other4Sql = "insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'合同审批流程超时提示',"+other4+",sysdate,4,'#','other')";
			}
			baseinfoService.executeUpdateUpdate(other4Sql);
			
			
			//5.合同先执行后签订风险提示,各公司为0
			String all5Sql = "select t.value from DW_CONTRACT_P_MANAGEMENT t where t.name ='合同先执行后签订风险提示' and t.company_id is null";
			String rest5Sql = "select sum(t.value) from DW_CONTRACT_P_MANAGEMENT t where t.name ='合同先执行后签订风险提示' and t.company_id is not null and t.company_id!='other'";
			int other5 = Integer.valueOf(baseinfoService.findOneData(all5Sql))-Integer.valueOf(baseinfoService.findOneData(rest5Sql));
			if(other5<0){
				other5 = 0;
			}
			String other5Sql = "";
			if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name='合同先执行后签订风险提示' and t.company_id='other'")){
				other5Sql = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+other5+"',t.create_date=sysdate where t.name='合同先执行后签订风险提示' and t.company_id='other'";
			}else{
				other5Sql = "insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'合同先执行后签订风险提示',"+other5+",sysdate,5,'#','other')";
			}
			baseinfoService.executeUpdateUpdate(other5Sql);
			
			//6.合同进展信息风险提示
			String all6Sql = "select t.value from DW_CONTRACT_P_MANAGEMENT t where t.name ='合同进展信息风险提示' and t.company_id is null";
			String rest6Sql = "select sum(t.value) from DW_CONTRACT_P_MANAGEMENT t where t.name ='合同进展信息风险提示' and t.company_id is not null and t.company_id!='other'";
			int other6 = Integer.valueOf(baseinfoService.findOneData(all6Sql))-Integer.valueOf(baseinfoService.findOneData(rest6Sql));
			if(other6<0){
				other6 = 0;
			}
			String other6Sql = "";
			if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name='合同进展信息风险提示' and t.company_id='other'")){
				other6Sql = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+other6+"',t.create_date=sysdate where t.name='合同进展信息风险提示' and t.company_id='other'";
			}else{
				other6Sql = "insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'合同进展信息风险提示',"+other6+",sysdate,6,'#','other')";
			}
			baseinfoService.executeUpdateUpdate(other6Sql);
			
			//7.合同变更超额告警提示
			String all7Sql = "select t.value from DW_CONTRACT_P_MANAGEMENT t where t.name ='合同变更超额告警提示' and t.company_id is null";
			String rest7Sql = "select sum(t.value) from DW_CONTRACT_P_MANAGEMENT t where t.name ='合同变更超额告警提示' and t.company_id is not null and t.company_id!='other'";
			int other7 = Integer.valueOf(baseinfoService.findOneData(all7Sql))-Integer.valueOf(baseinfoService.findOneData(rest7Sql));
			if(other7 < 0){
				other7 = 0;
			}
			String other7Sql = "";
			if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name='合同变更超额告警提示' and t.company_id='other'")){
				other7Sql = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+other7+"',t.create_date=sysdate where t.name='合同变更超额告警提示' and t.company_id='other'";
			}else{
				other7Sql = "insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'合同变更超额告警提示',"+other7+",sysdate,7,'#','other')";
			}
			baseinfoService.executeUpdateUpdate(other7Sql);
			
			//8.合同支付超付告警提示
			String all8Sql = "select t.value from DW_CONTRACT_P_MANAGEMENT t where t.name ='合同支付超付告警提示' and t.company_id is null";
			String rest8Sql = "select sum(t.value) from DW_CONTRACT_P_MANAGEMENT t where t.name ='合同支付超付告警提示' and t.company_id is not null and t.company_id!='other'";
			int other8 = Integer.valueOf(baseinfoService.findOneData(all8Sql))-Integer.valueOf(baseinfoService.findOneData(rest8Sql));
			if(other8 < 0 ){
				other8 = 0;
			}
			String other8Sql = "";
			if(other8<0) other8 = 0;
			if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name='合同支付超付告警提示' and t.company_id='other'")){
				other8Sql = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+other8+"',t.create_date=sysdate where t.name='合同支付超付告警提示' and t.company_id='other'";
			}else{
				other8Sql = "insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'合同支付超付告警提示',"+other8+",sysdate,8,'#','other')";
			}
			baseinfoService.executeUpdateUpdate(other8Sql);
			
			//9.合同销号条件异常提示
			String all9Sql = "select t.value from DW_CONTRACT_P_MANAGEMENT t where t.name ='合同销号条件异常提示' and t.company_id is null";
			String rest9Sql = "select sum(t.value) from DW_CONTRACT_P_MANAGEMENT t where t.name ='合同销号条件异常提示' and t.company_id is not null and t.company_id!='other'";
			int other9 = Integer.valueOf(baseinfoService.findOneData(all9Sql))-Integer.valueOf(baseinfoService.findOneData(rest9Sql));
			if(other9 <0 ){
				other9 = 0;
			}
			String other9Sql = "";
			if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name='合同销号条件异常提示' and t.company_id='other'")){
				other9Sql = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+other9+"',t.create_date=sysdate where t.name='合同销号条件异常提示' and t.company_id='other'";
			}else{
				other9Sql = "insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'合同销号条件异常提示',"+other9+",sysdate,9,'#','other')";
			}
			baseinfoService.executeUpdateUpdate(other9Sql);
			
			//10.合同质保期到期提示---未实现功能,默认设置为0
			String all10Sql = "select t.value from DW_CONTRACT_P_MANAGEMENT t where t.name ='合同质保期到期提示' and t.company_id is null";
			String rest10Sql = "select sum(t.value) from DW_CONTRACT_P_MANAGEMENT t where t.name ='合同质保期到期提示' and t.company_id is not null and t.company_id!='other'";
			int other10 = Integer.valueOf(baseinfoService.findOneData(all10Sql))-Integer.valueOf(baseinfoService.findOneData(rest10Sql));
			if(other10 < 0){
				other10 = 0;
			}
			String other10Sql = "";
			if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name='合同质保期到期提示' and t.company_id='other'")){
				other10Sql = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+other10+"',t.create_date=sysdate where t.name='合同质保期到期提示' and t.company_id='other'";
			}else{
				other10Sql = "insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'合同质保期到期提示',"+other10+",sysdate,10,'#','other')";
			}
			baseinfoService.executeUpdateUpdate(other10Sql);
			
			
			
			//11.待补全项目
			String all11Sql = "select t.value from DW_CONTRACT_P_MANAGEMENT t where t.name ='待补全项目信息' and t.company_id is null";
			String rest11Sql = "select sum(t.value) from DW_CONTRACT_P_MANAGEMENT t where t.name ='待补全项目信息' and t.company_id is not null and t.company_id!='other'";
			int other11 = Integer.valueOf(baseinfoService.findOneData(all11Sql))-Integer.valueOf(baseinfoService.findOneData(rest11Sql));
			if(other11 < 0){
				other11 = 0;
			}
			String other11Sql = "";
			if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name='待补全项目信息' and t.company_id='other'")){
				other11Sql = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+other11+"',t.create_date=sysdate where t.name='待补全项目信息' and t.company_id='other'";
			}else{
				other11Sql = "insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'待补全项目信息',"+other11+",sysdate,11,'#','other')";
			}
			baseinfoService.executeUpdateUpdate(other11Sql);
			
			
			
			
			//12.待补全合同
			String all12Sql = "select t.value from DW_CONTRACT_P_MANAGEMENT t where t.name ='待补全合同信息' and t.company_id is null";
			String rest12Sql = "select sum(t.value) from DW_CONTRACT_P_MANAGEMENT t where t.name ='待补全合同信息' and t.company_id is not null and t.company_id!='other'";
			int other12 = Integer.valueOf(baseinfoService.findOneData(all12Sql))-Integer.valueOf(baseinfoService.findOneData(rest12Sql));
			if(other12 < 0){
				other12 = 0;
			}
			String other12Sql = "";
			if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name='待补全合同信息' and t.company_id='other'")){
				other12Sql = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+other12+"',t.create_date=sysdate where t.name='待补全合同信息' and t.company_id='other'";
			}else{
				other12Sql = "insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'待补全合同信息',"+other12+",sysdate,12,'#','other')";
			}
			baseinfoService.executeUpdateUpdate(other12Sql);
		}else if(companyId!=null && "center".equals(companyId)){		//设置维保中心
			//管控信息
			//查询6家公司的总数,hardcode写死,companyId=2545,2718,2719,2720,2721,2722
			for(int m=1; m<=12; m++){
				String sqlCenter = "select sum(t.value) from DW_CONTRACT_P_MANAGEMENT t where t.order_no = '"+m+"' and t.company_id in ('2545','2718','2719','2720','2721','2722')";
				String resultCenter = baseinfoService.findOneData(sqlCenter);
				String updateCenter ="";
				if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.order_no='"+m+"' and t.company_id='center'")){
					updateCenter = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+resultCenter+"',t.create_date=sysdate where t.order_no='"+m+"' and t.company_id='center'";
				}else{
					String name ="";
					switch (m) {
					case 1:
						name = "立项后合同签订超时提示";
						break;
					case 2:
						name ="项目批复概算超额告警提示";
						break;
					case 3:
						name ="采购竞价合同申报待办提示";
						break;
					case 4:
						name ="合同审批流程超时提示";
						break;
					case 5:
						name ="合同先执行后签订风险提示";
						break;
					case 6:
						name ="合同进展信息风险提示";
						break;
					case 7:
						name ="合同变更超额告警提示";
						break;
					case 8:
						name ="合同支付超付告警提示";
						break;
					case 9:
						name ="合同销号条件异常提示";
						break;
					case 10:
						name ="合同质保期到期提示";
						break;
					case 11:
						name ="待补全项目信息";
						break;
					case 12:
						name ="待补全合同信息";
						break;
					default:
						break;
					}
					updateCenter = "insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'"+name+"',"+resultCenter+",sysdate,"+m+",'#','center')";
				}
				baseinfoService.executeUpdateUpdate(updateCenter);
			}
		}else{
			//1.立项后合同签订超时提示
			String sql1 ="";
			if(companyId!=null && !"".equals(companyId)){
				sql1 = "select count(count(distinct p.id)) from c_project p,c_contract c where p.project_type in (2,3,4) and p.project_adddept_id='"+companyId+"' and p.removed=0"+ 
				" and p.id = c.project_id and c.contract_type in ('2','2,1','2,2','2,3') and c.removed=0 and c.project_id is not null"+
				" and p.approval_date is not null and c.contract_signed_date is not null"+
				" and to_date(p.approval_date,'yyyy-mm-dd') < (to_date(c.contract_signed_date,'yyyy-mm-dd')-120) " +
				" and p.id not in (" +
					"select distinct p.id from c_project p,c_contract c where p.project_type in (2,3,4) and p.removed=0"+ 
					" and p.id = c.project_id and c.contract_type in ('2','2,1','2,2','2,3') and c.removed=0 and c.project_id is not null"+
					" and p.approval_date is not null and c.contract_signed_date is not null"+
					" and to_date(p.approval_date,'yyyy-mm-dd') >= (to_date(c.contract_signed_date,'yyyy-mm-dd')-120)" +
				")"+
				"group by p.id";
			}else{
				sql1 = "select count(count(distinct p.id)) from c_project p,c_contract c where p.project_type in (2,3,4) and p.removed=0"+ 
				" and p.id = c.project_id and c.contract_type in ('2','2,1','2,2','2,3') and c.removed=0 and c.project_id is not null"+
				" and p.approval_date is not null and c.contract_signed_date is not null"+
				" and to_date(p.approval_date,'yyyy-mm-dd') < (to_date(c.contract_signed_date,'yyyy-mm-dd')-120)" +
				" and p.id not in (" +
					"select distinct p.id from c_project p,c_contract c where p.project_type in (2,3,4) and p.removed=0"+ 
					" and p.id = c.project_id and c.contract_type in ('2','2,1','2,2','2,3') and c.removed=0 and c.project_id is not null"+
					" and p.approval_date is not null and c.contract_signed_date is not null"+
					" and to_date(p.approval_date,'yyyy-mm-dd') >= (to_date(c.contract_signed_date,'yyyy-mm-dd')-120)" +
				")"+
				" group by p.id";
			}
			
			String result1 = baseinfoService.findOneData(sql1);
			String sql1update ="";
			
			if(companyId!=null && !"".equals(companyId)){		//有公司编号
				if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name ='立项后合同签订超时提示' and t.COMPANY_ID='"+companyId+"'")){
					sql1update = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+result1+"',t.create_date=sysdate where t.name='立项后合同签订超时提示' and t.COMPANY_ID='"+companyId+"'";
				}else{
					sql1update ="insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'立项后合同签订超时提示',"+result1+",sysdate,1,'#',"+companyId+")";
				}
			}else{
				if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name ='立项后合同签订超时提示' and t.company_id is null")){
					sql1update = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+result1+"',t.create_date=sysdate where t.name='立项后合同签订超时提示' and t.COMPANY_ID is null";
				}else{
					sql1update ="insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'立项后合同签订超时提示',"+result1+",sysdate,1,'#','')";
				}
			}
			baseinfoService.executeUpdateUpdate(sql1update);
			
			
			//2.项目批复概算超额告警提示,各公司不为0
			String sql2 = "";
			if(companyId!=null && !"".equals(companyId)){
				sql2 = "select c.project_id,sum(c.contract_price) from c_contract c where c.removed='0' and c.contract_type in ('2','2,1','2,2','2,3') " +
						"and c.project_id in (select t.id from c_project t where t.project_adddept_id ='"+companyId+"' and t.project_type in (2,3,4) and t.removed =0 and t.id not in (select t.id from c_project t where t.project_type = '4' and t.project_budget_all=0)) " +
						"group by c.project_id";
			}else{
				sql2 = "select c.project_id,sum(c.contract_price) from c_contract c where c.removed='0' and c.contract_type in ('2','2,1','2,2','2,3') " +
						"and c.project_id in (select t.id from c_project t where t.project_type in (2,3,4) and t.removed =0 and t.id not in (select t.id from c_project t where t.project_type = '4' and t.project_budget_all=0)) " +
						"group by c.project_id";
			}
				 
			List<Object[]> resultList = baseinfoService.findBySql(sql2);
			String sql2update ="";
			int result2 = 0 ;
			if (resultList!=null && resultList.size()>0) {
				String sql2_sub = "";
				
				for(Object[] array : resultList){
					sql2_sub = "select * from c_project p where p.id='"+array[0]+"' and to_number(p.project_budget_all)<"+array[1];
					if(baseinfoService.isResultExist(sql2_sub)){
						result2 ++;
					}
				}
			}
			if(companyId!=null && !"".equals(companyId)){
				if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name='项目批复概算超额告警提示' and t.company_id='"+companyId+"'")){
					sql2update = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+result2+"',t.create_date=sysdate where t.name='项目批复概算超额告警提示' and t.company_id='"+companyId+"'";
				}else{
					sql2update = "insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'项目批复概算超额告警提示',"+result2+",sysdate,2,'#',"+companyId+")";
				}
			}else{
				if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name='项目批复概算超额告警提示' and t.company_id is null")){
					sql2update = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+result2+"',t.create_date=sysdate where t.name='项目批复概算超额告警提示' and t.company_id is null";
				}else{
					sql2update = "insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'项目批复概算超额告警提示',"+result2+",sysdate,2,'#','')";
				}
			}
			
			
			baseinfoService.executeUpdateUpdate(sql2update);
			
			
			//3.采购竞价合同申报待办提示--未实现功能，默认设置为0,各公司为0
			String sql3update ="";
			if(companyId!=null && !"".equals(companyId)){
				if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name='采购竞价合同申报待办提示' and t.company_id='"+companyId+"'")){
//					sql3update = "update DW_CONTRACT_P_MANAGEMENT t set t.value=0,t.create_date=sysdate where t.name='采购竞价合同申报待办提示'";
				}else{
					sql3update = "insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'采购竞价合同申报待办提示',0,sysdate,3,'#',"+companyId+")";
					baseinfoService.executeUpdateUpdate(sql3update);
				}
			}else{
				if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name='采购竞价合同申报待办提示' and t.company_id is null")){
//					sql3update = "update DW_CONTRACT_P_MANAGEMENT t set t.value=0,t.create_date=sysdate where t.name='采购竞价合同申报待办提示'";
				}else{
					sql3update = "insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'采购竞价合同申报待办提示',0,sysdate,3,'#','')";
					baseinfoService.executeUpdateUpdate(sql3update);
				}
			}
			
			
			
			//4.合同审批流程超时提示
			String sql4 = "select count(*) from ("+
					"select i.processname,i.incident from incidents i ,ht_xx h where i.processname=h.model_id and h.pinstance_id = i.incident and i.status=1 and h.removed=0"+
					" and i.processname='合同审批流程' and sysdate-i.starttime > 30"+
					" union"+
					" select  i.processname,i.incident from tasks t,incidents i,ht_xx h where t.processname='合同审批流程' and t.Steplabel='备案' and t.status=1 "+
					" and sysdate-t.starttime > 90 and t.processname=h.model_id and t.processname=i.processname and i.status=1 and i.processname=h.model_id"+
					" and t.incident=i.incident and t.incident=h.pinstance_id  and i.incident=h.pinstance_id"+
					" and h.removed=0 and t.assignedtouser like '%ST/%')";
			String result4 = baseinfoService.findOneData2(sql4);
			String sql4update ="";
			if(companyId!=null && !"".equals(companyId)){
				if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name ='合同审批流程超时提示' and t.company_id='"+companyId+"'")){
					sql4update = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+0+"',t.create_date=sysdate where t.name='合同审批流程超时提示' and t.company_id='"+companyId+"'";
				}else{
					sql4update ="insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'合同审批流程超时提示',"+0+",sysdate,4,'#',"+companyId+")";
				}
			}else{
				if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name ='合同审批流程超时提示' and t.company_id is null")){
					sql4update = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+result4+"',t.create_date=sysdate where t.name='合同审批流程超时提示' and t.company_id is null";
				}else{
					sql4update ="insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'合同审批流程超时提示',"+result4+",sysdate,4,'#','')";
				}
			}
			
			baseinfoService.executeUpdateUpdate(sql4update);	
			
			
			//5.合同先执行后签订风险提示
			String sql5 = "";
			String sql5update ="";
			if(companyId!=null && !"".equals(companyId)){
				sql5 = "select count(id) from c_contract c where c.contract_owner_execute_id='"+companyId+"' and c.contract_type in ('2','2,1','2,2','2,3') and c.removed=0 and c.contract_start_date <  c.contract_signed_date";
				String result5 = baseinfoService.findOneData(sql5);
				if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name ='合同先执行后签订风险提示' and t.company_id='"+companyId+"'")){
					sql5update = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+result5+"',t.create_date=sysdate where t.name='合同先执行后签订风险提示' and t.company_id='"+companyId+"'";
				}else{
					sql5update ="insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'合同先执行后签订风险提示',"+result5+",sysdate,5,'#',"+companyId+")";
				}
			}else{
				sql5 = "select count(id) from c_contract c where c.contract_type in ('2','2,1','2,2','2,3') and c.removed=0 and c.contract_start_date <  c.contract_signed_date";
				String result5 = baseinfoService.findOneData(sql5);
				if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name ='合同先执行后签订风险提示' and t.company_id is null")){
					sql5update = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+result5+"',t.create_date=sysdate where t.name='合同先执行后签订风险提示' and t.company_id is null";
				}else{
					sql5update ="insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'合同先执行后签订风险提示',"+result5+",sysdate,5,'#','')";
				}
			}
			baseinfoService.executeUpdateUpdate(sql5update);
			
			
			//6.合同进展信息风险提示
			String sql6 = "";
			String sql6update = "";
			if(companyId!=null && !"".equals(companyId)){
				sql6 = "select t.object_id from c_progress t,c_contract c where t.object_type ='1' and t.progress_type='1' and t.removed='0' " +
				" and t.object_id = c.id and c.removed='0' and c.contract_owner_execute_id='"+companyId+"' and c.contract_type in ('2','2,1','2,2','2,3') group by t.object_id";
				List<Object[]> resultList6 = baseinfoService.findBySql(sql6);
				int result6 = 0;
				if(resultList6!=null && resultList6.size()>0){
					result6 = resultList6.size();
				}
				if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name ='合同进展信息风险提示' and t.company_id='"+companyId+"'")){
					sql6update = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+result6+"',t.create_date=sysdate where t.name='合同进展信息风险提示' and t.company_id='"+companyId+"'";
				}else{
					sql6update ="insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'合同进展信息风险提示',"+result6+",sysdate,6,'#','"+companyId+"')";
				}
			}else{
				sql6 = "select t.object_id from c_progress t,c_contract c where t.object_type ='1' and t.progress_type='1' and t.removed='0' " +
				" and t.object_id = c.id and c.removed='0' and c.contract_type in ('2','2,1','2,2','2,3') group by t.object_id";
				List<Object[]> resultList6 = baseinfoService.findBySql(sql6);
				int result6 = 0;
				if(resultList6!=null && resultList6.size()>0){
					result6 = resultList6.size();
				}
				if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name ='合同进展信息风险提示' and t.company_id is null")){
					sql6update = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+result6+"',t.create_date=sysdate where t.name='合同进展信息风险提示' and t.company_id is null";
				}else{
					sql6update ="insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'合同进展信息风险提示',"+result6+",sysdate,6,'#','')";
				}
			}
			
			
			baseinfoService.executeUpdateUpdate(sql6update);
			
			
			//7.合同变更超额告警提示
			String sql7 ="";
			String sql7update ="";
			if(companyId!=null && !"".equals(companyId)){
				sql7 = "select t.contract_id,sum(t.money) from C_CONTRACT_STATUS t,C_CONTRACT c where t.type =1 and t.removed='0' " +
				" and t.contract_id = c.id and c.removed='0' and c.contract_owner_execute_id='"+companyId+"' and c.contract_type in ('2','2,1','2,2','2,3') group by t.contract_id";
				List<Object[]> resultList7 = baseinfoService.findBySql(sql7);
				int result7 = 0 ;
				if (resultList7!=null && resultList7.size()>0) {
					String sql7_sub = "";
					for(Object[] array : resultList7){
						sql7_sub = "select * from c_contract c where c.id='"+array[0]+"' and to_number(c.contract_price)*0.15<"+array[1];
						if(baseinfoService.isResultExist(sql7_sub)){
							result7 ++;
						}
					}
				} 
				if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name='合同变更超额告警提示' and t.company_id='"+companyId+"'")){
					sql7update = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+result7+"',t.create_date=sysdate where t.name='合同变更超额告警提示' and t.company_id='"+companyId+"'";
				}else{
					sql7update = "insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'合同变更超额告警提示',"+result7+",sysdate,7,'#','"+companyId+"')";
				}
			}else{
				sql7 = "select t.contract_id,sum(t.money) from C_CONTRACT_STATUS t,C_CONTRACT c where t.type =1 and t.removed='0' " +
				" and t.contract_id = c.id and c.removed='0' and c.contract_type in ('2','2,1','2,2','2,3') group by t.contract_id";
				List<Object[]> resultList7 = baseinfoService.findBySql(sql7);
				int result7 = 0 ;
				if (resultList7!=null && resultList7.size()>0) {
					String sql7_sub = "";
					for(Object[] array : resultList7){
						sql7_sub = "select * from c_contract c where c.id='"+array[0]+"' and to_number(c.contract_price)*0.15<"+array[1];
						if(baseinfoService.isResultExist(sql7_sub)){
							result7 ++;
						}
					}
				} 
				if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name='合同变更超额告警提示' and t.company_id is null")){
					sql7update = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+result7+"',t.create_date=sysdate where t.name='合同变更超额告警提示' and t.company_id is null";
				}else{
					sql7update = "insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'合同变更超额告警提示',"+result7+",sysdate,7,'#','')";
				}
			}
			baseinfoService.executeUpdateUpdate(sql7update);
			
			
			//8.合同支付超付告警提示
			String sql8 = "";
			String sql8update ="";
			if(companyId!=null && !"".equals(companyId)){
				sql8 = "select t.contract_id,sum(t.money) from C_CONTRACT_STATUS t,C_CONTRACT C where t.type ='3' and t.removed='0' " +
				" and t.contract_id = c.id and c.removed='0' and c.contract_owner_execute_id='"+companyId+"' and c.contract_type in ('2','2,1','2,2','2,3') group by t.contract_id" ;	//实际支付
				List<Object[]> resultList8 = baseinfoService.findBySql(sql8);		//[合同id,实际支付]
				int result8 = 0 ;
				if (resultList8!=null && resultList8.size()>0) {
					String sql8_sub = "";
					for(Object[] array : resultList8){
						if(array[1]==null || "".equals(array[1]) || "0".equals(array[1])) continue;			//实际支付为空或为0，不计入
						String sql8_ = "select c.final_price,c.verify_price,c.nation_verify_price,c.contract_price,c.pay_type from c_contract c where c.removed='0' and c.id='"+array[0]+"'";
						List<Object[]> contract = baseinfoService.findBySql(sql8_);			//[投资监理，审价单位，国家审计，合同价,支付方式]
						if(contract!=null && contract.size()>0){
							if((contract.get(0)[0]==null || "".equals(contract.get(0)[0])) && (contract.get(0)[1]==null || "".equals(contract.get(0)[1])) 
									&& (contract.get(0)[2]==null || "".equals(contract.get(0)[2])) && (contract.get(0)[4]!=null && "单价核算".equals(contract.get(0)[4]))){
								continue;
							}
						}else{
							continue;
						}
						
						
						sql8_sub = "select sum(t.money) from C_CONTRACT_STATUS t where t.type ='3' and t.removed='0' and t.contract_id='"+array[0]+"' group by t.contract_id";
						String actualMoney = baseinfoService.findOneData(sql8_sub);//实际支付
						Double actual = null;
						if(actualMoney!=null && !"".equals(actualMoney) && !"0".equals(actualMoney)){
							actual = Double.valueOf(actualMoney);
						}
						if(contract.get(0)[0]!=null && !"".equals(contract.get(0)[0]) && actual > (0.0001+Double.valueOf(contract.get(0)[0].toString()))){		//finalPrice
							if(actual>Double.valueOf(contract.get(0)[0].toString())){
								result8++;
								continue;
							}
						}
						if(contract.get(0)[1]!=null && !"".equals(contract.get(0)[1]) && actual > (0.0001+Double.valueOf(contract.get(0)[1].toString()))){		//verifyPrice
							if(actual>Double.valueOf(contract.get(0)[1].toString())){
								result8++;
								continue;
							}
						}
						if(contract.get(0)[2]!=null && !"".equals(contract.get(0)[2]) && actual > (0.0001+Double.valueOf(contract.get(0)[2].toString()))){		//nationVerifyPrice
							if(actual>Double.valueOf(contract.get(0)[2].toString())){
								result8++;
								continue;
							}
						}
						sql8_sub = "select sum(t.money) from C_CONTRACT_STATUS t where t.type ='1' and t.removed='0' and t.contract_id='"+array[0]+"' group by t.contract_id";
						String changedMoney = baseinfoService.findOneData(sql8_sub);//合同变更金额
						Double afterChanged = 0.0;
						if(contract.get(0)[3]!=null && !"".equals(contract.get(0)[3])){
							afterChanged = Double.valueOf(contract.get(0)[3].toString());
						}
						
						if(changedMoney!=null && !"0".equals(changedMoney) ){
							afterChanged = afterChanged + Double.valueOf(contract.get(0)[3].toString());
						}
						
						if(actual > (0.0001+afterChanged) && (afterChanged==0.0) && !"单价核算".equals(contract.get(0)[4])){			//实际支付大于变更后价钱，并且支付方式不等于单价核算
							result8++;
						}
					}
				} 
				if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name='合同支付超付告警提示' and t.company_id='"+companyId+"'")){
					sql8update = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+result8+"',t.create_date=sysdate where t.name='合同支付超付告警提示' and t.company_id='"+companyId+"'";
				}else{
					sql8update = "insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'合同支付超付告警提示',"+result8+",sysdate,8,'#','"+companyId+"')";
				}
			}else{
				sql8 = "select t.contract_id,sum(t.money) from C_CONTRACT_STATUS t,C_CONTRACT C where t.type ='3' and t.removed='0' " +
				" and t.contract_id = c.id and c.removed='0' and c.contract_type in ('2','2,1','2,2','2,3') group by t.contract_id";	//实际支付
				List<Object[]> resultList8 = baseinfoService.findBySql(sql8);		//[合同id,实际支付]
				int result8 = 0 ;
				if (resultList8!=null && resultList8.size()>0) {
					String sql8_sub = "";
					for(Object[] array : resultList8){
						if(array[1]==null || "".equals(array[1]) || "0".equals(array[1])) continue;			//实际支付为空或为0，不计入
						String sql8_ = "select c.final_price,c.verify_price,c.nation_verify_price,c.contract_price,c.pay_type from c_contract c where c.removed='0' and c.id='"+array[0]+"'";
						List<Object[]> contract = baseinfoService.findBySql(sql8_);			//[投资监理，审价单位，国家审计，合同价,支付方式]
						if(contract!=null && contract.size()>0){
							if((contract.get(0)[0]==null || "".equals(contract.get(0)[0])) && (contract.get(0)[1]==null || "".equals(contract.get(0)[1])) 
									&& (contract.get(0)[2]==null || "".equals(contract.get(0)[2])) && (contract.get(0)[4]!=null && "单价核算".equals(contract.get(0)[4]))){
								continue;
							}
						}else{
							continue;
						}
						
						
						sql8_sub = "select sum(t.money) from C_CONTRACT_STATUS t where t.type ='3' and t.removed='0' and t.contract_id='"+array[0]+"' group by t.contract_id";
						String actualMoney = baseinfoService.findOneData(sql8_sub);//实际支付
						Double actual = null;
						if(actualMoney!=null && !"".equals(actualMoney) && !"0".equals(actualMoney)){
							actual = Double.valueOf(actualMoney);
						}
						if(contract.get(0)[0]!=null && !"".equals(contract.get(0)[0]) && actual > (0.0001+Double.valueOf(contract.get(0)[0].toString()))){		//finalPrice
							if(actual>Double.valueOf(contract.get(0)[0].toString())){
								result8++;
								continue;
							}
						}
						if(contract.get(0)[1]!=null && !"".equals(contract.get(0)[1]) && actual > (0.0001+Double.valueOf(contract.get(0)[1].toString()))){		//verifyPrice
							if(actual>Double.valueOf(contract.get(0)[1].toString())){
								result8++;
								continue;
							}
						}
						if(contract.get(0)[2]!=null && !"".equals(contract.get(0)[2]) && actual > (0.0001+Double.valueOf(contract.get(0)[2].toString()))){		//nationVerifyPrice
							if(actual>Double.valueOf(contract.get(0)[2].toString())){
								result8++;
								continue;
							}
						}
						sql8_sub = "select sum(t.money) from C_CONTRACT_STATUS t where t.type ='1' and t.removed='0' and t.contract_id='"+array[0]+"' group by t.contract_id";
						String changedMoney = baseinfoService.findOneData(sql8_sub);//合同变更金额
						Double afterChanged = null;
						if(contract.get(0)[3]!=null && !"".equals(contract.get(0)[3])){
							afterChanged = Double.valueOf(contract.get(0)[3].toString());
						}
						
						if(changedMoney!=null && !"0".equals(changedMoney) ){
							afterChanged = afterChanged + Double.valueOf(contract.get(0)[3].toString());
						}
						
						if(actual > (0.0001+afterChanged) && (afterChanged==0.0) && !"单价核算".equals(contract.get(0)[4])){			//实际支付大于变更后价钱，并且支付方式不等于单价核算
							result8++;
						}
						
						
					}
				}
				if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name='合同支付超付告警提示' and t.company_id is null")){
					sql8update = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+result8+"',t.create_date=sysdate where t.name='合同支付超付告警提示' and t.company_id is null";
				}else{
					sql8update = "insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'合同支付超付告警提示',"+result8+",sysdate,8,'#','')";
				}
			}
			baseinfoService.executeUpdateUpdate(sql8update);
			
			
			//9.合同销号条件异常提示
			String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String sql9 ="";
			String sql9update ="";
			if(companyId!=null && !"".equals(companyId)){
				sql9 = "select t.contract_id,sum(t.money) from c_contract_status t where t.removed='0' and t.type='2' and t.contract_id in"+ 
				"(select c.id from c_contract c where c.removed='0' and c.contract_owner_execute_id ='"+companyId+"' and c.contract_type in ('2','2,1','2,2','2,3') and c.contract_destory_date<'"+nowDate+"') group by t.contract_id";
				List<Object[]> resultList9 = baseinfoService.findBySql(sql9);		//合同编号、实际支付
				int result9 = 0 ;
				if (resultList9!=null && resultList9.size()>0) {
					String sql9_sub = "";
					for(Object[] array : resultList9){
						if(array[1]==null || "".equals(array[1])) continue;
						sql9_sub = "select sum(t.money) from C_CONTRACT_STATUS t where t.type =2 and t.removed='0' and t.contract_id='"+array[0]+"' group by t.contract_id";
						String planPay = baseinfoService.findOneData(sql9_sub);
						if(planPay!=null && !"".equals(planPay)){		//有变更信息
							if(Double.valueOf(planPay) > Double.valueOf(array[1].toString())){
								result9++;
							}
						}else{	//无变更信息,查询合同原价
							String sql9_sub2 = "select * from c_contract c where c.id='"+array[0]+"' and c.contract_price<"+array[1]+"";
							if(baseinfoService.isResultExist(sql9_sub2)){
								result9++;
							}
						}
					}
				} 
				if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name='合同销号条件异常提示' and t.company_id='"+companyId+"'")){
					sql9update = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+result9+"',t.create_date=sysdate where t.name='合同销号条件异常提示' and t.company_id='"+companyId+"'";
				}else{
					sql9update = "insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'合同销号条件异常提示',"+result9+",sysdate,9,'#','"+companyId+"')";
				}
			}else{
				sql9 = "select t.contract_id,sum(t.money) from c_contract_status t where t.removed='0' and t.type='2' and t.contract_id in"+ 
				"(select c.id from c_contract c where c.removed='0' and c.contract_type in ('2','2,1','2,2','2,3') and c.contract_destory_date<'"+nowDate+"') group by t.contract_id";
				List<Object[]> resultList9 = baseinfoService.findBySql(sql9);		//合同编号、实际支付
				int result9 = 0 ;
				if (resultList9!=null && resultList9.size()>0) {
					String sql9_sub = "";
					for(Object[] array : resultList9){
						if(array[1]==null || "".equals(array[1])) continue;
						sql9_sub = "select sum(t.money) from C_CONTRACT_STATUS t where t.type =2 and t.removed='0' and t.contract_id='"+array[0]+"' group by t.contract_id";
						String planPay = baseinfoService.findOneData(sql9_sub);
						if(planPay!=null && !"".equals(planPay)){		//有变更信息
							if(Double.valueOf(planPay) > Double.valueOf(array[1].toString())){
								result9++;
							}
						}else{	//无变更信息,查询合同原价
							String sql9_sub2 = "select * from c_contract c where c.id='"+array[0]+"' and c.contract_price<"+array[1]+"";
							if(baseinfoService.isResultExist(sql9_sub2)){
								result9++;
							}
						}
					}
				} 
				if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name='合同销号条件异常提示' and t.company_id is null")){
					sql9update = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+result9+"',t.create_date=sysdate where t.name='合同销号条件异常提示' and t.company_id is null";
				}else{
					sql9update = "insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'合同销号条件异常提示',"+result9+",sysdate,9,'#','')";
				}
			}
			baseinfoService.executeUpdateUpdate(sql9update);
			
			
			//10.合同质保期到期提示---未实现功能,默认设置为0
			String sql10update ="";
			if(companyId!=null && !"".equals(companyId)){
				if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name='合同质保期到期提示' and t.company_id='"+companyId+"'")){
//					sql10update = "update DW_CONTRACT_P_MANAGEMENT t set t.value=0,t.create_date=sysdate where t.name='合同质保期到期提示'";
				}else{
					sql10update = "insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'合同质保期到期提示',0,sysdate,10,'#','"+companyId+"')";
					baseinfoService.executeUpdateUpdate(sql10update);
				}
			}else{
				if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name='合同质保期到期提示' and t.company_id is null")){
//					sql10update = "update DW_CONTRACT_P_MANAGEMENT t set t.value=0,t.create_date=sysdate where t.name='合同质保期到期提示'";
				}else{
					sql10update = "insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'合同质保期到期提示',0,sysdate,10,'#','')";
					baseinfoService.executeUpdateUpdate(sql10update);
				}
			}
			
			
			//11.待补全的项目
			String sql11 = "";
			String sql11update ="";
			if(companyId!=null && !"".equals(companyId)){
				sql11 = "select count(*) from c_project p where p.project_type in (2,3,4) and p.removed=0 and p.project_adddept_id='"+companyId+"'"+ 
				" and (p.dispatch_no is null or  p.approval_date is null "+ 
				" or p.project_moneysource is null or"+ 
				" p.money_source_type  is null or  p.professional_type is null )";			//待补全信息的项目
				
				String result11 = baseinfoService.findOneData(sql11);
				if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name ='待补全项目信息' and t.company_id='"+companyId+"'")){
					sql11update = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+result11+"',t.create_date=sysdate where t.name='待补全项目信息' and t.company_id='"+companyId+"'";
				}else{
					sql11update ="insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'待补全项目信息',"+result11+",sysdate,11,'#',"+companyId+")";
				}
			}else{
				sql11 = "select count(*) from c_project p where p.project_type in (2,3,4) and p.removed=0 "+ 
				" and (p.dispatch_no is null or  p.approval_date is null "+ 
				" or p.project_moneysource is null or"+ 
				" p.money_source_type  is null or  p.professional_type is null )";			//待补全信息的项目
				
				String result11 = baseinfoService.findOneData(sql11);
				if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name ='待补全项目信息' and t.company_id is null")){
					sql11update = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+result11+"',t.create_date=sysdate where t.name='待补全项目信息' and t.company_id is null";
				}else{
					sql11update ="insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'待补全项目信息',"+result11+",sysdate,11,'#',"+companyId+")";
				}
			}
			
			baseinfoService.executeUpdateUpdate(sql11update);
			
			
			//12.待补全的合同
			String sql12 = "";
			String sql12update ="";
			if(companyId!=null && !"".equals(companyId)){
				sql12 = "select count(*) from c_contract c where c.contract_type in ('2','2,1','2,2','2,3') and c.removed=0 and c.contract_owner_execute_id='"+companyId+"'"+ 
				" and (c.contract_name is null or c.contract_no is null or c.contract_price is null"+ 
				" or c.contract_owner_name is null or c.contract_owner_execute_name is null or c.build_supplier_name  is null"+
				" or c.pay_type is null or c.contract_passed_date is null or c.contract_start_date is null  or c.contract_signed_date is null"+
				" or c.contract_end_date is null or c.contract_status is null or c.invite_bid_type is null or c.project_name is null)";		//待补全信息的合同
				
				String result12 = baseinfoService.findOneData(sql12);
				if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name ='待补全合同信息' and t.company_id='"+companyId+"'")){
					sql12update = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+result12+"',t.create_date=sysdate where t.name='待补全合同信息' and t.company_id='"+companyId+"'";
				}else{
					sql12update ="insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'待补全合同信息',"+result12+",sysdate,12,'#',"+companyId+")";
				}
			}else{
				sql12 = "select count(*) from c_contract c where c.contract_type in ('2','2,1','2,2','2,3') and c.removed=0 "+ 
				" and (c.contract_name is null or c.contract_no is null or c.contract_price is null"+ 
				" or c.contract_owner_name is null or c.contract_owner_execute_name is null or c.build_supplier_name  is null"+
				" or c.pay_type is null or c.contract_passed_date is null or c.contract_start_date is null  or c.contract_signed_date is null"+
				" or c.contract_end_date is null or c.contract_status is null or c.invite_bid_type is null or c.project_name is null)";		//待补全信息的合同
				
				String result12 = baseinfoService.findOneData(sql12);
				if(baseinfoService.isResultExist("select * from DW_CONTRACT_P_MANAGEMENT t where t.name ='待补全合同信息' and t.company_id is null ")){
					sql12update = "update DW_CONTRACT_P_MANAGEMENT t set t.value='"+result12+"',t.create_date=sysdate where t.name='待补全合同信息' and t.company_id is null";
				}else{
					sql12update ="insert into DW_CONTRACT_P_MANAGEMENT t values((select sys_guid() from dual),'待补全合同信息',"+result12+",sysdate,12,'#',"+companyId+")";
				}
			}
			
			baseinfoService.executeUpdateUpdate(sql12update);
			
		}
		
	
	
	}
	
	
	public void updateDwContractPriceNumber(){
		String sqldelete ="delete from dw_contract_p_price_number";
		baseinfoService.executeUpdateUpdate(sqldelete);
		
		
		String sql = "insert into dw_contract_p_price_number(id,contract_type,control_date,company_id,company_name,contract_price,contract_num,data_type,create_date)"+ 
		" select to_char(seq_dw_contract_p_price_number.nextval) sec_id,cc.* from("+
				" select c.contract_type,c.contract_signed_date,c.contract_owner_execute_id,c.contract_owner_execute_name,"+
				" sum(c.contract_price) sum_price,count(*) sum_num,1,sysdate from ("+
				" select substr(t.contract_type,0,1) contract_type,"+
				"(case instr(t.contract_signed_date,'-',1,2) when 7 then replace(substr(t.contract_signed_date,0,6),'-','-0') when 8 then substr(t.contract_signed_date,0,7) end) contract_signed_date,"+
				"t.contract_owner_execute_id,t.contract_owner_execute_name,to_number(t.contract_price) contract_price"+
				" from c_contract t where t.removed = '0' and t.contract_type like '%,%' and t.contract_owner_execute_id is not null"+
				" and t.contract_signed_date is not null and t.contract_signed_date like '%-%' and instr(t.contract_signed_date,'-',1,2)>0"+ 
				" and (case instr(t.contract_signed_date,'-',1,2) when 7 then substr(t.contract_signed_date,6,1) when 8 then substr(t.contract_signed_date,6,2) end) between 1 and 12"+
				" and to_date(case instr(t.contract_signed_date,'-',1,2) when 7 then substr(t.contract_signed_date,0,6) when 8 then substr(t.contract_signed_date,0,7) end,'YYYY-MM')>=to_date('2008-01','YYYY-MM')"+ 
				" and to_date(case instr(t.contract_signed_date,'-',1,2) when 7 then substr(t.contract_signed_date,0,6) when 8 then substr(t.contract_signed_date,0,7) end,'YYYY-MM')<=sysdate"+
				") c"+
				" group by contract_type,contract_signed_date,contract_owner_execute_id,contract_owner_execute_name"+
				" order by contract_type,contract_signed_date,contract_owner_execute_id,contract_owner_execute_name"+
				") cc";
		baseinfoService.executeUpdateUpdate(sql);
		
		//新增的
		String sql1 = "select distinct t.control_date from dw_contract_p_price_number t order by t.control_date ASC";
		List<Object[]> result = baseinfoService.findBySql(sql1);
		
		if(result!=null && result.size()>0){
			for(int i=0; i<result.size(); i++){
				String sql2 = "select sum(t.contract_num),sum(t.contract_price) from dw_contract_p_price_number t where t.control_date='"+result.get(i)+"'" +
						" and t.company_id in ('2718','2719','2720','2721','2722','2545')";
				List<Object[]> result2 = baseinfoService.findBySql(sql2);
				if(result2!=null && result2.size()>0){
					int contractType = 2; //默认为2，运维类
					int dataType = 1;	//默认为1，合同数据
					for(int m=0; m<result2.size(); m++){	//系统每次都先delete，所以都是insert
						String contractNum ;
						String contractPrice;
						if(result2.get(m)[0]==null){
							contractNum = "0";
						}else{
							contractNum = formatStringData(result2.get(m)[0].toString());
						}
						if(result2.get(m)[1]==null){
							contractPrice = "0";
						}else{
							contractPrice = formatStringData(result2.get(m)[1].toString());
						}
						
						
						String sql3 ="insert into dw_contract_p_price_number(id,contract_type,control_date,company_id,company_name,contract_price,contract_num,data_type,create_date) " +
								" values((select sys_guid() from dual),"+contractType+",'"+result.get(i)+"','center','维保中心',"+contractPrice+","+contractNum+","+dataType+",sysdate)";
						baseinfoService.executeUpdateUpdate(sql3);
					}
				}
			}
		}
		

	}
	
	public String formatStringData(String str){
		if(str==null || "".equals(str)) return str;
		if(str.indexOf(".")>=0){	//有小数点
			str = str+"0000";
			str = str.substring(0,str.indexOf(".")+5);
			
		}
		return str;
	}

}
