package com.sts.taxi.dao;

import java.util.List;

import org.springframework.orm.hibernate4.HibernateTemplate;

import com.sts.taxi.model.TaxiOrder;

public class TaxiDao 
{
	HibernateTemplate hibernateTemplate;
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public void save(TaxiOrder t)
	{
		this.hibernateTemplate.save(t);
	}
	
	
	public List<TaxiOrder> queryByID(int userid)
	{
		String hql="from TaxiOrder t where t.userid="+userid+" order by t.date DESC";
		
		return (List<TaxiOrder>) this.hibernateTemplate.find(hql);
	}
}
