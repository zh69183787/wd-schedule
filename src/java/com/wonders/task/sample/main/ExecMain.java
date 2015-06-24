/**
 * 
 */
package com.wonders.task.sample.main;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.schedule.util.SpringBeanUtil;
import com.wonders.task.sample.ITaskService;
import com.wonders.task.sample.bo.Clazz;
import com.wonders.task.sample.bo.Student;
import com.wonders.task.sample.bo.Teacher;

/** 
 * @ClassName: TestMain 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年2月27日 下午12:18:44 
 *  
 */

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
/* 功能模块入口点，beanid即数据库配置中的name */
@Service("execMain")
@Scope("prototype")
public class ExecMain {
	private HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	//注入hibernateTemplate
	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	private static SessionFactory sf;
	
	
	
	public static SessionFactory getSf() {
		return sf;
	}

	@Resource(name="sessionFactory")
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	public static void main(String[] args){
		ApplicationContext applicationContext = null;  
		String[] fileUrl = new String[]{"classpath*:applicationContext*.xml"};  
		applicationContext = new ClassPathXmlApplicationContext(fileUrl);  		
		ExecMain main = (ExecMain)SpringBeanUtil.getBean("execMain");
		
		//1  多方插入
//		Clazz c = new Clazz();
//		c.setName("class");
//		Student s = new Student();
//		s.setName("student");
//		s.setClazz(c);
//		main.getHibernateTemplate().save(s);
		
		//2一方插入
//		Clazz c = new Clazz();
//		c.setName("class2");
//		Student s1 = new Student();
//		s1.setName("student1");
//		Student s2 = new Student();
//		s2.setName("student2");
//		s1.setClazz(c);s2.setClazz(c);
//		Set<Student> set = new HashSet<Student>();
//		set.add(s1);set.add(s2);
//		c.setSet(set);
//		main.getHibernateTemplate().save(c);
		
		//3一方查找 lazy 就报错  因为 延迟加载 一方 fetchType 默认lazy  
		//解决方法：lazy改成eager  或者hql left join fetch
		//Clazz c = main.getHibernateTemplate().get(Clazz.class, "8a81a97c452741d701452741de480002");
//		String hql = "from Clazz c left join fetch c.set";
//		Session session = main.getHibernateTemplate().getSessionFactory().
//				openSession();
//		Clazz c = (Clazz) session.createQuery(hql).uniqueResult();
//		//Clazz c = (Clazz) session.get(Clazz.class, "8a81a97c452741d701452741de480002");
//		session.close();
//		System.out.println(c.getName());
//		System.out.println(c.getSet().size());
		
		//4多方查找 lazy 就报错  因为 延迟加载 多方 fetchType 默认 eager
		//解决方法：lazy改成eager  或者hql left join fetch
		Student s = main.getHibernateTemplate().get(Student.class, "8a81a97c452741d701452741df130003");
//		String hql = "from Student s left join fetch s.clazz where s.id = '8a81a97c452741d701452741df130003'";
//		Session session = main.getHibernateTemplate().getSessionFactory().
//				openSession();
//		Student s = (Student) session.createQuery(hql).uniqueResult();
		System.out.println(s.getClazz().getName());
	
		//5 clob
//		Clazz c = new Clazz();
//		c.setContent("111111111111111");
//		main.getHibernateTemplate().save(c);
		//Clazz c = main.getHibernateTemplate().get(Clazz.class, "8a81a97c452b585001452b58597f0002");
		//System.out.println(c.getContent());
		
		
		
	}
}
