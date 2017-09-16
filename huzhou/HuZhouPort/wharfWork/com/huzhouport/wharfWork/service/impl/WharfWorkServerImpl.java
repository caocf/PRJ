package com.huzhouport.wharfWork.service.impl;










import java.io.UnsupportedEncodingException;
import java.util.List;

import com.huzhouport.attendace.model.Location;
import com.huzhouport.dangerousgoodsportln.model.Port;
import com.huzhouport.log.model.PageModel;
import com.huzhouport.publicuser.model.WharfBinding;
import com.huzhouport.wharfWork.dao.WharfWorkDao;
import com.huzhouport.wharfWork.model.Wharf;
import com.huzhouport.wharfWork.model.WharfWork;
import com.huzhouport.wharfWork.model.Wharfbean;

import com.huzhouport.wharfWork.service.WharfWorkServer;









public class WharfWorkServerImpl implements WharfWorkServer {
	private WharfWorkDao wharfworkdao;

	public void setWharfworkdao(WharfWorkDao wharfworkdao) {
		this.wharfworkdao = wharfworkdao;
	}

	public PageModel<Wharfbean> findByScrollServer(int currentPage, int maxPage,String wharfworkid) { //

		String hql="from WharfWork ww , WharfBinding w ,Port p1 ,Port p2 where ww.wharfWorkID=w.wharfNumber and ww.startingPort=p1.portID and ww.arrivalPort=p2.portID and ww.wharfWorkID='"+wharfworkid+"' order by ww.declareTime desc ";
		//System.out.println("hql="+hql); 		
		return this.wharfworkdao.findByPageScroll(hql, currentPage, maxPage);
	}
	public PageModel<Wharfbean> findByScrollServer1(int currentPage, int maxPage,String wharfworkid ,String content) { //

		 try {
				content=new String(content.getBytes("ISO8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		String hql="from WharfWork ww , WharfBinding w ,Port p1 ,Port p2 where ww.wharfWorkID=w.wharfNumber and ww.startingPort=p1.portID and ww.arrivalPort=p2.portID and ww.wharfWorkID='"+wharfworkid+"' and (ww.shipName like '%"+content+"%' or p1.portName like '%"+content+"%' or p2.portName like '%"+content+"%' or ww.cargoTypes like '%"+content+"%') order by ww.declareTime desc ";
		//System.out.println("hql="+hql); 		
		return this.wharfworkdao.findByPageScroll(hql, currentPage, maxPage);
	}
	public Wharfbean findBywharfworkid(String id){
		String hql="from WharfWork ww, WharfBinding w,Port p1, Port p2 ,Location l where ww.wharfWorkID=w.wharfNumber and ww.startingPort=p1.portID and ww.arrivalPort=p2.portID and ww.locationID=l.locationID and ww.id="+id;
		return this.wharfworkdao.findBywharfworkid(hql);
	}
	public Wharf viewWharf(String wharfworkid){
		String hql="from Wharf w where w.wharfID="+wharfworkid;
		return this.wharfworkdao.viewWharf(hql);
	}
	
	public String insertLocation(Location location ){
		return wharfworkdao.insertLocation(location);
	}
	 public List<Port> findStartingPortName(String name){
		 String hql ="from Port p where p.portName='"+name+"'"; 
		return this.wharfworkdao.findStartingPortName(hql);
	 }
	 
	 public String  savePort(Port port){
		return this.wharfworkdao.savePort(port);
	 }
	 public void saveWharfwork(WharfWork wharfwork){
		 wharfworkdao.saveWharfwork(wharfwork);
	 }
	
	 public void updateWharf(int wharfworksurplus,int id){
		// String hql="update LeaveOrOt l set l.approvalResult="+i+" where l.leaveOrOtID="+leaveOrOtID;
		 String hql ="update WharfBinding w set w.wharfWorkSurplus="+wharfworksurplus+"  where w.wharfNumber="+id;
		 wharfworkdao.updateWharf(hql);
	 }
		public PageModel<Wharfbean> findByScrollServer2(int currentPage, int maxPage) { //
			String hql="from WharfWork ww , WharfBinding w ,Port p1 ,Port p2 where ww.wharfWorkID=w.wharfNumber and ww.startingPort=p1.portID and ww.arrivalPort=p2.portID order by ww.declareTime desc ";
		    //String hql="from WharfWork ww , Wharf w ,Port p1 ,Port p2 where ww.wharfWorkID=w.wharfID and ww.startingPort=p1.portID and ww.arrivalPort=p2.portID  order by ww.declareTime desc ";
			//System.out.println("hql="+hql); 		
			return this.wharfworkdao.findByPageScroll(hql, currentPage, maxPage);
		}
		
		public PageModel<Wharfbean> findByScrollServer3(int currentPage, int maxPage ,String content) { //}
			
			 try {
					content=new String(content.getBytes("ISO8859-1"),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			String hql="from WharfWork ww , WharfBinding w ,Port p1 ,Port p2 where ww.wharfWorkID=w.wharfNumber and ww.startingPort=p1.portID and ww.arrivalPort=p2.portID  and (w.wharfNum like '%"+content+"%' or ww.shipName like '%"+content+"%' or p1.portName like '%"+content+"%' or p2.portName like '%"+content+"%'  or ww.cargoTypes like '%"+content+"%' or ww.wharfWorkID like '%"+content+"%') order by ww.declareTime desc ";
			//System.out.println("hql="+hql); 		
			return this.wharfworkdao.findByPageScroll(hql, currentPage, maxPage);
		}
		
		public PageModel<WharfBinding> findByScrollServer4(int currentPage, int maxPage) { //

			String hql="from WharfBinding w  ";
			//System.out.println("hql="+hql); 		
			return this.wharfworkdao.findByPageScroll4(hql, currentPage, maxPage);
		}
		public PageModel<WharfBinding> findByScrollServer5(int currentPage, int maxPage,String content) { //

			String hql="from WharfBinding w  where (w.wharfNum like '%"+content+"%' or w.wharfNumber like '%"+content+"%')";
			//System.out.println("hql="+hql); 		
			return this.wharfworkdao.findByPageScroll4(hql, currentPage, maxPage);
		}
		
		
		public void updateWharfBinding(String  content,String id){
				// String hql="update LeaveOrOt l set l.approvalResult="+i+" where l.leaveOrOtID="+leaveOrOtID;
				 String hql ="update WharfBinding w set w.wharfWorkNorm='"+content+"' , w.wharfWorkSurplus='"+content+"'  where w.wharfNumber='"+id+"'";
				 wharfworkdao.updateWharf(hql);
			 }
		
		 public List<WharfBinding> wharfWorkSurplus(String id){
			 String hql ="from WharfBinding w where wharfUser="+id; 
			return this.wharfworkdao.wharfWorkSurplus(hql);
		 }
		//还原码头作业的月定额
		    public void ReturnValueByMonth(){
		    	 String hql ="update  WharfBinding w set wharfWorkSurplus=wharfWorkNorm"; 
				this.wharfworkdao.updateWharf(hql);
		    }
}
