package com.sts.smartbus.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.sts.smartbus.model.QRCode;

public class CircleDao {

	HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	/**
	 * 查询相似公交站点
	 * @param name
	 * @return
	 */
	public List<QRCode> queryName(String name,int num)
	{
		String hql="from QRCode q where q.name like '%"+name+"%'";
		
		if(num==0)
			return (List<QRCode>) this.hibernateTemplate.find(hql);
		else
			return (List<QRCode>)queryCount(hql, 0, num);
	}
	
	/**
	 * 分页
	 * @param hql
	 * @param startSet
	 * @param maxSet
	 * @return
	 */
	protected List<?> queryCount(String hql, int startSet, int maxSet) {
		Session session = null;
		List<?> list = null;
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
