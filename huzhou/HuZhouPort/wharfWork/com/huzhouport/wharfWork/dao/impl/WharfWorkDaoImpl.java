package com.huzhouport.wharfWork.dao.impl;




import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;











import com.huzhouport.attendace.model.Location;
import com.huzhouport.dangerousgoodsportln.model.Port;
import com.huzhouport.log.model.PageModel;
import com.huzhouport.log.model.QueryCount;
import com.huzhouport.publicuser.model.WharfBinding;
import com.huzhouport.wharfWork.dao.WharfWorkDao;
import com.huzhouport.wharfWork.model.Wharf;
import com.huzhouport.wharfWork.model.WharfWork;
import com.huzhouport.wharfWork.model.Wharfbean;











public class WharfWorkDaoImpl extends HibernateDaoSupport implements WharfWorkDao{
	

	public PageModel findByPageScroll(String hql, int firstPage, int maxPage) {
		
		PageModel pm = new PageModel();
	    Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Query query = session.createQuery(hql);
        query.setFirstResult(firstPage);
		query.setMaxResults(maxPage);
		ArrayList  list = null;
		try{
		  list=(ArrayList) query.list();
		}catch(Exception e){
			
			e.printStackTrace();
		}
		Iterator iterator1 = list.iterator();	
		 Wharfbean st;
		List<Wharfbean> list1 = new ArrayList<Wharfbean>();
		while (iterator1.hasNext()){
			st = new Wharfbean();
		    Object[] object = (Object[]) iterator1.next();

		    
		    WharfWork ww=(WharfWork) object[0];
		    WharfBinding w=(WharfBinding) object[1];
      	    Port p1= (Port) object[2];
      	    Port p2=(Port) object[3];
			
      	  
      	    st.setId(ww.getId());
      	    st.setWharfID(ww.getWharfWorkID());
      	    st.setWharfName(w.getWharfNum());
            st.setPortMart(ww.getPortMart());
      	    st.setShipName(ww.getShipName());
      	    st.setDeclareTime(ww.getDeclareTime());
      	    st.setStartingPortName(p1.getPortName());
      	    st.setArrivalPortName(p2.getPortName());
      	    st.setCargoTypes(ww.getCargoTypes());
      	    st.setCarrying(ww.getCarrying());
      	    st.setUniti(ww.getUniti());
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
	
	public Wharfbean findBywharfworkid(String hql){
		
	
	    Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		//Query query = session.createQuery(hql);
		ArrayList  list = null;
		try{
		  Query query = session.createQuery(hql);
		  list=(ArrayList) query.list();
		}catch(Exception e){
			
			e.printStackTrace();
		}
		   Wharfbean	st = new Wharfbean();
		    Object[] object = (Object[]) list.get(0);
		    WharfWork ww=(WharfWork) object[0];
		    WharfBinding w=(WharfBinding) object[1];
     	    Port p1= (Port) object[2];
     	    Port p2=(Port) object[3];
     	    Location l=(Location) object[4];
			
     	  
     	    st.setId(ww.getId());
     	    st.setWharfID(ww.getWharfWorkID());
     	    st.setWharfName(w.getWharfNum());
     	    st.setWharfWorkNorm(Integer.parseInt(w.getWharfWorkNorm()));
     	    st.setWharfWorkSurplus(Integer.parseInt(w.getWharfWorkSurplus()));
     	    st.setName(ww.getName());
     	    st.setShipName(ww.getShipName());
     	    st.setStartingPort(ww.getStartingPort());
     	    st.setStartingPortName(p1.getPortName());
     	    st.setArrivalPort(ww.getArrivalPort());
    	    st.setArrivalPortName(p2.getPortName());
     	    st.setPortMart(ww.getPortMart());
     	    st.setCargoTypes(ww.getCargoTypes());
    	    st.setCarrying(ww.getCarrying());
    	    st.setUniti(ww.getUniti());
    	    st.setWorkTime(ww.getWorkTime());
    	    st.setLocationID(ww.getLocationID());
    	    st.setLongitude(l.getLongitude());
    	    st.setLatitude(l.getLatitude());
    	    st.setLocationName(l.getLocationName());
    	    st.setWorkPhoto(ww.getWorkPhoto());
     	    st.setDeclareTime(ww.getDeclareTime());
		
	
     	   session.clear();
   		session.close();
		return st;
		
	}
	
	
	public Wharf viewWharf(String hql){

	    Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		//Query query = session.createQuery(hql);
		ArrayList  list = null;
		try{
		  Query query = session.createQuery(hql);
		  list=(ArrayList) query.list();
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		Wharf w=(Wharf) list.get(0);
		session.clear();
		session.close();
		return w;
	}
	public String insertLocation(Location location ){
		HibernateTemplate ht = this.getHibernateTemplate();
		SessionFactory factory = ht.getSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();
		
		session.save(location);
		session.getTransaction().commit();
	  
		session.clear();
		session.close();
		return  location.getLocationID()+"";
	}
	public List<Port> findStartingPortName(String hql){
		 Session session=this.getHibernateTemplate().getSessionFactory().openSession();
			Query query=session.createQuery(hql);
			List list=query.list();
			session.clear();
			session.close();
			return list;
	}
	
	public String  savePort(Port port){
		HibernateTemplate ht = this.getHibernateTemplate();
		SessionFactory factory = ht.getSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();
		session.save(port);
		session.getTransaction().commit();
		session.clear();
		session.close();
		return port.getPortID()+"";
	}
	public void saveWharfwork(WharfWork wharfwork){
		HibernateTemplate ht = this.getHibernateTemplate();
		SessionFactory factory = ht.getSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();
		session.save(wharfwork);
		session.getTransaction().commit();
		session.clear();
		session.close();
	}
	 public void updateWharf(String hql){
		 Session session = this.getHibernateTemplate().getSessionFactory().openSession();
			session.beginTransaction();  
			Query query =session.createQuery(hql);
			query.executeUpdate();
			session.getTransaction().commit();
			session.clear();
			session.close();
	 }

	 
	 public PageModel findByPageScroll4(String hql, int firstPage, int maxPage) {
			
			PageModel pm = new PageModel();
		    Session session = this.getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.createQuery(hql);
	        query.setFirstResult(firstPage);
			query.setMaxResults(maxPage);
			ArrayList  list = null;
			try{
			  list=(ArrayList) query.list();
			}catch(Exception e){
				
				e.printStackTrace();
			}
			Iterator iterator1 = list.iterator();	
			//WharfBinding st;
			List<WharfBinding> list1 = new ArrayList<WharfBinding>();
			while (iterator1.hasNext()){
				//st = new WharfBinding();
			    Object object = (Object) iterator1.next();    
			    //WharfBinding w=(WharfBinding) object;
				list1.add((WharfBinding) object);	
		}
			pm.setRecordsDate(list1);
			pm.setTotal(((Long)QueryCount.getQueryCount(hql, session)).intValue());
			pm.setCurrentPage(firstPage);
			pm.setManPage(maxPage);
			session.clear();
			session.close();
			return pm;
		}	 
	 public List<WharfBinding> wharfWorkSurplus(String hql){
		    Session session=this.getHibernateTemplate().getSessionFactory().openSession();
			Query query=session.createQuery(hql);
			List list=query.list();
			session.clear();
			session.close();
			return list;
	}
}