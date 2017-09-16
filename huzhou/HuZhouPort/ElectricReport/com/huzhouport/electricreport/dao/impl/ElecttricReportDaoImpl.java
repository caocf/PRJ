package com.huzhouport.electricreport.dao.impl;

import java.util.List;

import com.huzhouport.common.dao.impl.BaseDaoImpl;
import com.huzhouport.electricreport.dao.ElectricReportDao;
import com.huzhouport.electricreport.model.Boatman;
import com.huzhouport.electricreport.model.ElectricArrival;
import com.huzhouport.electricreport.model.ElectricReportNew;

public class ElecttricReportDaoImpl extends BaseDaoImpl<ElectricReportNew> implements ElectricReportDao {
	// 查询条数
	public Integer CountListByInfo(ElectricReportNew electricReportNew) throws Exception {
		String hql = "select count(*) from ElectricReportNew e ";
		if(electricReportNew.getShipName()!=null) hql+="where e.shipName='" + electricReportNew.getShipName()+"'";
		if(electricReportNew.getShipInfo()!=null){
			String shipinfo=electricReportNew.getShipInfo().trim();
			if(electricReportNew.getShipName()==null){
				hql+=" where e.shipName like '%" + shipinfo
				+ "%' or e.startingPort like '%" + shipinfo
				+ "%' or e.arrivalPort like '%" + shipinfo
				+ "%' or e.shipUserName like '%" + shipinfo
				+ "%' or e.toBeDockedAtThePier like '%" + shipinfo
				+"%' or e.cargoType like '%" + shipinfo+"%'";
			}else{
				hql+=" and (e.shipName like '%" + shipinfo
				+ "%' or e.startingPort like '%" + shipinfo
				+ "%' or e.arrivalPort like '%" + shipinfo
				+ "%' or e.shipUserName like '%" + shipinfo
				+ "%' or e.toBeDockedAtThePier like '%" + shipinfo
				+"%' or e.cargoType like '%" + shipinfo+"%')";
			}
			
		}
		hql+=" order by e.reportTime desc";
		return this.countEForeignKey(null,hql);
	}
	// 查询本页
	public List<?> SearchListByInfo(ElectricReportNew electricReportNew, int startSet, int maxSet)
			throws Exception {
		String hql = "from ElectricReportNew e ";
		if(electricReportNew.getShipName()!=null) hql+="where e.shipName='" + electricReportNew.getShipName()+"'";
		if(electricReportNew.getShipInfo()!=null){
			String shipinfo=electricReportNew.getShipInfo().trim();
			if(electricReportNew.getShipName()==null){
				hql+=" where e.shipName like '%" + shipinfo
				+ "%' or e.startingPort like '%" + shipinfo
				+ "%' or e.arrivalPort like '%" + shipinfo
				+ "%' or e.shipUserName like '%" + shipinfo
				+ "%' or e.toBeDockedAtThePier like '%" + shipinfo
				+"%' or e.cargoType like '%" + shipinfo+"%'";
			}else{
				hql+=" and (e.shipName like '%" + shipinfo
				+ "%' or e.startingPort like '%" + shipinfo
				+ "%' or e.arrivalPort like '%" + shipinfo
				+ "%' or e.shipUserName like '%" + shipinfo
				+ "%' or e.toBeDockedAtThePier like '%" + shipinfo
				+"%' or e.cargoType like '%" + shipinfo+"%')";
			}
			
		}
		hql+=" order by e.reportTime desc";
		List<?> list = this.queryqueryEForeignKey(null, hql, startSet, maxSet);
		return list;
	}
	//某条电子报港
	@SuppressWarnings("unchecked")
	public List<ElectricReportNew> ElectricReportByReportId(int reportID) throws Exception{
		String hql = "from ElectricReportNew e where e.reportID=" + reportID;
		return this.hibernateTemplate.find(hql);
	}
	//增加电子报港信息
	public String SaveElectricReport(ElectricReportNew electricReportNew) throws Exception{
		this.hibernateTemplate.save(electricReportNew);
		return null;
	}
	//修改电子报港信息
	public String UpdateElectricReport(ElectricReportNew electricReportNew) throws Exception{
		this.hibernateTemplate.update(electricReportNew);
		return null;
	}
	// 航线调整修改目的港 参数
	public String updateReportArrivalPort(ElectricReportNew electricReportNew,ElectricArrival electricArrival) throws Exception{
		String hql = "update ElectricReportNew e set e.arrivalPort ='" + electricReportNew.getArrivalPort()
		+ "', e.estimatedTimeOfArrival='"+electricReportNew.getEstimatedTimeOfArrival()+"' where e.reportID='" + electricReportNew.getReportID()+"'";
		this.hibernateTemplate.bulkUpdate(hql);
		this.hibernateTemplate.save(electricArrival);
		return null;
	}
	// 设置船员信息
	public String SetBoatUserInfo(Boatman boatman) throws Exception{
		/*String hql="select b from Boatman b where b.boatmanShip='" + boatman.getBoatmanShip()+ "' and b.boatmanKind="+boatman.getBoatmanKind();
		List<Boatman> list=this.hibernateTemplate.find(hql);
		if(list!=null){
			if(list.size()>0){
				boatman.setBoatmanID(list.get(0).getBoatmanID());
			}
		}*/
		this.hibernateTemplate.saveOrUpdate(boatman);
		return null;
	}
	// 删除船员信息
	public String DeleteBoatUserInfoByShipName(Boatman boatman) throws Exception{
		this.hibernateTemplate.bulkUpdate("delete Boatman b where b.boatmanShip='" + boatman.getBoatmanShip()+ "'");
		return null;
	}
	// 显示船员信息
	public List<?> ShowBoatUserInfo(Boatman boatman) throws Exception{
		String hql = "select b,bk,c from Boatman b,BoatmanKind bk,Certificate c where c.cardID=b.boatCardID and b.boatmanShip='" + boatman.getBoatmanShip()+ "' and bk.boatmanKindID=b.boatmanKind";
		return this.hibernateTemplate.find(hql);
	}
	// 港口列表
	public List<?> showPortList() throws Exception{
		String hql = "from Port";
		return this.hibernateTemplate.find(hql);
	}
	//显示始发港
	public String showStartPort(String shipName) throws Exception{
		String hql="select e.arrivalPort from ElectricReportNew e  where e.shipName='"+shipName+"' order by e.reportTime desc";
		List<?> list = this.hibernateTemplate.find(hql);
		if(list.size()>0){
			return list.get(0).toString();
		}else{
			return "null";
		}
		
	}
	//历史航线
	@SuppressWarnings("unchecked")
	public List<ElectricArrival> GetOldReport(ElectricReportNew electricreportnew) throws Exception{
		String hql="from ElectricArrival e  where e.reportID="+electricreportnew.getReportID()+" order by e.electId desc";
		return this.hibernateTemplate.find(hql);
	}
}
