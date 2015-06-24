package com.wonders.task.asset.dao.impl;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.wonders.task.asset.dao.DwAssetCodeInfoDao;


@Repository("dwAssetCodeInfoDao")
public class DwAssetCodeInfoDaoImpl implements DwAssetCodeInfoDao {
	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	// 注入hibernateTemplate
	@Resource(name = "hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findAllDwAsset() {
		String sql = "select * from dw_asset_code_info d where d.removed = '0'";
		SQLQuery query = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public void saveOrUpdateAll(Collection col) {
		getHibernateTemplate().saveOrUpdateAll(col);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findAllCountByTypeidDm() {
		String sql = "select d.type_id,d.dm,count(*) as value " 
				+ "from t_asset_info t , t_asset_code_info c,dw_asset_code_info d "
				+ "where t.TYPE_1=c.ID and c.TYPE_ID=d.TYPE_ID and c.CODE=d.DM group by d.type_id,d.dm " 
				+ "union all " 
				+ "select d.type_id,d.dm,count(*) as value "
				+ "from t_asset_info t , t_asset_code_info c,dw_asset_code_info d " 
				+ "where t.type_1 in(select distinct id from t_asset_code_info "
				+ "where code_info_id='13441' and removed=0) and t.TYPE_2=c.ID and c.TYPE_ID=d.TYPE_ID and c.CODE=d.DM group by d.type_id,d.dm " 
				+ "union all "
				+ "select d.type_id,d.dm,count(*) as value " 
				+ "from t_asset_info t , t_asset_code_info c,dw_asset_code_info d "
				+ "where t.route_num =c.ID and c.TYPE_ID=d.TYPE_ID and c.CODE=d.DM group by d.type_id,d.dm " 
				+ "union all " 
				+ "select d.type_id,d.dm,count(*) as value "
				+ "from t_asset_info t , dw_asset_code_info d where t.REGISTRY =d.dm and d.TYPE_ID=1 group by d.type_id,d.dm " 
				+ "union all " 
				+ "select d.type_id,d.dm,count(*) as value "
				+ "from t_asset_info t , t_asset_code_info c,dw_asset_code_info d " 
				+ "where t.OWNER_DUTY =to_char(c.ID) and c.TYPE_ID=d.TYPE_ID and c.CODE=d.DM group by d.type_id,d.dm";
		SQLQuery query = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		return query.list();
	}

	@Override
	public String findIdByTypeIdAndDm(String typeId, String dm) {
		String sql="select d.id from dw_asset_code_info d where d.type_id = '"+typeId+"' and d.dm = '"+dm+"'";
		SQLQuery query = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.addScalar("id", Hibernate.STRING);
		return (String)query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public Object load(String id, Class clazz) {
		Object instance = getHibernateTemplate().get(clazz, id);
		return instance;
	}

}
