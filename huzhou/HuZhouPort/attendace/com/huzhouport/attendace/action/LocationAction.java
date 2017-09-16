package com.huzhouport.attendace.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.attendace.model.Location;
import com.huzhouport.attendace.service.LocationService;
import com.huzhouport.common.action.BaseAction;
import com.huzhouport.common.util.GlobalVar;
import com.opensymphony.xwork2.ModelDriven;

public class LocationAction extends BaseAction implements ModelDriven<Location> {
	private static final long serialVersionUID = 1L;
	private int totalPage;// 总页数
	private int allTotal;
	private Location location = new Location();
	
	private LocationService locationService;
	private List<?> list;

	public Location getModel() {
		
		return location;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getAllTotal() {
		return allTotal;
	}

	public void setAllTotal(int allTotal) {
		this.allTotal = allTotal;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public void setLocationService(LocationService locationService) {
		this.locationService = locationService;
	}

	// 分页查询
	public String showLocationInfo() throws Exception {
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map = this.locationService.countPageLocationInfo(location,
					GlobalVar.PAGESIZE);
			totalPage = map.get("pages");
			allTotal = map.get("allTotal");
			if (totalPage != 0) {

				list = this.locationService.searchLocationInfo(location,
						Integer.valueOf(this.getCpage()), GlobalVar.PAGESIZE);

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return SUCCESS;
	}
	
	public String addLocationInfo() throws Exception {
		this.locationService.add(location);
		return SUCCESS;
	}

}
