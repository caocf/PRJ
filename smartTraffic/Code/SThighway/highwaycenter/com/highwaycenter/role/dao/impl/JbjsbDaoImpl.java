package com.highwaycenter.role.dao.impl;

import java.math.BigInteger;

import org.springframework.stereotype.Repository;

import com.common.action.Constants;
import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import com.highwaycenter.role.dao.JbjsbDao;
import com.highwaycenter.role.model.HJcJbjsb;

@Repository("jbjsbdao")
public class JbjsbDaoImpl extends BaseDaoDB<HJcJbjsb> implements JbjsbDao{

	@Override
	public void save(HJcJbjsb jbjsb) {
		super.save(jbjsb);
		
	}
	
	@Override
	public Integer saveAndReturn(HJcJbjsb jbjsb) {
		return (Integer)super.saveAndReturn(jbjsb);
		
	}

	@Override
	public void update(HJcJbjsb jbjsb) {
		super.update(jbjsb);
		
	}

	@Override
	public void delete(HJcJbjsb jbjsb) {
		super.delete(jbjsb);
		
	}

	@Override
	public HJcJbjsb findById(int jbjsb_id) {
		
		return super.findUnique(new HJcJbjsb(), "jsbh", jbjsb_id);
	}

	@Override
	public BaseQueryRecords findAll() {
		
		return super.find(new HJcJbjsb());
	}

	@Override
	public int  selectCountByBh(Integer jsbh) {
		
	/*	Long jscount = super.count(new HJcJbjsb(), "jsbh", jsbh);
		return jscount.intValue();*/
		String sql = "select count(*) from h_jc_jbjsb where jsbh = "+jsbh;
		return  ((BigInteger)super.find(new SQL(sql)).getData().get(0)).intValue();
	}

	@Override
	public BaseQueryRecords jsListAll(int page, int rows) {	
		//return super.find(new HJcJbjsb(), page, rows);
		String hql = " from HJcJbjsb where jsbh <> "+Constants.SUPERMANAGER_JSBH+" ";
		return super.find(new HQL(hql),page,rows);
		
		
		
	}

	@Override
	public int countSameName(String jsmc,Integer jsbh) {
		   return super.countSameName("jsmc", jsmc, "h_jc_jbjsb","jsbh",jsbh);
		
	}

}
