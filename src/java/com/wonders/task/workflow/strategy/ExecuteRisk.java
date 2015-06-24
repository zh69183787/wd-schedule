/**   
* @Title: BudgetEstimate.java 
* @Package com.wonders.task.workflow.strategy 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年8月4日 下午3:22:30 
* @version V1.0   
*/
package com.wonders.task.workflow.strategy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.wonders.task.workflow.model.flow.vo.ReviewMainVo;
import com.wonders.task.workflow.model.flow.xml.ReviewMainXml;

/** 
 * @ClassName: BudgetEstimate 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年8月4日 下午3:22:30 
 *  
 */
public class ExecuteRisk implements IKPIControl{

	/** 
	* @Title: operate 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param     设定文件 
	* @throws 
	*/
	@Override
	public boolean operate(ReviewMainXml xml) {
		ReviewMainVo vo = xml.getMainVo();
		return compare_date(vo.getExecPeriodStart(),vo.getSignTime());
	}

	private boolean compare_date(String DATE1, String DATE2) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() < dt2.getTime()) {
				return true;
			} else {
				//System.out.println("dt1在dt2后");
				return false;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return false;
	}
}
