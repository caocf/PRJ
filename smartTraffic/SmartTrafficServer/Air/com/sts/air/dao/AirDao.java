package com.sts.air.dao;


import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.sts.air.model.AirCity;

public class AirDao {
	private HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	
	public void saveCity(AirCity data)
	{
		this.hibernateTemplate.save(data);
	}
	
	public List<AirCity> findAllCity()
	{
		String hql = "from AirCity";
		return (List<AirCity>) this.hibernateTemplate.find(hql);
	}
	
	public List<AirCity> findCityByName(String name)
	{
		String hql="from AirCity a where a.name like '%"+name+"%'";
		return (List<AirCity>) this.hibernateTemplate.find(hql);
	}
}
