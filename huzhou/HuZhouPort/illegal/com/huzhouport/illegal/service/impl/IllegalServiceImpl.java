package com.huzhouport.illegal.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.huzhouport.attendace.model.Location;
import com.huzhouport.common.service.impl.BaseServiceImpl;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.ManageFile;
import com.huzhouport.common.util.QueryConditionSentence;
import com.huzhouport.cruiselog.model.CruiseLogLink;
import com.huzhouport.electric.action.GetWSByAxis2;
import com.huzhouport.illegal.dao.IllegalDao;
import com.huzhouport.illegal.model.AbImage;
import com.huzhouport.illegal.model.Abnormal;
import com.huzhouport.illegal.model.Excutor;
import com.huzhouport.illegal.model.Illegal;
import com.huzhouport.illegal.model.IllegalEvidence;
import com.huzhouport.illegal.model.IllegalEvidenceLink;
import com.huzhouport.illegal.model.IllegalSupplement;
import com.huzhouport.illegal.service.IllegalService;
import com.huzhouport.pushmsg.model.PushMsg;
import com.huzhouport.pushmsg.service.PushMsgService;


public class IllegalServiceImpl extends BaseServiceImpl<Illegal> implements IllegalService{
	private IllegalDao illegalDao;
	private PushMsgService pushMsgService;
	private QueryConditionSentence qcs = new QueryConditionSentence();// 封装条件
	
	public void setPushMsgService(PushMsgService pushMsgService) {
		this.pushMsgService = pushMsgService;
	}
	
	public void setIllegalDao(IllegalDao illegalDao) {
		this.illegalDao = illegalDao;
	}
	//查询条数
	public Map<String,Integer> countPageIllegalInfo(Illegal illegal,int pageSize)throws Exception{
        //int userid=illegal.getReviewUser();
		Map<String,Integer> map=new HashMap<String, Integer>();
		//int total = this.illegalDao.countPageIllegalInfo(illegal," il.reviewResult=0 and (il.reviewUser="+userid+" or il.enforecers1="+userid);
		int total = this.illegalDao.countPageIllegalInfo(illegal,qcs.QCS(illegal.getQueryCondition()));
		map.put("allTotal", total);
		int pages = calculateTotalPage(total, pageSize); // 获取计算分页页数
		map.put("pages", pages);
		return map;
	
	}
	
	// 查询或显示全部列表
	public List<?> searchIllegalInfo(Illegal illegal,int pageNo, int pageSize) throws Exception{
		int beginIndex = (pageNo - 1) * pageSize;
		return this.illegalDao.searchIllegalInfo(illegal,qcs.QCS(illegal.getQueryCondition()),
				qcs.Sequence(illegal.getQueryCondition()), beginIndex,pageSize);
		
	}
	//查询条数-手机端
	public Map<String,Integer> countPageIllegalInfo_mobi(Illegal illegal,int pageSize)throws Exception{

		Map<String,Integer> map=new HashMap<String, Integer>();
		int total = this.illegalDao.countPageIllegalInfo_mobi(illegal);
		map.put("allTotal", total);
		int pages = calculateTotalPage(total, pageSize); // 获取计算分页页数
		map.put("pages", pages);
		return map;
	
	}
	
	// 查询或显示全部列表-手机端
	public List<?> searchIllegalInfo_mobi(Illegal illegal,int pageNo, int pageSize) throws Exception{
		int beginIndex = (pageNo - 1) * pageSize;
		return this.illegalDao.searchIllegalInfo_mobi(illegal,qcs.Sequence(illegal.getQueryCondition()), beginIndex,pageSize);
		
	}
	//查询条数-手机端-待审核
	public Map<String,Integer> countPageIllegalInfoByCheck_mobi(Illegal illegal,int pageSize)throws Exception{

		Map<String,Integer> map=new HashMap<String, Integer>();
		int total = this.illegalDao.countPageIllegalInfoByCheck_mobi(illegal);
		map.put("allTotal", total);
		int pages = calculateTotalPage(total, pageSize); // 获取计算分页页数
		map.put("pages", pages);
		return map;
	
	}
	
	// 查询或显示全部列表-手机端-待审核
	public List<?> searchIllegalInfoByCheck_mobi(Illegal illegal,int pageNo, int pageSize) throws Exception{
		int beginIndex = (pageNo - 1) * pageSize;
		List<?> data = this.illegalDao.searchIllegalInfoByCheck_mobi(illegal,qcs.Sequence(illegal.getQueryCondition()), beginIndex,pageSize);
		
		//如果用户主动加载列表，将所有消息推送出去。
		List<Integer> messageids = new ArrayList<Integer>();
		for (int i = 0; i < data.size(); i++) {
			Object[] objs =  (Object[]) data.get(i);
			
			Illegal ill = (Illegal) objs[0];
			messageids.add(ill.getIllegalId());
		}
		this.pushMsgService.pushPushMsg(PushMsg.MODULERID_ILLEGAL, -1, messageids);
		
		
		
		//获得消息的状态
		Map<String,PushMsg> mapMsgs = this.pushMsgService.getPushMsgs(PushMsg.MODULERID_ILLEGAL, -1, messageids);
		for (int i = 0; i < data.size(); i++) {
			Object[] objs =  (Object[]) data.get(i);
			Illegal ill = (Illegal) objs[0];
			
			int status = PushMsg.MSGSTATUS_PUSHED_READ;
			if (mapMsgs.containsKey(""+ill.getIllegalId())) {
				status = mapMsgs.get(""+ill.getIllegalId()).getMsgstatus();
			}
			
			ill.setStatus(status);
		}
		return data;
	}

	// 全部列表
	public List<?> IllegalList() throws Exception{
		return this.illegalDao.IllegalList();
	}
	//显示违章信息
	public  List<?> showInfoByIllegalId(Illegal illegal)throws Exception{
		List<?> data = this.illegalDao.showInfoByIllegalId(illegal);
		
		//读取此信息
		if (data != null && data.size() > 0) {
			Object[] objs =  (Object[]) data.get(0);
			Illegal ill = (Illegal) objs[0];
			
			this.pushMsgService.readPushMsg(PushMsg.MODULERID_ILLEGAL, -1, ill.getIllegalId());
		}
		
		return data;
	}
	//显示审核信息
	public  List<?> showCheckInfoByIllegalId(Illegal illegal)throws Exception{
		return this.illegalDao.showCheckInfoByIllegalId(illegal);
	}
	//显示违章附件信息
	public  List<?> showEvidenceByIllegalId(Illegal illegal)throws Exception{
		return this.illegalDao.showEvidenceByIllegalId(illegal);
	}
	//显示违章补充信息
	public  List<?> showSupplementByIllegalId(Illegal illegal)throws Exception{
		return this.illegalDao.showSupplementByIllegalId(illegal);
	}
	//增加违章取证信息
	public  String addIllegalByIllegalId(Illegal illegal)throws Exception{
		
		return this.illegalDao.addIllegalByIllegalId(illegal);
	}
	//补充违章信息
	public  String addSupplementByIllegalId(Illegal illegal,IllegalEvidence illegalEvidence,IllegalSupplement illegalSupplement)throws Exception{
		int illegalId=illegal.getIllegalId();
		//修改违章表
		this.illegalDao.updateIllegalByIllegalId(illegal);
		//添加或修改补充信息表
		illegalSupplement.setIllegalId(illegalId);
		this.illegalDao.addIllegalSupplement(illegalSupplement);
		//添加附件并保存
		addEvendice(illegalId,illegalEvidence);
		return null;
	}
	public void addEvendice(int illegalId,IllegalEvidence illegalEvidence) throws Exception{
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
	                //保存违章附件表
	                IllegalEvidence ILE=new IllegalEvidence();
	                ILE.setEvidenceKind(1);
	                ILE.setEvidenceName(uploadsName.get(i));
	                ILE.setEvidencePath(sname);	
	                int iEvidenceId=Integer.parseInt(this.illegalDao.addIllegalEvidence(ILE));
	                //保存违章附件关联表
	                IllegalEvidenceLink iel=new IllegalEvidenceLink();
	                iel.setEvidenceId(iEvidenceId);
	                iel.setIllegalId(illegalId);
                   this.illegalDao.addIllegalEvidenceLink(iel);
	            }
	        } 
	}
	//删除单个附件
	public String deleteEvidenceByEvidenceId(IllegalEvidenceLink illegalEvidenceLink)throws Exception{
		int evidenceId=illegalEvidenceLink.getEvidenceId();
		//删除违章附件关联表
		this.illegalDao.deleteIllegalEvidenceLink(illegalEvidenceLink);
		//删除附件表
		List<IllegalEvidence> list=this.illegalDao.findIllegalEvidenceByevidenceId(evidenceId);
		IllegalEvidence ile=new IllegalEvidence();
		ile.setEvidenceId(evidenceId);
		this.illegalDao.deleteIllegalEvidence(ile);
		//删除附件文件
		if(list.size()==1)
		{
		ManageFile manageFile=new ManageFile();
		String sPath=ServletActionContext.getServletContext().getRealPath(
				"/"+GlobalVar.FilePATH+"/"+list.get(0).getEvidencePath());
		manageFile.DeleteFolder(sPath);
		}
		
		return null;
	}
	//审核违章信息  //放在这里
	@SuppressWarnings("unchecked")
	public String checkIllegalByIllegalId(Illegal illegal)throws Exception{
		this.illegalDao.checkIllegalByIllegalId(illegal);
		if(illegal.getReviewResult()==1){
			//显示Webservice中需要的违章信息
		List<Object[]> list_ill=this.illegalDao.GetWebServiceInfoByIllegalId(illegal);
		List<IllegalEvidence> list_evi=(List<IllegalEvidence>) this.illegalDao.showEvidenceByIllegalId(illegal);
		String evi="";
		if(list_evi.size()>0){
			evi=list_evi.get(0).getEvidencePath();
			evi=ServletActionContext.getServletContext().getRealPath(GlobalVar.FilePATH+"/"+evi);
		}
			//审核通过
//			shipNum 船名号
//			zfr1 第一执法人
//			zfr2 第二执法人
//			qzsj 取证时间
//			ayid 违章案由ID(参见字典表)
//			ms 违章描述
//			dd 违章地点
//			zjurl 相关证据链接地址
//			dockreportid 站所ID(参见字典表)

			Boolean ispost=GetWSByAxis2.ShipVoilate(list_ill,evi);
			if(ispost==true){
				this.illegalDao.ChengePostStatus(illegal);
			}
		}
		return null;
	}
	//位置信息
	public List<?> showLocationByIllegalId(Illegal illegal)throws Exception{
		return this.illegalDao.showLocationByIllegalId(illegal);
	}
	//违章缘由列表
	public List<?> showIllegalReasonList()throws Exception{
		return this.illegalDao.showIllegalReasonList();
	}
	//增加违章取证信息
	public String addIllegalByIllegalId(Location location,Illegal illegal,IllegalEvidence illegalEvidence)throws Exception{
		//保存位置信息
		int locationId=Integer.parseInt(this.illegalDao.addLocation(location));
		//保存违章信息
		illegal.setIllegalLocation(locationId);
		int illegalId=Integer.parseInt(this.illegalDao.addIllegalByIllegalId(illegal));
		//添加附件并保存
		addEvendice(illegalId,illegalEvidence);

		//增加违章取证 审核人消息推送
		PushMsg msg = new PushMsg();
				
		msg.setModulerid(PushMsg.MODULERID_ILLEGAL);
		msg.setMsgstatus(PushMsg.MSGSTATUS_NOTPUSH_NOTREAD);
		msg.setMessageid(illegal.getIllegalId());
		msg.setPushmsgtime(new Date());
		msg.setUserid(illegal.getReviewUser());
		
		this.pushMsgService.add(msg);
		
		return String.valueOf(illegalId);
	}
	public void addCruiseLogLink(CruiseLogLink cruiseloglink)throws Exception{
		this.illegalDao.addCruiseLogLink(cruiseloglink);
	}
	// 搜索违章缘由列表 -手机端
	public List<?> searchIllegalReasonList(String reasonName)throws Exception{
		return this.illegalDao.searchIllegalReasonList(reasonName);
	}

	public List<?> getAbnormalList(String pot,String status,int num,int page) {
		
		String s1=pot;String s2=status;
		return this.illegalDao.getAbnormalList(pot, status,num,page);
	}

	public void addProcessedAbInfo(Abnormal abinfo) 
	{
		// TODO Auto-generated method stub
		this.illegalDao.addProcessedAbInfo(abinfo);
	}

	public Abnormal getAbnormalById(int id) {
		// TODO Auto-generated method stub
		return illegalDao.getAbnormalById(id);
	}

	public int saveNewAbInfo(Abnormal abinfo) {
		// TODO Auto-generated method stub
		return illegalDao.saveNewAbInfo(abinfo);
	}

	public int saveDir(AbImage img) 
	{
		// TODO Auto-generated method stub
		return illegalDao.saveDir(img);
	}

	public List<?> AbImageByAid(int aid) {
		// TODO Auto-generated method stub
		return illegalDao.AbImageByAid(aid);
	}

	public List<?> ExcutorList(String pot,String state) {
		// TODO Auto-generated method stub
		return illegalDao.ExcutorList(pot,state);
	}

	public Excutor getExcutorByName(String name) 
	{
		// TODO Auto-generated method stub
		return illegalDao.getExcutorByName(name);
	}

	public void UpdateExcutor(Excutor ex) {
		illegalDao.UpdateExcutor(ex);
		
	}

	public void saveExcutor(Excutor ex) {
		illegalDao.saveExcutor(ex);
		
	}

	public Abnormal upidateAB(Abnormal  ab) {
		illegalDao.upidateAB(ab);
		return null;
	}
	
}
