package com.highwaycenter.gljg.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.SQL;
import com.highwaycenter.bean.GlryInfo;
import com.highwaycenter.gljg.dao.RybmDao;
import com.highwaycenter.gljg.model.HJcGlbm;
import com.highwaycenter.gljg.model.HJcRybm;
import com.highwaycenter.gljg.model.HjcZw;

@Repository("rybmdao")
public class RybmDaoImpl extends BaseDaoDB<HJcRybm> implements RybmDao{

	@Override
	public void deleteByry(Integer rybh) {
		String sql = "delete from h_jc_rybm where rybh="+rybh;
		super.delete(new SQL(sql));
		
	}

	@Override
	public void deleteBybm(String bmbh) {
		String sql = "delete from h_jc_rybm where bmbh='"+bmbh+"'";
		super.delete(new SQL(sql));
		
	}

	@Override
	public void updateRybmorder(int rybmbh, int order) {
		String sql = "update  h_jc_rybm set ryordercolumn="+order+" where rybmbh="+rybmbh;
		super.update(new SQL(sql));
		
	}

	@Override
	public List<String> selectBmByry(Integer rybh) {
		String sql = "select a.bmmc from h_jc_glbm a, h_jc_rybm b where a.bmdm=b.bmbh and b.rybh = "+rybh;
		return (List<String>) super.find(new SQL(sql)).getData();
	}
	
	@Override
	public  List<HJcRybm> selectRybmList(int rybh){
		String sql = " select a.rybh,a.bgsdh,a.zwbh,a.bmbh,b.bmmc from h_jc_rybm a,h_jc_glbm b where a.bmbh=b.bmdm and a.rybh ="+rybh
				+" order by b.ordercolumn desc";
		  try {
				SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
				 q.addScalar("rybh",StandardBasicTypes.INTEGER);
				 q.addScalar("bgsdh",StandardBasicTypes.STRING);
				 q.addScalar("zwbh",StandardBasicTypes.STRING);
				 q.addScalar("bmbh",StandardBasicTypes.STRING);
				 q.addScalar("bmmc",StandardBasicTypes.STRING);
				 //q.addScalar("zwmc",StandardBasicTypes.STRING);
				 q.setResultTransformer(Transformers.aliasToBean(HJcRybm.class));
				 return (List<HJcRybm>) new BaseQueryRecords(q.list()).getData();
		  }catch(Exception e){
			  e.printStackTrace();
			  return new ArrayList<HJcRybm>();
		  }
	}

	@Override
	public List<GlryInfo> selectGlryBySsjgOrder(String bmbh,
			String orderkey, boolean ifdesc) {
		String sql = "select b.rybmbh as rybh,a.ryxm from h_jc_glry a,h_jc_rybm b where a.rybh=b.rybh and b.bmbh ='"+bmbh+"' order by "
				+orderkey+" ";
		if(ifdesc==true){
			sql+=" desc";
		}
		sql+=" ,a.xmpyszm";
		  try {
				SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
				 q.addScalar("rybh",StandardBasicTypes.INTEGER);
				 q.addScalar("ryxm",StandardBasicTypes.STRING);
				 q.setResultTransformer(Transformers.aliasToBean(GlryInfo.class));
				 return (List<GlryInfo>) new BaseQueryRecords(q.list()).getData();
		  }catch(Exception e){
			  e.printStackTrace();
			  return new ArrayList<GlryInfo>();
		  }
		
		
		
	}

	@Override
	public List<HJcGlbm> selectBmObjList(int rybh) {
		String sql = "select a.bmdm,a.bmmc from h_jc_glbm a, h_jc_rybm b where a.bmdm=b.bmbh and b.rybh = "+rybh;
		try {
			SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
			 q.addScalar("bmdm",StandardBasicTypes.STRING);
			 q.addScalar("bmmc",StandardBasicTypes.STRING);
			 q.setResultTransformer(Transformers.aliasToBean(HJcGlbm.class));
			 return (List<HJcGlbm>) new BaseQueryRecords(q.list()).getData();
	  }catch(Exception e){
		  e.printStackTrace();
		  return new ArrayList<HJcGlbm>();
	  }
	}

	

	@Override
	public int selectZwbh(String zwmc) {
		String sql ="select zwbh from h_jc_zw where zwmc='"+zwmc+"'";
		
		return (Integer)super.findUnique(new SQL(sql));
	}

	@Override
	public BaseQueryRecords selectZwlistOrder(int page,int rows) {
		String sql ="select zwbh,zwmc from h_jc_zw ";
	       try {
				SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
				 q.addScalar("zwbh",StandardBasicTypes.INTEGER);
				 q.addScalar("zwmc",StandardBasicTypes.STRING);
				 q.setResultTransformer(Transformers.aliasToBean(HjcZw.class));
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
	public String selectZwstr(String zwbhs){
		String sql = "select group_concat(zwmc) from h_jc_zw where zwbh in ("+zwbhs+")";
		return (String) super.findUnique(new SQL(sql));
		
	}

	@Override
	public void savePost(String zwmc) {
		String sql = "insert into h_jc_zw (zwmc) values ('"+zwmc+"')";
		super.executeSql(new SQL(sql));
		
	}

	@Override
	public void deletePost(int zwbh) {
		String sql = "delete from h_jc_zw where zwbh ="+zwbh;
		super.delete(new SQL(sql));
	}

	@Override
	public void updatePost(int zwbh, String zwmc) {
		String sql = "update   h_jc_zw set zwmc = '"+zwmc+"' where zwbh ="+zwbh;
		super.update(new SQL(sql));
	}

	@Override
	public String selectzwbhs(String zwmc) {
		String sql = "select group_concat(CAST(zwbh as char)) as zwbh from h_jc_zw where zwmc in ("+zwmc+")";
		System.out.println("sql"+sql);
		return (String) super.findUnique(new SQL(sql));
	}

	@Override
	public void updateRybmzw(int rybh, String bgsdh, String zwbh) {
		String sql = "";
		if(bgsdh==null){
			 sql = "update h_jc_rybm set zwbh='"+zwbh+"' where rybh ="+rybh ;
		}else if(zwbh==null){
			 sql = "update h_jc_rybm set bgsdh='"+bgsdh+"' where rybh ="+rybh ;
		}else{
			 sql = "update h_jc_rybm set zwbh='"+zwbh+"', bgsdh ='"+bgsdh+"' where rybh ="+rybh ;
		}
		super.update(new SQL(sql));
	}

	@Override
	public String selectAllZw() {
		String sql = "select group_concat(zwbh) from h_jc_rybm";
		return (String) super.findUnique(new SQL(sql));
	}

	@Override
	public int countSameName(Integer zwbh, String zwmc) {
		return super.countSameName("zwmc", zwmc, "h_jc_zw", "zwbh", zwbh);
		
	}

	
	
}
