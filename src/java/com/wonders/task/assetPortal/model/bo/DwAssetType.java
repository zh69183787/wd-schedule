package com.wonders.task.assetPortal.model.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "DW_ASSET_TYPE")
public class DwAssetType {
	private String id;
	private String line;
	private String station;
	private String updateTime;
	private String removed;
	private String type;
	private Long quantityType1; 
	private Long quantityType2;
	private Long quantityType3;
	private Long quantityType4;
	private Long quantityType5;
	private Long quantityType6;
	private Long quantityType7;
	private Long quantityType8;
	private Long quantityType9;
	private Long quantityType10;
	private Long quantityType11;
	private Long quantityType12;
	private Long quantityType13;
	private Long quantityType14;
	private Long quantityType15;
	private Long quantityType16;
	private Long quantityType17;
	private Long quantityType18;
	private Long quantityType19;
	private Long quantityType20;
	private Double valueType1;
	private Double valueType2;
	private Double valueType3;
	private Double valueType4;
	private Double valueType5;
	private Double valueType6;
	private Double valueType7;
	private Double valueType8;
	private Double valueType9;
	private Double valueType10;
	private Double valueType11;
	private Double valueType12;
	private Double valueType13;
	private Double valueType14;
	private Double valueType15;
	private Double valueType16;
	private Double valueType17;
	private Double valueType18;
	private Double valueType19;
	private Double valueType20;
	private String ownerCompany;
	private Double allValue;
	private Double nowValue;
	private Long allLife;
	private Long nowLife;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "ID")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "LINE", nullable = true, length = 50)
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	@Column(name = "STATION", nullable = true, length = 50)
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	@Column(name = "UPDATE_TIME", nullable = true, length = 50)
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	@Column(name = "REMOVED", nullable = true, length = 1)
	public String getRemoved() {
		return removed;
	}
	public void setRemoved(String removed) {
		this.removed = removed;
	}
	@Column(name = "TYPE", nullable = true, length = 1)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(name = "QUANTITY_TYPE1", nullable = true)
	public Long getQuantityType1() {
		return quantityType1;
	}
	public void setQuantityType1(Long quantityType1) {
		this.quantityType1 = quantityType1;
	}
	@Column(name = "QUANTITY_TYPE2", nullable = true)
	public Long getQuantityType2() {
		return quantityType2;
	}
	public void setQuantityType2(Long quantityType2) {
		this.quantityType2 = quantityType2;
	}
	@Column(name = "QUANTITY_TYPE3", nullable = true)
	public Long getQuantityType3() {
		return quantityType3;
	}
	public void setQuantityType3(Long quantityType3) {
		this.quantityType3 = quantityType3;
	}
	@Column(name = "QUANTITY_TYPE4", nullable = true)
	public Long getQuantityType4() {
		return quantityType4;
	}
	public void setQuantityType4(Long quantityType4) {
		this.quantityType4 = quantityType4;
	}
	@Column(name = "QUANTITY_TYPE5", nullable = true)
	public Long getQuantityType5() {
		return quantityType5;
	}
	public void setQuantityType5(Long quantityType5) {
		this.quantityType5 = quantityType5;
	}
	@Column(name = "QUANTITY_TYPE6", nullable = true)
	public Long getQuantityType6() {
		return quantityType6;
	}
	public void setQuantityType6(Long quantityType6) {
		this.quantityType6 = quantityType6;
	}
	@Column(name = "QUANTITY_TYPE7", nullable = true)
	public Long getQuantityType7() {
		return quantityType7;
	}
	public void setQuantityType7(Long quantityType7) {
		this.quantityType7 = quantityType7;
	}
	@Column(name = "QUANTITY_TYPE8", nullable = true)
	public Long getQuantityType8() {
		return quantityType8;
	}
	public void setQuantityType8(Long quantityType8) {
		this.quantityType8 = quantityType8;
	}
	@Column(name = "QUANTITY_TYPE9", nullable = true)
	public Long getQuantityType9() {
		return quantityType9;
	}
	public void setQuantityType9(Long quantityType9) {
		this.quantityType9 = quantityType9;
	}
	@Column(name = "QUANTITY_TYPE10", nullable = true)
	public Long getQuantityType10() {
		return quantityType10;
	}
	public void setQuantityType10(Long quantityType10) {
		this.quantityType10 = quantityType10;
	}
	@Column(name = "QUANTITY_TYPE11", nullable = true)
	public Long getQuantityType11() {
		return quantityType11;
	}
	public void setQuantityType11(Long quantityType11) {
		this.quantityType11 = quantityType11;
	}
	@Column(name = "QUANTITY_TYPE12", nullable = true)
	public Long getQuantityType12() {
		return quantityType12;
	}
	public void setQuantityType12(Long quantityType12) {
		this.quantityType12 = quantityType12;
	}
	@Column(name = "QUANTITY_TYPE13", nullable = true)
	public Long getQuantityType13() {
		return quantityType13;
	}
	public void setQuantityType13(Long quantityType13) {
		this.quantityType13 = quantityType13;
	}
	@Column(name = "QUANTITY_TYPE14", nullable = true)
	public Long getQuantityType14() {
		return quantityType14;
	}
	public void setQuantityType14(Long quantityType14) {
		this.quantityType14 = quantityType14;
	}
	@Column(name = "QUANTITY_TYPE15", nullable = true)
	public Long getQuantityType15() {
		return quantityType15;
	}
	public void setQuantityType15(Long quantityType15) {
		this.quantityType15 = quantityType15;
	}
	@Column(name = "QUANTITY_TYPE16", nullable = true)
	public Long getQuantityType16() {
		return quantityType16;
	}
	public void setQuantityType16(Long quantityType16) {
		this.quantityType16 = quantityType16;
	}
	@Column(name = "QUANTITY_TYPE17", nullable = true)
	public Long getQuantityType17() {
		return quantityType17;
	}
	public void setQuantityType17(Long quantityType17) {
		this.quantityType17 = quantityType17;
	}
	@Column(name = "QUANTITY_TYPE18", nullable = true)
	public Long getQuantityType18() {
		return quantityType18;
	}
	public void setQuantityType18(Long quantityType18) {
		this.quantityType18 = quantityType18;
	}
	@Column(name = "QUANTITY_TYPE19", nullable = true)
	public Long getQuantityType19() {
		return quantityType19;
	}
	public void setQuantityType19(Long quantityType19) {
		this.quantityType19 = quantityType19;
	}
	@Column(name = "QUANTITY_TYPE20", nullable = true)
	public Long getQuantityType20() {
		return quantityType20;
	}
	public void setQuantityType20(Long quantityType20) {
		this.quantityType20 = quantityType20;
	}
	@Column(name = "VALUE_TYPE1", nullable = true)
	public Double getValueType1() {
		return valueType1;
	}
	public void setValueType1(Double valueType1) {
		this.valueType1 = valueType1;
	}
	@Column(name = "VALUE_TYPE2", nullable = true)
	public Double getValueType2() {
		return valueType2;
	}
	public void setValueType2(Double valueType2) {
		this.valueType2 = valueType2;
	}
	@Column(name = "VALUE_TYPE3", nullable = true)
	public Double getValueType3() {
		return valueType3;
	}
	public void setValueType3(Double valueType3) {
		this.valueType3 = valueType3;
	}
	@Column(name = "VALUE_TYPE4", nullable = true)
	public Double getValueType4() {
		return valueType4;
	}
	public void setValueType4(Double valueType4) {
		this.valueType4 = valueType4;
	}
	@Column(name = "VALUE_TYPE5", nullable = true)
	public Double getValueType5() {
		return valueType5;
	}
	public void setValueType5(Double valueType5) {
		this.valueType5 = valueType5;
	}
	@Column(name = "VALUE_TYPE6", nullable = true)
	public Double getValueType6() {
		return valueType6;
	}
	public void setValueType6(Double valueType6) {
		this.valueType6 = valueType6;
	}
	@Column(name = "VALUE_TYPE7", nullable = true)
	public Double getValueType7() {
		return valueType7;
	}
	public void setValueType7(Double valueType7) {
		this.valueType7 = valueType7;
	}
	@Column(name = "VALUE_TYPE8", nullable = true)
	public Double getValueType8() {
		return valueType8;
	}
	public void setValueType8(Double valueType8) {
		this.valueType8 = valueType8;
	}
	@Column(name = "VALUE_TYPE9", nullable = true)
	public Double getValueType9() {
		return valueType9;
	}
	public void setValueType9(Double valueType9) {
		this.valueType9 = valueType9;
	}
	@Column(name = "VALUE_TYPE10", nullable = true)
	public Double getValueType10() {
		return valueType10;
	}
	public void setValueType10(Double valueType10) {
		this.valueType10 = valueType10;
	}
	@Column(name = "VALUE_TYPE11", nullable = true)
	public Double getValueType11() {
		return valueType11;
	}
	public void setValueType11(Double valueType11) {
		this.valueType11 = valueType11;
	}
	@Column(name = "VALUE_TYPE12", nullable = true)
	public Double getValueType12() {
		return valueType12;
	}
	public void setValueType12(Double valueType12) {
		this.valueType12 = valueType12;
	}
	@Column(name = "VALUE_TYPE13", nullable = true)
	public Double getValueType13() {
		return valueType13;
	}
	public void setValueType13(Double valueType13) {
		this.valueType13 = valueType13;
	}
	@Column(name = "VALUE_TYPE14", nullable = true)
	public Double getValueType14() {
		return valueType14;
	}
	public void setValueType14(Double valueType14) {
		this.valueType14 = valueType14;
	}
	@Column(name = "VALUE_TYPE15", nullable = true)
	public Double getValueType15() {
		return valueType15;
	}
	public void setValueType15(Double valueType15) {
		this.valueType15 = valueType15;
	}
	@Column(name = "VALUE_TYPE16", nullable = true)
	public Double getValueType16() {
		return valueType16;
	}
	public void setValueType16(Double valueType16) {
		this.valueType16 = valueType16;
	}
	@Column(name = "VALUE_TYPE17", nullable = true)
	public Double getValueType17() {
		return valueType17;
	}
	public void setValueType17(Double valueType17) {
		this.valueType17 = valueType17;
	}
	@Column(name = "VALUE_TYPE18", nullable = true)
	public Double getValueType18() {
		return valueType18;
	}
	public void setValueType18(Double valueType18) {
		this.valueType18 = valueType18;
	}
	@Column(name = "VALUE_TYPE19", nullable = true)
	public Double getValueType19() {
		return valueType19;
	}
	public void setValueType19(Double valueType19) {
		this.valueType19 = valueType19;
	}
	@Column(name = "VALUE_TYPE20", nullable = true)
	public Double getValueType20() {
		return valueType20;
	}
	public void setValueType20(Double valueType20) {
		this.valueType20 = valueType20;
	}
	@Column(name = "OWNER_COMPANY", nullable = true,length=50)
	public String getOwnerCompany() {
		return ownerCompany;
	}
	public void setOwnerCompany(String ownerCompany) {
		this.ownerCompany = ownerCompany;
	}
	@Column(name = "ALL_VALUE", nullable = true)
	public Double getAllValue() {
		return allValue;
	}
	public void setAllValue(Double allValue) {
		this.allValue = allValue;
	}
	@Column(name = "NOW_VALUE", nullable = true)
	public Double getNowValue() {
		return nowValue;
	}
	public void setNowValue(Double nowValue) {
		this.nowValue = nowValue;
	}
	@Column(name = "ALL_LIFE", nullable = true)
	public Long getAllLife() {
		return allLife;
	}
	public void setAllLife(Long allLife) {
		this.allLife = allLife;
	}
	@Column(name = "NOW_LIFE", nullable = true)
	public Long getNowLife() {
		return nowLife;
	}
	public void setNowLife(Long nowLife) {
		this.nowLife = nowLife;
	}
	
}
