package com.huzhouport.illegal.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.attendace.model.Location;
import com.huzhouport.common.dao.impl.BaseDaoImpl;
import com.huzhouport.cruiselog.model.CruiseLogLink;
import com.huzhouport.illegal.dao.IllegalDao;
import com.huzhouport.illegal.model.AbImage;
import com.huzhouport.illegal.model.Abnormal;
import com.huzhouport.illegal.model.Excutor;
import com.huzhouport.illegal.model.Illegal;
import com.huzhouport.illegal.model.IllegalEvidence;
import com.huzhouport.illegal.model.IllegalEvidenceLink;
import com.huzhouport.illegal.model.IllegalSupplement;


public class IllegalDaoImpl extends BaseDaoImpl<Illegal> implements IllegalDao{

	// 查询或显示全部列表分页
	public List<?> searchIllegalInfo(Illegal illegal, String condition, String sequence,
			int startSet, int maxSet) throws Exception {
		String hql = "select il,u1,u2,ir from Illegal il,User u1,User u2,IllegalReason ir where u1.userId=il.enforecers1 and u2.userId=il.enforecers2" +
				" and ir.reasonId=il.illegalReason ";
		if (condition != "") {
			hql += "and (" + condition + ")";
		}
		if (sequence != null) {
			hql += sequence;
		}
		List<?> list = this.queryqueryEForeignKey(illegal, hql, startSet, maxSet);
		return list;
	}

	//分页
	public int countPageIllegalInfo(Illegal illegal, String condition) throws Exception {

		String hql = "select count(*) from Illegal il,User u1,User u2,IllegalReason ir  where u1.userId=il.enforecers1 and u2.userId=il.enforecers2 and ir.reasonId=il.illegalReason ";

		if (condition != "") {
			hql += "and (" + condition + ")";
		}
         System.out.println("hql=="+hql);
		return this.countEForeignKey(illegal, hql);
	}
	// 查询或显示全部列表分页-手机端
	public List<?> searchIllegalInfo_mobi(Illegal illegal, String sequence,
			int startSet, int maxSet) throws Exception {
		String hql = "select il,u1,u2,ir from Illegal il,User u1,User u2,IllegalReason ir where u1.userId=il.enforecers1 and u2.userId=il.enforecers2" +
				" and ir.reasonId=il.illegalReason ";
		if (illegal.getIllegalObject() != null) {
			hql += "and (u1.name like '%"+illegal.getIllegalObject()+"%' or u2.name like '%"+illegal.getIllegalObject()+"%'" +
					" or il.illegalObject like '%"+illegal.getIllegalObject()+"%')";
		}
		if (sequence != null) {
			hql += sequence;
		}
		List<?> list = this.queryqueryEForeignKey(illegal, hql, startSet, maxSet);
		return list;
	}

	//分页-手机端
	public int countPageIllegalInfo_mobi(Illegal illegal) throws Exception {

		String hql = "select count(*) from Illegal il,User u1,User u2,IllegalReason ir  where u1.userId=il.enforecers1 and u2.userId=il.enforecers2 and ir.reasonId=il.illegalReason ";

		if (illegal.getIllegalObject() != null) {
			hql += "and (u1.name like '%"+illegal.getIllegalObject()+"%' or u2.name like '%"+illegal.getIllegalObject()+"%'" +
					" or il.illegalObject like '%"+illegal.getIllegalObject()+"%')";
		}

		return this.countEForeignKey(illegal, hql);
	}
	// 查询或显示全部列表分页-手机端-待审核
	public List<?> searchIllegalInfoByCheck_mobi(Illegal illegal, String sequence,
			int startSet, int maxSet) throws Exception {
		String hql = "select il,u1,u2,ir from Illegal il,User u1,User u2,IllegalReason ir where u1.userId=il.enforecers1 and u2.userId=il.enforecers2" +
				" and ir.reasonId=il.illegalReason and il.reviewResult!=1 ";
		if (illegal.getIllegalObject() != null) {
			hql += "and (u1.name like '%"+illegal.getIllegalObject()+"%' or u2.name like '%"+illegal.getIllegalObject()+"%'" +
					" or il.illegalObject like '%"+illegal.getIllegalObject()+"%')";
		}
		hql+=" and il.reviewUser="+illegal.getReviewUser();
		if (sequence != null) {
			hql += sequence;
		}
		List<?> list = this.queryqueryEForeignKey(illegal, hql, startSet, maxSet);
		return list;
	}

	//分页-手机端-驳回
	public int countPageIllegalInfoByCheck_mobi(Illegal illegal) throws Exception {

		String hql = "select count(*) from Illegal il,User u1,User u2,IllegalReason ir  where u1.userId=il.enforecers1 and u2.userId=il.enforecers2 and ir.reasonId=il.illegalReason" +
				" and il.reviewResult!=1 ";

		if (illegal.getIllegalObject() != null) {
			hql += "and (u1.name like '%"+illegal.getIllegalObject()+"%' or u2.name like '%"+illegal.getIllegalObject()+"%'" +
					" or il.illegalObject like '%"+illegal.getIllegalObject()+"%')";
		}
		hql+=" and il.reviewUser="+illegal.getReviewUser();
		return this.countEForeignKey(illegal, hql);
	}
	//全部列表
	public List<?> IllegalList() throws Exception {
		String hql = "from Illegal ";
		return this.hibernateTemplate.find(hql);
	}
	//显示违章信息
	public  List<?> showInfoByIllegalId(Illegal illegal)throws Exception {
		String hql = "select il,u1,u2,ir,u3 from Illegal as il,User u1,User u2,IllegalReason ir,  User u3 where u1.userId=il.enforecers1 and" +
				" u2.userId=il.enforecers2 and u3.userId=il.reviewUser and ir.reasonId=il.illegalReason  and il.illegalId="+illegal.getIllegalId();
		return this.hibernateTemplate.find(hql);
	}
	//显示审核信息
	public  List<?> showCheckInfoByIllegalId(Illegal illegal)throws Exception {
		String hql = "select il,u3 from Illegal as il,User u3 where u3.userId=il.reviewUser and il.illegalId="+illegal.getIllegalId();
		return this.hibernateTemplate.find(hql);
	}
	//显示违章附件信息
	public  List<?> showEvidenceByIllegalId(Illegal illegal)throws Exception {
		String hql = "select ie from IllegalEvidence as ie,IllegalEvidenceLink as iel where iel.evidenceId=ie.evidenceId and iel.illegalId="+illegal.getIllegalId();
		return this.hibernateTemplate.find(hql);
	}
	//显示违章补充信息
	public  List<?> showSupplementByIllegalId(Illegal illegal)throws Exception {
		String hql = "select ils,u from IllegalSupplement ils,User u where u.userId=ils.userId and ils.illegalId="+illegal.getIllegalId()+" order by ils.supplementTime desc";
		return this.hibernateTemplate.find(hql);
	}
	//增加违章取证信息
	public  String addIllegalByIllegalId(Illegal illegal)throws Exception {
		Serializable ss=this.hibernateTemplate.save(illegal);
		System.out.println("ss:"+ss);
		return ss.toString();
	}
	//增加违章位置信息
	public  String addLocation(Location location)throws Exception {
		return this.hibernateTemplate.save(location).toString();
	}
	//增加巡航日志和违章的link表
		public void addCruiseLogLink(CruiseLogLink cruiseloglink)throws Exception {
	    this.hibernateTemplate.save(cruiseloglink);
	}
	//修改违章取证信息
	public  String updateIllegalByIllegalId(Illegal illegal)throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> conditionMap = new HashMap<String, String>();
		map.put("illegalReason", illegal.getIllegalReason()+"");
		map.put("illegalContent", illegal.getIllegalContent());
		conditionMap.put("illegalId", illegal.getIllegalId()+"");
		this.modifyByConditions(illegal,map,conditionMap,null);
	return null;
	}
	//增加违章补充信息
	public  String addIllegalSupplement(IllegalSupplement illegalSupplement)throws Exception {
		this.hibernateTemplate.saveOrUpdate(illegalSupplement);
		return null;
	}
	//保存违章附件表
	public  String addIllegalEvidence(IllegalEvidence illegalEvidence)throws Exception {	
		
		return this.hibernateTemplate.save(illegalEvidence).toString();
	}
	
	//保存违章附件关联表
	public  String addIllegalEvidenceLink(IllegalEvidenceLink illegalEvidenceLink)throws Exception {		
		this.hibernateTemplate.save(illegalEvidenceLink);
		return null;
	}
	//删除违章附件关联表
	public String deleteIllegalEvidenceLink(IllegalEvidenceLink illegalEvidenceLink)throws Exception {		
		this.hibernateTemplate.delete(illegalEvidenceLink);
		return null;
	}
	//删除违章附件表
	public String deleteIllegalEvidence(IllegalEvidence illegalEvidence)throws Exception {		
		this.hibernateTemplate.delete(illegalEvidence);
		return null;
	}
	//查找单个违章附件
	@SuppressWarnings("unchecked")
	public List<IllegalEvidence> findIllegalEvidenceByevidenceId(int evidenceId)throws Exception {	
		String hql="from IllegalEvidence ile where ile.evidenceId="+evidenceId;	
		return this.hibernateTemplate.find(hql);
	}
	//审核违章信息
	public String checkIllegalByIllegalId(Illegal illegal)throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> conditionMap = new HashMap<String, String>();
		map.put("reviewUser", String.valueOf(illegal.getReviewUser()));
		map.put("reviewResult", String.valueOf(illegal.getReviewResult()));
		map.put("reviewComment", String.valueOf(illegal.getReviewComment()));
		map.put("reviewWether", String.valueOf(illegal.getReviewWether()));
		map.put("reviewTime", illegal.getReviewTime());
		conditionMap.put("illegalId", illegal.getIllegalId()+"");
		this.modifyByConditions(illegal,map,conditionMap,null);
		return null;
	}
	//位置信息
	public List<?> showLocationByIllegalId(Illegal illegal)throws Exception{
		String hql="select lo from Location as lo,Illegal as il where il.illegalLocation=lo.locationID" +
				" and il.illegalId="+illegal.getIllegalId();
		return this.hibernateTemplate.find(hql);
	}
	//违章缘由列表
	public List<?> showIllegalReasonList()throws Exception{
		String hql="from IllegalReason";
		return this.hibernateTemplate.find(hql);
	}
	//显示Webservice中需要的违章信息
	@SuppressWarnings("unchecked")
	public  List<Object[]> GetWebServiceInfoByIllegalId(Illegal illegal)throws Exception {
		String hql = "select il.illegalObject,u1.userName,u2.userName,il.illegalTime,ir.refReasonID,il.illegalContent,lo.locationName,d.refDepartID " +
				"from Illegal as il,User u1,User u2,IllegalReason ir,Department d,Location lo " +
				" where u1.userId=il.enforecers1 and il.illegalLocation=lo.locationID and " +
				" u2.userId=il.enforecers2 and ir.reasonId=il.illegalReason and u1.partOfDepartment=d.departmentId and il.illegalId="+illegal.getIllegalId();
		return this.hibernateTemplate.find(hql);
	}
	//已提交webservice该数据库
	public String ChengePostStatus(Illegal illegal)throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> conditionMap = new HashMap<String, String>();
		map.put("isPost", "1");
		conditionMap.put("illegalId",  illegal.getIllegalId()+"");
		this.modifyByConditions(illegal,map,conditionMap,null);
		return null;
	}
	// 搜索违章缘由列表 -手机端
	public List<?> searchIllegalReasonList(String reasonName)throws Exception{
		String hql="from IllegalReason i where i.reasonName like '%"+reasonName+"%'";
		return this.hibernateTemplate.find(hql);
	}

	public List<?> getAbnormalList(String pot,String status,int num,int page) 
	{
		
		// hibernateTemplate.find();
		return this.queryqueryEForeignKey(null,"from Abnormal ab where ab.gatename='"+pot+"' and ab.status="+status+" order by ab.abdate desc",num,page);
	}

	public void addProcessedAbInfo(Abnormal abinfo) 
	{
		
		//hibernateTemplate.refresh(abinfo);
		//hibernateTemplate.clear();
		this.hibernateTemplate.update(abinfo);
	}

	public Abnormal getAbnormalById(int id) 
	{
		
		return (Abnormal) hibernateTemplate.find("from Abnormal ab where ab.aid='"+id+"'").get(0);
	}

	public int saveNewAbInfo(Abnormal abinfo) 
	{
		// TODO Auto-generated method stub
		return (Integer)hibernateTemplate.save(abinfo);
	}

	public int saveDir(AbImage img)
	{
		// TODO Auto-generated method stub
		return (Integer)hibernateTemplate.save(img);
	}

	public List<?> AbImageByAid(int aid) 
	{
		
		return hibernateTemplate.find("select abi.dir from AbImage as abi where abi.abid="+aid);
	}

	public List<?> ExcutorList(String pot,String state) 
	{
		// TODO Auto-generated method stub
		return hibernateTemplate.find("select ex.name from Excutor ex where ex.potid='"+pot+"'"+" and ex.state='"+state+"'");
	}

	public Excutor getExcutorByName(String name) 
	{
		List<?> list=hibernateTemplate.find("from Excutor ex where ex.name='"+name+"'");
		
		if(!list.isEmpty())
		{
			return (Excutor)list.get(0);
		}
		
		return null;
	}

	public void UpdateExcutor(Excutor ex)
	{
		hibernateTemplate.update(ex);
		
	}

	public void saveExcutor(Excutor ex) {
		hibernateTemplate.save(ex);
		
	}

	public Abnormal upidateAB(Abnormal  ab) {
		hibernateTemplate.update(ab);
		return null;
	}
}
