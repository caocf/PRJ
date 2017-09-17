package com.highwaycenter.gljg.dao.impl;

import java.sql.Timestamp;

import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.SQL;
import com.highwaycenter.gljg.dao.DllpDao;
import com.highwaycenter.gljg.model.HJcDllp;
import com.highwaycenter.gljg.model.HJcGlry;



@Repository("dllpdao")
public class DllpDaoImpl  extends BaseDaoDB<HJcDllp> implements DllpDao{


	@Override
	public void save(HJcDllp dllp) {
		 super.save(dllp);
		
	}

	@Override
	public void update(HJcDllp dllp) {
		super.update(dllp);
		
	}

	@Override
	public void delete(HJcDllp dllp) {
		super.delete(dllp);
		
	}
	
	@Override
	public HJcDllp findById(String token) {
		
		return super.findUnique(new HJcDllp(), "lp", token);
	}
	
	/*@Override
	public int getCountByLP(String token) {
		
		return super.count(new HJcDllp(), "lp", token).intValue();
	}
	*/

	@Override
	public String saveAndReturn(HJcDllp dllp) {
		return (String) super.saveAndReturn(dllp);
	}

	@Override
	public int countdllp(String token) {
		return super.count(new HJcDllp(), "lp", token).intValue();
	}

	@Override
	public void deleteByRy(Integer rybh) {
		String sql = "delete from h_jc_dllp where rybh = "+rybh;
		 super.delete(new SQL(sql));
		
	}

	@Override
	public HJcGlry findRyBylp(String token) {
		String sql = "select a.rybh,b.ryxm from h_jc_dllp as a,h_jc_glry as b where a.rybh = b.rybh and a.lp='"+token+"'";
		HJcGlry glry = new HJcGlry();
		Object[] obj = (Object[]) super.findUnique(new SQL(sql));
		if(obj!=null){
			glry.setRybh((Integer)obj[0]);
			glry.setRyxm((String)obj[1]);
		}
		return glry;
	}

	@Override
	public Integer rybhBylp(String token) {
		String sql = "select a.rybh from h_jc_dllp as a where a.lp='"+token+"'";
		return (Integer) super.findUnique(new SQL(sql));
	}

	@Override
	public void deleteByLp(String lp) {
		String sql = "delete from h_jc_dllp where lp = '"+lp+"'";
		 super.delete(new SQL(sql));
		
	}

	@Override
	public String selectLpByRy(Integer rybh) {
		String  sql ="select lp from h_jc_dllp where rybh ="+rybh;
		return (String) super.findUnique(new SQL(sql));
	}

	@Override
	public void updateLp(String newlp,Timestamp dlsj,
    		String dldz,Timestamp scczsj,Integer rybh) {
		String sql = "update h_jc_dllp set lp='"+newlp+"', dlsj='"+dlsj+"',dldz='"
				+ dldz+"',scczsj='"+scczsj+"' "
				+ " where rybh="+rybh;
		 super.update(new SQL(sql));
		
	}

	


}
