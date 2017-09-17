package com.highwaycenter.fwss.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.common.action.BaseResult;
import com.common.action.Constants;
import com.common.dao.BaseQueryRecords;
import com.common.service.BaseService;
import com.highwaycenter.fwss.dao.FwqDao;
import com.highwaycenter.fwss.dao.SfzDao;
import com.highwaycenter.fwss.model.HFwFwq;
import com.highwaycenter.fwss.model.HFwSfz;
import com.highwaycenter.fwss.service.FwssService;
import com.highwaycenter.lxjbxx.dao.LxjbxxDao;

@Service("fwssservice")
public class FwssServiceImpl extends BaseService implements FwssService{

	@Resource(name="fwqdao")
	private FwqDao fwqDao;
	@Resource(name="sfzdao")
	private SfzDao sfzDao;
	@Resource(name="lxjbxxdao")
	private LxjbxxDao lxjbxxDao;
	@Override
	public BaseResult selectUnion(int page, int rows, String selectValue,
			String fwssType) {
		BaseQueryRecords records = this.fwqDao.selectUnion(page, rows, selectValue, fwssType);
		BaseResult result =new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		result.setObj(records);
		return result;
	}
	@Override
	public BaseResult selectFwssXq(String fwssbh,int type) {
		
		Integer bh = Integer.valueOf(fwssbh);
		BaseResult result= new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);;
		//解析服务设施编号
		if(type==1){//是服务区类型
			HFwFwq fwq = this.fwqDao.selectFwq(bh);
			String xlmc = fwq.getXlmc();
			if(xlmc!=null&&!xlmc.trim().equals("")){
				String lxjc = this.lxjbxxDao.selectLxjc(xlmc);
				if(lxjc!=null&&!lxjc.trim().equals("")){
					xlmc +="("+lxjc+")";
					//fwq.setXlmc(xlmc);
				}
			}
			fwq.setLxjc(xlmc);
			result.setObj(fwq);
		}
		if(type==2){//是收费站类型
			HFwSfz sfz = this.sfzDao.selectSfz(bh);
			if(sfz.getSfzlxbh().equals(2)){
				sfz.setSfzlxmc("匝道收费站");
			}
			if(sfz.getSfzlxbh().equals(1)){
				sfz.setSfzlxmc("主线收费站");
			}
			String xlmc = sfz.getXlmc();
			if(xlmc!=null&&!xlmc.trim().equals("")){
				String lxjc = this.lxjbxxDao.selectLxjc(xlmc);
				if(lxjc!=null&&!lxjc.trim().equals("")){
					xlmc +="("+lxjc+")";
					
				}
			}
			sfz.setLxjc(xlmc);
			result.setObj(sfz);
		}
		
		return result;
	}
	
	@Override
	public BaseResult deleteFwss(String fwssbh,int type) {
	   Integer bh = Integer.valueOf(fwssbh);
	   BaseResult result = new  BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		//解析服务设施编号
				if(type==1){//是服务区类型
					String fwqname = this.fwqDao.selectnameByBh("h_fw_fwq", "FWQMC", "FWQBH", bh);
					 this.fwqDao.delete(bh);
					result.setObj(fwqname);
					
				}
				if(type==2){//是收费站类型
					String sfzname = this.sfzDao.selectnameByBh("h_fw_sfz", "sfzmc", "sfzbh", bh);
					 this.sfzDao.delete(bh);
					 result.setObj(sfzname);
					
				}
	 	return result;
	}
	
	@Override
	public BaseResult saveFwq(HFwFwq fwq) {
		//检查是否重命名
		int countName = this.fwqDao.countSameName(fwq.getFwqmc(), null);
		if(countName>0){
			return new BaseResult(Constants.FWQ_NAME_REPEAT_CODE,Constants.FWQ_NAME_REPEAT_INFO);
		}
		//保存
		Integer fwqbh = (Integer) this.fwqDao.saveAndReturn(fwq);
		BaseResult result = new  BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		result.setObj(fwqbh);
		return result;
	}
	
	@Override
	public BaseResult updateFwq(HFwFwq fwq) {
		//检查是否重命名
		int countName = this.fwqDao.countSameName(fwq.getFwqmc(), fwq.getFwqbh());
		if(countName>0){
			return new BaseResult(Constants.FWQ_NAME_REPEAT_CODE,Constants.FWQ_NAME_REPEAT_INFO);
		}
		//更新
		this.fwqDao.update(fwq);
		BaseResult result = new  BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		return result;
	}
	@Override
	public BaseResult saveSfz(HFwSfz sfz) {
		//检查是否重命名
		int countName = this.sfzDao.countSameName(sfz.getSfzmc(), null);
	    if(countName>0){
			return new BaseResult(Constants.FWQ_NAME_REPEAT_CODE,Constants.FWQ_NAME_REPEAT_INFO);
		}
		//保存
		Integer sfzbh = (Integer) this.sfzDao.saveAndReturn(sfz);
		BaseResult result = new  BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		result.setObj(sfzbh);
		return result;
	}
	@Override
	public BaseResult updateSfz(HFwSfz sfz) {
		//检查是否重命名
		int countName = this.sfzDao.countSameName(sfz.getSfzmc(), sfz.getSfzbh());
		if(countName>0){
			return new BaseResult(Constants.FWQ_NAME_REPEAT_CODE,Constants.FWQ_NAME_REPEAT_INFO);
		}
		//更新
		this.sfzDao.update(sfz);
		BaseResult result = new  BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		return result;
	}
	
	@Override
	public BaseResult selectFwssList(int page, int rows, String selectValue,
			int type,String selectId) {
		BaseResult result =new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		if(type==1){//服务区list
			BaseQueryRecords records = this.fwqDao.selectFwqList(page, rows, selectValue,selectId);
			result.setObj(records);
		}else if(type==2){
			BaseQueryRecords records = this.sfzDao.selectSfzList(page, rows, selectValue,selectId);
			result.setObj(records);
		}
		return result;
	}

	
	
}
