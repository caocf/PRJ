package com.sts.liveness.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.sts.liveness.model.Liveness;


public class LivenessDao {
	
	HibernateTemplate hibernateTemplate;
	
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public void save(Liveness liveness)
	{
		this.hibernateTemplate.save(liveness);
	}
	
	public void update(Liveness liveness)
	{
		this.hibernateTemplate.update(liveness);
	}
	
	public List<Liveness> findByImei(String imei,String date)
	{
		String hql="from Liveness l where l.imei='"+imei+"' and l.logindate='"+date+"'";
		
		return (List<Liveness>) this.hibernateTemplate.find(hql);
	}
	
	public List<Map<String, Object>> queryLivenessCount(String start,String end) {

		String hql = "select d.logindate,COUNT(*) from Liveness d ";

		if(start!=null &&!start.equals(""))
		{
			hql+=" where d.logindate >='"+start+"'";
			
			if(end!=null &&!end.equals(""))
			{
				hql+=" and d.logindate <='"+end+"'";
			}
		}
		else if(end!=null &&!end.equals(""))
		{
			hql+=" where d.logindate <='"+end+"'";
		}
		
		hql+= " GROUP BY d.logindate ";
		
		return countByGroup(hql);
	}

	// 分组统计（如以后常用可修改加到basedao中）
	public List<Map<String, Object>> countByGroup(String hql) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		Session session = this.hibernateTemplate.getSessionFactory()
				.openSession();
		try {
			Transaction tx = session.beginTransaction();
			Iterator it = session.createQuery(hql).iterate();

			while (it.hasNext()) {
				Map<String, Object> temp = new HashMap<String, Object>();

				Object[] oc = (Object[]) it.next();

				temp.put("date", oc[0].toString());
				temp.put("count", oc[1].toString());

				result.add(temp);
			}
			tx.commit();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			session.clear();
			session.close();
		}

		return result;
	}
}
