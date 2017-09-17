package com.highwaycenter.log.dao.impl;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.SQL;
import com.highwaycenter.bean.CzrzInfo;
import com.highwaycenter.log.dao.CzrzDao;
import com.highwaycenter.log.model.HJcCzrz;

@Repository("czrzdao")
public class CzrzDaoImpl extends BaseDaoDB<HJcCzrz> implements CzrzDao{

	@Override
	public void saveCzrz(HJcCzrz czrz) {
		super.save(czrz);
		
	}

	@Override
	public void deleteCzrz(HJcCzrz czrz) {
		super.delete(czrz);
		
	}

	@Override
	public HJcCzrz findInfoByRzbh(int czrzbh) {
		String sql = "select a.rzbh,a.rzlxbh,a.czr, "+
	" a.rzbt,a.rzmc,a.czsj,b.ryxm,c.rzlxmc) from h_jc_czrz as a left join h_jc_glry as b on "
	+ "a.czr=b.rybh left join h_jc_rzlx as c on a.rzlxbh = c.rzlxbh where a.rzbh = " +czrzbh;
        try{
		SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
			
		 q.addScalar("rzbh",StandardBasicTypes.INTEGER);
		 q.addScalar("rzlxbh",StandardBasicTypes.INTEGER);
		 q.addScalar("czr", StandardBasicTypes.INTEGER);
		 q.addScalar("rzbt",StandardBasicTypes.STRING);
		 q.addScalar("rzmc", StandardBasicTypes.STRING);
		 q.addScalar("czsj", StandardBasicTypes.TIMESTAMP);
		 q.addScalar("ryxm", StandardBasicTypes.STRING);
		 q.addScalar("rzlxmc", StandardBasicTypes.STRING);
		 q.setResultTransformer(Transformers.aliasToBean(HJcCzrz.class));
		 List<HJcCzrz> list = q.list();
		 if(list!=null&&list.size()>0){
			 return list.get(0);
		 }else{
			 return null;
		 }
    }catch(Exception e){
    	e.printStackTrace();
    }
        return null;
	}

	@Override
	public List<CzrzInfo>  excelInputCzrz( String startTime,String endTime, String selectvalue) {
       StringBuffer sql = new StringBuffer("select b.ryxm,a.rzbt,a.rzmc,a.czsj,c.rzlxmc from h_jc_czrz as a left join h_jc_glry as b on "
				+ "a.czr=b.rybh left join h_jc_rzlx as c on a.rzlxbh = c.rzlxbh  where 1=1 ");
       
       if(startTime!=null&&!startTime.trim().equals("")){
			//有开始时间与结束时间
			sql.append("and a.czsj >= '"+startTime+"' ");
			
		}
		if(endTime!=null&&!endTime.trim().equals("")){
			sql.append("and a.czsj <= '"+endTime+"' ");
			
		}
		if(selectvalue!=null&&!selectvalue.trim().equals("")){
			sql.append(" and b.ryxm like '%"+selectvalue+"%' ");
			
		}
		sql.append(" order by a.czsj desc");
		 try {
				SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
	        	 q.addScalar("ryxm", StandardBasicTypes.STRING);
				 q.addScalar("rzbt",StandardBasicTypes.STRING);
				 q.addScalar("rzmc", StandardBasicTypes.STRING);
				 q.addScalar("czsj", StandardBasicTypes.TIMESTAMP);	
				 q.addScalar("rzlxmc", StandardBasicTypes.STRING);
				 q.setResultTransformer(Transformers.aliasToBean(CzrzInfo.class));
				  return q.list();
					
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
	
	}

	@Override
	public HJcCzrz findByRzbh(int czrzbh) {
		
		return super.findUnique(new HJcCzrz(), "rzbh", czrzbh);
	}

	@Override
	public void deleteByRy(Integer rybh) {
		// TODO Auto-generated method stub
		String sql = "delete from h_jc_czrz where czr = "+rybh;
		 super.delete(new SQL(sql));
		
	}

	@Override
	public BaseQueryRecords selectCzrzList(int page, int rows,
			String startTime, String endTime, String selectvalue) {
		StringBuffer sql = new StringBuffer("select a.rzbh,a.rzlxbh,a.czr, "+
				" a.rzbt,a.rzmc,a.czsj,b.ryxm,c.rzlxmc from h_jc_czrz as a left join h_jc_glry as b on "
				+ "a.czr=b.rybh left join h_jc_rzlx as c on a.rzlxbh = c.rzlxbh where 1=1 ");
		
		StringBuffer sqlcount = new StringBuffer("select count(*) from h_jc_czrz as a left join h_jc_glry as b on "
				+ "a.czr=b.rybh left join h_jc_rzlx as c on a.rzlxbh = c.rzlxbh where 1=1 ");
		if(startTime!=null&&!startTime.trim().equals("")){
			//有开始时间与结束时间
			sql.append("and a.czsj >= '"+startTime+"' ");
			sqlcount.append("and a.czsj >= '"+startTime+"' ");
		}
		if(endTime!=null&&!endTime.trim().equals("")){
			sql.append("and a.czsj <= '"+endTime+"' ");
			sqlcount.append("and a.czsj <= '"+endTime+"' ");
		}
		if(selectvalue!=null&&!selectvalue.trim().equals("")){
			sql.append(" and b.ryxm like '%"+selectvalue+"%' ");
			sqlcount.append(" and b.ryxm like '%"+selectvalue+"%' ");
		}
		
		sql.append("order by a.czsj desc");
		
		 try {
				SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
				SQLQuery qcount = getCurrentSession().createSQLQuery(sqlcount.toString());
				BigInteger count = (BigInteger)qcount.uniqueResult();  	
				 q.addScalar("rzbh",StandardBasicTypes.INTEGER);
				 q.addScalar("rzlxbh",StandardBasicTypes.INTEGER);
				 q.addScalar("czr", StandardBasicTypes.INTEGER);
				 q.addScalar("rzbt",StandardBasicTypes.STRING);
				 q.addScalar("rzmc", StandardBasicTypes.STRING);
				 q.addScalar("czsj", StandardBasicTypes.TIMESTAMP);
				 q.addScalar("ryxm", StandardBasicTypes.STRING);
				 q.addScalar("rzlxmc", StandardBasicTypes.STRING);
				 q.setResultTransformer(Transformers.aliasToBean(HJcCzrz.class));
				
				 
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
	public void deletList(String list) {
		String sql = "delete  from h_jc_czrz where rzbh in ("+list+")";
		super.delete(new SQL(sql));
		
	}

}
