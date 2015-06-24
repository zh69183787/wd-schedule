/**
 * 
 */
package com.wonders.task.sample.main;

import com.wonders.schedule.util.DbUtil;
import com.wonders.schedule.util.SpringBeanUtil;
import com.wonders.task.sample.bo.Clazz;
import com.wonders.task.sample.bo.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/** 
 * @ClassName: TestMain 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年2月27日 下午12:18:44 
 *  
 */

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
/* 功能模块入口点，beanid即数据库配置中的name */
@Service("testMain")
@Scope("prototype")
public class TestMain {
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

    public void exec(){
        Clazz t = new Clazz();
        t.setId("sss");
        t.setName("ccccccccccccccccccccc");
        t.setContent("ccccccccccccccccccccccccccccccc");

        //main.getHibernateTemplate().save(s1);
        //String hql = "from Teacher t left join fetch t.clazz";
        //List<Teacher> list = (List<Teacher>)main.getHibernateTemplate().find(hql);
        //System.out.println(list.get(0).getClazz().getName());
        //System.out.println(java.util.UUID.randomUUID().toString());
        //main.getHibernateTemplate().save(s2);
//		Clazz c2 = (Clazz) main.getHibernateTemplate().get(Clazz.class, "8a81a97c48fdb9240148fdb92e200002");
//		c2.setName("fsdfdsfdsfsdfdsfdsfdsfsdf");
//		//c2.setId("33");
//		main.getHibernateTemplate().save(c2);
        //this.getHibernateTemplate().save(t);

//        List l = this.getHibernateTemplate().find("from Clazz");
//        this.getHibernateTemplate().clear();
//        this.getHibernateTemplate().get(Clazz.class,"1");

//
//        System.out.println(this.getHibernateTemplate().find("from Clazz").size());
//        this.getHibernateTemplate().save(t);
//       System.out.println(this.getHibernateTemplate().find("from Clazz"));


//        System.out.println(this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery
//                ("select * from z_class").list().size());
//        this.getHibernateTemplate().save(t);
//        System.out.println(this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery
//                ("select * from z_class").list().size());

        Locale.setDefault(Locale.JAPANESE);//推荐用英语地区的算法
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+7"));

        Calendar c = Calendar.getInstance();
        System.out.println(c.getTime());
        System.out.println(DbUtil.getJdbcTemplate("").queryForList("select * from z_class").size());
        this.getHibernateTemplate().save(t);
        this.getHibernateTemplate().clear();
        System.out.println(DbUtil.getJdbcTemplate("").queryForList("select * from z_class").size());

        System.out.println(this.getHibernateTemplate().
                find("select distinct c from Clazz c where c.name ='ccccccccccccccccccccc'").size());

    }
	
	
	public static SessionFactory getSf() {
		return sf;
	}

	@Resource(name="sessionFactory")
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	public void test(){
        Calendar c = Calendar.getInstance();
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Clazz c2 = (Clazz) session.get(Clazz.class, "8a81a97c4471c661014471c669360002");
		//Clazz c2 = (Clazz) main.getHibernateTemplate().get(Clazz.class, "8a81a97c4471c661014471c669360002");
		Student ss = (Student)session.get(Student.class,"8a81a97c449572e201449572eace0002");
		//Hibernate.initialize(c2.getSet());
		
		System.out.println("11111111111111111111111");
		//session.close();
		//Set<Student> sss =  c2.getSet();
		System.out.println("22222222222222222");
		//this.getHibernateTemplate().delete(c2);
		this.getHibernateTemplate().delete(ss);
		Student s1 = null;
//		for(Student s: sss){
//			
//			System.out.println(s.getName());
//			s1 = s ;
//		
//		}
		
		//Clazz c3 = new Clazz();
		//c3.setName("gggg");
		//s1.setClazz(c3);
		//this.getHibernateTemplate().update(s1);
	}
	
	public static void main(String[] args) throws SQLException {
		ApplicationContext applicationContext = null;  
		String[] fileUrl = new String[]{"classpath*:applicationContext*.xml"};  
		applicationContext = new ClassPathXmlApplicationContext(fileUrl);
        TestMain main = (TestMain) SpringBeanUtil.getBean("testMain");
        main.exec();
//        Connection con = null;
//        PreparedStatement pstmt = null;
//        Statement st = null;
//        String url = "jdbc:oracle:thin:@10.1.48.13:1521:stptdemo";
//        try {
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            con = DriverManager.getConnection(url, "stptdemo", "shstpt");
//               con.setAutoCommit(false);
//            String sql = "insert into z_class t(id,name,version) values(sys_guid(),?,0) ";
//            pstmt = con.prepareStatement(sql);
//           pstmt.setString(1,"3333333333");
//            //  con.setAutoCommit(false);
//            pstmt.addBatch();
//
//            pstmt.executeBatch();
//
//            String querySql = "select count(*) count from z_class";
//            st = con.createStatement();
//            ResultSet rs = st.executeQuery(querySql);
//            if(rs.next()){
//                System.out.println(rs.getInt("count"));
//            }
//             con.commit();
//
//
//        } catch (Exception e) {
//            //  con.rollback();
//            // System.out.println("提交失败，回滚！");
//            //              con.setAutoCommit(true);
//            e.printStackTrace();
//        }finally{
//            if(pstmt!=null) pstmt.close();
//            if(con!=null) con.close();
//        }

//		Student s1 = new Student();
//		Student s2 = new Student();
//		Clazz c1 = new Clazz();
//		c1.setName("1223332班");
//		s1.setName("z1");
//		s1.setClazz(c1);
//		//s1.setClassId("111");
//		s2.setName("z2");
//		s2.setClazz(c1);
//		Set<Student> set = new HashSet<Student>();
//		Teacher t = new Teacher();
//		Clazz ct = new Clazz();
//		ct.setName("ttttttttttttttttttt");
//		//c1.setTeacher(t);
//		t.setName("tttttttttttt");
//		t.setClazz(ct);
//		ct.setTeacher(t);
//		set.add(s1);set.add(s2);
//		c1.setSet(set);


		
//		Session session = main.getHibernateTemplate().getSessionFactory().openSession();
//		Clazz c2 = (Clazz) session.get(Clazz.class, "8a81a97c4503adda014503ade14a0002");
//		
//		Session sf0 = sf.openSession();
//		Transaction tf = sf0.beginTransaction();
//		Clazz cz = new Clazz();
//		cz.setName("ggggggggggggggggggggggg");
//		sf0.save(cz);
//		tf.commit();		
//		
//		Session sf1 = sf.openSession();
//		Session sf2 = sf.openSession();
//		
//		
//		
//		Clazz cc1 = (Clazz)sf1.createQuery("from Clazz s where s.name='ggggggggggggggggggggggg'").uniqueResult();
//		Clazz cc2 = (Clazz)sf2.createQuery("from Clazz s where s.name='ggggggggggggggggggggggg'").uniqueResult();
//		
//		
//		System.out.println(cc1.getVersion() +"-"+cc2.getVersion());
//		Transaction tf1 = sf1.beginTransaction();
//		cc1.setName("dddddd");
//		//sf1.update(cc1);
//		tf1.commit();
//		sf1.close();
//		System.out.println(cc1.getVersion() +"-"+cc2.getVersion());
//		
//		Transaction tf2 = sf2.beginTransaction();
//		cc2.setName("cccccccccccc");
//		//sf2.update(cc2);
//		tf2.commit();
//		sf2.close();
//		System.out.println(cc1.getVersion() +"-"+cc2.getVersion());
//		
		
//		Clazz cc1 = (Clazz) main.getHibernateTemplate().get(Clazz.class, "8a81a97c45249ecc0145249ed3600002");
//		Clazz cc2 = (Clazz) main.getHibernateTemplate().get(Clazz.class, "8a81a97c45249ecc0145249ed3600002");
//		
//		//
//		cc1.setName("c1");
//		main.getHibernateTemplate().update(cc1);
//		System.out.println(cc1.getVersion());
////		System.out.println(cc1.getSet().size());
//		cc2.setName("c2");
//		main.getHibernateTemplate().update(cc2);
//		System.out.println(cc2.getVersion());
//		
		//		//Clazz c2 = (Clazz) main.getHibernateTemplate().get(Clazz.class, "8a81a97c4471c661014471c669360002");
		//Student ss = (Student)main.getHibernateTemplate().get(Student.class, "8a81a97c452009f501452009fd2d0002");
		//		Hibernate.initialize(c2.getSet());
//		
		//System.out.println(ss.getClazz().getName());
		//System.out.println(ss.getClassId());
//		System.out.println("11111111111111111111111");
//		session.close();
//		Set<Student> sss =  c2.getSet();
//		System.out.println("22222222222222222");
//		for(Student s: sss){
//			
//			System.out.println(s.getName());
//		}
		//main.test();
		//main.getHibernateTemplate().delete(c2);
	}
}
