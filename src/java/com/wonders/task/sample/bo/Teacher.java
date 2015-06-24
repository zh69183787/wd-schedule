/**   
* @Title: Teacher.java 
* @Package com.wonders.task.sample.bo 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhoushun   
* @date 2014年4月1日 上午9:08:19 
* @version V1.0   
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

/** 
 * @ClassName: Teacher 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年4月1日 上午9:08:19 
 *  
 */

@Entity 
@Table(name="Z_TEACHER") 
public class Teacher {
	private String id;
	private String name;
	private Clazz clazz;
	
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
	
 	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  	@JoinColumn(name="class_id")  
	public Clazz getClazz() {
		return clazz;
	}
	
	
	public void setClazz(Clazz clazz) {
		this.clazz = clazz;
	}
	
	
	
}
