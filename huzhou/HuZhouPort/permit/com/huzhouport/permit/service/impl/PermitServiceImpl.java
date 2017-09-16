package com.huzhouport.permit.service.impl;

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
import com.huzhouport.inspection.model.Inspection;
import com.huzhouport.inspection.model.InspectionEvidenceLink;
import com.huzhouport.inspection.model.InspectionSupplement;
import com.huzhouport.permit.dao.PermitDao;
import com.huzhouport.permit.service.PermitService;

public class PermitServiceImpl extends BaseServiceImpl<Inspection> implements PermitService{
	private PermitDao permitDao;
	private QueryConditionSentence qcs = new QueryConditionSentence();// 封装条件
	public void setPermitDao(PermitDao permitDao) {
		this.permitDao = permitDao;
	}
	//查询条数
	public Map<String,Integer> countPageInspectionInfo(Inspection inspection,int pageSize)throws Exception{

		Map<String,Integer> map=new HashMap<String, Integer>();
		int total = this.permitDao.countPageInspectionInfo(inspection,qcs.QCS(inspection.getQueryCondition()));
		map.put("allTotal", total);
		int pages = calculateTotalPage(total, pageSize); // 获取计算分页页数
		map.put("pages", pages);
		return map;
	
	}
	
	// 查询或显示全部列表
	public List<?> searchInspectionInfo(Inspection inspection,int pageNo, int pageSize) throws Exception{
		int beginIndex = (pageNo - 1) * pageSize;
		return this.permitDao.searchInspectionListByPage(inspection,qcs.QCS(inspection.getQueryCondition()),
				qcs.Sequence(inspection.getQueryCondition()), beginIndex,pageSize);
		
	}
	//查询条数-手机端
	public Map<String,Integer> countPageInspectionInfo_mobi(Inspection inspection,int pageSize)throws Exception{

		Map<String,Integer> map=new HashMap<String, Integer>();
		int total = this.permitDao.countPageInspectionInfo_mobi(inspection);
		map.put("allTotal", total);
		int pages = calculateTotalPage(total, pageSize); // 获取计算分页页数
		map.put("pages", pages);
		return map;
	
	}
	
	// 查询或显示全部列表-手机端
	public List<?> searchInspectionInfo_mobi(Inspection inspection,int pageNo, int pageSize) throws Exception{
		int beginIndex = (pageNo - 1) * pageSize;
		return this.permitDao.searchInspectionListByPage_mobi(inspection,qcs.Sequence(inspection.getQueryCondition()), beginIndex,pageSize);
		
	}
	//查询条数-手机端-待审核
	public Map<String,Integer> countPageInspectionInfoByCheck_mobi(Inspection inspection,int pageSize)throws Exception{

		Map<String,Integer> map=new HashMap<String, Integer>();
		int total = this.permitDao.countPageInspectionInfoByCheck_mobi(inspection);
		map.put("allTotal", total);
		int pages = calculateTotalPage(total, pageSize); // 获取计算分页页数
		map.put("pages", pages);
		return map;
	
	}
	
	// 查询或显示全部列表-手机端-待审核
	public List<?> searchInspectionInfoByCheck_mobi(Inspection inspection,int pageNo, int pageSize) throws Exception{
		int beginIndex = (pageNo - 1) * pageSize;
		return this.permitDao.searchInspectionListByPageByCheck_mobi(inspection,qcs.Sequence(inspection.getQueryCondition()), beginIndex,pageSize);
		
	}

	//显示许可信息
	public  List<?> showInfoByInspectionId(Inspection inspection)throws Exception{
		return this.permitDao.showInfoByInspectionId(inspection);
	}
	//显示审核信息
	public  List<?> showCheckInfoByInspectionId(Inspection inspection)throws Exception{
		return this.permitDao.showCheckInfoByInspectionId(inspection);
	}
	//显示许可附件信息
	public  List<?> showEvidenceByInspectionId(Inspection inspection)throws Exception{
		return this.permitDao.showEvidenceByInspectionId(inspection);
	}
	//显示许可补充信息
	public  List<?> showSupplementByInspectionId(Inspection inspection)throws Exception{
		return this.permitDao.showSupplementByInspectionId(inspection);
	}
	//增加许可取证信息
	public  String addInspectionByInspectionId(Inspection inspection)throws Exception{
		
		return this.permitDao.addInspectionByInspectionId(inspection);
	}
	//补充许可信息
	public  String addSupplementByInspectionId(Inspection inspection,IllegalEvidence illegalEvidence,InspectionSupplement inspectionSupplement)throws Exception{
		int inspectionId=inspection.getInspectionId();
		//修改许可表
		this.permitDao.updateInspectionByInspectionId(inspection);
		//添加补充信息表
		inspectionSupplement.setInspectionId(inspectionId);
		this.permitDao.addInspectionSupplement(inspectionSupplement);
		//添加附件并保存
		addEvendice(inspectionId,illegalEvidence);
		return null;
	}
	public void addEvendice(int inspectionId,IllegalEvidence illegalEvidence) throws Exception{
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
	                int iEvidenceId=Integer.parseInt(this.permitDao.addIllegalEvidence(ILE));
	                //保存许可附件关联表
	                InspectionEvidenceLink iel=new InspectionEvidenceLink();
	                iel.setEvidenceId(iEvidenceId);
	                iel.setInspectionId(inspectionId);
                   this.permitDao.addInspectionEvidenceLink(iel);
	            }
	        } 
	}
	
	//审核许可信息
	public String checkInspectionByInspectionId(Inspection inspection)throws Exception{
		return this.permitDao.checkInspectionByInspectionId(inspection);
	}
	//位置信息
	public List<?> showLocationByInspectionId(Inspection inspection)throws Exception{
		return this.permitDao.showLocationByInspectionId(inspection);
	}
	
	//增加许可取证信息
	public String addInspectionByInspectionId(Location location,Inspection inspection,IllegalEvidence illegalEvidence)throws Exception{
		//保存位置信息
		int locationId=Integer.parseInt(this.permitDao.addLocation(location));
		//保存许可信息
		inspection.setInspectionLocation(locationId);
		int inspectionId=Integer.parseInt(this.permitDao.addInspectionByInspectionId(inspection));
		//添加附件并保存
		addEvendice(inspectionId,illegalEvidence);

		return null;
	}

}
