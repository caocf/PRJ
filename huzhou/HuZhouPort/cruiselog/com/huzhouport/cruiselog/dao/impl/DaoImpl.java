package com.huzhouport.cruiselog.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.huzhouport.attendace.model.Location;
import com.huzhouport.cruiselog.dao.Dao;
import com.huzhouport.cruiselog.model.CruiseIssue;
import com.huzhouport.cruiselog.model.CruiseLog;
import com.huzhouport.cruiselog.model.CruiseLogBean;
import com.huzhouport.cruiselog.model.CruiseLogLink;
import com.huzhouport.cruiselog.model.CruiseLogLocationLink;
import com.huzhouport.cruiselog.model.CruiseRecord;
import com.huzhouport.cruiselog.model.IllegalBean;
import com.huzhouport.illegal.model.Illegal;
import com.huzhouport.log.model.PageModel;
import com.huzhouport.log.model.QueryCount;
import com.huzhouport.user.model.User;


public class DaoImpl extends HibernateDaoSupport implements Dao
{
	public PageModel findByPageScroll(String hql, int firstPage, int maxPage) 
	{		
		PageModel pm = new PageModel();
	    Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Query query = session.createQuery(hql);
        query.setFirstResult(firstPage);
		query.setMaxResults(maxPage);
		ArrayList  list=(ArrayList) query.list();
		Iterator iterator1 = list.iterator();	
		 System.out.println("size="+list.size());

		 CruiseLogBean st;
		List<CruiseLogBean> list1 = new ArrayList<CruiseLogBean>();
		//for (int i=0;i<list.size();i++){
		while (iterator1.hasNext()){
			st = new CruiseLogBean();
			//Object[] object= (Object[]) list.get(i);
		    Object object = (Object) iterator1.next();
		//	System.out.println("length="+object.length);
		    CruiseLog c= (CruiseLog) object;
      	    
      	    st.setCruiseLogID(c.getCruiseLogID());
      	    st.setCruiseLogUser(c.getCruiseLogUser());
      	    
      	    String[] sub=c.getCruiseLogUser().split(",");
      	     System.out.println("sub.length=="+sub.length+" "+c.getCruiseLogUser());
            String name="";
      	    for(int i=0;i<sub.length ;i++){
      	    	if(name.equals("")){
      	    	name=name+findUsername(sub[i]);	
      	    	}else{
      	         name=name+","+findUsername(sub[i]);
      	    	}
      	    	}
      	    
      	    st.setCruiseLogUserName(name);
      	    st.setCruiseLogLoaction(c.getCruiseLogLoaction());
      	    st.setStartTime(c.getStartTime());
      	    st.setEndTime(c.getEndTime());
		
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
	
	
public PageModel findByPageScroll1(String hql, int firstPage, int maxPage,List list11,String content) {
		
		PageModel pm = new PageModel();
	    Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Query query = session.createQuery(hql);
        query.setFirstResult(firstPage);
		query.setMaxResults(maxPage);
		ArrayList  list=(ArrayList) query.list();
		Iterator iterator1 = list.iterator();	
		 System.out.println("size="+list.size());

		 CruiseLogBean st;
		List<CruiseLogBean> list1 = new ArrayList<CruiseLogBean>();
		//for (int i=0;i<list.size();i++){
		while (iterator1.hasNext()){
			st = new CruiseLogBean();
			//Object[] object= (Object[]) list.get(i);
		    Object object = (Object) iterator1.next();
		//	System.out.println("length="+object.length);
		    CruiseLog c= (CruiseLog) object;
      	    
      	    st.setCruiseLogID(c.getCruiseLogID());
      	    st.setCruiseLogUser(c.getCruiseLogUser());
      	    
      	    String[] sub=c.getCruiseLogUser().split(",");
      	   //  System.out.println("sub.length=="+sub.length+" "+c.getCruiseLogUser());
            String name="";
      	    for(int i=0;i<sub.length ;i++){
      	    	if(name.equals("")){
      	    	name=name+findUsername(sub[i]);	
      	    	}else{
      	         name=name+","+findUsername(sub[i]);
      	    	}
      	    	}
      	    
      	    st.setCruiseLogUserName(name);
      	    st.setCruiseLogLoaction(c.getCruiseLogLoaction());
      	    st.setStartTime(c.getStartTime());
      	    st.setEndTime(c.getEndTime());
		
      	    //去掉模糊查找name 产生的bug
      	    int s=0;
      	    if(list11.size()==0){
      	    list1.add(st);
      	    }else{
      	    	for(int i=0;i<list11.size();i++){
      	    	String userid=list11.get(i).toString();
      	    	   for(int j=0;j<sub.length;j++){
      	    		 
      	    		   if(userid.equals(sub[j])){

      	    			  s=1;
      	    			  break;
      	    		   }
      	    	   }
      	    	}
      	    	
      	    	if(s==1||c.getCruiseLogLoaction().contains(content)){
      	          list1.add(st);
      	    	}
      	    	
      	    	
      	    }
			
	}
	
		
		
		
		pm.setRecordsDate(list1);
		
	
		
	
		pm.setTotal(((Long)QueryCount.getQueryCount(hql, session)).intValue());
		pm.setCurrentPage(firstPage);
		pm.setManPage(maxPage);
		session.clear();
		session.close();
		return pm;
	}		
	//通过id 获得user表的name
	public String findUsername(String id){ 
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
	public List<User> findByUsername(String hql){
		
		
		 Session session=this.getHibernateTemplate().getSessionFactory().openSession();
			
			Query query=session.createQuery(hql);
			List list=query.list();
			//User user=(User)list.get(0);
			//String name=user.getName();
			session.clear();
			session.close();
		
		
		return list;
		
	}
	
	public CruiseLogBean findBycruiseLogID(String hql){
		Session session=this.getHibernateTemplate().getSessionFactory().openSession();
		
		Query query=session.createQuery(hql);
		List list=query.list();
		CruiseLog cruiselog=(CruiseLog) list.get(0);
		CruiseLogBean cruiselogbean=new CruiseLogBean();
		
		cruiselogbean.setCruiseLogID(cruiselog.getCruiseLogID());
		cruiselogbean.setCruiseLogUser(cruiselog.getCruiseLogUser());
		String[] sub=cruiselog.getCruiseLogUser().split(",");
   	   //  System.out.println("sub.length=="+sub.length+" "+c.getCruiseLogUser());
         String name="";
   	    for(int i=0;i<sub.length ;i++){
   	    	if(name.equals("")){
   	    	name=name+findUsername(sub[i]);	
   	    	}else{
   	         name=name+","+findUsername(sub[i]);
   	    	}
   	    	}
   	  
   	 cruiselogbean.setShipName(cruiselog.getShipName());
   	 cruiselogbean.setCruiseLogUserName(name);
   	 cruiselogbean.setCruiseLogLoaction(cruiselog.getCruiseLogLoaction());
   	 cruiselogbean.setStartTime(cruiselog.getStartTime());
   	 cruiselogbean.setEndTime(cruiselog.getEndTime());
		
		session.clear();
		session.close();
		return cruiselogbean;
	}
	
	public List<IllegalBean> findIllegalList(String hql){
		
	    Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Query query = session.createQuery(hql);
		ArrayList  list=(ArrayList) query.list();
		Iterator iterator1 = list.iterator();	
		 System.out.println("size="+list.size());
		 IllegalBean st;
		List<IllegalBean> list1 = new ArrayList<IllegalBean>();
		
		while (iterator1.hasNext()){
			st = new IllegalBean();
			Object[] object = (Object[]) iterator1.next();
		   
		
		   
		    Illegal i= (Illegal) object[0];
		    CruiseLogLink cl= (CruiseLogLink) object[1];
		    Location l=(Location) object[2];
		    
		    
      	    st.setIllegalId(i.getIllegalId());
      	    st.setEnforecers1(i.getEnforecers1());
      	    st.setEnforecersname1(findUsername(i.getEnforecers1()+""));
      	    st.setEnforecers2(i.getEnforecers2());
      	    st.setEnforecersname2(findUsername(i.getEnforecers2()+""));
      	    st.setIllegalLocation(i.getIllegalLocation());
      	    st.setIllegalCategories(i.getIllegalCategories());
      	    st.setReviewWether(i.getReviewWether());
      	    st.setReviewUser(i.getReviewUser());
      	    st.setIllegalObject(i.getIllegalObject());
      	    st.setIllegalReason(i.getIllegalReason());
      	    st.setIllegalContent(i.getIllegalContent());
      	    st.setIllegalTime(i.getIllegalTime());
      	    st.setLocationName(l.getLocationName());
		    
		    if(i.getReviewUser()==0){
		    	st.setReviewUserName("0");
		    }else{
		    	st.setReviewUserName(findUsername(i.getReviewUser()+""));
		    }
      	    
		    
			list1.add(st);
			
			
	}
		session.clear();
		session.close();
		return list1;
	}
public List<CruiseLogBean> showCruiseLogUnfinish(String hql){
		
	    Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Query query = session.createQuery(hql);       
		ArrayList  list=(ArrayList) query.list();
		Iterator iterator1 = list.iterator();	

		 CruiseLogBean st;
		List<CruiseLogBean> list1 = new ArrayList<CruiseLogBean>();
		//for (int i=0;i<list.size();i++){
		while (iterator1.hasNext()){
			st = new CruiseLogBean();
			//Object[] object= (Object[]) list.get(i);
		    Object object = (Object) iterator1.next();
		//	System.out.println("length="+object.length);
		    CruiseLog c= (CruiseLog) object;
      	    
      	    st.setCruiseLogID(c.getCruiseLogID());
      	    st.setCruiseLogUser(c.getCruiseLogUser());
      	    
      	    String[] sub=c.getCruiseLogUser().split(",");
      	// System.out.println("sub.length=="+sub.length+" "+c.getCruiseLogUser());
            String name="";
      	    for(int i=0;i<sub.length ;i++){
      	    	if(name.equals("")){
      	    	name=name+findUsername(sub[i]);	
      	    	}else{
      	         name=name+","+findUsername(sub[i]);
      	    	}
      	    	}
      	    
      	    st.setCruiseLogUserName(name);
      	    st.setCruiseLogLoaction(c.getCruiseLogLoaction());
      	    st.setStartTime(c.getStartTime());
      	    st.setEndTime(c.getEndTime());
		
      	 
      	    
      	    
			
      	  list1.add(st);
			
			
	}
		session.clear();
		session.close();
	return list1;	
		
		
	}
	public List<CruiseLogBean> showCruiseLogUnfinish1(String hql ,List list11,String content){
		
	    Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Query query = session.createQuery(hql);       
		ArrayList  list=(ArrayList) query.list();
		Iterator iterator1 = list.iterator();	

		 CruiseLogBean st;
		List<CruiseLogBean> list1 = new ArrayList<CruiseLogBean>();
		//for (int i=0;i<list.size();i++){
		while (iterator1.hasNext()){
			st = new CruiseLogBean();
			//Object[] object= (Object[]) list.get(i);
		    Object object = (Object) iterator1.next();
		//	System.out.println("length="+object.length);
		    CruiseLog c= (CruiseLog) object;
      	    
      	    st.setCruiseLogID(c.getCruiseLogID());
      	    st.setCruiseLogUser(c.getCruiseLogUser());
      	    
      	    String[] sub=c.getCruiseLogUser().split(",");
      	// System.out.println("sub.length=="+sub.length+" "+c.getCruiseLogUser());
            String name="";
      	    for(int i=0;i<sub.length ;i++){
      	    	if(name.equals("")){
      	    	name=name+findUsername(sub[i]);	
      	    	}else{
      	         name=name+","+findUsername(sub[i]);
      	    	}
      	    	}
      	    
      	    st.setCruiseLogUserName(name);
      	    st.setCruiseLogLoaction(c.getCruiseLogLoaction());
      	    st.setStartTime(c.getStartTime());
      	    st.setEndTime(c.getEndTime());
		
      	    
      	  //去掉模糊查找name 产生的bug
      	    int s=0;
      	    if(list11.size()==0){
      	    list1.add(st);
      	    }else{
      	    	for(int i=0;i<list11.size();i++){
      	    	String userid=list11.get(i).toString();
      	    	   for(int j=0;j<sub.length;j++){
      	    		 
      	    		   if(userid.equals(sub[j])){

      	    			  s=1;
      	    			  break;
      	    		   }
      	    	   }
      	    	}
      	    	
      	    	if(s==1||c.getCruiseLogLoaction().contains(content)){
      	          list1.add(st);
      	    	}
      	    	
      	    	
      	    }
      	    
      	    
			
			
	}
		session.clear();
		session.close();
	return list1;	
		
		
	}
	
	public List<Location> showMap(String hql){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Query query = session.createQuery(hql);       
		ArrayList  list=(ArrayList) query.list();
		Iterator iterator1 = list.iterator();	
		Location st;
		List<Location> list1 = new ArrayList<Location>();
		while (iterator1.hasNext()){
		st = new Location();
		Object[] object = (Object[]) iterator1.next();
		CruiseLogLocationLink c= (CruiseLogLocationLink) object[0];
		Location l= (Location) object[1];
		st.setLocationID(l.getLocationID());
		st.setLongitude(l.getLongitude());
		st.setLatitude(l.getLatitude());
		st.setLocationName(l.getLocationName());
		list1.add(st);
		}
		session.clear();
		session.close();
		return list1;
	}
	
	public List<IllegalBean> findIllegalList1(String hql){
		
	    Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Query query = session.createQuery(hql);
		ArrayList  list=(ArrayList) query.list();
		Iterator iterator1 = list.iterator();	
		 System.out.println("size="+list.size());
		 IllegalBean st;
		List<IllegalBean> list1 = new ArrayList<IllegalBean>();
		
		while (iterator1.hasNext()){
			st = new IllegalBean();
			Object[] object = (Object[]) iterator1.next();
		   
		
		   
		    Illegal i= (Illegal) object[0];
		  
		    Location l=(Location) object[1];
		    
		    
      	    st.setIllegalId(i.getIllegalId());
      	    st.setEnforecers1(i.getEnforecers1());
      	    st.setEnforecersname1(findUsername(i.getEnforecers1()+""));
      	    st.setEnforecers2(i.getEnforecers2());
      	    st.setEnforecersname2(findUsername(i.getEnforecers2()+""));
      	    st.setIllegalLocation(i.getIllegalLocation());
      	    st.setIllegalCategories(i.getIllegalCategories());
      	    st.setReviewWether(i.getReviewWether());
      	    st.setReviewUser(i.getReviewUser());
      	    st.setIllegalObject(i.getIllegalObject());
      	    st.setIllegalReason(i.getIllegalReason());
      	    st.setIllegalContent(i.getIllegalContent());
      	    st.setIllegalTime(i.getIllegalTime());
      	    st.setLocationName(l.getLocationName());
		    
		    if(i.getReviewUser()==0){
		    	st.setReviewUserName("0");
		    }else{
		    	st.setReviewUserName(findUsername(i.getReviewUser()+""));
		    }
      	    
		    
			list1.add(st);
			
			
	}
		session.clear();
		session.close();
		return list1;
	}

	public String insert(CruiseLog cruiselog ){
		HibernateTemplate ht = this.getHibernateTemplate();
		SessionFactory factory = ht.getSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();
		
		session.save(cruiselog);
		session.getTransaction().commit();
	  
		session.clear();
		session.close();
		return  cruiselog.getCruiseLogID()+"";
	}
	public void insert1(CruiseLogLink cruiseloglink ){
		HibernateTemplate ht = this.getHibernateTemplate();
		SessionFactory factory = ht.getSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();
		
		session.save(cruiseloglink);
		session.getTransaction().commit();
	  
		session.clear();
		session.close();
	}
	public void insert2(CruiseLogLocationLink cruiseloglocationlink){
		HibernateTemplate ht = this.getHibernateTemplate();
		SessionFactory factory = ht.getSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();
		
		session.save(cruiseloglocationlink);
		session.getTransaction().commit();
	  
		session.clear();
		session.close();
	}
	
	
	
	public void updateCruiseLog(String hql){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		session.beginTransaction();  
		Query query =session.createQuery(hql);
		query.executeUpdate();
		session.getTransaction().commit();
		session.clear();
		session.close();
	}
	public List<CruiseLogLink> findCruiseLogLink(String hql){
		Session session=this.getHibernateTemplate().getSessionFactory().openSession();
		Query query=session.createQuery(hql);
		List list=query.list();
		session.clear();
		session.close();
		return list;
	}
	public List<CruiseLogLocationLink> findCruiseLogLocationLink(String hql){
		Session session=this.getHibernateTemplate().getSessionFactory().openSession();
		Query query=session.createQuery(hql);
		List list=query.list();
		session.clear();
		session.close();
		return list;
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


	@Override
	public List<?> findIssuesByPid(int pid) 
	{
		String hql="from IssueType it where it.pid="+pid;
		List<?> list=this.getHibernateTemplate().find(hql);
		return list;
	}


	@Override
	public List<?> findChannelsByType(int sfgg) 
	{
		String hql="from CHdHdaojcxx ch where ch.sfgg="+sfgg;
		return this.getHibernateTemplate().find(hql);
	}


	@Override
	public void commitCruise(CruiseRecord cruiseRecord) {
		this.getHibernateTemplate().save(cruiseRecord);
		
	}


	@Override
	public void commitIssue(CruiseIssue cruiseIssue) {
		this.getHibernateTemplate().save(cruiseIssue);
		
	}


	@Override
	public void save(Object obj) {
		this.getHibernateTemplate().save(obj);
		
	}


	@Override
	public void update(Object obj) {
		this.getHibernateTemplate().update(obj);
		
	}


	@Override
	public List<?> findRecordsByUserid(int userid)
	{
		String hql="select cr from CruiseRecord cr left join cr.userids as user where cr.status=1 and user.userId="+userid;
		
		return this.getHibernateTemplate().find(hql);
	}


	@Override
	public List<?> findIssuesByRid(int rid) 
	{
		String hql="select ci,ch,it from CruiseIssue ci,CHdHdaojcxx ch, IssueType it where ci.channelid=ch.id and ci.type=it.id and  ci.recordid="
					+rid;
		//Session session=this.getHibernateTemplate().getSessionFactory().openSession();
		//session.createSQLQuery("").list();
		return this.getHibernateTemplate().find(hql);
	}


	@Override
	public List<?> findEvidenceByIssueID(int rid)
	{
		String hql="from CruiseFile cf where cf.issueid="+rid;
		return this.getHibernateTemplate().find(hql);
	}


	@Override
	public void delete(Object obj) {
		this.getHibernateTemplate().delete(obj);
		
	}


	@Override
	public List<?> findIssuesByUser(int userid) 
	{
		String hql="select it,  ch, ci,cr from IssueType it,  CHdHdaojcxx ch, CruiseIssue as ci,  CruiseRecord as cr left join cr.userids as user where" +
				" ci.type=it.id and ci.channelid=ch.id and cr.status=1 and user.userId="
							+userid+" and ci.recordid=cr.id";
		return this.getHibernateTemplate().find(hql);
	}


	@Override
	public CruiseRecord getCruiseRecordByID(int id)
	{
		String hql="from CruiseRecord cr where cr.id="+id;
		List<CruiseRecord> lsit=this.getHibernateTemplate().find(hql);
		return lsit.get(0);
	}


	@Override
	public List<?> findCruiseToolsByTip(String tip) 
	{
		String hql="from CruiseTools ct where ct.name like '%"+tip+"%'";
		return this.getHibernateTemplate().find(hql);
	}
	
}