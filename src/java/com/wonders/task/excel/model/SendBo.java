package com.wonders.task.excel.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: zhoushun
 * Date: 2014/11/21
 * Time: 11:31
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "T_DOC_SEND")
public class SendBo implements Serializable{
    private String title;
    private String id;
    private String normative;
    private String sendId;
    private String sendUser;
    private String sendDept;
    private String sendMainId;
    private String sendInsideId;
    private String sendReportId;
    private String attach;
    private String error;
    private String process;
    private String incident;
    private String sendMain;
    private Long removed;
    private String operator;
    private String code1;
    private String code2;
    private String code3;
    private String sendDate;

    @Column(name = "SEND_DATE", length = 10)
    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    @Column(name = "CODE1", length = 10)
    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    @Column(name = "CODE2", length = 10)
    public String getCode2() {
        return code2;
    }

    public void setCode2(String code2) {
        this.code2 = code2;
    }

    @Column(name = "CODE3", length = 10)
    public String getCode3() {
        return code3;
    }

    public void setCode3(String code3) {
        this.code3 = code3;
    }

    public SendBo(){
        this.removed =0L;
        this.operator="ADMIN";
        this.process = "发文流程";
        this.incident = "-1";
    }

    @Column(name = "SEND_MAIN", length = 4000)
    public String getSendMain() {
        return sendMain;
    }

    public void setSendMain(String sendMain) {
        this.sendMain = sendMain;
    }

    @Column(name = "REMOVED", length = 10,columnDefinition="NUMBER(10)")
    public Long getRemoved() {
        return removed;
    }

    public void setRemoved(Long removed) {
        this.removed = removed;
    }

    @Column(name = "OPERATOR", length = 100)
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Column(name = "DOC_TITLE", length = 200)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "ID", unique = true, nullable = false, length = 100)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "NORMATIVE", length = 1)
    public String getNormative() {
        return normative;
    }

    public void setNormative(String normative) {
        this.normative = normative;
    }

    @Column(name = "SEND_ID", length = 200)
    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    @Column(name = "SEND_USER", length = 100)
    public String getSendUser() {
        return sendUser;
    }

    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }

    @Column(name = "SEND_USERDEPT", length = 100)
    public String getSendDept() {
        return sendDept;
    }

    public void setSendDept(String sendDept) {
        this.sendDept = sendDept;
    }

    @Transient
    public String getSendMainId() {
        return sendMainId;
    }

    public void setSendMainId(String sendMainId) {
        this.sendMainId = sendMainId;
    }

    @Transient
    public String getSendInsideId() {
        return sendInsideId;
    }

    public void setSendInsideId(String sendInsideId) {
        this.sendInsideId = sendInsideId;
    }

    @Transient
    public String getSendReportId() {
        return sendReportId;
    }

    public void setSendReportId(String sendReportId) {
        this.sendReportId = sendReportId;
    }

    @Transient
    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    @Transient
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Column(name = "MODELID", length = 100)
    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    @Column(name = "PINSTANCEID", length = 100)
    public String getIncident() {
        return incident;
    }

    public void setIncident(String incident) {
        this.incident = incident;
    }

    @Override
    public int hashCode() {
        int result = 17;
    	result = 37 * result + (getProcess() == null ? 0 : getProcess().hashCode());
    	result = 37 * result + (getIncident() == null ? 0 : getIncident().hashCode());
    	return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof SendBo){
            SendBo pk=(SendBo)obj;
            if(this.process.equals(pk.process)&&this.incident.equals(pk.incident)){
                return true;
            }
        }
        return false;
    }
}
