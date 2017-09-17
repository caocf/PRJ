package com.sts.suggstion.dao;

import java.util.List;

import org.springframework.orm.hibernate4.HibernateTemplate;

import com.sts.suggstion.model.Suggestion;

public class SuggestionDao 
{
	HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public boolean save(Suggestion suggestion)
	{
		try
		{
			hibernateTemplate.save(suggestion);
			return true;
		}
		catch(Exception e)
		{
		}
		return false;
	}
	
	public List<Suggestion> getAllSuggestions()
	{
		String hql="from Suggestion s";
		return (List<Suggestion>) hibernateTemplate.find(hql);
	}
}
