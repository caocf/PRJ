package com.sts.news.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.sts.news.model.RoadInfo;

public class RoadDao  
{
	HibernateTemplate hibernateTemplate;
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public List<RoadInfo> queryRoadInfoList(int start,int num)
	{
		String hql = "from RoadInfo r ";

		System.out.println(hql);
		
		return queryCount(hql, start, num);
	}
	
	
	protected List<RoadInfo> queryCount(String hql, int startSet, int maxSet) {
		Session session = null;
		List<RoadInfo> list =new ArrayList<RoadInfo>();
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
