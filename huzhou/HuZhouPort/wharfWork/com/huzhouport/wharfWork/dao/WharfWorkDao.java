package com.huzhouport.wharfWork.dao;
import java.util.List;
import com.huzhouport.attendace.model.Location;
import com.huzhouport.dangerousgoodsportln.model.Port;
import com.huzhouport.log.model.PageModel;
import com.huzhouport.publicuser.model.WharfBinding;
import com.huzhouport.wharfWork.model.Wharf;
import com.huzhouport.wharfWork.model.WharfWork;
import com.huzhouport.wharfWork.model.Wharfbean;
public interface WharfWorkDao {
	
	
	
	
	


	
	
	
	public PageModel findByPageScroll(String hql,int firstPage,int maxPage);  
	public Wharfbean findBywharfworkid(String hql);
	public Wharf viewWharf(String hql);
	public String insertLocation(Location location );
	public List<Port> findStartingPortName(String hql);
	public String  savePort(Port port);
	public void saveWharfwork(WharfWork wharfwork);
	 public void updateWharf(String hql);
	 public PageModel findByPageScroll4(String hql,int firstPage,int maxPage);  
	public List<WharfBinding> wharfWorkSurplus(String hql);
}
