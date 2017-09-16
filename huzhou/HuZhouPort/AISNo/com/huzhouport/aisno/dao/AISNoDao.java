package com.huzhouport.aisno.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import com.huzhouport.aisno.model.AIS;
import com.huzhouport.aisno.model.AISNo;
import com.huzhouport.common.util.DateTimeUtil;

public class AISNoDao 
{
	HibernateTemplate hibernateTemplate;
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate)
	{
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public List<AISNo> queryShipNameByAisNo(String no)
	{
		String hql="from AISNo a where a.aisnum='"+no+"'";
		
		return hibernateTemplate.find(hql);
	}
	
	public List<?> queryShipNameByShipName(String name)
	{
		String hql="from AISNo a where a.shipname='"+name+"'";
		
		List<?> list=hibernateTemplate.find(hql);
		int n= list.size();
		String s=list.toString();
		return list;
	}
	
	public List<AIS> queryShipNameByEditAisNo(String no)
	{
		String hql="from AIS a where a.num='"+no+"'";
		
		return hibernateTemplate.find(hql);
	}
	
	public List<AIS> queryShipNameByEditShipName(String shipname)
	{
		String hql="from AIS a where a.name='"+shipname+"'";
		
		return hibernateTemplate.find(hql);
	}
	
	public void saveAis(AIS a)
	{
		a.setAdddate(DateTimeUtil.getCurrTimeFmt());
		//this.hibernateTemplate.save(a);
		List<AIS> list= queryShipNameByEditShipName(a.getName());
		if(list!=null&&list.size()>0)
		{
			AIS ais=list.get(0);
			if(ais!=null)
			{
				hibernateTemplate.delete(ais);
			}
		}
		List<AIS> list1= queryShipNameByEditAisNo(a.getNum());
		if(list1!=null&&list1.size()>0)
		{
			AIS ais=list1.get(0);
			if(ais!=null)
			{
				hibernateTemplate.delete(ais);
			}
		}
		
		hibernateTemplate.saveOrUpdate(a);
	}
	
	public void updateAis(AIS a)
	{
		a.setAdddate(DateTimeUtil.getCurrTimeFmt());
		this.hibernateTemplate.update(a);
	}
	
	public List<AIS> queryEditAis(int page,int num)
	{
		String hql="from AIS a order by a.adddate desc";
		if(page>0&&num>0)
			return (List<AIS>) queryCount(hql, (page-1)*num, num);
		
		return this.hibernateTemplate.find(hql);
	}
	
	public int countAis()
	{
		return countRecord("select count(*) from AIS a ");
	}
	
	
	
	// 分页
	protected List<?> queryCount(String hql, int startSet, int maxSet) {
		Session session = null;
		List<?> list = null;
		session = this.hibernateTemplate.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			final Query q = session.createQuery(hql);
			q.setFirstResult(startSet); // 从第几条开始
			q.setMaxResults(maxSet); // 取出几条
			list = q.list();
			session.getTransaction().commit();
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println(e1.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return list;
	}

	// 总数
	public int countRecord(String hql) {
		Session session = this.hibernateTemplate.getSessionFactory()
				.openSession();
		int result = 0;
		try {
			Query query = session.createQuery(hql);
			result = ((Number) query.uniqueResult()).intValue();
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println(e1.getMessage());
		} finally {
			session.clear();
			session.close();
		}

		return result;
	}

	public void updateAisInfo(AIS a) {
		// TODO Auto-generated method stub
		Session session = null;
		List<?> list = null;
		session = this.hibernateTemplate.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			String hql="update AIS set appflag="+a.getAppflag()+",checker='"+a.getChecker()+"' where num="+a.getNum();
			Query queryupdate=session.createQuery(hql);
			int ret=queryupdate.executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println(e1.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}
	
	public void updateShipnameByAisNo(AISNo aisno)
	{
		Session session = this.hibernateTemplate.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			String hql="update AISNo set shipname='"+aisno.getShipname()+"' where aisnum='"+aisno.getAisnum()+"'";
			Query queryupdate=session.createQuery(hql);
			int ret=queryupdate.executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println(e1.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}
	
}
