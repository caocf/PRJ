package com.highwaycenter.gljg.dao.impl;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.SQL;
import com.highwaycenter.bean.GlryInfo;
import com.highwaycenter.gljg.dao.GlbmDao;
import com.highwaycenter.gljg.model.HJcGlbm;
import com.highwaycenter.gljg.model.HJcGljg;

@Repository("glbmdao")
public class GlbmDaoImpl extends BaseDaoDB<HJcGlbm> implements GlbmDao{

	@Override
	public void save(HJcGlbm glbm) {
		super.save(glbm);
		
	}
	
	@Override
	public String saveAndReturn(HJcGlbm glbm) {
		return (String) super.saveAndReturn(glbm);
		
	}

	@Override
	public void update(HJcGlbm glbm) {
		super.update(glbm);
		
	}

	@Override
	public void delete(HJcGlbm glbm) {
		super.delete(glbm);
		
	}

	@Override
	public HJcGlbm findById(String glbm_id) {
		return super.findUnique(new HJcGlbm(), "bmdm", glbm_id);
	}

	@Override
	public BaseQueryRecords findByKey(String key, Object value, int page,
			int rows) {
		// TODO Auto-generated method stub
		return super.find(new HJcGlbm(),key, value, page, rows);
	}

	@Override
	public int countById(String glbm_id) {
		String sql = "select count(*) from h_jc_glbm where BMDM = '"+  glbm_id +"'";		
		// TODO Autre
		return ((BigInteger)super.find(new SQL(sql)).getData().get(0)).intValue();
	}

	@Override
	public BaseQueryRecords findBySql(SQL sql) {
		// TODO Auto-generated method stub
		return super.find(sql);
	}

	@Override
	public String selectSjjg(String bmdm) {
		String sql = "select ssgljgdm from h_jc_glbm where bmdm ='"+bmdm+"'";
	    return (String) super.findUnique(new SQL(sql));
	}
	
	
	@Override
	public int countSameBm(String jgdm,String bmdm,String bmmc){//有部门代码说明要判断是否是自身
		
		StringBuffer sql = new StringBuffer("select count(*) from h_jc_glbm where ssgljgdm='"+jgdm+"' and bmmc = '"+bmmc+"' ");
		if(bmdm!=null&&!bmdm.trim().equals("")){
			sql.append(" and bmdm <> '"+bmdm+"'");
	      }
		return ((BigInteger)super.find(new SQL(sql.toString())).getData().get(0)).intValue();
	}

	@Override
	public void updateBysql(String bmdm, String bmmc,String bmdesc ) {
		String sql = "update h_jc_glbm set bmmc ='"+bmmc+"',bmdesc ='"+bmdesc+"' where bmdm='"+bmdm+"' ";
		super.update(new SQL(sql));
		
	}

	@Override
	public List<HJcGlbm> selectGlbmBySsjgOrder(String ssgljgdm,
			String orderkey, boolean ifdesc) {
		return (List<HJcGlbm>) super.findOrderBy(new HJcGlbm(), "ssgljgdm", ssgljgdm,orderkey , ifdesc, -1, -1).getData();
		 
	}

	@Override
	public void updateOrderColumn(String bmdm, int ordervalue) {
		String sql = "update h_jc_glbm set ordercolumn ="+ordervalue+" where bmdm='"+bmdm+"' ";
		super.update(new SQL(sql));
		
	}

	@Override
	public String selectSjjgMc(String bmdm) {
		String sql = "select gljgmc from h_jc_gljg a,  h_jc_glbm b  where a.gljgdm=b.ssgljgdm and b.bmdm ='"+bmdm+"'";
	    return (String) super.findUnique(new SQL(sql)); 
	}

	@Override
	public HJcGljg selectSjjgObj(String bmdm) {
		String sql = "select a.gljgdm,a.gljgmc from h_jc_gljg a,  h_jc_glbm b  where a.gljgdm=b.ssgljgdm and b.bmdm ='"+bmdm+"'";
		try {
			SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
			 q.addScalar("gljgdm",StandardBasicTypes.STRING);
			 q.addScalar("gljgmc",StandardBasicTypes.STRING);
			 q.setResultTransformer(Transformers.aliasToBean(HJcGljg.class));
			 List<HJcGljg> lists = q.list();
			 if (lists!=null&&lists.size() > 0) {
					return lists.get(0);
			 }else{
					return null;
			}
	}catch(Exception e){
    	e.printStackTrace();
    }
		return null;
	}

}
