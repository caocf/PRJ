package com.huzhouport.leaveandovertime.dao.impl;




import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;



import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.leaveandovertime.dao.Dao;
import com.huzhouport.leaveandovertime.model.LeaveOrOt;
import com.huzhouport.leaveandovertime.model.LeaveOrOtApproval;
import com.huzhouport.leaveandovertime.model.LeaveOrOtApprovalbean;
import com.huzhouport.leaveandovertime.model.LeaveOrOtKind;
import com.huzhouport.leaveandovertime.model.LeaveOrOtKindbean;



import com.huzhouport.log.model.PageModel;
import com.huzhouport.log.model.QueryCount;
import com.huzhouport.pushmsg.model.PushMsg;

import com.huzhouport.user.model.User;









public class DaoImpl extends HibernateDaoSupport  implements Dao{
	public PageModel findByPageScroll(String hql, int firstPage, int maxPage,String action) {
		PageModel pm = new PageModel();

	    Session session = this.getHibernateTemplate().getSessionFactory().openSession();

		Query query = session.createQuery(hql);

        query.setFirstResult(firstPage);
		query.setMaxResults(maxPage);
		ArrayList  list=(ArrayList) query.list();
		Iterator iterator1 = list.iterator();	
	
		
		int approvalID;
		String approvalName;
		String approvalresult = null;
		LeaveOrOtKindbean st;
		List<LeaveOrOtKindbean> list1 = new ArrayList<LeaveOrOtKindbean>();
		while (iterator1.hasNext()){
			st = new LeaveOrOtKindbean();
		    Object[] object = (Object[]) iterator1.next();
			LeaveOrOt l=(LeaveOrOt) object[0];
			LeaveOrOtKind lk=(LeaveOrOtKind) object[1];
			User u =(User) object[2];
			
			st.setKindID(lk.getKindID());
			st.setKindName(lk.getKindName());
			st.setKindType(lk.getKindType());
			st.setDefauleDate(lk.getDefauleDate());
			st.setLeaveOrOtID(l.getLeaveOrOtID());
			st.setLeaveOrOtKind(l.getLeaveOrOtKind());
			st.setLeaveOrOtUser(u.getName());
			
//			String name=findUsername(l.getLeaveOrOtUser());
//			st.setLeaveOrOtUser(name);
			
			st.setLeaveOrOtReason(l.getLeaveOrOtReason());
			st.setLeaveOrOtDate(l.getLeaveOrOtDate());
			st.setBeginDate(l.getBeginDate());
			st.setLastDate(l.getLastDate());
			st.setEndDate(l.getEndDate());

			int result=l.getApprovalResult();
			if(result==1){
				approvalresult=GlobalVar.AWAITING_APPROVAL;
			}
			if(result==2){
				approvalresult=GlobalVar.ISPENDING;
			}
			if(result==3){
				approvalresult=GlobalVar.REJECT;
			}
			if(result==4){
				approvalresult=GlobalVar.APPROVED;
			}
			st.setApprovalResult(approvalresult);
			

			//审批人id name 
			approvalID=l.getApprovalID1();
			
			
		/*if(l.getApprovalResult3()!=0){
			approvalID=l.getApprovalID3();
		}else{
		   if(l.getApprovalID3()==0){
			   if(l.getApprovalResult2()!=0){
				   approvalID=l.getApprovalID2(); 
			   }else{
				   if(l.getApprovalID2()==0){
					   approvalID=l.getApprovalID1();
				   }else{
					   if(l.getApprovalResult1()!=0){
						   approvalID=l.getApprovalID2();
					   }else{
						   approvalID=l.getApprovalID1();
					   }
				   }
			   } 
		   }else{
			   if(l.getApprovalResult2()!=0){
				   approvalID=l.getApprovalID3(); 
			   }else{
				   if(l.getApprovalID2()==0){
					   approvalID=l.getApprovalID1();
				   }else{
					   if(l.getApprovalResult1()!=0){
						   approvalID=l.getApprovalID2();
					   }else{
						   approvalID=l.getApprovalID1();
					   }
				   }
			   }
		   }
		}*/
		approvalName=findUsername(approvalID,session);

			st.setApprovalID(approvalID);
	        st.setApprovalName(approvalName);
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
	

	

	
	
	
public String findUsername(int id ,Session session){ //通过id 获得user表的name
	    String hql="from User u where u.userId="+id;
	   // Session session=getHibernateTemplate().getSessionFactory().openSession();  //getCurrentSession();获取当前存在的 
		Query query=session.createQuery(hql);
		List<User>list=query.list();
		String name=list.get(0).getName();
	return name;
	}
	
public List findByUsername(String hql){
	
	
	 Session session=this.getHibernateTemplate().getSessionFactory().openSession();
		
		Query query=session.createQuery(hql);
		List list=query.list();
		//User user=(User)list.get(0);
		//String name=user.getName();
		session.clear();
		session.close();
	
	
	return list;
	
}

public LeaveOrOtKindbean findByLeaveOrOtKindbean(String hql){
	 Session session = this.getHibernateTemplate().getSessionFactory().openSession();
	Query query=session.createQuery(hql);
	ArrayList  list=(ArrayList) query.list();
	Iterator iterator1 = list.iterator();	
	 System.out.println("size="+list.size());
	
	 String 	name1;
		String 	name2;
		String 	name3;
	String approvalName;
	String approvalresult = null;
	LeaveOrOtKindbean st;
	List<LeaveOrOtKindbean> list1 = new ArrayList<LeaveOrOtKindbean>();
	//for (int i=0;i<list.size();i++){
	while (iterator1.hasNext()){
		st = new LeaveOrOtKindbean();
		//Object[] object= (Object[]) list.get(i);
	    Object[] object = (Object[]) iterator1.next();
	//	System.out.println("length="+object.length);
//	
		
		LeaveOrOt l=(LeaveOrOt) object[0];
		LeaveOrOtKind lk=(LeaveOrOtKind) object[1];
	//	System.out.println("hihi="+l.getApprovalID1());
		st.setKindID(lk.getKindID());
		st.setKindName(lk.getKindName());
		st.setKindType(lk.getKindType());
		st.setDefauleDate(lk.getDefauleDate());
		
		st.setLeaveOrOtID(l.getLeaveOrOtID());
		st.setLeaveOrOtKind(l.getLeaveOrOtKind());
		//st.setLeaveOrOtUser(l.getLeaveOrOtUser());
		String name=findUsername(l.getLeaveOrOtUser(),session);
		st.setLeaveOrOtUser(name);
		st.setLeaveOrOtReason(l.getLeaveOrOtReason());
		st.setLeaveOrOtDate(l.getLeaveOrOtDate());
		st.setBeginDate(l.getBeginDate());
		st.setLastDate(l.getLastDate());
		st.setEndDate(l.getEndDate());
		
		st.setApprovalTime1(l.getApprovalTime1());
		st.setApprovalTime2(l.getApprovalTime2());
		st.setApprovalTime3(l.getApprovalTime3());
		
		if(l.getApprovalID1()!=0){
			
		name1=findUsername(l.getApprovalID1(),session);
			//System.out.println("name="+name);
	       st.setApprovalID1(name1);
		}else{name1="0"; st.setApprovalID1(name1);}
		
		st.setApprovalResult1(l.getApprovalResult1());
		st.setApprovalOpinion1(l.getApprovalOpinion1());
        if(l.getApprovalID2()!=0){
			
			name2=findUsername(l.getApprovalID2(),session);
			//System.out.println("name="+name);
	       st.setApprovalID2(name2);
		}else{name2="0";st.setApprovalID2(name2);}
        st.setApprovalResult2(l.getApprovalResult2());
		st.setApprovalOpinion2(l.getApprovalOpinion2());
		if(l.getApprovalID3()!=0){
				
				name3=findUsername(l.getApprovalID3(),session);
				//System.out.println("name="+name);
		       st.setApprovalID3(name3);
		}
		else{name3="0";st.setApprovalID3(name3);}
		
	    st.setApprovalResult3(l.getApprovalResult3());
		st.setApprovalOpinion3(l.getApprovalOpinion3());
	    
		//st.setApprovalResult(l.getApprovalResult());
		int result=l.getApprovalResult();
		
		if(result==1){
			approvalresult=GlobalVar.AWAITING_APPROVAL;
		}
		if(result==2){
			approvalresult=GlobalVar.ISPENDING;
		}
		if(result==3){
			approvalresult=GlobalVar.REJECT;
		}
		if(result==4){
			approvalresult=GlobalVar.APPROVED;
		}
		st.setApprovalResult(approvalresult);
		
		
		System.out.println("name1+name2+name3="+name1+" "+name2+"  "+name3);
		
		/*//取到审批人	
		if(name2=="0"){
			st.setApprovalID(l.getApprovalID1());
			approvalName=name1;	
			
		}else{
			if(l.getApprovalResult1()==0){
				st.setApprovalID(l.getApprovalID1());
				approvalName=name1;	
			}else{
			   if(l.getApprovalResult2()==0){
				   st.setApprovalID(l.getApprovalID2());
					approvalName=name2;	  
			   }else{
				   if(name3=="0"){
					   st.setApprovalID(l.getApprovalID2());
						approvalName=name2;	    
				   }else{
					   st.setApprovalID(l.getApprovalID3());
						approvalName=name3;
				   }
			   }
			}
			
		}*/
		
		//取到审批人	
			st.setApprovalID(l.getApprovalID1());
			approvalName=name1;	
        st.setApprovalName(approvalName);
        
        st.setAddress(l.getAddress());
		list1.add(st);
		
		
		
}
	LeaveOrOtKindbean 	leaveOrOtKindbean=list1.get(0);
	session.clear();
	session.close();
	return leaveOrOtKindbean;
}


public List<LeaveOrOt> findByLeaveOrOt(String hql){
	 Session session=this.getHibernateTemplate().getSessionFactory().openSession();
		
		Query query=session.createQuery(hql);
		List list=query.list();
		
		//String name=user.getName();
		session.clear();
		session.close();
	
	
	return list;
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

public LeaveOrOtKind findByKindID(String hql){
	 Session session=this.getHibernateTemplate().getSessionFactory().openSession();
		
		Query query=session.createQuery(hql);
		List list=query.list();
		LeaveOrOtKind leaveOrOtKind=(LeaveOrOtKind) list.get(0);
		session.clear();
		session.close();
	
	
	return leaveOrOtKind;
}

public void saveLeaveOrOt(LeaveOrOt leaveOrOt){
	HibernateTemplate ht = this.getHibernateTemplate();
	SessionFactory factory = ht.getSessionFactory();
	Session session = factory.openSession();
	session.beginTransaction();
	session.save(leaveOrOt);
	session.getTransaction().commit();
	
	//add by Grond Start
	PushMsg pushMsg=new PushMsg();
	pushMsg.setPushmsgtime(new Date());
	pushMsg.setMsgstatus(1);//消息状态自定义：1未推送未读；2未推送已读，3已推送未读；4已推送已读；消息轮询主要是未推送的。
	pushMsg.setModulerid(3);//模块ID，自定义：1为来自日程安排的消息；2为来自通知公告的消息；3为来自请假申请表的消息
	pushMsg.setMessageid(leaveOrOt.getLeaveOrOtID());//消息内容ID，是日程安排表、请假申请表、通知公告信息表的外键
	pushMsg.setUserid(leaveOrOt.getApprovalID1());
	savapushMsgObject(pushMsg,session);
	//add by Grond End

	session.clear();
	session.close();
}
//add by Grond Start

public void savapushMsgObject(PushMsg pushMsg,Session session){

	session.beginTransaction();
	session.save(pushMsg);
	session.getTransaction().commit();

}
//add by Grond End

public List<LeaveOrOtKindbean> findByuserid1(String hql){
	 Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Query query=session.createQuery(hql);
		ArrayList  list=(ArrayList) query.list();
		Iterator iterator1 = list.iterator();	
		 System.out.println("size="+list.size());
		
		 String approvalresult = null;
		 String 	name1;
			String 	name2;
			String 	name3;
		String approvalName;
		LeaveOrOtKindbean st;
		List<LeaveOrOtKindbean> list1 = new ArrayList<LeaveOrOtKindbean>();
		while (iterator1.hasNext()){
			st = new LeaveOrOtKindbean();
			//Object[] object= (Object[]) list.get(i);
		    Object[] object = (Object[]) iterator1.next();
		//	System.out.println("length="+object.length);
	//	
			
			LeaveOrOt l=(LeaveOrOt) object[0];
			LeaveOrOtKind lk=(LeaveOrOtKind) object[1];
		
			//st.setKindID(lk.getKindID());
			st.setKindName(lk.getKindName());
			st.setKindType(lk.getKindType());
			//st.setDefauleDate(lk.getDefauleDate());
			
			st.setLeaveOrOtID(l.getLeaveOrOtID());
			//st.setLeaveOrOtKind(l.getLeaveOrOtKind());
			String name=findUsername(l.getLeaveOrOtUser(),session);
			st.setLeaveOrOtUser(name);
			//System.out.println(name);
			st.setLeaveOrOtReason(l.getLeaveOrOtReason());
			st.setLeaveOrOtDate(l.getLeaveOrOtDate());
			st.setBeginDate(l.getBeginDate());
			//st.setLastDate(l.getLastDate());
			st.setEndDate(l.getEndDate());
			int result=l.getApprovalResult();
			
			if(result==1){
				approvalresult=GlobalVar.AWAITING_APPROVAL;
			}
			if(result==2){
				approvalresult=GlobalVar.ISPENDING;
			}
			if(result==3){
				approvalresult=GlobalVar.REJECT;
			}
			if(result==4){
				approvalresult=GlobalVar.APPROVED;
			}
			st.setApprovalResult(approvalresult);
			
	if(l.getApprovalID1()!=0){
				
				name1=findUsername(l.getApprovalID1(),session);
					//System.out.println("name="+name);
			       st.setApprovalID1(name1);
				}else{name1="0";}
				
				st.setApprovalResult1(l.getApprovalResult1());
				st.setApprovalOpinion1(l.getApprovalOpinion1());
		        if(l.getApprovalID2()!=0){
					
					name2=findUsername(l.getApprovalID2(),session);
					//System.out.println("name="+name);
			       st.setApprovalID2(name2);
				}else{name2="0";}
		        st.setApprovalResult2(l.getApprovalResult2());
				st.setApprovalOpinion2(l.getApprovalOpinion2());
				if(l.getApprovalID3()!=0){
						
						name3=findUsername(l.getApprovalID3(),session);
						//System.out.println("name="+name);
				       st.setApprovalID3(name3);
				}
				else{name3="0";}
				
				//取到审批人	
				st.setApprovalID(l.getApprovalID1());
				approvalName=name1;	
/*
				//取到审批人	
				if(name2=="0"){
					st.setApprovalID(l.getApprovalID1());
					approvalName=name1;	
					
				}else{
					if(l.getApprovalResult1()==0){
						st.setApprovalID(l.getApprovalID1());
						approvalName=name1;	
					}else{
					   if(l.getApprovalResult2()==0){
						   st.setApprovalID(l.getApprovalID2());
							approvalName=name2;	  
					   }else{
						   if(name3=="0"){
							   st.setApprovalID(l.getApprovalID2());
								approvalName=name2;	    
						   }else{
							   st.setApprovalID(l.getApprovalID3());
								approvalName=name3;
						   }
					   }
					}
					
				}*/
			st.setApprovalName(approvalName);
			list1.add(st);
		}
		session.clear();
		session.close();
		return list1;
} 
public List<LeaveOrOtKindbean> findByuserid3(String hql){
	 Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Query query=session.createQuery(hql);
		ArrayList  list=(ArrayList) query.list();
		Iterator iterator1 = list.iterator();	
		 System.out.println("size="+list.size());
		
		 String approvalresult = null;
		 String 	name1;
			String 	name2;
			String 	name3;
		String approvalName;
		LeaveOrOtKindbean st;
		List<LeaveOrOtKindbean> list1 = new ArrayList<LeaveOrOtKindbean>();
		while (iterator1.hasNext()){
			st = new LeaveOrOtKindbean();
			//Object[] object= (Object[]) list.get(i);
		    Object[] object = (Object[]) iterator1.next();
		//	System.out.println("length="+object.length);
	//	
			
			LeaveOrOt l=(LeaveOrOt) object[0];
			LeaveOrOtKind lk=(LeaveOrOtKind) object[1];
		
			//st.setKindID(lk.getKindID());
			st.setKindName(lk.getKindName());
			st.setKindType(lk.getKindType());
			//st.setDefauleDate(lk.getDefauleDate());
			
			st.setLeaveOrOtID(l.getLeaveOrOtID());
			//st.setLeaveOrOtKind(l.getLeaveOrOtKind());
			String name=findUsername(l.getLeaveOrOtUser(),session);
			st.setLeaveOrOtUser(name);
			st.setLeaveOrOtReason(l.getLeaveOrOtReason());
			st.setLeaveOrOtDate(l.getLeaveOrOtDate());
			st.setBeginDate(l.getBeginDate());
			//st.setLastDate(l.getLastDate());
			st.setEndDate(l.getEndDate());
			int result=l.getApprovalResult();
			
			if(result==1){
				approvalresult=GlobalVar.AWAITING_APPROVAL;
			}
			if(result==2){
				approvalresult=GlobalVar.ISPENDING;
			}
			if(result==3){
				approvalresult=GlobalVar.REJECT;
			}
			if(result==4){
				approvalresult=GlobalVar.APPROVED;
			}
			st.setApprovalResult(approvalresult);
			
			
			if(l.getApprovalID1()!=0){
				
				name1=findUsername(l.getApprovalID1(),session);
					//System.out.println("name="+name);
			       st.setApprovalID1(name1);
				}else{name1="0";}
				
				st.setApprovalResult1(l.getApprovalResult1());
				st.setApprovalOpinion1(l.getApprovalOpinion1());
		        if(l.getApprovalID2()!=0){
					
					name2=findUsername(l.getApprovalID2(),session);
					//System.out.println("name="+name);
			       st.setApprovalID2(name2);
				}else{name2="0";}
		        st.setApprovalResult2(l.getApprovalResult2());
				st.setApprovalOpinion2(l.getApprovalOpinion2());
				if(l.getApprovalID3()!=0){
						
						name3=findUsername(l.getApprovalID3(),session);
						//System.out.println("name="+name);
				       st.setApprovalID3(name3);
				}
				else{name3="0";}
				
				//取到审批人	
					st.setApprovalID(l.getApprovalID1());
					approvalName=name1;	
					

			/*	//取到审批人	
				if(name2=="0"){
					st.setApprovalID(l.getApprovalID1());
					approvalName=name1;	
					
				}else{
					if(l.getApprovalResult1()==0){
						st.setApprovalID(l.getApprovalID1());
						approvalName=name1;	
					}else{
					   if(l.getApprovalResult2()==0){
						   st.setApprovalID(l.getApprovalID2());
							approvalName=name2;	  
					   }else{
						   if(name3=="0"){
							   st.setApprovalID(l.getApprovalID2());
								approvalName=name2;	    
						   }else{
							   st.setApprovalID(l.getApprovalID3());
								approvalName=name3;
						   }
					   }
					}
					
				}*/
			
			
	        st.setApprovalName(approvalName);
			list1.add(st);
		}
		session.clear();
		session.close();
		return list1;
} 

public List<LeaveOrOtKind> findByKindName(String hql){
	 Session session=this.getHibernateTemplate().getSessionFactory().openSession();
		
		Query query=session.createQuery(hql);
		List list=query.list();
		//LeaveOrOtKind leaveOrOtKind=(LeaveOrOtKind) list.get(0);
		session.clear();
		session.close();
	
	
	return list;
}

public List<LeaveOrOtKindbean> selectByuserid1(String hql){
	 Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Query query=session.createQuery(hql);
		ArrayList  list=(ArrayList) query.list();
		Iterator iterator1 = list.iterator();	
		 System.out.println("size="+list.size());
		
		 String approvalresult = null;
		 String 	name1;
			String 	name2;
			String 	name3;
		String approvalName;
		 
		LeaveOrOtKindbean st;
		List<LeaveOrOtKindbean> list1 = new ArrayList<LeaveOrOtKindbean>();
		while (iterator1.hasNext()){
			st = new LeaveOrOtKindbean();
			//Object[] object= (Object[]) list.get(i);
		    Object[] object = (Object[]) iterator1.next();
		//	System.out.println("length="+object.length);
	//	
			
			LeaveOrOt l=(LeaveOrOt) object[0];
			LeaveOrOtKind lk=(LeaveOrOtKind) object[1];
			User u=(User) object[2];
		
			//st.setKindID(lk.getKindID());
			st.setKindName(lk.getKindName());
			st.setKindType(lk.getKindType());
			//st.setDefauleDate(lk.getDefauleDate());
			
			st.setLeaveOrOtID(l.getLeaveOrOtID());
			st.setLeaveOrOtUser(u.getName());
			//System.out.println(name);
			st.setLeaveOrOtReason(l.getLeaveOrOtReason());
			st.setLeaveOrOtDate(l.getLeaveOrOtDate());
			st.setBeginDate(l.getBeginDate());
			//st.setLastDate(l.getLastDate());
			st.setEndDate(l.getEndDate());
			int result=l.getApprovalResult();
			
			if(result==1){
				approvalresult=GlobalVar.AWAITING_APPROVAL;
			}
			if(result==2){
				approvalresult=GlobalVar.ISPENDING;
			}
			if(result==3){
				approvalresult=GlobalVar.REJECT;
			}
			if(result==4){
				approvalresult=GlobalVar.APPROVED;
			}
			st.setApprovalResult(approvalresult);
			
if(l.getApprovalID1()!=0){
				
				name1=findUsername(l.getApprovalID1(),session);
					//System.out.println("name="+name);
			       st.setApprovalID1(name1);
				}else{name1="0";}
				
				st.setApprovalResult1(l.getApprovalResult1());
				st.setApprovalOpinion1(l.getApprovalOpinion1());
		        if(l.getApprovalID2()!=0){
					
					name2=findUsername(l.getApprovalID2(),session);
					//System.out.println("name="+name);
			       st.setApprovalID2(name2);
				}else{name2="0";}
		        st.setApprovalResult2(l.getApprovalResult2());
				st.setApprovalOpinion2(l.getApprovalOpinion2());
				if(l.getApprovalID3()!=0){
						
						name3=findUsername(l.getApprovalID3(),session);
						//System.out.println("name="+name);
				       st.setApprovalID3(name3);
				}
				else{name3="0";}
				
				

				//取到审批人	
					st.setApprovalID(l.getApprovalID1());
					approvalName=name1;	
	        st.setApprovalName(approvalName);
			list1.add(st);
		}
		session.clear();
		session.close();
		return list1;
} 

public List<LeaveOrOtApprovalbean> leaveOrOtApproval(String hql){
	
	 Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Query query=session.createQuery(hql);
		List<LeaveOrOtApproval>list=query.list();
	    
		if(list.size()==0){
			List<LeaveOrOtApprovalbean> list1 = new ArrayList<LeaveOrOtApprovalbean>();
			return list1;
		}else{
		LeaveOrOtApprovalbean st;
		List<LeaveOrOtApprovalbean> list1 = new ArrayList<LeaveOrOtApprovalbean>();
			st = new LeaveOrOtApprovalbean();

			
			st.setUserID(list.get(0).getUserID());
			st.setApproval1(list.get(0).getApproval1());
			st.setApproval2(list.get(0).getApproval2());
			st.setApproval3(list.get(0).getApproval3());
			
			if(list.get(0).getApproval1()==0){
				st.setApprovalName1("0");
			}else{
			st.setApprovalName1(findUsername(list.get(0).getApproval1(),session));
			}
			if(list.get(0).getApproval2()==0){
				st.setApprovalName2("0");
			}else{
			st.setApprovalName2(findUsername(list.get(0).getApproval2(),session));
			}
			if(list.get(0).getApproval3()==0){
				st.setApprovalName3("0");
			}else{
			st.setApprovalName3(findUsername(list.get(0).getApproval3(),session));
			}
			
			list1.add(st);

			session.clear();
			session.close();
			
		return list1;
		}	
}
	
public void saveLeaveOrOtApproval (LeaveOrOtApproval approval){
	HibernateTemplate ht = this.getHibernateTemplate();
	SessionFactory factory = ht.getSessionFactory();
	Session session = factory.openSession();
	session.beginTransaction();
	session.save(approval);
	session.getTransaction().commit();
	session.clear();
	session.close();
}

}

