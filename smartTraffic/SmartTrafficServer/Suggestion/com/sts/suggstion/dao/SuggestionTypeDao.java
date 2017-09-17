package com.sts.suggstion.dao;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.sts.suggstion.model.SuggestionType;

public class SuggestionTypeDao 
{
	HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public List<SuggestionType> getAllSuggestionTypes()
	{
		String hql="from SuggestionType s";
		return (List<SuggestionType>) hibernateTemplate.find(hql);
	}
}
