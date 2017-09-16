package com.huzhouport.patrol.dao;

import java.util.List;

import com.huzhouport.attendace.model.Location;
import com.huzhouport.illegal.model.IllegalEvidence;
import com.huzhouport.patrol.model.Patrol;
import com.huzhouport.patrol.model.PatrolEvidenceLink;
import com.huzhouport.patrol.model.PatrolSupplement;

public interface PatrolDao {
	// 查询或显示全部列表分页
	public List<?> searchPatrolListByPage(Patrol patrol, String condition, String sequence,
			int startSet, int maxSet) throws Exception ;
	//统计条数
	public int countPagePatrolInfo(Patrol patrol, String condition) throws Exception;
	// 查询或显示全部列表分页-手机端
	public List<?> searchPatrolListByPage_mobi(Patrol patrol, String sequence,
			int startSet, int maxSet) throws Exception;
	//统计条数-手机端
	public int countPagePatrolInfo_mobi(Patrol patrol) throws Exception;
	//显示详细信息
	public  List<?> showInfoByPatrolId(Patrol patrol)throws Exception;	
	//显示附件信息
	public  List<?> showEvidenceByPatrolId(Patrol patrol)throws Exception ;
	//显示补充信息
	public  List<?> showSupplementByPatrolId(Patrol patrol)throws Exception;
	//增加信息
	public  String addPatrolByPatrolId(Patrol patrol)throws Exception;
	//增加位置信息
	public  String addLocation(Location location)throws Exception;
	//修改信息
	public  String updatePatrolByPatrolId(Patrol patrol)throws Exception;
	//增加补充信息
	public  String addPatrolSupplement(PatrolSupplement patrolSupplement)throws Exception;
	//保存附件表
	public  String addIllegalEvidence(IllegalEvidence illegalEvidence)throws Exception;
	//保存附件关联表
	public  String addPatrolEvidenceLink(PatrolEvidenceLink patrolEvidenceLink)throws Exception;
	//删除附件关联表
	public String deletePatrolEvidenceLink(PatrolEvidenceLink patrolEvidenceLink)throws Exception;
	//删除附件表
	public String deleteIllegalEvidence(IllegalEvidence illegalEvidence)throws Exception;
	//查找单个许可附件
	public List<IllegalEvidence> findPatrolEvidenceByevidenceId(int evidenceId)throws Exception;
	//位置信息
	public List<?> showLocationByPatrolId(Patrol patrol)throws Exception;
	
}
