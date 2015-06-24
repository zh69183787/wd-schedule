package com.wonders.task.htxx.model.bo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "DW_CONTRACT_P_PRICE_NUMBER")
public class DwContractPriceNumber {
	private String id;
    private String controlDate;
    private BigDecimal contractType;
    private String companyId;
    private String companyName;
    private String createDate;
    private BigDecimal contractNum;
    private BigDecimal contractPrice;
    private BigDecimal dataType;
    
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

    @Column(name="CONTROL_DATE", length=40)

    public String getControlDate() {
        return this.controlDate;
    }
    
    public void setControlDate(String controlDate) {
        this.controlDate = controlDate;
    }

    @Column(name="CONTRACT_TYPE", precision=22, scale=0)

    public BigDecimal getContractType() {
        return this.contractType;
    }
    
    public void setContractType(BigDecimal contractType) {
        this.contractType = contractType;
    }

    @Column(name="COMPANY_ID", length=40)

    public String getCompanyId() {
        return this.companyId;
    }
    
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Column(name="COMPANY_NAME", length=400)

    public String getCompanyName() {
        return this.companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Column(name="CREATE_DATE")

    public String getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Column(name="CONTRACT_NUM", precision=22, scale=0)

    public BigDecimal getContractNum() {
        return this.contractNum;
    }
    
    public void setContractNum(BigDecimal contractNum) {
        this.contractNum = contractNum;
    }

    @Column(name="CONTRACT_PRICE", precision=22, scale=0)

    public BigDecimal getContractPrice() {
        return this.contractPrice;
    }
    
    public void setContractPrice(BigDecimal contractPrice) {
        this.contractPrice = contractPrice;
    }

    @Column(name="DATA_TYPE", precision=22, scale=0)

    public BigDecimal getDataType() {
        return this.dataType;
    }
    
    public void setDataType(BigDecimal dataType) {
        this.dataType = dataType;
    }
}
