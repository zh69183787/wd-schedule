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
@Table(name = "DW_CONTRACT_P_ASSIGN_TYPE")
public class DwContractAssignType {
	private String id;
    private BigDecimal contractType;
    private BigDecimal assignType;
    private Date createDate;
    private String controlYear;
    private String controlCompany;
    private String controlCompanyId;
    private BigDecimal exe1;
    private BigDecimal exe2;
    private BigDecimal exe3;
    private BigDecimal exe4;
    private BigDecimal exe5;
    private BigDecimal exe6;
    private BigDecimal exe7;
    private BigDecimal exe8;
    private BigDecimal exe9;
    private BigDecimal exe10;
    
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

    @Column(name="CREATE_DATE")

    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name="CONTROL_YEAR", length=40)

    public String getControlYear() {
        return this.controlYear;
    }
    
    public void setControlYear(String controlYear) {
        this.controlYear = controlYear;
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

    @Column(name="EXE_1", precision=22, scale=0)

    public BigDecimal getExe1() {
        return this.exe1;
    }
    
    public void setExe1(BigDecimal exe1) {
        this.exe1 = exe1;
    }

    @Column(name="EXE_2", precision=22, scale=0)

    public BigDecimal getExe2() {
        return this.exe2;
    }
    
    public void setExe2(BigDecimal exe2) {
        this.exe2 = exe2;
    }

    @Column(name="EXE_3", precision=22, scale=0)

    public BigDecimal getExe3() {
        return this.exe3;
    }
    
    public void setExe3(BigDecimal exe3) {
        this.exe3 = exe3;
    }

    @Column(name="EXE_4", precision=22, scale=0)

    public BigDecimal getExe4() {
        return this.exe4;
    }
    
    public void setExe4(BigDecimal exe4) {
        this.exe4 = exe4;
    }

    @Column(name="EXE_5", precision=22, scale=0)

    public BigDecimal getExe5() {
        return this.exe5;
    }
    
    public void setExe5(BigDecimal exe5) {
        this.exe5 = exe5;
    }

    @Column(name="EXE_6", precision=22, scale=0)

    public BigDecimal getExe6() {
        return this.exe6;
    }
    
    public void setExe6(BigDecimal exe6) {
        this.exe6 = exe6;
    }

    @Column(name="EXE_7", precision=22, scale=0)

    public BigDecimal getExe7() {
        return this.exe7;
    }
    
    public void setExe7(BigDecimal exe7) {
        this.exe7 = exe7;
    }

    @Column(name="EXE_8", precision=22, scale=0)

    public BigDecimal getExe8() {
        return this.exe8;
    }
    
    public void setExe8(BigDecimal exe8) {
        this.exe8 = exe8;
    }

    @Column(name="EXE_9", precision=22, scale=0)

    public BigDecimal getExe9() {
        return this.exe9;
    }
    
    public void setExe9(BigDecimal exe9) {
        this.exe9 = exe9;
    }

    @Column(name="EXE_10", precision=22, scale=0)

    public BigDecimal getExe10() {
        return this.exe10;
    }
    
    public void setExe10(BigDecimal exe10) {
        this.exe10 = exe10;
    }

}
