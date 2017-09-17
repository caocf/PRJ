package com.highwaycenter.role.dao.impl;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import com.highwaycenter.gljg.model.HJcGlry;
import com.highwaycenter.role.dao.RyjsDao;
import com.highwaycenter.role.model.HJcRyjs;

@Repository("ryjsdao")
public class RyjsDaoImpl extends BaseDaoDB<HJcRyjs> implements RyjsDao{

	@Override
	public void save(HJcRyjs ryjs) {
		super.save(ryjs);
		
	}

	@Override
	public void update(HJcRyjs ryjs) {
		super.update(ryjs);
		
	}

	@Override
	public void delete(HJcRyjs ryjs) {
		super.delete(ryjs);
		
	}

	@Override
	public HJcRyjs findByGlry(HJcGlry glry) {
		String hql = "from HJcRyjs js where js.id.HJcGlry.rybh="+glry.getRybh();	
		return (HJcRyjs) super.findUnique(new HQL(hql));
		
	}

	@Override
	public int selectCountByJsbh(int jsbh) {
		String sql = "select count(*) from h_jc_ryjs where jsbh="+ jsbh;	
		return  ((BigInteger)super.findUnique(new SQL(sql))).intValue();
	}

	@Override
	public void deleteByRy(Integer rybh) {
		String sql = "delete from h_jc_ryjs where rybh = "+rybh;
		 super.delete(new SQL(sql));
		
	}

	@Override
	public Integer findjsbhByRy(Integer rybh) {
		String sql = "select jsbh from h_jc_ryjs where rybh ="+rybh;
		Object obj = super.findUnique(new SQL(sql));
		if(obj!=null){
			return (Integer)obj;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> findRylistByJs(Integer jsbh) {
		String sql ="select rybh from h_jc_ryjs where jsbh ="+jsbh;
		return (List<Integer>) super.find(new SQL(sql)).getData();
	}
}
