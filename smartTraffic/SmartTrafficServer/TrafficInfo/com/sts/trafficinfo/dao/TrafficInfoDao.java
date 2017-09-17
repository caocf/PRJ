package com.sts.trafficinfo.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;


public class TrafficInfoDao {

	/*---------------------hibernateTemplate 注入类 ---------------------------*/
	
	HibernateTemplate hibernateTemplate;
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	/*------------------------dao 内容----------------------*/
	
	/**
	 * 
	 * @param zone
	 * @param page
	 * @param num
	 * @return
	 */
	public List<?> queryICcards(int zone,int page,int num)
	{	
		String hql ="from ICcard i ";
		
		if(zone!=-1)
			hql+=" where i.zone="+zone;
		
		if(page>0 && num >0)
			return queryCount(hql, (page-1)*num, num);
		else
			return this.hibernateTemplate.find(hql);
	}
	
	/**
	 * 
	 * @param type
	 * @param page
	 * @param num
	 * @return
	 */
	public List<?> queryTrafficPoints(int type,int page,int num)
	{
		String hql="from TrafficPoint t ";
		if(type!=-1)
			hql+=" where t.type="+type;
		
		if(page>0 && num >0)
			return queryCount(hql, (page-1)*num, num);
		else
			return this.hibernateTemplate.find(hql);
	}
	
	/**
	 * 
	 * @param page
	 * @param num
	 * @return
	 */
	public List<?> queryTaxiCompanies(int page,int num)
	{
		String hql="from TaxiCompany t ";

		if(page>0 && num >0)
			return queryCount(hql, (page-1)*num, num);
		else
			return this.hibernateTemplate.find(hql);
	}
	
	/**
	 * 
	 * @param zone
	 * @param page
	 * @param num
	 * @return
	 */
	public List<?> queryTicketsAgents(int zone,int page,int num)
	{
		String hql ="from TicketsAgent i ";
		
		if(zone!=-1)
			hql+=" where i.zone="+zone;
		
		if(page>0 && num >0)
			return queryCount(hql, (page-1)*num, num);
		else
			return this.hibernateTemplate.find(hql);
	}
	
	/**
	 * 
	 * @param page
	 * @param num
	 * @return
	 */
	public List<?> queryTicketHotLines(int page,int num)
	{
		String hql="from TicketHotLine t ";

		if(page>0 && num >0)
			return queryCount(hql, (page-1)*num, num);
		else
			return this.hibernateTemplate.find(hql);
	}
	
	
	
	/*-----------------------------------------------------*/
	
	/**
	 * 
	 * @param hql
	 * @param start
	 * @param num
	 * @return
	 */
	protected List<?> queryCount(String hql, int start,
			int num) {
		Session session = null;
		List<?> list = null;
		session = this.hibernateTemplate.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			final Query q = session.createQuery(hql);
			q.setFirstResult(start); // 从第几条开始
			q.setMaxResults(num); // 取出几条
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
