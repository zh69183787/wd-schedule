/**
 * 
 */
package com.wonders.task.sample.bo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;


/** 
 * @ClassName: Clazz 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年2月27日 上午11:48:47 
 *  
 */
@Entity 
@Table(name="Z_CLASS") 
public class Clazz implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6224738252966513441L;
	
	private int version;
	private String id;
	private String name;
	private String content;
	private Set<Student> set;
	private Teacher teacher;
	
	 @Version
	    @Column(name = "version")
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
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
//
//	@OneToMany(cascade=CascadeType.ALL,mappedBy="clazz")
//	//@OneToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH},mappedBy="clazz")
//	public Set<Student> getSet() {
//		return set;
//	}
//	public void setSet(Set<Student> set) {
//		this.set = set;
//	}
//
//	@OneToOne(cascade=CascadeType.ALL,mappedBy="clazz", fetch=FetchType.LAZY)
//	public Teacher getTeacher() {
//		return teacher;
//	}
//	public void setTeacher(Teacher teacher) {
//		this.teacher = teacher;
//	}

	@Lob
    @Basic(fetch=FetchType.LAZY)
    @Column(name = "CONTENT",columnDefinition = "CLOB")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
