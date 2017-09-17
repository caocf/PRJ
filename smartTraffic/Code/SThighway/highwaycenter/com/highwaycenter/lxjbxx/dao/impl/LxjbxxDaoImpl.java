package com.highwaycenter.lxjbxx.dao.impl;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import com.highwaycenter.bean.GlryInfo;
import com.highwaycenter.bean.Lxgk;
import com.highwaycenter.lxjbxx.dao.LxjbxxDao;
import com.highwaycenter.lxjbxx.model.HJcLxjbxx;

@Repository("lxjbxxdao")
public class LxjbxxDaoImpl extends BaseDaoDB<HJcLxjbxx> implements LxjbxxDao{

	@Override
	public HJcLxjbxx selectByBzbm(String bzbm) {
		
		return super.findUnique(new HJcLxjbxx(), "bzbm", bzbm);
	}

	@Override
	public BaseQueryRecords selectXqList(Integer page, Integer rows) {
		
		return super.find(new HJcLxjbxx(), page, rows);
	}

	@Override
	public BaseQueryRecords selectGkList(Integer page, Integer rows,Integer xzqhdm,String selectvalue,String selectId) {
	/*	String hql = "select new  com.highwaycenter.bean.Lxgk(a.bzbm, a.lxdm, a.ldbh, a.gldwmc,"+
			"a.lxjc, a.lxdfmc, a.ldqdzh, a.ldzdzh, a.lc,"+
			"a.ldqdmc, a.ldzdmc, a.xzqh, a.xzqhdm,"+
			"a.gydwmc, a.kgrq, a.jgrq, a.ysrq, a.tcrq,a.xjnd) from HJcLxjbxx a";*/
		StringBuffer sql =new StringBuffer( "select a.bzbm, a.lxdm, a.ldbh, a.gldwmc,"+
				"a.lxjc, a.lxdfmc, a.ldqdzh, a.ldzdzh, a.lc,"+
				"a.ldqdmc, a.ldzdmc, a.xzqh, a.xzqhdm,"+
				"a.gydwmc, a.jsdj,a.sjcs  from h_jc_lxjbxx a left join h_jc_xzdj b on left(a.lxdm,1)=b.djszm   where 1=1 ");
		StringBuffer sqlcount = new StringBuffer("select count(*) from h_jc_lxjbxx  a  where 1=1 ");
		if(selectId!=null&&!selectId.equals("")){
			sql.append(" and a.bzbm = '"+selectId +"' ");
			sqlcount.append(" and a.bzbm = '"+selectId +"' ");
		}else{
		
		if(xzqhdm!=null&&!xzqhdm.equals("")){
			sql.append(" and a.xzqhdm = "+xzqhdm +" ");
			sqlcount.append(" and a.xzqhdm = "+xzqhdm +" ");
		}
		if(selectvalue!=null&&!selectvalue.equals("")){//路线代码，路线简称，路线地方名称
			sql.append(" and (a.lxdm like '%"+selectvalue+"%' or a.lxjc like '%"+selectvalue+"%' or a.lxdfmc like '%"
					+selectvalue+"%' )");
			sqlcount.append(" and (a.lxdm like '%"+selectvalue+"%' or a.lxjc like '%"+selectvalue+"%' or a.lxdfmc like '%"
					+selectvalue+"%' )");
		}
		sql.append("  order by b.xzdj asc ,a.lxdm ,a.ldqdzh asc,a.ldzdzh asc");
		}
		 try {
				SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
				SQLQuery qcount =  getCurrentSession().createSQLQuery(sqlcount.toString());
				BigInteger count = (BigInteger)qcount.uniqueResult();  	
				 q.addScalar("bzbm",StandardBasicTypes.STRING);
				 q.addScalar("lxdm",StandardBasicTypes.STRING);
				 q.addScalar("ldbh", StandardBasicTypes.STRING);
				 q.addScalar("gldwmc", StandardBasicTypes.STRING);
				 q.addScalar("lxjc", StandardBasicTypes.STRING);
				 q.addScalar("lxdfmc", StandardBasicTypes.STRING);
				 q.addScalar("ldqdzh", StandardBasicTypes.FLOAT);
				 q.addScalar("ldzdzh", StandardBasicTypes.FLOAT);
				 q.addScalar("lc", StandardBasicTypes.FLOAT);
				 q.addScalar("ldqdmc", StandardBasicTypes.STRING);
				 q.addScalar("ldzdmc",StandardBasicTypes.STRING);
				 q.addScalar("xzqh", StandardBasicTypes.STRING);
				 q.addScalar("xzqhdm", StandardBasicTypes.INTEGER);
				 q.addScalar("gydwmc", StandardBasicTypes.STRING);
				 q.addScalar("jsdj", StandardBasicTypes.STRING);
				 q.addScalar("sjcs", StandardBasicTypes.INTEGER);
				 
				q.setResultTransformer(Transformers.aliasToBean(Lxgk.class));
				// page和rows 都 >0 时返回分页数据
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
	public List<Object[]> selectPropertyList(String key1,String key2) {
		String sql = "select distinct a."+key1 +",a."+key2+" from h_jc_lxjbxx a";
		return (List<Object[]>) super.find(new SQL(sql)).getData();
	}

	@Override
	public BaseQueryRecords selectByHql(HQL hql) {
		return super.find(hql);
	}

	@Override
	public BaseQueryRecords selectByHql(HQL hql, Integer page, Integer rows) {	
		return super.find(hql, page, rows);
	}

	@Override
	public String selectLxjc(String lxdm) {
		String sql = "select distinct lxjc from h_jc_lxjbxx where lxdm='"+lxdm+"'";
		
		return (String)this.findUnique(new SQL(sql));
	}

	@Override
	public BaseQueryRecords selectGkListByCondition(Integer page, Integer rows,
			String condition) {
		StringBuffer sql =new StringBuffer( "select a.bzbm, a.lxdm, a.ldbh, a.gldwmc,"+
				"a.lxjc, a.lxdfmc, a.ldqdzh, a.ldzdzh, a.lc,"+
				"a.ldqdmc, a.ldzdmc, a.xzqh, a.xzqhdm,"+
				"a.gydwmc, a.jsdj,a.sjcs  from h_jc_lxjbxx a left join h_jc_xzdj b on left(a.lxdm,1)=b.djszm   where 1=1 ");
		
		if(condition!=null&&!condition.equals("")){
			sql.append(condition);
		}
		
		sql.append("  order by b.xzdj asc ,a.ldqdzh asc,a.ldzdzh asc,a.lxdm asc");
		 try {
				SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
				 q.addScalar("bzbm",StandardBasicTypes.STRING);
				 q.addScalar("lxdm",StandardBasicTypes.STRING);
				 q.addScalar("ldbh", StandardBasicTypes.STRING);
				 q.addScalar("gldwmc", StandardBasicTypes.STRING);
				 q.addScalar("lxjc", StandardBasicTypes.STRING);
				 q.addScalar("lxdfmc", StandardBasicTypes.STRING);
				 q.addScalar("ldqdzh", StandardBasicTypes.FLOAT);
				 q.addScalar("ldzdzh", StandardBasicTypes.FLOAT);
				 q.addScalar("lc", StandardBasicTypes.FLOAT);
				 q.addScalar("ldqdmc", StandardBasicTypes.STRING);
				 q.addScalar("ldzdmc",StandardBasicTypes.STRING);
				 q.addScalar("xzqh", StandardBasicTypes.STRING);
				 q.addScalar("xzqhdm", StandardBasicTypes.INTEGER);
				 q.addScalar("gydwmc", StandardBasicTypes.STRING);
				 q.addScalar("jsdj", StandardBasicTypes.STRING);
				 q.addScalar("sjcs", StandardBasicTypes.INTEGER);
				 
				q.setResultTransformer(Transformers.aliasToBean(Lxgk.class));
				// page和rows 都 >0 时返回分页数据
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

	@Override
	public String selectKznr(int kzbh ) {
	    String sql = "select kznr from h_jc_lwkzb where kzbh="+kzbh;
		return (String) super.findUnique(new SQL(sql));
	}

	@Override
	public void editKznr(int kzbh, String kznr) {
		String sql = "update h_jc_lwkzb set kznr ='"+kznr+"' where kzbh="+kzbh;
		super.update(new SQL(sql));
		
	}

}
