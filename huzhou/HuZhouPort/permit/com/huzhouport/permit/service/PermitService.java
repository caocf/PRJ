package com.huzhouport.permit.service;

import java.util.List;
import java.util.Map;

import com.huzhouport.attendace.model.Location;
import com.huzhouport.illegal.model.IllegalEvidence;
import com.huzhouport.inspection.model.Inspection;
import com.huzhouport.inspection.model.InspectionSupplement;

public interface PermitService {
	//查询条数
	public Map<String,Integer> countPageInspectionInfo(Inspection inspection,int pageSize)throws Exception;
	// 查询或显示全部列表
	public List<?> searchInspectionInfo(Inspection inspection,int pageNo, int pageSize) throws Exception;
	//查询条数-手机端
	public Map<String,Integer> countPageInspectionInfo_mobi(Inspection inspection,int pageSize)throws Exception;
	// 查询或显示全部列表-手机端
	public List<?> searchInspectionInfo_mobi(Inspection inspection,int pageNo, int pageSize) throws Exception;
	//查询条数-手机端-待审核
	public Map<String,Integer> countPageInspectionInfoByCheck_mobi(Inspection inspection,int pageSize)throws Exception;
	// 查询或显示全部列表-手机端-待审核
	public List<?> searchInspectionInfoByCheck_mobi(Inspection inspection,int pageNo, int pageSize) throws Exception;
	//显示许可信息
	public  List<?> showInfoByInspectionId(Inspection inspection)throws Exception;
	//显示审核信息
	public  List<?> showCheckInfoByInspectionId(Inspection inspection)throws Exception;
	//显示许可附件信息
	public  List<?> showEvidenceByInspectionId(Inspection inspection)throws Exception;
	//显示许可补充信息
	public  List<?> showSupplementByInspectionId(Inspection inspection)throws Exception;
	//增加许可取证信息
	public  String addInspectionByInspectionId(Inspection inspection)throws Exception;
	//补充许可信息
	public  String addSupplementByInspectionId(Inspection inspection,IllegalEvidence illegalEvidence,InspectionSupplement inspectionSupplement)throws Exception;
	//审核许可信息
	public String checkInspectionByInspectionId(Inspection inspection)throws Exception;
	//位置信息
	public List<?> showLocationByInspectionId(Inspection inspection)throws Exception;
	//增加许可取证信息
	public String addInspectionByInspectionId(Location location,Inspection inspection,IllegalEvidence illegalEvidence)throws Exception;

	
	
	
	
}
