package com.huzhouport.wharfWork.service;




import java.util.List;

import com.huzhouport.attendace.model.Location;
import com.huzhouport.dangerousgoodsportln.model.Port;
import com.huzhouport.log.model.PageModel;
import com.huzhouport.publicuser.model.WharfBinding;
import com.huzhouport.wharfWork.model.Wharf;
import com.huzhouport.wharfWork.model.WharfWork;
import com.huzhouport.wharfWork.model.Wharfbean;






public interface WharfWorkServer {
	
	public PageModel<Wharfbean> findByScrollServer(int currentPage,int maxPage ,String wharfworkid); //码头作业申报显示
	public PageModel<Wharfbean> findByScrollServer1(int currentPage,int maxPage ,String wharfworkid,String content); //查找码头作业申报显示
    public Wharfbean findBywharfworkid(String id);
    public Wharf viewWharf(String wharfworkid);
    public String insertLocation(Location location ); //新建 返回id
    public List<Port> findStartingPortName(String name); 
    public String  savePort(Port port);
    public void saveWharfwork(WharfWork wharfwork);
    public void updateWharf(int wharfworksurplus,int id); 
    public PageModel<Wharfbean> findByScrollServer2(int currentPage,int maxPage); //码头作业申报显示
    public PageModel<Wharfbean> findByScrollServer3(int currentPage,int maxPage ,String content); 
    
    public PageModel<WharfBinding> findByScrollServer4(int currentPage,int maxPage); //码头作业申报显示
    public PageModel<WharfBinding> findByScrollServer5(int currentPage,int maxPage,String content); //码头作业申报显示
    public void updateWharfBinding(String  content,String id); 
    public List<WharfBinding> wharfWorkSurplus(String id); 
    
    //还原码头作业的月定额
    public void ReturnValueByMonth(); 
}
