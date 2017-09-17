package com.sts.company.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateTemplate;


import com.sts.company.model.Company;

public class CompanyDao {
	HibernateTemplate hibernateTemplate;
	HibernateTemplate oraclHibernateTemplate;


	public void setOraclHibernateTemplate(
			HibernateTemplate oraclHibernateTemplate) {
		this.oraclHibernateTemplate = oraclHibernateTemplate;
	}
//	public List<Company> findCompany(String selectword,int page){
//		List<Company> companylist=new ArrayList<Company>();
//		String selectString="";
//		if(!StringUtils.isEmpty(selectword)){
//			selectString="where a.yhmc like %"+selectword+"%";
//		}
//		String sql="select a.yhid,a.yhmc,a.yhdz,a.fddbr,a.jyxkzh from z_yh_chuzuchejyyhxx a ";
//		sql+=selectString;
//		return companylist;
//	}
	
	public List<?> querySQL(String hql) {
		Session session = null;
		List<?> list = null;
		session = this.oraclHibernateTemplate.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			final Query q = session.createSQLQuery(hql);
			q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//			q.setFirstResult(startSet); // 从第几条开始
//			q.setMaxResults(maxSet); // 取出几条
			list = q.list();
			session.getTransaction().commit();
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println(e1.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return list;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
}
