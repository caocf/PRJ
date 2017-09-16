package com.huzhouport.dangerousGoodsJob.dao.impl;
        




import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;









import com.huzhouport.dangerousGoodsJob.dao.Dao;
import com.huzhouport.dangerousGoodsJob.model.DangerousWorkDeclare;
import com.huzhouport.dangerousGoodsJob.model.DangerousWorkDeclareBean;
import com.huzhouport.dangerousGoodsJob.model.WharfJobNum;
import com.huzhouport.dangerousgoodsportln.model.Port;
import com.huzhouport.leaveandovertime.model.LeaveOrOtKindbean;
import com.huzhouport.log.model.PageModel;
import com.huzhouport.log.model.QueryCount;
import com.huzhouport.user.model.User;











public class DaoImpl extends HibernateDaoSupport implements Dao{
	
	
	



	

	




	public PageModel findByPageScroll(String hql, int firstPage, int maxPage) {
		
		PageModel pm = new PageModel();
	    Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Query query = session.createQuery(hql);
        query.setFirstResult(firstPage);
		query.setMaxResults(maxPage);
		ArrayList  list=(ArrayList) query.list();
		Iterator iterator1 = list.iterator();	
		 System.out.println("size="+list.size());
		 DangerousWorkDeclareBean st;
		List<DangerousWorkDeclareBean> list1 = new ArrayList<DangerousWorkDeclareBean>();
		while (iterator1.hasNext()){
			st = new DangerousWorkDeclareBean();
			Object object = (Object) iterator1.next();
      	  DangerousWorkDeclare d=(DangerousWorkDeclare) object;
			
      	    st.setDeclareID(d.getDeclareID());
      	    st.setShipName(d.getShipName());
      	    st.setDeclareTime(d.getDeclareTime());
      	    st.setWharfID(d.getWharfID());
      	    st.setCargoTypes(d.getCargoTypes());
      	    st.setReviewResult(d.getReviewResult());
      	    
			list1.add(st);
			
			
	}
	
		
		
		
		pm.setRecordsDate(list1);
		
	
		
	
		pm.setTotal(((Long)QueryCount.getQueryCount(hql, session)).intValue());
		pm.setCurrentPage(firstPage);
		pm.setManPage(maxPage);
		session.clear();
		session.close();
		return pm;
	}		
	
	public DangerousWorkDeclareBean findByID(String hql){
	    Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Query query = session.createQuery(hql);
		ArrayList  list=(ArrayList) query.list();
		Iterator iterator1 = list.iterator();	
		DangerousWorkDeclareBean st=null;
		while (iterator1.hasNext()){
			st = new DangerousWorkDeclareBean();
			Object[] object = (Object[]) iterator1.next();
			DangerousWorkDeclare d=(DangerousWorkDeclare) object[0];
	      	
			Port p1= (Port) object[1];
			Port p2= (Port) object[2];
			
      	    st.setDeclareID(d.getDeclareID());
      	    st.setShipName(d.getShipName());
      	    st.setDeclareTime(d.getDeclareTime());
      	    st.setStartingPort(d.getStartingPort());
      	    st.setStartingPortName(p1.getPortName());
      	    st.setArrivalPort(d.getArrivalPort());
      	    st.setArrivalPortName(p2.getPortName());
      	    st.setCargoTypes(d.getCargoTypes());
      	    st.setDangerousLevel(d.getDangerousLevel());
      	    st.setWharfID(d.getWharfID());
      	    st.setWorkTIme(d.getWorkTIme());
      	    st.setCarrying(d.getCarrying());
      	    st.setReviewUser(d.getReviewUser());
      	    if(d.getReviewUser()==0){
      	    	st.setReviewUserName("0");
      	    }else{
      	    String name	=findUsername(d.getReviewUser());
      	    st.setReviewUserName(name);
      	    }
      	    st.setReviewResult(d.getReviewResult());
      	    st.setReviewOpinion(d.getReviewOpinion());
      	    st.setReviewTime(d.getReviewTime());
      	session.clear();
  		session.close();
	}
		return st;
}
	
public String findUsername(int id){ //通过id 获得user表的name
	String hql="from User where userId="+id;
	 Session session=this.getHibernateTemplate().getSessionFactory().openSession();
		
		Query query=session.createQuery(hql);
		List list=query.list();
		User user=(User)list.get(0);
		String name=user.getName();
		session.clear();
		session.close();
	
	
	return name;
	
	
	}

public  void update(String hql){
	//Session session=HibernateUitl.getSessionFactory().getCurrentSession();
	Session session = this.getHibernateTemplate().getSessionFactory().openSession();
	session.beginTransaction();  
	Query query =session.createQuery(hql);
	query.executeUpdate();
	session.getTransaction().commit();
	session.clear();
	session.close();
}

public void insert(DangerousWorkDeclare dangerousWorkDeclare){
	System.out.println("ShipName==="+dangerousWorkDeclare.getShipName());
	HibernateTemplate ht = this.getHibernateTemplate();
	SessionFactory factory = ht.getSessionFactory();
	Session session = factory.openSession();
	session.beginTransaction();
	session.save(dangerousWorkDeclare);
	session.getTransaction().commit();
	session.clear();
	session.close();
}

public List<DangerousWorkDeclareBean> findList(String hql){
	Session session = this.getHibernateTemplate().getSessionFactory().openSession();
	   Query query = session.createQuery(hql);
	   ArrayList  list=(ArrayList) query.list();
	   Iterator iterator1 = list.iterator();	
	   System.out.println("size="+list.size());

		 DangerousWorkDeclareBean st;
		List<DangerousWorkDeclareBean> list1 = new ArrayList<DangerousWorkDeclareBean>();

		while (iterator1.hasNext()){
			st = new DangerousWorkDeclareBean();
		    Object object = (Object) iterator1.next();   
    	    DangerousWorkDeclare d=(DangerousWorkDeclare) object;

    	    st.setDeclareID(d.getDeclareID());
    	    st.setShipName(d.getShipName());
    	    st.setDeclareTime(d.getDeclareTime());
    	    st.setWharfID(d.getWharfID());
    	    st.setCargoTypes(d.getCargoTypes());
    	    st.setReviewResult(d.getReviewResult());
			list1.add(st);
			
		}
		session.clear();
		session.close();
		return list1;
		 
}

public List<WharfJobNum> findWharfJobNum(String hql){ //通过id 获得user表的name
	
	 Session session=this.getHibernateTemplate().getSessionFactory().openSession();
	Query query=session.createQuery(hql);
	List list=query.list();
	session.clear();
	session.close();
	
	
	return list;
	
	
	}

public List<Port> findStartingPortName(String hql){
	 Session session=this.getHibernateTemplate().getSessionFactory().openSession();
		Query query=session.createQuery(hql);
		List list=query.list();
		session.clear();
		session.close();
		return list;
}

public void savePort(Port port){
	
	HibernateTemplate ht = this.getHibernateTemplate();
	SessionFactory factory = ht.getSessionFactory();
	Session session = factory.openSession();
	session.beginTransaction();
	session.save(port);
	session.getTransaction().commit();
	session.clear();
	session.close();
}

}