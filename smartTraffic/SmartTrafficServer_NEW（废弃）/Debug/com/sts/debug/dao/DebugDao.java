package com.sts.debug.dao;

import java.util.List;



import org.springframework.orm.hibernate4.HibernateTemplate;

import com.sts.debug.model.Debug;
import com.sts.suggstion.model.Suggestion;

public class DebugDao 
{
	HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public boolean save(Debug debug)
	{
		try
		{
			hibernateTemplate.save(debug);
			return true;
		}
		catch(Exception e)
		{
		}
		return false;
	}
	
	public List<Debug> getAllDebugs()
	{
		String hql="from Debug d";
		return (List<Debug>) hibernateTemplate.find(hql);
	}
}
