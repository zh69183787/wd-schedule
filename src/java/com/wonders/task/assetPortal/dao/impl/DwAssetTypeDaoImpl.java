package com.wonders.task.assetPortal.dao.impl;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.task.assetPortal.dao.DwAssetTypeDao;

@Repository("dwAssetTypeDao")
public class DwAssetTypeDaoImpl implements DwAssetTypeDao{
	private HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	//注入hibernateTemplate
	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> countTypeByStation(){
		String sql = "select (case when t3.name = '一号线' then '01号线' " +
				"when t3.name = '二号线' then '02号线' " +
				"when t3.name = '三号线' then '03号线' " +
				"when t3.name = '四号线' then '04号线' " +
				"when t3.name = '五号线' then '05号线' " +
				"when t3.name = '六号线' then '06号线' " +
				"when t3.name = '七号线' then '07号线' " +
				"when t3.name = '八号线' then '08号线' " +
				"when t3.name = '九号线' then '09号线' " +
				"when t3.name = '十号线' then '10号线' " +
				"when t3.name = '十一号线' then '11号线' " +
				"when t3.name = '十二号线' then '12号线' " +
				"when t3.name = '十三号线' then '13号线' " +
				"when t3.name = '十四号线' then '14号线' " +
				"when t3.name = '十五号线' then '15号线' " +
				"when t3.name = '十六号线' then '16号线' end) line," +
				"t2.name station,t4.name type, sum(to_number(t.compact_price))/10000 sum_price ,count(t.type_1) count_sum "+
				"from t_asset_info t,t_asset_code_info t2 ,t_asset_code_info t3,t_asset_code_info t4 "+
				"where (REGEXP_LIKE(t.compact_price,'^-?[1-9]\\d*$') or REGEXP_LIKE(t.compact_price,'^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$')) "+
				"and t.removed = 0 and t.station_num = t2.id and t.route_num = t3.id and t.type_1 = t4.id "+
				"group by t3.name,t2.name,t4.name "+
				"order by line,station";
		SQLQuery query = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.addScalar("line", Hibernate.STRING).addScalar("station", Hibernate.STRING).addScalar("type", Hibernate.STRING)
				.addScalar("sum_price", Hibernate.DOUBLE).addScalar("count_sum", Hibernate.LONG);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> countTypeByLine(){
		String sql = "select (case when t3.name = '一号线' then '01号线' " +
				"when t3.name = '二号线' then '02号线' " +
				"when t3.name = '三号线' then '03号线' " +
				"when t3.name = '四号线' then '04号线' " +
				"when t3.name = '五号线' then '05号线' " +
				"when t3.name = '六号线' then '06号线' " +
				"when t3.name = '七号线' then '07号线' " +
				"when t3.name = '八号线' then '08号线' " +
				"when t3.name = '九号线' then '09号线' " +
				"when t3.name = '十号线' then '10号线' " +
				"when t3.name = '十一号线' then '11号线' " +
				"when t3.name = '十二号线' then '12号线' " +
				"when t3.name = '十三号线' then '13号线' " +
				"when t3.name = '十四号线' then '14号线' " +
				"when t3.name = '十五号线' then '15号线' " +
				"when t3.name = '十六号线' then '16号线' end) line," +
				"t4.name type, sum(to_number(t.compact_price))/10000 sum_price ,count(t.type_1) count_sum "+
				"from t_asset_info t ,t_asset_code_info t3,t_asset_code_info t4 "+
				"where (REGEXP_LIKE(t.compact_price,'^-?[1-9]\\d*$') or REGEXP_LIKE(t.compact_price,'^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$')) "+
				"and t.removed = 0 and t.route_num = t3.id and t.type_1 = t4.id "+
				"group by t3.name,t4.name "+
				"order by line";
		SQLQuery query = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.addScalar("line", Hibernate.STRING).addScalar("type", Hibernate.STRING)
				.addScalar("sum_price", Hibernate.DOUBLE).addScalar("count_sum", Hibernate.LONG);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> countTypeAllLine(){
		String sql = "select t4.name type, sum(to_number(t.compact_price))/10000 sum_price ,count(t.type_1) count_sum "+
				"from t_asset_info t ,t_asset_code_info t4 "+
				"where (REGEXP_LIKE(t.compact_price,'^-?[1-9]\\d*$') or REGEXP_LIKE(t.compact_price,'^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$')) "+
				"and t.removed = 0  and t.type_1 = t4.id "+
				"group by t4.name";
		SQLQuery query = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.addScalar("type", Hibernate.STRING).addScalar("sum_price", Hibernate.DOUBLE).addScalar("count_sum", Hibernate.LONG);
		return query.list();
	}
	
	public String findIdByLine(String line){
		String sql = "select t.id from dw_asset_type t where t.line = '"+line+"' and t.removed = '0' and t.type = '1'";
		SQLQuery query = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.addScalar("id", Hibernate.STRING);
		return (String)query.uniqueResult();
	}
	
	public String findIdByStation(String line,String station){
		String sql = "select t.id from dw_asset_type t where t.line = '"+line+"' and t.station = '"+station+"' and t.removed = '0' and t.type = '2'";
		SQLQuery query = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.addScalar("id", Hibernate.STRING);
		return (String)query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public Object load(String id,Class clazz) {
		Object instance = getHibernateTemplate().get(clazz, id);
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public void saveOrUpdateAll(Collection col) {
		getHibernateTemplate().saveOrUpdateAll(col);
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> countValueByCompany(){
		String sql = "select (case when t3.name = '一号线' then '01号线' " +
				"when t3.name = '二号线' then '02号线' " +
				"when t3.name = '三号线' then '03号线' " +
				"when t3.name = '四号线' then '04号线' " +
				"when t3.name = '五号线' then '05号线' " +
				"when t3.name = '六号线' then '06号线' " +
				"when t3.name = '七号线' then '07号线' " +
				"when t3.name = '八号线' then '08号线' " +
				"when t3.name = '九号线' then '09号线' " +
				"when t3.name = '十号线' then '10号线' " +
				"when t3.name = '十一号线' then '11号线' " +
				"when t3.name = '十二号线' then '12号线' " +
				"when t3.name = '十三号线' then '13号线' " +
				"when t3.name = '十四号线' then '14号线' " +
				"when t3.name = '十五号线' then '15号线' " +
				"when t3.name = '十六号线' then '16号线' end) line," +
				"t2.name company,t4.name type, sum(to_number(t.yuanzhi))/10000 sum_price ,count(t.type_1) count_sum,"+
				"sum(to_number(t.jingzhi))/10000 now_price "+
				"from t_asset_info t,t_asset_code_info t2 ,t_asset_code_info t3,t_asset_code_info t4 "+
				"where (REGEXP_LIKE(t.yuanzhi,'^-?[1-9]\\d*$') or REGEXP_LIKE(t.yuanzhi,'^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$')) "+
				"and (REGEXP_LIKE(t.jingzhi,'^-?[1-9]\\d*$') or REGEXP_LIKE(t.jingzhi,'^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$')) "+
				"and REGEXP_LIKE(t.owner_duty,'^-?[1-9]\\d*$') "+
				"and t.removed = 0 and t.owner_duty = t2.id and t.route_num = t3.id and t.type_1 = t4.id "+
				"group by t3.name,t2.name,t4.name "+
				"order by line,company";
		SQLQuery query = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.addScalar("line", Hibernate.STRING).addScalar("company", Hibernate.STRING).addScalar("type", Hibernate.STRING)
			.addScalar("sum_price", Hibernate.DOUBLE).addScalar("count_sum", Hibernate.LONG).addScalar("now_price", Hibernate.DOUBLE);
		return query.list();
	}
	
	public String findIdByCompany(String line,String company){
		String sql = "select t.id from dw_asset_type t where t.line = '"+line+"' and t.owner_company = '"+company+"' and t.removed = '0' and t.type = '3'";
		SQLQuery query = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.addScalar("id", Hibernate.STRING);
		return (String)query.uniqueResult();
	}
}
