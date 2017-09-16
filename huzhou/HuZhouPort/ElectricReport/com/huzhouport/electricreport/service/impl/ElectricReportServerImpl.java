package com.huzhouport.electricreport.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.common.service.impl.BaseServiceImpl;
import com.huzhouport.electricreport.dao.ElectricReportDao;
import com.huzhouport.electricreport.model.Boatman;
import com.huzhouport.electricreport.model.ElectricArrival;
import com.huzhouport.electricreport.model.ElectricReportNew;
import com.huzhouport.electricreport.service.ElectricReportService;

public class ElectricReportServerImpl extends BaseServiceImpl<ElectricReportNew> implements ElectricReportService {
	private ElectricReportDao electricReportDao;
	
	public void setElectricReportDao(ElectricReportDao electricReportDao) {
		this.electricReportDao = electricReportDao;
	}
	
	public Map<String, Integer> CountListByInfo(ElectricReportNew electricReportNew, int pageSize) throws Exception {
		Map<String,Integer> map=new HashMap<String, Integer>();
		int total = this.electricReportDao.CountListByInfo(electricReportNew);
		map.put("allTotal", total);
		int pages = calculateTotalPage(total, pageSize); // 获取计算分页页数
		map.put("pages", pages);
		return map;
	}
	public List<?> SearchListByInfo(ElectricReportNew electricReportNew, int pageNo, int pageSize)
			throws Exception {
		int beginIndex = (pageNo - 1) * pageSize;
		return this.electricReportDao.SearchListByInfo(electricReportNew, beginIndex,pageSize);
	}
	//某条电子报港
	public List<?> ElectricReportByReportId(int reportID) throws Exception{
		return this.electricReportDao.ElectricReportByReportId(reportID);
	}
	//增加电子报港信息
	public String SaveElectricReport(ElectricReportNew electricReportNew) throws Exception{
		this.electricReportDao.SaveElectricReport(electricReportNew);
		return null;
	}
	//修改电子报港信息
	public Boolean UpdateElectricReport(ElectricReportNew electricReportNew) throws Exception{
		List<ElectricReportNew> e=this.electricReportDao.ElectricReportByReportId(electricReportNew.getReportID());
		if(e.size()>0){
			electricReportNew.setShipName(e.get(0).getShipName());
			electricReportNew.setStartingPort(e.get(0).getStartingPort());
			electricReportNew.setReportTime(e.get(0).getReportTime());
			this.electricReportDao.UpdateElectricReport(electricReportNew);
			return true;
		}else{
			return false;
		}
		
	}
	// 航线调整修改目的港 参数
	public String updateReportArrivalPort(ElectricReportNew electricReportNew,ElectricArrival electricArrival) throws Exception{
		return this.electricReportDao.updateReportArrivalPort(electricReportNew,electricArrival);
	}
	// 设置船员信息
	public String SetBoatUserInfo(Boatman boatman) throws Exception{
		// 删除船员信息
		this.electricReportDao.DeleteBoatUserInfoByShipName(boatman);
		String[] boatmanName=boatman.getBoatmanName().split(",");
		String[] boatCardNum=boatman.getBoatCardNum().split(",");
		String[] boatmanKind=boatman.getKindList().split(",");
		String[] boatmanCardID=boatman.getBoatCardID().split(",");
		for(int i=0;i<boatmanName.length;i++){
			Boatman b=new Boatman();
			b.setBoatCardNum(boatCardNum[i]);
			//b.setBoatmanID(boatman.getBoatmanID());
			b.setBoatmanKind(Integer.parseInt(boatmanKind[i].trim()));
			b.setBoatmanName(boatmanName[i]);
			b.setBoatmanShip(boatman.getBoatmanShip());
			b.setBoatCardID(boatmanCardID[i]);
			this.electricReportDao.SetBoatUserInfo(b);
		}
		return null;
	}
	// 显示船员信息
	public List<?> ShowBoatUserInfo(Boatman boatman) throws Exception{
		return this.electricReportDao.ShowBoatUserInfo(boatman);
	}
	// 港口列表
	public List<?> showPortList() throws Exception{
		return this.electricReportDao.showPortList();
	}
	//显示始发港
	public String showStartPort(String shipName) throws Exception{
		return this.electricReportDao.showStartPort(shipName);
	}
	//历史航线
	public List<ElectricArrival> GetOldReport(ElectricReportNew electricreportnew) throws Exception{
		return this.electricReportDao.GetOldReport(electricreportnew);
	}
}
