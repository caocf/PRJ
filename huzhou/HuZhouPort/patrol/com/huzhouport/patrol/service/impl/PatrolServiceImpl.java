package com.huzhouport.patrol.service.impl;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.huzhouport.attendace.model.Location;
import com.huzhouport.common.service.impl.BaseServiceImpl;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.QueryConditionSentence;
import com.huzhouport.illegal.model.IllegalEvidence;
import com.huzhouport.patrol.dao.PatrolDao;
import com.huzhouport.patrol.model.Patrol;
import com.huzhouport.patrol.model.PatrolEvidenceLink;
import com.huzhouport.patrol.model.PatrolSupplement;
import com.huzhouport.patrol.service.PatrolService;


public class PatrolServiceImpl extends BaseServiceImpl<Patrol> implements PatrolService{
	private PatrolDao patrolDao;
	private QueryConditionSentence qcs = new QueryConditionSentence();// 封装条件
	public void setPatrolDao(PatrolDao patrolDao) {
		this.patrolDao = patrolDao;
	}
	//查询条数
	public Map<String,Integer> countPagePatrolInfo(Patrol patrol,int pageSize)throws Exception{

		Map<String,Integer> map=new HashMap<String, Integer>();
		int total = this.patrolDao.countPagePatrolInfo(patrol,qcs.QCS(patrol.getQueryCondition()));
		map.put("allTotal", total);
		int pages = calculateTotalPage(total, pageSize); // 获取计算分页页数
		map.put("pages", pages);
		return map;
	
	}
	
	// 查询或显示全部列表
	public List<?> searchPatrolInfo(Patrol patrol,int pageNo, int pageSize) throws Exception{
		int beginIndex = (pageNo - 1) * pageSize;
		return this.patrolDao.searchPatrolListByPage(patrol,qcs.QCS(patrol.getQueryCondition()),
				qcs.Sequence(patrol.getQueryCondition()), beginIndex,pageSize);
		
	}
	//查询条数-手机端
	public Map<String,Integer> countPagePatrolInfo_mobi(Patrol patrol,int pageSize)throws Exception{

		Map<String,Integer> map=new HashMap<String, Integer>();
		int total = this.patrolDao.countPagePatrolInfo_mobi(patrol);
		map.put("allTotal", total);
		int pages = calculateTotalPage(total, pageSize); // 获取计算分页页数
		map.put("pages", pages);
		return map;
	
	}
	
	// 查询或显示全部列表-手机端
	public List<?> searchPatrolInfo_mobi(Patrol patrol,int pageNo, int pageSize) throws Exception{
		int beginIndex = (pageNo - 1) * pageSize;
		return this.patrolDao.searchPatrolListByPage_mobi(patrol,qcs.Sequence(patrol.getQueryCondition()), beginIndex,pageSize);
		
	}

	//显示详细信息
	public  List<?> showInfoByPatrolId(Patrol patrol)throws Exception{
		return this.patrolDao.showInfoByPatrolId(patrol);
	}
	//显示附件信息
	public  List<?> showEvidenceByPatrolId(Patrol patrol)throws Exception{
		return this.patrolDao.showEvidenceByPatrolId(patrol);
	}
	//显示补充信息
	public  List<?> showSupplementByPatrolId(Patrol patrol)throws Exception{
		return this.patrolDao.showSupplementByPatrolId(patrol);
	}
	//补充信息
	public  String addSupplementByPatrolId(Patrol patrol,IllegalEvidence illegalEvidence,PatrolSupplement patrolSupplement)throws Exception{
		int patrolId=patrol.getPatrolId();
		//修改许可表
		this.patrolDao.updatePatrolByPatrolId(patrol);
		//添加或修改补充信息表
		patrolSupplement.setPatrolId(patrolId);
		this.patrolDao.addPatrolSupplement(patrolSupplement);
		//添加附件并保存
		addEvendice(patrolId,illegalEvidence);
		return null;
	}
	public void addEvendice(int patrolId,IllegalEvidence illegalEvidence) throws Exception{
		List<File> uploads=illegalEvidence.getEf();
		 List<String> uploadsName=illegalEvidence.getEfFileName();
		 if (uploads != null)
	        {
	            int i = 0;
	            for (; i < uploads.size(); i++)
	            {
	                java.io.InputStream is = new java.io.FileInputStream(uploads.get(i));
	                int beginIndex=uploadsName.get(i).lastIndexOf('.'); 
	                String stype=uploadsName.get(i).substring(beginIndex+1);
	                String sname=(new Date()).getTime()+"."+stype;
	                String root=ServletActionContext.getServletContext().getRealPath(GlobalVar.FilePATH) + "/"+sname;
	                java.io.OutputStream os = new java.io.FileOutputStream(root);
	                byte buffer[] = new byte[8192];
	                int count = 0;
	                while ((count = is.read(buffer)) > 0)
	                {
	                    os.write(buffer, 0, count);
	                }
	                os.close();
	                is.close();
	                //保存许可附件表
	                IllegalEvidence ILE=new IllegalEvidence();
	                ILE.setEvidenceKind(1);
	                ILE.setEvidenceName(uploadsName.get(i));
	                ILE.setEvidencePath(sname);	
	                int iEvidenceId=Integer.parseInt(this.patrolDao.addIllegalEvidence(ILE));
	                //保存许可附件关联表
	                PatrolEvidenceLink iel=new PatrolEvidenceLink();
	                iel.setEvidenceId(iEvidenceId);
	                iel.setPatrolId(patrolId);
                   this.patrolDao.addPatrolEvidenceLink(iel);
	            }
	        } 
	}
	
	//位置信息
	public List<?> showLocationByPatrolId(Patrol patrol)throws Exception{
		return this.patrolDao.showLocationByPatrolId(patrol);
	}
	
	//增加信息
	public String addPatrolByPatrolId(Location location,Patrol patrol,IllegalEvidence illegalEvidence)throws Exception{
		//保存位置信息
		int locationId=Integer.parseInt(this.patrolDao.addLocation(location));
		//保存许可信息
		patrol.setPatrolLocation(locationId);
		int patrolId=Integer.parseInt(this.patrolDao.addPatrolByPatrolId(patrol));
		//添加附件并保存
		addEvendice(patrolId,illegalEvidence);

		return null;
	}

}
