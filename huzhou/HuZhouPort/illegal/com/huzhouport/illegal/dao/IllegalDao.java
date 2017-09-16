package com.huzhouport.illegal.dao;

import java.util.List;

import com.huzhouport.attendace.model.Location;
import com.huzhouport.cruiselog.model.CruiseLogLink;
import com.huzhouport.illegal.model.AbImage;
import com.huzhouport.illegal.model.Abnormal;
import com.huzhouport.illegal.model.Excutor;
import com.huzhouport.illegal.model.Illegal;
import com.huzhouport.illegal.model.IllegalEvidence;
import com.huzhouport.illegal.model.IllegalEvidenceLink;
import com.huzhouport.illegal.model.IllegalSupplement;

public interface IllegalDao {
	// 查询或显示全部列表
	public List<?> searchIllegalInfo(Illegal illegal, String condition, String sequence,
			int startSet, int maxSet) throws Exception;
	//分页
	public int countPageIllegalInfo(Illegal illegal, String condition) throws Exception;
	// 查询或显示全部列表分页-手机端
	public List<?> searchIllegalInfo_mobi(Illegal illegal, String sequence,int startSet, int maxSet) throws Exception;
	//分页-手机端
	public int countPageIllegalInfo_mobi(Illegal illegal) throws Exception;
	// 查询或显示全部列表分页-手机端-待审核
	public List<?> searchIllegalInfoByCheck_mobi(Illegal illegal, String sequence,int startSet, int maxSet) throws Exception;
	//分页-手机端-待审核
	public int countPageIllegalInfoByCheck_mobi(Illegal illegal) throws Exception;
	//全部列表
	public List<?> IllegalList() throws Exception;
	//显示违章主表信息
	public  List<?> showInfoByIllegalId(Illegal illegal)throws Exception;
	//显示审核信息
	public  List<?> showCheckInfoByIllegalId(Illegal illegal)throws Exception;
	//显示违章附件信息
	public  List<?> showEvidenceByIllegalId(Illegal illegal)throws Exception;
	//显示违章补充信息
	public  List<?> showSupplementByIllegalId(Illegal illegal)throws Exception ;
	//增加违章取证信息
	public  String addIllegalByIllegalId(Illegal illegal)throws Exception;
	//修改违章取证信息
	public  String updateIllegalByIllegalId(Illegal illegal)throws Exception;
	//增加违章补充信息
	public  String addIllegalSupplement(IllegalSupplement illegalSupplement)throws Exception;
	//保存违章附件表
	public  String addIllegalEvidence(IllegalEvidence illegalEvidence)throws Exception;
	//保存违章附件关联表
	public  String addIllegalEvidenceLink(IllegalEvidenceLink illegalEvidenceLink)throws Exception;
	//删除违章附件关联表
	public String deleteIllegalEvidenceLink(IllegalEvidenceLink illegalEvidenceLink)throws Exception;
	//删除违章附件表
	public String deleteIllegalEvidence(IllegalEvidence illegalEvidence)throws Exception;
	//查找单个违章附件
	public List<IllegalEvidence> findIllegalEvidenceByevidenceId(int evidenceId)throws Exception;
	//审核违章信息
	public String checkIllegalByIllegalId(Illegal illegal)throws Exception;
	//位置信息
	public List<?> showLocationByIllegalId(Illegal illegal)throws Exception;
	//违章缘由列表
	public List<?> showIllegalReasonList()throws Exception;
	//增加违章位置信息
	public  String addLocation(Location location)throws Exception;
	//显示Webservice中需要的违章信息
	public  List<Object[]> GetWebServiceInfoByIllegalId(Illegal illegal)throws Exception;
	//已提交webservice该数据库
	public String ChengePostStatus(Illegal illegal)throws Exception;
	//增加巡航日志和违章的link表
	public void addCruiseLogLink(CruiseLogLink cruiseloglink)throws Exception;
	// 搜索违章缘由列表 -手机端
	public List<?> searchIllegalReasonList(String reasonName)throws Exception;
	
	public List<?> getAbnormalList(String pot,String statusint ,int num,int page);
	public void addProcessedAbInfo(Abnormal abinfo);
	public Abnormal getAbnormalById(int id);
	public int saveNewAbInfo(Abnormal abinfo);
	public int saveDir(AbImage img);
	public List<?> AbImageByAid(int aid);
	public List<?> ExcutorList(String pot,String state);
	public Excutor getExcutorByName(String name) ;
	public void UpdateExcutor(Excutor ex);
	public void saveExcutor(Excutor ex);
	public Abnormal upidateAB(Abnormal  ab);
	
}
