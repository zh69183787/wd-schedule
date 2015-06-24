/**   
* @Title: BudgetEstimate.java 
* @Package com.wonders.task.workflow.strategy 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年8月4日 下午3:22:30 
* @version V1.0   
*/
package com.wonders.task.workflow.strategy;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import com.wonders.schedule.util.StringUtil;
import com.wonders.task.workflow.model.flow.vo.ReviewMainVo;
import com.wonders.task.workflow.model.flow.xml.ReviewMainXml;

/** 
 * @ClassName: BudgetEstimate 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年8月4日 下午3:22:30 
 *  
 */
public class BasicInfo implements IKPIControl{
	public final static String checkField[] = {
		"projectName","projectIdentifier","projectCompany",
		"projectCharge","projectChargeDept","contractIdentifier",
		"contractSelfNum","contractName","contractMoney",
		"contractMoneyType","contractBudget",
		"purchaseType","contractType1",
		"contractType2","contractGroup",
		"oppositeCompany","dealPerson",
		"passTime","signTime","execPeriodStart",
		"execPeriodEnd"
	};
	
	/** 
	* @Title: operate 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param     设定文件 
	* @throws 
	*/
	@Override
	public boolean operate(ReviewMainXml xml) {
		ReviewMainVo vo = xml.getMainVo();
		return propertyCheck(vo);
	}
	
	
	private boolean propertyCheck(ReviewMainVo vo){
		List<String> tempList = Arrays.asList(checkField);
		boolean flag = false;
		try {  
            Field[] f = vo.getClass().getDeclaredFields();//根据字段名来获取字段          
            if (f!= null && f.length > 0) {  
            	for(Field temp : f){
            		temp.setAccessible(true);
            		if(tempList.contains(temp.getName()) && 
            			(temp.get(vo) == null || 
            			StringUtil.getNotNullValueString(temp.get(vo)).length()==0)){
            			return true;
            		}
            		
            	} 
            }  
        } catch (Exception ex) {  
                ex.printStackTrace();  
        }  
		
		return flag;
	}

}
