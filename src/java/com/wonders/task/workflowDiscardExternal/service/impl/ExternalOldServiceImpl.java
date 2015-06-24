/**   
* @Title: ExternalNewServiceImpl.java 
* @Package com.wonders.task.workflowDiscardExternal.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年7月1日 下午1:17:52 
* @version V1.0   
*/
package com.wonders.task.workflowDiscardExternal.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.schedule.util.DbUtil;
import com.wonders.task.contractReview.util.ContractReviewUtil;
import com.wonders.task.workflowDiscardExternal.dao.ExternalOldDao;
import com.wonders.task.workflowDiscardExternal.model.bo.DiscardBo;
import com.wonders.task.workflowDiscardExternal.model.vo.ApproveVo;
import com.wonders.task.workflowDiscardExternal.model.vo.ProcessInfoVo;
import com.wonders.task.workflowDiscardExternal.model.vo.DiscardVo;
import com.wonders.task.workflowDiscardExternal.service.ExternalOldService;
import com.wonders.task.workflowDiscardExternal.util.BeanUtils;

/** 
 * @ClassName: ExternalNewServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年7月1日 下午1:17:52 
 *  
 */

@Transactional(value = "txManager2",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service("externalDiscardOldService")
public class ExternalOldServiceImpl implements ExternalOldService{
	@Autowired
	private ExternalOldDao dao;
		
	public List<ProcessInfoVo> getInfo(){
		String sql = "select distinct t.processname process,"
				+ " t.incident,"
				+ " t.assignedtouser loginName ,t.steplabel stepName from "
				+ " tasks t,incidents i , pcl_project_discard r "
				+ " where t.steplabel='返回修改' and t.endtime >= sysdate-1 "
				+ " and i.processname=t.processname and i.incident=t.incident "
				+ " and i.status=1 and t.status=1 and i.processname='项目销项流程'"
				+ " and r.removed=0 and r.INSTANCEID=i.incident and r.MODELID = i.processname";
		List<ProcessInfoVo> list = DbUtil.getJdbcTemplate("stptinc").
				query(sql,new BeanPropertyRowMapper(ProcessInfoVo.class));
		
		return list;
	}

	
	@SuppressWarnings({ "unchecked", })
	@Override
	public List<ApproveVo> getApproveInfo(String pname,String pincident) {
		String url = ContractReviewUtil.url;
		String sql = "select a.process,a.incidentno,"
				+ " a.dept,a.dept_id deptId,a.username,a.stepname,a.userfullname,"
				+ " a.remark,to_char(a.upddate,'yyyy-mm-dd hh24:mi:ss') upddate,"
				+ " case when a.file_group_id is null then a.file_group_id "
				+ " else ? || a.file_group_id end approveAttach"
				+ " from t_approvedinfo  a where a.process=? and a.incidentno=?";
		List<ApproveVo> list = DbUtil.getJdbcTemplate("stptinc").
				query(sql,new Object[]{url,pname,pincident},new RowMapper() {
					@Override
					public Object mapRow(ResultSet rs, int arg1)  throws SQLException{
						 ApproveVo vo = new ApproveVo();   
						 
		                    vo.setApproveAttach(rs.getString("approveAttach")==null?"":rs.getString("approveAttach"));
		                    vo.setDept(rs.getString("dept"));
		                    vo.setDeptId(rs.getString("deptId"));
		                    vo.setIncidentno(rs.getInt("incidentno")+"");
		                    vo.setProcess(rs.getString("process"));
		                    vo.setRemark(rs.getString("remark")==null?"":rs.getString("remark"));
		                    vo.setStepname(rs.getString("stepname"));
		                    vo.setUpddate(rs.getString("upddate"));
		                    vo.setUserfullname(rs.getString("userfullname"));;
		                    vo.setUsername(rs.getString("username"));
						 	 
	                    return vo;   
					}
					
				});
		
		return list;
	}

	
	@Override
	public DiscardVo getMainBo(String pname, String pincident) {
		DiscardVo vo = null;
		DiscardBo bo  = this.dao.getMainBo(pname, pincident);
		if(bo != null){
			vo = new DiscardVo();
			BeanUtils.copyProperties(bo, vo);
		}
		return vo;
	}
}
