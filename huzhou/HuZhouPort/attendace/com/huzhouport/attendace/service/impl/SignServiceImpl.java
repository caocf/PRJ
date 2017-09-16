package com.huzhouport.attendace.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.attendace.dao.LocationDao;
import com.huzhouport.attendace.dao.SignDao;
import com.huzhouport.attendace.model.Location;
import com.huzhouport.attendace.model.Sign;
import com.huzhouport.attendace.service.SignService;
import com.huzhouport.common.service.impl.BaseServiceImpl;
import com.huzhouport.common.util.QueryConditionSentence;

public class SignServiceImpl extends BaseServiceImpl<Sign> implements SignService{
	private QueryConditionSentence qcs = new QueryConditionSentence();// 封装条件
	private SignDao signDao;
	private LocationDao locationDao;

	public void setSignDao(SignDao signDao) {
		this.signDao = signDao;
	}

	public String addSignInfo(Sign sign,Location location) throws Exception {
		sign.setSignTime(new Date());
		Map<String,Integer> map=new HashMap<String, Integer>();
		map=this.signDao.findSignByCondition(sign);
		int in=map.get("in");
		int out=map.get("out");
		int a=in-out;
		//当今天签到数-签退数>0,不能签到，否则可签到不可签退
		if(a>0){
			//不能签到
			if(sign.getSignStatus()==0){
				throw new Exception("今天您已经签过到！");
			}
		}else{
			//不能签退
			if(sign.getSignStatus()==1){
				if(in==0){
					throw new Exception("今天您还未签到，所以无法签退！");
				}
				throw new Exception("您今天已经签退过了！");				
			}
			
		}
		this.locationDao.save(location);
		sign.setSignLocation(location.getLocationID());	
		this.signDao.addSignInfo(sign);
		return null;
	}

	public Map<String, Integer> countPageSignInfo(Sign sign, int pageSize)
			throws Exception {
		
		Map<String,Integer> map=new HashMap<String, Integer>();
		int total = this.signDao.countPageSignInfo(sign, qcs
				.QCS(sign.getQueryCondition()));
		map.put("allTotal", total);
		int pages = calculateTotalPage(total, pageSize); // 获取计算分页页数
		map.put("pages", pages);
		return map;
	}

	public List<?> searchSignInfo(Sign sign, int pageNo, int pageSize)
			throws Exception {
		int beginIndex = (pageNo - 1) * pageSize;
		return this.signDao.searchSignInfo(sign, qcs.QCS(sign
				.getQueryCondition()), qcs.Sequence(sign
				.getQueryCondition()), beginIndex, pageSize);

	}

	public void setLocationDao(LocationDao locationDao) {
		this.locationDao = locationDao;
	}

	public List<?> seeSignInfoID(Sign sign) throws Exception {
		
		return this.signDao.seeSignInfoID(sign);
	}
	

}
