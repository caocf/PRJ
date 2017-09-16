package com.huzhouport.equipment.service.impl;



import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;




import com.huzhouport.dangerousgoodsportln.model.DangerousArrivalDeclareBean;
import com.huzhouport.equipment.dao.Dao;
import com.huzhouport.equipment.model.Equipment;
import com.huzhouport.equipment.model.EquipmentKind;
import com.huzhouport.equipment.model.Equipmentbean;
import com.huzhouport.equipment.service.EquipmentServer;
import com.huzhouport.log.model.PageModel;









public class EquipmentServerImpl implements EquipmentServer{
	private Dao dao;
	
	
	
	
//	public Dao getDao() {
//		return dao;
//	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	
	
	


	public PageModel<Equipmentbean> findByScrollServer(int currentPage, int maxPage,String action) { //请假加班显示
		String hql="from Equipment e,EquipmentKind ek ,User u,User u1 where e.equipmentKind=ek.equipmentKindID and e.equipmentUser=u.userId and e.approvalID=u1.userId  order by e.equipmentDate desc";
		return this.dao.findByPageScroll(hql, currentPage, maxPage ,action);
	}
	public PageModel<Equipmentbean> findByScrollServer1(int currentPage, int maxPage,String action) { //请假加班显示
		String hql="from Equipment e,EquipmentKind ek ,User u,User u1 where e.equipmentKind=ek.equipmentKindID and e.equipmentUser=u.userId and e.approvalID=u1.userId and e.approvalResult=0 order by e.equipmentDate desc";
		return this.dao.findByPageScroll(hql, currentPage, maxPage ,action);
	}
	public PageModel<Equipmentbean> findByScrollServer2(int currentPage, int maxPage,String action,String content) { //请假加班显示
		String hql="from Equipment e,EquipmentKind ek ,User u,User u1 where e.equipmentKind=ek.equipmentKindID and e.equipmentUser=u.userId and e.approvalID=u1.userId and (u.name like '%"+content+"%' or ek.equipmentKindName like '%"+content+"%' or u1.name like '%"+content+"%' ) order by e.equipmentDate desc";
		return this.dao.findByPageScroll(hql, currentPage, maxPage ,action);
	}
	
	public Equipmentbean findByID(String equipmentID){
	    String hql ="from Equipment e,EquipmentKind ek ,User u,User u1 where e.equipmentKind=ek.equipmentKindID and e.equipmentUser=u.userId and e.approvalID=u1.userId  and e.equipmentID="+equipmentID;			
	    return this.dao.findByID(hql);
		}
	 public void updateByID(Equipmentbean equipmentbean){
		 String hql ="update Equipment e set e.approvalResult="+equipmentbean.getApprovalResult()+" ,e.approvalOpinion='"+equipmentbean.getApprovalOpinion()+"',e.approvalTime='"+equipmentbean.getApprovalTime()+"' where  e.equipmentID="+equipmentbean.getEquipmentID();
		 dao.update(hql);
	 }
	 public List<Equipmentbean> equipmentApproval(String equipmentID){
		 String hql="from Equipment e,EquipmentKind ek ,User u,User u1 where e.equipmentKind=ek.equipmentKindID and e.equipmentUser=u.userId and e.approvalID=u1.userId and e.approvalID="+equipmentID+" order by e.equipmentDate desc";
		 System.out.println("hql="+hql);
	 return dao.equilpmentlist(hql);
	 }
	 
	 public List<Equipmentbean> equipmentApply(String equipmentID){
		 String hql="from Equipment e,EquipmentKind ek ,User u,User u1 where e.equipmentKind=ek.equipmentKindID and e.equipmentUser=u.userId and e.approvalID=u1.userId and e.equipmentUser="+equipmentID+" order by e.equipmentDate desc";
		 System.out.println("hql="+hql);
		 return dao.equilpmentlist(hql);
	 }
	 public List<Equipmentbean> equipmentAll(){
		 String hql="from Equipment e,EquipmentKind ek ,User u,User u1 where e.equipmentKind=ek.equipmentKindID and e.equipmentUser=u.userId and e.approvalID=u1.userId  order by e.equipmentDate desc";
	 return dao.equilpmentlist(hql);
	 }
	 public List<Equipmentbean> equipmentApprovalbyMycontent(String equipmentID,String content){
		 try {
				content=new String(content.getBytes("ISO8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		 
		String hql="from Equipment e,EquipmentKind ek ,User u,User u1 where e.equipmentKind=ek.equipmentKindID and e.equipmentUser=u.userId and e.approvalID=u1.userId and (u.name like '%"+content+"%' or ek.equipmentKindName like '%"+content+"%'  ) and e.approvalID="+equipmentID+"  order by e.equipmentDate desc";
		 return dao.equilpmentlist(hql);

	 }
	 
	 public List<EquipmentKind> equipmentkindAll(){
		 String hql="from EquipmentKind ek";
		 return dao.equipmentkindAll(hql);
	 }
	 public void equipmentAdd(Equipment equipment){
		 dao.equipmentAdd(equipment);
	 }
	 
}
