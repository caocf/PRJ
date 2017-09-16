package com.huzhouport.dangerousgoodsportln.service;



import java.util.List;

import com.huzhouport.dangerousgoodsportln.model.DangerousArrivalDeclare;
import com.huzhouport.dangerousgoodsportln.model.DangerousArrivalDeclareBean;
import com.huzhouport.dangerousgoodsportln.model.Goods;
import com.huzhouport.dangerousgoodsportln.model.GoodsKind;
import com.huzhouport.dangerousgoodsportln.model.Port;
import com.huzhouport.leaveandovertime.model.LeaveOrOt;
import com.huzhouport.leaveandovertime.model.LeaveOrOtKind;
import com.huzhouport.leaveandovertime.model.LeaveOrOtKindbean;
import com.huzhouport.log.model.PageModel;
import com.huzhouport.organization.model.Department;
import com.huzhouport.user.model.User;





public interface DangerousGoodsPortlnServer {
	
	
	
	
	
	
	
	
	
	
	public PageModel<DangerousArrivalDeclareBean> findByScrollServer(int currentPage,int maxPage ); //危险品申报显示
	public PageModel<DangerousArrivalDeclareBean> findByScrollServer1(int currentPage,int maxPage ,String content); //危险品申报显示
    public DangerousArrivalDeclareBean findByID(String declareID);//通过id 查找
    public void updateByID(String declareID,String userid,String reviewResult,String reviewOpinion);//通过id 查找
    public void insert(DangerousArrivalDeclare dangerousArrivalDeclare); //新建
    
    public List<DangerousArrivalDeclareBean> findList(); //危险品申报显示手机端
    public List<DangerousArrivalDeclareBean> findList1(String content); //危险品申报查找手机端
    public List<DangerousArrivalDeclareBean> findListByname(String userid); //危险品申报显示手机端  船户版
    public List<DangerousArrivalDeclareBean> findListByname1(String content,String userid); //危险品申报查找手机端 船户版
    
    public List<Port> findStartingPortName(String name); 
    public void savePort(Port port);
    
    public List<Department> newaddresslistdepartment(String departmentId);
    public List<User> newaddresslistuser(String departmentId);
    public List<User> newaddresslistuserp(String departmentId);
    public List<User> selectnewaddresslist(String content);
    
    
    public List<Department> newaddresslistdepartmentInfo(String departmentId,String userid);
    public List<User> newaddresslistuserInfo(String departmentId,String userid);
    
    public List<GoodsKind> GoodsKindAll();
    public List<GoodsKind> GoodsKindAll1();
    public List<Goods> GoodsAll(String id);
    
}
