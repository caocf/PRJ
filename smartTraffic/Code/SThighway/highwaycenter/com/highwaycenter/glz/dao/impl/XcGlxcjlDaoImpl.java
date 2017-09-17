package com.highwaycenter.glz.dao.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import com.highwaycenter.bean.DwryInfo;
import com.highwaycenter.bean.GlxcldBean;
import com.highwaycenter.bean.XcglgkBean;
import com.highwaycenter.bean.selectListBean;
import com.highwaycenter.glz.dao.XcGlxcjlDao;
import com.highwaycenter.glz.model.HGlzXcGlxcjl;

@Repository("xcglxcjldao")
public class XcGlxcjlDaoImpl extends BaseDaoDB<HGlzXcGlxcjl> implements XcGlxcjlDao{

	@Override
	public BaseQueryRecords selectXcGlxcjl(Integer page, Integer rows,
			String stationId, String workDate) {
		StringBuffer sql = new StringBuffer("select a.id, a.stationId,b.name,a.inspectDate,DATE_FORMAT(a.inspectDate,'%Y年%m月%d日'), a.weather,a.content,"+
			" a.inspector, a.handlerSignature,  DATE_FORMAT(a.handleDate,'%Y-%m-%d') from h_glz_xc_glxcjl as a left join h_glz_glz_jbxx as b on a.stationId=b.id  where 1=1 ");
		StringBuffer sqlcount = new StringBuffer("select count(*) from h_glz_xc_glxcjl as a left join h_glz_glz_jbxx as b on a.stationId=b.id  where 1=1 ");
		
		if(stationId!=null&&!stationId.trim().equals("")){
			sql.append(" and a.stationId ='"+stationId+"' " );
			sqlcount.append(" and a.stationId ='"+stationId+"' " );
		}
		if(workDate!=null&&!workDate.equals("")){
			sql.append(" and a.inspectDate like '%"+workDate+"%' " );
			sqlcount.append(" and a.inspectDate like '%"+workDate+"%' " );
		}
		sql.append("order by a.inspectDate desc");
		BaseQueryRecords records = super.findNewC(new SQL(sql.toString()),sqlcount.toString(),page,rows);
		List<Object[]> objlist  = (List<Object[]>) records.getData();
		List<XcglgkBean> xclist  =new  ArrayList<XcglgkBean>();
		if(objlist!=null&&objlist.size()>0){
			Iterator<Object[]> it = objlist.iterator();
			while(it.hasNext()){
				Object[] obj = it.next();
				XcglgkBean xc = new XcglgkBean();
				xc.setId((String)obj[0]);
				xc.setStationId((String)obj[1]);
				xc.setStationName((String)obj[2]);
				xc.setInspectDate((Date)obj[3]);
				xc.setInspectDateString((String)obj[4]);
				xc.setWeather((String)obj[5]);
				xc.setContent((String)obj[6]);
				String inspector = (String)obj[7];
				String inspectorNew =inspector;
				if(inspector!=null&&inspector.length()>0&&inspector.substring(0, 1).equals(",")){		
					inspectorNew = inspector.substring(1,inspector.length());
				}
				xc.setInspector(inspectorNew);
				String handlerSignature = (String)obj[8];
				String handlerSignatureNew = handlerSignature;
				if(handlerSignature!=null&&handlerSignature.length()>0&&handlerSignature.substring(0, 1).equals(",")){		
					handlerSignatureNew = handlerSignature.substring(1,handlerSignature.length());
				}
				xc.setHandlerSignature(handlerSignatureNew);
				xc.setHandleDate((String)obj[9]);
				xclist.add(xc);
				
			}
		}
		
		records.setData(xclist);
		

		return records;
	}

	@Override
	public HGlzXcGlxcjl selectXcGlxcjlXq(String id) {
	    
		return super.findUnique(new HGlzXcGlxcjl(), "id",id);
	}

	//String id, String roadRecordId, String sectionId,String sectionName, String stake
	@Override
	public List<GlxcldBean> selectXcGlxcldXq(String roadRecordId) {
		GlxcldBean xcld= new GlxcldBean();
		List<GlxcldBean>  xcjllist  = new ArrayList<GlxcldBean>();
		String sql ="select a.id,a.roadRecordId,a.sectionId,b.sectionName,a.stake from h_glz_xc_glxcld as a left join h_glz_glz_gyld as b "+
	               " on  a.sectionId=b.id where a.roadRecordId ='"+roadRecordId+"'";
		List<Object[]> objlist =  (List<Object[]>) super.find(new SQL(sql)).getData();
		if(objlist!=null&&objlist.size()>0){
			Iterator<Object[]> it = objlist.iterator();
			while(it.hasNext()){
				Object[] obj = it.next();
		         xcld= new GlxcldBean((String)obj[0],(String)obj[1],(String)obj[2],(String)obj[3],(String)obj[4]);
		         xcjllist.add(xcld);
		  }
		}
		
		return xcjllist;
	}

	@Override
	public List<selectListBean> selectInspectDateList() {
		String sql = "select distinct inspectDate from h_glz_xc_glxcjl order by inspectDate ";
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
