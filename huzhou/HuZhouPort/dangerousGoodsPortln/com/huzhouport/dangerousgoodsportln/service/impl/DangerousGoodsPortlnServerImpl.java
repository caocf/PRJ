package com.huzhouport.dangerousgoodsportln.service.impl;



import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;





import com.huzhouport.dangerousgoodsportln.dao.Dao;
import com.huzhouport.dangerousgoodsportln.model.DangerousArrivalDeclare;
import com.huzhouport.dangerousgoodsportln.model.DangerousArrivalDeclareBean;
import com.huzhouport.dangerousgoodsportln.model.Goods;
import com.huzhouport.dangerousgoodsportln.model.GoodsKind;
import com.huzhouport.dangerousgoodsportln.model.Port;
import com.huzhouport.dangerousgoodsportln.service.DangerousGoodsPortlnServer;
import com.huzhouport.log.model.PageModel;
import com.huzhouport.organization.model.Department;
import com.huzhouport.user.model.User;









public class DangerousGoodsPortlnServerImpl implements DangerousGoodsPortlnServer {
	private Dao dao;
	
	
	
	
//	public Dao getDao() {
//		return dao;
//	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	
	
	


	public PageModel<DangerousArrivalDeclareBean> findByScrollServer(int currentPage, int maxPage) { //

		//String hql="from LeaveOrOt l,LeaveOrOtKind lk where l.leaveOrOtKind=lk.kindID order by l.approvalResult";
	String hql ="from Port p1,DangerousArrivalDeclare d,Port p2 where p1.portID=d.startingPort and p2.portID=d.arrivalPort order by d.declareTime desc ";
	
		System.out.println("hql="+hql); 		
		return this.dao.findByPageScroll(hql, currentPage, maxPage);
	}
	public PageModel<DangerousArrivalDeclareBean> findByScrollServer1(int currentPage, int maxPage,String content) { //

		//String hql="from LeaveOrOt l,LeaveOrOtKind lk where l.leaveOrOtKind=lk.kindID order by l.approvalResult";
	     String hql ="from Port p1,DangerousArrivalDeclare d,Port p2 where p1.portID=d.startingPort and p2.portID=d.arrivalPort and(d.shipName like '%"+content+"%' or p1.portName like '%"+content+"%' or p2.portName like '%"+content+"%'or d.cargoTypes like '%"+content+"%' ) order by d.declareTime desc ";
		
		System.out.println("hql="+hql); 		
		return this.dao.findByPageScroll(hql, currentPage, maxPage);
	}
	public DangerousArrivalDeclareBean findByID(String declareID){
    String hql ="from Port p1,DangerousArrivalDeclare d,Port p2 where p1.portID=d.startingPort and p2.portID=d.arrivalPort and d.declareID="+declareID;
				
	return this.dao.findByID(hql);
	}

	 public void updateByID(String declareID,String userid,String reviewResult ,String reviewOpinion){
		 Date now = new Date();
	      DateFormat d8 = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM); //显示日期，时间（精确到分）
	      String declareTime = d8.format(now);
		 String hql ="update from DangerousArrivalDeclare d set d.reviewUser="+userid+" ,d.reviewResult="+reviewResult+",d.reviewOpinion='"+reviewOpinion+"',d.reviewTime='"+declareTime+"' where  d.declareID="+declareID;
			
		 System.out.println("hql="+hql); 
		 dao.update(hql);
	 }
	
	 public void insert(DangerousArrivalDeclare dangerousArrivalDeclare){
		 dao.insert(dangerousArrivalDeclare);
	 }
	 public List<DangerousArrivalDeclareBean> findList(){
		String hql ="from Port p1,DangerousArrivalDeclare d,Port p2 where p1.portID=d.startingPort and p2.portID=d.arrivalPort order by d.declareTime desc ";
				
		return this.dao.findList(hql);
	 }
	 public List<DangerousArrivalDeclareBean> findList1(String content){
		//String hql ="from Port p1,DangerousArrivalDeclare d,Port p2 where p1.portID=d.startingPort and p2.portID=d.arrivalPort and d.reviewResult=0 ";
		 try {
			content=new String(content.getBytes("ISO8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 String hql ="from Port p1,DangerousArrivalDeclare d,Port p2 where p1.portID=d.startingPort and p2.portID=d.arrivalPort and(d.shipName like '%"+content+"%' or p1.portName like '%"+content+"%' or p2.portName like '%"+content+"%'  or d.cargoTypes like '%"+content+"%' ) order by d.declareTime desc ";		
			return this.dao.findList(hql);
		 }
	 
	 public List<DangerousArrivalDeclareBean> findListByname(String userid){
			//String hql ="from Port p1,DangerousArrivalDeclare d,Port p2 where p1.portID=d.startingPort and p2.portID=d.arrivalPort and d.shipName='"+shipName+"' order by d.declareTime desc ";
		 String hql="from Port p1,DangerousArrivalDeclare d,Port p2,ShipBinding s where p1.portID=d.startingPort and p2.portID=d.arrivalPort and d.shipName=s.shipNum and s.shipUser="+userid+" order by d.declareTime desc";
		 return this.dao.findListByuserid(hql);
		 }
		 public List<DangerousArrivalDeclareBean> findListByname1(String content,String userid){
			//String hql ="from Port p1,DangerousArrivalDeclare d,Port p2 where p1.portID=d.startingPort and p2.portID=d.arrivalPort and d.reviewResult=0 ";
			 try {
				content=new String(content.getBytes("ISO8859-1"),"UTF-8");
		
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 String hql ="from Port p1,DangerousArrivalDeclare d,Port p2, ShipBinding s  where p1.portID=d.startingPort and p2.portID=d.arrivalPort and d.shipName=s.shipNum and(d.shipName like '%"+content+"%' or p1.portName like '%"+content+"%' or p2.portName like '%"+content+"%' or d.cargoTypes like '%"+content+"%' ) and s.shipUser="+userid+" order by d.declareTime desc";	
				return this.dao.findListByuserid(hql);
			 }
		 public List<Port> findStartingPortName(String name){
			 String hql ="from Port p where p.portName='"+name+"'";
				
			System.out.println("hql="+hql); 
			return this.dao.findStartingPortName(hql);
		 }
		 public void savePort(Port port){
			 dao.savePort(port);
		 }
		 
		 public List<Department> newaddresslistdepartment(String departmentId){
			String hql="from Department d where d.partOfDepartmentId ="+departmentId;
			return dao.newaddresslistdepartment(hql);
		 }
		 public List<User> newaddresslistuser(String departmentId){
			 String hql="from User u where u.partOfDepartment="+departmentId+" and u.userStatus=1 order by u.order ASC";
			 return dao.newaddresslistuser(hql);
		 }
		 
		 public List<User> selectnewaddresslist(String content){
			 try {
					content=new String(content.getBytes("ISO8859-1"),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 String hql ="from User u where (u.name like '%"+content+"%' or u.tel like '%"+content+"%' )  ";
					
					System.out.println("hql="+hql); 		
			   return this.dao.newaddresslistuser(hql);
		 }
		 
		  public List<GoodsKind> GoodsKindAll(){
			  String hql="select DISTINCT(g1.goodsKindID),g1.goodsKindName from Goods g ,GoodsKind g1 where g.goodsKindID=g1.goodsKindID";
			  return this.dao.GoodsKindAll( hql);
		  }
		  public List<GoodsKind> GoodsKindAll1(){
			  String hql="select g1.goodsKindID, g1.goodsKindName from GoodsKind g1 where g1.goodsKindID NOT in (select  g.goodsKindID from Goods g) ";
			  return this.dao.GoodsKindAll( hql);
		  }
		  
		  
		  
	 
		  public List<Goods> GoodsAll(String id){
			  String hql="from Goods g where g.goodsKindID="+id;
			  return this.dao.GoodsAll( hql);
		  }






		public List<Department> newaddresslistdepartmentInfo(
				String departmentId, String userid) {
			String hql="from Department d where d.partOfDepartmentId ="+departmentId;
			return dao.newaddresslistdepartment(hql);
		}






		public List<User> newaddresslistuserInfo(String departmentId,
				String userid) {
			 String hql="from User u where u.partOfDepartment="+departmentId+" " +
			 		"and u.userId not in (select s.userId from ScheduleEventUser s where s.eventId="+userid+")";
			 return dao.newaddresslistuser(hql);
		}






		@Override
		public List<User> newaddresslistuserp(String departmentId) 
		{
			 String hql="from User u where u.partOfDepartment="+departmentId+" and u.userStatus=1 order by u.order ASC";
			 return dao.newaddresslistuser(hql);
			
		}
}
