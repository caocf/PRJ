package com.huzhouport.patrol.service;

import java.util.List;
import java.util.Map;

import com.huzhouport.attendace.model.Location;
import com.huzhouport.illegal.model.IllegalEvidence;
import com.huzhouport.patrol.model.Patrol;
import com.huzhouport.patrol.model.PatrolSupplement;

public interface PatrolService {
	//查询条数
	public Map<String,Integer> countPagePatrolInfo(Patrol patrol,int pageSize)throws Exception;
	// 查询或显示全部列表
	public List<?> searchPatrolInfo(Patrol patrol,int pageNo, int pageSize) throws Exception;
	//查询条数-手机端
	public Map<String,Integer> countPagePatrolInfo_mobi(Patrol patrol,int pageSize)throws Exception;
	// 查询或显示全部列表-手机端
	public List<?> searchPatrolInfo_mobi(Patrol patrol,int pageNo, int pageSize) throws Exception;
	//显示详细信息
	public  List<?> showInfoByPatrolId(Patrol patrol)throws Exception;
	//显示附件信息
	public  List<?> showEvidenceByPatrolId(Patrol patrol)throws Exception;
	//显示补充信息
	public  List<?> showSupplementByPatrolId(Patrol patrol)throws Exception;
	//补充信息
	public  String addSupplementByPatrolId(Patrol patrol,IllegalEvidence illegalEvidence,PatrolSupplement patrolSupplement)throws Exception;
	//位置信息
	public List<?> showLocationByPatrolId(Patrol patrol)throws Exception;
	//增加许可取证信息
	public String addPatrolByPatrolId(Location location,Patrol patrol,IllegalEvidence illegalEvidence)throws Exception;	
	
}
