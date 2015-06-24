/**
 * 
 */
package com.wonders.task.sample.bo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

/** 
 * @ClassName: Student 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年2月27日 上午11:48:30 
 *  
 */

@Entity 
@Table(name="Z_STUDENT") 
public class Student {
	private String id;
	private String name;
	private Clazz clazz;
	private String classId;
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name = "ID")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "NAME", length = 500)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	//@ManyToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE}) 
	@ManyToOne(cascade={CascadeType.ALL},fetch=FetchType.LAZY) 
	@JoinColumn(name="class_id")
  	//@ManyToOne(cascade = CascadeType.REFRESH, fetch=FetchType.EAGER)
  	//@ManyToOne
	//@Cascade(value=org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	//@JoinColumn(name="class_id" ,nullable = true,insertable=false,updatable=false)     //student类中对应外键的属性：classid 
	//@Where(clause = "removed=0")
	public Clazz getClazz() {
		return clazz;
	}
	public void setClazz(Clazz clazz) {
		this.clazz = clazz;
	}
	
	//@Transient
	@Column(name = "CLASS_ID", length = 50 ,nullable = true,insertable=false,updatable=false)
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	
	
}
