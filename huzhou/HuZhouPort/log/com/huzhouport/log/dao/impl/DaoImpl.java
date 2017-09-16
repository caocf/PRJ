package com.huzhouport.log.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huzhouport.log.dao.Dao;
import com.huzhouport.log.model.Log;
import com.huzhouport.log.model.LogStyle;
import com.huzhouport.log.model.PageModel;
import com.huzhouport.log.model.QueryCount;
import com.huzhouport.log.model.Logbean;









public class DaoImpl extends HibernateDaoSupport implements Dao{
	
	
	

	

	

	



//日志分页
	public PageModel findByPageScroll(String hql, int firstPage, int maxPage,String action) {
		PageModel pm = new PageModel();
	    Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Query query = session.createQuery(hql);
		//session.createSQLQuery(hql);
        query.setFirstResult(firstPage);
		query.setMaxResults(maxPage);
		ArrayList  list=(ArrayList) query.list();
		Iterator iterator1 = list.iterator();
		Logbean st ;
		List<Logbean> list1 = new ArrayList<Logbean>();

		while (iterator1.hasNext()){
			st = new Logbean();
			//Object[] object= (Object[]) list.get(i);
		    Object[] object = (Object[]) iterator1.next();
//			int logId= (Integer) object[0];
//		   //String logId = (String) object[0];sa
//		   //	int logId1= Integer.parseInt(logId);
//		    String logUser=(String) object[1];
//			Timestamp logTime=(Timestamp) object[2];
//			String logContent=(String) object[3];
//			int partOfStyle =(Integer) object[4];
//			int styleId =(Integer) object[5];
//			String styleName=(String) object[6];
//			System.out.println("styleName="+styleName);
//			st.setLogId(logId);
//			st.setLogUser(logUser);
//			st.setLogTime(logTime);
//			st.setLogContent(logContent);
//			st.setPartOfStyle(partOfStyle);
//			st.setStyleId(styleId);
//			st.setStyleName(styleName);
//			list1.add(st);
			
			Log log = (Log) object[0]; 
			LogStyle logStyle = (LogStyle) object[1]; 
			st.setLogId(log.getLogId());
			st.setLogUser(log.getLogUser());
			st.setLogTime(log.getLogTime());
			st.setLogContent(log.getLogContent());
		    st.setPartOfStyle(log.getPartOfStyle());
		    st.setIsApp(log.getIsApp());
			st.setStyleId(logStyle.getStyleId());
			st.setStyleName(logStyle.getStyleName());
			list1.add(st);
			
			
			
	}
	//
		
		
		pm.setRecordsDate( list1);
		
		
		pm.setAction(action);
		pm.setTotal(((Long)QueryCount.getQueryCount(hql, session)).intValue());
		pm.setCurrentPage(firstPage);
		pm.setManPage(maxPage);
		
		session.clear();
		session.close();
		return pm;
	}
	
	public void delete(String hql){  //日志删除
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		session.beginTransaction();  
		Query query =session.createQuery(hql);
		query.executeUpdate();
		session.getTransaction().commit();
		session.clear();
		session.close();
	}
	public boolean  logsave(Log log){
		HibernateTemplate ht = this.getHibernateTemplate();
		SessionFactory factory = ht.getSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();
	
		try{
		session.save(log);
		session.getTransaction().commit();
		return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		finally {
			session.clear();
			session.close();
		}
		
		
	}
	
	
}

