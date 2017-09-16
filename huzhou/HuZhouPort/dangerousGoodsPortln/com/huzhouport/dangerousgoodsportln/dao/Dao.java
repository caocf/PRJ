package com.huzhouport.dangerousgoodsportln.dao;





import java.util.List;

import com.huzhouport.dangerousgoodsportln.model.DangerousArrivalDeclare;
import com.huzhouport.dangerousgoodsportln.model.DangerousArrivalDeclareBean;
import com.huzhouport.dangerousgoodsportln.model.Goods;
import com.huzhouport.dangerousgoodsportln.model.GoodsKind;
import com.huzhouport.dangerousgoodsportln.model.Port;
import com.huzhouport.log.model.PageModel;
import com.huzhouport.organization.model.Department;
import com.huzhouport.user.model.User;





public interface Dao {
	
	
	
	
	


	
	
	
	public PageModel findByPageScroll(String hql,int firstPage,int maxPage);  //危险品申报显示
	public DangerousArrivalDeclareBean findByID(String hql);//通过id 查找
    public void update(String hql);  //审批提交
    public void insert(DangerousArrivalDeclare dangerousArrivalDeclare);//新建
    
    public List<DangerousArrivalDeclareBean> findList(String hql);
    public List<DangerousArrivalDeclareBean> findListByuserid(String hql);
    
    public List<Port> findStartingPortName(String hql);
    
    public void savePort(Port port);
    
    public List<Department> newaddresslistdepartment(String hql);
    public List<User> newaddresslistuser(String hql);
    public List<User> newaddresslistuserp(String hql);
    
    public List<GoodsKind> GoodsKindAll(String hql);
    public List<Goods> GoodsAll(String hql);
    
}
