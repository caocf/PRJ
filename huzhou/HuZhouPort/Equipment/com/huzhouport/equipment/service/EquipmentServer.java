package com.huzhouport.equipment.service;



import java.util.List;


import com.huzhouport.dangerousgoodsportln.model.DangerousArrivalDeclareBean;
import com.huzhouport.equipment.model.Equipment;
import com.huzhouport.equipment.model.EquipmentKind;
import com.huzhouport.equipment.model.Equipmentbean;
import com.huzhouport.log.model.PageModel;





public interface EquipmentServer {
	public PageModel<Equipmentbean> findByScrollServer(int currentPage,int maxPage ,String action); //显示
	public PageModel<Equipmentbean> findByScrollServer1(int currentPage,int maxPage ,String action); //待审批显示
	public PageModel<Equipmentbean> findByScrollServer2(int currentPage,int maxPage ,String action,String content); //查找显示
	 public Equipmentbean findByID(String equipmentID);//通过id 查找
	 public void updateByID(Equipmentbean equipmentbean);
	 public List<Equipmentbean> equipmentApproval(String equipmentID);
	 public List<Equipmentbean> equipmentApply(String equipmentID);
	 public List<Equipmentbean> equipmentAll();
	 public List<Equipmentbean> equipmentApprovalbyMycontent(String equipmentID,String content);
	 public List<EquipmentKind> equipmentkindAll();
	 public void equipmentAdd(Equipment equipment);
}
