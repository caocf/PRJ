package com.sts.repair.dao;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.sts.repair.model.CallHelp;

public class CallHelpDao {

	HibernateTemplate hibernateTemplate;
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public void save(CallHelp c)
	{
		this.hibernateTemplate.save(c);
	}
}
