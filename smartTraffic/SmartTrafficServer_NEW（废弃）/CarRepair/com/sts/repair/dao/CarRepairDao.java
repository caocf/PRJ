package com.sts.repair.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.sts.repair.model.CarRepair;

public class CarRepairDao 
{
	HibernateTemplate hibernateTemplate;
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	public List<CarRepair> queryRepairByName(String name,int start,int num)
	{
		String hql = "from CarRepair c where c.name like '%"+name+"%'";
		
		return queryCount(hql, start, num);
	}
	
	public List<CarRepair> queryRepairDefault(int start,int num)
	{
		String hql = "from CarRepair as c";
		
		return queryCount(hql, start, num);
	}
	
	protected List<CarRepair> queryCount(String hql, int startSet,
			int maxSet) {
		Session session = null;
		List<CarRepair> list = new ArrayList<CarRepair>();
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
