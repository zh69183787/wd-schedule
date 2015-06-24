/**   
 * @Title: BudgetEstimate.java 
 * @Package com.wonders.task.workflow.strategy 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author zhoushun   
 * @date 2014年8月4日 下午3:22:30 
 * @version V1.0   
 */
package com.wonders.task.workflow.strategy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.wonders.schedule.util.DbUtil;
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
public class BudgetEstimate implements IKPIControl {

	private static final String sql = "select "
			+ " case when ( sum(to_number(c.contract_price))) is null"
			+ " then 0 else sum(to_number(c.contract_price)) end c ,"
			+ " case when (sum(distinct to_number(p.project_budget_all))) is null "
			+ " then 0 else  sum(distinct to_number(p.project_budget_all)) end p "
			+ " from c_contract c,c_project p where c.project_name= ? and c.project_name is not null"
			+ " and c.project_name=p.project_name and c.removed=0 and p.removed=0";
	/**
	 * @Title: operate
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param 设定文件
	 * @throws
	 */
	@Override
	public boolean operate(ReviewMainXml xml) {
		double c,p = 0;
		ReviewMainVo vo = xml.getMainVo();
		List<Map<String,Object>> list = DbUtil.getJdbcTemplate("").
				queryForList(sql,new Object[]{StringUtil.getNotNullValueString(vo.getProjectName())});
		if(list != null && list.size() > 0){
			Map<String,Object> map = list.get(0);
			c = ((BigDecimal) map.get("c")).doubleValue() ;p = ((BigDecimal) map.get("p")).doubleValue();
			if(c!=0 && p!=0 && p<c){
				return true;
			}
		}
		return false;
	}

	
}