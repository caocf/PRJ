package com.huzhouport.patrol.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.attendace.model.Location;
import com.huzhouport.common.action.BaseAction;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.illegal.model.IllegalEvidence;
import com.huzhouport.patrol.model.Patrol;
import com.huzhouport.patrol.model.PatrolEvidenceLink;
import com.huzhouport.patrol.model.PatrolSupplement;
import com.huzhouport.patrol.service.PatrolService;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class PatrolAction extends BaseAction implements ModelDriven<Patrol>{
	private Patrol patrol=new Patrol();
	private PatrolSupplement patrolSupplement=new PatrolSupplement();
	private PatrolEvidenceLink patrolEvidenceLink=new PatrolEvidenceLink();
	private IllegalEvidence illegalEvidence=new IllegalEvidence();
	private Location location=new Location();
	private PatrolService patrolService;
	private int totalPage;// 总页数
	private int allTotal;// 显示总条数
	private List<?> list;
	private int result;
	
	public PatrolSupplement getPatrolSupplement() {
		return patrolSupplement;
	}

	public void setPatrolSupplement(PatrolSupplement patrolSupplement) {
		this.patrolSupplement = patrolSupplement;
	}

	public PatrolEvidenceLink getPatrolEvidenceLink() {
		return patrolEvidenceLink;
	}

	public void setPatrolEvidenceLink(
			PatrolEvidenceLink patrolEvidenceLink) {
		this.patrolEvidenceLink = patrolEvidenceLink;
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

	public Patrol getModel() {
		return patrol;
	}

	public Patrol getPatrol() {
		return patrol;
	}

	public void setPatrol(Patrol patrol) {
		this.patrol = patrol;
	}

	public void setPatrolService(PatrolService patrolService) {
		this.patrolService = patrolService;
	}
	// 分页排序查询
	public String showPatrolList() throws Exception {
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map = this.patrolService.countPagePatrolInfo(patrol, GlobalVar.PAGESIZE);
			totalPage = map.get("pages");
			allTotal = map.get("allTotal");
			if (totalPage != 0) {
				list = this.patrolService.searchPatrolInfo(patrol,Integer
						.valueOf(this.getCpage()), GlobalVar.PAGESIZE);

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

		return SUCCESS;
	}
	// 分页排序查询-手机端
	public String showPatrolList_mobi() throws Exception {
		if(patrol.getPatrolObject()!=null)
			{
			patrol.setPatrolObject(new String(patrol.getPatrolObject().getBytes("ISO8859-1"),"UTF-8"));
			
			}
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map = this.patrolService.countPagePatrolInfo_mobi(patrol, GlobalVar.PAGESIZE);
			totalPage = map.get("pages");
			allTotal = map.get("allTotal");
			if (totalPage != 0) {
				list = this.patrolService.searchPatrolInfo_mobi(patrol, Integer
						.valueOf(this.getCpage()), GlobalVar.PAGESIZE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

		return SUCCESS;
	}
	
	//显示详细信息
	public String showPatrolInfoByPatrolId()throws Exception {
		try {
			list=this.patrolService.showInfoByPatrolId(patrol);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	//显示附件信息
	public String showPatrolEvidenceByPatrolId()throws Exception {
		try {
			list=this.patrolService.showEvidenceByPatrolId(patrol);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	//显示补充信息
	public String showPatrolSupplementByPatrolId()throws Exception {
		try {
			list=this.patrolService.showSupplementByPatrolId(patrol);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	//增加信息-手机端
	public String addPatrolByPatrolId()throws Exception {
		try {
			this.patrolService.addPatrolByPatrolId(location,patrol,illegalEvidence);
			result=1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	//补充信息-服务器
	public String addPatrolSupplementByServer()throws Exception {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			patrolSupplement.setSupplementTime(df.format(new Date()).toString());
			this.patrolService.addSupplementByPatrolId(patrol,illegalEvidence,patrolSupplement);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	//补充信息-手机端
	public String addPatrolSupplementByMobi()throws Exception {
		try {
			this.patrolService.addSupplementByPatrolId(patrol,illegalEvidence,patrolSupplement);
			result=1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	//位置信息
	public String showLocationByPatrolId()throws Exception {
		try {
			list=this.patrolService.showLocationByPatrolId(patrol);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
