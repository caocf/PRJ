package com.sts.suggstion.dao;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.sts.suggstion.model.AppSuggestion;

public class AppSuggestionDao 
{
	HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public void save(AppSuggestion suggestion)
	{
		this.hibernateTemplate.save(suggestion);
	}

}
