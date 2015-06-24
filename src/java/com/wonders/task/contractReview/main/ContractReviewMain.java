/**
 *
 */
package com.wonders.task.contractReview.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.wonders.task.contractReview.model.vo.CompanyRoute;
import com.wonders.task.workflowExternal.util.BeanUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.schedule.util.SpringBeanUtil;
import com.wonders.task.contractReview.model.bo.PAttach;
import com.wonders.task.contractReview.model.bo.PContract;
import com.wonders.task.contractReview.service.ContractReviewService;
import com.wonders.task.contractReview.service.PContractService;
import com.wonders.task.sample.ITaskService;
import com.wonders.task.todoItem.util.StringUtil;

/**
 * @author zhoushun
 * @ClassName: ContractReviewMain
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2014年1月21日 下午12:06:35
 */
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service("contractReviewMain")
public class ContractReviewMain implements ITaskService {
    private PContractService pcService;
    private ContractReviewService service;

    public ContractReviewService getService() {
        return service;
    }

    @Autowired(required = false)
    public void setService(@Qualifier("contractReviewService") ContractReviewService service) {
        this.service = service;
    }

    public PContractService getPcService() {
        return pcService;
    }

    @Autowired(required = false)
    public void setPcService(@Qualifier("pContractService") PContractService pcService) {
        this.pcService = pcService;
    }

    /**
     * @param @param  param
     * @param @return 设定文件
     * @throws
     * @Title: exec
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */
    @Override
    public String exec(String param) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<PContract> list = this.service.getInfo();
        Map<String, CompanyRoute> lineMap = this.service.getLine();
        Map<String, CompanyRoute> companyMap = this.service.getCompany();

        Map<String, String> corporationMap = this.service.getCorporation();
        Map<String, String> deptMap = this.service.getDept();

        List newList = new ArrayList();

        for (PContract bo : list) {
            String attach = "";
            String groupId = StringUtil.getNotNullValueString(bo.getContractAttachMent());
            List<PAttach> attachList = this.service.getAttachInfo(groupId);
            bo.setUpdateDate(sdf.format(new Date()));
            String moneySource = bo.getContractOwnerName();

            for (PAttach a : attachList) {
                this.pcService.save(a);
                attach += (a.getId() + ",");
            }
            bo.setContractAttachMent(attach.length() == 0 ? null : attach.substring(0, attach.length() - 1));

            //成本内
            if ("1".equals(bo.getContractGrouping())) {
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                JSONObject companyInfoJson = new JSONObject();
                if (companyMap.containsKey(bo.getContractOwnerExecuteName()))
                    companyInfoJson.put("unitId", (companyMap.get(bo.getContractOwnerExecuteName()).getUnitId()));
                else
                    companyInfoJson.put("unitId", "");
                companyInfoJson.put("unitName", bo.getContractOwnerExecuteName());
                companyInfoJson.put("lineName", "");
                companyInfoJson.put("money", bo.getContractPrice());
                jsonArray.add(companyInfoJson);
                jsonObject.put("moneySource", jsonArray);
                moneySource = jsonObject.toString();
            } else {
                try {
                    moneySource = changeJsonData(moneySource, lineMap);
                } catch (Exception e) {
//                    continue;
                }

            }
            bo.setContractOwnerName(moneySource);
            bo.setBuildSupplierId(corporationMap.get(bo.getBuildSupplierName()));
            bo.setContractOwnerExecuteId(deptMap.get(bo.getContractOwnerExecuteName()));


            List<PContract> contracts = pcService.getContract(bo.getSelfNo());
            if(contracts != null && contracts.size() > 0){
                PContract pContract = contracts.get(0);

                BeanUtils.copyProperties(pContract,bo,new String[]{"projectId","contractType","contractGrouping",
                        "inviteBidType","projectName","projectNo","contractNo","selfNo","contractName","contractPrice",
                        "payType","buildSupplierName","registerPersonName","registerPersonLoginName","createDate",
                        "contractPassedDate","contractSignedDate","contractStartDate","contractEndDate","buildSupplierId",
                        "remark","contractAttachMent","contractOwnerExecuteName","contractOwnerExecuteId","contractOwnerName","updateDate"});
            }
        }
        this.pcService.saveBatch(list);
        return "";
    }


    @SuppressWarnings("unused")
    public static void main(String[] args) throws Exception {

        ContractReviewMain v = new ContractReviewMain();

		ApplicationContext applicationContext = null;
		String[] fileUrl = new String[]{"classpath*:*Context*.xml"};
		applicationContext = new ClassPathXmlApplicationContext(fileUrl);
		ITaskService task = (ITaskService) SpringBeanUtil.getBean("contractReviewMain");

		task.exec("");

    }

    public String changeJsonData(String strJson, Map<String, CompanyRoute> dicMap)throws Exception{
        if (StringUtils.isNotBlank(strJson)) {
            JSONObject jsonObject = JSONObject.fromObject(strJson);
            if (jsonObject.containsKey("moneySource")) {
                JSONArray jsonArray = (JSONArray) jsonObject.get("moneySource");
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject js = (JSONObject) jsonArray.get(i);
                    if (dicMap.containsKey(js.get("lineName"))) {
                        CompanyRoute companyRoute = dicMap.get(js.get("lineName"));
                        js.put("lineName", companyRoute.getLineName());
                        js.put("unitId", companyRoute.getUnitId());
                        js.put("unitName", companyRoute.getUnitName());
                    }
                }
                return jsonObject.toString();
            }

        }

        return strJson;

    }

}
