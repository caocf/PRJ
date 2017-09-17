package com.highwaycenter.role.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.common.action.BaseResult;
import com.common.action.Constants;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import com.common.service.BaseService;
import com.highwaycenter.role.dao.JbqxDao;
import com.highwaycenter.role.dao.QxzDao;
import com.highwaycenter.role.dao.QxzqxdyDao;
import com.highwaycenter.role.dao.impl.QxzqxdyDaoImpl;
import com.highwaycenter.role.model.HJcJbqx;



import com.highwaycenter.role.model.HJcQxz;
import com.highwaycenter.role.model.HJcQxzqxdy;
import com.highwaycenter.role.service.QxzService;


@Service("qxzservice")
public class QxzServiceImpl extends BaseService implements QxzService{

	@Resource(name="qxzdao")
	private QxzDao qxzDao;
	@Resource(name="jbqxdao")
	private JbqxDao jbqxDao;
	@Resource(name="qxzqxdydao")
	private QxzqxdyDao qxzqxdyDao;
	
    /**
     * 版本2权限组接口：新增权限组
     */
	@Override
	public BaseResult qxzSave(HJcQxz qxz, List<Integer> qxjh) {
		//1.验证是否重命名
		int countName = this.qxzDao.countSameName(qxz.getQxzmc(), null);
		if(countName>0){
			return new BaseResult(Constants.QXZMC_RENAME_CODE,Constants.QXZMC_RENAME_INFO);
		}
		//2.保存权限组
		int qxzbh = this.qxzDao.saveAndReturn(qxz);
		//3.保存权限组权限关系
		saveQxzqx(qxzbh,qxjh);
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		result.setObj(qxzbh);
		return result;
	}
	
	 /**
     * 版本2权限组接口2：编辑权限组
     */
	@Override
	public BaseResult qxToQxzSave(HJcQxz qxz, List<Integer> qxjh) {
		//1.验证是否重命名
		int countName = this.qxzDao.countSameName(qxz.getQxzmc(), qxz.getQxzbh());
		if(countName>0){
			return new BaseResult(Constants.QXZMC_RENAME_CODE,Constants.QXZMC_RENAME_INFO);
		}
		//2.编辑权限组
		 this.qxzDao.update(qxz);
		//3.删除可能存在的权限组权限关系
		this.qxzqxdyDao.deleteByQxz(qxz.getQxzbh());
		//4.保存权限组权限关系
		saveQxzqx(qxz.getQxzbh(),qxjh);
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		
		return result;
	}
	
    protected void saveQxzqx(int qxzbh,List<Integer> qxjh){
    	if(qxjh!=null&&qxjh.size()>0){
			Iterator<Integer> it = qxjh.iterator();
			while(it.hasNext()){
				int qxbh = it.next();
				//验证权限是否存在
				HJcJbqx jbqx = this.jbqxDao.findById(qxbh);
				if(jbqx!=null){
					this.qxzqxdyDao.save(new HJcQxzqxdy(qxbh,qxzbh));
				}
			}
		}
    }

	@Override
	public BaseResult qxzDelete(int qxzbh) {
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		String qxzmc = this.qxzDao.selectnameByBh("h_jc_qxz", "qxzmc", "qxzbh", qxzbh);
		result.setObj(qxzmc);
		//删除权限组的权限组权限关系
		this.qxzqxdyDao.deleteByQxz(qxzbh);
		//删除权限组
		this.qxzDao.deleteByBh(qxzbh);
		return result;
	}



}
