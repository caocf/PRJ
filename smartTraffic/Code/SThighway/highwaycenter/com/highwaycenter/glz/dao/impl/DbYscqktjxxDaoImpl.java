package com.highwaycenter.glz.dao.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import com.highwaycenter.bean.DwryInfo;
import com.highwaycenter.bean.selectListBean;
import com.highwaycenter.glz.dao.DbYscqktjxxDao;
import com.highwaycenter.glz.model.HGlzDbYscqktjxx;
@Repository("dbyscqktjxxdao")
public class DbYscqktjxxDaoImpl extends BaseDaoDB<HGlzDbYscqktjxx> implements DbYscqktjxxDao{

	@Override
	public BaseQueryRecords selectYsctjxxList(int page, int rows,
			String stationId, String workDate) {
		try{
		StringBuffer sql = new StringBuffer("select a.id,a.stationId,b.name,a.workDate,a.planFund,a.actualFund,a.planSandStone,a.actualSandStone,a.planAsphalt,"+
			"a.actualAsphalt,a.qualityNorm,a.actualQuality,a.qualityPercent,a.planAmount,a.actualAmount,a.projectQuality,a.dutyPercent,a.producePercent ,DATE_FORMAT(a.workDate,'%Y年%m月') from h_glz_db_yscqktjxx as a "+
					 "left join h_glz_glz_jbxx as b on a.stationId=b.id where 1=1 ");
		if(stationId!=null&&!stationId.trim().equals("")){
			sql.append(" and a.stationId ='"+stationId+"' ");
		}
		if(workDate!=null&&!workDate.equals("")){
			sql.append(" and a.workDate like '%"+workDate+"%' " );
		}
	
		BaseQueryRecords records = super.find(new SQL(sql.toString()),page,rows);
		List<Object[]> objlist  = (List<Object[]>) records.getData();
		List<HGlzDbYscqktjxx> ysclist  =new  ArrayList<HGlzDbYscqktjxx>();
		if(objlist!=null&&objlist.size()>0){
			Iterator<Object[]> it = objlist.iterator();
			while(it.hasNext()){
				Object[] obj = it.next();
				HGlzDbYscqktjxx ysc = new HGlzDbYscqktjxx();
				ysc.setId((String)obj[0]);
				ysc.setStationId((String)obj[1]);
				ysc.setStationName((String)obj[2]);
				ysc.setWorkDate((Timestamp)obj[3]);
				
				ysc.setPlanFund(obj[4]==null?null:((BigDecimal)obj[4]).doubleValue());
				ysc.setActualFund(obj[5]==null?null:((BigDecimal)obj[5]).doubleValue());
				ysc.setPlanSandStone(obj[6]==null?null:((BigDecimal)obj[6]).doubleValue());
				ysc.setActualSandStone(obj[7]==null?null:((BigDecimal)obj[7]).doubleValue());
				ysc.setPlanAsphalt(obj[8]==null?null:((BigDecimal)obj[8]).doubleValue());
				ysc.setActualAsphalt(obj[9]==null?null:((BigDecimal)obj[9]).doubleValue());
				ysc.setQualityNorm(obj[10]==null?null:((BigDecimal)obj[10]).doubleValue());
				ysc.setActualQuality(obj[11]==null?null:((BigDecimal)obj[11]).doubleValue());
				ysc.setQualityPercent(obj[12]==null?null:((BigDecimal)obj[12]).doubleValue());
				ysc.setPlanAmount(obj[13]==null?null:((BigDecimal)obj[13]).doubleValue());
				ysc.setActualAmount(obj[14]==null?null:((BigDecimal)obj[14]).doubleValue());
				ysc.setProjectQuality((String)obj[15]);
				ysc.setDutyPercent(obj[16]==null?null:((BigDecimal)obj[16]).doubleValue());
				ysc.setProducePercent(obj[17]==null?null:((BigDecimal)obj[17]).doubleValue());
				ysc.setWorkdateString((String)obj[18]);
				ysclist.add(ysc);
			}
		}
		records.setData(ysclist);
		return records;
		}catch(java.lang.Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<selectListBean> selectWorkDateList() {
		String sql = "select distinct workDate from h_glz_db_yscqktjxx order by workDate ";
		List<Timestamp> objlist =  (List<Timestamp>) super.find(new SQL(sql)).getData();
		HashSet<String> timeset = new HashSet<String>();
		List<selectListBean> list = new ArrayList<selectListBean>();
		if(objlist!=null&&objlist.size()>0){
			Iterator<Timestamp> it = objlist.iterator();
			while(it.hasNext()){
				
				Timestamp timetemp = it.next();
				if(timetemp!=null){
                String timeid = ""; 
                DateFormat idformate = new SimpleDateFormat("yyyy");   
                timeid = idformate.format(timetemp);
                timeset.add(timeid);
                }
			}
			
			Iterator<String> itString = timeset.iterator();
			while(itString.hasNext()){
				String timeid = itString.next();
				String timename = timeid+"年";
				selectListBean bean = new selectListBean();
				bean.setId_string(timeid);
				bean.setName(timename);
				list.add(bean);
						
			}
			
			
		}
		Collections.sort(list);
		return list;
	}

	
	
}
