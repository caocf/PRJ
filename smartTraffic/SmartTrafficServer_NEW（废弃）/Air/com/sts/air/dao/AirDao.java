package com.sts.air.dao;

import java.util.List;

import org.springframework.orm.hibernate4.HibernateTemplate;


import com.sts.air.model.AirCity;

public class AirDao{

	HibernateTemplate hibernateTemplate;
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public void saveCity(AirCity data)
	{
		hibernateTemplate.save(data);
	}
	
	public List<AirCity> findAllCity()
	{
		String hql = "from AirCity";
		
		return (List<AirCity>) hibernateTemplate.find(hql);

	}
	
	public List<AirCity> findCityByName(String name)
	{
		String hql="from AirCity a where a.name like '%"+name+"%'";
		return (List<AirCity>) hibernateTemplate.find(hql);
	}
}
