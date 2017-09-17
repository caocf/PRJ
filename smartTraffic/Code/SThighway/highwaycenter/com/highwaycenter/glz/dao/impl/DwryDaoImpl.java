package com.highwaycenter.glz.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import com.highwaycenter.bean.DwryInfo;
import com.highwaycenter.glz.dao.DwryDao;
import com.highwaycenter.glz.model.HGlzJxDwry;

@Repository("dwrydao")
public class DwryDaoImpl extends BaseDaoDB<HGlzJxDwry> implements DwryDao{

	@Override
	public BaseQueryRecords selectDwrygkList(Integer page, Integer rows,String stationId, String selectvalue) {
		/*StringBuffer hql = new StringBuffer("select new com.highwaycenter.bean.DwryInfo(a.id, a.stationId, a.workerName,a.sex,"
			+"a.workType,a.post,a.birthday,a.telephone,"
			+"a.mobilephone, a.professional,a.entryTime,"
			+"a.workStatus) from HGlzJxDwry "+
			 "a where 1=1 ");*/
		
		/*StringBuffer hql = new StringBuffer(" from HGlzJxDwry where 1=1 ");*/
		StringBuffer sql = new StringBuffer("select a.id, a.stationId,b.name, a.workerName,a.sex,"
				+"a.workType,a.post,a.birthday,a.telephone,"
				+"a.mobilephone, a.professional,a.entryTime,"
				+"a.workStatus  from h_glz_jx_dwry as a left join h_glz_glz_jbxx as b on a.stationId=b.id where 1=1 ");
		if(stationId!=null&&!stationId.trim().equals("")){
			sql.append(" and a.stationId ='"+stationId+"' ");
		}
		if(selectvalue!=null&&!selectvalue.trim().equals("")){
			sql.append(" and a.workerName like '%"+selectvalue+"%' ");
		}
		BaseQueryRecords records = super.find(new SQL(sql.toString()),page,rows);
		List<Object[]> objlist  = (List<Object[]>) records.getData();
		List<DwryInfo> dwrylist  =new  ArrayList<DwryInfo>();
		if(objlist!=null&&objlist.size()>0){
			Iterator<Object[]> it = objlist.iterator();
			while(it.hasNext()){
				Object[] obj = it.next();
				DwryInfo dwry = new DwryInfo();
				dwry.setId((String)obj[0]);
				dwry.setStationId((String)obj[1]);
				dwry.setStationName((String)obj[2]);
				dwry.setWorkerName((String)obj[3]);
				dwry.setSex((short)obj[4]);
				dwry.setWorkType((String)obj[5]);
				dwry.setPost((String)obj[6]);
				dwry.setBirthday((Date)obj[7]);
				dwry.setTelephone((String)obj[8]);
				dwry.setMobilephone((String)obj[9]);
				dwry.setProfessional((String)obj[10]);
				dwry.setEntryTime((Date)obj[11]);
				dwry.setWorkStatus((short)obj[12]);
				dwrylist.add(dwry);
				
			}
		}
		
		records.setData(dwrylist);
		return records;
		
		
		
		
	}

	@Override
	public HGlzJxDwry selectDwryXq(String id) {
		
		return super.findUnique(new HGlzJxDwry(), "id",id);
	}

}
