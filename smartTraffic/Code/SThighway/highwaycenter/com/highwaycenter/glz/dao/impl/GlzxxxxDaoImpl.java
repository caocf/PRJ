package com.highwaycenter.glz.dao.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javassist.bytecode.Descriptor.Iterator;

import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import com.highwaycenter.bean.GlzJbxx;
import com.highwaycenter.glz.dao.GlzxxxxDao;
import com.highwaycenter.glz.model.HGlzGlzXxxx;
@Repository("glzxxxxdao")
public class GlzxxxxDaoImpl extends BaseDaoDB<HGlzGlzXxxx> implements GlzxxxxDao{
	
    @Override
    public HGlzGlzXxxx selectGlzqx(String id) {  
	    return super.findUnique(new HGlzGlzXxxx(), "id",id);
    }

	@Override
	public BaseQueryRecords selectGlzgkList(Integer page, Integer rows,
			String stationId, String selectvalue) {
		StringBuffer hql = new StringBuffer("select new com.highwaycenter.bean.Glzgk (a.id, a.stationId,a.year,"+
			"a.maintainTotal, a.bridgeCount, a.tunnelCount,a.greenLong, a.countryRoad,"+
			 "a.provinceRoad,a.cityRoad) from HGlzGlzXxxx a where 1=1 ");
		if(stationId!=null&&!stationId.equals(null)){
			hql.append(" and a.stationId = '"+stationId+"' ");
		}
		if(selectvalue!=null&&!selectvalue.equals("")){
			hql.append("");
		}
		
		return super.find(new HQL(hql.toString()),page,rows);
	}

	@Override
	public List<Object[]> selectPropertyList(String key1, String key2) {
		String sql = "select distinct "+key1 +", "+key2+" from h_glz_glz_xxxx ";
		return (List<Object[]>) super.find(new SQL(sql)).getData();
	}

	@Override
	public BaseQueryRecords selectGlzgkListNew(Integer page,Integer rows,String gljgdm,String selectvalue,String selectId) {
       try{
		List<GlzJbxx> glzlist = new ArrayList<GlzJbxx>();
		/*StringBuffer sql = new StringBuffer("select (a.id) as stationId,a.orgId,a.code,a.name,a.remark,a.address,a.telephone,a.fax,a.supervisor,a.email,a.builddate,a.picId,c.id,c.year,c.bridgeCount,c.tunnelCount ,c.countryRoad,c.provinceRoad,c.cityRoad from h_glz_glz_jbxx as a left join "+
                                          "(SELECT b.* from h_glz_glz_xxxx b join ( select d.stationid ,max(d.year) as maxyear from h_glz_glz_xxxx  "
	                                 	+ "as d group by stationId ) t on b.stationid= t.stationid and t.maxyear=b.year) c on a.id=c.stationid  where 1=1 " );*/
		
		StringBuffer sql = new StringBuffer("select (a.id) as stationId,a.orgId,a.code,a.name,a.remark,a.address,a.telephone,a.fax,a.supervisor,a.email,a.builddate,a.picId,c.id,c.year,c.bridgeCount,c.tunnelCount ,c.countryRoad,c.provinceRoad,c.cityRoad,e.gljgdm,e.gljgmc "
                +" from h_glz_glz_jbxx as a left join "+
               "(SELECT b.* from h_glz_glz_xxxx b join ( select d.stationid ,max(d.year) as maxyear from h_glz_glz_xxxx  "
          	+ "as d group by stationId ) t on b.stationid= t.stationid and t.maxyear=b.year) c on a.id=c.stationid  left join   h_jc_jgglzdy as f on f.stationId = a.id left join "+
        "h_jc_gljg as e on   f.gljgdm = e.gljgdm where 1=1 " );
		
		if(selectId!=null&&!selectId.equals("")){
			sql.append(" and c.id = '"+selectId+"' ");
		}
		else{
		
		if(gljgdm!=null&&!gljgdm.trim().equals("")){
			/*sql.append(" and a.xzqhdm = '"+stationId+"' ");*/
			sql.append(" and f.gljgdm = '"+gljgdm+"' ");
		}
		
		if(selectvalue!=null&&!selectvalue.trim().equals("")){
			 sql.append("and (a.name like '%"+selectvalue+"%' or a.code like '%"+selectvalue+"%') ");
		}
		}
		BaseQueryRecords records = super.find(new SQL(sql.toString()),page,rows);
		List<Object[]> objlist = (List<Object[]>) records.getData();
		if(objlist!=null&&objlist.size()>0){
			java.util.Iterator<Object[]> it = objlist.iterator();
			while(it.hasNext()){
				Object[] obj = it.next();
				GlzJbxx glz = new GlzJbxx();
				glz.setStationId((String) obj[0]);
				glz.setOrgId((String)obj[1]);
				glz.setCode((String)obj[2]);
				glz.setName((String)obj[3]);
				glz.setRemark((String)obj[4]);
				glz.setAddress((String)obj[5]);
				glz.setTelephone((String)obj[6]);
				glz.setFax((String)obj[7]);
				glz.setSupervisor((String)obj[8]);
				glz.setEmail((String)obj[9]);
				glz.setBuildDate((Timestamp)obj[10]);
				glz.setPicId((String)obj[11]);
				glz.setXxxxId((String)obj[12]);
				glz.setYear((Integer)obj[13]);
				glz.setBridgeCount((BigDecimal)obj[14]);
				glz.setTunnelCount((BigDecimal)obj[15]);
				glz.setCountryRoad((BigDecimal)obj[16]);
				glz.setProvinceRoad((BigDecimal)obj[17]);
				glz.setCityRoad((BigDecimal)obj[18]);
				glz.setGljgdm((String)obj[19]);
				glz.setGljgmc((String)obj[20]);
				glzlist.add(glz);
				
			}
		}
		
		
		records.setData(glzlist);
		return records;
		}catch (java.lang.Exception e){
			e.printStackTrace();
		}
		return null;
		
	}





}