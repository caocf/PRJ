package com.huzhouport.illegal.service;

import java.util.List;
import java.util.Map;

import com.huzhouport.attendace.model.Location;
import com.huzhouport.cruiselog.model.CruiseLogLink;
import com.huzhouport.illegal.model.AbImage;
import com.huzhouport.illegal.model.Abnormal;
import com.huzhouport.illegal.model.Excutor;
import com.huzhouport.illegal.model.Illegal;
import com.huzhouport.illegal.model.IllegalEvidence;
import com.huzhouport.illegal.model.IllegalEvidenceLink;
import com.huzhouport.illegal.model.IllegalSupplement;

public interface IllegalService {
	//查询条数
	public Map<String,Integer> countPageIllegalInfo(Illegal illegal,int pageSize)throws Exception;
	// 查询或显示全部列表
	public List<?> searchIllegalInfo(Illegal illegal,int pageNo, int pageSize) throws Exception;
	//查询条数-手机端
	public Map<String,Integer> countPageIllegalInfo_mobi(Illegal illegal,int pageSize)throws Exception;
	// 查询或显示全部列表-手机端
	public List<?> searchIllegalInfo_mobi(Illegal illegal,int pageNo, int pageSize) throws Exception;
	//查询条数-手机端-待审核
	public Map<String,Integer> countPageIllegalInfoByCheck_mobi(Illegal illegal,int pageSize)throws Exception;
	// 查询或显示全部列表-手机端-待审核
	public List<?> searchIllegalInfoByCheck_mobi(Illegal illegal,int pageNo, int pageSize) throws Exception;
	// 全部列表
	public List<?> IllegalList() throws Exception;
	//显示违章信息
	public  List<?> showInfoByIllegalId(Illegal illegal)throws Exception;
	//显示审核信息
	public  List<?> showCheckInfoByIllegalId(Illegal illegal)throws Exception;
	//显示违章附件信息
	public  List<?> showEvidenceByIllegalId(Illegal illegal)throws Exception;
	//显示违章补充信息
	public  List<?> showSupplementByIllegalId(Illegal illegal)throws Exception ;
	//增加违章取证信息
	public  String addIllegalByIllegalId(Illegal illegal)throws Exception;
	//补充违章信息
	public  String addSupplementByIllegalId(Illegal illegal,IllegalEvidence illegalEvidence,IllegalSupplement illegalSupplement)throws Exception;
	//删除单个附件
	public String deleteEvidenceByEvidenceId(IllegalEvidenceLink illegalEvidenceLink)throws Exception;
	//审核违章信息
	public String checkIllegalByIllegalId(Illegal illegal)throws Exception;
	//位置信息
	public List<?> showLocationByIllegalId(Illegal illegal)throws Exception;
	//违章缘由列表
	public List<?> showIllegalReasonList()throws Exception;
	//增加违章取证信息
	public String addIllegalByIllegalId(Location location,Illegal illegal,IllegalEvidence illegalEvidence)throws Exception;
	public void addCruiseLogLink(CruiseLogLink cruiseloglink)throws Exception;
	
	
	// 搜索违章缘由列表 -手机端
	public List<?> searchIllegalReasonList(String reasonName)throws Exception;
	
	public List<?> getAbnormalList(String pot,String status,int num,int page); 
	public void addProcessedAbInfo(Abnormal abinfo); 
	public Abnormal getAbnormalById(int id);
	public int saveNewAbInfo(Abnormal abinfo) ;
	public int saveDir(AbImage img);
	public List<?> AbImageByAid(int aid);
	public List<?> ExcutorList(String pot,String state);
	public Excutor getExcutorByName(String name);
	public void UpdateExcutor(Excutor ex);
	public void saveExcutor(Excutor ex);
	public Abnormal upidateAB(Abnormal  ab);
}
