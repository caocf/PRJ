package com.highwaycenter.legal.service.impl;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.common.action.BaseResult;
import com.common.action.Constants;
import com.common.action.Constants.LegalState;
import com.common.dao.BaseQueryRecords;
import com.common.service.BaseService;
import com.common.utils.DateTimeUtil;
import com.highwaycenter.legal.dao.XzpunishmentDao;
import com.highwaycenter.legal.model.HXzPunishment;
import com.highwaycenter.legal.service.XzPunishmentService;
import com.highwaycenter.gljg.dao.GljgDao;
@Service("punishmentservice")
public class XzPunishmentServiceImpl extends BaseService implements XzPunishmentService{
	
	@Resource(name="xzpunishimentdao")
    private XzpunishmentDao punishmentDao;
	@Resource(name="gljgdao")
	private GljgDao gljgDao;

	@Override
	public BaseResult saveXzPunishment(HXzPunishment punishment) {
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		int countname = this.punishmentDao.countSameNameByWh(punishment.getXzcfjdwh(), null,0); 
		if(countname>0){
			return new BaseResult(1001,"行政处罚决定文号已存在,请输入正确的文号！");
		}
		//设置处罚日期的Date
		Date xzcfrqdate = null;
		String rqstr = punishment.getXzcfrq();
		if(rqstr!=null&&!rqstr.trim().equals("")){
			try {
				xzcfrqdate = DateTimeUtil.getDateByStringFmt(rqstr, "yyyy-MM-dd");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		punishment.setXzcfrqdate(xzcfrqdate);
		punishment.setXzcfly(HXzPunishment.XZCFLY_LOACL); //设置本地录入标志
		punishment.setOuGuid(Constants.LEGAL_OUGUID);  //设置本地的OuGuid
		punishment.setOuName(Constants.LEGAL_OUNAME);  //设置本地的OuName
		punishment.setReginname(Constants.LEGAL_REGIONNAME); //设置地区名字
		//根据文书判断是新增还是删除
		int deletesamewh = this.punishmentDao.countSameNameByWh(punishment.getXzcfjdwh(), null,1); 
		if(deletesamewh>0){
		     Integer bh = this.punishmentDao.selectcountSameByWh(punishment.getXzcfjdwh(), 1);
		     //查询上次的数据版本
		     Integer dateversion = (Integer) this.punishmentDao.selectValueByKey("dateversion", bh);
		     //查询上次同步时间
		     String synctime = (String)this.punishmentDao.selectValueByKey("synctime", bh);
		     
		     punishment.setXzcfbh(bh);
			 punishment.setState(LegalState.INSERT.toInt());
			 punishment.setIsdelete(0);
			 punishment.setDateversion(dateversion);
			 punishment.setSynctime(synctime);
			 this.punishmentDao.update(punishment);
		}else{
		    punishment.setState(LegalState.INSERT.toInt()); //本地状态：新增
		    punishment.setIsdelete(0);                     //正常状态
		    punishment.setDateversion(1);                  //新增数据版本是1
		    this.punishmentDao.save(punishment);
		}
      
		return result;
	}

	@Override
	public BaseResult updateXzPunishment(HXzPunishment punishment) {
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
	
		String oldxzcfjdwh = (String) this.punishmentDao.selectValueByKey("xzcfjdwh", punishment.getXzcfbh());
		if(!oldxzcfjdwh.equals(punishment.getXzcfjdwh())){
			return new BaseResult(1007,"行政处罚决定文号不能修改！");
		}
		//设置处罚日期的Date
				Date xzcfrqdate = null;
				String rqstr = punishment.getXzcfrq();
				if(rqstr!=null&&!rqstr.trim().equals("")){
					try {
						xzcfrqdate = DateTimeUtil.getDateByStringFmt(rqstr, "yyyy-MM-dd");
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	    punishment.setXzcfrqdate(xzcfrqdate);
	    punishment.setXzcfly(HXzPunishment.XZCFLY_LOACL); //设置本地录入标志
		punishment.setOuGuid(Constants.LEGAL_OUGUID);
		punishment.setOuName(Constants.LEGAL_OUNAME);
		punishment.setReginname(Constants.LEGAL_REGIONNAME);//设置地区名字
		
		//查询上次的数据版本号
	     Integer dateversion = (Integer) this.punishmentDao.selectValueByKey("dateversion", punishment.getXzcfbh());
	     //查询上次同步时间
	     String synctime = (String)this.punishmentDao.selectValueByKey("synctime",  punishment.getXzcfbh());

	     punishment.setDateversion(dateversion);
	     punishment.setSynctime(synctime);
	     punishment.setIsdelete(0);
	     punishment.setState(LegalState.UPDATE.toInt());
	 
		this.punishmentDao.update(punishment);

		return result;	
	}

	@Override
	public BaseResult selectXzPunishmentList(int page, int rows,
			String selectKey, String selectValue,String xzcfjgdm) {
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		BaseQueryRecords records = this.punishmentDao.selectXzcfList(page, rows, 
				xzcfjgdm, selectKey, selectValue);
		result.setObj(records);
		return result;
	}

	@Override
	public BaseResult deleteXzPunishment(int xzcfbh) {
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		Object wh = this.punishmentDao.selectValueByKey("xzcfjdwh", xzcfbh);
		result.setObj(wh);
		this.punishmentDao.delete(xzcfbh);
		return result;
	
	}

	@Override
	public BaseResult selectXzPunishmentXq(int xzcfbh) {
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		HXzPunishment  punish = this.punishmentDao.selectXzPunishmentXq(xzcfbh);
		if(punish.getIsdelete()==0){
			result.setObj(punish);
		}
		return result;
	}

	

	@Override
	public BaseResult selectOptionList(String type) {
		List list = new ArrayList();
		if(type!=null){
		  if(type.equals("1")){//h_xz_task 行政处罚权利事项表
			  list = this.punishmentDao.selectList("h_xz_task", "ITEM_ID", "TASKNAME");
		  }else if(type.equals("2")){//h_xz_cardtype 证件类型表
			  list = this.punishmentDao.selectList("h_xz_cardtype", "typebh", "cardname");
		  }else if(type.equals("3")){//h_xz_cfdxtype 行政处罚类型编号
			  list = this.punishmentDao.selectList("h_xz_cfdxtype", "typebh", "cfdxname");
		  }
		  
		}
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		result.setList(list);
		return result;
		
	}
	
	
	

}
