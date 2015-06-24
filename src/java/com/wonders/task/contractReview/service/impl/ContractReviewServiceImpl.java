/**
 *
 */
package com.wonders.task.contractReview.service.impl;

import com.wonders.schedule.util.DbUtil;
import com.wonders.task.contractReview.model.bo.PAttach;
import com.wonders.task.contractReview.model.bo.PContract;
import com.wonders.task.contractReview.model.vo.CompanyRoute;
import com.wonders.task.contractReview.service.ContractReviewService;
import com.wonders.task.contractReview.util.ContractReviewUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoushun
 * @ClassName: ContractReviewServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2014年1月21日 下午12:06:54
 */

@Service("contractReviewService")
@Transactional(value = "dsTransactionManager2", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ContractReviewServiceImpl implements ContractReviewService {


    @Override
    public Map<String, CompanyRoute> getLine() {
        String sql = "select object_name lineName,id lineId,unitName,unitId" +
                "  from C_COMPANY_ROUTE c" +
                "  left join (select s.object_name unitName, s.id unitId" +
                "               from C_COMPANY_ROUTE s" +
                "              where s.id <> '01'" +
                "                and s.removed = '0' and s.type='1') on unitId = c.pid" +
                " where c.pid <> '01'" +
                "   and c.removed = '0' ";

        List<CompanyRoute> companyRouteList = DbUtil.getJdbcTemplate("stptdemo").query(sql, new BeanPropertyRowMapper(CompanyRoute.class));
        HashMap<String, CompanyRoute> result = new HashMap<String, CompanyRoute>();
        for (CompanyRoute companyRoute : companyRouteList) {
            if (companyRoute.getLineName().equals("8号线三期")&&companyRoute.getUnitId().equals("113")) {
                    result.put(companyRoute.getLineName(), companyRoute);

            } else if (companyRoute.getLineName().equals("5号线南延伸")&&companyRoute.getUnitId().equals("112")) {
                    result.put(companyRoute.getLineName(), companyRoute);

            }else if (companyRoute.getLineName().equals("14号线")&&companyRoute.getUnitId().equals("114")) {
                    result.put(companyRoute.getLineName(), companyRoute);

            }else if(!companyRoute.getLineName().equals("8号线三期")&&!companyRoute.getLineName().equals("5号线南延伸")&&!companyRoute.getLineName().equals("14号线"))
                result.put(companyRoute.getLineName(), companyRoute);
        }

        return result;
    }

    @Override
    public Map<String, CompanyRoute> getCompany() {
        String sql = "select id unitId,object_name unitName from C_COMPANY_ROUTE where type=? and removed = ?";
        List<CompanyRoute> companyRouteList = DbUtil.getJdbcTemplate("stptdemo").query(sql, new Object[]{"1", "0"}, new BeanPropertyRowMapper(CompanyRoute.class));
        HashMap<String, CompanyRoute> result = new HashMap<String, CompanyRoute>();
        for (CompanyRoute companyRoute : companyRouteList) {
            result.put(companyRoute.getUnitName(), companyRoute);
        }

        return result;
    }

    @Override
    public Map getCorporation() {
        String sql = "select company_name_chn,max(comp_id) comp_id from ps_corporation group by company_name_chn";
        List<Map<String,Object>> result = DbUtil.getJdbcTemplate("stptdemo").queryForList(sql);
        HashMap<String, String> mapResult = new HashMap<String, String>();
        for (Map<String,Object> map : result) {
               mapResult.put((String)map.get("company_name_chn"), ((BigDecimal)map.get("comp_id")).intValue()+"");
        }

        return mapResult;
    }

    @Override
    public Map<String, String> getDept() {
        String sql = "select name,max(id) id from c_dept_tree group by name";
        List<Map<String,Object>> result = DbUtil.getJdbcTemplate("stptdemo").queryForList(sql);
        HashMap<String, String> mapResult = new HashMap<String, String>();
        for (Map<String,Object> map : result) {
            mapResult.put((String)map.get("name"), ((String)map.get("id")));
        }

        return mapResult;
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<PContract> getInfo() {
        String sql = "select t.project_id projectId,"
                + " t.contract_type1_id||','||t.contract_type2_id contractType,"
                + " t.contract_group_id contractGrouping,"
                + " t.purchase_type_id inviteBidType,"
                + " t.project_name projectName,"
                + " t.project_identifier projectNo,"
                + " t.contract_identifier contractNo,"
                + " t.contract_self_num selfNo,"
                + " t.contract_name contractName,"
                + " t.contract_money contractPrice,"
                + " t.contract_money_type payType,"
                + " t.opposite_company buildSupplierName,"
                + " t.reg_person registerPersonName,"
                + " replace(t.reg_login_name,'ST/','') registerPersonLoginName,"
                + " t.reg_time createDate,"
                + " t.pass_time contractPassedDate,"
                + " t.sign_time contractSignedDate,"
                + " t.exec_period_start contractStartDate,"
                + " t.exec_period_end contractEndDate,"
                + " t.remark remark,"
                + " t.attach contractAttachMent,"
                + " t.project_charge_dept contractOwnerExecuteName,"
                + " t.money_source contractOwnerName,"
                + " t.budget_type budgetType,"
                + " t.budget_type_code budgetTypeCode,"
                + " t.stat_type statType"
                + " from t_contract_review t "
                + " where t.operate_time < to_char(sysdate,'yyyy-mm-dd')"
                + " and t.operate_time >= to_char(sysdate-1,'yyyy-mm-dd')"
                + " and t.removed=0 and t.flag = '1'";
        List<PContract> list = DbUtil.getJdbcTemplate("stptinc").
                query(sql, new BeanPropertyRowMapper(PContract.class));
        return list;
    }

    public List<PAttach> getAttachInfo(String groupId) {
        String url = ContractReviewUtil.url;
        String sql = "select "
                + " t.filename,"
                + " t.fileextname,"
                + " t.filesize,"
                + " case when instr(t.path,'http')>0 then path else ? || id end path,"
                + " '' savefilename,"
                + " t.operate_time operateTime, "
                + " t.uploaddate,t.uploader,"
                + " t.uploader_login_name uploaderLoginName  from t_attach t "
                + " where t.groupid=? and t.removed =0 ";
        @SuppressWarnings({"unchecked", "rawtypes"})
        List<PAttach> list = DbUtil.getJdbcTemplate("stptinc").
                query(sql, new Object[]{url, groupId}, new BeanPropertyRowMapper(PAttach.class));
        return list;

    }
}
