package com.huzhouport.integratedquery.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.common.action.BaseAction;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.integratedquery.service.ShipLibraryService;
import com.huzhouport.publicuser.model.ShipLibrary;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class ShipLibraryAction extends BaseAction implements ModelDriven<ShipLibrary> {

	private ShipLibrary shipLibrary=new ShipLibrary();
	private ShipLibraryService shipLibraryService;
	private int totalPage;// 总页数
	private int allTotal;// 显示总条数
	private List<?> list;
	public ShipLibrary getModel() {
		return shipLibrary;
	}
	public ShipLibrary getShipLibrary() {
		return shipLibrary;
	}
	public void setShipLibrary(ShipLibrary shipLibrary) {
		this.shipLibrary = shipLibrary;
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
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
	public void setShipLibraryService(ShipLibraryService shipLibraryService) {
		this.shipLibraryService = shipLibraryService;
	}
	//模糊搜索船舶名称
	public String SearchShipName() {
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map = this.shipLibraryService.CountShipNameListByInfo(shipLibrary,
					GlobalVar.PAGESIZE);
			totalPage = map.get("pages");
			allTotal = map.get("allTotal");
			if (totalPage != 0) {
				list = this.shipLibraryService.SearchShipNameListByInfo(
						shipLibrary, Integer.valueOf(this.getCpage()),
						GlobalVar.PAGESIZE);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;

	}
}
