package com.huzhouport.equipment.dao.impl;




import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;







import com.huzhouport.dangerousgoodsportln.model.DangerousArrivalDeclare;
import com.huzhouport.dangerousgoodsportln.model.DangerousArrivalDeclareBean;
import com.huzhouport.dangerousgoodsportln.model.Port;
import com.huzhouport.equipment.dao.Dao;
import com.huzhouport.equipment.model.Equipment;
import com.huzhouport.equipment.model.EquipmentKind;
import com.huzhouport.equipment.model.Equipmentbean;
import com.huzhouport.log.model.PageModel;
import com.huzhouport.log.model.QueryCount;

import com.huzhouport.user.model.User;









public class DaoImpl extends HibernateDaoSupport implements Dao{
	
	
	

	

	

	




	public PageModel findByPageScroll(String hql, int firstPage, int maxPage,String action) {
		PageModel pm = new PageModel();
	    Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Query query = session.createQuery(hql);
        query.setFirstResult(firstPage);
		query.setMaxResults(maxPage);
		ArrayList  list=(ArrayList) query.list();
		Iterator iterator1 = list.iterator();	
		Equipmentbean st;
		List<Equipmentbean> list1 = new ArrayList<Equipmentbean>();
		//for (int i=0;i<list.size();i++){
		while (iterator1.hasNext()){
			st = new Equipmentbean();
		
		    Object[] object = (Object[]) iterator1.next();
	
			
		    Equipment e=(Equipment) object[0];
		    EquipmentKind ek=(EquipmentKind) object[1];
			User u=(User) object[2];
			User u1=(User) object[3];
			st.setEquipmentID(e.getEquipmentID());
			st.setEquipmentKind(e.getEquipmentKind());
			st.setEquipmentKindName(ek.getEquipmentKindName());
			st.setEquipmentUser(e.getEquipmentUser());
			st.setEquipmentUserName(u.getName());
			st.setEquipmentDate(e.getEquipmentDate());
			st.setEquipmentReason(e.getEquipmentReason());
			st.setApprovalID(e.getApprovalID());
			st.setApprovalName(u1.getName());
			st.setApprovalResult(e.getApprovalResult());
			st.setApprovalOpinion(e.getApprovalOpinion());
			st.setApprovalTime(e.getApprovalTime());
			list1.add(st);	
	}
		pm.setRecordsDate( list1);
		pm.setAction(action);
		pm.setTotal(((Long)QueryCount.getQueryCount(hql, session)).intValue());
		pm.setCurrentPage(firstPage);
		pm.setManPage(maxPage);
		session.clear();
		session.close();
		return pm;
	}		
	
	 public Equipmentbean findByID(String hql){
		  Session session = this.getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.createQuery(hql);
			ArrayList  list=(ArrayList) query.list();
			Iterator iterator1 = list.iterator();	
			Equipmentbean st=null;
			//while (iterator1.hasNext()){
				st = new Equipmentbean();
				Object[] object = (Object[]) iterator1.next();
			    Equipment e=(Equipment) object[0];
			    EquipmentKind ek=(EquipmentKind) object[1];
				User u=(User) object[2];
				User u1=(User) object[3];
				st.setEquipmentID(e.getEquipmentID());
				st.setEquipmentKind(e.getEquipmentKind());
				st.setEquipmentKindName(ek.getEquipmentKindName());
				st.setEquipmentUser(e.getEquipmentUser());
				st.setEquipmentUserName(u.getName());
				st.setEquipmentDate(e.getEquipmentDate());
				st.setEquipmentReason(e.getEquipmentReason());
				st.setApprovalID(e.getApprovalID());
				st.setApprovalName(u1.getName());
				st.setApprovalResult(e.getApprovalResult());
				st.setApprovalOpinion(e.getApprovalOpinion());
				st.setApprovalTime(e.getApprovalTime());
	      	    
	      	    session.clear();
	  		    session.close();
		//}
			return st;
}
	 
	 public  void update(String hql){
			Session session = this.getHibernateTemplate().getSessionFactory().openSession();
			session.beginTransaction();  
			Query query =session.createQuery(hql);
			query.executeUpdate();
			session.getTransaction().commit();
			session.clear();
			session.close();
		}
	 
	 public List<Equipmentbean> equilpmentlist(String hql){
		  Session session = this.getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.createQuery(hql);
			ArrayList  list=(ArrayList) query.list();
			Iterator iterator1 = list.iterator();	
			Equipmentbean st=null;
			List<Equipmentbean> list1 = new ArrayList<Equipmentbean>();
			while (iterator1.hasNext()){
				st = new Equipmentbean();
				Object[] object = (Object[]) iterator1.next();
			    Equipment e=(Equipment) object[0];
			    EquipmentKind ek=(EquipmentKind) object[1];
				User u=(User) object[2];
				User u1=(User) object[3];
				st.setEquipmentID(e.getEquipmentID());
				st.setEquipmentKind(e.getEquipmentKind());
				st.setEquipmentKindName(ek.getEquipmentKindName());
				st.setEquipmentUser(e.getEquipmentUser());
				st.setEquipmentUserName(u.getName());
				st.setEquipmentDate(e.getEquipmentDate());
				st.setEquipmentReason(e.getEquipmentReason());
				st.setApprovalID(e.getApprovalID());
				st.setApprovalName(u1.getName());
				st.setApprovalResult(e.getApprovalResult());
				st.setApprovalOpinion(e.getApprovalOpinion());
				st.setApprovalTime(e.getApprovalTime());

	  		  list1.add(st);
		}
			  session.clear();
	  		  session.close();
			return list1;
	 }
	 public List<EquipmentKind> equipmentkindAll(String hql){
		 Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Query query = session.createQuery(hql);
		ArrayList  list=(ArrayList) query.list();
		 session.clear();
  		 session.close();
		return list;
	 }
	 
	 public void equipmentAdd(Equipment equipment){
			HibernateTemplate ht = this.getHibernateTemplate();
			SessionFactory factory = ht.getSessionFactory();
			Session session = factory.openSession();
			session.beginTransaction();
			session.save(equipment);
			session.getTransaction().commit();
			session.clear();
			session.close();
	 }
	 

}