package com.sts.complain.dao;



import org.springframework.orm.hibernate4.HibernateTemplate;

import com.sts.complain.model.Complain;

public class ComplainDao 
{
	HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public boolean save(Complain complain)
	{
		try
		{
			hibernateTemplate.save(complain);
			return true;
		}
		catch(Exception e)
		{
		}
		return false;
	}
}
