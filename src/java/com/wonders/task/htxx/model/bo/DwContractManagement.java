package com.wonders.task.htxx.model.bo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "DW_CONTRACT_P_MANAGEMENT")
public class DwContractManagement {
	private String id;
    private String name;
    private BigDecimal value;
    private String createDate;
    private BigDecimal orderNo;
    private String url;
    
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

    @Column(name="NAME", length=200)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    @Column(name="VALUE", precision=22, scale=0)

    public BigDecimal getValue() {
        return this.value;
    }
    
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Column(name="CREATE_DATE")

    public String getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Column(name="ORDER_NO", precision=22, scale=0)

    public BigDecimal getOrderNo() {
        return this.orderNo;
    }
    
    public void setOrderNo(BigDecimal orderNo) {
        this.orderNo = orderNo;
    }

    @Column(name="URL", length=400)

    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
}
