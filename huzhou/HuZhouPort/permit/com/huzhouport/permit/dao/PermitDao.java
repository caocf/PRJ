package com.huzhouport.permit.dao;

import java.util.List;

import com.huzhouport.attendace.model.Location;
import com.huzhouport.illegal.model.IllegalEvidence;
import com.huzhouport.inspection.model.Inspection;
import com.huzhouport.inspection.model.InspectionEvidenceLink;
import com.huzhouport.inspection.model.InspectionSupplement;

public interface PermitDao {
	// 查询或显示全部列表分页
	public List<?> searchInspectionListByPage(Inspection inspection, String condition, String sequence,
			int startSet, int maxSet) throws Exception ;
	//统计条数
	public int countPageInspectionInfo(Inspection inspection, String condition) throws Exception;
	// 查询或显示全部列表分页-手机端
	public List<?> searchInspectionListByPage_mobi(Inspection inspection, String sequence,
			int startSet, int maxSet) throws Exception;
	//统计条数-手机端
	public int countPageInspectionInfo_mobi(Inspection inspection) throws Exception;
	// 查询或显示全部列表分页-手机端-待审核
	public List<?> searchInspectionListByPageByCheck_mobi(Inspection inspection,String sequence,
			int startSet, int maxSet) throws Exception;
	//统计条数-手机端-待审核
	public int countPageInspectionInfoByCheck_mobi(Inspection inspection) throws Exception ;
	//显示许可信息
	public  List<?> showInfoByInspectionId(Inspection inspection)throws Exception;
	//显示许可审核信息
	public  List<?> showCheckInfoByInspectionId(Inspection inspection)throws Exception ;
	//显示许可附件信息
	public  List<?> showEvidenceByInspectionId(Inspection inspection)throws Exception ;
	//显示许可补充信息
	public  List<?> showSupplementByInspectionId(Inspection inspection)throws Exception;
	//增加许可踏勘信息
	public  String addInspectionByInspectionId(Inspection inspection)throws Exception;
	//增加许可踏勘信息
	public  String addLocation(Location location)throws Exception;
	//修改许可踏勘信息
	public  String updateInspectionByInspectionId(Inspection inspection)throws Exception;
	//增加许可补充信息
	public  String addInspectionSupplement(InspectionSupplement inspectionSupplement)throws Exception;
	//保存许可附件表
	public  String addIllegalEvidence(IllegalEvidence illegalEvidence)throws Exception;
	//保存许可附件关联表
	public  String addInspectionEvidenceLink(InspectionEvidenceLink inspectionEvidenceLink)throws Exception;
	//删除许可附件关联表
	public String deleteInspectionEvidenceLink(InspectionEvidenceLink inspectionEvidenceLink)throws Exception;
	//删除许可附件表
	public String deleteIllegalEvidence(IllegalEvidence illegalEvidence)throws Exception;
	//查找单个许可附件
	public List<IllegalEvidence> findInspectionEvidenceByevidenceId(int evidenceId)throws Exception;
	//审核许可信息
	public String checkInspectionByInspectionId(Inspection inspection)throws Exception;
	//位置信息
	public List<?> showLocationByInspectionId(Inspection inspection)throws Exception;
	
}
