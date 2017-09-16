package com.huzhouport.dangerousGoodsJob.dao;






import java.util.List;

import com.huzhouport.dangerousGoodsJob.model.DangerousWorkDeclare;
import com.huzhouport.dangerousGoodsJob.model.DangerousWorkDeclareBean;
import com.huzhouport.dangerousGoodsJob.model.WharfJobNum;
import com.huzhouport.dangerousgoodsportln.model.DangerousArrivalDeclareBean;
import com.huzhouport.dangerousgoodsportln.model.Port;
import com.huzhouport.log.model.PageModel;





public interface Dao {
	
	
	
	
	


	
	
	
	public PageModel findByPageScroll(String hql,int firstPage,int maxPage);  //危险品申报显示
    public DangerousWorkDeclareBean findByID(String hql);//通过id 查找
    public void update(String hql);  //审批提交
  public void insert(DangerousWorkDeclare dangerousWorkDeclare);//新建
  public List<DangerousWorkDeclareBean> findList(String hql);
  public List<WharfJobNum> findWharfJobNum(String hql);
  public List<Port> findStartingPortName(String hql);
  
  public void savePort(Port port); 
	
}
