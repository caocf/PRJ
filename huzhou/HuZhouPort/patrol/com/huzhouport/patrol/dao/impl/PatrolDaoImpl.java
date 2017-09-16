package com.huzhouport.patrol.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.attendace.model.Location;
import com.huzhouport.common.dao.impl.BaseDaoImpl;
import com.huzhouport.illegal.model.IllegalEvidence;
import com.huzhouport.patrol.dao.PatrolDao;
import com.huzhouport.patrol.model.Patrol;
import com.huzhouport.patrol.model.PatrolEvidenceLink;
import com.huzhouport.patrol.model.PatrolSupplement;

public class PatrolDaoImpl extends BaseDaoImpl<Patrol> implements PatrolDao{
	// 列表分页-pc端
	public List<?> searchPatrolListByPage(Patrol patrol, String condition, String sequence,
			int startSet, int maxSet) throws Exception {
		String hql = "select p,u from Patrol p,User u where u.userId=p.patrolUser ";
		if (condition != "") {
			hql += "and (" + condition + ")";
		}
		if (sequence != null) {
			hql += sequence;
		}
		List<?> list = this.queryqueryEForeignKey(patrol, hql, startSet, maxSet);
		return list;
	}
	//统计条数
	public int countPagePatrolInfo(Patrol patrol, String condition) throws Exception {

		String hql = "select count(*) from Patrol p,User u where u.userId=p.patrolUser  ";

		if (condition != "") {
			hql += "and (" + condition + ")";
		}

		return this.countEForeignKey(patrol, hql);
	}
	// 查询或显示全部列表分页-手机端
	public List<?> searchPatrolListByPage_mobi(Patrol patrol,String sequence,
			int startSet, int maxSet) throws Exception {
		String hql = "select p,u from Patrol p,User u where u.userId=p.patrolUser ";
		if (patrol.getPatrolObject()!=null) {
			hql +=  "and (u.name like '%"+patrol.getPatrolObject()+"%' or p.patrolObject like '%"+patrol.getPatrolObject()+"%')";
		}
		if (sequence != null) {
			hql += sequence;
		}
		List<?> list = this.queryqueryEForeignKey(patrol, hql, startSet, maxSet);
		return list;
	}
	//统计条数-手机端
	public int countPagePatrolInfo_mobi(Patrol patrol) throws Exception {

		String hql = "select count(*) from Patrol p,User u where u.userId=p.patrolUser  ";

		if (patrol.getPatrolObject()!=null) {
			hql +=  "and (u.name like '%"+patrol.getPatrolObject()+"%' or p.patrolObject like '%"+patrol.getPatrolObject()+"%')";
		}

		return this.countEForeignKey(patrol, hql);
	}
	
	//显示详细信息
	public  List<?> showInfoByPatrolId(Patrol patrol)throws Exception {
		String hql = "select p,u from Patrol p,User u where u.userId=p.patrolUser and" +
				" p.patrolId="+patrol.getPatrolId();
		return this.hibernateTemplate.find(hql);
	}
	
	//显示附件信息
	public  List<?> showEvidenceByPatrolId(Patrol patrol)throws Exception {
		String hql = "select ie from IllegalEvidence as ie,PatrolEvidenceLink as pel where pel.evidenceId=ie.evidenceId and pel.patrolId="+patrol.getPatrolId();
		return this.hibernateTemplate.find(hql);
	}
	//显示补充信息
	public  List<?> showSupplementByPatrolId(Patrol patrol)throws Exception {
		String hql = "select ps,u from PatrolSupplement ps,User u where u.userId=ps.userId and ps.patrolId="+patrol.getPatrolId()+" order by ps.supplementTime desc";
		return this.hibernateTemplate.find(hql);
	}
	//增加信息
	public  String addPatrolByPatrolId(Patrol patrol)throws Exception {
		Serializable ss=this.hibernateTemplate.save(patrol);
		return ss.toString();
	}
	//增加位置信息
	public  String addLocation(Location location)throws Exception {
		return this.hibernateTemplate.save(location).toString();
	}
	//修改许可踏勘信息
	public  String updatePatrolByPatrolId(Patrol patrol)throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> conditionMap = new HashMap<String, String>();
		map.put("patrolContent", patrol.getPatrolContent());
		conditionMap.put("patrolId", String.valueOf(patrol.getPatrolId()));
		this.modifyByConditions(patrol,map,conditionMap,null);
	return null;
	}
	//增加许可补充信息
	public  String addPatrolSupplement(PatrolSupplement patrolSupplement)throws Exception {
		this.hibernateTemplate.saveOrUpdate(patrolSupplement);
		return null;
	}
	//保存许可附件表
	public  String addIllegalEvidence(IllegalEvidence illegalEvidence)throws Exception {		
		return this.hibernateTemplate.save(illegalEvidence).toString();
	}
	
	//保存许可附件关联表
	public  String addPatrolEvidenceLink(PatrolEvidenceLink patrolEvidenceLink)throws Exception {		
		this.hibernateTemplate.save(patrolEvidenceLink);
		return null;
	}
	//删除许可附件关联表
	public String deletePatrolEvidenceLink(PatrolEvidenceLink patrolEvidenceLink)throws Exception {		
		this.hibernateTemplate.delete(patrolEvidenceLink);
		return null;
	}
	//删除许可附件表
	public String deleteIllegalEvidence(IllegalEvidence illegalEvidence)throws Exception {		
		this.hibernateTemplate.delete(illegalEvidence);
		return null;
	}
	//查找单个许可附件
	@SuppressWarnings("unchecked")
	public List<IllegalEvidence> findPatrolEvidenceByevidenceId(int evidenceId)throws Exception {	
		String hql="from IllegalEvidence ile where ile.evidenceId="+evidenceId;	
		return this.hibernateTemplate.find(hql);
	}
	
	//位置信息
	public List<?> showLocationByPatrolId(Patrol patrol)throws Exception{
		String hql="select lo from Location as lo,Patrol as p where p.patrolLocation=lo.locationID" +
				" and p.patrolId="+patrol.getPatrolId();
		return this.hibernateTemplate.find(hql);
	}
}
