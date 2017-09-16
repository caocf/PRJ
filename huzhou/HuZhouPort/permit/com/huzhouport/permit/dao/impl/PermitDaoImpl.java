package com.huzhouport.permit.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.attendace.model.Location;
import com.huzhouport.common.dao.impl.BaseDaoImpl;
import com.huzhouport.illegal.model.IllegalEvidence;
import com.huzhouport.inspection.model.Inspection;
import com.huzhouport.inspection.model.InspectionEvidenceLink;
import com.huzhouport.inspection.model.InspectionSupplement;
import com.huzhouport.permit.dao.PermitDao;

public class PermitDaoImpl extends BaseDaoImpl<Inspection> implements PermitDao{
	// 查询或显示全部列表分页
	public List<?> searchInspectionListByPage(Inspection inspection, String condition, String sequence,
			int startSet, int maxSet) throws Exception {
		String hql = "select i,u from Inspection i,User u where u.userId=i.inspectionUser ";
		if (condition != "") {
			hql += "and (" + condition + ")";
		}
		if (sequence != null) {
			hql += sequence;
		}
		List<?> list = this.queryqueryEForeignKey(inspection, hql, startSet, maxSet);
		return list;
	}
	//统计条数
	public int countPageInspectionInfo(Inspection inspection, String condition) throws Exception {

		String hql = "select count(*) from Inspection i,User u where u.userId=i.inspectionUser  ";

		if (condition != "") {
			hql += "and (" + condition + ")";
		}

		return this.countEForeignKey(inspection, hql);
	}
	// 查询或显示全部列表分页-手机端
	public List<?> searchInspectionListByPage_mobi(Inspection inspection,String sequence,
			int startSet, int maxSet) throws Exception {
		String hql = "select i,u from Inspection i,User u where u.userId=i.inspectionUser ";
		if (inspection.getInspectionObject()!=null) {
			hql +=  "and (u.name like '%"+inspection.getInspectionObject()+"%' or i.inspectionObject like '%"+inspection.getInspectionObject()+"%')";
		}
		if (sequence != null) {
			hql += sequence;
		}
		List<?> list = this.queryqueryEForeignKey(inspection, hql, startSet, maxSet);
		return list;
	}
	//统计条数-手机端
	public int countPageInspectionInfo_mobi(Inspection inspection) throws Exception {

		String hql = "select count(*) from Inspection i,User u where u.userId=i.inspectionUser  ";

		if (inspection.getInspectionObject()!=null) {
			hql +=  "and (u.name like '%"+inspection.getInspectionObject()+"%' or i.inspectionObject like '%"+inspection.getInspectionObject()+"%')";
		}

		return this.countEForeignKey(inspection, hql);
	}
	// 查询或显示全部列表分页-手机端-待审核
	public List<?> searchInspectionListByPageByCheck_mobi(Inspection inspection, String sequence,
			int startSet, int maxSet) throws Exception {
		String hql = "select i,u from Inspection i,User u where u.userId=i.inspectionUser and i.reviewResult!=1 ";
		if (inspection.getInspectionObject()!=null) {
			hql +=  "and (u.name like '%"+inspection.getInspectionObject()+"%' or i.inspectionObject like '%"+inspection.getInspectionObject()+"%')";
		}
		if (sequence != null) {
			hql += sequence;
		}
		List<?> list = this.queryqueryEForeignKey(inspection, hql, startSet, maxSet);
		return list;
	}
	//统计条数-手机端-待审核
	public int countPageInspectionInfoByCheck_mobi(Inspection inspection) throws Exception {

		String hql = "select count(*) from Inspection i,User u where u.userId=i.inspectionUser and i.reviewResult!=1 ";

		if (inspection.getInspectionObject()!=null) {
			hql +=  "and (u.name like '%"+inspection.getInspectionObject()+"%' or i.inspectionObject like '%"+inspection.getInspectionObject()+"%')";
		}

		return this.countEForeignKey(inspection, hql);
	}
	//显示许可信息
	public  List<?> showInfoByInspectionId(Inspection inspection)throws Exception {
		String hql = "select i,u from Inspection i,User u where u.userId=i.inspectionUser and" +
				" i.inspectionId="+inspection.getInspectionId();
		return this.hibernateTemplate.find(hql);
	}
	//显示许可审核信息
	public  List<?> showCheckInfoByInspectionId(Inspection inspection)throws Exception {
		String hql = "select i,u from Inspection i,User u where u.userId=i.reviewUser and i.inspectionId="+inspection.getInspectionId();
		return this.hibernateTemplate.find(hql);
	}
	//显示许可附件信息
	public  List<?> showEvidenceByInspectionId(Inspection inspection)throws Exception {
		String hql = "select ie from IllegalEvidence as ie,InspectionEvidenceLink as iel where iel.evidenceId=ie.evidenceId and iel.inspectionId="+inspection.getInspectionId();
		return this.hibernateTemplate.find(hql);
	}
	//显示许可补充信息
	public  List<?> showSupplementByInspectionId(Inspection inspection)throws Exception {
		String hql = "select ils,u from InspectionSupplement ils,User u where u.userId=ils.userId and ils.inspectionId="+inspection.getInspectionId()+" order by ils.supplementTime desc";
		return this.hibernateTemplate.find(hql);
	}
	//增加许可踏勘信息
	public  String addInspectionByInspectionId(Inspection inspection)throws Exception {
		Serializable ss=this.hibernateTemplate.save(inspection);
		return ss.toString();
	}
	//增加许可踏勘信息
	public  String addLocation(Location location)throws Exception {
		return this.hibernateTemplate.save(location).toString();
	}
	//修改许可踏勘信息
	public  String updateInspectionByInspectionId(Inspection inspection)throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> conditionMap = new HashMap<String, String>();
		map.put("inspectionContent", inspection.getInspectionContent());
		conditionMap.put("inspectionId", String.valueOf(inspection.getInspectionId()));
		this.modifyByConditions(inspection,map,conditionMap,null);
	return null;
	}
	//增加许可补充信息
	public  String addInspectionSupplement(InspectionSupplement inspectionSupplement)throws Exception {
		this.hibernateTemplate.saveOrUpdate(inspectionSupplement);
		return null;
	}
	//保存许可附件表
	public  String addIllegalEvidence(IllegalEvidence illegalEvidence)throws Exception {		
		return this.hibernateTemplate.save(illegalEvidence).toString();
	}
	
	//保存许可附件关联表
	public  String addInspectionEvidenceLink(InspectionEvidenceLink inspectionEvidenceLink)throws Exception {		
		this.hibernateTemplate.save(inspectionEvidenceLink);
		return null;
	}
	//删除许可附件关联表
	public String deleteInspectionEvidenceLink(InspectionEvidenceLink inspectionEvidenceLink)throws Exception {		
		this.hibernateTemplate.delete(inspectionEvidenceLink);
		return null;
	}
	//删除许可附件表
	public String deleteIllegalEvidence(IllegalEvidence illegalEvidence)throws Exception {		
		this.hibernateTemplate.delete(illegalEvidence);
		return null;
	}
	//查找单个许可附件
	@SuppressWarnings("unchecked")
	public List<IllegalEvidence> findInspectionEvidenceByevidenceId(int evidenceId)throws Exception {	
		String hql="from IllegalEvidence ile where ile.evidenceId="+evidenceId;	
		return this.hibernateTemplate.find(hql);
	}
	//审核许可信息
	public String checkInspectionByInspectionId(Inspection inspection)throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> conditionMap = new HashMap<String, String>();
		map.put("reviewUser", String.valueOf(inspection.getReviewUser()));
		map.put("reviewResult", String.valueOf(inspection.getReviewResult()));
		map.put("reviewComment", String.valueOf(inspection.getReviewComment()));
		map.put("reviewWether", String.valueOf(inspection.getReviewWether()));
		map.put("reviewTime", inspection.getReviewTime());
		conditionMap.put("inspectionId", inspection.getInspectionId()+"");
		this.modifyByConditions(inspection,map,conditionMap,null);
		return null;
	}
	//位置信息
	public List<?> showLocationByInspectionId(Inspection inspection)throws Exception{
		String hql="select lo from Location as lo,Inspection as i where i.inspectionLocation=lo.locationID" +
				" and i.inspectionId="+inspection.getInspectionId();
		return this.hibernateTemplate.find(hql);
	}
}
