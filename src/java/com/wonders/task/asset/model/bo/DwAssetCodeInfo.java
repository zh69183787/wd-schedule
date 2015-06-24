package com.wonders.task.asset.model.bo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "DW_ASSET_CODE_INFO")
public class DwAssetCodeInfo implements Serializable{

		/**
	 * 
	 */
	private static final long serialVersionUID = 8334825036189361329L;

		private Date addTime; // addTime

		private String dm; // dm

		private String id; // id

		private String name; // name

		private Long removed; // removed

		private Long typeId; // typeId

		private Date updateTime; // updateTime

		private Long value; // value

		public void setAddTime(Date addTime) {
			this.addTime = addTime;
		}

		@Column(name = "ADD_TIME", nullable = true, length = 7)
		public Date getAddTime() {
			return addTime;
		}

		public void setDm(String dm) {
			this.dm = dm;
		}

		@Column(name = "DM", nullable = true, length = 199)
		public String getDm() {
			return dm;
		}

		public void setId(String id) {
			this.id = id;
		}

		@Id
		@GeneratedValue(generator="system.uuid")
		@GenericGenerator(name="system.uuid",strategy="uuid")
		@Column(name = "ID", nullable = false, length = 50)
		public String getId() {
			return id;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Column(name = "NAME", nullable = true, length = 199)
		public String getName() {
			return name;
		}

		public void setRemoved(Long removed) {
			this.removed = removed;
		}

		@Column(name = "REMOVED", nullable = true, length = 22)
		public Long getRemoved() {
			return removed;
		}

		public void setTypeId(Long typeId) {
			this.typeId = typeId;
		}

		@Column(name = "TYPE_ID", nullable = true, length = 22)
		public Long getTypeId() {
			return typeId;
		}

		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}

		@Column(name = "UPDATE_TIME", nullable = true, length = 7)
		public Date getUpdateTime() {
			return updateTime;
		}

		public void setValue(Long value) {
			this.value = value;
		}

		@Column(name = "VALUE", nullable = true, length = 22)
		public Long getValue() {
			return value;
		}

}
