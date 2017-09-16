package com.huzhouport.permit.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.attendace.model.Location;
import com.huzhouport.common.action.BaseAction;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.illegal.model.IllegalEvidence;
import com.huzhouport.inspection.model.Inspection;
import com.huzhouport.inspection.model.InspectionEvidenceLink;
import com.huzhouport.inspection.model.InspectionSupplement;
import com.huzhouport.permit.service.PermitService;
import com.opensymphony.xwork2.ModelDriven;

public class PermitAction extends BaseAction implements ModelDriven<Inspection>{
	private static final long serialVersionUID = 1L;
	private Inspection inspection=new Inspection();
	private InspectionSupplement inspectionSupplement=new InspectionSupplement();
	private InspectionEvidenceLink inspectionEvidenceLink=new InspectionEvidenceLink();
	private IllegalEvidence illegalEvidence=new IllegalEvidence();
	private Location location=new Location();
	private PermitService permitService;
	private int totalPage;// 总页数
	private int allTotal;// 显示总条数
	private List<?> list;
	private int result;
	
	public InspectionSupplement getInspectionSupplement() {
		return inspectionSupplement;
	}

	public void setInspectionSupplement(InspectionSupplement inspectionSupplement) {
		this.inspectionSupplement = inspectionSupplement;
	}

	public InspectionEvidenceLink getInspectionEvidenceLink() {
		return inspectionEvidenceLink;
	}

	public void setInspectionEvidenceLink(
			InspectionEvidenceLink inspectionEvidenceLink) {
		this.inspectionEvidenceLink = inspectionEvidenceLink;
	}

	public IllegalEvidence getIllegalEvidence() {
		return illegalEvidence;
	}

	public void setIllegalEvidence(IllegalEvidence illegalEvidence) {
		this.illegalEvidence = illegalEvidence;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
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

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public Inspection getModel() {
		return inspection;
	}

	public Inspection getInspection() {
		return inspection;
	}

	public void setInspection(Inspection inspection) {
		this.inspection = inspection;
	}

	public void setPermitService(PermitService permitService) {
		this.permitService = permitService;
	}
	// 分页排序查询
	public String showInspectionList() throws Exception {
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map = this.permitService.countPageInspectionInfo(inspection, GlobalVar.PAGESIZE);
			totalPage = map.get("pages");
			allTotal = map.get("allTotal");
			if (totalPage != 0) {
				list = this.permitService.searchInspectionInfo(inspection,Integer
						.valueOf(this.getCpage()), GlobalVar.PAGESIZE);

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

		return SUCCESS;
	}
	// 分页排序查询-手机端
	public String showInspectionList_mobi() throws Exception {
		if(inspection.getInspectionObject()!=null)
			{
			inspection.setInspectionObject(new String(inspection.getInspectionObject().getBytes("ISO8859-1"),"UTF-8"));
			
			}
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map = this.permitService.countPageInspectionInfo_mobi(inspection, GlobalVar.PAGESIZE);
			totalPage = map.get("pages");
			allTotal = map.get("allTotal");
			if (totalPage != 0) {
				list = this.permitService.searchInspectionInfo_mobi(inspection, Integer
						.valueOf(this.getCpage()), GlobalVar.PAGESIZE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

		return SUCCESS;
	}
	// 分页排序查询-手机端-待审核
	public String showInspectionListByCheck_mobi() throws Exception {
		if(inspection.getInspectionObject()!=null)
			{
			inspection.setInspectionObject(new String(inspection.getInspectionObject().getBytes("ISO8859-1"),"UTF-8"));
			
			}
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map = this.permitService.countPageInspectionInfoByCheck_mobi(inspection, GlobalVar.PAGESIZE);
			totalPage = map.get("pages");
			allTotal = map.get("allTotal");
			if (totalPage != 0) {
				list = this.permitService.searchInspectionInfoByCheck_mobi(inspection, Integer
						.valueOf(this.getCpage()), GlobalVar.PAGESIZE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

		return SUCCESS;
	}
	//显示许可信息
	public String showInspectionInfoByInspectionId()throws Exception {
		try {
			list=this.permitService.showInfoByInspectionId(inspection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	//显示审核信息
	public String showInspectionCheckInfoByInspectionId()throws Exception {
		try {
			list=this.permitService.showCheckInfoByInspectionId(inspection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	//显示许可附件信息
	public String showInspectionEvidenceByInspectionId()throws Exception {
		try {
			list=this.permitService.showEvidenceByInspectionId(inspection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	//显示许可补充信息
	public String showInspectionSupplementByInspectionId()throws Exception {
		try {
			list=this.permitService.showSupplementByInspectionId(inspection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	//增加许可取证信息
	public String addInspectionByInspectionId()throws Exception {
		try {
			this.permitService.addInspectionByInspectionId(location,inspection,illegalEvidence);
			result=1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	//补充许可信息-服务端
	public String addInspectionSupplementByServer()throws Exception {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			inspectionSupplement.setSupplementTime(df.format(new Date()).toString());
			this.permitService.addSupplementByInspectionId(inspection,illegalEvidence,inspectionSupplement);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	//补充许可信息-手机端
	public String addInspectionSupplementByMobi()throws Exception {
		try {
			this.permitService.addSupplementByInspectionId(inspection,illegalEvidence,inspectionSupplement);
			result=1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	//审核许可信息-服务端
	public String checkInspectionByServer()throws Exception {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			inspection.setReviewTime(df.format(new Date()).toString());
			this.permitService.checkInspectionByInspectionId(inspection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	//审核许可信息-手机端
	public String checkInspectionByInspectionId()throws Exception {
		try {
			this.permitService.checkInspectionByInspectionId(inspection);
			result=1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	//位置信息
	public String showLocationByInspectionId()throws Exception {
		try {
			list=this.permitService.showLocationByInspectionId(inspection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
