/**
 * 
 */
package com.wonders.task.excel.main;

import com.wonders.schedule.util.SpringBeanUtil;
import com.wonders.task.excel.model.SendBo;
import com.wonders.task.excel.model.ZDocsFile;
import com.wonders.task.excel.model.ZDocsRight;
import com.wonders.task.excel.service.ExcelService;
import com.wonders.task.sample.ITaskService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author dasdasdsadsa
 */

@Transactional(value = "txManager2",propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
@Service("sendMain")
public class SendMain implements ITaskService{
    //private static Logger log = Logger.getLogger(getClass().getName());

    private final Log log = LogFactory.getLog(getClass());

	private  ExcelService service;

    /**
     * @return the service
     */
    ExcelService getService() {
        return service;
    }

    /**
     * @param service the service to set
     * @author zhaaaaaaaaaaaaaaaaaaaoushun
     */
    @Autowired(required=false)
    public void setService(@Qualifier("excelService")ExcelService service) {
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
        //error
        List<SendBo> error = new ArrayList<SendBo>();
        //导入老数据
        if(false) {
            //找到已有的文件字号发文
//            //老流程
//            String existSql = "select t.send_id from t_doc_send t,\n" +
//                    " incidents i where t.removed=0 and t.modelid in ('新发文流程','发文流程')\n" +
//                    " and i.processname=t.modelid and i.incident=t.pinstanceid and i.status=2 and t.flag in (1,2)\n" +
//                    " and t.pinstanceid is not null";
//            Map<String,String> existCode = this.getService().getMapInfo(existSql,"SEND_ID","SEND_ID");
//            List<SendBo> result = PoiUtil.readExcel2007();
//            for (Iterator it = result.iterator(); it.hasNext();) {
//                SendBo bo = (SendBo)it.next();
//                if(existCode.containsKey(bo.getSendId())){
//                    it.remove();
//                }else {
//                    if(this.judgeNotNull(bo, new String[]{"code1", "code2", "code3", "sendDate",
//                            "sendId",  "operator", "removed", "sendDept",
//                            "title"})) {
//                        error.add(bo);
//                    }
//                }
//            }
//
//            System.out.println(result.size());
//
//            //错误判断 ，解析失败 停止
//            if (error.size() > 0) {
//                log.debug("--------------------error info--------------------------------");
//                for (SendBo bo : error) {
//
//                    log.debug(bo.getSendId() + "-" + bo.getTitle() + "error info = " + bo.getError());
//
//                }
//                log.debug("--------------------error info--------------------------------");
//                 return "0";
//            }
//
//            this.getService().saveAll(result);
            String excelDeptSql = "select t.new_dept_name,t.new_dept_id from z_dept_send t where t.removed=0";
            //导入的发文流程
            String excelSendSql = "select t.modelid process, t.pinstanceid incident, t.doc_title title, t.id id, t.send_id sendId, " +
                    " t.send_user sendUser,t.send_userdept sendDept,\n" +
                    " t.send_main_id sendMainId,t.send_inside_id sendInsideId,t.send_report_id sendReportId,\n" +
                    " t.content_att_main attach,t.rowid from t_doc_send t where t.pinstanceid='-1'";
            String deptDocSql = "select t.dept_id,t.sid from z_docs_catalog t where t.state=1 and t.flag ='000' and t.parent_sid is null";
            //excel 导入 发起部门Map：key 部门名称 value 新部门ID
            Map<String, String> excelDeptMap = this.getService().getMapInfo(excelDeptSql, "NEW_DEPT_NAME", "NEW_DEPT_ID");
            //部门文件柜Map key：新部门ID value 目录主键
            Map<String, String> deptDocMap = this.getService().getMapInfo(deptDocSql, "DEPT_ID", "SID");
            List<SendBo> data = this.service.getBoInfo(excelSendSql, SendBo.class);
            //结果数据
            List<ZDocsFile> result = new ArrayList<ZDocsFile>();
            for(SendBo bo : data){
                ZDocsFile fileBo = new ZDocsFile();
                fileBo.setCreateUser("ADMIN_SEND");
                fileBo.setCreateUserName("ADMIN_SEND");
                fileBo.setUpdateUser("ADMIN_SEND");
                fileBo.setUpdateUserName("ADMIN_SEND");
                fileBo.setParentSid(deptDocMap.get(excelDeptMap.get(bo.getSendDept())));
                fileBo.setFileName(bo.getSendId() + " " + bo.getTitle());
                fileBo.setRefId(bo.getId());
                fileBo.setFilePath("");
                result.add(fileBo);
            }

            this.getService().saveAll(result);
            return "";
        }else{
            log.debug("-----------------------start-----------------------------------------");
            String sendDeptSql = "select t.old_dept_name,t.new_dept_id from z_dept_send t where t.removed=0";
            String recvDeptSql = "select t.old_dept_id,t.new_dept_id from z_dept_recv t where t.removed=0";
            String deptDocSql = "select t.dept_id,t.sid from z_docs_catalog t where t.state=1 and t.flag ='000' and t.parent_sid is null";
            String docSendSql = "select distinct f.ref_id,c.sid from z_docs_catalog c ,z_docs_file f\n" +
                    " where c.sid = f.parent_sid and c.flag='000' and c.state=1 and f.state=1\n" +
                    " and f.flag ='003' and f.link_flag='3' ";
            String deptCodeSql = "select t.new_dept_id deptId ,t.id id  from t_dept_code t where t.removed = 0";
           //发起部门Map：key 部门名称 value 新部门ID
            Map<String, String> sendDeptMap = this.getService().getMapInfo(sendDeptSql, "OLD_DEPT_NAME", "NEW_DEPT_ID");
            //接收部门Map：key 老部门ID value 新部门ID
            Map<String, String> recvDeptMap = this.getService().getMapInfo(recvDeptSql, "OLD_DEPT_ID", "NEW_DEPT_ID");
            //部门文件柜Map key：新部门ID value 目录主键
            Map<String, String> deptDocMap = this.getService().getMapInfo(deptDocSql, "DEPT_ID", "SID");
            //各部门所有文件：Map key：各部门文件柜目录主键，value List 文件记录中的发文主键
            Map<String, List<String>> docSendMap = this.getService().getMapListInfo(docSendSql, "SID", "REF_ID", new Object[]{});
            //部门文件号
            Map<String, String> deptCodeMap = this.getService().getMapInfo(deptCodeSql, "DEPTID", "ID");

            //老流程
            String sendSql = "select t.modelid process, t.pinstanceid incident, t.doc_title title, t.id id, t.send_id sendId, " +
                    " t.send_user sendUser,t.send_userdept sendDept,\n" +
                    " t.send_main_id sendMainId,t.send_inside_id sendInsideId,t.send_report_id sendReportId,\n" +
                    " t.content_att_main attach,t.rowid from t_doc_send t,\n" +
                    " incidents i where t.removed=0 and t.modelid in ('发文流程')\n" +
                    " and i.processname=t.modelid and i.incident=t.pinstanceid and i.status=2 and t.flag in (1,2)\n" +
                    " and t.pinstanceid is not null";
            //新流程
            String newSendSql = "select t.modelid process, t.pinstanceid incident,t.doc_title title, t.id, t.send_id sendId, " +
                    " t.send_user sendUser,t.send_userdept sendDept,\n" +
                    " t.send_main_id sendMainId,t.send_inside_id sendInsideId,t.send_report_id sendReportId,\n" +
                    " t.content_att_main attach,t.rowid from t_doc_send t,\n" +
                    " incidents i where t.removed=0 and t.modelid in ('新发文流程')\n" +
                    " and i.processname=t.modelid and i.incident=t.pinstanceid and i.status=2 and t.flag in (1,2)\n" +
                    " and t.pinstanceid is not null";

            //流程流转的人
            String processSql = "select distinct a.dept_id deptId,a.username loginName " +
                    " from t_approvedinfo a where a.process = ? and a.incidentno = ? and a.dept_id is not null";

            //老数据
            List<SendBo> data = this.service.getBoInfo(sendSql + " union " + newSendSql, SendBo.class);
            //新数据
            //List<SendBo> newData = this.service.getBoInfo(newSendSql, SendBo.class);

            //结果数据
            List<ZDocsFile> result = new ArrayList<ZDocsFile>();

            //key 发文主键ID value 部门列表及人员姓名
            Map<SendBo, Map<String, List<String>>> resultMap = new HashMap<SendBo, Map<String, List<String>>>();


            //解析send表 构成Map
            for (SendBo bo : data) {
                //必要字段为空 则加入错误列表
                if (judgeNull(bo, new String[]{"code1", "code2", "code3", "sendDate",
                        "error", "sendMain", "operator", "removed",
                        "normative", "sendMainId", "sendInsideId", "sendReportId"})) {
                    setErrorInfo(error, bo, "必要字段为空！");
                }
                //正常参数
                else {
                    Map<String, List<String>> deptMap = new HashMap<String, List<String>>();
                    //拟稿部门
                    if (StringUtils.isNotEmpty(sendDeptMap.get(bo.getSendDept()))) {
                        deptMap.put(sendDeptMap.get(bo.getSendDept()), new ArrayList<String>());
                        resultMap.put(bo, deptMap);
                    } else {
                        setErrorInfo(error, bo, "拟稿部门无法匹配！");
                        continue;
                    }
                    //接收部门
                    if ("新发文流程".equals(bo.getProcess())) {
                        linkDeptId(null, bo.getSendMainId() + "," + bo.getSendInsideId() + "," + bo.getSendReportId(), deptMap);
                    } else {
                        linkDeptId(recvDeptMap, bo.getSendMainId() + "," + bo.getSendInsideId() + "," + bo.getSendReportId(), deptMap);
                    }

                    //经办部门及人员
                    //查找该流程流转部门与经办人
                    Map<String, List<String>> processMap = this.getService().getMapListInfo(processSql, "DEPTID", "LOGINNAME",
                            new Object[]{bo.getProcess(), bo.getIncident()});
                    if ("新发文流程".equals(bo.getProcess())) {
                        deptMap.putAll(processMap);
                    } else {
                        setRight(deptMap, processMap, recvDeptMap);
                    }


                }
            }


            //错误判断 ，解析失败 停止
            if (error.size() > 0) {
                log.debug("--------------------error info--------------------------------");
                for (SendBo bo : error) {

                    log.debug(bo.getSendId() + "-" + bo.getTitle() + "error info = " + bo.getError());

                }
                log.debug("--------------------error info--------------------------------");
                 return "0";
            }

            //解析数据位 文件柜 格式
            log.debug("--------------------send info--------------------------------");
            SendBo sendBo;
            String url;
            String deptId;
            String catalogId;
            Map<String, List<String>> deptMap;
            for (Map.Entry<SendBo, Map<String, List<String>>> entry : resultMap.entrySet()) {
                //log.debug(entry.getKey().getSendId());
                sendBo = entry.getKey();
                deptMap = entry.getValue();
                url = "/attach/loadFileList.action?" +
                        "attachMemo=fawen_att_dic&procType=view&fileGroup=contentAttMain&" +
                        "fileGroupName=contentAttMainGroup&fileGroupId=" + sendBo.getAttach();
                for (Map.Entry<String, List<String>> map : deptMap.entrySet()) {
                    deptId = map.getKey();
                    catalogId = deptDocMap.get(deptId);
                    if (deptId != null && deptId.length() > 0 && catalogId != null
                            && (docSendMap.get(catalogId) == null
                            || !docSendMap.get(catalogId).contains(sendBo.getId()))) {
                        //文件柜
                        ZDocsFile fileBo = new ZDocsFile();
                        fileBo.setCreateUser("ADMIN_SEND");
                        fileBo.setCreateUserName("ADMIN_SEND");
                        fileBo.setUpdateUser("ADMIN_SEND");
                        fileBo.setUpdateUserName("ADMIN_SEND");
                        fileBo.setParentSid(deptDocMap.get(deptId));
                        fileBo.setFileName(sendBo.getSendId() + " " + sendBo.getTitle());
                        fileBo.setRefId(sendBo.getId());
                        if ("新发文流程".equals(sendBo.getProcess())) {
                            fileBo.setFilePath("http://10.1.48.101:8080/workflow" + url + "&listType=2" + "&codeId=" + deptCodeMap.get(deptId));
                        } else {
                            fileBo.setFilePath("http://10.1.44.18" + url + "&listType=0");
                        }
                        //权限
                        List<ZDocsRight> rights = new ArrayList<ZDocsRight>();
                        List<String> person = map.getValue();
                        for (String o : person) {
                            ZDocsRight docRight = new ZDocsRight();
                            docRight.setUptuser("ADMIN_SEND");
                            docRight.setEmpid(o);
                            docRight.setDocFile(fileBo);
                            rights.add(docRight);
                        }
                        fileBo.setDocRight(rights);
                        result.add(fileBo);
                    }
                }


            }
            log.debug("--------------------send info--------------------------------");
            this.getService().saveAll(result);

            log.debug("-----------------------end-----------------------------------------");
            return "";
        }
	}

    /**
     * 设置文件权限
     * @param deptMap
     * @param processMap
     * @param recvDeptMap
     */
    private void setRight(Map<String,List<String>> deptMap,Map<String,List<String>> processMap,Map<String,String> recvDeptMap){
        String deptId;
        List<String> list;
        for(Map.Entry<String,List<String>> pEntry : processMap.entrySet()){
            deptId = recvDeptMap.get(pEntry.getKey());
            list = new ArrayList<String>();
            if(deptMap.containsKey(deptId)){
                for(String s:pEntry.getValue()){
                    list.add(s+deptId);
                }
                deptMap.get(deptId).addAll(list);
            }else{
                for(String s:pEntry.getValue()){
                    list.add(s+deptId);
                }
                deptMap.put(deptId,list);
            }

        }
    }

    /**
     * 拼接主送部门抄送部门内发部门
     * @param deptIds
     * @param deptMap
     */
    private void linkDeptId(Map<String,String> recvDeptMap,String deptIds,Map<String,List<String>> deptMap){
        String deptId;
        if(deptIds != null && deptIds.split(",").length > 0){
            for(String s : deptIds.split(",")){
                if(recvDeptMap == null){
                    deptId = s;
                }else{
                    deptId = recvDeptMap.get(s);
                }
                if(deptId!=null && deptId.length() > 0 && !"null".equals(deptId) && !deptMap.containsKey(deptId)){
                    deptMap.put(deptId, new ArrayList<String>());
                }
            }
        }
    }

    /**
     * 设置错误信息
     * @param error
     * @param bo
     * @param info
     */
    private void setErrorInfo(List<SendBo> error, SendBo bo, String info){
        bo.setError(info);
        error.add(bo);
    }

    /**
     * 判断必要变量是否为空
     * @param obj
     * @param ignoreProperties
     * @return
     */
    private boolean judgeNull(Object obj,String[] ignoreProperties){
        boolean flag = false;
        List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;
        Field[] fields = obj.getClass().getDeclaredFields();
        String varName = null;
        for (Field field : fields) {
            varName = field.getName();
            if (ignoreProperties == null || !ignoreList.contains(varName)) {
                boolean accessFlag = field.isAccessible();
                field.setAccessible(true);
                Object res;
                try {
                    res = field.get(obj);
                    if (res == null || res.toString().length() == 0) {
                        flag = true;
                        break;
                    }
                } catch (Exception ignored) {
                }
                field.setAccessible(accessFlag);
            }

        }
        return flag;
    }

    private boolean judgeNotNull(Object obj,String[] properties){
        boolean flag = false;
        List<String> pList = (properties != null) ? Arrays.asList(properties) : null;
        Field[] fields = obj.getClass().getDeclaredFields();
        String varName = null;
        for (Field field : fields) {
            varName = field.getName();
            if (properties != null && pList.contains(varName)) {
                boolean accessFlag = field.isAccessible();
                field.setAccessible(true);
                Object res;
                try {
                    res = field.get(obj);
                    if (res == null || res.toString().length() == 0) {
                        flag = true;
                        break;
                    }
                } catch (Exception ignored) {
                }
                field.setAccessible(accessFlag);
            }

        }
        return flag;
    }
	
	
	public static void main(String[] args) {
        ApplicationContext applicationContext = null;
        String[] fileUrl = new String[]{"classpath*:*Context*.xml"};
        applicationContext = new ClassPathXmlApplicationContext(fileUrl);
        ITaskService task = (ITaskService) SpringBeanUtil.getBean("sendMain");
        task.exec("");
        System.out.println("-----------------------------------------end-----------------------------");
    }

    public static void foo(List<? super Number> l){

        l.add(new Integer(2));  // 编译通过么？ Why ?

    }
}
