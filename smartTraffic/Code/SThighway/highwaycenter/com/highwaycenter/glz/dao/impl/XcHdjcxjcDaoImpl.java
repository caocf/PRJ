package com.highwaycenter.glz.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.SQL;
import com.highwaycenter.bean.HdjcxjcBean;
import com.highwaycenter.bean.HdjcxjcmxBean;
import com.highwaycenter.bean.QljcxjcBean;
import com.highwaycenter.bean.QljcxjcmxBean;
import com.highwaycenter.bean.selectListBean;
import com.highwaycenter.glz.dao.XcHdjcxjcDao;
import com.highwaycenter.glz.model.HGlzXcHdjcxjc;
@Repository("hdjcxjcdao")
public class XcHdjcxjcDaoImpl extends BaseDaoDB<HGlzXcHdjcxjc> implements XcHdjcxjcDao{

	@Override
	public BaseQueryRecords selectHdjcxjcList(Integer page, Integer rows,
			String stationId, String culverId, String selectvalue) {
		StringBuffer sql = new StringBuffer("select a.id,b.sectionName, a.culverId, c.code,"+
				"a.checkDate,a.maintenanceOrg,a.nextCheckDate,a.judgeType,"
				+ "a.weather,a.culverPeg,a.culverType)  from h_glz_xc_hdjcxjc as a left join h_glz_glz_gyld as b on "
				+" a.partId = b.id left join h_glz_hdgk as c on a.culverId =c.id where 1=1 ");
	        if(stationId!=null&&!stationId.trim().equals("")){
	        	sql.append(" and a.stationId ='"+stationId+"' ");
	        }
	        if(culverId!=null&&!culverId.trim().equals("")){
	        	sql.append(" and a.culverId ='"+culverId+"' ");
	        }
			List list = new ArrayList();
	        BaseQueryRecords records = super.find(new SQL(sql.toString()),page,rows);
	        List<Object[]> objlist = (List<Object[]>) records.getData();
	        if(objlist!=null&&objlist.size()>0){
	        	Iterator<Object[]> it = objlist.iterator();
	        	while(it.hasNext()){
	        		Object[] obj = it.next();
	        		HdjcxjcBean  hdljc = new HdjcxjcBean();
	        		hdljc.setId((String)obj[0]);
	        		hdljc.setSectionName((String)obj[1]);
	        		hdljc.setCulverId((String)obj[2]);
	        		hdljc.setCulverCode((String)obj[3]);
	        		hdljc.setCheckDate((Timestamp)obj[4]);
	        		hdljc.setMaintenanceOrg((String)obj[5]);
	        		hdljc.setNextCheckDate((Timestamp)obj[6]);
	        		hdljc.setJudgeType((String)obj[7]);
	        		hdljc.setWeather((String)obj[8]);
	        		hdljc.setCulverPeg((String)obj[9]);
	        		hdljc.setCulverType((String)obj[10]);
	        		list.add(hdljc);	
	          }
	        }
	        	records.setData(list);
	        
			return records;
	}

	@Override
	public HdjcxjcBean selectHdjcxjcXq(String id) {
		String sql = "select a.id,b.sectionName, a.culverId, c.code,"+
				"a.checkDate,a.maintenanceOrg,a.nextCheckDate,a.judgeType,"
				+ "a.weather,a.culverPeg,a.culverType)  from h_glz_xc_hdjcxjc as a left join h_glz_glz_gyld as b on "
				+" a.partId = b.id left join h_glz_hdgk as c on a.culverId =c.id where a.id ='"+id+"'";
		Object[] obj = (Object[]) super.findUnique(new SQL(sql));
		HdjcxjcBean  hdljc = new HdjcxjcBean();
		if(obj!=null){
		
    		hdljc.setId((String)obj[0]);
    		hdljc.setSectionName((String)obj[1]);
    		hdljc.setCulverId((String)obj[2]);
    		hdljc.setCulverCode((String)obj[3]);
    		hdljc.setCheckDate((Timestamp)obj[4]);
    		hdljc.setMaintenanceOrg((String)obj[5]);
    		hdljc.setNextCheckDate((Timestamp)obj[6]);
    		hdljc.setJudgeType((String)obj[7]);
    		hdljc.setWeather((String)obj[8]);
    		hdljc.setCulverPeg((String)obj[9]);
    		hdljc.setCulverType((String)obj[10]);
    		
		}
		
		
		
		
		return hdljc;
	}

	@Override
	public List<HdjcxjcmxBean> selecHdjcxjcmxXq(String checkId) {
		String sql = "select a.id,b.code,b.name,a.description, a.suggestion,a.memo,a.judgeType from h_glz_xc_hdjcxjcmx as a ,"
				+ "h_glz_xc_jcbj as b where a.componentId = b.id and a.checkId = '"+checkId+"'";
		List<HdjcxjcmxBean> list = new ArrayList<HdjcxjcmxBean>();
		List<Object[]> objlist =  (List<Object[]>) super.find(new SQL(sql)).getData();
		if(objlist!=null&&objlist.size()>0){
			Iterator<Object[]> it = objlist.iterator();
			while(it.hasNext()){
				Object[] obj = it.next();
				HdjcxjcmxBean  hdjcmx = new HdjcxjcmxBean();
				
				hdjcmx.setId((String)obj[0]);
				hdjcmx.setComponentCode((String)obj[1]);
				hdjcmx.setComponentName((String)obj[2]);
				hdjcmx.setDescription((String)obj[3]);
				hdjcmx.setSuggestion((String)obj[4]);
				hdjcmx.setMemo((String)obj[5]);
				hdjcmx.setJudgeType((String)obj[6]);
				 list.add(hdjcmx);
			}
		}
		
		return list;
	}

	@Override
	public List<Object[]> selectPropertyList(String key1, String key2) {
		String sql = "select distinct a."+key1 +",a."+key2+" from h_glz_xc_hdjcxjcmx a";
		return (List<Object[]>) super.find(new SQL(sql)).getData();
	}

	@Override
	public List<selectListBean> selectHdCodeList() {
		String sql = "select distinct a.culverId ,b.code from h_glz_xc_hdjcxjc as a,h_glz_hdgk as b where a.culverId =b.id";
		List list = new ArrayList();
		List<Object[]> objlist = (List<Object[]>) super.find(new SQL(sql)).getData();
        if(objlist!=null&&objlist.size()>0){
        	Iterator<Object[]> it = objlist.iterator();
        	while(it.hasNext()){
        		Object[] obj = it.next();
        		selectListBean hdmc = new selectListBean();
        		hdmc.setId_string((String)obj[0]);
        		hdmc.setName((String)obj[1]);
        		list.add(hdmc);
        	}
        }
		return list;
	}

}
