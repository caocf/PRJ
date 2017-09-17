package com.highwaycenter.bzbx.dao.impl;


import java.math.BigInteger;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.SQL;
import com.highwaycenter.bzbx.dao.BzbxDao;
import com.highwaycenter.bzbx.model.HBzbxBzbx;
@Repository("bzbxdao")
public class BzbxDaoImpl extends BaseDaoDB<HBzbxBzbx> implements BzbxDao{

	@Override
	public void save(HBzbxBzbx bzbx) {
	     super.save(bzbx);
		
	}

	@Override
	public void update(HBzbxBzbx bzbx) {
		super.update(bzbx);
		
	}

	@Override
	public void delete(HBzbxBzbx bzbx) {
		super.delete(bzbx);
		
	}

	@Override
	public void delete(Integer bzbxbh) {
		String sql = "delete from h_bzbx_bzbx where bzbxbh="+bzbxbh;
		super.delete(new SQL(sql));
		
	}

	@Override
	public HBzbxBzbx selectUnique(Integer bzbxbh) {
		String sql = "select a.*,b.bzbxlxmc,d.lxjc as bzbxlxjc,c.xzqhmc as xzqhmc  from h_bzbx_bzbx as a left"
				+" join h_jc_bzbxlx as b on a.bzbxlxbh=b.bzbxlxbh "
				+ " left join (select distinct lxdm,lxjc from h_jc_lxjbxx) as d on d.lxdm=a.xlmc left join "
				+ " h_jc_xzqh as c on a.xzqhdm=c.xzqhdm  where a.bzbxbh="+bzbxbh;
//		return super.findUnique(new HBzbxBzbx(),"bzbxbh",bzbxbh);
try {
	SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
	 q.addScalar("bzbxbh",StandardBasicTypes.INTEGER);
	 q.addScalar("xzqhdm",StandardBasicTypes.INTEGER);
	 q.addScalar("xlmc", StandardBasicTypes.STRING);
	 q.addScalar("bzbxlxbh",StandardBasicTypes.INTEGER);
	 q.addScalar("gldj", StandardBasicTypes.STRING);
	 q.addScalar("bzwzl", StandardBasicTypes.STRING);
	 q.addScalar("bzwzm", StandardBasicTypes.STRING);
	 q.addScalar("bzwzr", StandardBasicTypes.STRING);
	 q.addScalar("bmnrta", StandardBasicTypes.STRING);
	 q.addScalar("bmnrsl", StandardBasicTypes.STRING);
	 q.addScalar("bmcc", StandardBasicTypes.STRING);
	 q.addScalar("zcxsjgg", StandardBasicTypes.STRING);
	 q.addScalar("azsj", StandardBasicTypes.STRING);			 
	 q.addScalar("zzazdw", StandardBasicTypes.STRING);
	 q.addScalar("gldw", StandardBasicTypes.STRING);
	 q.addScalar("bz", StandardBasicTypes.STRING);
	 q.addScalar("bzbxlxmc", StandardBasicTypes.STRING);
	 q.addScalar("xzqhmc",StandardBasicTypes.STRING);
	
	 q.setResultTransformer(Transformers.aliasToBean(HBzbxBzbx.class));
	 List<HBzbxBzbx> lists = q.list();
	 if (lists!=null&&lists.size() > 0) {
			return lists.get(0);
	 }else{
			return null;
	}
	
} catch (Exception e) {
	e.printStackTrace();
	throw e;
}

}


	@Override
	public BaseQueryRecords selectGkList(int page, int rows, Integer xzqhdm,
			Integer bzbxlxbh, String selectValue,String selectId) {
		StringBuffer sql = new StringBuffer("select  a.bzbxbh,a.bzwzl,a.bzwzm,a.bzwzr,a.bmnrta,a.azsj,b.bzbxlxmc,a.xlmc,a.xlmc as lxjc,a.bzbxlxbh  from h_bzbx_bzbx as a left"
				+" join h_jc_bzbxlx as b on a.bzbxlxbh=b.bzbxlxbh left join h_jc_xzdj d on left(a.xlmc,1)=d.djszm"
				+ "  where 1=1 ");
		StringBuffer sqlcount = new StringBuffer("select count(*) from h_bzbx_bzbx as a  where 1=1 ");
		if(selectId!=null&&!selectId.equals("")){
			sql.append(" and a.bzbxbh ='"+selectId+"' "); 
			sqlcount.append(" and a.bzbxbh ="+selectId+" ");
		}else{

		if(xzqhdm!=null&&!xzqhdm.equals("")){
			sql.append(" and a.xzqhdm ="+xzqhdm+" "); 
			sqlcount.append(" and a.xzqhdm ="+xzqhdm+" ");
		}
		if(bzbxlxbh!=null&&!bzbxlxbh.equals("")){
			sql.append(" and a.bzbxlxbh ="+bzbxlxbh+" ");
			sqlcount.append(" and a.bzbxlxbh ="+bzbxlxbh+" ");
		}
		if(selectValue!=null&&!selectValue.trim().equals("")){
			sql.append(" and (a.bzwzl like '%"+selectValue+"%' or a.bzwzm like '%"+selectValue+"%' or a.bzwzr like '%"+selectValue+"%') ");
			sqlcount.append(" and (a.bzwzl like '%"+selectValue+"%' or a.bzwzm like '%"+selectValue+"%' or a.bzwzr like '%"+selectValue+"%') ");
		}
		sql.append(" order by d.xzdj asc,a.xlmc,a.azsj desc, a.bzbxbh desc");
		}
		try {
			SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
			SQLQuery qcount =  getCurrentSession().createSQLQuery(sqlcount.toString());
			BigInteger count = (BigInteger)qcount.uniqueResult();  	
			 q.addScalar("bzbxbh",StandardBasicTypes.INTEGER);
		
			 q.addScalar("xlmc", StandardBasicTypes.STRING);
			 q.addScalar("lxjc", StandardBasicTypes.STRING);
			 q.addScalar("bzwzl", StandardBasicTypes.STRING);
			 q.addScalar("bzwzm", StandardBasicTypes.STRING);
			 q.addScalar("bzwzr", StandardBasicTypes.STRING);
			 q.addScalar("bmnrta", StandardBasicTypes.STRING);
			 q.addScalar("azsj", StandardBasicTypes.STRING);	
			 q.addScalar("bzbxlxbh", StandardBasicTypes.INTEGER);
			 q.addScalar("bzbxlxmc", StandardBasicTypes.STRING); 
		     q.setResultTransformer(Transformers.aliasToBean(HBzbxBzbx.class));
			
			if (page > 0 && rows > 0) {
				int total = count.intValue();
				q.setFirstResult((page - 1) * rows);
				q.setMaxResults(rows);
				return new BaseQueryRecords(q.list(), total, page, rows);
			} else {
				return new BaseQueryRecords(q.list());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public int countBybh(Integer bh) {
		String sql = "select count(*) from h_bzbx_bzbx where bzbxbh="+bh;
		
		return ((BigInteger)super.find(new SQL(sql)).getData().get(0)).intValue();
	}

	@Override
	public String selectTa(Integer bzbxbh) {
		String sql = "select bmnrta from h_bzbx_bzbx where bzbxbh="+bzbxbh;
		return (String) super.findUnique(new SQL(sql));
	}

	@Override
	public BaseQueryRecords selectGklisCondition(String condition,int page, int rows) {
		StringBuffer sql = new StringBuffer("select  a.bzbxbh,a.bzwzl,a.bzwzm,a.bzwzr,a.bmnrta,a.azsj,b.bzbxlxmc,a.xlmc,a.xlmc as lxjc,a.bzbxlxbh  from h_bzbx_bzbx as a left"
				+" join h_jc_bzbxlx as b on a.bzbxlxbh=b.bzbxlxbh left join h_jc_xzdj d on left(a.xlmc,1)=d.djszm"
				+ "  where 1=1 ");
		
		if(condition!=null&&!condition.equals("")){
			sql.append(condition); 
		}
		sql.append(" order by d.xzdj asc,a.xlmc,a.azsj desc, a.bzbxbh desc");
		
		try {
			SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
			
			//BigInteger count = (BigInteger)qcount.uniqueResult();  	
			 q.addScalar("bzbxbh",StandardBasicTypes.INTEGER);
		
			 q.addScalar("xlmc", StandardBasicTypes.STRING);
			 q.addScalar("lxjc", StandardBasicTypes.STRING);
			 q.addScalar("bzwzl", StandardBasicTypes.STRING);
			 q.addScalar("bzwzm", StandardBasicTypes.STRING);
			 q.addScalar("bzwzr", StandardBasicTypes.STRING);
			 q.addScalar("bmnrta", StandardBasicTypes.STRING);
			 q.addScalar("azsj", StandardBasicTypes.STRING);	
			 q.addScalar("bzbxlxbh", StandardBasicTypes.INTEGER);
			 q.addScalar("bzbxlxmc", StandardBasicTypes.STRING); 
		     q.setResultTransformer(Transformers.aliasToBean(HBzbxBzbx.class));
			
			if (page > 0 && rows > 0) {
				int total = q.list().size();
				q.setFirstResult((page - 1) * rows);
				q.setMaxResults(rows);
				return new BaseQueryRecords(q.list(), total, page, rows);
			} else {
				return new BaseQueryRecords(q.list());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
