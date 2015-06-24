/**
 * 
 */
package com.wonders.task.autoUrge.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.schedule.util.SpringBeanUtil;
import com.wonders.task.autoUrge.constants.AutoUrgeConstants;
import com.wonders.task.autoUrge.model.bo.TShortMsg;
import com.wonders.task.autoUrge.model.bo.TUrgeLog;
import com.wonders.task.autoUrge.model.vo.UserUrgeVo;
import com.wonders.task.autoUrge.service.AutoUrgeService;
import com.wonders.task.sample.ITaskService;

/** 
 * @ClassName: AutoUrgeMain 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2013-4-30 上午8:19:52 
 *  
 */

@Transactional(value = "txManager2",propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
@Service("autoUrgeMain")
public class AutoUrgeMain implements ITaskService{
	private AutoUrgeService service;

	public AutoUrgeService getService() {
		return service;
	}
	@Autowired(required=false)
	public void setService(@Qualifier("autoUrgeService")AutoUrgeService service) {
		this.service = service;
	}
	/** 
	* @Title: exec 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param 这里 设定超时天数
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public String exec(String param) {
		// TODO Auto-generated method stub
		List<UserUrgeVo> list = this.service.listUrgeInfo(param);
		Map<String,String> map = this.service.userMobileMap();
		//msg表
		List<TShortMsg> result = new ArrayList<TShortMsg>();
		//错误信息
		List<TUrgeLog> error = new ArrayList<TUrgeLog>();
		TShortMsg t = null;
		TUrgeLog u = null;
		String mobile1 = null;
		String mobile2 = null;
		for(UserUrgeVo vo : list){
			//employee
			mobile1 = map.get(vo.getUserId());
			if(mobile1 != null && mobile1.length() > 0){
				t = new TShortMsg();
				t.setMobile(mobile1);
				t.setContent(AutoUrgeConstants.EMPLOYEE_URGE+" "+vo.getSummary());
				if("ST/G00100000196".equals(vo.getUserId()) ||
						"ST/G01000000005".equals(vo.getUserId())||
						"ST/G07000000007".equals(vo.getUserId())||
						"ST/G02000100003".equals(vo.getUserId())||
						"ST/G00100000186".equals(vo.getUserId())||
						"ST/G00100000001".equals(vo.getUserId())||
						"ST/G00100000004".equals(vo.getUserId())||
						"ST/G00100000005".equals(vo.getUserId())||
						"ST/G00100000006".equals(vo.getUserId())||
						"ST/G00100000007".equals(vo.getUserId())||
						"ST/G00100000083".equals(vo.getUserId())){
					t.setStatus("1");
				}
				result.add(t);
				
				u = new TUrgeLog();
				u.setError(AutoUrgeConstants.SUCCESS_MOBILE_INFO);
				u.setContent(AutoUrgeConstants.EMPLOYEE_URGE+" "+vo.getSummary());
				u.setName(vo.getUserId());
				u.setDept(vo.getDeptId());
				error.add(u);
			}else{
				u = new TUrgeLog();
				u.setError(AutoUrgeConstants.ERROR_MOBILE_INFO);
				u.setContent(AutoUrgeConstants.EMPLOYEE_URGE+" "+vo.getSummary());
				u.setName(vo.getUserId());
				u.setDept(vo.getDeptId());
				error.add(u);
			}	
			
			//leader
			if("ST/G01000000005".equals(vo.getLeaderId())){
				mobile2 = map.get("ST/G00100000028");
			}else{
				mobile2 = map.get(vo.getLeaderId());
			}
			if(mobile2 != null && mobile2.length() > 0){
				t = new TShortMsg();
				t.setMobile(mobile2);
				t.setContent(AutoUrgeConstants.LEADER_URGE_PREFEIX+" "+vo.getUserName()+" "+AutoUrgeConstants.LEDER_URGE_POSTFIX+" "+vo.getSummary());
				if("ST/G00100000196".equals(vo.getLeaderId()) ||
						"ST/G01000000005".equals(vo.getLeaderId())||
						"ST/G07000000007".equals(vo.getLeaderId())||
						"ST/G02000100003".equals(vo.getLeaderId())||
						"ST/G00100000186".equals(vo.getLeaderId())||
						"ST/G00100000001".equals(vo.getLeaderId())||
						"ST/G00100000004".equals(vo.getLeaderId())||
						"ST/G00100000005".equals(vo.getLeaderId())||
						"ST/G00100000006".equals(vo.getLeaderId())||
						"ST/G00100000007".equals(vo.getLeaderId())||
						"ST/G00100000083".equals(vo.getLeaderId())){
					t.setStatus("1");
				}
				result.add(t);
				
				u = new TUrgeLog();
				u.setError(AutoUrgeConstants.SUCCESS_MOBILE_INFO);
				u.setContent(AutoUrgeConstants.LEADER_URGE_PREFEIX+" "+vo.getUserName()+" "+AutoUrgeConstants.LEDER_URGE_POSTFIX+" "+vo.getSummary());
				u.setName(vo.getLeaderId());
				u.setDept(vo.getDeptId());
				error.add(u);
			}else{
				u = new TUrgeLog();
				u.setError(AutoUrgeConstants.ERROR_MOBILE_INFO);
				u.setContent(AutoUrgeConstants.LEADER_URGE_PREFEIX+" "+vo.getUserName()+" "+AutoUrgeConstants.LEDER_URGE_POSTFIX+" "+vo.getSummary());
				u.setName(vo.getLeaderId());
				u.setDept(vo.getDeptId());
				error.add(u);
			}
		}
		
		this.service.saveBatch(result);
		this.service.saveBatch(error);
		return "";
	}
	
	
	public static void main(String[] args){
		ApplicationContext applicationContext = null;  
		String[] fileUrl = new String[]{"classpath*:*Context*.xml"};  
		applicationContext = new ClassPathXmlApplicationContext(fileUrl);  
		ITaskService task = (ITaskService) SpringBeanUtil.getBean("autoUrgeMain");
		task.exec("5");
		
	}
	
}
