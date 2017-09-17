package com.highwaycenter.qljbxx.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.common.action.BaseResult;
import com.common.action.Constants;
import com.common.dao.BaseQueryRecords;
import com.common.service.BaseService;
import com.highwaycenter.lxjbxx.dao.XzqhDao;
import com.highwaycenter.qljbxx.dao.QljbxxDao;
import com.highwaycenter.qljbxx.model.HJcQljbxx;
import com.highwaycenter.qljbxx.service.QljbxxService;

@Service("qljbxxservice")
public class QljbxxServiceImpl extends BaseService implements QljbxxService{

	
	@Resource(name="qljbxxdao")
	private QljbxxDao qljbxxDao;
	@Resource(name="xzqhdao")
	private XzqhDao xzqhDao;
	
	@Override
	public BaseResult selectQlgkList(Integer page, Integer rows,Integer xzqhdm,String selectvalue,String selectId) {
		BaseQueryRecords records = this.qljbxxDao.selectGkList(page, rows,xzqhdm,selectvalue,selectId);
	    BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
	    result.setObj(records);
		return result;
	}

	@Override
	public BaseResult selectQlqxList(Integer page, Integer rows) {
		BaseQueryRecords records = this.qljbxxDao.selectXqList(page, rows);
	    BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
	    result.setObj(records);
	    return result;
	}

	@Override
	public BaseResult selectQlqx(String bzbm) {
		HJcQljbxx qljbxx = this.qljbxxDao.selectByBzbm(bzbm);
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		result.setObj(qljbxx);
		return result;
	}

	@Override
	public BaseResult selectXzqhList() {
		List  xzqhlist = this.xzqhDao.selectAll();
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		result.setList(xzqhlist);
		return result;
	}

}
