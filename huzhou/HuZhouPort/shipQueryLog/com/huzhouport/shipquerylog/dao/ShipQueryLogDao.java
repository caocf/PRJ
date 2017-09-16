package com.huzhouport.shipquerylog.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.huzhouport.shipquerylog.model.ShipQueryLog;


public class ShipQueryLogDao {

	HibernateTemplate hibernateTemplate;
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public void saveShipQueryLog(ShipQueryLog log)
	{
		this.hibernateTemplate.save(log);
	}
	
	public List<ShipQueryLog> queryShipQueryLogs(String start,String end,int page,int num)
	{
		String hql="from ShipQueryLog s";
		
		if(start!=null && end !=null && !start.equals("") && !end.equals(""))
			hql="from ShipQueryLog s where s.querytime>='"+start+"' and s.querytime<='"+end+"'";
		else if(start!=null && !start.equals(""))
			hql="from ShipQueryLog s where s.querytime>='"+start+"'";
		else if(end!=null && !end.equals(""))
			hql="from ShipQueryLog s where s.querytime<='"+end+"'";
		
		return (List<ShipQueryLog>) queryCount(hql, (page-1)*num,num);
	}
	
	public int queryShipQueryLogsNum(String start,String end)
	{
		String hql="select count(*) from ShipQueryLog s";
		
		if(start!=null && end !=null && !start.equals("") && !end.equals(""))
			hql="select count(*) from ShipQueryLog s where s.querytime>='"+start+"' and s.querytime<='"+end+"'";
		else if(start!=null && !start.equals(""))
			hql="select count(*) from ShipQueryLog s where s.querytime>='"+start+"'";
		else if(end!=null && !end.equals(""))
			hql="select count(*) from ShipQueryLog s where s.querytime<='"+end+"'";
		
		return countRecord(hql);
	}
	
	public List<?> queryCount(String hql, int startSet, int maxSet) {
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

	// 总数
	public int countRecord(String hql) {
		Session session = this.hibernateTemplate.getSessionFactory()
				.openSession();
		int result = 0;
		try {
			Query query = session.createQuery(hql);
			result = ((Number) query.uniqueResult()).intValue();
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println(e1.getMessage());
		} finally {
			session.clear();
			session.close();
		}

		return result;
	}
}
