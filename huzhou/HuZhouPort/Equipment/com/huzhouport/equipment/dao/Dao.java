package com.huzhouport.equipment.dao;



import java.util.List;

import com.huzhouport.equipment.model.Equipment;
import com.huzhouport.equipment.model.EquipmentKind;
import com.huzhouport.equipment.model.Equipmentbean;
import com.huzhouport.leaveandovertime.model.LeaveOrOt;
import com.huzhouport.leaveandovertime.model.LeaveOrOtKind;
import com.huzhouport.leaveandovertime.model.LeaveOrOtKindbean;
import com.huzhouport.log.model.PageModel;





public interface Dao {
	
	
	
	
	

//	public List<Userbean> findAllUser(String hql);
	
	
	
	public PageModel findByPageScroll(String hql,int firstPage,int maxPage,String action);  //分页
	 public Equipmentbean findByID(String hql);//通过id 查找
	   public void update(String hql);  //审批提交
	   public List<Equipmentbean> equilpmentlist(String hql);
	   public List<EquipmentKind> equipmentkindAll(String hql);
	   public void equipmentAdd(Equipment equipment);

	
}
