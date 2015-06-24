package com.wonders.task.htxx.model.bo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "DW_CONTRACT_P_ASSIGN_STATS")
public class DwContractAssignStats {
	private String id;
    private BigDecimal contractType;
    private BigDecimal assignType;
    private String name1;
    private BigDecimal value1;
    private String name2;
    private BigDecimal value2;
    private String controlDate;
    private Date createDate;
    private String controlCompany;
    private String controlCompanyId;
    
    @Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name="ID", nullable=false, length=40)
    
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    @Column(name="CONTRACT_TYPE", precision=22, scale=0)

    public BigDecimal getContractType() {
        return this.contractType;
    }
    
    public void setContractType(BigDecimal contractType) {
        this.contractType = contractType;
    }

    @Column(name="ASSIGN_TYPE", precision=22, scale=0)

    public BigDecimal getAssignType() {
        return this.assignType;
    }
    
    public void setAssignType(BigDecimal assignType) {
        this.assignType = assignType;
    }

    @Column(name="NAME_1", length=200)

    public String getName1() {
        return this.name1;
    }
    
    public void setName1(String name1) {
        this.name1 = name1;
    }

    @Column(name="VALUE_1", precision=22, scale=0)

    public BigDecimal getValue1() {
        return this.value1;
    }
    
    public void setValue1(BigDecimal value1) {
        this.value1 = value1;
    }

    @Column(name="NAME_2", length=200)

    public String getName2() {
        return this.name2;
    }
    
    public void setName2(String name2) {
        this.name2 = name2;
    }

    @Column(name="VALUE_2", precision=22, scale=0)

    public BigDecimal getValue2() {
        return this.value2;
    }
    
    public void setValue2(BigDecimal value2) {
        this.value2 = value2;
    }

    @Column(name="CONTROL_DATE", length=40)

    public String getControlDate() {
        return this.controlDate;
    }
    
    public void setControlDate(String controlDate) {
        this.controlDate = controlDate;
    }

    @Column(name="CREATE_DATE")

    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name="CONTROL_COMPANY", length=100)

    public String getControlCompany() {
        return this.controlCompany;
    }
    
    public void setControlCompany(String controlCompany) {
        this.controlCompany = controlCompany;
    }
    
    @Column(name="CONTROL_COMPANY_ID", length=40)

    public String getControlCompanyId() {
        return this.controlCompanyId;
    }
    
    public void setControlCompanyId(String controlCompanyId) {
        this.controlCompanyId = controlCompanyId;
    }
}
