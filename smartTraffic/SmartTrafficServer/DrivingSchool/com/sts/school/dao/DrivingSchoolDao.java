package com.sts.school.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.sts.school.model.DrivingSchool;

public class DrivingSchoolDao 
{
	HibernateTemplate hibernateTemplate;
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	public List<DrivingSchool> querySchoolByName(String name,int start,int num)
	{
		String hql = "from DrivingSchool d where d.name like '%"+name+"%'";
		
		return queryCount(hql, start, num);
	}
	
	public List<DrivingSchool> querySchoolDefault(int start,int num)
	{
		String hql = "from DrivingSchool as d";
		
		return queryCount(hql, start, num);
	}
	
	protected List<DrivingSchool> queryCount(String hql, int startSet,
			int maxSet) {
		Session session = null;
		List<DrivingSchool> list = new ArrayList<DrivingSchool>();
		session = this.hibernateTemplate.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			final Query q = session.createQuery(hql);
			q.setFirstResult(startSet); // 从第几条开始
			q.setMaxResults(maxSet); // 取出几条
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
}
