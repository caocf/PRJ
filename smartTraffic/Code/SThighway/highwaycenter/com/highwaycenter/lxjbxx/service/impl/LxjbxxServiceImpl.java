package com.highwaycenter.lxjbxx.service.impl;

//xiugai27
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.common.action.BaseResult;
import com.common.action.Constants;
import com.common.dao.BaseQueryRecords;
import com.common.service.BaseService;
import com.highwaycenter.bean.XzqhBean;
import com.highwaycenter.lxjbxx.dao.LxjbxxDao;
import com.highwaycenter.lxjbxx.model.HJcLxjbxx;
import com.highwaycenter.lxjbxx.service.LxjbxxService;

@Service("lxjbxxservice")
public class LxjbxxServiceImpl extends BaseService implements LxjbxxService{
	
	
	@Resource(name="lxjbxxdao")
	private LxjbxxDao lxjbxxDao;

	@Override
	public BaseResult selectLxgkList(Integer page, Integer rows,Integer xzqhdm,String selectvalue,String selectId) {
	    BaseQueryRecords records = this.lxjbxxDao.selectGkList(page, rows,xzqhdm,selectvalue, selectId);
	    BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
	    result.setObj(records);
		return result;
	}

	@Override
	public BaseResult selectLxqxList(Integer page, Integer rows) {
		BaseQueryRecords records = this.lxjbxxDao.selectXqList(page, rows);
	    BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		result.setObj(records);
	    return result;
	}

	@Override
	public BaseResult selectLxqx(String bzbm) {
		HJcLxjbxx lxjbxx = this.lxjbxxDao.selectByBzbm(bzbm);
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		result.setObj(lxjbxx);
		return result;
	}

	@Override
	public BaseResult selectXzqhList() {
		List<Object[]> xzqhlist = this.lxjbxxDao.selectPropertyList("xzqhdm", "xzqh");
		List xzqhs = new ArrayList();
		if(xzqhlist!=null&&xzqhlist.size()>0){
		  Iterator<Object[]> it = xzqhlist.iterator();
		  while(it.hasNext()){
			  Object[] obj = it.next();
			  XzqhBean xzqh = new XzqhBean((Integer)obj[0],(String)obj[1]);
			  xzqhs.add(xzqh);	  
		  }
		}
		BaseResult result = new BaseResult(1,"行政区划列表获取成功");
		result.setList(xzqhs);
		
		return result;
	}

	@Override
	public BaseResult selectLwjc() {
		List  list = new ArrayList();
		String intru1 = this.lxjbxxDao.selectKznr(1);
		String intru2 = this.lxjbxxDao.selectKznr(2);
		list.add(intru1);
		list.add(intru2);
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		result.setList(list);
		return result;
	}

	@Override
	public BaseResult editLwjc(String jc1, String jc2) {
		this.lxjbxxDao.editKznr(1, jc1);
		this.lxjbxxDao.editKznr(2, jc2);
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		return result;
	}
	
	

}
