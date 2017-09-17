package com.sts.news.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.sts.news.model.RoadWork;

public class RoadWorkDao{
	HibernateTemplate hibernateTemplate;
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public List<RoadWork> queryRoadInfoList(int start,int num)
	{
		String hql = "from RoadWork r ";

		System.out.println(hql);
		
		return queryCount(hql, start, num);
	}
	
	protected List<RoadWork> queryCount(String hql, int startSet, int maxSet) {
		Session session = null;
		List<RoadWork> list =new ArrayList<RoadWork>();
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
