/**
 * 
 */
package com.wonders.task.contractInfo.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.schedule.util.SpringBeanUtil;
import com.wonders.task.contractInfo.constants.ContractInfoConstants;
import com.wonders.task.contractInfo.model.bo.ContractInfo;
import com.wonders.task.contractInfo.service.ContractInfoService;
import com.wonders.task.sample.ITaskService;

/** 
 * @ClassName: ContractInfoMain 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-5-13 下午02:57:13 
 *  
 */
@Transactional(value = "txManager",propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
@Service("contractInfoMain")
public class ContractInfoMain implements ITaskService{
	private	ContractInfoService service;
	
	public ContractInfoService getService() {
		return service;
	}
	
	@Autowired(required=false)
	public void setService(@Qualifier("contractInfoService")ContractInfoService service) {
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
		String date = param;
		if(param == null || "".equals(param)){
			 date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		}
		//合同列表
		List<String> cl = this.service.contractList();
		//进展次数统计
		Map<String,String> pcm = this.service.processStat(date, ContractInfoConstants.CONTRACT_CAL_MONTH,ContractInfoConstants.CONTRACT_STAT_COUNT );
		Map<String,String> pcy = this.service.processStat(date, ContractInfoConstants.CONTRACT_CAL_YEAR,ContractInfoConstants.CONTRACT_STAT_COUNT );
		Map<String,String> pct = this.service.processStat(date, ContractInfoConstants.CONTRACT_CAL_TOTAL,ContractInfoConstants.CONTRACT_STAT_COUNT );
		//变更次数统计
		Map<String,String> sccm = this.service.statusStat(date, ContractInfoConstants.CONTRACT_CAL_MONTH, ContractInfoConstants.CONTRACT_STAT_COUNT, ContractInfoConstants.CONTRACT_VALUE_ID, ContractInfoConstants.CONTRACT_STATUS_CHANGE);				
		Map<String,String> sccy = this.service.statusStat(date, ContractInfoConstants.CONTRACT_CAL_YEAR, ContractInfoConstants.CONTRACT_STAT_COUNT, ContractInfoConstants.CONTRACT_VALUE_ID, ContractInfoConstants.CONTRACT_STATUS_CHANGE);				
		Map<String,String> scct = this.service.statusStat(date, ContractInfoConstants.CONTRACT_CAL_TOTAL, ContractInfoConstants.CONTRACT_STAT_COUNT, ContractInfoConstants.CONTRACT_VALUE_ID, ContractInfoConstants.CONTRACT_STATUS_CHANGE);				
		//变更金额统计
		Map<String,String> scsmm = this.service.statusStat(date, ContractInfoConstants.CONTRACT_CAL_MONTH, ContractInfoConstants.CONTRACT_STAT_SUM, ContractInfoConstants.CONTRACT_VALUE_MONEY, ContractInfoConstants.CONTRACT_STATUS_CHANGE);				
		Map<String,String> scsmy = this.service.statusStat(date, ContractInfoConstants.CONTRACT_CAL_YEAR, ContractInfoConstants.CONTRACT_STAT_SUM, ContractInfoConstants.CONTRACT_VALUE_MONEY, ContractInfoConstants.CONTRACT_STATUS_CHANGE);				
		Map<String,String> scsmt = this.service.statusStat(date, ContractInfoConstants.CONTRACT_CAL_TOTAL, ContractInfoConstants.CONTRACT_STAT_SUM, ContractInfoConstants.CONTRACT_VALUE_MONEY, ContractInfoConstants.CONTRACT_STATUS_CHANGE);				
		//变更比例统计
		Map<String,String> scspm = this.service.statusStat(date, ContractInfoConstants.CONTRACT_CAL_MONTH, ContractInfoConstants.CONTRACT_STAT_SUM, ContractInfoConstants.CONTRACT_VALUE_PERCENT, ContractInfoConstants.CONTRACT_STATUS_CHANGE);				
		Map<String,String> scspy = this.service.statusStat(date, ContractInfoConstants.CONTRACT_CAL_YEAR, ContractInfoConstants.CONTRACT_STAT_SUM, ContractInfoConstants.CONTRACT_VALUE_PERCENT, ContractInfoConstants.CONTRACT_STATUS_CHANGE);				
		Map<String,String> scspt = this.service.statusStat(date, ContractInfoConstants.CONTRACT_CAL_TOTAL, ContractInfoConstants.CONTRACT_STAT_SUM, ContractInfoConstants.CONTRACT_VALUE_PERCENT, ContractInfoConstants.CONTRACT_STATUS_CHANGE);				
		//计划支付金额统计
		Map<String,String> spsmm = this.service.statusStat(date, ContractInfoConstants.CONTRACT_CAL_MONTH, ContractInfoConstants.CONTRACT_STAT_SUM, ContractInfoConstants.CONTRACT_VALUE_MONEY, ContractInfoConstants.CONTRACT_STATUS_PLAN_PAY);				
		Map<String,String> spsmy = this.service.statusStat(date, ContractInfoConstants.CONTRACT_CAL_YEAR, ContractInfoConstants.CONTRACT_STAT_SUM, ContractInfoConstants.CONTRACT_VALUE_MONEY, ContractInfoConstants.CONTRACT_STATUS_PLAN_PAY);				
		Map<String,String> spsmt = this.service.statusStat(date, ContractInfoConstants.CONTRACT_CAL_TOTAL, ContractInfoConstants.CONTRACT_STAT_SUM, ContractInfoConstants.CONTRACT_VALUE_MONEY, ContractInfoConstants.CONTRACT_STATUS_PLAN_PAY);				
		//计划支付比例统计
		Map<String,String> spspm = this.service.statusStat(date, ContractInfoConstants.CONTRACT_CAL_MONTH, ContractInfoConstants.CONTRACT_STAT_SUM, ContractInfoConstants.CONTRACT_VALUE_PERCENT, ContractInfoConstants.CONTRACT_STATUS_PLAN_PAY);				
		Map<String,String> spspy = this.service.statusStat(date, ContractInfoConstants.CONTRACT_CAL_YEAR, ContractInfoConstants.CONTRACT_STAT_SUM, ContractInfoConstants.CONTRACT_VALUE_PERCENT, ContractInfoConstants.CONTRACT_STATUS_PLAN_PAY);				
		Map<String,String> spspt = this.service.statusStat(date, ContractInfoConstants.CONTRACT_CAL_TOTAL, ContractInfoConstants.CONTRACT_STAT_SUM, ContractInfoConstants.CONTRACT_VALUE_PERCENT, ContractInfoConstants.CONTRACT_STATUS_PLAN_PAY);				
		//实际支付金额统计
		Map<String,String> sasmm = this.service.statusStat(date, ContractInfoConstants.CONTRACT_CAL_MONTH, ContractInfoConstants.CONTRACT_STAT_SUM, ContractInfoConstants.CONTRACT_VALUE_MONEY, ContractInfoConstants.CONTRACT_STATUS_ACTUAL_PAY);				
		Map<String,String> sasmy = this.service.statusStat(date, ContractInfoConstants.CONTRACT_CAL_YEAR, ContractInfoConstants.CONTRACT_STAT_SUM, ContractInfoConstants.CONTRACT_VALUE_MONEY, ContractInfoConstants.CONTRACT_STATUS_ACTUAL_PAY);				
		Map<String,String> sasmt = this.service.statusStat(date, ContractInfoConstants.CONTRACT_CAL_TOTAL, ContractInfoConstants.CONTRACT_STAT_SUM, ContractInfoConstants.CONTRACT_VALUE_MONEY, ContractInfoConstants.CONTRACT_STATUS_ACTUAL_PAY);				
		//实际支付比例统计
		Map<String,String> saspm = this.service.statusStat(date, ContractInfoConstants.CONTRACT_CAL_MONTH, ContractInfoConstants.CONTRACT_STAT_SUM, ContractInfoConstants.CONTRACT_VALUE_PERCENT, ContractInfoConstants.CONTRACT_STATUS_ACTUAL_PAY);				
		Map<String,String> saspy = this.service.statusStat(date, ContractInfoConstants.CONTRACT_CAL_YEAR, ContractInfoConstants.CONTRACT_STAT_SUM, ContractInfoConstants.CONTRACT_VALUE_PERCENT, ContractInfoConstants.CONTRACT_STATUS_ACTUAL_PAY);				
		Map<String,String> saspt = this.service.statusStat(date, ContractInfoConstants.CONTRACT_CAL_TOTAL, ContractInfoConstants.CONTRACT_STAT_SUM, ContractInfoConstants.CONTRACT_VALUE_PERCENT, ContractInfoConstants.CONTRACT_STATUS_ACTUAL_PAY);				
		
		//项目
		Map<String,String> pname = this.service.projectName();
		Map<String,String> pno = this.service.projectNo();
		
		List<ContractInfo> list = new ArrayList<ContractInfo>();
		
		if(cl.size() > 0){
			
			for(String s : cl){
				ContractInfo c = new ContractInfo();
				c.setStatDate(date);
				c.setContractId(s);
				c.setProjectNo(pno.get(s)==null?"":pno.get(s));
				c.setProjectName(pname.get(s)==null?"":pname.get(s));
				c.setProcessCountMonth(pcm.get(s)==null? "0":pcm.get(s));
				c.setProcessCountYear(pcm.get(s)==null? "0":pcy.get(s));
				c.setProcessCountTotal(pcm.get(s)==null? "0":pct.get(s));
				c.setChangeCountMonth(sccm.get(s)==null?"0":sccm.get(s));
				c.setChangeCountYear(sccy.get(s)==null?"0":sccy.get(s));
				c.setChangeCountTotal(scct.get(s)==null?"0":scct.get(s));
				c.setChangeMoneyMonth(scsmm.get(s)==null?"0":scsmm.get(s));
				c.setChangeMoneyYear(scsmy.get(s)==null?"0":scsmy.get(s));
				c.setChangeMoneyTotal(scsmt.get(s)==null?"0":scsmt.get(s));
				c.setChangePercentMonth(scspm.get(s)==null?"0":scspm.get(s));
				c.setChangePercentYear(scspy.get(s)==null?"0":scspy.get(s));
				c.setChangePercentTotal(scspt.get(s)==null?"0":scspt.get(s));
				c.setPayPlanMoneyMonth(spsmm.get(s)==null?"0":spsmm.get(s));
				c.setPayPlanMoneyYear(spsmy.get(s)==null?"0":spsmy.get(s));
				c.setPayPlanMoneyTotal(spsmt.get(s)==null?"0":spsmt.get(s));
				c.setPayPlanPercentMonth(spspm.get(s)==null?"0":spspm.get(s));
				c.setPayPlanPercentYear(spspy.get(s)==null?"0":spspy.get(s));
				c.setPayPlanPercentTotal(spspt.get(s)==null?"0":spspt.get(s));
				c.setPayActualMoneyMonth(sasmm.get(s)==null?"0":sasmm.get(s));
				c.setPayActualMoneyYear(sasmy.get(s)==null?"0":sasmy.get(s));
				c.setPayActualMoneyTotal(sasmt.get(s)==null?"0":sasmt.get(s));
				c.setPayActualPercentMonth(saspm.get(s)==null?"0":saspm.get(s));
				c.setPayActualPercentYear(saspy.get(s)==null?"0":saspy.get(s));
				c.setPayActualPercentTotal(saspt.get(s)==null?"0":saspt.get(s));
				//System.out.println(c.getPayActualMoneyYear());
				list.add(c);
			}
			
			this.service.saveBatch(list);
		}
				
		return "";
	}
	

	public static void main(String[] args){
		ApplicationContext applicationContext = null;  
		String[] fileUrl = new String[]{"classpath*:*Context*.xml"};  
		applicationContext = new ClassPathXmlApplicationContext(fileUrl);  
		ITaskService task = (ITaskService) SpringBeanUtil.getBean("contractInfoMain");
		task.exec("");
		
//		String s1 = new String("123");
//		String s2 = new String("123");
//		System.out.println(s1==s2);
//		System.out.println(s1.equals(s2));
//		System.out.println(s1.hashCode());
//		System.out.println(s2.hashCode());
//		
//		System.out.println(genRandomNum(12));
		
		
	}
	
	public static String random_serial(int limit, int need) {
        int[] temp = new int[limit];
        int[] result = new int[need];
         for (int i = 0; i < limit; i++)
             temp[i] = i;
         int w;
         Random rand = new Random();
         System.out.println(rand.nextInt(10));
         for (int i = 0; i < need; i++) {
            w = rand.nextInt(limit - i) + i;
             int t = temp[i];
            temp[i] = temp[w];
            temp[w] = t;
             result[i] = temp[i];
         }
         String s = "";
         for(int r:result){
        	 s += r;
         }
         return s;
    }
	
	
	 /**
	  * 生成随即密码
	  * @param pwd_len 生成的密码的总长度
	  * @return  密码的字符串
	  */
	 public static String genRandomNum(int pwd_len){
	  //35是因为数组是从0开始的，26个字母+10个数字
	  final int  maxNum = 36;
	  int i;  //生成的随机数
	  int count = 0; //生成的密码的长度
	  char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
	    'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
	    'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	  
	  StringBuffer pwd = new StringBuffer("");
	  Random r = new Random();
	  while(count < pwd_len){
	   //生成随机数，取绝对值，防止生成负数，
	   
	   i = Math.abs(r.nextInt(maxNum));  //生成的数最大为36-1
	   
	   if (i >= 0 && i < str.length) {
	    pwd.append(str[i]);
	    count ++;
	   }
	  }
	  
	  return pwd.toString();
	 }
}
