package com.sts.complain.dao;

import java.util.List;



import org.springframework.orm.hibernate4.HibernateTemplate;

import com.sts.complain.model.ComplainType;

public class ComplainTypeDao 
{
	HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public List<ComplainType> getAllComplainTypes()
	{
		String hql="from ComplainType s";
		return (List<ComplainType>) hibernateTemplate.find(hql);
	}
}
