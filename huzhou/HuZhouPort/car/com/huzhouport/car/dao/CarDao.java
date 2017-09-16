package com.huzhouport.car.dao;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.huzhouport.car.model.CarApplication;
import com.huzhouport.illegal.service.impl.AbnormalpushService;

public class CarDao 
{
	HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public void applyCar(CarApplication ca)
	{
		hibernateTemplate.save(ca);
	}
	
	public List<?>  findMyCarAps(int userid,int status)
	{
		String statustring="";
		switch(status)
		{
		case 1://待审批
			statustring=" and cp.status2id=1 and cp.status1id!=3";
			break;
		case 2://同意
			statustring=" and cp.status1id=2 and cp.status2id=2";
			break;
		case 3://驳回
			statustring=" and (cp.status1id=3 or cp.status2id=3)";
			break;
		case 4://撤回
			statustring=" and cp.status1id="+status;
			break;
		}
		
		String hql="from CarApplication cp , User u  where  cp.approver1id= u.userId and" +
				" cp.userid="+userid;
		return hibernateTemplate.find(hql/*+statustring*/+" order by cp.aptime desc");  
		
	}
	
	public List<?>  findMyCarCheck1(int apid,int status)
	{
		String statustring="";
		switch(status)
		{
		case 1://待审批
			statustring=" and cp.status1id=1";
			break;
		case 2://已审核
			statustring=" and cp.status1id!=1";
			break;
		}
		
		String hql="from CarApplication cp , User u  where  cp.userid= u.userId and" +
				" cp.approver1id="+apid;
		return hibernateTemplate.find(hql+statustring+" order by cp.aptime desc"); 
		
	}
	
	public List<?>  findMyCarCheck2(int officeid,int status)
	{
		String statustring="";
		switch(status)
		{
		case 1://待审批
			statustring=" and cp.status2id=1";
			break;
		case 2://同意
			statustring=" and cp.status2id!=1";
			break;
		}
		
		String hql="from CarApplication cp , User u,User ap  where  cp.userid= u.userId and cp.approver1id=ap.userId and cp.status1id=2 and" +
				" cp.approver2id="+officeid;
		return hibernateTemplate.find(hql+statustring+" order by cp.aptime desc");  
		
	}
	
	public void commitCheck(CarApplication carApplication)
	{
		hibernateTemplate.update(carApplication);
	}
	
	public CarApplication findCarApplicationByID(int id)
	{
		String hql="from CarApplication cp where  cp.id="+id;
		List<?> list=hibernateTemplate.find(hql);
		if(list.size()>0)return (CarApplication) list.get(0);
		return null;
		
	}
	
	public List<?> findUnvaliableCars(String d1,String d2)
	{
		String hql="from CarInfo ci, CarApplication cp where ci.id=cp.assignmentid" +
				" and cp.status1id=2 and cp.status2id=2 and ((cp.starttime between '"+d1+"' and '"+d2+"') or (cp.endtime between '"+d1+"' and '"+d2+"'))";
		List<?> list=hibernateTemplate.find(hql);
		
		return list;
		
	}
	public List<?> findAllCars()
	{
		String hql="from CarInfo";
		List<?> list=hibernateTemplate.find(hql);
		
		return list;		
	}

	public int countAp(int userid)
	{
		String hql="select count(*) from CarApplication ca where ca.isread=0 and ca.userid="+userid;
		List list=hibernateTemplate.find(hql);
		
		return Integer.parseInt(list.get(0).toString());		
	}
	public int countMyCheck(int userid)
	{
		String hql="select count(*) from CarApplication ca where ca.status1id=1 and ca.approver1id="+userid;
		List list=hibernateTemplate.find(hql);
		
		return Integer.parseInt(list.get(0).toString());		
	}
	public int countOfficeCheck(int userid)
	{
		String hql="select count(*) from CarApplication ca where ca.status1id=2 and ca.status2id=1 and ca.approver2id="+userid;
		List<?> list=hibernateTemplate.find(hql);
		
		return Integer.parseInt(list.get(0).toString());		
	}
}
