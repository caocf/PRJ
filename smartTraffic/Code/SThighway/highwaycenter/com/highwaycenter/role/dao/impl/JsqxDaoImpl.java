package com.highwaycenter.role.dao.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import com.highwaycenter.role.dao.JsqxDao;
import com.highwaycenter.role.model.HJcJbqx;
import com.highwaycenter.role.model.HJcJsqx;


@Repository("jsqxdao")
public class JsqxDaoImpl  extends BaseDaoDB<HJcJsqx> implements JsqxDao{

	@Override
	public void save(HJcJsqx jsqx) {
		super.save(jsqx);
		
	}

	@Override
	public void delete(HJcJsqx jsqx) {
		super.delete(jsqx);
		
	}

	@Override
	public HJcJsqx findById(int jsqx_id) {
		return super.findUnique(new HJcJsqx(), "bh", jsqx_id);
	}

	@Override
	public BaseQueryRecords findByRole(int jsbh) {
		 HQL hql = new HQL("from HJcJsqx jsqx where jsqx.HJcJbjsb.jsbh = "+jsbh);
			return super.find(hql);
	}

	@Override
	public void sqlOperate(String sql) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int deleteByJs(int jsbh) {
		String sql = "delete from h_jc_jsqx where jsbh = "+jsbh;
		return super.delete(new SQL(sql));
		
	}

	@Override
	public BaseQueryRecords findByHql(HQL hql) {
		
		return super.find(hql);
	}

	@Override
	public List<Integer> selectQxbhListByRole(Integer jsbh) {
		String sql = "select qxbh from h_jc_jsqx where jsbh = "+jsbh;
		List<Integer> bhlist = (List<Integer>) super.find(new SQL(sql)).getData();
		return bhlist;
	}

	@Override
	public BaseQueryRecords findBySql(SQL sql) {
		
		return super.find(sql);
	}

	@Override
	public List<HJcJbqx> findQxmcsByJs(int jsbh) {
	    String hql = "select a.HJcJbqx from HJcJsqx a where a.HJcJbjsb.jsbh ="+jsbh;
	    @SuppressWarnings("unchecked")
		List<HJcJbqx> qxmcs =  (List<HJcJbqx>) super.find(new HQL(hql)).getData();
		return qxmcs;
	}
	
	


}
